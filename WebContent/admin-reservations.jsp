<!-- Sadhana Chidambaran -->

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Demo | Reservations</title>
</head>

<body>

	<fieldset>
		<legend>Reservations</legend>
		<table>
			<tr>
				<th>Customer</th>
				<th>Ticket Number</th>
				<th>Flight</th>
				<th>Seat Number</th>
				<th>Round Trip</th>
				<th>Issue Date</th>
			</tr>
			<c:forEach var="row" items="${reservation_info}">
				<tr>
					<td align="center">${row.personName} (${row.userName})</td>
					<td align="center">${row.ticketNum}</td>
					<td align="center">${row.airlineName} ${row.flightNum}</td>
					<td align="center">${row.seatNum}</td>
					<td align="center">${row.roundTrip}</td>
					<td align="center">${row.issueDate}</td>
				</tr>
			</c:forEach>
		</table><br>
	</fieldset><br>
	
	<p>
		<input type="button" value="Return to Profile" onclick="window.location='/cs336-flight-booking/admin'">
	</p>
</body>
</html>