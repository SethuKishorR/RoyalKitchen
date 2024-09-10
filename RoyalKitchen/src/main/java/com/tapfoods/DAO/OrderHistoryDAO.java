package com.tapfoods.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import com.tapfoods.model.OrderHistory;

/**
 * The OrderHistoryDAO interface defines the methods for data access operations on {@link OrderHistory} entities.
 * <p>
 * This interface specifies the operations to add, retrieve, and list order histories in the database.
 * </p>
 */
public interface OrderHistoryDAO {
    /**
     * Adds a new order history to the database.
     * 
     * @param oh the {@link OrderHistory} object to be added
     * @return an integer indicating the result of the operation (e.g., the number of rows affected)
     * @throws SQLException if a database access error occurs
     */
    int addOrderHistory(OrderHistory oh) throws SQLException;

    /**
     * Retrieves all order histories from the database.
     * 
     * @return an {@link ArrayList} of {@link OrderHistory} objects representing all order histories
     * @throws SQLException if a database access error occurs
     */
    ArrayList<OrderHistory> getAllOrderHistories() throws SQLException;

    /**
     * Retrieves an order history from the database by its ID.
     * 
     * @param orderhistoryid the ID of the order history to be retrieved
     * @return the {@link OrderHistory} object corresponding to the specified ID, or {@code null} if no order history is found
     * @throws SQLException if a database access error occurs
     */
    OrderHistory getOrderHistory(int orderhistoryid) throws SQLException;

	/**
	 * Retrieves all order histories for a specific user from the database.
	 * <p>This method prepares an SQL {@code SELECT} statement with the user ID and retrieves the corresponding {@link OrderHistory} records.</p>
	 * 
	 * @param userId the ID of the user for whom order histories are to be retrieved
	 * @return an {@link ArrayList} of {@link OrderHistory} objects representing all order histories for the user
	 * @throws SQLException if a database access error occurs
	 */
	ArrayList<OrderHistory> getOrderHistoryByUserId(int userId) throws SQLException;

	/**
	 * Updates the status of an order in the database.
	 * 
	 * @param orderId the ID of the order to be updated
	 * @param status the new status to be set
	 * @return {@code true} if the update was successful, {@code false} otherwise
	 * @throws SQLException if a database access error occurs
	 */
	boolean updateOrderStatus(int orderid, String status) throws SQLException;
}