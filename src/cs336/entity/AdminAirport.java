// Sadhana Chidambaran

package cs336.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminAirport {
	public String airportID;
	public String airportName;
	public String airportState;
	public String airportCountry;
	
	public AdminAirport(ResultSet rs) throws SQLException {
		airportID = rs.getString("airport_id");
		airportName = rs.getString("airport_city");
		airportState = rs.getString("airport_state");
		airportCountry = rs.getString("airport_country");
	}

	public String getAirportID() {
		return airportID;
	}

	public String getAirportName() {
		return airportName;
	}

	public String getAirportState() {
		return airportState;
	}

	public String getAirportCountry() {
		return airportCountry;
	}
}