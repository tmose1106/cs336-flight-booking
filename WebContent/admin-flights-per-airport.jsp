<!-- Sadhana Chidambaran -->

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Demo | Flights per Airport</title>
</head>

<body>

	<fieldset>
		<legend>Departures</legend>
			<table>
				<tr>
					<th>Flight</th>
					<th>Flight Type</th>
					<th>Departure</th>
				</tr>
				<c:forEach var="row" items="${departures}">
					<tr>
							<td align="center">${row.airlineName} ${row.flightNum}</td>
							<td align="center">${row.flightType}</td>
							<td align="center">${row.departure}</td>
					</tr>
				</c:forEach>
			</table><br>
	</fieldset><br>
	
	<fieldset>
		<legend>Arrivals</legend>
			<table>
				<tr>
					<th>Flight</th>
					<th>Flight Type</th>
					<th>Arrival</th>
				</tr>
				<c:forEach var="row" items="${arrivals}">
					<tr>
						<td align="center">${row.airlineName} ${row.flightNum}</td>
						<td align="center">${row.flightType}</td>
						<td align="center">${row.arrival}</td>
					</tr>
				</c:forEach>
			</table><br>
	</fieldset><br>

	<p>
	<input type="button" value="Return to Profile" onclick="window.location='/cs336-flight-booking/admin'">
	</p>

</body>
</html>