package com.tapfoods.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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
	private Connection con;
	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet resultSet;

	private static final String ADD_USER = "INSERT INTO `user` (`username`, `email`, `phonenumber`, `address`, `password`) VALUES (?, ?, ?, ?, ?)";
	private static final String GET_ALL_USER = "SELECT * FROM `user`";
	private static final String GET_ON_EMAIL = "SELECT * FROM `user` WHERE `email`=?";
	private static final String UPDATE_ON_EMAIL = "UPDATE `user` SET `username`=?, `phonenumber`=?, `address`=?, `password`=? WHERE `email`=?";
	private static final String DELETE_ON_EMAIL = "DELETE FROM `user` WHERE `email`=?";
	private static final String CHECK_EMAIL_EXISTS = "SELECT 1 FROM `user` WHERE `email`=?";

	private int status = 0;

	/**
	 * Constructs a new {@code UserDAOImpl} instance and establishes a database connection.
	 * <p>
	 * This constructor initializes the database connection using {@link DBUtils#myConnect()}.
	 * </p>
	 * 
	 * @throws Exception if there is an error establishing the database connection
	 */
	public UserDAOImpl() throws Exception {
		try {
			con = DBUtils.myConnect();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed to connect to the database: " + e.getMessage(), e);
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
	 * @throws Exception if there is an error during the operation
	 */
	@Override
	public int addUser(User u) throws Exception {
		try {
			con = DBUtils.myConnect();
			pstmt = con.prepareStatement(ADD_USER);
			pstmt.setString(1, u.getUsername());
			pstmt.setString(2, u.getEmail());
			pstmt.setString(3, u.getPhonenumber());
			pstmt.setString(4, u.getAddress());
			pstmt.setString(5, u.getPassword());
			status = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error adding user: " + e.getMessage(), e);
		} finally {
			DBUtils.closeResources(con, null, pstmt, null);
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
	 * @throws Exception if there is an error during the operation
	 */
	@Override
	public ArrayList<User> getAllUser() throws Exception {
		ArrayList<User> userList = new ArrayList<>();
		try {
			con = DBUtils.myConnect();
			stmt = con.createStatement();
			resultSet = stmt.executeQuery(GET_ALL_USER);
			userList = extractUserListFromResultSet(resultSet);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error retrieving all users: " + e.getMessage(), e);
		} finally {
			DBUtils.closeResources(con, stmt, null, resultSet);
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
	 * @throws Exception if there is an error during the operation
	 */
	@Override
	public User getUser(String email) throws Exception {
		try {
			con = DBUtils.myConnect();
			pstmt = con.prepareStatement(GET_ON_EMAIL);
			pstmt.setString(1, email);
			resultSet = pstmt.executeQuery();
			ArrayList<User> userList = extractUserListFromResultSet(resultSet);
			if (!userList.isEmpty()) {
				return userList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error retrieving user by email: " + e.getMessage(), e);
		} finally {
			DBUtils.closeResources(con, null, pstmt, resultSet);
		}
		return null;
	}

	/**
	 * Checks if a user with the specified email already exists in the database.
	 * <p>
	 * This method prepares an SQL {@code SELECT} statement with a specified email and checks if a corresponding {@link User} record exists.
	 * </p>
	 * 
	 * @param email the email address to be checked
	 * @return {@code true} if the email exists, {@code false} otherwise
	 * @throws Exception if there is an error during the operation
	 */
	@Override
	public boolean emailExists(String email) throws Exception {
		boolean exists = false;
		try {
			con = DBUtils.myConnect();
			pstmt = con.prepareStatement(CHECK_EMAIL_EXISTS);
			pstmt.setString(1, email);
			resultSet = pstmt.executeQuery();
			exists = resultSet.next(); // If resultSet.next() is true, email exists
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error checking if email exists: " + e.getMessage(), e);
		} finally {
			DBUtils.closeResources(con, null, pstmt, resultSet);
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
	 * @throws Exception if there is an error during the operation
	 */
	@Override
	public int updateUser(User u) throws Exception {
		try {
			con = DBUtils.myConnect();
			pstmt = con.prepareStatement(UPDATE_ON_EMAIL);
			pstmt.setString(1, u.getUsername());
			pstmt.setString(2, u.getPhonenumber());
			pstmt.setString(3, u.getAddress());
			pstmt.setString(4, u.getPassword());
			pstmt.setString(5, u.getEmail());
			status = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error updating user: " + e.getMessage(), e);
		} finally {
			DBUtils.closeResources(con, null, pstmt, null);
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
	 * @throws Exception if there is an error during the operation
	 */
	@Override
	public int deleteUser(String email) throws Exception {
		try {
			con = DBUtils.myConnect();
			pstmt = con.prepareStatement(DELETE_ON_EMAIL);
			pstmt.setString(1, email);
			status = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error deleting user: " + e.getMessage(), e);
		} finally {
			DBUtils.closeResources(con, null, pstmt, null);
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
	 * @throws Exception if there is an error during the operation
	 */
	private ArrayList<User> extractUserListFromResultSet(ResultSet resultSet) throws Exception {
		ArrayList<User> userList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				User user = new User();
				user.setUserid(resultSet.getInt("userid"));
				user.setUsername(resultSet.getString("username"));
				user.setEmail(resultSet.getString("email"));
				user.setPhonenumber(resultSet.getString("phonenumber"));
				user.setAddress(resultSet.getString("address"));
				user.setPassword(resultSet.getString("password"));
				userList.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error extracting user list from ResultSet: " + e.getMessage(), e);
		}
		return userList;
	}
}