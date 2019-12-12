<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Demo | Wait List</title>
</head>
<body>
	<table>
		<thead>
			<tr>
				<th>Wait List for Flight ${airlineID}${flightID}<th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="row" items="${users}">
				<tr>
					<td>${row}
			    </tr>
			</c:forEach>
		</tbody>
	</table>
	<br>
	<input type="button" value="Profile" onclick="window.location='/cs336-flight-booking/customerRepProfile.jsp'" >
</body>
</html>