package com.tapfoods.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.tapfoods.model.Admin;

/**
 * The {@code AdminDAO} interface defines the data access operations for {@link Admin} entities.
 * <p>
 * This interface provides methods for adding, retrieving, updating, and deleting {@link Admin} entities.
 * </p>
 */
public interface AdminDAO {

    /**
     * Adds a new admin to the database.
     * <p>
     * This method inserts an {@link Admin} object into the database.
     * </p>
     * 
     * @param con the {@link Connection} object to be used for the database operation
     * @param a the {@link Admin} object to be added
     * @return an integer indicating the result of the operation (e.g., the number of rows affected)
     * @throws SQLException if an SQL error occurs
     */
    int addAdmin(Connection con, Admin a) throws SQLException;

    /**
     * Retrieves all admins from the database.
     * <p>
     * This method returns a list of all {@link Admin} objects stored in the database.
     * </p>
     * 
     * @return an {@link ArrayList} of {@link Admin} objects representing all admins
     */
    ArrayList<Admin> getAllAdmin();

    /**
     * Retrieves an admin from the database by their ID.
     * <p>
     * This method searches for an {@link Admin} with the specified admin key.
     * </p>
     * 
     * @param adminkey the key of the admin to be retrieved
     * @return the {@link Admin} object corresponding to the specified key, or {@code null} if no admin is found
     */
    Admin getAdmin(String adminkey);

    /**
     * Checks if an admin with the specified admin key already exists in the database.
     * <p>
     * This method checks for the existence of an {@link Admin} with the given admin key.
     * </p>
     * 
     * @param adminkey the admin key to be checked
     * @return {@code true} if an admin with the specified admin key exists, {@code false} otherwise
     * @throws SQLException if an SQL error occurs
     */
    boolean adminkeyExists(String adminkey) throws SQLException;

    /**
     * Updates an existing admin's information in the database.
     * <p>
     * This method modifies the corresponding {@link Admin} record in the database with updated information from the provided {@link Admin} object.
     * </p>
     * 
     * @param a the {@link Admin} object containing updated information
     * @return an integer indicating the result of the operation (e.g., the number of rows affected)
     */
    int updateAdmin(Admin a);

    /**
     * Deletes an admin from the database by their ID.
     * <p>
     * This method removes the {@link Admin} record with the specified ID from the database.
     * </p>
     * 
     * @param adminid the ID of the admin to be deleted
     * @param con the {@link Connection} object to be used for the database operation
     * @return an integer indicating the result of the operation (e.g., the number of rows affected)
     * @throws SQLException if an SQL error occurs
     */
    public int deleteAdmin(int adminid, Connection con) throws SQLException;

    /**
     * Creates a new admin entry in the database.
     * <p>
     * This method prepares an SQL {@code INSERT} statement and executes it to add an {@link Admin} object to the database.
     * </p>
     * 
     * @param admin the {@link Admin} object to be added
     * @return an integer indicating the result of the operation (e.g., the number of rows affected)
     */
}