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
 * Servlet implementation class AdminSalesReportServlet
 */
@WebServlet("/admin-sales-report")
public class AdminSalesReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminSalesReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private List<AdminRevenue> getCustomerSales(String start_date, String end_date) {
    	List<AdminRevenue> list = new ArrayList<AdminRevenue>();

		try (Connection db = DatabaseUtil.getConnection()) {
			try (PreparedStatement ps = db.prepareStatement("SELECT person_name AS name, SUM(booking_fee) AS total_revenue, COUNT(*) AS total_tickets FROM tickets NATURAL JOIN users WHERE issue_date BETWEEN ? AND ? GROUP BY user_name;")) {

				ps.setString(1, start_date);
				ps.setString(2, end_date);
				
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
    
    private List<AdminRevenue> getCustomerRepSales(String start_date, String end_date) {
    	List<AdminRevenue> list = new ArrayList<AdminRevenue>();

		try (Connection db = DatabaseUtil.getConnection()) {
			try (PreparedStatement ps = db.prepareStatement("SELECT person_name AS name, SUM(booking_fee) AS total_revenue, COUNT(*) AS total_tickets FROM tickets INNER JOIN users ON tickets.purchased_by = users.user_name WHERE issue_date BETWEEN ? AND ? AND user_type = 'R' GROUP BY purchased_by;")) {

				ps.setString(1, start_date);
				ps.setString(2, end_date);
				
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
		
		String month = request.getParameter("month");
		String year = request.getParameter("year");
		
		String start_date = year + "-" + month + "-01";
		String end_date;
		
		if (month.equals("02")) {
			end_date = year + "-" + month + "-29";
		}
		else if (month.equals("04") || month.equals("06") || month.equals("09") || month.equals("11")) {
			end_date = year + "-" + month + "-30";
		}
		else {
			end_date = year + "-" + month + "-31";
		}
		
		request.setAttribute("customer_rep_revenue",  getCustomerRepSales(start_date, end_date));
		request.setAttribute("customer_revenue",  getCustomerSales(start_date, end_date));
		
		request.getRequestDispatcher("/admin-sales-report.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}