package cs336.servlet;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs336.util.DatabaseUtil;

/**
 * Servlet implementation class EditFlightServlet
 */
@WebServlet("/EditFlightServlet")
public class EditFlightServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditFlightServlet() {
        super();
    }
    
    // Determine whether a flight is in a table
    private static boolean validateFlight(String airlineID, String flightID) {
    	Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	PreparedStatement ps;
		
    	try {
    		// Create a prepared statement for substituting in values
			ps = connection.prepareStatement(
				"SELECT * FROM flights WHERE airline_id = ? AND flight_num = ?;");
			
	    	ps.setString(1, airlineID);
	    	ps.setString(2,  flightID);
	    	
	    	ResultSet rs = ps.executeQuery();
	    	
	    	// next() returns a boolean saying if there are more rows in the set
	    	return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return false;
    }
    
    // Determine whether Airport is already in database
    private static boolean validate(String airportID, String table, String id) {
    	Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	PreparedStatement ps;
		
    	try {
    		// Create a prepared statement for substituting in values
			ps = connection.prepareStatement(
				"SELECT * FROM " + table + " WHERE " + id +" = ?;");
			
	    	ps.setString(1, airportID);
	    	
	    	ResultSet rs = ps.executeQuery();
	    	
	    	// next() returns a boolean saying if there are more rows in the set
	    	return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return false;
    }
    
    private static boolean validateDay(char day, String airlineID, String flightID) {
    	Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	PreparedStatement ps;
		
    	try {
    		// Create a prepared statement for substituting in values
			ps = connection.prepareStatement(
				"SELECT * FROM flights WHERE airline_id = ? AND flight_num = ?;");
			
	    	ps.setString(1, airlineID);
	    	ps.setString(2, flightID);
	    	
	    	ResultSet rs = ps.executeQuery();
	    	rs.next();
	    	String days = rs.getString("flight_days");
	    	
	    	if(days.indexOf(day) != -1) {
	    		return true;
	    	}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return false;
    }

    private static void edit_flight(String originalAirlineID, String originalFlightID, String airlineID, String flightID, String flightType, String flightDays,
    		String departDate, String arrivalDate, String fareFirst, String fareEcon) {
    	Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	try {
    		String query = "UPDATE flights SET";
    		int[] value = new int[8];
    		value[0] = 0;
    		boolean inserted = false;
    		if(!airlineID.equals("")){
    			query += " airline_id = ?";
    			value[0] = 1;
    			inserted = true;
    		}
    		value[1] = 0;
    		if(!flightID.equals("")) {
    			if(inserted) {
    				query += ",";
    			}
    			query += " flight_num = ?";
    			value[1] = 1;
    			inserted = true;
    		}
    		value[2] = 0;
    		if(!flightType.equals("n")) {
    			if(inserted) {
    				query += ",";
    			}
    			query += " flight_type = ?";
    			value[2] = 1;
    			inserted = true;
    		}
    		value[3] = 0;
    		if(!flightDays.equals("")) {
    			if(inserted) {
    				query += ",";
    			}
    			query += " flight_days = ?";
    			value[3] = 1;
    			inserted = true;
    		}
    		value[4] = 0;
    		if(!departDate.equals("")) {
    			if(inserted) {
    				query += ",";
    			}
    			query += " depart = ?";
    			value[4] = 1;
    			inserted = true;
    		}
    		value[5] = 0;
    		if(!arrivalDate.equals("")) {
    			if(inserted) {
    				query += ",";
    			}
    			query += " arrival = ?";
    			value[5] = 1;
    			inserted = true;
    		}
    		value[6] = 0;
    		if(!fareFirst.equals("")) {
    			if(inserted) {
    				query += ",";
    			}
    			query += " fare_first = ?";
    			value[6] = 1;
    			inserted = true;
    		}
    		value[7] = 0;
    		if(!fareEcon.equals("")) {
    			if(inserted) {
    				query += ",";
    			}
    			query += " fare_economy = ?";
    			value[7] = 1;
    			inserted = true;
    		}
    		query += " WHERE airline_id = ? AND flight_num = ?;";
    		PreparedStatement ps = connection.prepareStatement(query);
    		int count = 0;
    		for(int i = 0; i < 8; i++) {
    			String field = null;
    			if(value[i] == 1) {
    				switch (i) {
    				case 0: field = airlineID; break;
    				case 1: field = flightID; break;
    				case 2: field = flightType; break;
    				case 3: field = flightDays; break;
    				case 4: field = departDate; break;
    				case 5: field = arrivalDate; break;
    				case 6: field = fareFirst; break;
    				case 7: field = fareEcon; break;
    				}
    				count++;
    				ps.setString(count, field);
    			}
    		}
    		if(count < 1) {
    			return;
    		}
    		ps.setString(count+1, originalAirlineID);
    		ps.setString(count+2, originalFlightID);
    		ps.executeUpdate();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    // Update the destination or departure location
    private static void edit(String airlineID, String flightID, String airportID, String table, String id) {
    	Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	try {
    		
    		PreparedStatement ps = connection.prepareStatement(
				"UPDATE " + table + " SET " + id + " = ? WHERE airline_id = ? AND flight_num = " + flightID + ";");
    		ps.setString(1, airportID);
	    	ps.setString(2, airlineID);
	    	
	    	ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/editFlight.jsp").forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String originalAirlineID = request.getParameter("originalAirlineID");
		String originalFlightID = request.getParameter("originalFlightID");
		String flightID = request.getParameter("flightID");
		String airlineID = request.getParameter("airlineID");
		String flightType = request.getParameter("flightType");
		String flightDays = "";
		String departDate = request.getParameter("departDate");
		String arrivalDate = request.getParameter("arrivalDate");
		String fareFirst = request.getParameter("fareFirst");
		String fareEcon = request.getParameter("fareEcon");
		
		String departAirport = request.getParameter("departAirport");
		String arrivalAirport = request.getParameter("arrivalAirport");
		
		String aircraftID = request.getParameter("aircraftID");
		
		String days[] = {request.getParameter("mon"), request.getParameter("tue"), request.getParameter("wed"),
				request.getParameter("thur"), request.getParameter("fri"), request.getParameter("sat"),
				request.getParameter("sun")};
		if(!validateFlight(originalAirlineID, originalFlightID)) {
			//If flight doesn't exists, set error message and forward back to form
			request.setAttribute("errorMessage", "Flight doesn't exists.");
			
			request.getRequestDispatcher("/editFlight.jsp").forward(request, response);
		}
		
		for(int i = 0; i < 7; i++) {
			char field = 'N';
			switch (i) {
				case 0: field = 'M'; break;
				case 1: field = 'T'; break;
				case 2: field = 'W'; break;
				case 3: field = 'H'; break;
				case 4: field = 'F'; break;
				case 5: field = 'S'; break;
				case 6: field = 'U'; break;
			}
		if(days[i].equals("n")) {
   			if(validateDay(field, originalAirlineID, originalFlightID)) {
   				flightDays += field;
   			}
		} else if(days[i].equals("a")) {
			if(!validateDay(field, originalAirlineID, originalFlightID)) {
   				flightDays += field;
   			} else {
   				request.setAttribute("errorMessage", "The day" + field + "is already in the flight");
   				
   				request.getRequestDispatcher("/editFlight.jsp").forward(request, response);
   			}
		}
	}
		
	if(!departAirport.contentEquals("")) {
		if(!validate(departAirport, "departures", "airport_id")) {
			//If airport doesn't exist, set error message and forward back to form
			request.setAttribute("errorMessage", "Departing airport doesn't exist.");
			
			request.getRequestDispatcher("/editFlight.jsp").forward(request, response);
		}
		edit(originalAirlineID, originalFlightID, departAirport, "departures", "airport_id");
	}
	if(!arrivalAirport.equals("")) {
		if(!validate(arrivalAirport, "destinations", "airport_id")) {
			//If airport doesn't exist, set error message and forward back to form
			request.setAttribute("errorMessage", "Arrival airport doesn't exist.");
		
			request.getRequestDispatcher("/editFlight.jsp").forward(request, response);
		}
		edit(originalAirlineID, originalFlightID, arrivalAirport, "destinations", "airport_id");
	}
	if(!aircraftID.equals("")) {
		if(!validate(aircraftID, "aircrafts", "aircraft_id")) {
			//If airport doesn't exist, set error message and forward back to form
			request.setAttribute("errorMessage", "Aircraft doesn't exist.");
		
			request.getRequestDispatcher("/editFlight.jsp").forward(request, response);
		}
		edit(originalAirlineID, originalFlightID, aircraftID, "uses", "aircraft_id");
	}
	response.sendRedirect("/cs336-flight-booking/flightEdited.html");
			
	edit_flight(originalAirlineID, originalFlightID, airlineID, flightID, flightType, flightDays, departDate,
			arrivalDate, fareFirst, fareEcon);
			
	}	
}