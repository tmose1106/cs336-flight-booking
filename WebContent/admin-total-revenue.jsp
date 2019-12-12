<!-- Sadhana Chidambaran -->

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Demo | Revenue</title>
</head>

<body>

	<fieldset>
		<legend>Revenue</legend>
			<table>
				<tr>
					<th>Source</th>
					<th>Total Revenue</th>
					<th>Tickets Sold/Purchased</th>
				</tr>
				<c:forEach var="row" items="${revenue_info}">
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