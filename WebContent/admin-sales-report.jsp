<!-- Sadhana Chidambaran -->

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Demo | Sales Report</title>
</head>

<body>

	<fieldset>
		<legend>Sales Report</legend>
		<table>
			<tr>
				<th>Customer Representative</th>
				<th>Total Revenue</th>
				<th>Tickets Sold</th>
			</tr>
			<c:forEach var="row" items="${customer_rep_revenue}">
				<tr>
					<td align="center">${row.name}</td>
					<td align="center">$<fmt:formatNumber type="number" minFractionDigits="2" value="${row.totalRevenue}"/></td>
					<td align="center">${row.totalTickets}</td>
				</tr>
			</c:forEach>
		</table><br>
		
		<table>
			<tr>
				<th>Customer</th>
				<th>Total Revenue</th>
				<th>Tickets Purchased</th>
			</tr>
			<c:forEach var="row" items="${customer_revenue}">
				<tr>
					<td align="center">${row.name}</td>
					<td align="center">$<fmt:formatNumber type="number" minFractionDigits="2" value="${row.totalRevenue}"/></td>
					<td align="center">${row.totalTickets}</td>
				</tr>
			</c:forEach>
		</table><br>
	</fieldset><br>
	
	<p>
		<input type="button" value="Return to Profile" onclick="window.location='/cs336-flight-booking/admin'">
	</p>
</body>
</html>