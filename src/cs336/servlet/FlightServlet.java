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

import cs336.entity.Flight;
import cs336.util.DatabaseUtil;

/**
 * Servlet implementation class FlightServlet
 */
@WebServlet("/flight")
public class FlightServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FlightServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    private Flight getFlight(Integer flight_num, String airline_id) {
    	Flight flight;

		try (Connection db = DatabaseUtil.getConnection()) {
			try (PreparedStatement ps = db.prepareStatement("SELECT * FROM flights NATURAL JOIN airlines NATURAL JOIN destinations JOIN departures ON (destinations.flight_num = departures.flight_num & destinations.airline_id = departures.airline_id) WHERE (flight_num = ? AND airline_id = ?);")) {
				ps.setInt(1, flight_num);
				ps.setString(2, airline_id);
				
				try (ResultSet rs = ps.executeQuery()) {
					rs.next();
					flight = new Flight(rs);
				}
			} 
		} catch (SQLException ex) {
			return null;
		}
				
		return flight;
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("flight", getFlight(Integer.parseInt(request.getParameter("flight_num")),request.getParameter("airline_id")));
        
		request.getRequestDispatcher("/flight.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
