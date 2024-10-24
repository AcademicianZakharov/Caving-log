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
    <title>Read Handler</title>
    <style>
        /* CSS styles for the read form container */
        .read-container {
             
            width: 600px; /* Set the width of the container */
            margin: 20px auto; /* Center the container horizontally with some top margin */
            padding: 20px; /* Add some padding inside the container */
            border: 2px solid #ccc; /* Add a border */
            border-radius: 10px; /* Add some border radius */
            background-color: #f9f9f9; /* Background color */
        }

        /* CSS styles for the heading */
        .read--heading {
            text-align: center; /* Center the heading text */
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
    <h2 class="read-heading">Cavers</h2>
<%
        List<Caver> cavers = (List<Caver>) session.getAttribute("cavers");
        if (cavers != null && !cavers.isEmpty()) {
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
                for (Caver caver : cavers) {
            %>
            <tr>
                <td><%= caver.getCaver_id() %></td>
                <td><%= caver.getName() %></td>
                <td><%= caver.getPhone() %></td>
                <td><%= caver.getStatus() %></td>
                
                <td class="action-buttons">
                    <form action="CrudServlet" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="caver_id" value="<%= caver.getCaver_id() %>">
                        <button type="submit" class="delete-btn">Delete</button>
                    </form>
                    
                    <form action="update_caver.jsp" method="get" style="display:inline;">
                        <input type="hidden" name="caver_id" value="<%= caver.getCaver_id() %>">
                        <button type="submit" class="update-btn">Update</button>
                    </form>
                    
                    <form action="view_trips.jsp" method="get" style="display:inline;">
                        <input type="hidden" name="caver_id" value="<%= caver.getCaver_id() %>">
                        <button type="submit" class="view-btn">View Trips</button>
                    </form>
                    
                </td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
    <%
        } else {
    %>
    <p>No cavers found.</p>
    <%
        }
    %>


</div>      
</body>
</html>
