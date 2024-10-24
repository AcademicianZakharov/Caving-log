<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Caver Registration Form</title>
    <style>
        /* CSS styles for the registration form container */
        .registration-container {
            width: 600px; /* Set the width of the container */
            margin: 0 auto; /* Center the container horizontally */
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
    <form action="CrudServlet" method="post" name="insert">
        <label for="Name">Name:</label>
        <input type="text" id="name" name="name" class="input-field" onfocus="this.value=''" value="Name" required><br>

        <label for="phone">Phone  Number:</label>
        <input type="tel" id="phone" name="phone" class="input-field" onfocus="this.value=''" value="phone" required><br>
        
        <label for="status">Status:</label>
        <input type="tel" id="status" name="status" class="input-field" onfocus="this.value=''" value="status" required><br>
        
        <input type="hidden" name="action" value="insert">
        <input type="submit" value="insert">
    </form>
</div>

</body>
</html>
