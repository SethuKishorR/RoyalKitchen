package com.tapfoods.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tapfoods.DAO.RestaurantDAO;
import com.tapfoods.DAO.RestaurantSearchDAO;
import com.tapfoods.DAOImpl.RestaurantDAOImpl;
import com.tapfoods.DAOImpl.RestaurantSearchDAOImpl;
import com.tapfoods.model.Restaurant;

/**
 * Servlet for handling restaurant search and filtering.
 * <p>
 * This servlet processes requests to search and filter restaurants based on user queries. It maintains a list
 * of active queries in the session and retrieves restaurant details accordingly.
 * </p>
 * 
 * <p>
 * It performs the following tasks:
 * <ul>
 *     <li>Ensures that a valid session exists; redirects to the home page if not.</li>
 *     <li>Retrieves search query parameters and updates the list of active queries stored in the session.</li>
 *     <li>Fetches restaurant details based on active queries or retrieves all restaurants if no queries are active.</li>
 *     <li>Handles errors during database operations by forwarding to an error page.</li>
 *     <li>Stores the retrieved list of restaurants in the session and forwards the request to the JSP page for display.</li>
 * </ul>
 * </p>
 * 
 * @see javax.servlet.http.HttpServlet
 * @see com.tapfoods.DAO.RestaurantDAO
 * @see com.tapfoods.DAO.RestaurantSearchDAO
 * @see com.tapfoods.DAOImpl.RestaurantDAOImpl
 * @see com.tapfoods.DAOImpl.RestaurantSearchDAOImpl
 * @see com.tapfoods.model.Restaurant
 */
@WebServlet("/searchRestaurants")
public class RestaurantSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * Handles HTTP requests for searching and filtering restaurants.
	 * 
	 * <p>
	 * This method processes both GET and POST requests to update the list of active queries based on user input,
	 * retrieves the filtered list of restaurants from the database, and forwards the request to the restaurant.jsp page.
	 * It also handles cases where there is an error during database operations by forwarding to an error page.
	 * </p>
	 * 
	 * @param request  the HttpServletRequest object that contains the request data
	 * @param response the HttpServletResponse object used to send responses to the client
	 * @throws ServletException if there is an error during request processing
	 * @throws IOException      if there is an error sending the response
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Ensure the session exists
		var session = request.getSession(false);
		if (session == null) {
			response.sendRedirect("index.jsp");
			return;
		}

		// Retrieve the search query parameters
		String query = request.getParameter("query");
		String removeQuery = request.getParameter("removeQuery");
		String addQuery = request.getParameter("addQuery");

		// Retrieve the list of active queries from the session
		List<String> activeQueries = (List<String>) session.getAttribute("activeQueries");
		if (activeQueries == null) {
			activeQueries = new ArrayList<>();
		}

		// Update the active queries list based on user actions
		if (addQuery != null && !addQuery.isEmpty() && !activeQueries.contains(addQuery)) {
			activeQueries.add(addQuery);
		}
		if (removeQuery != null && !removeQuery.isEmpty()) {
			activeQueries.remove(removeQuery);
		}
		if (query != null && !query.isEmpty() && !activeQueries.contains(query)) {
			activeQueries.add(query);
		}

		// Store the updated list of active queries back in the session
		session.setAttribute("activeQueries", activeQueries);

		// Retrieve restaurant details from DAO
		List<Restaurant> restaurantList = new ArrayList<>();
		try {
			RestaurantSearchDAO searchDAO = new RestaurantSearchDAOImpl();
			if (!activeQueries.isEmpty()) {
				restaurantList = searchDAO.searchRestaurants(activeQueries);
			} else {
				RestaurantDAO restaurantDAO = new RestaurantDAOImpl();
				restaurantList = restaurantDAO.getAllRestaurant(); // Retrieve all restaurants
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("message", "An error occurred while retrieving restaurants.");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}

		// Store the filtered restaurant list in the session
		session.setAttribute("restaurantList", restaurantList);

		// Forward to JSP page
		request.getRequestDispatcher("restaurant.jsp").forward(request, response);
	}
}