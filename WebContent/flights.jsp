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
	<div id="filters">
	</div>
	<div id="flights">
		<c:forEach var="flight" items="${flights}">
			<table>
				<tr>
					<th>Airline</th>
					<td>${flight.airlineName}</td>
				</tr>
				<tr>
					<th>Type</th>
					<td>${flight.flightType}</td>
				</tr>
				<tr>
					<th>Departure</th>
					<td><fmt:formatDate type="both" value="${flight.departDatetime}" /></td>
				</tr>
				<tr>
					<th>Arrival</th>
					<td><fmt:formatDate type="both" value="${flight.arrivalDatetime}" /></td>
				</tr>
			</table>
		</c:forEach>
	</div>
</body>
</html>