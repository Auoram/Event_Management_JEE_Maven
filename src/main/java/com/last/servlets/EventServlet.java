/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.last.servlets;

import com.last.dao.EventDao;
import com.last.entities.Event;
import com.last.entities.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "EventServlet", urlPatterns = {"/events", "/events/create", "/events/update", "/events/delete"})
public class EventServlet extends HttpServlet {
    private final EventDao eventDao = new EventDao();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            if ("/events".equals(request.getServletPath())) {
                request.setAttribute("events", eventDao.getAllEvents());
                request.getRequestDispatcher("/events.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error retrieving events", e);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        try {
            String path = request.getServletPath();
            if ("/events/create".equals(path)) {
                Event event = new Event();
                event.setName(request.getParameter("name"));
                event.setDescription(request.getParameter("description"));
                event.setDate(LocalDateTime.parse(request.getParameter("date"), formatter));
                event.setLocation(request.getParameter("location"));
                event.setTotalSeats(Integer.parseInt(request.getParameter("totalSeats")));
                event.setAvailableSeats(Integer.parseInt(request.getParameter("totalSeats")));
                event.setCreatedBy(user.getId());
                eventDao.createEvent(event);
                response.sendRedirect("../events");
            } else if ("/events/update".equals(path)) {
                Event event = eventDao.getEventById(Integer.parseInt(request.getParameter("id")));
                event.setName(request.getParameter("name"));
                event.setDescription(request.getParameter("description"));
                event.setDate(LocalDateTime.parse(request.getParameter("date"), formatter));
                event.setLocation(request.getParameter("location"));
                int newTotalSeats = Integer.parseInt(request.getParameter("totalSeats"));
                if (event.getAvailableSeats() > newTotalSeats) {
                    event.setAvailableSeats(newTotalSeats);
                }
                event.setTotalSeats(newTotalSeats);
                eventDao.updateEvent(event);
                response.sendRedirect("../events");
            } else if ("/events/delete".equals(path)) {
                int eventId = Integer.parseInt(request.getParameter("id"));
                eventDao.deleteEvent(eventId);
                response.sendRedirect("../events");
            }
        } catch (Exception e) {
            throw new ServletException("Event operation failed", e);
        }
    }
}