<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Demo | Flight</title>
</head>
<body>
	<header>
		<a href="/cs336-flight-booking/flights">Back</a>
	</header>
			<table>
				<tr>
					<th>Departure</th>
					<td>${flight.airportID}</a></td>
				</tr>
				<tr>
				<tr>
					<th>Destination</th>
					<td>${flight.airportID}</td>
				</tr>
				<tr>
					<th>Airline</th>
					<td>${flight.airlineName}</td>
				</tr>
				<tr>
					<th>Flight Type</th>
					<td>${flight.flightType}</td>
				</tr>
				<tr>
					<th>Departure Time</th>
					<td>${flight.departDatetime}</td>
				</tr>
				<tr>
					<th>Arrival Time</th>
					<td>${flight.arrivalDatetime}</td>
				</tr>
				<tr>
					<th>Flight Days</th>
					<td>${flight.daysOfWeek}</td>
				</tr>
				<tr>
					<th>Economy Class Price</th>
					<td>$${flight.fareEconomy}</td>
				</tr>
				<tr>
					<th>First Class Price</th>
					<td>$${flight.fareFirst}</td>
				</tr>
			</table>
			
			
	   	<form action="/cs336-flight-booking/flight?flight_num=${flight.flightNumber}&airline_id=${flight.airlineId}" method="post"> 
	   		 <input type="checkbox" checked name="Roundtrip" value = 1> Round Trip
	   		 <input type="checkbox" checked name="Meal" value = 1> Meal
	   		 <p></p>
			<button name="type" type="submit" value="Econ" onclick="return confirm('Are you sure you would like to purchase Economy class? This ticket is NOT refundable!')">Economy Class</button>
			<button name="type" type="submit" value="First" onclick="return confirm('Are you sure you would like to purchase First class? This ticket is refundable.')">First Class</button>
			<button name="type" type="submit" value="Buis" onclick="return confirm('Are you sure you would like to purchase Business class? This ticket is refundable.')">Business Class</button>
		</form>
</body>
</html>