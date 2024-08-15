package com.tapfoods.DAO;

import java.util.ArrayList;

import com.tapfoods.model.OrderHistory;

/**
 * The {@code OrderHistoryDAO} interface defines the data access operations for {@link OrderHistory} entities.
 * <p>
 * This interface provides methods for adding, retrieving, and updating order histories.
 * </p>
 */
public interface OrderHistoryDAO {

	/**
	 * Adds a new order history to the database.
	 * <p>
	 * This method takes an {@link OrderHistory} object and inserts it into the database.
	 * </p>
	 * 
	 * @param oh the {@link OrderHistory} object to be added
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 */
	int addOrderHistory(OrderHistory oh);

	/**
	 * Retrieves all order histories from the database.
	 * <p>
	 * This method returns a list of all {@link OrderHistory} objects stored in the database.
	 * </p>
	 * 
	 * @return an {@link ArrayList} of {@link OrderHistory} objects representing all order histories
	 */
	ArrayList<OrderHistory> getAllOrderHistories();

	/**
	 * Retrieves an order history from the database by its ID.
	 * <p>
	 * This method searches for an {@link OrderHistory} with the specified ID.
	 * </p>
	 * 
	 * @param orderhistoryid the ID of the order history to be retrieved
	 * @return the {@link OrderHistory} object corresponding to the specified ID, or {@code null} if no order history is found
	 */
	OrderHistory getOrderHistory(int orderhistoryid);
}