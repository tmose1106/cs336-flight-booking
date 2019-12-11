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

    private List<Flight> getFlights(String departDate) {
    	List<Flight> list = new ArrayList<Flight>();

    	String query = "SELECT * FROM flights NATURAL JOIN airlines";
    	
    	if (departDate != null) {
    		query += " WHERE DATE(depart) = ?";
    	}
    	
		try (Connection db = DatabaseUtil.getConnection()) {
			try (PreparedStatement ps = db.prepareStatement(query + ";")) {
				if (departDate != null) {
					Date depart = Date.valueOf(departDate);
		    		ps.setDate(1, depart);
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
		request.setAttribute("flights", getFlights(departString));
        
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
