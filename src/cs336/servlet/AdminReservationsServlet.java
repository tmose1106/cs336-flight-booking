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

import cs336.entity.AdminReservation;
import cs336.util.DatabaseUtil;

/**
 * Servlet implementation class AdminReservationsServlet
 */
@WebServlet("/admin-reservations")
public class AdminReservationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminReservationsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    private List<AdminReservation> getFlightReservations(String flight_info) {
    	List<AdminReservation> list = new ArrayList<AdminReservation>();

		try (Connection db = DatabaseUtil.getConnection()) {
			try (PreparedStatement ps = db.prepareStatement("SELECT * FROM trips NATURAL JOIN tickets NATURAL JOIN users NATURAL JOIN airlines WHERE airline_id = ? AND flight_num = ? ORDER BY person_name, ticket_num;")) {

				String[] flight_info_split = flight_info.split(" ");
				
				ps.setString(1, flight_info_split[0]);
				ps.setString(2, flight_info_split[1]);
				
				try (ResultSet rs = ps.executeQuery()) {
					
					while (rs.next()) {
							list.add(new AdminReservation(rs));
					}
				}
			} 
		} catch (SQLException ex) {
			return null;
		}
				
		return list;
    }
    
    private List<AdminReservation> getCustomerReservations(String customer_info) {
    	List<AdminReservation> list = new ArrayList<AdminReservation>();

		try (Connection db = DatabaseUtil.getConnection()) {
			try (PreparedStatement ps = db.prepareStatement("SELECT * FROM trips NATURAL JOIN tickets NATURAL JOIN users NATURAL JOIN airlines WHERE user_name = ? ORDER BY ticket_num, airline_id, flight_num;")) {				

				ps.setString(1, customer_info);
				
				try (ResultSet rs = ps.executeQuery()) {
					
					while (rs.next()) {
							list.add(new AdminReservation(rs));
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
		
		if (request.getParameter("customer_info") == null) {
			request.setAttribute("reservation_info", getFlightReservations(request.getParameter("flight_info")));
		}
		else if (request.getParameter("flight_info") == null) {
			request.setAttribute("reservation_info", getCustomerReservations(request.getParameter("customer_info")));
		}
		
		request.getRequestDispatcher("/admin-reservations.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}