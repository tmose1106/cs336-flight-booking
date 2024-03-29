<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Demo | Flights</title>
</head>
<body>
	<header></header>
	<h1>Flights</h1>
	<table>
		<thead>
			<tr>
				<th>Airline</th>
				<th>Type</th>
				<th>Dep. Port</th>
				<th>Departure</th>
				<th>Arr. Port</th>
				<th>Arrival</th>
				<th>Fare Econ.</th>
				<th>Button</th>
			</tr>
		</thead>
		<c:forEach var="flight" items="${flights}">
			<tr>
				<td>${flight.airlineName}</td>
				<td>${flight.flightType}</td>
				<td>${flight.departureId}</td>
				<td><fmt:formatDate type="both" value="${flight.departDatetime}" /></td>
				<td>${flight.destinationId}</td>
				<td><fmt:formatDate type="both" value="${flight.arrivalDatetime}" /></td>
				<td>${flight.fareEconomy}</td>
				<td><a href="/cs336-flight-booking/flight?flight_num=${flight.flightNumber}&airline_id=${flight.airlineId}">View</a>
			</tr>
		</c:forEach>
	</table>
</body>
</html>