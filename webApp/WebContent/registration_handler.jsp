<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration Handler</title>
    <style>
        /* CSS styles for the registration form container */
        .registration-container {
             
            width: 600px; /* Set the width of the container */
            margin: 20px auto; /* Center the container horizontally with some top margin */
            padding: 20px; /* Add some padding inside the container */
            border: 2px solid #ccc; /* Add a border */
            border-radius: 10px; /* Add some border radius */
            background-color: #f9f9f9; /* Background color */
        }

        /* CSS styles for the heading */
        .registration-heading {
            text-align: center; /* Center the heading text */
        }

        /* CSS styles for input fields */
        .input-field {
            width: 100%;
            padding: 8px;
            margin: 5px 0;
            box-sizing: border-box;
        }
    </style>
</head>
<body>

<div class="registration-container">
    <h2 class="registration-heading">Registration Details</h2>
    <%
        // Retrieve form data
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String status = request.getParameter("status");


    %>
    <p><strong> Name:</strong> <%= name %></p>
    <p><strong>Phone Number:</strong> <%= phone %></p>
    <p><strong> Status:</strong> <%= status %></p>

</div>

</body>
</html>
