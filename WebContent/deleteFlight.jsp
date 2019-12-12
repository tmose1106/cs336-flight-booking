<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Demo | Delete Flight</title>
</head>
<body>
	<header>
		<a href="/cs336-flight-booking/customerRepProfile.jsp">Account</a>
	</header>
	<h1>Delete Flight</h1>
	<p>Please enter the integer id of the flight you want to delete</p>
	<div style="color: #FF0000;">${errorMessage}</div>
    <form action="DeleteFlightServlet" method="post">  
	    <label for="flightID"><b>Flight ID</b> (<font color="red">integer > 0</font>):</label>
		<input type="number" step=1 name="flightID" pattern="\d+" required min=1>
		<label for="airlineID"><b>Airline ID</b> (<font color="red">char 2</font>):</label>
	    <input type="text" name="airlineID" required minlength="2" maxlength="2">
	    <br>
	    <p>Enter the IDs again to confirm</p>
	    <label for="confirmFlightID"><b>Confirm Flight ID</b> (<font color="red">integer > 0</font>):</label>
		<input type="number" step=1 name="confirmFlightID" pattern="\d+" required min=1>
	    <label for="confirmAirlineID"><b>Confirm Airline ID</b> (<font color="red">char 2</font>):</label>
	    <input type="text" name="confirmAirlineID" required minlength="2" maxlength="2">
	    <br>
	    <input type="submit" value="Delete">
	    </form>
</body>
</html>