package com.tapfoods.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.tapfoods.model.Restaurant;

/**
 * The RestaurantDAO interface provides abstract methods for data access operations
 * related to {@link Restaurant} entities.
 * <p>Implementations of this interface will interact with the database to perform
 * CRUD operations on {@link Restaurant} objects.</p>
 */
public interface RestaurantDAO {

	/**
	 * Creates a new restaurant entry in the database.
	 * 
	 * @param con the {@link Connection} object used for database operations
	 * @param restaurant the {@link Restaurant} object to be added
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 * @throws SQLException if a database access error occurs
	 */
	int createRestaurant(Connection con, Restaurant restaurant) throws SQLException;

	/**
	 * Creates a new restaurant entry in the database.
	 * 
	 * @param restaurant the {@link Restaurant} object to be added
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 * @throws SQLException if a database access error occurs
	 */
	int createRestaurant(Restaurant restaurant) throws SQLException;

	/**
	 * Adds a new restaurant to the database.
	 * 
	 * @param r the {@link Restaurant} object to be added
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 * @throws SQLException if a database access error occurs
	 */
	int addRestaurant(Restaurant r) throws SQLException;

	/**
	 * Retrieves all restaurants from the database.
	 * 
	 * @return an {@link ArrayList} of {@link Restaurant} objects representing all restaurants
	 * @throws SQLException if a database access error occurs
	 */
	ArrayList<Restaurant> getAllRestaurant() throws SQLException;

	/**
	 * Retrieves a restaurant from the database by its ID.
	 * 
	 * @param restaurantid the ID of the restaurant to be retrieved
	 * @return the {@link Restaurant} object corresponding to the specified ID, or {@code null} if no restaurant is found
	 * @throws SQLException if a database access error occurs
	 */
	Restaurant getRestaurant(int restaurantid) throws SQLException;

	/**
	 * Updates an existing restaurant's information in the database.
	 * 
	 * @param r the {@link Restaurant} object containing updated information
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 * @throws SQLException if a database access error occurs
	 */
	int updateRestaurant(Restaurant r) throws SQLException;

	/**
	 * Deletes a restaurant from the database by its ID.
	 * 
	 * @param restaurantid the ID of the restaurant to be deleted
	 * @param con the {@link Connection} object used for database operations
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 * @throws SQLException if a database access error occurs
	 */
	int deleteRestaurant(Connection con, int restaurantid) throws SQLException;

	/**
	 * Deletes a restaurant entry from the database by its ID.
	 * 
	 * @param restaurantid the ID of the restaurant to be deleted
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 * @throws SQLException if a database access error occurs
	 */
	int deleteRestaurant(int restaurantid) throws SQLException;

	/**
	 * Retrieves the ID of the last inserted restaurant from the database.
	 * 
	 * @return the ID of the last inserted restaurant, or -1 if no ID is found
	 * @throws SQLException if a database access error occurs
	 */
	int getLastInsertedRestaurantId() throws SQLException;

	/**
	 * Updates the `adminid_fk` for a specific restaurant in the database.
	 * 
	 * @param con the {@link Connection} object used for database operations
	 * @param restaurant the {@link Restaurant} object containing the new `adminid_fk` and the restaurant ID
	 * @return {@code true} if the update was successful (i.e., one or more rows were affected), {@code false} otherwise
	 * @throws SQLException if a database access error occurs or the SQL statement fails
	 */
	boolean updateRestaurantAdminId(Connection con, Restaurant restaurant) throws SQLException;
}