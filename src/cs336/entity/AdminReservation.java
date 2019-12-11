// Sadhana Chidambaran

package cs336.entity;

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminReservation {
	public String userName;
	public String personName;
	public String ticketNum;
	public String airlineID;
	public String airlineName;
	public String flightNum;
	public String seatNum;
	public String roundTrip;
	public Timestamp issueDate;
	
	public AdminReservation(ResultSet rs) throws SQLException {
		userName = rs.getString("user_name");
		personName = rs.getString("person_name");
		ticketNum = rs.getString("ticket_num");
		airlineName = rs.getString("airline_name");
		airlineID = rs.getString("airline_id");
		flightNum = rs.getString("flight_num");
		seatNum = rs.getString("seat_num");
		roundTrip = rs.getString("round_trip");
		issueDate = rs.getTimestamp("issue_date");		
	}

	public String getUserName() {
		return userName;
	}

	public String getPersonName() {
		return personName;
	}
	
	public String getTicketNum() {
		return ticketNum;
	}

	public String getAirlineID() {
		return airlineID;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public String getFlightNum() {
		return flightNum;
	}

	public String getSeatNum() {
		return seatNum;
	}

	public String getRoundTrip() {
		return roundTrip;
	}

	public Timestamp getIssueDate() {
		return issueDate;
	}
}