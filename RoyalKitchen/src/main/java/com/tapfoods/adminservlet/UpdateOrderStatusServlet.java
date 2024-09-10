package com.tapfoods.adminservlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tapfoods.DAO.OrderHistoryDAO;
import com.tapfoods.DAO.OrderTableDAO;
import com.tapfoods.DAOImpl.OrderHistoryDAOImpl;
import com.tapfoods.DAOImpl.OrderTableDAOImpl;

/**
 * Servlet to update the status of an order.
 * <p>
 * This servlet processes the request to update the status of an order based on the provided order ID and new status.
 * It interacts with the database to update the order status and forwards the request to the appropriate page based on the result.
 * </p>
 */
@WebServlet("/admin/updateOrderStatusServlet")
public class UpdateOrderStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * Handles POST requests to update the status of an order.
	 * <p>
	 * This method retrieves the order ID and new status from the request, updates the order status in the database,
	 * and forwards the request to either a success or error page based on the outcome of the update operation.
	 * </p>
	 * 
	 * @param request  The {@link HttpServletRequest} object that contains the request from the client.
	 * @param response The {@link HttpServletResponse} object used to send a response to the client.
	 * @throws ServletException If an error occurs while processing the request.
	 * @throws IOException      If an I/O error occurs while processing the request or response.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int orderId = Integer.parseInt(request.getParameter("orderid"));
		String status = request.getParameter("status");

		try {
			OrderTableDAO orderTableDAO = new OrderTableDAOImpl();
			OrderHistoryDAO orderHistoryDAO = new OrderHistoryDAOImpl();
			boolean isTableUpdated = orderTableDAO.updateOrderStatus(orderId, status);
			boolean isHistoryUpdated = orderHistoryDAO.updateOrderStatus(orderId, status);

			if (isTableUpdated && isHistoryUpdated) {
				// Forward to adminReceiptServlet with success message
				request.setAttribute("orderid", orderId);
				request.setAttribute("message", "Order status updated successfully.");
				request.getRequestDispatcher("adminReceiptServlet").forward(request, response);
			} else {
				// Forward to error.jsp with a failure message
				request.setAttribute("message", "Failed to update order status.");
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Forward to error.jsp with an error message
			request.setAttribute("message", "Database error occurred while updating order status.");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}
}
