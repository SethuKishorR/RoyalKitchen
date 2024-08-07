package com.tapfoods.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.tapfoods.DAO.UserDAO;
import com.tapfoods.DBUtils.DBUtils;
import com.tapfoods.model.User;

/**
 * The {@code UserDAOImpl} class implements the {@link UserDAO} interface.
 * <p>
 * This class provides the actual implementation of data access operations for {@link User} entities using JDBC.
 * </p>
 */
public class UserDAOImpl implements UserDAO {
	private static final Logger logger = Logger.getLogger(UserDAOImpl.class.getName());

	private Connection con;
	private static final String ADD_USER = "INSERT INTO `user` (`username`, `email`, `phonenumber`, `address`, `password`) VALUES (?, ?, ?, ?, ?)";
	private static final String GET_ALL_USER = "SELECT * FROM `user`";
	private static final String GET_ON_EMAIL = "SELECT * FROM `user` WHERE `email`=?";
	private static final String UPDATE_ON_EMAIL = "UPDATE `user` SET `username`=?, `phonenumber`=?, `address`=?, `password`=? WHERE `email`=?";
	private static final String DELETE_ON_EMAIL = "DELETE FROM `user` WHERE `email`=?";
	private static final String CHECK_EMAIL_EXISTS = "SELECT 1 FROM `user` WHERE `email`=?";

	/**
	 * Constructs a new {@code UserDAOImpl} instance and establishes a database connection.
	 * <p>
	 * This constructor initializes the database connection using {@link DBUtils#myConnect()}.
	 * </p>
	 */
	public UserDAOImpl() {
		try {
			con = DBUtils.myConnect();
		} catch (Exception e) {
			logger.severe("Failed to connect to the database: " + e.getMessage());
		}
	}

	/**
	 * Adds a new user to the database.
	 * <p>
	 * This method prepares an SQL {@code INSERT} statement and executes it to add a {@link User} object to the database.
	 * </p>
	 * 
	 * @param u the {@link User} object to be added
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 */
	@Override
	public int addUser(User u) {
		int status = 0;
		try (PreparedStatement pstmt = con.prepareStatement(ADD_USER)) {
			pstmt.setString(1, u.getUsername());
			pstmt.setString(2, u.getEmail());
			pstmt.setString(3, u.getPhonenumber());
			pstmt.setString(4, u.getAddress());
			pstmt.setString(5, u.getPassword());
			status = pstmt.executeUpdate();
		} catch (Exception e) {
			logger.severe("Error adding user: " + e.getMessage());
		}
		return status;
	}

	/**
	 * Retrieves all users from the database.
	 * <p>
	 * This method executes an SQL {@code SELECT} statement to retrieve all {@link User} records and returns them as an {@link ArrayList}.
	 * </p>
	 * 
	 * @return an {@link ArrayList} of {@link User} objects representing all users
	 */
	@Override
	public ArrayList<User> getAllUser() {
		ArrayList<User> userList = new ArrayList<>();
		try (Statement stmt = con.createStatement(); ResultSet resultSet = stmt.executeQuery(GET_ALL_USER)) {
			userList = extractUserListFromResultSet(resultSet);
		} catch (Exception e) {
			logger.severe("Error retrieving all users: " + e.getMessage());
		}
		return userList;
	}

	/**
	 * Retrieves a user from the database by their email.
	 * <p>
	 * This method prepares an SQL {@code SELECT} statement with a specified email and retrieves the corresponding {@link User} record.
	 * </p>
	 * 
	 * @param email the email address of the user to be retrieved
	 * @return the {@link User} object corresponding to the specified email, or {@code null} if no user is found
	 */
	@Override
	public User getUser(String email) {
		User user = null;
		try (PreparedStatement pstmt = con.prepareStatement(GET_ON_EMAIL)) {
			pstmt.setString(1, email);
			try (ResultSet resultSet = pstmt.executeQuery()) {
				ArrayList<User> userList = extractUserListFromResultSet(resultSet);
				if (!userList.isEmpty()) {
					user = userList.get(0);
				}
			}
		} catch (Exception e) {
			logger.severe("Error retrieving user by email: " + e.getMessage());
		}
		return user;
	}

	/**
	 * Checks if a user with the specified email already exists in the database.
	 * <p>
	 * This method prepares an SQL {@code SELECT} statement with a specified email and checks if a corresponding {@link User} record exists.
	 * </p>
	 * 
	 * @param email the email address to be checked
	 * @return {@code true} if the email exists, {@code false} otherwise
	 */
	@Override
	public boolean emailExists(String email) {
		boolean exists = false;
		try (PreparedStatement pstmt = con.prepareStatement(CHECK_EMAIL_EXISTS)) {
			pstmt.setString(1, email);
			try (ResultSet resultSet = pstmt.executeQuery()) {
				exists = resultSet.next(); // If resultSet.next() is true, email exists
			}
		} catch (Exception e) {
			logger.severe("Error checking if email exists: " + e.getMessage());
		}
		return exists;
	}

	/**
	 * Updates an existing user's information in the database.
	 * <p>
	 * This method prepares an SQL {@code UPDATE} statement with the new user information and executes it to update the corresponding record.
	 * </p>
	 * 
	 * @param u the {@link User} object containing updated information
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 */
	@Override
	public int updateUser(User u) {
		int status = 0;
		try (PreparedStatement pstmt = con.prepareStatement(UPDATE_ON_EMAIL)) {
			pstmt.setString(1, u.getUsername());
			pstmt.setString(2, u.getPhonenumber());
			pstmt.setString(3, u.getAddress());
			pstmt.setString(4, u.getPassword());
			pstmt.setString(5, u.getEmail());
			status = pstmt.executeUpdate();
		} catch (Exception e) {
			logger.severe("Error updating user: " + e.getMessage());
		}
		return status;
	}

	/**
	 * Deletes a user from the database by their email.
	 * <p>
	 * This method prepares an SQL {@code DELETE} statement with a specified email and executes it to remove the corresponding {@link User} record.
	 * </p>
	 * 
	 * @param email the email address of the user to be deleted
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 */
	@Override
	public int deleteUser(String email) {
		int status = 0;
		try (PreparedStatement pstmt = con.prepareStatement(DELETE_ON_EMAIL)) {
			pstmt.setString(1, email);
			status = pstmt.executeUpdate();
		} catch (Exception e) {
			logger.severe("Error deleting user: " + e.getMessage());
		}
		return status;
	}

	/**
	 * Extracts a list of {@link User} objects from the provided {@link ResultSet}.
	 * <p>
	 * This method iterates over the {@link ResultSet} and creates a list of {@link User} objects from the data.
	 * </p>
	 * 
	 * @param resultSet the {@link ResultSet} object containing user data
	 * @return an {@link ArrayList} of {@link User} objects
	 */
	private ArrayList<User> extractUserListFromResultSet(ResultSet resultSet) {
		ArrayList<User> users = new ArrayList<>();
		try {
			while (resultSet.next()) {
				users.add(new User(
						resultSet.getInt("userid"),
						resultSet.getString("username"),
						resultSet.getString("email"),
						resultSet.getString("phonenumber"),
						resultSet.getString("address"),
						resultSet.getString("password")
						));
			}
		} catch (Exception e) {
			logger.severe("Error extracting users from result set: " + e.getMessage());
		}
		return users;
	}
}