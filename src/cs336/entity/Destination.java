package cs336.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Destination {
	public String airlineId;
	public Integer flightNumber;
	public String airportID;
	
	public Destination(ResultSet rs) throws SQLException {
		airlineId = rs.getString("airline_id");
		flightNumber = rs.getInt("flight_num");
		airportID = rs.getString("airport_id");
	}

	public String getAirlineId() {
		return airlineId;
	}

	public Integer getFlightNumber() {
		return flightNumber;
	}
	
	public String getAirportID() {
		return airportID;
	}
}
