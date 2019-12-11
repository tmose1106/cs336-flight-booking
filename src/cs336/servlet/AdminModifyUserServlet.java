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
 * Servlet implementation class AdminModifyUserServlet
 */
@WebServlet("/admin-modify-user")
public class AdminModifyUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminModifyUserServlet() {
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
    
    private void editUser(String user_name, String field, String info) {
    	try (Connection db = DatabaseUtil.getConnection()) {
    		try (PreparedStatement ps = db.prepareStatement("UPDATE users SET " + field + " = ? WHERE user_name = ?;")) {
    			ps.setString(1,  info);
    			ps.setString(2,  user_name);
    			
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

		
		if (validate("users", "user_name", request.getParameter("name"))) {
			editUser(request.getParameter("name"), request.getParameter("field"), request.getParameter("new_info"));

			request.setAttribute("successMessage", "User has been updated.");
			
			request.getRequestDispatcher("/admin-modify-user.jsp").forward(request, response);
		}
		else {
			request.setAttribute("errorMessage", "User does not exist.");
			
			request.getRequestDispatcher("/admin-modify-user.jsp").forward(request, response);			
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