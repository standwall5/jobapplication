package sandbox.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sandbox.model.UserLogin;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/UserLogin")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		HttpSession session = request.getSession();
		session.setAttribute("mode", 0);

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\johnp\\Documents\\IM Finals.accdb");
			String n = request.getParameter("uname");
			String p = request.getParameter("password");

			PreparedStatement preparedStatement = con
					.prepareStatement("select * from User where (email like ?) and password = ?");
			preparedStatement.setString(1, n);
			preparedStatement.setString(2, p);

			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				int id2 = rs.getInt("id");
				int id3 = rs.getInt("companyid");

				PreparedStatement preparedStatement2 = con.prepareStatement("select * from Company where id = ?");
				preparedStatement2.setInt(1, id3);
				ResultSet rs1 = preparedStatement2.executeQuery();

				if (rs1.next()) {
					int verifyNum = rs1.getInt("verify");
					UserLogin.setVerifyNum(verifyNum);
					UserLogin.setCompanyID(id3);
					UserLogin.setId2(id2);
					UserLogin.setIcon(rs.getString("icon"));
					UserLogin user1 = new UserLogin(id2);
					System.out.println(user1);
					System.out.println(UserLogin.getCompanyID());

					session.setAttribute("companyID", id3);
					session.setAttribute("verifyNum", verifyNum);
					session.setAttribute("currentUser", id2);
					session.setAttribute("icon", rs.getString("icon"));
					session.setAttribute("companyIcon", rs1.getString("companyimage"));
					session.setAttribute("isUser", 1);

					response.sendRedirect("joblist");
				}
			} else {
				session.setAttribute("errorMessage", "Wrong username or password. Please try again.");
				response.sendRedirect("login.jsp");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
