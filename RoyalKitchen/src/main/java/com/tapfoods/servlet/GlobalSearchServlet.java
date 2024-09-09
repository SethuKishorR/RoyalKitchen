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
import javax.servlet.http.HttpSession;

import com.tapfoods.DAO.MenuSearchDAO;
import com.tapfoods.DAO.RestaurantSearchDAO;
import com.tapfoods.DAOImpl.MenuSearchDAOImpl;
import com.tapfoods.DAOImpl.RestaurantSearchDAOImpl;
import com.tapfoods.model.Menu;
import com.tapfoods.model.Restaurant;

@WebServlet("/globalSearch")
public class GlobalSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles both GET and POST requests for global search.
	 * <p>
	 * This method processes the search query provided by the user, performs a search
	 * across both restaurants and menus using the respective DAO implementations,
	 * and forwards the results to the `globalSearch.jsp` page. It also handles
	 * potential errors and redirects to an error page if necessary.
	 * </p>
	 * 
	 * @param request the HttpServletRequest object containing the request data
	 * @param response the HttpServletResponse object used to send the response
	 * @throws ServletException if an error occurs during request processing
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			// Session is not available, redirect to the index page
			response.sendRedirect("index.jsp");
			return;
		}

		// Retrieve and validate the search query
		String query = request.getParameter("query");
		if (query == null || query.trim().isEmpty()) {
			request.setAttribute("message", "Search query cannot be empty.");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}

		List<Restaurant> restaurantList = new ArrayList<>();
		List<Menu> menuList = new ArrayList<>();

		try {
			// Perform search using RestaurantSearchDAO and MenuSearchDAO
			RestaurantSearchDAO restaurantSearchDAO = new RestaurantSearchDAOImpl();
			restaurantList = restaurantSearchDAO.globalSearch(query);

			MenuSearchDAO menuSearchDAO = new MenuSearchDAOImpl();
			menuList = menuSearchDAO.globalSearch(query);

		} catch (SQLException e) {
			// Handle SQL exceptions and forward to error page
			e.printStackTrace();
			request.setAttribute("message", "An error occurred during the search.");
			request.setAttribute("redirectUrl", "searchRestaurants");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}
		// Store search results in the session
		session.setAttribute("restaurantList", restaurantList);
		session.setAttribute("menuList", menuList);
		// Forward to the globalSearch.jsp page to display results
		request.getRequestDispatcher("globalSearch.jsp").forward(request, response);
	}
}