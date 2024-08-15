package com.tapfoods.DAO;

import java.util.ArrayList;

import com.tapfoods.model.Orderitem;

/**
 * The {@code OrderItemDAO} interface defines the contract for data access operations related to {@link Orderitem} entities.
 * <p>
 * This interface provides methods for adding, retrieving, and listing order items.
 * </p>
 */
public interface OrderItemDAO {

    /**
     * Adds a new order item to the database.
     * <p>
     * This method prepares an SQL {@code INSERT} statement and executes it to add an {@link Orderitem} object to the database.
     * </p>
     * 
     * @param oi the {@link Orderitem} object to be added
     * @return an integer indicating the result of the operation (e.g., the number of rows affected)
     */
    int addOrderItem(Orderitem oi);

    /**
     * Retrieves all order items from the database.
     * <p>
     * This method executes an SQL {@code SELECT} statement to retrieve all {@link Orderitem} records and returns them as an {@link ArrayList}.
     * </p>
     * 
     * @return an {@link ArrayList} of {@link Orderitem} objects representing all order items
     */
    ArrayList<Orderitem> getAllOrderitem();

    /**
     * Retrieves an order item from the database by its ID.
     * <p>
     * This method prepares an SQL {@code SELECT} statement with a specified order item ID and retrieves the corresponding {@link Orderitem} record.
     * </p>
     * 
     * @param orderitemid the ID of the order item to be retrieved
     * @return the {@link Orderitem} object corresponding to the specified ID, or {@code null} if no order item is found
     */
    Orderitem getOrderitem(int orderitemid);
}