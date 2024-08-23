package com.tapfoods.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import com.tapfoods.model.Menu;

/**
 * The {@code MenuDAO} interface defines the data access operations for {@link Menu} entities.
 * <p>
 * This interface provides methods for adding, retrieving, updating, and deleting menus.
 * </p>
 */
public interface MenuDAO {

	/**
	 * Adds a new menu to the database.
	 * <p>
	 * This method takes a {@link Menu} object and inserts it into the database.
	 * </p>
	 * 
	 * @param m the {@link Menu} object to be added
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 * @throws SQLException if a database access error occurs or the SQL statement fails
	 */
	int addMenu(Menu m) throws SQLException;

	/**
	 * Retrieves all menus from the database.
	 * <p>
	 * This method returns a list of all {@link Menu} objects stored in the database.
	 * </p>
	 * 
	 * @return an {@link ArrayList} of {@link Menu} objects representing all menus
	 * @throws SQLException if a database access error occurs or the SQL statement fails
	 */
	ArrayList<Menu> getAllMenu() throws SQLException;

	/**
	 * Retrieves a menu from the database by its ID.
	 * <p>
	 * This method searches for a {@link Menu} with the specified menu ID.
	 * </p>
	 * 
	 * @param menuid the ID of the menu to be retrieved
	 * @return the {@link Menu} object corresponding to the specified ID, or {@code null} if no menu is found
	 * @throws SQLException if a database access error occurs or the SQL statement fails
	 */
	Menu getMenu(int menuid) throws SQLException;

	/**
	 * Retrieves menus from the database by restaurant ID.
	 * 
	 * @param restaurantId the ID of the restaurant whose menus are to be retrieved
	 * @return an {@link ArrayList} of {@link Menu} objects
	 * @throws SQLException if a database access error occurs or the SQL statement fails
	 */
	ArrayList<Menu> getMenusByRestaurantId(int restaurantId) throws SQLException;

	/**
	 * Updates an existing menu's information in the database.
	 * <p>
	 * This method takes a {@link Menu} object with updated information and modifies the corresponding record in the database.
	 * </p>
	 * 
	 * @param m the {@link Menu} object containing updated information
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 * @throws SQLException if a database access error occurs or the SQL statement fails
	 */
	int updateMenu(Menu m) throws SQLException;

	/**
	 * Deletes a menu from the database by its ID.
	 * <p>
	 * This method removes the {@link Menu} record with the specified menu ID from the database.
	 * </p>
	 * 
	 * @param menuid the ID of the menu to be deleted
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 * @throws SQLException if a database access error occurs or the SQL statement fails
	 */
	int deleteMenu(int menuid) throws SQLException;
}