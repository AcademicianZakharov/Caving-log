
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Caver Information</title>
	<link rel="stylesheet" type="text/css" href="read_style.css">
	
</head>
<body>
    <div class="update-container">
        <h2 class="update-heading">Update Caver</h2>
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
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="caver_id" value="<%=request.getParameter("caver_id") %>">
            <button type="submit" style="margin-top: 10px;">Update Caver Information</button>
        </form>
    </div>
</body>
</html>








