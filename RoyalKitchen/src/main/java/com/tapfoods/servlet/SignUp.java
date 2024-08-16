package com.tapfoods.servlet;

import java.io.IOException;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.tapfoods.DAO.UserDAO;
import com.tapfoods.DAOImpl.UserDAOImpl;
import com.tapfoods.model.User;

/**
 * The SignUp servlet handles user registration.
 * <p>
 * It processes HTTP requests for user sign-up, including validation of input fields and
 * interaction with the database to store new user information.
 * </p>
 */
@WebServlet("/signUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Processes HTTP requests for user sign-up.
	 * <p>
	 * This method handles the user registration process by validating input fields, 
	 * checking for existing emails, and adding new user information to the database.
	 * </p>
	 * 
	 * @param req the HttpServletRequest object that contains the request the client made to the servlet
	 * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an input or output error occurs
	 */
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String confirmpassword = req.getParameter("confirmpassword");

		UserDAO userDao = null;
		try {
			userDao = new UserDAOImpl();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String errorMessage = null;

		/**
		 * Validates the email format.
		 * <p>
		 * This block checks if the provided email matches the specified regular expression pattern for valid email addresses.
		 * </p>
		 */
		if (!isValidEmail(email)) {
			errorMessage = "Invalid email format";
		} else
			try {
				if (userDao.emailExists(email)) {
					errorMessage = "Email already exists";
				} else if (password.length() < 8 || password.length() > 25) {
					errorMessage = "Password must be between 8 and 25 characters long";
				} else if (!password.equals(confirmpassword)) {
					errorMessage = "Passwords do not match";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		if (errorMessage != null) {
			/**
			 * Forwards to error.jsp with an error message and a link to the sign-up page.
			 * <p>
			 * If any validation fails, the request is forwarded to error.jsp with the appropriate error message and redirection URL.
			 * </p>
			 */
			req.setAttribute("message", errorMessage);
			req.setAttribute("redirectUrl", "signUp.jsp");
			req.getRequestDispatcher("error.jsp").forward(req, resp);
		} else {
			/**
			 * Creates a new User object and adds it to the database.
			 * <p>
			 * If the user registration is successful, the request is forwarded to success.jsp with a success message and redirection URL.
			 * If the registration fails, it forwards to error.jsp with a failure message.
			 * </p>
			 */
			User user = new User(email, password);
			int status = 0;
			try {
				status = userDao.addUser(user);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (status == 0) {
				req.setAttribute("message", "User registration failed. Please try again.");
				req.setAttribute("redirectUrl", "signUp.jsp");
				req.getRequestDispatcher("error.jsp").forward(req, resp);
			} else {
				req.setAttribute("message", "User registered successfully.");
				req.setAttribute("redirectUrl", "signIn.jsp");
				req.getRequestDispatcher("success.jsp").forward(req, resp);
			}
		}
	}

	/**
	 * Validates the email format.
	 * <p>
	 * This method checks if the provided email matches the specified regular expression pattern for valid email addresses.
	 * </p>
	 * 
	 * @param email the email address to validate
	 * @return true if the email format is valid, false otherwise
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