<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    %>

<%@ page import="simple.*"%> 
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>


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
    </style>
</head>
<body>

<div class="read-container">
    <h2 class="read-heading">Cavers</h2>
    <%

    request.getAttribute("cavers");

    
	  	//for (Caver caver: cavers) {
		
	  		out.print("Id: " + cavers.get(2).getCaver_id());
	        out.print("<br/>");
			
	        //out.print("Name: " + caver.getName());
	       // out.print("<br/>");
	        //out.print("Phone: " + caver.getPhone());
	        //out.print("<br/>");
	        //out.print("Status: " + caver.getStatus());
	        
	        //out.print("<br/>");
	        //out.print("<br/>");
    
		//}
    

    %>


</div>      
</body>
</html>
