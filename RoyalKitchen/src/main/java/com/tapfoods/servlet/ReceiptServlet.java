package com.tapfoods.servlet;

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
 * Servlet for handling receipt generation requests.
 * <p>
 * This servlet processes requests to view the details of a specific order. It retrieves order information
 * and associated order items from the database and forwards the request to the receipt.jsp page for display.
 * </p>
 * 
 * <p>
 * It performs the following tasks:
 * <ul>
 *     <li>Initializes data access objects (DAOs) for retrieving order and order item details.</li>
 *     <li>Retrieves the order ID from the request parameters.</li>
 *     <li>Fetches the order details and order items based on the provided order ID.</li>
 *     <li>Sets order details and items as request attributes and forwards the request to receipt.jsp.</li>
 *     <li>Handles cases where the order ID is missing or invalid by redirecting to an error page.</li>
 * </ul>
 * </p>
 * 
 * @see javax.servlet.http.HttpServlet
 * @see com.tapfoods.DAOImpl.OrderTableDAOImpl
 * @see com.tapfoods.DAOImpl.OrderItemDAOImpl
 * @see com.tapfoods.model.Ordertable
 * @see com.tapfoods.model.Orderitem
 */
@WebServlet("/receiptServlet")
public class ReceiptServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	/** Data access object for handling order table operations. */
	private OrderTableDAOImpl orderTableDAO;

	/** Data access object for handling order item operations. */
	private OrderItemDAOImpl orderItemDAO;

	/**
	 * Initializes the servlet and its data access objects.
	 * 
	 * @throws ServletException if there is an error initializing the DAOs
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
	 * Handles HTTP requests for generating order receipts.
	 * 
	 * <p>
	 * This method processes POST requests to view the details of an order by retrieving the order ID from
	 * the request parameters, fetching order details and items from the database, and forwarding the request
	 * to receipt.jsp for display. It handles errors such as missing or invalid order IDs by redirecting to
	 * an error page.
	 * </p>
	 * 
	 * @param request  the HttpServletRequest object that contains the request data
	 * @param response the HttpServletResponse object used to send responses to the client
	 * @throws ServletException if there is an error during request processing
	 * @throws IOException      if there is an error sending the response
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String orderIdParam = request.getParameter("orderid");
		// Redirect to error page if order ID is missing
		if (orderIdParam == null || orderIdParam.isEmpty()) {
			response.sendRedirect("error.jsp"); // Redirect to an error page if order ID is missing
			return;
		}

		try {
			int orderId = Integer.parseInt(orderIdParam); // Convert orderId to int
			Ordertable orderTable = orderTableDAO.getOrderTable(orderId);
			ArrayList<Orderitem> orderItems = orderItemDAO.getOrderItemsByOrderId(orderId); // Retrieve items by orderId
			// Forward to receipt.jsp if order is found
			if (orderTable != null) {
				request.setAttribute("orderTable", orderTable);
				request.setAttribute("orderItems", orderItems);
				request.setAttribute("orderid", orderId); // Set orderid as an integer attribute
				request.getRequestDispatcher("receipt.jsp").forward(request, response);
			} else {
				response.sendRedirect("error.jsp"); // Redirect to an error page if order not found
			}
		} catch (SQLException | NumberFormatException e) {
			throw new ServletException("Error retrieving order details", e);
		}
	}
}