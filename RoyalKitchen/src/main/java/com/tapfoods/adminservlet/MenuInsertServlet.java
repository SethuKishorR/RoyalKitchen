package com.tapfoods.adminservlet;

import com.tapfoods.DAO.MenuDAO;
import com.tapfoods.DAOImpl.MenuDAOImpl;
import com.tapfoods.model.Menu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet implementation class MenuInsertServlet
 * <p>
 * This servlet handles the process of adding a new menu item to the database. It retrieves
 * and validates menu details from the client request, inserts the new menu item into the
 * database, and forwards the user to an appropriate JSP page based on the outcome of the 
 * insertion operation.
 * </p>
 */
@WebServlet("/admin/AddMenuServlet")
public class MenuInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles HTTP POST requests for adding a new menu item.
	 * <p>
	 * This method retrieves the menu item details from the request, validates the input, 
	 * and attempts to insert the menu item into the database. It then forwards the request 
	 * to the appropriate JSP page based on the outcome of the insertion operation.
	 * </p>
	 * 
	 * @param req  The {@link HttpServletRequest} object that contains the request from the client.
	 * @param resp The {@link HttpServletResponse} object used to send a response to the client.
	 * @throws ServletException If the request cannot be handled.
	 * @throws IOException      If an I/O error occurs while handling the request or response.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String menuName = req.getParameter("menuname");
		String priceStr = req.getParameter("price");
		String description = req.getParameter("description");
		String isAvailable = req.getParameter("isavailable");
		String imagePath = req.getParameter("imagepath");
		int restaurantId;

		// Define the default redirect URL and message
		String redirectUrl = "adminRestaurant.jsp";
		String message;

		// Validate and parse restaurant ID
		try {
			restaurantId = Integer.parseInt(req.getParameter("restaurantid"));
		} catch (NumberFormatException e) {
			message = "Invalid restaurant ID.";
			req.setAttribute("message", message);
			req.setAttribute("redirectUrl", redirectUrl);
			req.getRequestDispatcher("error.jsp").forward(req, resp);
			return;
		}

		// Validate menu name input
		if (menuName == null || menuName.trim().isEmpty()) {
			message = "Menu name is required.";
			req.setAttribute("message", message);
			req.setAttribute("redirectUrl", redirectUrl);
			req.getRequestDispatcher("error.jsp").forward(req, resp);
			return;
		}

		// Validate and parse price input
		float price;

		if (imagePath == null || imagePath.trim().isEmpty()) {
			imagePath = null; // Set imagePath to null if no file is selected
		}

		try {
			price = Float.parseFloat(priceStr);
		} catch (NumberFormatException e) {
			message = "Invalid price format.";
			req.setAttribute("message", message);
			req.setAttribute("redirectUrl", redirectUrl);
			req.getRequestDispatcher("error.jsp").forward(req, resp);
			return;
		}

		// Create a new Menu object and set its properties
		Menu menu = new Menu();
		menu.setRestaurantid(restaurantId);
		menu.setMenuname(menuName);
		menu.setPrice(price);
		menu.setDescription(description);
		menu.setIsavailable(isAvailable);
		menu.setImagepath(imagePath);

		// Initialize the MenuDAO object
		MenuDAO menuDAO = null;
		try {
			menuDAO = new MenuDAOImpl();
		} catch (SQLException e) {
			e.printStackTrace();
			message = "Database connection error.";
			req.setAttribute("message", message);
			req.setAttribute("redirectUrl", redirectUrl);
			req.getRequestDispatcher("error.jsp").forward(req, resp);
			return;
		}

		// Attempt to add the menu item to the database
		int result;
		try {
			result = menuDAO.addMenu(menu);
		} catch (SQLException e) {
			e.printStackTrace();
			message = "Failed to add menu item. Please try again.";
			req.setAttribute("message", message);
			req.setAttribute("redirectUrl", redirectUrl);
			req.getRequestDispatcher("error.jsp").forward(req, resp);
			return;
		}

		// Check the result of the insertion operation and forward accordingly
		if (result > 0) {
			message = "Menu item added successfully.";
			redirectUrl = "adminRestaurant.jsp";
			req.setAttribute("message", message);
			req.setAttribute("redirectUrl", redirectUrl);
			req.getRequestDispatcher("success.jsp").forward(req, resp);
		} else {
			message = "Failed to add menu item. Please try again.";
			req.setAttribute("message", message);
			req.setAttribute("redirectUrl", redirectUrl);
			req.getRequestDispatcher("error.jsp").forward(req, resp);
		}
	}

	/**
	 * Handles HTTP GET requests by delegating to the POST request handler.
	 * <p>
	 * This method ensures that GET requests are processed in the same way as POST requests 
	 * by calling the {@link #doPost(HttpServletRequest, HttpServletResponse)} method.
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