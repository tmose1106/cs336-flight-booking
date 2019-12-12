// Sadhana Chidambaran

package cs336.entity;

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminFlightAirports {
	public String airlineName;
	public String airlineID;
	public String flightNum;
	public String flightType;
	public Timestamp departure;
	public Timestamp arrival;
	
	public AdminFlightAirports(ResultSet rs) throws SQLException {
		airlineName = rs.getString("airline_name");
		airlineID = rs.getString("airline_id");
		flightNum = rs.getString("flight_num");
		flightType = rs.getString("flight_type");
		departure = rs.getTimestamp("depart");
		arrival = rs.getTimestamp("arrival");
	}

	public String getAirlineName() {
		return airlineName;
	}	
	
	public String getAirlineID() {
		return airlineID;
	}

	public String getFlightNum() {
		return flightNum;
	}

	public String getFlightType() {
		return flightType;
	}

	public Timestamp getDeparture() {
		return departure;
	}

	public Timestamp getArrival() {
		return arrival;
	}
}