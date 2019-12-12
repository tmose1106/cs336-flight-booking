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

import cs336.entity.AdminAirline;
import cs336.entity.AdminAirport;
import cs336.entity.Flight;
import cs336.entity.AdminMostActiveFlights;
import cs336.entity.User;
import cs336.util.DatabaseUtil;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    private List<User> getUsersNoAdmins() {
    	List<User> list = new ArrayList<User>();

		try (Connection db = DatabaseUtil.getConnection()) {
			try (PreparedStatement ps = db.prepareStatement("SELECT * FROM users WHERE user_type = 'C' OR user_type = 'R';")) {				
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						list.add(new User(rs));
					}
				}
			} 
		} catch (SQLException ex) {
			return null;
		}
				
		return list;
    }

    private List<User> getCustomers() {
    	List<User> list = new ArrayList<User>();

		try (Connection db = DatabaseUtil.getConnection()) {
			try (PreparedStatement ps = db.prepareStatement("SELECT * FROM users WHERE user_type = 'C';")) {				
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						list.add(new User(rs));
					}
				}
			} 
		} catch (SQLException ex) {
			return null;
		}
				
		return list;
    }
    
    private List<User> getCustomerReps() {
    	List<User> list = new ArrayList<User>();

		try (Connection db = DatabaseUtil.getConnection()) {
			try (PreparedStatement ps = db.prepareStatement("SELECT * FROM users WHERE user_type = 'R';")) {				
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						list.add(new User(rs));
					}
				}
			} 
		} catch (SQLException ex) {
			return null;
		}
				
		return list;
    }
    
    private List<Flight> getFlights() {
    	List<Flight> list = new ArrayList<Flight>();

		try (Connection db = DatabaseUtil.getConnection()) {
			try (PreparedStatement ps = db.prepareStatement("SELECT * FROM flights NATURAL JOIN airlines;")) {				
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
    
    private List<AdminAirline> getAirlines() {
    	List<AdminAirline> list = new ArrayList<AdminAirline>();

		try (Connection db = DatabaseUtil.getConnection()) {
			try (PreparedStatement ps = db.prepareStatement("SELECT * FROM airlines;")) {				
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						list.add(new AdminAirline(rs));
					}
				}
			} 
		} catch (SQLException ex) {
			return null;
		}
				
		return list;
    }
    
    private User bestCustomer() {
    	User user;
    	
		try (Connection db = DatabaseUtil.getConnection()) {
			try (PreparedStatement ps = db.prepareStatement("SELECT * FROM users NATURAL JOIN (SELECT user_name, MAX(total_revenue) AS most_revenue FROM (SELECT user_name, SUM(booking_fee) AS total_revenue FROM tickets NATURAL JOIN users GROUP BY user_name) AS t1) AS t2 LIMIT 1;")) {				
				try (ResultSet rs = ps.executeQuery()) {
					rs.next();
					user = new User(rs);
				}
			} 
		} catch (SQLException ex) {
			return null;
		}
				
		return user;
    }
    
    private List<AdminMostActiveFlights> getMostActiveFlights() {
    	List<AdminMostActiveFlights> list = new ArrayList<AdminMostActiveFlights>();

		try (Connection db = DatabaseUtil.getConnection()) {
			try (PreparedStatement ps = db.prepareStatement("SELECT airline_name, airline_id, flight_num, COUNT(*) AS total_tickets FROM tickets NATURAL JOIN trips NATURAL JOIN airlines GROUP BY airline_id, flight_num ORDER BY total_tickets LIMIT 5;")) {				
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						list.add(new AdminMostActiveFlights(rs));
					}
				}
			} 
		} catch (SQLException ex) {
			return null;
		}
				
		return list;
    }
    
    private List<AdminAirport> getAirports() {
    	List<AdminAirport> list = new ArrayList<AdminAirport>();

		try (Connection db = DatabaseUtil.getConnection()) {
			try (PreparedStatement ps = db.prepareStatement("SELECT * FROM airports;")) {				
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						list.add(new AdminAirport(rs));
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
		request.setAttribute("cur_admin", request.getParameter("name"));
		request.setAttribute("users", getUsersNoAdmins());
		request.setAttribute("customers", getCustomers());
		request.setAttribute("customer_reps", getCustomerReps());
		request.setAttribute("flights", getFlights());
		request.setAttribute("airlines", getAirlines());
		request.setAttribute("best_customer", bestCustomer());
		request.setAttribute("active_flights", getMostActiveFlights());
		request.setAttribute("airports", getAirports());
        
		request.getRequestDispatcher("/admin.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}