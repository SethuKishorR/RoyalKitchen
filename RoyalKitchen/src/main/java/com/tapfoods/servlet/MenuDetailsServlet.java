package com.tapfoods.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tapfoods.DAO.MenuDAO;
import com.tapfoods.DAO.RestaurantDAO;
import com.tapfoods.DAOImpl.MenuDAOImpl;
import com.tapfoods.DAOImpl.RestaurantDAOImpl;
import com.tapfoods.model.Menu;
import com.tapfoods.model.Restaurant;

/**
 * Servlet to fetch and display menu and restaurant details based on menu ID and restaurant ID.
 * <p>
 * This servlet handles requests to fetch details for a specific menu item and restaurant
 * using their respective IDs. It retrieves the details from the database and forwards them
 * to the `menuItem.jsp` page for display. If any errors occur, it forwards to an error page.
 * </p>
 */
@WebServlet("/menuDetails")
public class MenuDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MenuDAO menuDAO;
	private RestaurantDAO restaurantDAO;
	/**
	 * Initializes the servlet by setting up DAO implementations.
	 * <p>
	 * This method is called by the servlet container to indicate that the servlet is being
	 * placed into service. It initializes the `menuDAO` and `restaurantDAO` objects used for
	 * database operations.
	 * </p>
	 * 
	 * @throws ServletException if a database connection fails during initialization
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
	 * Handles requests to fetch and display menu and restaurant details.
	 * <p>
	 * This method processes the request parameters to retrieve the menu and restaurant
	 * details from the database. It forwards the details to `menuItem.jsp` for display
	 * if found, or to `error.jsp` if any issues occur. It handles invalid ID formats and
	 * missing parameters by forwarding to the error page with appropriate messages.
	 * </p>
	 * 
	 * @param request the HttpServletRequest object containing request data
	 * @param response the HttpServletResponse object used to send the response
	 * @throws ServletException if an error occurs during request processing
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String menuIdParam = request.getParameter("id");
		String restaurantIdParam = request.getParameter("restaurantId");
		String menuName = request.getParameter("menuName");

		if (menuIdParam != null && !menuIdParam.isEmpty() && restaurantIdParam != null && !restaurantIdParam.isEmpty()) {
			try {
				int menuId = Integer.parseInt(menuIdParam);
				int restaurantId = Integer.parseInt(restaurantIdParam);

				Menu menu = menuDAO.getMenu(menuId);
				Restaurant restaurant = restaurantDAO.getRestaurant(restaurantId);

				if (menu != null && restaurant != null) {
					request.setAttribute("menu", menu);
					request.setAttribute("restaurant", restaurant);
					request.setAttribute("menuName", menuName);
					request.getRequestDispatcher("menuItem.jsp").forward(request, response);
				} else {
					request.setAttribute("errorMessage", "Menu or Restaurant details not found.");
					request.getRequestDispatcher("error.jsp").forward(request, response);
				}

			} catch (NumberFormatException e) {
				request.setAttribute("errorMessage", "Invalid menu or restaurant ID format.");
				request.getRequestDispatcher("error.jsp").forward(request, response);
			} catch (SQLException e) {
				throw new ServletException("Database query failed", e);
			}
		} else {
			request.setAttribute("errorMessage", "Menu or Restaurant ID is missing.");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}
}