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

import com.tapfoods.DAO.MenuDAO;
import com.tapfoods.DAO.MenuSearchDAO;
import com.tapfoods.DAOImpl.MenuDAOImpl;
import com.tapfoods.DAOImpl.MenuSearchDAOImpl;
import com.tapfoods.model.Menu;

/**
 * Servlet to handle menu search requests and display results based on active search queries.
 * <p>
 * This servlet processes search queries for menu items based on restaurant ID and active queries.
 * It retrieves menu items from the database, updates session attributes, and forwards the request 
 * to `menu.jsp` for displaying the results. It also handles adding and removing queries from the 
 * active search queries list.
 * </p>
 */
@WebServlet("/searchMenu")
public class MenuSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * Handles the HTTP request for searching menu items.
	 * <p>
	 * This method processes the request parameters, including restaurant ID and search queries.
	 * It retrieves menu items from the database based on the active queries and restaurant ID.
	 * The results are stored in the session and forwarded to `menu.jsp`. If any errors occur, 
	 * it forwards to an error page with an appropriate message.
	 * </p>
	 * 
	 * @param request the HttpServletRequest object containing request data
	 * @param response the HttpServletResponse object used to send the response
	 * @throws ServletException if an error occurs during request processing
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendRedirect("index.jsp");
			return;
		}

		String restaurantIdStr = request.getParameter("restaurantId");
		String restaurantName = request.getParameter("restaurantName");
		int restaurantId;

		if (restaurantIdStr != null && !restaurantIdStr.isEmpty()) {
			try {
				restaurantId = Integer.parseInt(restaurantIdStr);
			} catch (NumberFormatException e) {
				request.setAttribute("message", "Invalid restaurant ID.");
				request.getRequestDispatcher("error.jsp").forward(request, response);
				return;
			}
		} else {
			Integer sessionRestaurantId = (Integer) session.getAttribute("restaurantId");
			if (sessionRestaurantId != null) {
				restaurantId = sessionRestaurantId;
			} else {
				request.setAttribute("message", "Restaurant ID is missing.");
				request.getRequestDispatcher("error.jsp").forward(request, response);
				return;
			}
		}

		List<String> activeQueries = (List<String>) session.getAttribute("activeQueries");
		if (activeQueries == null) {
			activeQueries = new ArrayList<>();
		} else {
		}

		String removeQuery = request.getParameter("removeQuery");
		String addQuery = request.getParameter("addQuery");

		if (removeQuery != null && !removeQuery.isEmpty()) {
			activeQueries.remove(removeQuery);
		} else if (addQuery != null && !addQuery.isEmpty()) {
			if (!activeQueries.contains(addQuery)) {
				activeQueries.add(addQuery);
			}
		}

		List<Menu> menuList = null;
		try {
			if (activeQueries.isEmpty()) {
				MenuDAO menuDAO = new MenuDAOImpl();
				menuList = menuDAO.getMenusByRestaurantId(restaurantId);
			} else {
				MenuSearchDAO searchDAO = new MenuSearchDAOImpl();
				menuList = searchDAO.getMenusByRestaurantId(restaurantId, activeQueries);
			}

			session.setAttribute("menuList", menuList);

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("message", "An error occurred while retrieving menu items.");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}

		session.setAttribute("activeQueries", activeQueries);
		session.setAttribute("restaurantId", restaurantId);
		session.setAttribute("restaurantName", restaurantName);
		request.getRequestDispatcher("menu.jsp").forward(request, response);
	}
}