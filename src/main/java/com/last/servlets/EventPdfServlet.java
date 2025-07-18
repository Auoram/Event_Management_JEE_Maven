/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.last.servlets;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.last.dao.EventDao;
import com.last.entities.Event;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/download-event-pdf")
public class EventPdfServlet extends HttpServlet {
    private final EventDao eventDao = new EventDao();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int eventId = Integer.parseInt(request.getParameter("eventId"));
            Event event = eventDao.getEventById(eventId);
            if (event == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            document.add(new Paragraph("Event Registration Confirmation", titleFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Event: " + event.getName(), normalFont));
            document.add(new Paragraph("Date: " + event.getDate().toString(), normalFont));
            document.add(new Paragraph("Location: " + event.getLocation(), normalFont));
            document.add(new Paragraph("Your registration has been confirmed.", normalFont));
            document.close();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", 
                "attachment; filename=\"event-registration-" + eventId + ".pdf\"");
            response.setContentLength(baos.size());
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush(); 
        } catch (Exception e) {
            throw new ServletException("Error generating PDF", e);
        }
    }
}