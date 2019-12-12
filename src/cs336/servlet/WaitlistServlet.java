package cs336.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs336.entity.Flight2;
import cs336.entity.Seat;
import cs336.entity.Ticket;
import cs336.util.DatabaseUtil;

/**
 * Servlet implementation class FlightServlet
 */
@WebServlet("/waitlist")
public class WaitlistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WaitlistServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    private Flight2 getFlight(Integer flightnum, String airlineid) {
    	Flight2 flight;

		try (Connection db = DatabaseUtil.getConnection()) {
			try (PreparedStatement ps = db.prepareStatement("SELECT * FROM flights NATURAL JOIN airlines NATURAL JOIN destinations JOIN departures ON (destinations.flight_num = departures.flight_num AND destinations.airline_id = departures.airline_id AND destinations.flight_num = departures.flight_num AND destinations.airline_id=departures.airline_id) WHERE flights.flight_num = ? AND flights.airline_id = ?;")) {
				ps.setInt(1, flightnum);
				ps.setString(2, airlineid);
				
				try (ResultSet rs = ps.executeQuery()) {
					rs.next();
					flight = new Flight2(rs);
					return flight;
				}
			} 
		} catch (SQLException ex) {
			return null;
		}
    }
    
    private Integer getTicket() {
    		Ticket ticket;

		try (Connection db = DatabaseUtil.getConnection()) {
			try (PreparedStatement ps = db.prepareStatement("SELECT * FROM tickets ORDER BY ticket_num DESC LIMIT 1;")) {
				
				
				try (ResultSet rs = ps.executeQuery()) {
					rs.next();
					ticket = new Ticket(rs);
					return ticket.getTicketNum();
				}
			} 
		} catch (SQLException ex) {
			return null;
		}
    }

    private static void add_trip(String airlineid, Integer flightnum, Integer ticketnum, Integer aircraftid, Integer seatnum, Integer meal) {
    	
    	try (Connection connection = DatabaseUtil.getConnection()){
    		try (PreparedStatement ps = connection.prepareStatement("INSERT INTO trips (airline_id, flight_num, ticket_num, aircraft_id, seat_num, meal) values(?, ?, ?, NULL, NULL, NULL);")){

    		ps.setString(1, airlineid);
	    	ps.setInt(2, flightnum);
	    	ps.setInt(3, ticketnum);
	    	
	    	ps.executeUpdate();
	    	
		} }catch (SQLException e) {
			e.printStackTrace();
		}
    
    }
    
private static void add_ticket(Integer roundtrip, Double bookingfee, Double totalfare, String user) {
    	
    	try (Connection connection = DatabaseUtil.getConnection()){
    		try (PreparedStatement ps = connection.prepareStatement("INSERT INTO tickets (round_trip, booking_fee, issue_date, total_fare, user_name, purchased_by) values(NULL, ?, NOW(), ?, ?, ?);")){

	    	ps.setDouble(1, bookingfee);
	    	ps.setDouble(2, totalfare);
	    	ps.setString(3, user);
	    	ps.setString(4, user);
	    	
	    	ps.executeUpdate();
	    	
		} }catch (SQLException e) {
			e.printStackTrace();
		}
    
    }
  
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer flightnum = Integer.parseInt(request.getParameter("flight_num"));
		String airlineid = request.getParameter("airline_id");
		Flight2 flight = getFlight(flightnum , airlineid);
		request.setAttribute("flight", flight);
		request.getRequestDispatcher("/waitlist.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true); 
		String username = (String)session.getAttribute("name");
        Double bookingfee = 23.55;
        
        Double totalfare;
        Integer flightnum = Integer.parseInt(request.getParameter("flight_num"));
        String airlineid = request.getParameter("airline_id");
        String type = request.getParameter("type");
        
        Integer waitlist = Integer.parseInt(request.getParameter("waitlist"));
        Integer roundtrip = 0;
        Integer meal = 0;
        Integer seatnum = null;
        Integer aircraftid = null;
       
        Flight2 flight = getFlight(flightnum , airlineid);
        
        if (type=="Econ")
        	totalfare = bookingfee + flight.getFareEconomy();
        else
        	totalfare = bookingfee + flight.getFareFirst();
       
        if (username==null)
        {
        	response.sendRedirect("/cs336-flight-booking/LoginServlet");
        }
        
        else if (waitlist==1) {        	
        	response.sendRedirect("/cs336-flight-booking/success3.html");
        	
        	add_ticket(roundtrip, bookingfee, totalfare, username);
        	int ticketnum=getTicket();
        	add_trip(airlineid, flightnum, ticketnum, aircraftid, seatnum, meal);

        	// HttpSession session = request.getSession(true);  
        }  
	}
}
