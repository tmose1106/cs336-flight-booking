package cs336.entity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Ticket {
	public Integer ticketNum;
	public Integer roundTrip;
	public Float bookingFee;
	public Date issueDate;
	public Float totalFare;
	public String purchasedBy;
	
	public Ticket(ResultSet rs) throws SQLException {
		ticketNum = rs.getInt("ticket_num");
		roundTrip = rs.getInt("round_trip");
		bookingFee = rs.getFloat("booking_fee");
		issueDate = rs.getDate("issue_date");
		totalFare = rs.getFloat("totalFare");
		purchasedBy = rs.getString("purchased_by");
	}
	
	public Integer getTicketNum() {
		return ticketNum;
	}
	
	public Integer getRoundTrip() {
		return roundTrip;
	}
	
	public Float getBookingFee() {
		return bookingFee;
	}
	
	public Date getIssueDate() {
		return issueDate;
	}
	
	public Float getTotalFare() {
		return totalFare;
	}
	
	public String getPurchasedBy() {
		return purchasedBy;
	}
}