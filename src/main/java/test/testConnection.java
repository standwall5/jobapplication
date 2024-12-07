package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class testConnection {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		try {
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		Connection con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\johnp\\Documents\\IM Finals.accdb");
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM region");
			
			while (rs.next()) {
				System.out.println(rs.getString(1) + " " + rs.getString(2));
			}
		
		}catch (Exception e) {
			System.out.println(e);
		}
		}catch (Exception e) {
			System.out.println(e);
		}
	}

}
