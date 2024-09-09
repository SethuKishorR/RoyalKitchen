package com.tapfoods.adminservlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tapfoods.DAO.MenuDAO;
import com.tapfoods.DAOImpl.MenuDAOImpl;
import com.tapfoods.model.Admin;
import com.tapfoods.model.Menu;

/**
 * Servlet for handling requests related to the restaurant's menu.
 * <p>
 * This servlet processes GET requests to fetch and display the menu for a specific restaurant. 
 * It ensures that the admin is logged in, retrieves the menu items from the database, and forwards
 * the request to the appropriate JSP page to display the menu. If there is an issue with fetching
 * the menu or if the session is invalid, it redirects or forwards to an error page.
 * </p>
 * 
 * @see HttpServlet
 */
@WebServlet("/admin/AdminRestaurant")
public class AdminRestaurantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MenuDAO menuDAO;
	/**
	 * Handles HTTP GET requests for displaying the restaurant's menu.
	 * <p>
	 * This method checks if the admin is logged in by verifying the presence of an admin object in the session.
	 * If the admin is found, it retrieves the menu items for the restaurant and forwards the request to 
	 * {@code adminRestaurant.jsp} for displaying the menu. If there is an issue with fetching the menu, 
	 * it forwards the request to {@code error.jsp} with an appropriate error message.
	 * </p>
	 * 
	 * @param request  The {@link HttpServletRequest} object that contains the request from the client.
	 * @param response The {@link HttpServletResponse} object used to send a response to the client.
	 * @throws ServletException If the request cannot be handled.
	 * @throws IOException      If an I/O error occurs while handling the request or response.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			menuDAO = new MenuDAOImpl();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("admin") == null) {
			// Redirect to admin sign-in page if session is invalid
			response.sendRedirect("adminSignIn.jsp");
			return;
		}

		Admin admin = (Admin) session.getAttribute("admin");
		int restaurantId = admin.getRestaurantid_fk();
		session.setAttribute("restaurantId", restaurantId);
		try {
			// Fetch the menu list for the restaurant
			ArrayList<Menu> menuList = menuDAO.getMenusByRestaurantId(restaurantId);

			// Set the menu list as a request attribute
			request.setAttribute("menuList", menuList);

			// Forward to adminRestaurant.jsp to display the menu
			RequestDispatcher dispatcher = request.getRequestDispatcher("adminRestaurant.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			// Handle any SQL errors by redirecting to error page
			request.setAttribute("message", "Error fetching menu list: " + e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
			dispatcher.forward(request, response);
		}
	}
	/**
	 * Handles HTTP POST requests by delegating to the GET request handler.
	 * <p>
	 * This method ensures that POST requests are processed in the same way as GET requests by calling the 
	 * {@link #doGet(HttpServletRequest, HttpServletResponse)} method.
	 * </p>
	 * 
	 * @param request  The {@link HttpServletRequest} object that contains the request from the client.
	 * @param response The {@link HttpServletResponse} object used to send a response to the client.
	 * @throws ServletException If the request cannot be handled.
	 * @throws IOException      If an I/O error occurs while handling the request or response.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response); // Reuse the same logic for POST requests
	}
}