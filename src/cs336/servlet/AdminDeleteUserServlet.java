// Sadhana Chidambaran

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
 * Servlet implementation class AdminDeleteUserServlet
 */
@WebServlet("/admin-delete-user")
public class AdminDeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminDeleteUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    private static boolean validate(String table, String column, String value) {
    	try (Connection db = DatabaseUtil.getConnection()) {
    		
    		try(PreparedStatement ps = db.prepareStatement("SELECT * FROM " + table + " WHERE " + column + " = ?;")) {
    			ps.setString(1, value);
    			
    			try (ResultSet rs = ps.executeQuery()) {
    				return rs.next();
    			}
    		}
    		
    	} catch (SQLException e) {
    		e.toString();
    	}
    	
    	return false;
    }
    
    private void deleteUser(String user_name) {
    	try (Connection db = DatabaseUtil.getConnection()) {
    		try (PreparedStatement ps = db.prepareStatement("DELETE FROM users WHERE user_name = ?;")) {
    			
    			ps.setString(1, user_name);
    			
    			ps.executeUpdate();
    		}
    	} catch (SQLException ex) {
    		return;
    	}
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (validate("users", "user_name", request.getParameter("customer_rep"))) {
			deleteUser(request.getParameter("customer_rep"));
			
			request.setAttribute("successMessage", "Customer representative has been deleted.");
			
			request.getRequestDispatcher("/admin-delete-user.jsp").forward(request, response);
		}
		else if (validate("users", "user_name", request.getParameter("customer"))) {
			deleteUser(request.getParameter("customer"));
			
			request.setAttribute("successMessage", "Customer has been deleted.");
			
			request.getRequestDispatcher("/admin-delete-user.jsp").forward(request, response);
		}
		else {
			request.setAttribute("errorMessage", "User does not exist.");
			
			request.getRequestDispatcher("/admin-delete-user.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}