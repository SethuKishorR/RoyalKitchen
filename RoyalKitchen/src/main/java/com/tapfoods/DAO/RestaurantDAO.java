package com.tapfoods.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.tapfoods.model.Restaurant;

/**
 * The RestaurantDAO interface defines the contract for data access operations related to {@link Restaurant} entities.
 * <p>Implementing classes should provide the actual logic for interacting with the database to perform CRUD operations on restaurant records.</p>
 */
public interface RestaurantDAO {

	/**
	 * Creates a new restaurant entry in the database.
	 * <p>This method is responsible for adding a new {@link Restaurant} object to the database.</p>
	 * 
	 * @param restaurant the {@link Restaurant} object to be added
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 */
	int createRestaurant(Connection con, Restaurant restaurant) throws SQLException;

	/**
	 * Adds a new restaurant to the database.
	 * <p>This method is responsible for adding a {@link Restaurant} object with all its details to the database.</p>
	 * 
	 * @param r the {@link Restaurant} object to be added
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 */
	int addRestaurant(Restaurant r);

	/**
	 * Retrieves all restaurants from the database.
	 * <p>This method returns a list of all {@link Restaurant} records stored in the database.</p>
	 * 
	 * @return an {@link ArrayList} of {@link Restaurant} objects representing all restaurants
	 */
	ArrayList<Restaurant> getAllRestaurant();

	/**
	 * Retrieves a restaurant from the database by its ID.
	 * <p>This method returns a single {@link Restaurant} object based on the provided ID.</p>
	 * 
	 * @param restaurantid the ID of the restaurant to be retrieved
	 * @return the {@link Restaurant} object corresponding to the specified ID, or {@code null} if no restaurant is found
	 */
	Restaurant getRestaurant(int restaurantid);

	/**
	 * Updates an existing restaurant's information in the database.
	 * <p>This method is responsible for updating the details of an existing {@link Restaurant} record in the database.</p>
	 * 
	 * @param r the {@link Restaurant} object containing updated information
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 */
	int updateRestaurant(Restaurant r);

	/**
	 * Deletes a restaurant from the database by its ID.
	 * <p>This method is responsible for removing a {@link Restaurant} record from the database based on the provided ID.</p>
	 * 
	 * @param restaurantid the ID of the restaurant to be deleted
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 */
	int deleteRestaurant(int restaurantid, Connection con) throws SQLException;

	int getLastInsertedRestaurantId() throws SQLException;

	boolean updateRestaurantAdminId(Connection con, Restaurant restaurant) throws SQLException;
}