package com.tapfoods.adminservlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tapfoods.DAO.AdminDAO;
import com.tapfoods.DAOImpl.AdminDAOImpl;
import com.tapfoods.model.Admin;

/**
 * Servlet for handling updates to the admin profile.
 * <p>
 * This servlet processes POST requests to update the admin's profile information, including the password.
 * It ensures that the admin is logged in, validates the new password, updates the admin details in the database, 
 * and provides appropriate feedback.
 * </p>
 * 
 * @see HttpServlet
 */
@WebServlet("/admin/updateAdminProfile")
public class AdminUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Regex pattern for validating the password
	private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

	/**
	 * Handles HTTP POST requests for updating the admin profile.
	 * <p>
	 * This method retrieves the admin details from the session, validates and updates the password if provided, 
	 * and attempts to update the admin profile in the database. It forwards the request to an appropriate JSP page 
	 * based on the outcome of the update operation.
	 * </p>
	 * 
	 * @param req  The {@link HttpServletRequest} object that contains the request from the client.
	 * @param resp The {@link HttpServletResponse} object used to send a response to the client.
	 * @throws ServletException If the request cannot be handled.
	 * @throws IOException      If an I/O error occurs while handling the request or response.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Admin sessionAdmin = (Admin) session.getAttribute("admin");

		/**
		 * Checks if the admin is logged in by verifying the presence of an admin object in the session. 
		 * If no admin is found, it sets an error message and redirects to the sign-in page.
		 * <p>
		 * If the admin is not found in the session, it forwards to the error page with a message prompting 
		 * the admin to log in again.
		 * </p>
		 */
		if (sessionAdmin == null) {
			req.setAttribute("message", "Admin not found. Please log in again.");
			req.setAttribute("redirectUrl", "adminSignIn.jsp");
			req.getRequestDispatcher("error.jsp").forward(req, resp);
			return;
		}

		String password = req.getParameter("password");

		AdminDAO adminDAO = null;
		try {
			adminDAO = new AdminDAOImpl();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Admin currentAdmin = adminDAO.getAdmin(sessionAdmin.getAdminkey());

		/**
		 * Retrieves the current admin details from the database. If the admin is not found, it sets 
		 * an error message and redirects to the sign-in page.
		 * <p>
		 * If the admin is not found in the database, the request is forwarded to an error page with an appropriate message.
		 * </p>
		 */
		if (currentAdmin == null) {
			req.setAttribute("message", "Admin not found. Please log in again.");
			req.setAttribute("redirectUrl", "adminSignIn.jsp");
			req.getRequestDispatcher("error.jsp").forward(req, resp);
			return;
		}

		/**
		 * Validates and updates the password if a new password is provided. It checks that the password 
		 * is between 8 and 25 characters long.
		 * <p>
		 * If the password is valid, it updates the password in the admin object.
		 * </p>
		 */
		if (password != null && !password.trim().isEmpty()) {
			if (password.length() < 8 || password.length() > 25) {
				req.setAttribute("message", "Password must be between 8 and 25 characters long.");
				req.setAttribute("redirectUrl", "adminRestaurant.jsp");
				req.getRequestDispatcher("error.jsp").forward(req, resp);
				return;
			}

			// Validate password against the pattern
			Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
			Matcher matcher = pattern.matcher(password);
			if (!matcher.matches()) {
				req.setAttribute("message", "Password must include at least one lowercase letter, one uppercase letter, one digit, and one special character (@$!%*?&).");
				req.setAttribute("redirectUrl", "adminRestaurant.jsp");
				req.getRequestDispatcher("error.jsp").forward(req, resp);
				return;
			}
			currentAdmin.setPassword(password);
		}

		int status = adminDAO.updateAdmin(currentAdmin);

		/**
		 * Attempts to update the admin profile in the database. If the update is unsuccessful, it sets 
		 * an error message and redirects to the restaurant page. If successful, it updates the session with 
		 * the new admin details and forwards to a success page.
		 * <p>
		 * Based on the result of the update operation, the user is either redirected to a success page or shown 
		 * an error message.
		 * </p>
		 */
		if (status == 0) {
			req.setAttribute("message", "Admin update failed. Please try again.");
			req.setAttribute("redirectUrl", "adminRestaurant.jsp");
			req.getRequestDispatcher("error.jsp").forward(req, resp);
		} else {
			session.setAttribute("admin", currentAdmin);
			req.setAttribute("message", "Profile updated successfully.");
			req.setAttribute("redirectUrl", "adminRestaurant.jsp");
			req.getRequestDispatcher("success.jsp").forward(req, resp);
		}
	}

	/**
	 * Handles HTTP GET requests by delegating to the POST request handler.
	 * <p>
	 * This method ensures that GET requests are processed in the same way as POST requests by calling the 
	 * {@link #doPost(HttpServletRequest, HttpServletResponse)} method.
	 * </p>
	 * 
	 * @param req  The {@link HttpServletRequest} object that contains the request from the client.
	 * @param resp The {@link HttpServletResponse} object used to send a response to the client.
	 * @throws ServletException If the request cannot be handled.
	 * @throws IOException      If an I/O error occurs while handling the request or response.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}