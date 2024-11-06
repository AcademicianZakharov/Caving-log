
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trip Registration Form</title>
<link rel="stylesheet" type="text/css" href="read_style.css">

</head>
<body>
	<div class="registration-container">
		<h2 class="registration-heading">Add New Trip</h2>
		<form action="TripCrudServlet" method="post">
			<div>
				<label for="cave_name">Cave Name:</label> 
				<input type="text" id="cave name" name="cave_name" class="input-field" placeholder="cave name (no numbers or symbols)" pattern="^[A-Za-z\s]{1,100}$" required>
			</div>
			<div>
				<label for="start_time">Start Time:</label> 
				<input type="text" id="start_time" name="start_time" placeholder="YYYY-MM-DD HH:MM:SS" class="input-field" pattern="^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$" required>
			</div>
			<div>
				<label for="end_time">End Time:</label> 
				<input type="text" id="end_time" name="end_time" class="input-field" placeholder="YYYY-MM-DD HH:MM:SS" pattern="^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$" required>
			</div>
			<div>
				<label for="group_size">Group Size:</label> 
				<input type="tel" id="group_size" name="group_size" class="input-field" placeholder="only non negative integers" pattern="^\d+$" required>
			</div>
			<div>
				<label for="max_trip_length">Max Trip Length:</label> 
				<input type="tel" id="max_trip_length" name="max_trip_length" class="input-field" placeholder="only non negative decimals" pattern="^\d*[\.]?[\d]*$" required>
			</div>
			<input type="hidden" name="caver_id" id="caver_id" value="<%= request.getParameter("caver_id")%>">
			<input type="hidden" name="action" value="insert">
			<button type="submit" style="margin-top: 10px;">Add Trip</button>
		</form>

	</div>
</html>








