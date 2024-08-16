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
	 * This method inserts an {@link Admin} object into the database using the provided {@link Connection} object.
	 * It prepares an SQL {@code INSERT} statement, executes it, and returns the ID of the newly added admin.
	 * </p>
	 * 
	 * @param con the {@link Connection} object to be used for executing the SQL statement
	 * @param admin the {@link Admin} object to be added to the database
	 * @return an integer representing the ID of the newly added admin
	 * @throws SQLException if a database access error occurs or if the SQL operation fails
	 */
	int addAdmin(Connection con, Admin admin) throws SQLException;

	/**
	 * Adds a new admin to the database.
	 * <p>
	 * This method inserts an {@link Admin} object into the database. The database connection is assumed to be managed externally.
	 * It prepares an SQL {@code INSERT} statement, executes it, and returns the ID of the newly added admin.
	 * </p>
	 * 
	 * @param admin the {@link Admin} object to be added to the database
	 * @return an integer representing the ID of the newly added admin
	 * @throws SQLException if a database access error occurs or if the SQL operation fails
	 */
	int addAdmin(Admin admin) throws SQLException;


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
	 * Retrieves an admin from the database by their admin key.
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
	 * @param admin the {@link Admin} object containing updated information
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 */
	int updateAdmin(Admin admin);

	/**
	 * Deletes an admin from the database by their ID using an external connection.
	 * <p>
	 * This method prepares an SQL {@code DELETE} statement with a specified ID and executes it to remove the corresponding {@link Admin} record.
	 * </p>
	 * 
	 * @param adminid the ID of the admin to be deleted
	 * @param con the {@link Connection} object to be used for the database operation
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 * @throws SQLException if an SQL error occurs
	 */
	int deleteAdmin(Connection con, int adminid) throws SQLException;

	/**
	 * Deletes an admin from the database by their ID.
	 * <p>
	 * This method prepares an SQL {@code DELETE} statement with a specified ID and executes it to remove the corresponding {@link Admin} record.
	 * </p>
	 * 
	 * @param adminid the ID of the admin to be deleted
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 * @throws SQLException if an SQL error occurs
	 */
	int deleteAdmin(int adminid) throws SQLException;
}