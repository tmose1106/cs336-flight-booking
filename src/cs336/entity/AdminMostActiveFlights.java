// Sadhana Chidambaran

package cs336.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminMostActiveFlights {
	public String airlineID;
	public String airlineName;
	public String flightNum;
	public String tickets;
	
	public AdminMostActiveFlights(ResultSet rs) throws SQLException {
		airlineID = rs.getString("airline_id");
		airlineName = rs.getString("airline_name");	
		flightNum = rs.getString("flight_num");
		tickets = rs.getString("total_tickets");
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

	public String getTickets() {
		return tickets;
	}
}