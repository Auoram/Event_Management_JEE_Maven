<%-- 
    Document   : userDashboard
    Created on : 13 juin 2025, 14:42:43
    Author     : lenovo
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="com.last.entities.Event" %>
<%@ page import="com.last.entities.Registration" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="#">Event Management</a>
            <div class="navbar-nav ms-auto">
                <span class="navbar-text me-3">Welcome, <%= ((com.last.entities.User)session.getAttribute("user")).getUsername() %></span>
                <a class="nav-link" href="logout">Logout</a>
            </div>
        </div>
    </nav>
    <div class="container mt-4">
        <h2>Upcoming Events</h2>
        <div class="row row-cols-1 row-cols-md-3 g-4 mt-3">
            <%
                List<Event> events = (List<Event>) request.getAttribute("events");
                List<Registration> registrations = (List<Registration>) request.getAttribute("registrations");
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
                if (events != null) {
                    for (Event event : events) {
                        boolean isRegistered = false;
                        if (registrations != null) {
                            for (Registration reg : registrations) {
                                if (reg.getEventId() == event.getId()) {
                                    isRegistered = true;
                                    break;
                                }
                            }
                        }
            %>
                <div class="col">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title"><%= event.getName() %></h5>
                            <p class="card-text"><%= event.getDescription() != null ? event.getDescription() : "" %></p>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">
                                    <strong>Date:</strong> 
                                    <%= event.getDate() != null ? event.getDate().format(dateFormatter) : "N/A" %>
                                </li>
                                <li class="list-group-item"><strong>Location:</strong> <%= event.getLocation() != null ? event.getLocation() : "N/A" %></li>
                                <li class="list-group-item">
                                    <strong>Seats:</strong> <%= event.getAvailableSeats() %>/<%= event.getTotalSeats() %>
                                </li>
                            </ul>
                        </div>
                        <div class="card-footer">
                            <% if (isRegistered) { %>
                                <div class="d-flex flex-column" style="gap: 0.5rem;">
                                    <form action="unregister" method="post">
                                        <input type="hidden" name="eventId" value="<%= event.getId() %>">
                                        <button type="submit" class="btn btn-danger w-100">Cancel Registration</button>
                                    </form>
                                    <a href="download-event-pdf?eventId=<%= event.getId() %>" 
                                       class="btn btn-success w-100 text-white" 
                                       style="text-decoration: none;">
                                       <i class="bi bi-file-earmark-pdf"></i> Download Ticket
                                    </a>
                                </div>
                            <% } else if (event.getAvailableSeats() > 0) { %>
                                <form action="register" method="post">
                                    <input type="hidden" name="eventId" value="<%= event.getId() %>">
                                    <button type="submit" class="btn btn-primary w-100">Register</button>
                                </form>
                            <% } else { %>
                                <button class="btn btn-secondary w-100" disabled>Sold Out</button>
                            <% } %>
                        </div>
                    </div>
                </div>
            <%
                    }
                } else {
            %>
                <div class="col-12">
                    <div class="alert alert-warning">No events available</div>
                </div>
            <%
                }
            %>
        </div>
    </div>
</body>
</html>
