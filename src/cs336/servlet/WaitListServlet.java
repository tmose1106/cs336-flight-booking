package cs336.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs336.util.DatabaseUtil;

/**
 * Servlet implementation class WaitListServlet
 */
@WebServlet("/WaitListServlet")
public class WaitListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WaitListServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/customerRepProfile.jsp").forward(request, response);
	}
	
	// Determine whether a flight is in flights
	private static boolean validateFlight(String airlineID, String flightID) {
		Connection connection;
		try {
			connection = DatabaseUtil.getConnection();

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
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return false;
	}
	
	// Get list of user names where person doesn't have a seat (is on the waitlist)
	private List<String> getWaitList(String flightID, String airlineID) {
    	List<String> list = new ArrayList<String>();

		try (Connection db = DatabaseUtil.getConnection()) {
			try (PreparedStatement ps = db.prepareStatement(
					
					"SELECT tickets.user_name "
							+ "FROM trips INNER JOIN tickets ON tickets.ticket_num = trips.ticket_num "
							+ "WHERE trips.seat_num IS NULL AND trips.flight_num = ? AND trips.airline_id = ?;")){
				ps.setString(1, flightID);
				ps.setString(2, airlineID);				
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						list.add(rs.getString("user_name"));
					}
				}
			} 
		} catch (SQLException ex) {
			return null;
		}
				
		return list;
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String flightID = request.getParameter("flightID");
		String airlineID = request.getParameter("airlineID");
		if(!validateFlight(airlineID, flightID)) {
			request.setAttribute("errorMessage", "Flight does not exist");
        	
        	request.getRequestDispatcher("/customerRepProfile.jsp").forward(request, response);
		}
		
		request.setAttribute("users", getWaitList(flightID, airlineID));
		request.setAttribute("flightID", flightID);
		request.setAttribute("airlineID", airlineID);
        
		request.getRequestDispatcher("/waitList.jsp").forward(request, response);
		
	}

}
