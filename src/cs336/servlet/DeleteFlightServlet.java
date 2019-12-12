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
 * Servlet implementation class DeleteFlightServlet
 */
@WebServlet("/DeleteFlightServlet")
public class DeleteFlightServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteFlightServlet() {
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
    
    // Delete flight from flights
    private static void delete_flight(String airlineID, String flightID) {
    	Connection connection = null;
		try {
			connection = DatabaseUtil.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	try {
    		PreparedStatement ps = connection.prepareStatement(
    				"DELETE FROM flights WHERE airline_id = ? AND flight_num = ?;");

	    	ps.setString(1, airlineID);
	    	ps.setString(2, flightID);
	    	
	    	ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/deleteFlight.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String airlineID = request.getParameter("airlineID");
		String confirmAirlineID = request.getParameter("confirmAirlineID");
		String flightID = request.getParameter("flightID");
		String confirmFlightID = request.getParameter("confirmFlightID");
		if(airlineID.equals(confirmAirlineID) && flightID.equals(confirmFlightID)) {
			if(!validateFlight(airlineID, flightID)) {
				//If flight doesn't exists, set error message and forward back to form
				request.setAttribute("errorMessage", "Flight doesn't exists.");

				request.getRequestDispatcher("/deleteFlight.jsp").forward(request, response);
			} else {
				response.sendRedirect("/cs336-flight-booking/flightDeleted.html");

				delete_flight(airlineID, flightID);
			}
		} else {
			//If Flight Code and Confirm Code are not the same, set error message and forward back to form
			request.setAttribute("errorMessage", "Flight Code and Confirmation Code don't match");

			request.getRequestDispatcher("/deleteFlight.jsp").forward(request, response);
		}
	}

}
