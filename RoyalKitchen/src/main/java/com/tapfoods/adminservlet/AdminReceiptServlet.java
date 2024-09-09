package com.tapfoods.adminservlet;

import com.tapfoods.DAOImpl.OrderTableDAOImpl;
import com.tapfoods.DAOImpl.OrderItemDAOImpl;
import com.tapfoods.model.Ordertable;
import com.tapfoods.model.Orderitem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Servlet for handling requests related to retrieving and displaying order receipts.
 * <p>
 * This servlet processes requests to retrieve the details of a specific order based on its ID.
 * It initializes the necessary data access objects (DAOs), fetches the order details and associated items
 * from the database, and forwards the request to the appropriate JSP page for displaying the receipt.
 * </p>
 * 
 * @see HttpServlet
 */
@WebServlet("/admin/adminReceiptServlet")
public class AdminReceiptServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private OrderTableDAOImpl orderTableDAO;
	private OrderItemDAOImpl orderItemDAO;
	/**
	 * Initializes the servlet by creating instances of the DAO objects.
	 * <p>
	 * This method sets up the data access objects for retrieving order and order item details.
	 * If initialization fails, a {@link ServletException} is thrown.
	 * </p>
	 * 
	 * @throws ServletException If the initialization fails.
	 */
	@Override
	public void init() throws ServletException {
		try {
			orderTableDAO = new OrderTableDAOImpl();
			orderItemDAO = new OrderItemDAOImpl();
		} catch (SQLException e) {
			throw new ServletException("Failed to initialize DAOs", e);
		}
	}
	/**
	 * Handles HTTP requests by retrieving and displaying the details of a specific order.
	 * <p>
	 * This method retrieves the order ID from the request, fetches the order details and associated items
	 * from the database, and forwards the request to the appropriate JSP page. If the order ID is missing, 
	 * or an error occurs during retrieval, the request is forwarded to an error page with an appropriate message.
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
		String orderIdParam = request.getParameter("orderid");
		if (orderIdParam == null || orderIdParam.isEmpty()) {
			// Set message and forward to error.jsp
			request.setAttribute("message", "Order ID is missing.");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}

		try {
			int orderId = Integer.parseInt(orderIdParam); // Convert orderId to int
			Ordertable orderTable = orderTableDAO.getOrderTable(orderId);
			ArrayList<Orderitem> orderItems = orderItemDAO.getOrderItemsByOrderId(orderId); // Retrieve items by orderId

			if (orderTable != null) {
				// Set attributes and forward to adminReceipt.jsp
				request.setAttribute("orderTable", orderTable);
				request.setAttribute("orderItems", orderItems);
				request.setAttribute("orderid", orderId);
				// Set success message (if needed)
				request.setAttribute("message", "Order details retrieved successfully.");
				request.getRequestDispatcher("adminReceipt.jsp").forward(request, response);
			} else {
				// Set message and forward to error.jsp
				request.setAttribute("message", "Order not found.");
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
		} catch (SQLException | NumberFormatException e) {
			// Set error message and forward to error.jsp
			request.setAttribute("message", "Error retrieving order details: " + e.getMessage());
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}
}
