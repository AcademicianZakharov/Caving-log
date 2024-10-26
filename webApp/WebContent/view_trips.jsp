<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    %>

<%@ page import="simple.*"%> 
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.RequestDispatcher"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Trips</title>
    <link rel="stylesheet" type="text/css" href="read_style.css">
</head>
<body>

<div class="read-container">
    <h2 class="read-heading">Trips</h2>
<%
        List<Trip> trips = (List<Trip>) session.getAttribute("trips");
        if (trips != null && !trips.isEmpty()) {
    %>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Phone</th>
                <th>Status</th>
                <th>Operations</th>
            </tr>
        </thead>
        <tbody>
            <%
                for (Trip trip : trips) {
            %>
            <tr>
                <td><%= trip.getTrip_id() %></td>
                <td><%= trip.getCaver_id() %></td>
                <td><%= trip.getCave_name() %></td>
                <td><%= trip.getStart_time() %></td>
                <td><%= trip.getEnd_time() %></td>
                <td><%= trip.getGroup_size()%></td>
                <td><%= trip.getMax_trip_length()%></td>
                
                <td class="action-buttons">
                    <form action="CrudServlet" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name=trip_id value="<%= trip.getTrip_id() %>">
                        <button type="submit" class="delete-btn">Delete</button>
                    </form>
                    
                    <form action="update_trip.jsp" method="get" style="display:inline;">
                        <input type="hidden" name="caver_id" value="<%= trip.getTrip_id() %>">
                        <button type="submit" class="update-btn">Update</button>
                    </form>      
                </td>
            </tr>
            <%
                }
            %>
        </tbody>

    </table>
    <form action="registration.jsp" method="get" style="display:inline;">
    	<button type="submit" class="insert-btn">Add trip</button>
    </form>
    <%
        } else {
    %>
    <p>No Trips found.</p>
    <%
        }
    %>


</div>      
</body>
</html>
