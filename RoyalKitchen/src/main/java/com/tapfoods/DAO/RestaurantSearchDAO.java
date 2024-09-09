package com.tapfoods.DAO;

import com.tapfoods.model.Restaurant;

import java.sql.SQLException;
import java.util.List;

/**
 * The RestaurantSearchDAO interface provides methods for searching restaurants in the database.
 */
public interface RestaurantSearchDAO {

    /**
     * Searches for restaurants based on the provided criteria.
     * <p>
     * This method allows for flexible search based on restaurant name, cuisine type, address, and active status.
     * </p>
     *
     * @param restaurantName the name of the restaurant to search for (can be null for no filtering)
     * @param cuisineType the type of cuisine to search for (can be null for no filtering)
     * @param address the address to search for (can be null for no filtering)
     * @param isActive the active status of the restaurant (can be null for no filtering)
     * @return a list of restaurants matching the search criteria
     * @throws SQLException if any SQL error occurs
     */
    List<Restaurant> searchRestaurants(List<String> queries) throws SQLException;

    /**
     * Performs a global search across all restaurants based on a generic search query.
     * <p>
     * This method searches restaurants by name, cuisine type, address, and other attributes using a single query.
     * </p>
     *
     * @param query the search query that will be matched against multiple fields (e.g., name, cuisine, etc.)
     * @return a list of restaurants that match the global search criteria
     * @throws SQLException if any SQL error occurs
     */
    List<Restaurant> globalSearch(String query) throws SQLException;
}