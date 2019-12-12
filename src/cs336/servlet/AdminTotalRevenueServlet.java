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

import cs336.entity.AdminRevenue;
import cs336.util.DatabaseUtil;

/**
 * Servlet implementation class AdminRevenue
 */
@WebServlet("/admin-total-revenue")
public class AdminTotalRevenueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminTotalRevenueServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    private List<AdminRevenue> getFlightRevenue(String airline_id, String flight_num) {
    	List<AdminRevenue> list = new ArrayList<AdminRevenue>();

		try (Connection db = DatabaseUtil.getConnection()) {
			try (PreparedStatement ps = db.prepareStatement("SELECT CONCAT(airline_name, \" \", flight_num) AS name, COUNT(*) AS total_tickets, SUM(booking_fee) AS total_revenue FROM tickets NATURAL JOIN trips NATURAL JOIN airlines WHERE airline_id = ? and flight_num = ?;")) {		
				
				ps.setString(1, airline_id);
				ps.setString(2,  flight_num);;
				
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						list.add(new AdminRevenue(rs));
					}
				}
			} 
		} catch (SQLException ex) {
			return null;
		}
				
		return list;
    }
    
    private List<AdminRevenue> getAirlineRevenue(String airline_id) {
    	List<AdminRevenue> list = new ArrayList<AdminRevenue>();

		try (Connection db = DatabaseUtil.getConnection()) {
			try (PreparedStatement ps = db.prepareStatement("SELECT airline_name AS name, COUNT(*) AS total_tickets, SUM(booking_fee) AS total_revenue FROM tickets NATURAL JOIN trips NATURAL JOIN airlines WHERE airline_id = ?;")) {				

				ps.setString(1, airline_id);
				
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						list.add(new AdminRevenue(rs));
					}
				}
			} 
		} catch (SQLException ex) {
			return null;
		}
				
		return list;
    }
    
    private List<AdminRevenue> getCustomerRevenue(String user_name) {
    	List<AdminRevenue> list = new ArrayList<AdminRevenue>();

		try (Connection db = DatabaseUtil.getConnection()) {
			try (PreparedStatement ps = db.prepareStatement("SELECT CONCAT(person_name, \" (\", user_name, \")\") AS name, COUNT(*) AS total_tickets, SUM(booking_fee) AS total_revenue FROM tickets NATURAL JOIN users WHERE user_name = ?;")) {				

				ps.setString(1, user_name);
				
				try (ResultSet rs = ps.executeQuery()) {
					
					while (rs.next()) {
							list.add(new AdminRevenue(rs));
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
		if (request.getParameter("airline_info") == null && request.getParameter("customer_info") == null) {
			
			String flight_info = request.getParameter("flight_info");
			String[] flight_info_split = flight_info.split(" ");
			
			request.setAttribute("revenue_info", getFlightRevenue(flight_info_split[0], flight_info_split[1]));
		}
		else if (request.getParameter("flight_info") == null && request.getParameter("customer_info") == null) {
			request.setAttribute("revenue_info", getAirlineRevenue(request.getParameter("airline_info")));
		}
		else if (request.getParameter("flight_info") == null && request.getParameter("airline_info") == null) {
			request.setAttribute("revenue_info", getCustomerRevenue(request.getParameter("customer_info")));
		}
		
		request.getRequestDispatcher("/admin-total-revenue.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}