package com.tapfoods.DAO;

import java.util.ArrayList;

import com.tapfoods.model.User;

/**
 * The UserDAO interface defines the data access operations for {@link User} entities.
 * <p>This interface provides methods for adding, retrieving, updating, and deleting users.</p>
 */
public interface UserDAO {
    
    /**
     * Adds a new user to the database.
     * <p>This method takes a {@link User} object and inserts it into the database.</p>
     * 
     * @param u the {@link User} object to be added
     * @return an integer indicating the result of the operation (e.g., the number of rows affected)
     */
    int addUser(User u);
    
    /**
     * Retrieves all users from the database.
     * <p>This method returns a list of all {@link User} objects stored in the database.</p>
     * 
     * @return an {@link ArrayList} of {@link User} objects representing all users
     */
    ArrayList<User> getAllUser();
    
    /**
     * Retrieves a user from the database by their email.
     * <p>This method searches for a {@link User} with the specified email address.</p>
     * 
     * @param email the email address of the user to be retrieved
     * @return the {@link User} object corresponding to the specified email, or {@code null} if no user is found
     */
    User getUser(String email);
    
    /**
     * Updates an existing user's information in the database.
     * <p>This method takes a {@link User} object with updated information and modifies the corresponding record in the database.</p>
     * 
     * @param u the {@link User} object containing updated information
     * @return an integer indicating the result of the operation (e.g., the number of rows affected)
     */
    int updateUser(User u);
    
    /**
     * Deletes a user from the database by their email.
     * <p>This method removes the {@link User} record with the specified email address from the database.</p>
     * 
     * @param email the email address of the user to be deleted
     * @return an integer indicating the result of the operation (e.g., the number of rows affected)
     */
    int deleteUser(String email);
}
