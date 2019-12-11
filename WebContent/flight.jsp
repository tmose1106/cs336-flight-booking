<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Demo | Flight</title>
</head>
<body>
	<table>
		<thead>
			<tr>
				<th>Destination</th>
				<th>Airline</th>
				<th>Arrival Time</th>
				<th>Departure Time</th>
				<th>Flight Type</th>
				<th>Flight Days</th>
				<th>Economy Class Price</th>
				<th>First Class Price</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>${flight.airportID}</td>
				<td>${flight.airlineName}</td>
				<td>${flight.arrivalDatetime}</td>
				<td>${flight.departDatetime}</td>
				<td>${flight.flightType}</td>
				<td>${flight.daysOfWeek}</td>
				<td>${flight.fareFirst}</td>
				<td>${flight.fareEconomy}</td>
			</tr>
		</tbody>
	</table>
</body>
</html>