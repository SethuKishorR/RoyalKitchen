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
 * The UpdateProfile servlet handles updating user profiles.
 * <p>
 * This servlet processes HTTP POST requests to update user information such as username, phone number, address, and password.
 * It interacts with the database to retrieve and update user details and handles error and success scenarios by forwarding
 * to appropriate views.
 * </p>
 */
@WebServlet("/updateProfile")
public class UpdateProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Processes HTTP POST requests for updating user profiles.
	 * <p>
	 * This method retrieves the current user's details from the session, updates the details with values from the request parameters,
	 * and saves the changes to the database. It handles scenarios where the user is not found, and forwards to appropriate views
	 * based on whether the update was successful or not.
	 * </p>
	 * 
	 * @param req the HttpServletRequest object that contains the request the client made to the servlet
	 * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an input or output error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User sessionUser = (User) session.getAttribute("user");

		if (sessionUser == null) {
			req.setAttribute("message", "User not found. Please log in again.");
			req.setAttribute("redirectUrl", "signIn.jsp");
			req.getRequestDispatcher("error.jsp").forward(req, resp);
			return;
		}

		String email = sessionUser.getEmail();  
		String username = req.getParameter("username");
		String phonenumber = req.getParameter("phonenumber");
		String address = req.getParameter("address");
		String password = req.getParameter("password");

		UserDAO userDao = new UserDAOImpl();
		User currentUser = userDao.getUser(email);

		if (currentUser == null) {
			req.setAttribute("message", "User not found. Please log in again.");
			req.setAttribute("redirectUrl", "signIn.jsp");
			req.getRequestDispatcher("error.jsp").forward(req, resp);
			return;
		}

		/**
		 * Update user details if parameters are not null or empty.
		 * <p>
		 * The user details such as username, phone number, address, and password are updated based on the request parameters.
		 * </p>
		 */
		if (username != null && !username.trim().isEmpty()) {
			currentUser.setUsername(username);
		}
		if (phonenumber != null && !phonenumber.trim().isEmpty()) {
			currentUser.setPhonenumber(phonenumber);
		}
		if (address != null && !address.trim().isEmpty()) {
			currentUser.setAddress(address);
		}
		if (password != null && !password.trim().isEmpty()) {
			currentUser.setPassword(password);
		}

		int status = userDao.updateUser(currentUser);

		if (status == 0) {
			req.setAttribute("message", "User update failed. Please try again.");
			req.setAttribute("redirectUrl", "restaurant.jsp");
			req.getRequestDispatcher("error.jsp").forward(req, resp);
		} else {
			session.setAttribute("user", currentUser);
			req.setAttribute("message", "Profile updated successfully.");
			req.setAttribute("redirectUrl", "restaurant.jsp");
			req.getRequestDispatcher("success.jsp").forward(req, resp);
		}
	}
}