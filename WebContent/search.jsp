<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>  

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Flights | Search</title>
</head>
<body>
	<h1>Search</h1>
	<form id="filters" action="flights">
		<fieldset>
			<legend>Airline & Airports</legend>
			<label for="airlineId">Airline:</label>
			<select name="airlineId">
				<option value="" selected>All</option>
				<c:forEach var="airline" items="${airlines}">
					<option value="${airline.airlineId}">${airline.airlineName}</option>
				</c:forEach>
			</select>
			<label for="fromAirport">From:</label>
			<input name="fromAirport" type="text">
			<label for="toAirport">To:</label>
			<input name="toAirport" type="text">
		</fieldset>
		<fieldset>
			<legend>Dates and Times</legend>
			<label for="departureDate">Departure:</label>
			<input name="departureDate" type="date">
		</fieldset>
		<fieldset>
			<legend>Price</legend>
			<input name="minPrice" type="number" min="0">
			<span>to</span>
			<input name="maxPrice" type="number" min="0">
		</fieldset>
		<button>Search</button>
	</form>
</body>
</html>