<%-- 
    Document   : adminDashboard
    Created on : 13 juin 2025, 14:54:03
    Author     : lenovo
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="#">Event Management</a>
            <div class="navbar-nav ms-auto">
                <span class="navbar-text me-3">Welcome, ${user.username} (Admin)</span>
                <a class="nav-link" href="logout">Logout</a>
            </div>
        </div>
    </nav>
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>Events Management</h2>
            <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#createEventModal">
                Create New Event
            </button>
        </div>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Date</th>
                    <th>Location</th>
                    <th>Seats Available</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% for (com.last.entities.Event event : (java.util.List<com.last.entities.Event>) request.getAttribute("events")) { %>
                    <tr>
                        <td><%= event.getName() %></td>
                        <td><%= event.getDate().format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")) %></td>
                        <td><%= event.getLocation() %></td>
                        <td><%= event.getAvailableSeats() %>/<%= event.getTotalSeats() %></td>
                        <td>
                            <button class="btn btn-sm btn-warning" data-bs-toggle="modal" 
                                data-bs-target="#editEventModal" 
                                data-id="<%= event.getId() %>"
                                data-name="<%= event.getName() %>"
                                data-description="<%= event.getDescription() %>"
                                data-date="<%= event.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")) %>"
                                data-location="<%= event.getLocation() %>"
                                data-seats="<%= event.getTotalSeats() %>">
                                Edit
                            </button>
                            <form action="events/delete" method="post" class="d-inline">
                                <input type="hidden" name="id" value="<%= event.getId() %>">
                                <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                            </form>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>
    <div class="modal fade" id="createEventModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="events/create" method="post">
                    <div class="modal-header">
                        <h5 class="modal-title">Create New Event</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label">Event Name</label>
                            <input type="text" name="name" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Description</label>
                            <textarea name="description" class="form-control"></textarea>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Date & Time</label>
                            <input type="datetime-local" name="date" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Location</label>
                            <input type="text" name="location" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Total Seats</label>
                            <input type="number" name="totalSeats" class="form-control" min="1" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary">Create Event</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="modal fade" id="editEventModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="events/update" method="post">
                    <input type="hidden" name="id" id="editEventId">
                    <div class="modal-header">
                        <h5 class="modal-title">Edit Event</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label">Event Name</label>
                            <input type="text" name="name" id="editEventName" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Description</label>
                            <textarea name="description" id="editEventDescription" class="form-control"></textarea>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Date & Time</label>
                            <input type="datetime-local" name="date" id="editEventDate" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Location</label>
                            <input type="text" name="location" id="editEventLocation" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Total Seats</label>
                            <input type="number" name="totalSeats" id="editEventSeats" class="form-control" min="1" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary">Save Changes</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.getElementById('editEventModal').addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            document.getElementById('editEventId').value = button.getAttribute('data-id');
            document.getElementById('editEventName').value = button.getAttribute('data-name');
            document.getElementById('editEventDescription').value = button.getAttribute('data-description');
            document.getElementById('editEventDate').value = button.getAttribute('data-date');
            document.getElementById('editEventLocation').value = button.getAttribute('data-location');
            document.getElementById('editEventSeats').value = button.getAttribute('data-seats');
        });
    </script>
</body>
</html>