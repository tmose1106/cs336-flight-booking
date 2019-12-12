<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Demo | Customer Representative</title>
</head>
<body>
	<h1>Account</h1>
	<br>
	<p><b>Make or Edit a Reservation for a Customer</b></p>
	<input type="button" value="Make" onclick="window.location='/cs336-flight-booking/makeReservation.jsp'" >
	<br>
	<input type="button" value="Edit" onclick="window.location='/cs336-flight-booking/editReservation.jsp'" >
	<p><b>Retrieve Wait-list for a Flight</b></p>
	<form method="post" action="WaitListServlet">
		<label for="flightID">Flight ID:</label>
	    <input type="number" step=1 name="flightID" pattern="\d+" required min=1>
	    <label for="airlineID">Airline ID:</label>
	    <input type="text" name="airlineID" required minlength="2" maxlength="2">
	    <br>
	    <input type="submit" value="Go">
	</form>
	<br>
	<p><b>Manipulate the Database</b></p>
	<form method="post" action="action.jsp">
		<p>Please select what part of the database you want to change and how you want to change it<p>
		<select name="choice" size=1>
			<option value="Aircraft.jsp">Aircraft</option>
			<option value="Airport.jsp">Airport</option>
			<option value="Flight.jsp">Flight</option>
		</select>&nbsp;<br> <input type="radio" name="command" value="add" checked/> Add
		<input type="radio" name="command" value="edit"/> Edit
		<input type="radio" name="command" value="delete"/> Delete
		<br>
		<input type="submit" value="Go">
	</form>
	<br>
	<br>
	<input type="button" value="Log Out" onclick="window.location='/cs336-flight-booking/logout.jsp'" >
</body>
</html>