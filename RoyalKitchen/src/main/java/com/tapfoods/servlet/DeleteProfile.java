package com.tapfoods.servlet;

import java.io.IOException;
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
 * Servlet implementation class DeleteProfile
 * <p>
 * This servlet handles the process of deleting a user profile. It ensures that the user is logged in, verifies the provided password, 
 * and attempts to delete the user from the database. Based on the result, it either invalidates the user session and redirects 
 * to a success page or forwards to an error page with an appropriate message.
 * </p>
 */
@WebServlet("/deleteProfile")
public class DeleteProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles POST requests for profile deletion.
	 * <p>
	 * This method retrieves the user object from the session, checks if the user is logged in, and validates the provided password. 
	 * If the password is correct, it attempts to delete the user's profile from the database. Depending on the outcome of the 
	 * deletion process, it either invalidates the user session and redirects to a success page or forwards to an error page with 
	 * an appropriate message.
	 * </p>
	 * 
	 * @param req the HttpServletRequest object containing the request data
	 * @param resp the HttpServletResponse object for sending the response
	 * @throws ServletException if an error occurs during request handling
	 * @throws IOException if an input or output error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User sessionUser = (User) session.getAttribute("user");

		/**
		 * Checks if the user is logged in by verifying the presence of a user object in the session. If no user is found, it sets 
		 * an error message and redirects to the sign-in page.
		 * <p>
		 * If the user is not found in the session, it forwards to the error page with a message prompting the user to log in again.
		 * </p>
		 */
		if (sessionUser == null) {
			req.setAttribute("message", "User not found. Please log in again.");
			req.setAttribute("redirectUrl", "signIn.jsp");
			req.getRequestDispatcher("error.jsp").forward(req, resp);
			return;
		}

		/**
		 * Retrieves the email address of the logged-in user from the session and the password provided in the request. It then 
		 * attempts to retrieve the current user details from the database.
		 * <p>
		 * The email is fetched from the session, and the password is obtained from the request parameters.
		 * </p>
		 */
		String email = sessionUser.getEmail();
		String password = req.getParameter("password");

		UserDAO userDao = null;
		User currentUser = null;
		try {
			userDao = new UserDAOImpl();
			currentUser = userDao.getUser(email);
		} catch (Exception e) {
			e.printStackTrace();
		}

		/**
		 * Verifies the provided password against the one stored in the database. If the password is incorrect or the user is not found, 
		 * it sets an error message and redirects to the restaurant page.
		 * <p>
		 * If the password is invalid or the user does not exist, the request is forwarded to an error page with an appropriate message.
		 * </p>
		 */
		if (currentUser == null || !currentUser.getPassword().equals(password)) {
			req.setAttribute("message", "Invalid password. Please try again.");
			req.setAttribute("redirectUrl", "searchRestaurants");
			req.getRequestDispatcher("error.jsp").forward(req, resp);
			return;
		}

		/**
		 * Attempts to delete the user profile from the database. If the deletion is unsuccessful, it sets an error message and redirects 
		 * to the restaurant page. If successful, it invalidates the user session and redirects to the sign-up page with a success message.
		 * <p>
		 * Based on the result of the deletion operation, the user is either redirected to the success page or shown an error message.
		 * </p>
		 */
		try {
			int status = userDao.deleteUser(email);

			if (status == 0) {
				req.setAttribute("message", "Profile deletion failed. Please try again.");
				req.setAttribute("redirectUrl", "searchRestaurants");
				req.getRequestDispatcher("error.jsp").forward(req, resp);
			} else {
				session.invalidate();
				req.setAttribute("message", "Profile deleted successfully.");
				req.setAttribute("redirectUrl", "signUp.jsp");
				req.getRequestDispatcher("success.jsp").forward(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("message", "An error occurred. Please try again.");
			req.setAttribute("redirectUrl", "searchRestaurants");
			req.getRequestDispatcher("error.jsp").forward(req, resp);
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