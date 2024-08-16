package com.tapfoods.servlet;

import java.io.IOException;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tapfoods.DAO.UserDAO;
import com.tapfoods.DAOImpl.UserDAOImpl;
import com.tapfoods.model.User;

/**
 * Servlet implementation class SignIn
 * <p>
 * This servlet handles the user sign-in process. It validates the provided email format, checks if the email and password match 
 * the records in the database, and manages user sessions. Depending on the validation results, it either forwards to an error page 
 * or redirects to the restaurant page.
 * </p>
 */
@WebServlet("/signIn")
public class SignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles POST requests for user sign-in.
	 * <p>
	 * This method retrieves the email and password from the request parameters, validates the email format, and checks the credentials 
	 * against the database. If any validation fails, it forwards to an error page with an appropriate message. If the credentials are 
	 * valid, it stores the user object and email in the session and redirects to the restaurant page.
	 * </p>
	 * 
	 * @param req the HttpServletRequest object containing the request data
	 * @param resp the HttpServletResponse object for sending the response
	 * @throws ServletException if an error occurs during request handling
	 * @throws IOException if an input or output error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		User user = null;
		UserDAO userDao = null;
		try {
			userDao = new UserDAOImpl();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String errorMessage = null;

		/**
		 * Validates the email format using a regular expression.
		 * <p>
		 * If the email format is invalid, an appropriate error message is set.
		 * </p>
		 */
		if (!isValidEmail(email)) {
			errorMessage = "Invalid email format";
		} else if (password == null || password.trim().isEmpty()) {
			errorMessage = "Password is required";
		} else {
			/**
			 * Retrieves the user from the database using the provided email.
			 * <p>
			 * If the user is not found or the provided password does not match the one stored in the database, an appropriate error 
			 * message is set.
			 * </p>
			 */
			try {
				user = userDao.getUser(email);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (user == null) {
				errorMessage = "Email not found";
			} else if (!user.getPassword().equals(password)) {
				errorMessage = "Invalid password";
			}
		}

		if (errorMessage != null) {
			/**
			 * Forwards to the error page with an error message and a link to the sign-in page.
			 * <p>
			 * If there is an error, the request is forwarded to the error page with a message and redirection URL.
			 * </p>
			 */
			req.setAttribute("message", errorMessage);
			req.setAttribute("redirectUrl", "signIn.jsp");
			req.getRequestDispatcher("error.jsp").forward(req, resp);
		} else {
			/**
			 * Stores the user object and email in the session and redirects to the restaurant page.
			 * <p>
			 * If the credentials are valid, the user object is stored in the session, and the response is redirected to the restaurant page.
			 * </p>
			 */
			HttpSession session = req.getSession();
			session.setAttribute("user", user);
			session.setAttribute("userEmail", user.getEmail()); // Store email

			resp.sendRedirect("restaurant.jsp");
		}
	}

	/**
	 * Validates the email format using a regular expression.
	 * <p>
	 * This method checks if the provided email matches a specified regular expression pattern.
	 * </p>
	 * 
	 * @param email the email address to validate
	 * @return {@code true} if the email format is valid, {@code false} otherwise
	 */
	private boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailRegex);
		return email != null && pattern.matcher(email).matches();
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