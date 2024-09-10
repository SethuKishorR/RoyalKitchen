package com.tapfoods.adminservlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tapfoods.DAO.MenuDAO;
import com.tapfoods.DAOImpl.MenuDAOImpl;
import com.tapfoods.model.Admin;

/**
 * Servlet implementation class MenuDeleteServlet
 * <p>
 * This servlet handles the deletion of menu items from the database by authenticated admins.
 * It verifies the admin's session and password before proceeding to delete the specified
 * menu item. Based on the outcome, it forwards the request to either a success or error page
 * with appropriate messages.
 * </p>
 */
@WebServlet("/admin/deleteMenu")
public class MenuDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles HTTP POST requests for deleting a menu item.
	 * <p>
	 * This method retrieves the menu ID and admin password from the request parameters,
	 * validates the admin session and password, and attempts to delete the specified
	 * menu item from the database. It then forwards the request to a success or error
	 * JSP page based on the result of the deletion operation.
	 * </p>
	 *
	 * @param request  The {@link HttpServletRequest} object that contains the request made by the client.
	 * @param response The {@link HttpServletResponse} object that contains the response sent to the client.
	 * @throws ServletException If an input or output error is detected when the servlet handles the request.
	 * @throws IOException      If the request for the POST could not be handled.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/**
		 * Retrieve menu ID and admin password from the request parameters.
		 * <p>
		 * Parses the menu ID from a string to an integer and checks for any parsing errors.
		 * If parsing fails, forwards to the error page with an appropriate message.
		 * </p>
		 */
		String menuidStr = request.getParameter("menuid");
		String adminPassword = request.getParameter("adminPassword");
		int menuid = Integer.parseInt(menuidStr);

		/**
		 * Retrieve the admin object from the current HTTP session.
		 * <p>
		 * Checks whether the admin is logged in by verifying the existence of the admin object
		 * in the session. If the session has expired or the admin is not logged in, forwards
		 * to the error page with an appropriate message.
		 * </p>
		 */
		HttpSession session = request.getSession();
		Admin admin = (Admin) session.getAttribute("admin");

		if (admin == null) {
			request.setAttribute("message", "Session expired. Please log in again.");
			request.setAttribute("redirectUrl", "adminSignIn.jsp");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}

		/**
		 * Validate the provided admin password.
		 * <p>
		 * Compares the provided password with the password stored in the admin session object.
		 * If the passwords do not match, forwards to the error page with an authentication failure message.
		 * </p>
		 */
		if (adminPassword == null || !adminPassword.equals(admin.getPassword())) {
			request.setAttribute("message", "Authentication failed. Invalid admin password.");
			request.setAttribute("redirectUrl", "AdminRestaurant");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}

		/**
		 * Attempt to delete the menu item from the database.
		 * <p>
		 * Initializes the {@link MenuDAO} implementation and calls the {@code deleteMenu} method
		 * with the specified menu ID. Handles any exceptions during the database operation and
		 * forwards to the appropriate JSP page based on the result.
		 * </p>
		 */
		try {
			MenuDAO menuDAO = new MenuDAOImpl();
			int status = menuDAO.deleteMenu(menuid);

			if (status == 0) {
				request.setAttribute("message", "Menu deletion failed.");
				request.setAttribute("redirectUrl", "AdminRestaurant");
				request.getRequestDispatcher("error.jsp").forward(request, response);
			} else {
				request.setAttribute("message", "Menu deleted successfully.");
				request.setAttribute("redirectUrl", "AdminRestaurant");
				request.getRequestDispatcher("success.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Error deleting menu item: " + e.getMessage());
			request.setAttribute("redirectUrl", "AdminRestaurant");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}

	/**
	 * Handles HTTP GET requests by delegating to the POST request handler.
	 * <p>
	 * This method ensures that GET requests are processed in the same way as POST requests
	 * by calling the {@link #doPost(HttpServletRequest, HttpServletResponse)} method.
	 * </p>
	 *
	 * @param request  The {@link HttpServletRequest} object that contains the request made by the client.
	 * @param response The {@link HttpServletResponse} object that contains the response sent to the client.
	 * @throws ServletException If an input or output error is detected when the servlet handles the request.
	 * @throws IOException      If the request for the GET could not be handled.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}