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
}