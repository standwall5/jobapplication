package sandbox.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sandbox.model.Company;
import sandbox.model.JobPosts;
import sandbox.model.User;

public class SearchDAO {
	private static final String jdbcURL = "jdbc:ucanaccess://C:\\Users\\johnp\\Documents\\IM Finals.accdb";

	protected Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			conn = DriverManager.getConnection(jdbcURL);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public List<User> selectAllUsersSearch(String query) throws SQLException {
		List<User> users = new ArrayList<>();
		String searchQuery = "SELECT * FROM User WHERE firstname LIKE ? OR lastname LIKE ? OR district LIKE ? OR barangay LIKE ? OR bio LIKE ?";

		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(searchQuery)) {

			preparedStatement.setString(1, "%" + query + "%");
			preparedStatement.setString(2, "%" + query + "%");
			preparedStatement.setString(3, "%" + query + "%");
			preparedStatement.setString(4, "%" + query + "%");
			preparedStatement.setString(5, "%" + query + "%");

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String fname = rs.getString("firstname");
				String lname = rs.getString("lastname");
				String district = rs.getString("district");
				String barangay = rs.getString("barangay");
				String bio = rs.getString("bio");
				String icon = rs.getString("icon");

				users.add(new User(id, fname, lname, district, barangay, bio, icon));
			}
		}
		return users;
	}

	public List<Company> selectAllCompaniesSearch(String query) throws SQLException {
		List<Company> companies = new ArrayList<>();
		String searchQuery = "SELECT * FROM Company WHERE companyname LIKE ? OR description LIKE ? OR address LIKE ?";

		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(searchQuery)) {

			preparedStatement.setString(1, "%" + query + "%");
			preparedStatement.setString(2, "%" + query + "%");
			preparedStatement.setString(3, "%" + query + "%");

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("companyname");
				String desc = rs.getString("description");
				String icon = rs.getString("companyimage");
				String address = rs.getString("address");

				companies.add(new Company(id, name, desc, icon, address));
			}
		}
		return companies;
	}

	public List<JobPosts> selectAllJobsSearch(String query) {
		List<JobPosts> jobs = new ArrayList<>();
		String searchQuery = "SELECT * FROM posts WHERE title LIKE ? OR description LIKE ? OR address LIKE ? OR postdate LIKE ?";

		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(searchQuery)) {

			preparedStatement.setString(1, "%" + query + "%");
			preparedStatement.setString(2, "%" + query + "%");
			preparedStatement.setString(3, "%" + query + "%");
			preparedStatement.setString(4, "%" + query + "%");

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				int companyId = rs.getInt("companyid");
				String title = rs.getString("title");
				String desc = rs.getString("description");
				String address = rs.getString("address");
				String category = rs.getString("category");
				String postDate = rs.getString("postDate");

				String companyQuery = "SELECT companyname FROM Company WHERE id = ?";
				try (PreparedStatement companyStatement = conn.prepareStatement(companyQuery)) {
					companyStatement.setInt(1, companyId);
					ResultSet companyResult = companyStatement.executeQuery();

					if (companyResult.next()) {
						String companyName = companyResult.getString("companyname");
						jobs.add(
								new JobPosts(id, companyName, title, desc, address, category, postDate, "placeholder"));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return jobs;
	}
}
