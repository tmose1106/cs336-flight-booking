<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<header>
		<a href="/cs336-flight-booking/customerRepProfile.jsp">Account</a>
	</header>
	<h1>Make Reservation</h1>
	<div style="color: #FF0000;">${errorMessage}</div>
    <form action="makeReservationServlet" method="post">
    	<p>Please verify your customer rep redentials</p>
    	<label for="purchaser">Username:</label>
	    <input type="text" name="purchaser" required maxlength="20">
	    <br>
	    <label for="password">Password:</label>
	    <input type="password" name="password" required maxlength="20">
	    <br>  
    	<p>Please enter the user name of the customer you want to make the reservation for</p>
    	<label for="name"><b>User name:</b></label>
	    <input type="text" name="name" required maxlength="20">
	    <br> 
    	<p>Please enter the ID of the airline operating the first flight.
    	The first flight is the location the customer is STARTING their trip</p> 
	    <label for="airlineID"><b>Airline ID</b> (<font color="red">char 2</font>):</label>
	    <input type="text" name="airlineID" required minlength="2" maxlength="2">
	    <br>
	    <p>Please enter the ID of the first flight</p> 
	    <label for="flightID"><b>Flight ID</b> (<font color="red">Integer > 0</font>):</label>
	    <input type="number" step=1 name="flightID" pattern="\d+" required min=1>
	    <br>
	    <p>Please enter whether the customer is flying first class or economy fare.
	    <br>Business class is a type of first class</p>
	    <input type="radio" name="fare" value="first"/> First
		<input type="radio" name="fare" value="economy" checked/> Economy 
		<br>
		<p>Please select seat type.</p>
		<input type="radio" name="seatType" value="First"/> First
		<input type="radio" name="seatType" value="Buis"/> Business
		<input type="radio" name="seatType" value="Econ" checked/> Economy 
		<br>
		<p>Is it acceptable to be put on the wait list?</p>
		<input type="checkbox" name="waitlist" value="y"/> Wait List Acceptable
		<p>Please select whether a customer wants a meal on the flight</p>
		<input type="checkbox" name="meal" value="1"/> Meal 
		<br>
		<input type="submit" value="Go">
    </form>
</body>
</html>