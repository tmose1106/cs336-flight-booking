// Sadhana Chidambaran

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

import cs336.entity.AdminFlightAirports;
import cs336.util.DatabaseUtil;

/**
 * Servlet implementation class AdminFlightsPerAirport
 */
@WebServlet("/admin-flights-per-airport")
public class AdminFlightsPerAirportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminFlightsPerAirportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    private List<AdminFlightAirports> getDepartures(String airport) {
    	List<AdminFlightAirports> list = new ArrayList<AdminFlightAirports>();

		try (Connection db = DatabaseUtil.getConnection()) {
			try (PreparedStatement ps = db.prepareStatement("SELECT * FROM flights NATURAL JOIN departures NATURAL JOIN airlines WHERE airport_id = ? ORDER BY depart;")) {				
				
				ps.setString(1, airport);
				
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						list.add(new AdminFlightAirports(rs));
					}
				}
			} 
		} catch (SQLException ex) {
			return null;
		}
				
		return list;
    }
    
    private List<AdminFlightAirports> getArrivals(String airport) {
    	List<AdminFlightAirports> list = new ArrayList<AdminFlightAirports>();

		try (Connection db = DatabaseUtil.getConnection()) {
			try (PreparedStatement ps = db.prepareStatement("SELECT * FROM flights NATURAL JOIN destinations NATURAL JOIN airlines WHERE airport_id = ? ORDER BY arrival;")) {				
				
				ps.setString(1, airport);
				
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						list.add(new AdminFlightAirports(rs));
					}
				}
			} 
		} catch (SQLException ex) {
			return null;
		}
				
		return list;
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("departures", getDepartures(request.getParameter("airport")));
		request.setAttribute("arrivals", getArrivals(request.getParameter("airport")));

		request.getRequestDispatcher("/admin-flights-per-airport.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}