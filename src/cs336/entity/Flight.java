package cs336.entity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Flight {
	public String airlineId, airlineName;
	public Integer flightNumber;
	public String flightType;
	public List<String> daysOfWeek;
	public Date departDatetime, arrivalDatetime;
	public Float fareFirst, fareEconomy;
	
	public Flight(ResultSet rs) throws SQLException {
		airlineId = rs.getString("airline_id");
		airlineName = rs.getString("airline_name");
		flightNumber = rs.getInt("flight_num");
		flightType = rs.getString("flight_type");
		daysOfWeek = parseDays(rs.getString("flight_days"));
		arrivalDatetime = rs.getDate("arrival");
		departDatetime = rs.getDate("depart");
		fareFirst = rs.getFloat("fare_first");
		fareEconomy = rs.getFloat("fare_economy");
	}

	public String getAirlineId() {
		return airlineId;
	}

	public Integer getFlightNumber() {
		return flightNumber;
	}

	private List<String> parseDays(String days) {
		List<String> longDays = new ArrayList<String>();
		
		for (char c : days.toCharArray()) {
			switch (c) {
			case 'M':
				longDays.add("Monday");
				break;
			case 'T':
				longDays.add("Tuesday");
				break;
			case 'W':
				longDays.add("Wednesday");
				break;
			case 'H':
				longDays.add("Thursday");
				break;
			case 'F':
				longDays.add("Friday");
				break;
			case 'S':
				longDays.add("Saturday");
				break;
			case 'U':
				longDays.add("Sunday");
				break;
			default:
				break;
			}
		}
		
		return longDays;
	}
	
	public String getAirlineName() {
		return airlineName;
	}

	public String getFlightType() {
		return flightType;
	}

	public List<String> getDaysOfWeek() {
		return daysOfWeek;
	}

	public Date getDepartDatetime() {
		return departDatetime;
	}

	public Date getArrivalDatetime() {
		return arrivalDatetime;
	}

	public Float getFareFirst() {
		return fareFirst;
	}

	public Float getFareEconomy() {
		return fareEconomy;
	}
}
