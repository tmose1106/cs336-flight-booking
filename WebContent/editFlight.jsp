<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Demo | Edit Flight</title>
</head>
<body>
	<header>
		<a href="/cs336-flight-booking/customerRepProfile.jsp">Account</a>
	</header>
	<h1>Edit Flight</h1>
	<div style="color: #FF0000;">${errorMessage}</div>
    <form action="EditFlightServlet" method="post"> 
    	<p>Please enter the ID of the Flight you want to modify</p> 
	    <label for="originalFlightID"><b>Flight ID</b> (<font color="red">Integer > 0</font>):</label>
	    <input type="number" step=1 name="originalFlightID" pattern="\d+" required min=1>
	    <br>
	    <p>Please enter the ID of the airline operating the flight you want to modify</p> 
	    <label for="originalAirlineID"><b>Airline ID</b> (<font color="red">char 2</font>):</label>
	    <input type="text" name="originalAirlineID" required minlength="2" maxlength="2">
	    <br>
	    <p><b><font color="green">Fields left empty will not be changed</font></b></p>
	    <p>Please enter the new ID of the Flight</p> 
	    <label for="flightID"><b>New Flight ID</b> (<font color="blue">Integer</font>):</label>
	    <input type="number" step=1 name="flightID" pattern="\d+" min=1>
	    <br>
	    <p>Please enter the ID of the new airline operating the flight</p> 
	    <label for="airlineID"><b>New Airline ID</b> (<font color="blue">char 2</font>):</label>
	    <input type="text" name="airlineID" minlength="2" maxlength="2">
	    <br>
	    <p>Please enter the new Departure Airport for the flight</p>
    	<label for="departAirport"><b>New Departure Airport</b> (<font color="blue">char 3</font>):</label>
	    <input type="text" name="departAirport" minlength="3" maxlength="3">
	    <br>
	    <p>Please enter the new departure date and time. The time is 24 hour standard</p>
  		<label for="departDate">New Departure Date Time (<font color="blue">YYYY/MM/DD HH:MI</font>): </label>
	    <input type="text" step=1 name="departDate" pattern="\d+{4}/\d+{2}/\d+{2}" minlength="16" maxlength="16">
	    <br>
	    <p>Please enter the new Arrival Airport for the flight</p>
    	<label for="arrivalAirport"><b>New Arrival Airport</b> (<font color="blue">char 3</font>):</label>
	    <input type="text" name="arrivalAirport" minlength="3" maxlength="3">
	   	<p>Please enter the new Arrival date and time. The time is 24 hour standard.</p>
  		<label for="arrivalDate">New Arrival Date Time (<font color="blue">YYYY/MM/DD HH:MI</font>): </label>
	    <input type="text" step=1 name="arrivalDate" pattern="\d+{4}/\d+{2}/\d+{2}" minlength="16" maxlength="16">
	    <br>
	    <p><b>Please indicate if you want to change the flight type</b></p>
	    <input type="radio" name="flightType" value = "n" checked/> No change
	    <input type="radio" name="flightType" value="Domestic"/> Domestic
		<input type="radio" name="flightType" value="Internat"/> International
		<br>
		<p><b>Please indicate how you want to change the flight days.</b></p>
		<p><b>Monday: </b></p>
		<input type="radio" name="mon" value="a">add
		<input type="radio" name="mon" value="d">delete
		<input type="radio" name="mon" value="n" checked>no change
		<br>
		<p><b>Tuesday: </b></p>
  		<input type="radio" name="tue" value="a">add
  		<input type="radio" name="tue" value="d">delete
  		<input type="radio" name="tue" value="n" checked>no change
  		<br>
  		<p><b>Wednesday</b></p>
  		<input type="radio" name="wed" value="a">add
  		<input type="radio" name="wed" value="d">delete
  		<input type="radio" name="wed" value="n" checked>no change
  		<br>
  		<p><b>Thursday</b></p>
  		<input type="radio" name="thur" value="a">add
  		<input type="radio" name="thur" value="d">delete
  		<input type="radio" name="thur" value="n" checked>no change
  		<br>
  		<p><b>Friday</b></p>
  		<input type="radio" name="fri" value="a">add
  		<input type="radio" name="fri" value="d">delete
  		<input type="radio" name="fri" value="n" checked>no change
  		<p><b>Saturday</b></p>
  		<input type="radio" name="sat" value="a">add
  		<input type="radio" name="sat" value="d">delete
  		<input type="radio" name="sat" value="n" checked>no change
  		<p><b>Sunday</b></p>
  		<input type="radio" name="sun" value="a">add
  		<input type="radio" name="sun" value="d">delete
  		<input type="radio" name="sun" value="n" checked>no change
  		<br>
	    <p><b>Please enter the new fare for the flight.</b></p>
	    <label for="fareEcon">Economy Fare:</label>
	    <input type="number" step="any" name="fareEcon" pattern="\d+" min=0>
	    <br>
	    <label for="fareFirst">First Class Fare:</label>
	    <input type="number" step="any" name="fareFirst" pattern="\d+" min=0>
	    <br>
	    <p>Please enter the new aircraft of the flight<p>
	    <label for="aircraftID"><b>Aircraft ID</b> (<font color="blue">Required Integer</font>):</label>
	    <input type="number" step=1 name="aircraftID" pattern="\d+" min=1>
	    <br>
	    <br>
	    <input type="submit" value="Edit Flight">
    </form>
</body>
</html>