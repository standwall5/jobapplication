package sandbox.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sandbox.model.JobPosts;
import sandbox.model.User;
import sandbox.model.UserLogin;

public class PostDAO {
	private static final String jdbcURL = "jdbc:ucanaccess://C:\\\\Users\\\\johnp\\\\Documents\\\\IM Finals.accdb";

	private static final String SELECT_ALL_JOBS = "select * from posts";
	private static final String SELECT_ALL_JOBS_COMPANY = "select * from posts where companyid = ?";
	private static final String SELECT_JOB_BY_ID = "select * from posts where id = ?";
	private static final String DELETE_JOBS_SQL = "delete from posts where id = ?";

	protected Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			conn = DriverManager.getConnection(jdbcURL);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return conn;
	}

	public JobPosts getUserById(int userId) {
		JobPosts post = null;
		ResultSet rs = null;

		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement("select * from User where id = ?");) {

			preparedStatement.setInt(1, userId);

			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("id");
				String fname = rs.getString("firstname");
				String lname = rs.getString("lastname");
				String district = rs.getString("district");
				String barangay = rs.getString("barangay");
				String bio = rs.getString("bio");
				String icon = rs.getString("icon");

				post = new JobPosts(id, fname, lname, district, barangay, bio, icon);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return post;
	}

	public JobPosts getCompanyById(int userId) {
		JobPosts company = null;
		ResultSet rs = null;

		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement("select * from Company where id = ?");) {

			preparedStatement.setInt(1, userId);

			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				String name = rs.getString("companyname");
				String address = rs.getString("address");
				String desc = rs.getString("description");
				String icon = rs.getString("companyImage");

				company = new JobPosts(name, desc, icon, address);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return company;
	}

	public int insertResume(User resume) {
		int rowCount = 0;
		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(
						"INSERT INTO Resume (address, workhist, educhist, skills, resdesc, userID) VALUES"
								+ "(?, ?, ?, ?, ?, ?)");) {
			preparedStatement.setString(1, resume.getAddress());
			preparedStatement.setString(2, resume.getWorkhist());
			preparedStatement.setString(3, resume.getEduchist());
			preparedStatement.setString(4, resume.getSkills());
			preparedStatement.setString(5, resume.getResdesc());
			preparedStatement.setInt(6, UserLogin.getId2());
			rowCount = preparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowCount;
	}

	public boolean updateResume(User resume) {
		boolean rowCount = false;
		String sql = "UPDATE Resume SET address = ?, workhist = ?, educhist = ?, skills = ?, resdesc = ? WHERE userID = ?";

		try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

			preparedStatement.setString(1, resume.getAddress());
			preparedStatement.setString(2, resume.getWorkhist());
			preparedStatement.setString(3, resume.getEduchist());
			preparedStatement.setString(4, resume.getSkills());
			preparedStatement.setString(5, resume.getResdesc());
			preparedStatement.setInt(6, UserLogin.getId2());

			rowCount = preparedStatement.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowCount;
	}

	public JobPosts selectJob(int id) {
		JobPosts jobpost = null;
		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(SELECT_JOB_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int companyid = rs.getInt("companyid");
				String title = rs.getString("title");
				String desc = rs.getString("description");
				String address = rs.getString("address");
				String category = rs.getString("category");
				String postdate = rs.getString("postDate");

				String companyQuery = "SELECT companyname FROM Company WHERE id = ?";
				try (PreparedStatement preparedStatement2 = conn.prepareStatement(companyQuery)) {
					preparedStatement2.setInt(1, companyid);
					ResultSet rs2 = preparedStatement2.executeQuery();

					if (rs2.next()) {
						String company = rs2.getString("companyname");
						jobpost = new JobPosts(id, company, title, desc, address, category, postdate, "placeholder");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobpost;
	}

	public List<JobPosts> selectAllJobs() {
		List<JobPosts> work = new ArrayList<>();
		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_JOBS);) {

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				int companyid = rs.getInt("companyid");
				String title = rs.getString("title");
				String desc = rs.getString("description");
				String address = rs.getString("address");
				String category = rs.getString("category");
				String postdate = rs.getString("postDate");

				String companyQuery = "SELECT companyname FROM Company WHERE id = ?";
				try (PreparedStatement preparedStatement2 = conn.prepareStatement(companyQuery)) {
					preparedStatement2.setInt(1, companyid);
					ResultSet rs2 = preparedStatement2.executeQuery();

					if (rs2.next()) {
						String company = rs2.getString("companyname");
						work.add(new JobPosts(id, company, title, desc, address, category, postdate, "placeholder"));
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return work;
	}

	public List<JobPosts> selectAllJobsCompany() {
		List<JobPosts> work = new ArrayList<>();
		try (Connection conn = getConnection();
				PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_JOBS_COMPANY);) {
			preparedStatement.setInt(1, UserLogin.getCompanyID());

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				int companyid = rs.getInt("companyid");
				String title = rs.getString("title");
				String desc = rs.getString("description");
				String address = rs.getString("address");
				String category = rs.getString("category");
				String postdate = rs.getString("postDate");

				final String companyQuery = "SELECT companyname FROM Company WHERE id = ?";
				try (PreparedStatement preparedStatement2 = conn.prepareStatement(companyQuery)) {
					preparedStatement2.setInt(1, companyid);
					ResultSet rs2 = preparedStatement2.executeQuery();

					if (rs2.next()) {
						String company = rs2.getString("companyname");
						work.add(new JobPosts(id, company, title, desc, address, category, postdate, "placeholder"));
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return work;
	}

	public boolean deleteWork(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection conn = getConnection(); PreparedStatement statement = conn.prepareStatement(DELETE_JOBS_SQL);) {
			statement.setInt(1, id);

			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	public void applyWork(int postId, int userId) throws SQLException {
		try (Connection conn = getConnection();
				PreparedStatement statement = conn
						.prepareStatement("INSERT INTO Applications (postid, userid) VALUES (?, ?)")) {

			statement.setInt(1, postId);
			statement.setInt(2, userId);

			int rowCount = statement.executeUpdate();
			if (rowCount > 0) {
				System.out.println("Application successfully added.");
			} else {
				System.out.println("No rows affected. Application may not have been added.");
			}
		} catch (SQLException e) {
			System.err.println("Error applying work: " + e.getMessage());
			throw e;
		}
	}

	public int checkApply(int postId, int userId) throws SQLException {
		int exists = 0;
		try (Connection conn = getConnection();
				PreparedStatement statement = conn
						.prepareStatement("SELECT * from applications where userid = ? and postid = ?")) {
			statement.setInt(1, userId);
			statement.setInt(2, postId);

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				exists = 1;
			} else {
				exists = 0;
			}
		} catch (SQLException e) {
			System.err.println("Error applying work: " + e.getMessage());
			throw e;
		}
		return exists;
	}

	public void acceptApplicant(int id) throws SQLException {
		try (Connection conn = getConnection();
				PreparedStatement statement = conn.prepareStatement("SELECT * FROM Company where id = ?")) {
			statement.setInt(1, UserLogin.getCompanyID());

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				int empId = rs.getInt("EmpID");
				PreparedStatement statement2 = conn.prepareStatement("UPDATE User set EmployeeID = ? where id = ?");
				statement2.setInt(1, empId);
				statement2.setInt(2, id);

				int rowCount = statement2.executeUpdate();

			}
		}
	}

	public void rejectApplicant(int userId, int workId) throws SQLException {
		try (Connection conn = getConnection();
				PreparedStatement statement = conn
						.prepareStatement("DELETE FROM Applications WHERE Userid = ? AND postid = ?")) {

			statement.setInt(1, userId);
			statement.setInt(2, workId);

			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.println(
						"Applicant with User ID " + userId + " and Work ID " + workId + " rejected successfully.");
			} else {
				System.out.println("No applicant found with User ID " + userId + " and Work ID " + workId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public User selectAllResume(int id) throws SQLException {
		User user = null;
		try (Connection conn = getConnection();
				PreparedStatement statement = conn.prepareStatement("SELECT * FROM resume where userid = ?")) {

			statement.setInt(1, id);

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				String address = rs.getString("address");
				String workhist = rs.getString("workhist");
				String educhist = rs.getString("educhist");
				String skills = rs.getString("skills");
				String desc = rs.getString("resdesc");

				user = new User(address, workhist, educhist, skills, desc);
			} else {
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return user;
	}

	/*
	 * public void applyWork(int id, int id2) throws SQLException { int rowCount =
	 * 0; try(Connection conn = getConnection(); PreparedStatement statement = conn.
	 * prepareStatement("INSERT INTO Applications (postid, userid) VALUES (?, ?)");)
	 * { statement.setInt(1, id); statement.setInt(2, id2);
	 * 
	 * rowCount = statement.executeUpdate(); } }
	 */
}
