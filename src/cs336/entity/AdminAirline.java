// Sadhana Chidambaran

package cs336.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminAirline {
	public String airlineID;
	public String airlineName;
	
	public AdminAirline(ResultSet rs) throws SQLException {
		airlineID = rs.getString("airline_id");
		airlineName = rs.getString("airline_name");	
	}

	public String getAirlineID() {
		return airlineID;
	}

	public String getAirlineName() {
		return airlineName;
	}
}