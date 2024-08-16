package com.tapfoods.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.tapfoods.DAO.AdminDAO;
import com.tapfoods.DBUtils.DBUtils;
import com.tapfoods.model.Admin;

/**
 * The AdminDAOImpl class implements the {@link AdminDAO} interface.
 * <p>This class provides the actual implementation of data access operations for {@link Admin} entities using JDBC.</p>
 */
public class AdminDAOImpl implements AdminDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet resultSet;

	private static final String ADD_ADMIN = "INSERT INTO `admin` (`adminkey`, `password`, `restaurantid_fk`) VALUES (?, ?, ?)";
	private static final String GET_ALL_ADMIN = "SELECT * FROM `admin`";
	private static final String GET_ON_KEY = "SELECT * FROM `admin` WHERE `adminkey`=?";
	private static final String UPDATE_ON_ID = "UPDATE `admin` SET `adminkey`=?, `password`=?, `restaurantid_fk`=? WHERE `adminid`=?";
	private static final String DELETE_ON_ID = "DELETE FROM `admin` WHERE `adminid`=?";
	private static final String CHECK_ADMINSKEY_EXISTS = "SELECT COUNT(*) FROM `admin` WHERE `adminkey`=?";

	private int status = 0;
	
	/**
	 * Constructs a new {@code AdminDAOImpl} instance and establishes a database connection.
	 * <p>This constructor initializes the database connection using {@link DBUtils#myConnect()}.</p>
	 */
	public AdminDAOImpl() throws SQLException {
		try {
			con = DBUtils.myConnect();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to connect to the database.", e);
		}
	}

	/**
	 * Adds a new admin to the database.
	 * <p>This method prepares an SQL {@code INSERT} statement and executes it to add an {@link Admin} object to the database.</p>
	 * <p>It also retrieves and returns the ID of the newly added admin.</p>
	 * 
	 * @param con the {@link Connection} object used for executing the SQL statement
	 * @param admin the {@link Admin} object containing the details to be added
	 * @return an integer representing the ID of the newly added admin
	 * @throws SQLException if a database access error occurs or if the SQL operation fails
	 */
	public int addAdmin(Connection con, Admin admin) throws SQLException {
		try {
			pstmt = con.prepareStatement(ADD_ADMIN, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, admin.getAdminkey());
			pstmt.setString(2, admin.getPassword());
			pstmt.setInt(3, admin.getRestaurantid_fk());

			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {
				resultSet = pstmt.getGeneratedKeys();
				if (resultSet.next()) {
					status = resultSet.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error while adding admin.", e);
		}
		return status;
	}

	/**
	 * Adds a new admin to the database.
	 * <p>This method prepares an SQL {@code INSERT} statement and executes it to add an {@link Admin} object to the database.</p>
	 * 
	 * @param admin the {@link Admin} object to be added
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 * @throws SQLException if a database access error occurs
	 */
	public int addAdmin(Admin admin) throws SQLException {
		try {
			con = DBUtils.myConnect();
			pstmt = con.prepareStatement(ADD_ADMIN, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, admin.getAdminkey());
			pstmt.setString(2, admin.getPassword());
			pstmt.setInt(3, admin.getRestaurantid_fk());

			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {
				resultSet = pstmt.getGeneratedKeys();
				if (resultSet.next()) {
					status = resultSet.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error while adding admin.", e);
		} finally {
			DBUtils.closeResources(null, null, pstmt, resultSet);
		}
		return status;
	}

	/**
	 * Retrieves all admins from the database.
	 * <p>This method executes an SQL {@code SELECT} statement to retrieve all {@link Admin} records and returns them as an {@link ArrayList}.</p>
	 * 
	 * @return an {@link ArrayList} of {@link Admin} objects representing all admins
	 */
	@Override
	public ArrayList<Admin> getAllAdmin() {
		ArrayList<Admin> adminList = new ArrayList<>();
		try {
			con = DBUtils.myConnect();
			stmt = con.createStatement();
			resultSet = stmt.executeQuery(GET_ALL_ADMIN);
			adminList = extractAdminListFromResultSet(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to retrieve all admins.", e);
		} finally {
			DBUtils.closeResources(null, stmt, null, resultSet);
		}
		return adminList;
	}

	/**
	 * Retrieves an admin from the database by their admin key.
	 * <p>This method prepares an SQL {@code SELECT} statement with a specified admin key and retrieves the corresponding {@link Admin} record.</p>
	 * 
	 * @param adminkey the admin key of the admin to be retrieved
	 * @return the {@link Admin} object corresponding to the specified admin key, or {@code null} if no admin is found
	 */
	@Override
	public Admin getAdmin(String adminkey) {
		try {
			con = DBUtils.myConnect();
			pstmt = con.prepareStatement(GET_ON_KEY);
			pstmt.setString(1, adminkey);
			resultSet = pstmt.executeQuery();
			ArrayList<Admin> adminList = extractAdminListFromResultSet(resultSet);
			if (!adminList.isEmpty()) {
				return adminList.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to retrieve admin by key.", e);
		} finally {
			DBUtils.closeResources(null, null, pstmt, resultSet);
		}
		return null;
	}

	/**
	 * Checks if an admin with the specified admin key already exists in the database.
	 * <p>This method prepares an SQL {@code SELECT} statement with the admin key and checks if any records match.</p>
	 * 
	 * @param adminkey the admin key to be checked
	 * @return {@code true} if an admin with the specified admin key exists, {@code false} otherwise
	 */
	@Override
	public boolean adminkeyExists(String adminkey) {
		boolean exists = false;
		try {
			con = DBUtils.myConnect();
			pstmt = con.prepareStatement(CHECK_ADMINSKEY_EXISTS);
			pstmt.setString(1, adminkey);
			resultSet = pstmt.executeQuery();
			if (resultSet.next() && resultSet.getInt(1) > 0) {
				exists = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to check if admin key exists.", e);
		} finally {
			DBUtils.closeResources(null, null, pstmt, resultSet);
		}
		return exists;
	}

	/**
	 * Updates an existing admin's information in the database.
	 * <p>This method prepares an SQL {@code UPDATE} statement with the new admin information and executes it to update the corresponding record.</p>
	 * 
	 * @param a the {@link Admin} object containing updated information
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 */
	@Override
	public int updateAdmin(Admin a) {
		try {
			con = DBUtils.myConnect();
			con.setAutoCommit(false); // Start transaction
			pstmt = con.prepareStatement(UPDATE_ON_ID);
			pstmt.setString(1, a.getAdminkey());
			pstmt.setString(2, a.getPassword());
			pstmt.setInt(3, a.getRestaurantid_fk());
			pstmt.setInt(4, a.getAdminid());
			status = pstmt.executeUpdate();
			con.commit(); // Commit transaction
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback(); // Rollback transaction on error
				} catch (SQLException rollbackEx) {
					rollbackEx.printStackTrace();
				}
			}
			e.printStackTrace();
			throw new RuntimeException("Failed to update admin.", e);
		} finally {
			DBUtils.closeResources(null, null, pstmt, null);
		}
		return status;
	}

	/**
	 * Deletes an admin from the database by their ID using an external connection.
	 * <p>This method prepares an SQL {@code DELETE} statement with a specified ID and executes it to remove the corresponding {@link Admin} record.</p>
	 * 
	 * @param adminid the ID of the admin to be deleted
	 * @param con the {@link Connection} to be used for the operation
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public int deleteAdmin(Connection con, int adminid) throws SQLException {
		try {
			pstmt = con.prepareStatement(DELETE_ON_ID);
			pstmt.setInt(1, adminid);
			status = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to delete admin.", e);
		}
		return status;
	}

	/**
	 * Deletes an admin from the database by their ID.
	 * <p>This method prepares an SQL {@code DELETE} statement with a specified ID and executes it to remove the corresponding {@link Admin} record.</p>
	 * 
	 * @param adminid the ID of the admin to be deleted
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public int deleteAdmin(int adminid) throws SQLException {
		try {
			con = DBUtils.myConnect();
			pstmt = con.prepareStatement(DELETE_ON_ID);
			pstmt.setInt(1, adminid);
			status = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to delete admin.", e);
		} finally {
			DBUtils.closeResources(null, null, pstmt, null);
		}
		return status;
	}

	/**
	 * Extracts a list of {@link Admin} objects from the provided {@link ResultSet}.
	 * <p>This method iterates over the {@link ResultSet} and creates a list of {@link Admin} objects from the data.</p>
	 * 
	 * @param resultSet the {@link ResultSet} object containing admin data
	 * @return an {@link ArrayList} of {@link Admin} objects
	 */
	public ArrayList<Admin> extractAdminListFromResultSet(ResultSet resultSet) {
		ArrayList<Admin> admins = new ArrayList<>();
		try {
			while (resultSet.next()) {
				admins.add(new Admin(
						resultSet.getInt("adminid"),
						resultSet.getString("adminkey"),
						resultSet.getString("password"),
						resultSet.getInt("restaurantid_fk")
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to extract admin list from result set.", e);
		}
		return admins;
	}
}