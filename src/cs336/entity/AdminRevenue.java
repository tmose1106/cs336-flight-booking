// Sadhana Chidambaran

package cs336.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRevenue {
	public String name;
	public String totalTickets;
	public Float totalRevenue;
	
	public AdminRevenue(ResultSet rs) throws SQLException {
		name = rs.getString("name");
		totalTickets = rs.getString("total_tickets");	
		totalRevenue = rs.getFloat("total_revenue");
	}

	public String getName() {
		return name;
	}

	public String getTotalTickets() {
		return totalTickets;
	}

	public Float getTotalRevenue() {
		return totalRevenue;
	}
}