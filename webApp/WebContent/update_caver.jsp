
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Caver Information</title>
    <style>
        .update-container {
            width: 600px;
            margin: 0 auto;
            padding: 20px;
            border: 2px solid #ccc;
            border-radius: 10px;
            background-color: #f9f9f9;
        }
        .update-heading {
            text-align: center;
        }
        .input-field {
            width: 100%;
            padding: 8px;
            margin: 5px 0;
            box-sizing: border-box;
        }
        .error {
            color: red;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="update-container">
        <h2 class="update-heading">Update Caver</h2>
        <form action="CrudServlet" method="post">
            <div>
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" class="input-field" required>
            </div>
            <div>
                <label for="status">Status:</label>
                <input type="text" id="status" name="status" class="input-field" required>
            </div>
            <div>
                <label for="phone">Phone Number:</label>
                <input type="tel" id="phone" name="phone" class="input-field" required>
            </div>
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="caver_id" value="<%=request.getParameter("caver_id") %>">
            <button type="submit" style="margin-top: 10px;">Update Caver Information</button>
        </form>
    </div>
</body>
</html>








