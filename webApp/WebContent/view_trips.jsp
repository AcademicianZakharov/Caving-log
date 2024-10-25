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
    <style>
        /* CSS styles for the read form container */
        .read-container {
             
            width: 600px; 
            margin: 20px auto; 
            padding: 20px; 
            border: 2px solid #ccc; 
            border-radius: 10px; 
            background-color: #f9f9f9; 
        }

        /* CSS styles for the heading */
        .read--heading {
            text-align: center; 
        }

        /* CSS styles for input fields */
        .input-field {
            width: 100%;
            padding: 8px;
            margin: 5px 0;
            box-sizing: border-box;
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        table, th, td {
            border: 1px solid #ccc;
        }
        th, td {
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
    </style>
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
                    
                    <form action="update_caver.jsp" method="get" style="display:inline;">
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
