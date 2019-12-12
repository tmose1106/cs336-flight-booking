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
@WebServlet("/flight")
public class FlightServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FlightServlet() {
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
    
    private static Seat getSeat(Integer flightnum, String airlineid, String type) {
    	Seat seat;
    	
		try (Connection db = DatabaseUtil.getConnection()) {
			try (PreparedStatement ps = db.prepareStatement("SELECT * FROM flights NATURAL JOIN uses NATURAL JOIN seats WHERE flight_num = ? AND airline_id = ? AND class = ? AND seat_num NOT IN (SELECT seat_num FROM flights NATURAL JOIN trips NATURAL JOIN uses NATURAL JOIN seats WHERE flight_num = ? AND airline_id = ? AND class = ?) LIMIT 1;")) {
				ps.setInt(1, flightnum);
				ps.setString(2, airlineid);
				ps.setString(3, type);
				ps.setInt(4, flightnum);
				ps.setString(5, airlineid);
				ps.setString(6, type);
				
				try (ResultSet rs = ps.executeQuery()) {
					rs.next();
					seat = new Seat(rs);
					return seat;
				}
			} 
		} catch (SQLException ex) {
			return null;
		}
    }
    
    private static void add_trip(String airlineid, Integer flightnum, Integer ticketnum, Integer aircraftid, Integer seatnum, Integer meal) {
    	
    	try (Connection connection = DatabaseUtil.getConnection()){
    		try (PreparedStatement ps = connection.prepareStatement("INSERT INTO trips (airline_id, flight_num, ticket_num, aircraft_id, seat_num, meal) values(?, ?, ?, ?, ?, ?);")){

    		ps.setString(1, airlineid);
	    	ps.setInt(2, flightnum);
	    	ps.setInt(3, ticketnum);
	    	ps.setInt(4, aircraftid);
	    	ps.setInt(5, seatnum);
	    	ps.setInt(6, meal);
	    	
	    	ps.executeUpdate();
	    	
		} }catch (SQLException e) {
			e.printStackTrace();
		}
    
    }
    
private static void add_ticket(Integer roundtrip, Double bookingfee, Double totalfare, String user) {
    	
    	try (Connection connection = DatabaseUtil.getConnection()){
    		try (PreparedStatement ps = connection.prepareStatement("INSERT INTO tickets (round_trip, booking_fee, issue_date, total_fare, user_name, purchased_by) values(?, ?, NOW(), ?, ?, ?);")){

	    	ps.setInt(1, roundtrip);
	    	ps.setDouble(2, bookingfee);
	    	ps.setDouble(3, totalfare);
	    	ps.setString(4, user);
	    	ps.setString(5, user);
	    	
	    	ps.executeUpdate();
	    	int hello = 1;
	    	
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
		request.getRequestDispatcher("/flight.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true); 
		String username = (String)session.getAttribute("name");
        Double bookingfee = 23.55;
        Integer roundtrip = Integer.parseInt(request.getParameter("Roundtrip")); // Bool val from user input?

        Integer meal = Integer.parseInt(request.getParameter("Meal")); // Bool val from user input?
        
        Double totalfare;
        Integer flightnum = Integer.parseInt(request.getParameter("flight_num"));
        String airlineid = request.getParameter("airline_id");
        String type = request.getParameter("type");
        Seat seat = getSeat(flightnum, airlineid, type);
        
        if(seat==null) {
        	// If no seats, send to waitlist
        	response.sendRedirect("/cs336-flight-booking/waitlist?flight_num="+flightnum+"&airline_id="+airlineid);
        	return;
        }
        	Integer seatnum = seat.getSeatNum();
        	Integer aircraftid = seat.getAircraftID();
       
        Flight2 flight = getFlight(flightnum , airlineid);
    	
        if(roundtrip != 1)
        	roundtrip = 0;
        
        if(meal != 1)
        	meal = 0;
        
        if (type=="Econ")
        	totalfare = bookingfee + flight.getFareEconomy();
        else
        	totalfare = bookingfee + flight.getFareFirst();
       
        if (username==null)
        {
        	response.sendRedirect("/cs336-flight-booking/LoginServlet");
        }
        
        else {        	
        	response.sendRedirect("/cs336-flight-booking/success2.html");
        	
        	add_ticket(roundtrip, bookingfee, totalfare, username);
        	int ticketnum=getTicket();
        	add_trip(airlineid, flightnum, ticketnum, aircraftid, seatnum, meal);

        	// HttpSession session = request.getSession(true);  
        }  
	}
}
