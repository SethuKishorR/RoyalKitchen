package com.tapfoods.adminservlet;

import java.io.IOException;
import java.sql.Connection;
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
import com.tapfoods.DBUtils.DBUtils;
import com.tapfoods.model.Admin;
import com.tapfoods.model.Restaurant;

/**
 * <p>Servlet for handling admin sign-up operations.</p>
 * <p>This servlet is responsible for processing sign-up requests for new admin users and
 * their associated restaurant details. It interacts with the database to create new records
 * for admins and restaurants. It uses DAO implementations to perform database operations.</p>
 * 
 * <p>URL Mapping: <code>/admin/adminSignUp</code></p>
 */
@WebServlet("/admin/adminSignUp")
public class AdminSignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Data Access Object for interacting with Admin entities.</p>
	 */
	private AdminDAO adminDAO;

	/**
	 * <p>Data Access Object for interacting with Restaurant entities.</p>
	 */
	private RestaurantDAO restaurantDAO;

	/**
	 * <p>Static constant representing the administrator's private key.</p>
	 * <p>This key is used for authentication or validation purposes.</p>
	 */
	private static final String ADMINISTRATOR_PRIVATE_KEY = "admin.royalKitchen-20030802@website.com";

	/**
	 * <p>Initializes the servlet by creating instances of DAO implementations.</p>
	 * <p>This method is called by the servlet container during servlet initialization.</p>
	 * <p>It sets up the Data Access Objects for interacting with the database.</p>
	 *
	 * @throws ServletException If an error occurs during initialization.
	 */
	@Override
	public void init() throws ServletException {
		adminDAO = new AdminDAOImpl();
		restaurantDAO = new RestaurantDAOImpl();
	}

	/**
	 * <p>Handles HTTP POST requests for the admin sign-up process.</p>
	 * <p>This method processes different steps of the sign-up process based on the 'step' parameter provided in the request.</p>
	 * <p>The method performs the following operations:</p>
	 * <ul>
	 *   <li>Checks if the 'step' parameter is present and valid.</li>
	 *   <li>Processes the request based on the value of the 'step' parameter by calling the corresponding handler method.</li>
	 *   <li>Forwards to an error page with an appropriate message if the parameter is missing, invalid, or if an exception occurs.</li>
	 * </ul>
	 * <p>In case of an exception, the method sets an error message and forwards the request to an error page.</p>
	 *
	 * @param request  The HttpServletRequest object containing the client's request.
	 * @param response The HttpServletResponse object to send the response to the client.
	 * @throws ServletException If an error occurs during servlet processing.
	 * @throws IOException      If an I/O error occurs while handling the request or response.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {
			String stepParam = request.getParameter("step");
			if (stepParam == null || stepParam.isEmpty()) {
				// Handle missing parameter
				request.setAttribute("message", "Step parameter is missing.");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}

			int step = Integer.parseInt(stepParam);

			// Process based on the step value
			switch (step) {
			case 1:
				handleStep1(request, response, session);
				break;
			case 2:
				handleStep2(request, response, session);
				break;
			case 3:
				handleStep3(request, response, session);
				break;
			case 4:
				handleFinalProcess(request, response, session);
				break;
			default:
				// Handle unknown step
				request.setAttribute("message", "Unknown step parameter.");
				request.getRequestDispatcher("error.jsp").forward(request, response);
				break;
			}
		} catch (NumberFormatException e) {
			// Handle invalid number format
			request.setAttribute("message", "Invalid step parameter format.");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		} catch (Exception e) {
			// Handle other exceptions
			request.setAttribute("message", "An unexpected error occurred.");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}

	/**
	 * <p>Handles the first step of the sign-up process by validating the platform key provided in the request.</p>
	 * <p>This method performs the following operations:</p>
	 * <ul>
	 *   <li>Retrieves the platform key from the request parameters.</li>
	 *   <li>Checks if the provided platform key matches the predefined administrator key.</li>
	 *   <li>If the platform key is valid, updates session attributes to reflect the current status and step, and redirects to the 'adminSignUp.jsp' page.</li>
	 *   <li>If the platform key is invalid, invalidates the session and forwards the request to an error handling method with an appropriate error message.</li>
	 *   <li>Handles exceptions by logging the error details, restarting the session if an {@code IllegalStateException} occurs, and redirecting to an error page.</li>
	 * </ul>
	 *
	 * @param request  The HttpServletRequest object containing the client's request.
	 * @param response The HttpServletResponse object to send the response to the client.
	 * @param session  The HttpSession object to manage session attributes and state.
	 * @throws IOException If an I/O error occurs while handling the request or response.
	 */
	private void handleStep1(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		try {
			String platformKey = request.getParameter("platformKey");
			System.out.println("Handling Step 1");
			System.out.println("Platform Key: " + platformKey);

			if (ADMINISTRATOR_PRIVATE_KEY.equals(platformKey)) {
				session.setAttribute("status", 1);
				session.setAttribute("currentStep", 2);
				session.setAttribute("platformKey", platformKey);

				System.out.println("Session attributes after Step 1:");
				System.out.println("status: " + session.getAttribute("status"));
				System.out.println("currentStep: " + session.getAttribute("currentStep"));
				System.out.println("platformKey: " + session.getAttribute("platformKey"));

				response.sendRedirect("adminSignUp.jsp");
			} else {
				session.invalidate();
				handleClientError(request, response, "Invalid platform key. Please try again.","adminSignUp.jsp");
			}
		} 
		catch (IllegalStateException e) {
			// Log the exception details
			System.err.println("IllegalStateException occurred: " + e.getMessage());

			// Restart the session
			session.invalidate();
			session = request.getSession(true);

			// Set a message or redirect after restarting the session
			session.setAttribute("message", "An error occurred, and the session has been restarted.");
			response.sendRedirect("error.jsp");
		}
		catch (Exception e) {
			handleGeneralException(request, response);
		}
	}

	/**
	 * <p>Handles the second step of the sign-up process by validating the admin key and password details provided in the request.</p>
	 * <p>This method performs the following operations:</p>
	 * <ul>
	 *   <li>Retrieves the admin key, password, and confirm password from the request parameters.</li>
	 *   <li>Validates the password against a predefined pattern to ensure it meets security requirements.</li>
	 *   <li>Validates the admin key to ensure it follows the required format.</li>
	 *   <li>Checks if the provided passwords match.</li>
	 *   <li>Checks if the admin key already exists in the system.</li>
	 *   <li>If all validations pass, stores the admin key and password in the session, updates the current step, and redirects to the 'adminSignUp.jsp' page.</li>
	 *   <li>If any validation fails or an exception occurs, forwards the request to an error handling method with an appropriate error message.</li>
	 * </ul>
	 *
	 * @param request  The HttpServletRequest object containing the client's request.
	 * @param response The HttpServletResponse object to send the response to the client.
	 * @param session  The HttpSession object to manage session attributes and state.
	 * @throws IOException If an I/O error occurs while handling the request or response.
	 */
	private void handleStep2(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		try {
			String adminKey = request.getParameter("adminkey");
			String password = request.getParameter("password");
			String confirmPassword = request.getParameter("confirmpassword");

			String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
			String adminKeyPattern = "^admin\\.[a-zA-Z0-9]+@[a-zA-Z0-9]+_[a-zA-Z0-9]+\\.com$";

			// Validate password
			if (!password.matches(passwordPattern)) {
				handleClientError(request, response, 
						"Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character.",
						"adminSignUp.jsp"); // Example redirect URL
				return;
			}

			// Validate admin key
			if (!adminKey.matches(adminKeyPattern)) {
				handleClientError(request, response, 
						"Admin key must follow the pattern: admin.adminname@restaurantname_address.com",
						"adminSignUp.jsp"); // Example redirect URL
				return;
			}

			// Check if passwords match
			if (!password.equals(confirmPassword)) {
				handleClientError(request, response, 
						"Passwords do not match. Please try again.",
						"adminSignUp.jsp"); // Example redirect URL
				return;
			}

			// Check if admin key already exists
			if (adminDAO.adminkeyExists(adminKey)) {
				handleClientError(request, response, 
						"Admin key already exists. Please choose another key.",
						"adminSignUp.jsp"); // Example redirect URL
				return;
			}

			// Store validated data in session and proceed to the next step
			session.setAttribute("adminKey", adminKey);
			session.setAttribute("password", password);
			session.setAttribute("currentStep", 3);

			System.out.println("Session attributes after Step 2:");
			System.out.println("adminKey: " + session.getAttribute("adminKey"));
			System.out.println("password: " + session.getAttribute("password"));
			System.out.println("currentStep: " + session.getAttribute("currentStep"));

			response.sendRedirect("adminSignUp.jsp");

		} 
		catch (IllegalStateException e) {
			// Log the exception details
			System.err.println("IllegalStateException occurred: " + e.getMessage());

			// Restart the session
			session.invalidate();
			session = request.getSession(true);

			// Set a message or redirect after restarting the session
			session.setAttribute("message", "An error occurred, and the session has been restarted.");
			response.sendRedirect("error.jsp");
		}
		catch (Exception e) {
			handleGeneralException(request, response);
		}
	}

	/**
	 * <p>Handles the third step of the sign-up process by verifying form submission and storing restaurant details provided in the request.</p>
	 * <p>This method performs the following operations:</p>
	 * <ul>
	 *   <li>Retrieves and compares the form token from the request with the token stored in the session to prevent duplicate submissions.</li>
	 *   <li>If the form has already been submitted, forwards the request to an error handling method with a message indicating that the form should not be refreshed.</li>
	 *   <li>Retrieves restaurant details (name and address) from the request parameters.</li>
	 *   <li>Stores the restaurant details in the session and updates the current step to proceed to the final step.</li>
	 *   <li>If any exception occurs, handles it appropriately by logging the details, restarting the session if necessary, and redirecting to an error page or handling it through a general exception method.</li>
	 * </ul>
	 *
	 * @param request  The HttpServletRequest object containing the client's request.
	 * @param response The HttpServletResponse object to send the response to the client.
	 * @param session  The HttpSession object to manage session attributes and state.
	 * @throws IOException If an I/O error occurs while handling the request or response.
	 */
	private void handleStep3(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		try {
			String formToken = request.getParameter("formToken");
			String sessionToken = (String) session.getAttribute("formToken");

			System.out.println("Handling Step 3");
			System.out.println("Form Token: " + formToken);
			System.out.println("Session Token: " + sessionToken);

			// Check if form has already been submitted
			if (sessionToken != null && sessionToken.equals(formToken)) {
				handleClientError(request, response, "Form already submitted. Please do not refresh.","adminSignUp.jsp");
				return;
			}

			// Retrieve and store restaurant details from request
			String restaurantName = request.getParameter("restaurantname");
			String restaurantAddress = request.getParameter("address");

			// Store data in session and proceed to final step
			session.setAttribute("restaurantName", restaurantName);
			session.setAttribute("restaurantAddress", restaurantAddress);
			session.setAttribute("currentStep", 4);

			System.out.println("Session attributes after Step 3:");
			System.out.println("restaurantName: " + session.getAttribute("restaurantName"));
			System.out.println("restaurantAddress: " + session.getAttribute("restaurantAddress"));
			System.out.println("currentStep: " + session.getAttribute("currentStep"));

			response.sendRedirect("adminSignUp.jsp");

		} 
		catch (IllegalStateException e) {
			// Log the exception details
			System.err.println("IllegalStateException occurred: " + e.getMessage());

			// Restart the session
			session.invalidate();
			session = request.getSession(true);

			// Set a message or redirect after restarting the session
			session.setAttribute("message", "An error occurred, and the session has been restarted.");
			response.sendRedirect("error.jsp");
		} 
		catch (Exception e) {
			handleGeneralException(request, response);
		}
	}

	/**
	 * <p>Handles the final step of the sign-up process by creating an admin and restaurant in the database, managing transactions, and providing appropriate feedback.</p>
	 * <p>This method performs the following operations:</p>
	 * <ul>
	 *   <li>Establishes a connection to the database and disables auto-commit to manage transactions manually.</li>
	 *   <li>Retrieves the admin key, password, restaurant name, and address from the session.</li>
	 *   <li>Creates a new restaurant entry in the database.</li>
	 *   <li>If the restaurant is created successfully, proceeds to create an admin entry with the associated restaurant ID.</li>
	 *   <li>Updates the restaurant entry with the created admin ID.</li>
	 *   <li>Commits the transaction if all operations are successful. Otherwise, rolls back the transaction and sets an appropriate error message.</li>
	 *   <li>Handles various exceptions, including SQL and general exceptions, by logging error details, rolling back the transaction if necessary, and setting error messages for the user.</li>
	 *   <li>Ensures the database connection is properly closed in the `finally` block.</li>
	 * </ul>
	 *
	 * @param request  The HttpServletRequest object containing the client's request.
	 * @param response The HttpServletResponse object to send the response to the client.
	 * @param session  The HttpSession object to manage session attributes and state.
	 * @throws IOException If an I/O error occurs while handling the request or response.
	 */
	private void handleFinalProcess(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		Connection con = null;
		try {
			con = DBUtils.myConnect();
			if (con == null) {
				throw new SQLException("Failed to establish database connection.");
			}
			con.setAutoCommit(false);

			String adminKey = (String) session.getAttribute("adminKey");
			String password = (String) session.getAttribute("password");
			String restaurantName = (String) session.getAttribute("restaurantName");
			String restaurantAddress = (String) session.getAttribute("restaurantAddress");

			System.out.println("Handling Final Process");

			// Create the restaurant without adminid_fk initially
			Restaurant restaurant = new Restaurant(restaurantName, restaurantAddress);
			int restaurantId = restaurantDAO.createRestaurant(con, restaurant);

			if (restaurantId > 0) {
				// Update the Restaurant object with the generated restaurantId
				restaurant.setRestaurantid(restaurantId); // Ensure this method exists

				// Create admin with restaurantId_fk
				Admin admin = new Admin(adminKey, password);
				System.out.println("Retrieved Restaurant ID: " + restaurantId);
				admin.setRestaurantid_fk(restaurantId);
				int adminResult = adminDAO.addAdmin(con, admin);

				if (adminResult > 0) {
					// Update restaurant with adminid_fk
					restaurant.setAdminid_fk(adminResult);
					boolean updateSuccess = restaurantDAO.updateRestaurantAdminId(con, restaurant);

					if (updateSuccess) {
						con.commit();
						request.setAttribute("message", "Admin and restaurant have been successfully created.");
						request.setAttribute("redirectUrl", "adminRestaurant.jsp"); // URL to move forward
						request.getRequestDispatcher("success.jsp").forward(request, response);
						session.invalidate();
					} else {
						con.rollback();
						request.setAttribute("message", "Failed to update restaurant with admin ID. Transaction rolled back.");
						request.setAttribute("redirectUrl", "adminSignUp.jsp"); // URL to return or retry
						request.getRequestDispatcher("error.jsp").forward(request, response);
					}
				} else {
					con.rollback();
					request.setAttribute("message", "Failed to create admin. Transaction rolled back.");
					request.setAttribute("redirectUrl", "adminSignUp.jsp"); // URL to return or retry
					request.getRequestDispatcher("error.jsp").forward(request, response);
				}
			} else {
				con.rollback();
				request.setAttribute("message", "Failed to create restaurant. Transaction rolled back.");
				request.setAttribute("redirectUrl", "adminSignUp.jsp"); // URL to return or retry
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
		} catch (IllegalStateException e) {
			System.err.println("IllegalStateException occurred: " + e.getMessage());
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException rollbackEx) {
					System.err.println("Failed to roll back the connection: " + rollbackEx.getMessage());
				}
			}
			session.invalidate();
			session = request.getSession(true);
			request.setAttribute("message", "An error occurred, and the session has been restarted.");
			request.setAttribute("redirectUrl", "adminSignIn.jsp"); // URL for error handling
			try {
				request.getRequestDispatcher("error.jsp").forward(request, response);
			} catch (ServletException | IOException e1) {
				e1.printStackTrace();
			}
		} catch (SQLException e) {
			System.err.println("SQLException occurred: " + e.getMessage());
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException rollbackEx) {
					System.err.println("Failed to roll back the connection: " + rollbackEx.getMessage());
				}
			}
			request.setAttribute("message", "An error occurred while processing your request.");
			request.setAttribute("redirectUrl", "adminSignUp.jsp"); // URL to return or retry
			try {
				request.getRequestDispatcher("error.jsp").forward(request, response);
			} catch (ServletException | IOException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			System.err.println("Exception occurred: " + e.getMessage());
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException rollbackEx) {
					System.err.println("Failed to roll back the connection: " + rollbackEx.getMessage());
				}
			}
			request.setAttribute("message", "An unexpected error occurred.");
			request.setAttribute("redirectUrl", "adminSignUp.jsp"); // URL to return or retry
			try {
				request.getRequestDispatcher("error.jsp").forward(request, response);
			} catch (ServletException | IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (con != null && !con.isClosed()) {
					con.close();
					System.out.println("Database connection closed.");
				}
			} catch (SQLException e) {
				System.err.println("Failed to close the connection: " + e.getMessage());
			}
		}
	}

	/**
	 * Handles HTTP GET requests by delegating to the POST request handler.
	 * <p>
	 * This method ensures that GET requests are processed in the same way as POST requests by calling the {@link #doPost(HttpServletRequest, HttpServletResponse)} method.
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

	/**
	 * Handles client-side errors by forwarding to the error page with a specific message and optional redirect URL.
	 * <p>
	 * This method sets an error message and an optional redirect URL as request attributes, then forwards the request to the {@code error.jsp} page.
	 * If forwarding fails, it redirects to {@code error.jsp} instead.
	 * </p>
	 * 
	 * @param request   The {@link HttpServletRequest} object containing the client's request.
	 * @param response  The {@link HttpServletResponse} object used to send the response to the client.
	 * @param message   The error message to be displayed on the error page.
	 * @param redirectUrl Optional URL to redirect to after displaying the error message. If {@code null}, no redirect URL is set.
	 * @throws IOException If an I/O error occurs while handling the request or response.
	 */
	private void handleClientError(HttpServletRequest request, HttpServletResponse response, String message, String redirectUrl) throws IOException {
		request.setAttribute("message", message);
		if (redirectUrl != null) {
			request.setAttribute("redirectUrl", redirectUrl);
		}
		try {
			request.getRequestDispatcher("error.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}
	}

	/**
	 * Handles general exceptions by forwarding to the error page with a generic error message.
	 * <p>
	 * This method sets a generic error message and forwards the request to the {@code error.jsp} page. If forwarding fails, it redirects to {@code error.jsp} instead.
	 * </p>
	 * 
	 * @param request   The {@link HttpServletRequest} object containing the client's request.
	 * @param response  The {@link HttpServletResponse} object used to send the response to the client.
	 * @throws IOException If an I/O error occurs while handling the request or response.
	 */
	private void handleGeneralException(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setAttribute("message", "An error occurred. Please try again later.");
		try {
			request.getRequestDispatcher("error.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}
	}
}
