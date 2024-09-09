package com.tapfoods.DAO;

import java.sql.SQLException;
import java.util.List;
import com.tapfoods.model.Menu;

/**
 * DAO interface for searching menu items.
 * <p>
 * This interface defines methods for searching menu items based on various criteria,
 * including by restaurant ID and using global search queries.
 * </p>
 */
public interface MenuSearchDAO {
	/**
	 * Searches for menu items based on the provided restaurant ID and filters.
	 * <p>
	 * This method retrieves menu items associated with a specific restaurant,
	 * applying the provided filters to narrow down the search results.
	 * </p>
	 * 
	 * @param restaurantId the ID of the restaurant for which to retrieve menu items
	 * @param filters a list of filters to apply to the search (e.g., cuisine type, price range)
	 * @return a list of menu items that match the search criteria
	 * @throws SQLException if there is an error executing the SQL query
	 */
	List<Menu> getMenusByRestaurantId(int restaurantId, List<String> filters) throws SQLException;

	/**
	 * Searches for menu items based on a global query.
	 * <p>
	 * This method performs a global search across various menu attributes, such as menu name,
	 * cuisine type, and price, using the provided query string.
	 * </p>
	 * 
	 * @param query the search query string
	 * @return a list of menu items that match the global search criteria
	 * @throws SQLException if there is an error executing the SQL query
	 */
	List<Menu> globalSearch(String query) throws SQLException;
}