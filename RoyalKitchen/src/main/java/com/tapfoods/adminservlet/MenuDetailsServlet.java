package com.tapfoods.adminservlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tapfoods.DAO.MenuDAO;
import com.tapfoods.DAO.RestaurantDAO;
import com.tapfoods.DAOImpl.MenuDAOImpl;
import com.tapfoods.DAOImpl.RestaurantDAOImpl;
import com.tapfoods.model.Menu;
import com.tapfoods.model.Restaurant;

/**
 * Servlet to fetch and display menu and restaurant details based on menu ID and restaurant ID.
 * <p>
 * This servlet retrieves details of a menu item and the associated restaurant based on the provided 
 * menu ID and restaurant ID. It then forwards the data to the {@code menuItem.jsp} page for display.
 * If any errors occur, or if the IDs are missing or invalid, it forwards the request to {@code error.jsp}
 * with an appropriate error message.
 * </p>
 * 
 * @see HttpServlet
 */
@WebServlet("/admin/adminmenuDetails")
public class MenuDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MenuDAO menuDAO;
	private RestaurantDAO restaurantDAO;
	/**
	 * Initializes the servlet and sets up the DAOs.
	 * <p>
	 * This method is called when the servlet is first loaded. It initializes the {@link MenuDAO} and
	 * {@link RestaurantDAO} objects for database interactions. If initialization fails, it throws a
	 * {@link ServletException}.
	 * </p>
	 * 
	 * @throws ServletException If database initialization fails.
	 */
	@Override
	public void init() throws ServletException {
		try {
			menuDAO = new MenuDAOImpl();
			restaurantDAO = new RestaurantDAOImpl(); // Assuming you have a RestaurantDAOImpl
		} catch (SQLException e) {
			throw new ServletException("Database connection failed", e);
		}
	}
	/**
	 * Handles HTTP requests to fetch and display menu and restaurant details.
	 * <p>
	 * This method processes the request to retrieve details of a menu item and the associated restaurant
	 * based on the provided menu ID. It sets the retrieved details as request attributes and forwards
	 * the request to {@code menuItem.jsp} for display. If the IDs are missing or invalid, or if there are
	 * issues with retrieving data, it forwards the request to {@code error.jsp} with an appropriate message.
	 * </p>
	 * 
	 * @param request  The {@link HttpServletRequest} object that contains the request from the client.
	 * @param response The {@link HttpServletResponse} object used to send a response to the client.
	 * @throws ServletException If the request cannot be handled.
	 * @throws IOException      If an I/O error occurs while handling the request or response.
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String menuIdParam = request.getParameter("id");
		HttpSession session = request.getSession(false);
		Integer restaurantId = (Integer) session.getAttribute("restaurantId");

		if (menuIdParam != null && !menuIdParam.isEmpty()) {
			try {
				int menuId = Integer.parseInt(menuIdParam);

				Menu menu = menuDAO.getMenu(menuId);
				Restaurant restaurant = restaurantDAO.getRestaurant(restaurantId);

				if (menu != null && restaurant != null) {
					request.setAttribute("menu", menu);
					request.setAttribute("restaurant", restaurant);
					request.getRequestDispatcher("menuItem.jsp").forward(request, response);
				} else {
					request.setAttribute("message", "Menu or Restaurant details not found.");
					request.getRequestDispatcher("error.jsp").forward(request, response);
				}

			} catch (NumberFormatException e) {
				request.setAttribute("message", "Invalid menu or restaurant ID format.");
				request.getRequestDispatcher("error.jsp").forward(request, response);
			} catch (SQLException e) {
				throw new ServletException("Database query failed", e);
			}
		} else {
			request.setAttribute("message", "Menu or Restaurant ID is missing.");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}
}