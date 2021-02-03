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

@WebServlet(urlPatterns = { "/userPage" })
public class UserPage extends HttpServlet{

	public UserPage() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/userPage.jsp");
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
		doGet(request, response);
	}

}
