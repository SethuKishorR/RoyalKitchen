package com.tapfoods.adminservlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tapfoods.DAO.AdminDAO;
import com.tapfoods.DAO.RestaurantDAO;
import com.tapfoods.DAOImpl.AdminDAOImpl;
import com.tapfoods.DAOImpl.RestaurantDAOImpl;
import com.tapfoods.model.Admin;
import com.tapfoods.model.Restaurant;

/**
 * <p>Servlet implementation for handling admin sign-in requests.</p>
 * <p>This servlet processes the login credentials of an admin, validates them,
 * and, if successful, stores the admin and associated restaurant details in the session.
 * If the credentials are invalid or an error occurs, it forwards the request to an error page.</p>
 */
@WebServlet("/admin/adminSignIn")
public class AdminSignInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String ADMIN_KEY_PATTERN = "^[a-zA-Z]+(\\.[a-zA-Z]+)?@[a-zA-Z]+_[a-zA-Z]+$";

	/**
	 * <p>Handles the HTTP POST request for admin sign-in.</p>
	 * <p>This method performs the following operations:
	 * <ul>
	 * <li>Validates the format of the provided admin key.</li>
	 * <li>Retrieves the admin details from the database using the admin key.</li>
	 * <li>Checks if the provided password matches the stored password.</li>
	 * <li>If successful, stores the admin and restaurant details in the session.</li>
	 * <li>If an error occurs, forwards the request to an error page with an appropriate message.</li>
	 * </ul></p>
	 *
	 * @param req  The {@link HttpServletRequest} object containing the client request.
	 * @param resp The {@link HttpServletResponse} object containing the response to be sent to the client.
	 * @throws ServletException If an error occurs during servlet processing.
	 * @throws IOException      If an I/O error occurs.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String adminKey = req.getParameter("adminkey");
		String password = req.getParameter("password");
		Admin admin = null;
		AdminDAO adminDao = null;
		RestaurantDAO restaurantDao = null;
		try {
			adminDao = new AdminDAOImpl();
			restaurantDao = new RestaurantDAOImpl();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String errorMessage = null;

		// Validate admin key format
		if (!isValidAdminKey(adminKey)) {
			errorMessage = "Invalid admin key format";
		} else {
			// Retrieve admin from the database
			admin = adminDao.getAdmin(adminKey);

			if (admin == null) {
				errorMessage = "Admin key not found";
			} else if (!admin.getPassword().equals(password)) {
				errorMessage = "Invalid password";
			}
		}

		// Handle errors
		if (errorMessage != null) {
			req.setAttribute("message", errorMessage);
			req.setAttribute("redirectUrl", "adminSignIn.jsp");
			req.getRequestDispatcher("error.jsp").forward(req, resp);
		} else {
			// Store admin details in session
			HttpSession session = req.getSession();
			session.setAttribute("admin", admin); // Store Admin object directly
			session.setAttribute("restaurantid_fk", admin.getRestaurantid_fk());

			// Retrieve the restaurant details and set it in session
			Restaurant restaurant = null;
			try {
				restaurant = restaurantDao.getRestaurant(admin.getRestaurantid_fk());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			session.setAttribute("restaurant", restaurant);

			resp.sendRedirect("AdminRestaurant");
		}
	}

	/**
	 * <p>Handles the HTTP GET request by forwarding it to the doPost method.</p>
	 * <p>This method ensures that GET requests are processed in the same way as POST requests.</p>
	 *
	 * @param req  The {@link HttpServletRequest} object containing the client request.
	 * @param resp The {@link HttpServletResponse} object containing the response to be sent to the client.
	 * @throws ServletException If an error occurs during servlet processing.
	 * @throws IOException      If an I/O error occurs.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	/**
	 * <p>Validates the format of the provided admin key using a regular expression.</p>
	 * <p>This method checks if the admin key matches the predefined pattern.</p>
	 *
	 * @param adminKey The admin key to be validated.
	 * @return {@code true} if the admin key is valid; {@code false} otherwise.
	 */
	private boolean isValidAdminKey(String adminKey) {
		return adminKey != null && adminKey.matches(ADMIN_KEY_PATTERN);
	}
}