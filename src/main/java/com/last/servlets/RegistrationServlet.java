/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.last.servlets;

import com.last.dao.EventDao;
import com.last.dao.RegistrationDao;
import com.last.entities.Event;
import com.last.entities.User;
import com.last.entities.Registration;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RegistrationServlet", urlPatterns = {"/register", "/unregister"})
public class RegistrationServlet extends HttpServlet {
    private final RegistrationDao registrationDao = new RegistrationDao();
    private final EventDao eventDao = new EventDao();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        try {
            String path = request.getServletPath();
            int eventId = Integer.parseInt(request.getParameter("eventId"));
            if ("/register".equals(path)) {
                Event event = eventDao.getEventById(eventId);
                if (event.getAvailableSeats() > 0 && !registrationDao.isUserRegistered(user.getId(), eventId)) {
                    registrationDao.createRegistration(new Registration(user.getId(), eventId));
                    event.setAvailableSeats(event.getAvailableSeats() - 1);
                    eventDao.updateEvent(event);
                }
                response.sendRedirect("dashboard");
            } else if ("/unregister".equals(path)) {
                registrationDao.getRegistrationsByUser(user.getId()).stream()
                    .filter(r -> r.getEventId() == eventId)
                    .findFirst()
                    .ifPresent(r -> {
                        try {
                            registrationDao.deleteRegistration(r.getId());
                            Event event = eventDao.getEventById(eventId);
                            event.setAvailableSeats(event.getAvailableSeats() + 1);
                            eventDao.updateEvent(event);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                response.sendRedirect("dashboard");
            }
        } catch (Exception e) {
            throw new ServletException("Registration operation failed", e);
        }
    }
}