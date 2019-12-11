<!-- Sadhana Chidambaran -->

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Demo | Admin Profile</title>
</head>

<body>
	
	<h3>${name}'s Admin Profile</h3>
	
	<p>
	<input type="button" value="Log Out" onclick="window.location='/cs336-flight-booking/logout.jsp'">
	</p>
	
	<fieldset>
		<legend>Modify Users</legend>
		
		<b>Add a New User</b>
		<form action="admin-new-user" method="post">
			Name: <input type="text" name="new_name">
			Username: <input type="text" name="new_username"><br>
			Password: <input type="text" name="new_password">
			Confirm password: <input type="text" name="new_confirm_password"><br>
			<input type="radio" name="new_user_type" value="R" checked>Customer Representative<br>
 		 	<input type="radio" name="new_user_type" value="C">Customer<br>
			<input type="submit" value="Submit">
		</form><br>
		
		<b>Delete a User</b>
		<form action="admin-delete-user" method="post">
			Customer Representative<br>
			<select name="customer_rep">
				<option value=""></option>
				<c:forEach var="row" items="${customer_reps}">
					<option value="${row.userName}">${row.personName} (${row.userName})</option>
				</c:forEach>
    		</select>
    		<input type="submit" value="Submit"><br>

			Customer<br>
			<select name="customer">
				<option value=""></option>
				<c:forEach var="row" items="${customers}">
					<option value="${row.userName}">${row.personName} (${row.userName})</option>
				</c:forEach>
    		</select>
    		<input type="submit" value="Submit"><br>
		</form><br>
		
		<b>Edit a User</b>
		<form action="admin-modify-user" method="post">
			Select User:
			<select name="name">
				<option value=""></option>
				<c:forEach var="row" items="${users}">
					<option value="${row.userName}">${row.personName} (${row.userName})</option>
				</c:forEach>
			</select><br>
			
			Select Field:
			<select name="field">
				<option value=""></option>
				<option value="person_name">Name</option>
				<option value="user_name">Username</option>
				<option value="user_pass">Password</option>
			</select><br>
			
			Enter New Value: <input type="text" name="new_info"><br>
			<input type="submit" value="Submit"><br>
		</form>
	</fieldset><br>
	
	<fieldset>
		<legend>Sales Report</legend>
		
		<form action="admin-sales-report" method="post">
			Month:
			<select name="month">
		    	<option value="01">January</option>
    			<option value="02">February</option>
    			<option value="03">March</option>
    			<option value="04">April</option>
    			<option value="05">May</option>
    			<option value="06">June</option>
   			 	<option value="07">July</option>
   			 	<option value="08">August</option>
    			<option value="09">September</option>
   			 	<option value="10">October</option>
    			<option value="11">November</option>
    			<option value="12">December</option>
  			</select>
  			
  			 Year:
		   	<select name="year">
		    	<option value="2019">2019</option>
		    	<option value="2018">2018</option>
		    	<option value="2017">2017</option>
		    	<option value="2016">2016</option>
		    	<option value="2015">2015</option>
		    	<option value="2014">2014</option>
		    	<option value="2013">2013</option>
		    	<option value="2012">2012</option>
		    	<option value="2011">2011</option>
		    	<option value="2010">2010</option>
		  	</select>
		  	<input type="submit" value="Submit">
		</form>
	</fieldset><br>
	
	<fieldset>
		<legend>Reservations</legend>
		
		<form action="admin-reservations" method="post">
		List by Flight: 
		<select name="flight_info">
			<option value=""></option>
			<c:forEach var="row" items="${flights}">
				<option value="${row.airlineId} ${row.flightNumber}">${row.airlineName} ${row.flightNumber}</option>
			</c:forEach>
		</select>
		<input type="submit" value="Submit">
		</form><br>

		<form action="admin-reservations" method="post">
		List by Customer:
		<select name="customer_info">
			<option value=""></option>
			<c:forEach var="row" items="${customers}">
				<option value="${row.userName}">${row.personName} (${row.userName})</option>
			</c:forEach>
		</select>
		<input type="submit" value="Submit">
		</form>
	</fieldset><br>
	
	<fieldset>
		<legend>Revenue</legend>
		
		<form action="admin-total-revenue" method="post">
		From a Flight: 
			<select name="flight_info">
			<option value=""></option>
			<c:forEach var="row" items="${flights}">
				<option value="${row.airlineId} ${row.flightNumber}">${row.airlineName} ${row.flightNumber}</option>
			</c:forEach>
			</select>
			<input type="submit" value="Submit">
		</form><br>
		
		<form action="admin-total-revenue" method="post">
		From an Airline: 
			<select name="airline_info">
				<option value=""></option>
				<c:forEach var="row" items="${airlines}">
					<option value="${row.airlineID}">${row.airlineName}</option>
				</c:forEach>
			</select>
			<input type="submit" value="Submit">
		</form><br>
		
		<form action="admin-total-revenue" method="post">
		From a Customer: 
			<select name="customer_info">
				<option value=""></option>
				<c:forEach var="row" items="${customers}">
					<option value="${row.userName}">${row.personName} (${row.userName})</option>
				</c:forEach>
			</select>
			<input type="submit" value="Submit">		
		</form>
	</fieldset><br>

	<fieldset>
		<legend>Customer that Generated the Most Revenue</legend>
			${best_customer.personName} (${best_customer.userName})
			<input type="button" value="Refresh" onclick="window.location='/cs336-flight-booking/admin'">
	</fieldset><br>
	
	<fieldset>
		<legend>Most Active Flights</legend>
			<table>
				<tr>
					<th>Flight</th>
					<th>Tickets Sold</th>
				</tr>
				<tr>
					<c:forEach var="row" items="${active_flights}">
						<td align="center">${row.airlineName} ${row.flightNum}</td>
						<td align="center">${row.tickets}</td>
					</c:forEach>
				</tr>
			</table><br>
			<input type="button" value="Refresh" onclick="window.location='/cs336-flight-booking/admin'">
	</fieldset><br>
	
	<fieldset>
		<legend>Flights per Airport</legend>
		
		<form action="admin-flights-per-airport" method="post">
		Select Airport: 
		<select name="airport">
			<option value=""></option>
			<c:forEach var="row" items="${airports}">
				<option value="${row.airportID}">${row.airportID}</option>
			</c:forEach>
		</select>
		<input type="submit" value="Submit">
		</form>
	</fieldset>

</body>
</html>