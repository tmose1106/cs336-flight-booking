<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Demo | Flight</title>
</head>
<body>
	<header>
		<a href="/cs336-flight-booking/flights">Flights</a>
	</header>
	<h1>Sorry, the class you have chosen is full!</h1>
	<p>Click "Join" below to join the waiting list</p>
	   	<form action="/cs336-flight-booking/waitlist?flight_num=${flight.flightNumber}&airline_id=${flight.airlineId}&roundtrip=${ticket.roundtrip}&meal=${trip.meal}" method="post">
			<button name="waitlist" type="submit" value=1>Join</button>
		</form>
		
</body>
</html>