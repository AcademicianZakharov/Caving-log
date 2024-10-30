
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Trip Information</title>
	<link rel="stylesheet" type="text/css" href="read_style.css">
	
</head>
<body>
    <div class="update-container">
        <h2 class="update-heading">Update Caver</h2>
        <form action="TripCrudServlet" method="post">
            <div>
                <label for="cave_name">Cave Name:</label>
                <input type="text" id="cave name" name="cave_name" class="input-field"  pattern="^[A-Za-z\s]{1,100}$" required>
            </div>
            <div>
                <label for="start_time">Start Time:</label>
                <input type="text" id="start_time" name="start_time" class="input-field"  
                pattern="((((19|20)([2468][048]|[13579][26]|0[48])|2000)-02-29|((19|20)[0-9]{2}-(0[4678]|1[02])-(0[1-9]|[12][0-9]|30)|(19|20)[0-9]{2}-(0[1359]|11)-(0[1-9]|[12][0-9]|3[01])|(19|20)[0-9]{2}-02-(0[1-9]|1[0-9]|2[0-8])))\s([01][0-9]|2[0-3]):([012345][0-9]):([012345][0-9]))" required>
            </div>
            <div>
                <label for="end_time">End Time:</label>
                <input type="text" id="end_time" name="end_time" class="input-field" 
                pattern="((((19|20)([2468][048]|[13579][26]|0[48])|2000)-02-29|((19|20)[0-9]{2}-(0[4678]|1[02])-(0[1-9]|[12][0-9]|30)|(19|20)[0-9]{2}-(0[1359]|11)-(0[1-9]|[12][0-9]|3[01])|(19|20)[0-9]{2}-02-(0[1-9]|1[0-9]|2[0-8])))\s([01][0-9]|2[0-3]):([012345][0-9]):([012345][0-9]))" required>
                 required>
            </div>
            <div>
                <label for="group_size">Group Size:</label>
                <input type="tel" id="group_size" name="group_size" class="input-field" pattern="^[1-9]\d$"  required>
            </div>
            <div>
                <label for="max_trip_length">Max Trip Length:</label>
                <input type="tel" id="max_trip_length" name="max_trip_length" class="input-field" pattern="^(?:\d+|\d.\d+)$" required>
            </div>
            <input type="hidden" name="caver_id" value="<%=request.getParameter("caver_id") %>">
            <input type="hidden" name="trip_id" value="<%=request.getParameter("trip_id") %>">
            <input type="hidden" name="action" value="update">
            <button type="submit" style="margin-top: 10px;">Update Trip</button>
        </form>
    </div>
</body>
</html>








