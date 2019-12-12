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
 * Servlet implementation class AdminNewUserServlet
 */
@WebServlet("/admin-new-user")
public class AdminNewUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminNewUserServlet() {
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
    
    private void addUser(String user_name, String user_pass, String person_name, String user_type) {
    	try (Connection db = DatabaseUtil.getConnection()) {
    		try (PreparedStatement ps = db.prepareStatement("INSERT INTO users(user_name, user_pass, person_name, user_type) values(?, ?, ?, ?);")) {
    			
    			ps.setString(1, user_name);
    			ps.setString(2, user_pass);
    			ps.setString(3, person_name);
    			ps.setString(4, user_type);
    			
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

		if (validate("users", "user_name", request.getParameter("new_username"))) {
			request.setAttribute("errorMessage", "Username is taken.");
			
			request.getRequestDispatcher("/admin-new-user.jsp").forward(request, response);
		}
		else if ((request.getParameter("new_username") != null && !request.getParameter("new_username").isEmpty()) && (request.getParameter("new_password") != null && !request.getParameter("new_password").isEmpty()) && (request.getParameter("new_password").equals(request.getParameter("new_confirm_password")))) {
			addUser(request.getParameter("new_username"), request.getParameter("new_password"), request.getParameter("new_name"), request.getParameter("new_user_type"));

			if (request.getParameter("new_user_type").equals("R")) {
				request.setAttribute("successMessage", "Customer representative has been added!");
				
				request.getRequestDispatcher("/admin-new-user.jsp").forward(request, response);
			}
			else if (request.getParameter("new_user_type").equals("C")) {
				request.setAttribute("successMessage", "Customer has been added!");
				
				request.getRequestDispatcher("/admin-new-user.jsp").forward(request, response);
			}
		}
		else if (request.getParameter("new_username") == null || request.getParameter("new_username").isEmpty()) {
			request.setAttribute("errorMessage", "Please enter a username.");
			
			request.getRequestDispatcher("/admin-new-user.jsp").forward(request, response);			
		}
		else if (request.getParameter("new_password") == null || request.getParameter("new_password").isEmpty()) {
			request.setAttribute("errorMessage", "Please enter a password.");
			
			request.getRequestDispatcher("/admin-new-user.jsp").forward(request, response);			
		}
		else if (!request.getParameter("new_password").equals(request.getParameter("new_confirm_password"))) {
			request.setAttribute("errorMessage", "Passwords do not match.");
			
			request.getRequestDispatcher("/admin-new-user.jsp").forward(request, response);					
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