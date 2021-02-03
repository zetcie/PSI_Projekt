package pl.nzc;

import java.io.IOException;
import java.sql.SQLException;
//import java.sql.Date;
import java.sql.Timestamp;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean hasError = false;
		String errorString = null;
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		try {
			boolean ifExists = DBUtils.ifUserExists(userName);
			if (ifExists == false) {
				hasError = true;
				errorString = "Login invalid";
			} else {
				String correctPassword = DBUtils.findUsersPassword(userName);
				if (!password.equals(correctPassword)) {
					hasError = true;
					int noOfIncorrectPasswords = DBUtils.findUsersNoOfIncorrectPasswords(userName);
					errorString = "Password invalid, number of login attempts: " + (noOfIncorrectPasswords+1);
					DBUtils.updateNoOfIncorrectPasswords(userName, noOfIncorrectPasswords + 1);
					noOfIncorrectPasswords = DBUtils.findUsersNoOfIncorrectPasswords(userName);
					if (noOfIncorrectPasswords > 2) {
						DBUtils.updateAvailability(userName, 1);
						long millis = System.currentTimeMillis();
						Timestamp date = new Timestamp(millis);
						DBUtils.updateDisableToDate(userName, date);
						millis = System.currentTimeMillis()+(3*24*60*60*1000);
						date = new Timestamp(millis);
						DBUtils.updateLoginStory(userName, "blocked", date);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int availability;
		try {
			availability = DBUtils.findUsersAvailability(userName);
			String[] data = DBUtils.findUsersData(userName);
			String disableTo = data[4];
			long now = System.currentTimeMillis();
			Timestamp today = new Timestamp(now);
			Timestamp disableToDate = DBUtils.findUsersDisableToDate(userName);
			if (availability == 1) {
				if(today.before(disableToDate)) {
					errorString = "Account is not available to " + disableTo;
					hasError = true;
				}
				else {
					DBUtils.updateAvailability(userName, 0);
					DBUtils.updateDisableToDate(userName, null);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (hasError) {
			request.setAttribute("errorString", errorString);

			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
		} else {
			try {
				DBUtils.updateNoOfIncorrectPasswords(userName, 0);
				DBUtils.updateLoginStory(userName, "success", null);
				long millis = System.currentTimeMillis();
				Timestamp date = new Timestamp(millis);
				Timestamp dateValid = DBUtils.datePasswordValid(userName);
				HttpSession session = request.getSession();
				DBUtils.storeLoginedUser(session, userName);
				if(date.before(dateValid)) {
					response.sendRedirect(request.getContextPath() + "/userPage");
				}
				else {
					response.sendRedirect(request.getContextPath() + "/changePassword");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
