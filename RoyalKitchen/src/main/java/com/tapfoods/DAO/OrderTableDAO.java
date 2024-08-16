package com.tapfoods.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import com.tapfoods.model.Ordertable;

/**
 * The {@code OrderTableDAO} interface defines the data access operations for {@link Ordertable} entities.
 * <p>
 * This interface provides methods for adding, retrieving, and updating orders.
 * </p>
 */
public interface OrderTableDAO {

	/**
	 * Adds a new order to the database.
	 * <p>
	 * This method takes an {@link Ordertable} object and inserts it into the database.
	 * </p>
	 * 
	 * @param ot the {@link Ordertable} object to be added
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 * @throws SQLException if a database access error occurs
	 */
	int addOrderTable(Ordertable ot) throws SQLException;

	/**
	 * Retrieves all orders from the database.
	 * <p>
	 * This method returns a list of all {@link Ordertable} objects stored in the database.
	 * </p>
	 * 
	 * @return an {@link ArrayList} of {@link Ordertable} objects representing all orders
	 * @throws SQLException if a database access error occurs
	 */
	ArrayList<Ordertable> getAllOrderTable() throws SQLException;

	/**
	 * Retrieves an order from the database by its ID.
	 * <p>
	 * This method searches for an {@link Ordertable} with the specified ID.
	 * </p>
	 * 
	 * @param orderid the ID of the order to be retrieved
	 * @return the {@link Ordertable} object corresponding to the specified ID, or {@code null} if no order is found
	 * @throws SQLException if a database access error occurs
	 */
	Ordertable getOrderTable(int orderid) throws SQLException;
}