package com.tapfoods.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tapfoods.DAOImpl.OrderHistoryDAOImpl;
import com.tapfoods.DAOImpl.OrderItemDAOImpl;
import com.tapfoods.model.OrderHistory;
import com.tapfoods.model.User;

@WebServlet("/OrderHistory")
public class HistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles GET requests to fetch and display the order history for the logged-in user.
	 * <p>
	 * This method retrieves the user information from the session, uses the `OrderHistoryDAOImpl`
	 * to fetch the order history for the user from the database, and forwards the results to
	 * `history.jsp` for display. If the user is not logged in, it redirects to `history.jsp`.
	 * In case of an error during data retrieval, it sets an error message and redirects to `error.jsp`.
	 * </p>
	 * 
	 * @param request the HttpServletRequest object containing the request data
	 * @param response the HttpServletResponse object used to send the response
	 * @throws ServletException if an error occurs during request processing
	 * @throws IOException if an I/O error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		// Ensure user is logged in
		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect("history.jsp");
			return;
		}

		// Get user details from session
		User user = (User) session.getAttribute("user");
		int userId = user.getUserid();

		try {
			// Fetch order history for the logged-in user
			OrderHistoryDAOImpl orderHistoryDAO = new OrderHistoryDAOImpl();
			ArrayList<OrderHistory> orderHistoryList = orderHistoryDAO.getOrderHistoryByUserId(userId);

			// Store the order history in session or request
			request.setAttribute("orderHistoryList", orderHistoryList);

			// Forward to history.jsp
			request.getRequestDispatcher("history.jsp").forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			// Handle error and forward to error page
			session.setAttribute("errorMessage", "Failed to retrieve order history.");
			response.sendRedirect("error.jsp");
		}
	}

	/**
	 * Handles POST requests by delegating to the doGet method.
	 * <p>
	 * This method ensures that POST requests are processed in the same way as GET requests,
	 * making it possible to handle order history retrieval through both types of HTTP requests.
	 * </p>
	 * 
	 * @param request the HttpServletRequest object containing the request data
	 * @param response the HttpServletResponse object used to send the response
	 * @throws ServletException if an error occurs during request processing
	 * @throws IOException if an I/O error occurs
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}