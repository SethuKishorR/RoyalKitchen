package com.tapfoods.adminservlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tapfoods.DAOImpl.OrderTableDAOImpl;
import com.tapfoods.model.Ordertable;

/**
 * Servlet for handling requests related to the order table for a specific restaurant.
 * <p>
 * This servlet processes requests to retrieve and display the order history for a given restaurant.
 * It ensures that the session is valid, fetches the order history from the database, and forwards 
 * the request to the appropriate JSP page for displaying the order table.
 * </p>
 * 
 * @see HttpServlet
 */
@WebServlet("/admin/adminOrderTable")
public class AdminOrderTableServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	/**
	 * Handles HTTP requests by retrieving the order history for a specific restaurant.
	 * <p>
	 * This method checks for a valid session and retrieves the restaurant ID. It then fetches the 
	 * order history from the database and forwards the request to the JSP page for displaying the 
	 * order table. If an error occurs during the database operation, it forwards to an error page 
	 * with an appropriate message.
	 * </p>
	 * 
	 * @param req  The {@link HttpServletRequest} object that contains the request from the client.
	 * @param resp The {@link HttpServletResponse} object used to send a response to the client.
	 * @throws ServletException If the request cannot be handled.
	 * @throws IOException      If an I/O error occurs while handling the request or response.
	 */
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		Integer restaurantId = (Integer) session.getAttribute("restaurantId");

		try {
			// Fetch order history for the given restaurant ID
			OrderTableDAOImpl orderTableDAO = new OrderTableDAOImpl();
			ArrayList<Ordertable> orderTableList = orderTableDAO.getOrderTableByRestaurantId(restaurantId);

			// Store the order history in request
			req.setAttribute("orderTableList", orderTableList);

			// Forward to history.jsp
			req.getRequestDispatcher("adminOrdersTable.jsp").forward(req, resp);

		} catch (SQLException e) {
			e.printStackTrace();  // You might want to use a logging framework instead
			req.setAttribute("message", "Failed to retrieve order history.");
			req.getRequestDispatcher("error.jsp").forward(req, resp);
		}
	}
	/**
	 * Handles HTTP POST requests by delegating to the GET request handler.
	 * <p>
	 * This method ensures that POST requests are processed in the same way as GET requests by calling
	 * the {@link #service(HttpServletRequest, HttpServletResponse)} method.
	 * </p>
	 * 
	 * @param req  The {@link HttpServletRequest} object that contains the request from the client.
	 * @param resp The {@link HttpServletResponse} object used to send a response to the client.
	 * @throws ServletException If the request cannot be handled.
	 * @throws IOException      If an I/O error occurs while handling the request or response.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);  // Handle POST requests the same way as GET requests
	}
}