package pl.nzc;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/changePassword" })
public class ChangePassword extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public ChangePassword() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/changePassword.jsp");
		dispatcher.forward(request, response);
		HttpSession session = request.getSession();
		String userName = DBUtils.getLoginedUser(session);
		if (userName == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		request.setAttribute("userName", userName);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userName = DBUtils.getLoginedUser(session);
		System.out.println(userName);
		String newpassword = request.getParameter("newpassword");
		String repeatpassword = request.getParameter("repeatpassword");
		
		if(newpassword.equals(repeatpassword)) {
			if(newpassword.length()<8) {
				String errorString = "Password must be at least 8 characters long ";
				request.setAttribute("errorString", errorString);
				doGet(request, response);
			}
			else {
				boolean ifPasswordWasUsed = false;
				try {
					ifPasswordWasUsed = DBUtils.ifPasswordWasUsed(userName, newpassword);
					if(ifPasswordWasUsed) {
						String errorString = "The password was used";
						request.setAttribute("errorString", errorString);
						doGet(request, response);
					}
					else {
						DBUtils.updatePassword(userName, newpassword);
						DBUtils.updatePasswordStory(userName, newpassword);
						response.sendRedirect(request.getContextPath() + "/userPage");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		else {
			String errorString = "The passwords are not the same";
			request.setAttribute("errorString", errorString);
			doGet(request, response);
		}
		//doGet(request, response);
	}
}
