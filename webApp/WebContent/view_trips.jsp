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
        int caverId = (Integer)(session.getAttribute("caver_id"));
    %>
    <table>
        <thead>
            <tr>
                <th>Trip ID</th>
                <th>Caver ID</th>
                <th>Cave Name</th>
                <th>Start Time</th>
                <th>End Time</th>
                <th>Group Size</th>
                <th>Max Trip Length</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <%
				

                for (Trip trip : trips) {
                	if(trip.getCaver_id() == caverId){
            %>
            <tr>
                <td><%= trip.getTrip_id() %></td>
                <td><%= caverId %></td>
                <td><%= trip.getCave_name() %></td>
                <td><%= trip.getStart_time() %></td>
                <td><%= trip.getEnd_time() %></td>
                <td><%= trip.getGroup_size()%></td>
                <td><%= trip.getMax_trip_length()%></td>
                
                <td class="action-buttons">
                    <form action="TripCrudServlet" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name=trip_id value="<%= trip.getTrip_id() %>">
                        <button type="submit" class="delete-btn">Delete</button>
                    </form>
                    
                    <form action="update_trip.jsp" method="get" style="display:inline;">
                        <input type="hidden" name="trip_id" value="<%= trip.getTrip_id() %>">
                        <input type="hidden" name="caver_id" value="<%= caverId %>">
                        <input type="hidden" name="saved_cave_name" value="<%= trip.getCave_name() %>">
                        <input type="hidden" name="saved_start_time" value="<%= trip.getStart_time() %>">
                        <input type="hidden" name="saved_end_time" value="<%= trip.getEnd_time() %>">
                        <input type="hidden" name="saved_group_size" value="<%= trip.getGroup_size() %>">
                        <input type="hidden" name="saved_max_trip_length" value="<%= trip.getMax_trip_length() %>">
                        <button type="submit" class="update-btn">Update</button>
                    </form>      
                </td>
            </tr>
            <%	
            		}
                }
            %>
        </tbody>

    </table>
    <form action="register_trip.jsp" method="post" style="display:inline;">
        <input type="hidden" name="caver_id" value="<%= caverId %>">
    	<button type="submit" class="insert-btn">Add trip</button>
    </form>
    <form action="CrudServlet" method="get" >
    	<button type="submit" class="insert-btn">Back to Cavers</button>
    </form>
    <%
        } else {
    %>
    <p>No Trips found.</p>
        <form action="register_trip.jsp" method="post" style="display:inline;">
        <input type="hidden" name="caver_id" value="<%= (Integer)(session.getAttribute("caver_id")) %>">
    	<button type="submit" class="insert-btn">Add trip</button>
    </form>
    <form action="CrudServlet" method="get" >
    	<button type="submit" class="insert-btn">Back to Cavers</button>
    </form>
    <%
        }
    %>

</div>
</body>
</html>
