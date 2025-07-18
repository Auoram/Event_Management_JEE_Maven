/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.last.servlets;

import com.last.dao.EventDao;
import com.last.dao.RegistrationDao;
import com.last.entities.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DashboardServlet", urlPatterns = {"/dashboard"})
public class DashboardServlet extends HttpServlet {
    private final EventDao eventDao = new EventDao();
    private final RegistrationDao registrationDao = new RegistrationDao();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        try {
            if ("admin".equals(user.getRole())) {
                request.setAttribute("events", eventDao.getAllEvents());
                request.getRequestDispatcher("/adminDashboard.jsp").forward(request, response);
            } else {
                request.setAttribute("events", eventDao.getUpcomingEvents());
                request.setAttribute("registrations", registrationDao.getRegistrationsByUser(user.getId()));
                request.getRequestDispatcher("/userDashboard.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Dashboard error", e);
        }
    }
}