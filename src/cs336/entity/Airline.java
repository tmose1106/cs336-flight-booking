package cs336.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Airline {
	public String airlineId, airlineName;
	
	public Airline(ResultSet rs) throws SQLException {
		airlineId = rs.getString("airline_id");
		airlineName = rs.getString("airline_name");
	}

	public String getAirlineId() {
		return airlineId;
	}

	public String getAirlineName() {
		return airlineName;
	}
}
