
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Caver Registration Form</title>
	<link rel="stylesheet" type="text/css" href="read_style.css">
	
</head>
<body>
    <div class="registration-container">
        <h2 class="registration-heading">Register New Caver</h2>
        <form action="CrudServlet" method="post">
            <div>
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" class="input-field"  pattern="^[A-Za-z\s]{1,100}$" required>
            </div>
            <div>
                <label for="status">Status:</label>
                <input type="text" id="status" name="status" class="input-field" pattern="^[A-Za-z\s]{1,100}$" required>
            </div>
            <div>
                <label for="phone">Phone Number:</label>
                <input type="tel" id="phone" name="phone" class="input-field" pattern="^[0-9]{3}-[0-9]{3}-[0-9]{4}$" required>
            </div>
            <input type="hidden" name="action" value="insert">
            <button type="submit" style="margin-top: 10px;">Add Caver</button>
        </form>
	<form action="CrudServlet" method="get" >
    	<button type="submit" class="insert-btn">Back to Cavers</button>
    </form>
    </div>
    <script src="validate_form.js"></script>
    </body>
</html>








