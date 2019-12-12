package cs336.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
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

import cs336.entity.Flight;
import cs336.util.DatabaseUtil;
import cs336.util.QueryUtil;

/**
 * Servlet implementation class FlightsServlet
 */
@WebServlet("/flights")
public class FlightsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FlightsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    private List<Flight> getFlights(String departDate, String departId, String destId) {
    	List<Flight> list = new ArrayList<Flight>();

    	String query = "SELECT flights.*,\n" + 
    			"       airlines.airline_name,\n" + 
    			"       destinations.airport_id AS destination_id,\n" + 
    			"       departures.airport_id AS departure_id\n" + 
    			"FROM flights\n" + 
    			"NATURAL JOIN airlines\n" + 
    			"NATURAL JOIN destinations\n" + 
    			"JOIN departures ON ((departures.airline_id,\n" + 
    			"                     departures.flight_num) = (flights.airline_id,\n" + 
    			"                                               flights.flight_num))\n";
    	
    	List<String> whereClauses = new ArrayList<String>();
    	List<Object> whereValues = new ArrayList<Object>(); 
    	
    	if (departDate != "") {
    		whereClauses.add("DATE(depart) = ?");
    		whereValues.add(Date.valueOf(departDate));
    	}
    	
    	if (departId != "") {
    		whereClauses.add("departures.airport_id = ?");
    		whereValues.add(departId);
    	}
    	
    	if (destId != "") {
    		whereClauses.add("destinations.airport_id = ?");
    		whereValues.add(destId);
    	}
    	
    	query += QueryUtil.getWhereClause(whereClauses);
    	
		try (Connection db = DatabaseUtil.getConnection()) {
			try (PreparedStatement ps = db.prepareStatement(query + ";")) {
				for (int i = 0; i < whereValues.size(); i++) {
					ps.setObject(i + 1, whereValues.get(i));
				}
				
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						list.add(new Flight(rs));
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
		String departString = request.getParameter("departureDate");
		request.setAttribute("flights", getFlights(
				departString, request.getParameter("fromAirport"), request.getParameter("toAirport")));
        
		request.getRequestDispatcher("/flights.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
