package com.tapfoods.DAO;

import java.util.ArrayList;
import com.tapfoods.model.User;

/**
 * The {@code UserDAO} interface defines the data access operations for {@link User} entities.
 * <p>
 * This interface provides methods for adding, retrieving, updating, and deleting {@link User} entities.
 * </p>
 */
public interface UserDAO {

    /**
     * Adds a new user to the database.
     * <p>
     * This method inserts a {@link User} object into the database.
     * </p>
     * 
     * @param u the {@link User} object to be added
     * @return an integer indicating the result of the operation (e.g., the number of rows affected)
     * @throws Exception if there is an error during the operation
     */
    int addUser(User u) throws Exception;

    /**
     * Retrieves all users from the database.
     * <p>
     * This method returns a list of all {@link User} objects stored in the database.
     * </p>
     * 
     * @return an {@link ArrayList} of {@link User} objects representing all users
     * @throws Exception if there is an error during the operation
     */
    ArrayList<User> getAllUser() throws Exception;

    /**
     * Retrieves a user from the database by their email address.
     * <p>
     * This method searches for a {@link User} with the specified email address.
     * </p>
     * 
     * @param email the email address of the user to be retrieved
     * @return the {@link User} object corresponding to the specified email, or {@code null} if no user is found
     * @throws Exception if there is an error during the operation
     */
    User getUser(String email) throws Exception;

    /**
     * Checks if a user with the specified email address already exists in the database.
     * <p>
     * This method checks for the existence of a {@link User} with the given email address.
     * </p>
     * 
     * @param email the email address to be checked
     * @return {@code true} if a user with the specified email exists, {@code false} otherwise
     * @throws Exception if there is an error during the operation
     */
    boolean emailExists(String email) throws Exception;

    /**
     * Updates an existing user's information in the database.
     * <p>
     * This method modifies the corresponding {@link User} record in the database with updated information from the provided {@link User} object.
     * </p>
     * 
     * @param u the {@link User} object containing updated information
     * @return an integer indicating the result of the operation (e.g., the number of rows affected)
     * @throws Exception if there is an error during the operation
     */
    int updateUser(User u) throws Exception;

    /**
     * Deletes a user from the database by their email address.
     * <p>
     * This method removes the {@link User} record with the specified email address from the database.
     * </p>
     * 
     * @param email the email address of the user to be deleted
     * @return an integer indicating the result of the operation (e.g., the number of rows affected)
     * @throws Exception if there is an error during the operation
     */
    int deleteUser(String email) throws Exception;
}