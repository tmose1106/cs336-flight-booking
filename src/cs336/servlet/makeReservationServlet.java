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

/**
 * Servlet implementation class makeReservationServlet
 */

@WebServlet("/makeReservationServlet")
public class makeReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static Connection connection = DatabaseUtil.getConnection();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public makeReservationServlet() {
        super();
    }
    
    private static void generateTicket(String roundTrip, String bookingFee, String totalFare, String name, String purchaser) {
    		try(
    				PreparedStatement ps = connection.prepareStatement(
    									"INSERT INTO tickets (round_trip, booking_fee, issue_date, total_fare, user_name, purchased_by) VALUES "
    									+ "(?, ?, NOW(), ?, ?, ?); ")){

    				ps.setString(1, roundTrip);
    				ps.setString(2, bookingFee);
    				ps.setString(3, totalFare);
    				ps.setString(4, name);
    				ps.setString(5, purchaser);

    				ps.executeUpdate();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    private static double getFromFlights(String fare, String flightID, String airlineID) {
    		try(
    			// Create a prepared statement for substituting in values
    			PreparedStatement ps = connection.prepareStatement(
    					"SELECT " + fare + " FROM flights WHERE flight_num = " + flightID + " AND airline_id = ?;")){

    			ps.setString(1, airlineID);

    			try(ResultSet rs = ps.executeQuery()){
    				rs.next();
    				return rs.getDouble(fare);
    			}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}

    	return -1;
    }
    
    private static int getFromUses(String aircraftID, String flightID, String airlineID) {
    		try(
    			// Create a prepared statement for substituting in values
    			PreparedStatement ps = connection.prepareStatement(
    					"SELECT " + aircraftID +" FROM uses WHERE flight_num = ? AND airline_id = ?;")){
    			
    			ps.setString(1, flightID);
    			ps.setString(2, airlineID);

    			try(ResultSet rs = ps.executeQuery()){
    				rs.next();
    				return rs.getInt(aircraftID);
    			}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}

    	return -1;
    }
    
    private static int getAvailableSeat(String flightID, String aircraftID, String airlineID, String seatType) {
    		try(
    			// Create a prepared statement for substituting in values
    			PreparedStatement ps = connection.prepareStatement(
    					"SELECT seat_num FROM seats " + 
    					"WHERE aircraft_id = ? AND class = ? AND seat_num NOT IN ( " + 
    					"SELECT seat_num FROM trips " + 
    					"		WHERE flight_num = ? AND airline_id = ?);")){

    			ps.setString(1, aircraftID);
    			ps.setString(2, seatType);
    			ps.setString(3, flightID);
    			ps.setString(4, airlineID);

    			try(ResultSet rs = ps.executeQuery()){
    				if(rs.next()){
    				return rs.getInt("seat_num");
    				}
    				return 0;
    			}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}

    	return -1;
    }
    
    // Determine whether a flight is in flights
    private static boolean validateFlight(String airlineID, String flightID) {
    	
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
    
    private static boolean validate(String username, String password) {
    	
    	PreparedStatement ps;
		
    	try {
    		// Create a prepared statement, for substituting in values
			ps = connection.prepareStatement(
				"SELECT * FROM users WHERE user_name = ? AND user_pass = ?;");
			
	    	ps.setString(1, username);
	    	ps.setString(2, password);
	    	
	    	ResultSet rs = ps.executeQuery();
	    	
	    	// next() returns a boolean saying if there are more rows in the set
	    	return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return false;
    }
    
    private static String getPrivilegeType(String username) {
    	
    	PreparedStatement ps;
    	
    	try {
    		ps = connection.prepareStatement("SELECT * FROM users WHERE user_name = ?;");
    		
    		ps.setString(1, username);
    		
    		ResultSet rs = ps.executeQuery();
    		
    		//Get the privilege
    		if (rs.next()) {
    			String access = rs.getString("user_type");
    			return access;
    		}
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	// If privilege column is NULL return lowest level privilege;
    	return "C";
	}
    
    private static void insertTrip(String airlineID, String flightID, String ticketNum, String aircraftID, 
    		String seatNum, String meal) {
    	
    	try {
    		
    		PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO trips (airline_id, flight_num, ticket_num, aircraft_id, seat_num, meal)"
				+ " VALUES (?, ?, ?, ?, " + seatNum + ", " + meal + ");");
	    	ps.setString(1, airlineID);
	    	ps.setString(2, flightID);
	    	ps.setString(3, ticketNum);
	    	ps.setString(4, aircraftID);
	    	
	    	ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    private static int getTicketNum() {
    	try {
    		
    		PreparedStatement ps = connection.prepareStatement(
    				"SELECT ticket_num FROM tickets ORDER BY ticket_num DESC LIMIT 1;");
    		ResultSet rs = ps.executeQuery();
    		if(rs.next()) {
    			return rs.getInt("ticket_num");
    		}
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	return(-1);
	}
	
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/makeReservation.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String flightID = request.getParameter("flightID");
		String airlineID = request.getParameter("airlineID");
		String totalFare = request.getParameter("fare");
		String seatType = request.getParameter("seatType");
		String meal = request.getParameter("meal");
		String waitlist = request.getParameter("waitlist");
		String purchaser = request.getParameter("purchaser");
		String password = request.getParameter("password");
		
		if(totalFare.equals("economy") && (!seatType.equals("Econ"))){
			request.setAttribute("errorMessage", "Business and First Class seats require first class fare");
			request.getRequestDispatcher("/makeReservation.jsp").forward(request, response);
		}
		if (validate(purchaser, password)) {
			if(getPrivilegeType(purchaser).equals("C")) {
				if(!purchaser.contentEquals(name)) {
					request.setAttribute("errorMessage", "Customers cannot make reservations for other customers");

					request.getRequestDispatcher("/login.jsp").forward(request, response);
				}
			}
			if(!validateFlight(airlineID, flightID)) {
				request.setAttribute("errorMessage", "Requested flight does not exist.");

				request.getRequestDispatcher("/makeReservation.jsp").forward(request, response);
			}
			String roundTrip = "0";
			double fare = getFromFlights("fare_" + totalFare, flightID, airlineID);
			String bookingFee = Double.toString((Math.round((0.05*fare)*100))/100);
			totalFare = Double.toString(fare);
			String aircraftID = Integer.toString(getFromUses("aircraft_id", flightID, airlineID));
			String seatNum = Integer.toString(getAvailableSeat(flightID, aircraftID, airlineID, seatType));
			if(seatNum.equals("-1")) {
				request.setAttribute("errorMessage", "A seat could not be found");

				request.getRequestDispatcher("/makeReservation.jsp").forward(request, response);
			} else if(seatNum.equals("0")) {
				if(waitlist == null) {
					request.setAttribute("errorMessage", "There are no seats of the requested type available");

					request.getRequestDispatcher("/makeReservation.jsp").forward(request, response);
				}
				seatNum = "NULL";
			}
			if(meal == null) {
				meal = "0";
			}
			generateTicket(roundTrip, bookingFee, totalFare, name, purchaser);
			String ticketNum = Integer.toString(getTicketNum());
			if(!ticketNum.equals("-1")) {
				request.setAttribute("ticket", ticketNum);

				request.getRequestDispatcher("/reservationMade.html").forward(request, response);
				
				insertTrip(airlineID, flightID, ticketNum, aircraftID, seatNum, meal);
			} else {
				request.setAttribute("errorMessage", "Ticket could not be added");

				request.getRequestDispatcher("/makeReservation.jsp").forward(request, response);
			}
		} else {
			request.setAttribute("errorMessage", "Credentials Invalid");

			request.getRequestDispatcher("/makeReservation.jsp").forward(request, response);
		}
	}

}
