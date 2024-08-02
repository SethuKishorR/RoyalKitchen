package com.tapfoods.DAO;

import java.util.ArrayList;
import com.tapfoods.model.Restaurant;

/**
 * The RestaurantDAO interface defines the data access operations for {@link Restaurant} entities.
 * <p>This interface provides methods for adding, retrieving, updating, and deleting restaurants.</p>
 */
public interface RestaurantDAO {
    
    /**
     * Adds a new restaurant to the database.
     * <p>This method takes a {@link Restaurant} object and inserts it into the database.</p>
     * 
     * @param r the {@link Restaurant} object to be added
     * @return an integer indicating the result of the operation (e.g., the number of rows affected)
     */
    int addRestaurant(Restaurant r);
    
    /**
     * Retrieves all restaurants from the database.
     * <p>This method returns a list of all {@link Restaurant} objects stored in the database.</p>
     * 
     * @return an {@link ArrayList} of {@link Restaurant} objects representing all restaurants
     */
    ArrayList<Restaurant> getAllRestaurant();
    
    /**
     * Retrieves a restaurant from the database by its ID.
     * <p>This method searches for a {@link Restaurant} with the specified ID.</p>
     * 
     * @param restaurantid the ID of the restaurant to be retrieved
     * @return the {@link Restaurant} object corresponding to the specified ID, or {@code null} if no restaurant is found
     */
    Restaurant getRestaurant(int restaurantid);
    
    /**
     * Updates an existing restaurant's information in the database.
     * <p>This method takes a {@link Restaurant} object with updated information and modifies the corresponding record in the database.</p>
     * 
     * @param r the {@link Restaurant} object containing updated information
     * @return an integer indicating the result of the operation (e.g., the number of rows affected)
     */
    int updateRestaurant(Restaurant r);
    
    /**
     * Deletes a restaurant from the database by its ID.
     * <p>This method removes the {@link Restaurant} record with the specified ID from the database.</p>
     * 
     * @param restaurantid the ID of the restaurant to be deleted
     * @return an integer indicating the result of the operation (e.g., the number of rows affected)
     */
    int deleteRestaurant(int restaurantid);
}
