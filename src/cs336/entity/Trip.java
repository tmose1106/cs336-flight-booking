package cs336.entity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Trip {
	public String airlineID;
	public Integer flightNum;
	public Integer ticketNum;
	public Integer aircraftID;
	public Integer seatNum;
	public Integer meal;

	public String airlineId, airlineName;
	public Integer flightNumber;
	public String flightType;
	public List<String> daysOfWeek;
	public Date departDatetime, arrivalDatetime;
	public Float fareFirst, fareEconomy;
	
	
	public Trip(ResultSet rs) throws SQLException {
		airlineID = rs.getString("airline_id");
		flightNum = rs.getInt("flight_num");
		ticketNum = rs.getInt("ticket_num");
		aircraftID = rs.getInt("aircraft_id");
		seatNum = rs.getInt("seat_num");
		meal = rs.getInt("meal");
	}
	
	public String getAirlineID() {
		return airlineID;
	}
	
	public Integer getFlightNum() {
		return flightNum;
	}
	
	public Integer getTicketNum() {
		return ticketNum;
	}
	
	public Integer getAircraftID() {
		return aircraftID;
	}
	
	public Integer getSeatNum() {
		return seatNum;
	}
	
	public Integer getMeal() {
		return meal;
	}
}