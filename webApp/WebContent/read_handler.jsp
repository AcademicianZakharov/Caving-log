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
<link rel="stylesheet" type="text/css" href="read_style.css">
<link rel="stylesheet" type="text/css" href="search_bar_styles.css">
</head>
<body>
<div class="read-container">
    <h2 class="read-heading">Cavers</h2>
    <div class="search-container">
        <input type="text"
               id="searchInput"
               class="search-input"
               placeholder="Search by name, phone, or status"
               onkeyup="searchTable()">
    </div>
    <%
    List<Caver> cavers = (List<Caver>) session.getAttribute("cavers");
    if (cavers != null && !cavers.isEmpty()) {
    %>
    <table id="caversTable">
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
                        <input type="hidden" name="previous_name" value="<%= caver.getName() %>">
                        <input type="hidden" name="previous_phone" value="<%= caver.getPhone() %>">
                        <input type="hidden" name="previous_status" value="<%= caver.getStatus() %>">
                        <button type="submit" class="update-btn">Update</button>
                    </form>
                    <form action="TripCrudServlet" method="post" style="display:inline;">
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
    <form action="registration.jsp" method="get" style="display:inline;">
        <button type="submit" class="insert-btn">Add Caver</button>
    </form>
    <%
    } else {
    %>
    <p>No cavers found.</p>
        <form action="registration.jsp" method="get" style="display:inline;">
        <button type="submit" class="insert-btn">Add Caver</button>
    </form>
    <%
    }
    %>
</div>
<script src ="search_bar.js">

</script>
</body>
</html>