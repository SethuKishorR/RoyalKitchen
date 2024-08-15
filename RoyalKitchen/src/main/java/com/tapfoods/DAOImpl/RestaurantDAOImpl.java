package com.tapfoods.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.tapfoods.DAO.RestaurantDAO;
import com.tapfoods.DBUtils.DBUtils;
import com.tapfoods.model.Restaurant;

/**
 * The RestaurantDAOImpl class implements the {@link RestaurantDAO} interface.
 * <p>This class provides the actual implementation of data access operations for {@link Restaurant} entities using JDBC.</p>
 */
public class RestaurantDAOImpl implements RestaurantDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet resultSet;
	private ArrayList<Restaurant> restaurantList = new ArrayList<>();

	private static final String CREATE_RESTAURANT = "INSERT INTO `restaurant` (`restaurantname`, `address`, `adminid_fk`) VALUES (?, ?, ?)";
	private static final String ADD_RESTAURANT = "INSERT INTO `restaurant` (`restaurantname`, `deliverytime`, `cuisinetype`, `address`, `ratings`, `isactive`, `imagepath`, `adminid_fk`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_RESTAURANT = "SELECT * FROM `restaurant`";
	private static final String GET_ON_ID = "SELECT * FROM `restaurant` WHERE `restaurantid`=?";
	private static final String UPDATE_ON_ID = "UPDATE `restaurant` SET `restaurantname`=?, `deliverytime`=?, `cuisinetype`=?, `address`=?, `ratings`=?, `isactive`=?, `imagepath`=?, `adminid_fk`=? WHERE `restaurantid`=?";
	private static final String DELETE_ON_ID = "DELETE FROM `restaurant` WHERE `restaurantid`=?";
	private static final String GET_LAST_ID = "SELECT LAST_INSERT_ID()";
	private static final String UPDATE_ADMINID = "UPDATE `restaurant` SET `adminid_fk` = ? WHERE `restaurantid` = ?";

	/**
	 * Constructs a new {@code RestaurantDAOImpl} instance and establishes a database connection.
	 * <p>This constructor initializes the database connection using {@link DBUtils#myConnect()}.</p>
	 */
	public RestaurantDAOImpl() {
		try {
			con = DBUtils.myConnect();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to connect to the database.", e);
		}
	}

	/**
	 * Creates a new restaurant entry in the database.
	 * <p>This method prepares an SQL {@code INSERT} statement and executes it to add a {@link Restaurant} object to the database.</p>
	 * 
	 * @param restaurant the {@link Restaurant} object to be added
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 */
	@Override
	public int createRestaurant(Connection con, Restaurant restaurant) throws SQLException {
		try (PreparedStatement ps = con.prepareStatement(CREATE_RESTAURANT, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, restaurant.getRestaurantname());
			ps.setString(2, restaurant.getAddress());

			// Handle null adminid_fk during initial creation
			if (restaurant.getAdminid_fk() != null) {
				ps.setInt(3, restaurant.getAdminid_fk());
			} else {
				ps.setNull(3, java.sql.Types.INTEGER);
			}

			// Execute the update
			int affectedRows = ps.executeUpdate();

			// Check if auto-generated key was returned
			if (affectedRows > 0) {
				try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						return generatedKeys.getInt(1); // Return the generated key
					} else {
						throw new SQLException("Failed to retrieve auto-generated key.");
					}
				}
			} else {
				throw new SQLException("No rows affected. Restaurant creation failed.");
			}
		}
	}

	/**
	 * Adds a new restaurant to the database.
	 * <p>This method prepares an SQL {@code INSERT} statement and executes it to add a {@link Restaurant} object to the database.</p>
	 * 
	 * @param r the {@link Restaurant} object to be added
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 */
	@Override
	public int addRestaurant(Restaurant r) {
		int status = 0;
		try {
			con.setAutoCommit(false); // Start transaction
			pstmt = con.prepareStatement(ADD_RESTAURANT);
			pstmt.setString(1, r.getRestaurantname());
			pstmt.setInt(2, r.getDeliverytime());
			pstmt.setString(3, r.getCuisinetype());
			pstmt.setString(4, r.getAddress());
			pstmt.setFloat(5, r.getRatings());
			pstmt.setString(6, r.getIsactive());
			pstmt.setString(7, r.getImagepath());
			pstmt.setInt(8, r.getAdminid_fk());
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
			throw new RuntimeException("Failed to add restaurant.", e);
		}
		return status;
	}

	/**
	 * Retrieves all restaurants from the database.
	 * <p>This method executes an SQL {@code SELECT} statement to retrieve all {@link Restaurant} records and returns them as an {@link ArrayList}.</p>
	 * 
	 * @return an {@link ArrayList} of {@link Restaurant} objects representing all restaurants
	 */
	@Override
	public ArrayList<Restaurant> getAllRestaurant() {
		try {
			stmt = con.createStatement();
			resultSet = stmt.executeQuery(GET_ALL_RESTAURANT);
			restaurantList = extractRestaurantListFromResultSet(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to retrieve all restaurants.", e);
		}
		return restaurantList;
	}

	/**
	 * Retrieves a restaurant from the database by its ID.
	 * <p>This method prepares an SQL {@code SELECT} statement with a specified ID and retrieves the corresponding {@link Restaurant} record.</p>
	 * 
	 * @param restaurantid the ID of the restaurant to be retrieved
	 * @return the {@link Restaurant} object corresponding to the specified ID, or {@code null} if no restaurant is found
	 */
	@Override
	public Restaurant getRestaurant(int restaurantid) {
		try {
			pstmt = con.prepareStatement(GET_ON_ID);
			pstmt.setInt(1, restaurantid);
			resultSet = pstmt.executeQuery();
			restaurantList = extractRestaurantListFromResultSet(resultSet);
			if (!restaurantList.isEmpty()) {
				return restaurantList.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to retrieve restaurant by ID.", e);
		}
		return null;
	}

	/**
	 * Updates an existing restaurant's information in the database.
	 * <p>This method prepares an SQL {@code UPDATE} statement with the new restaurant information and executes it to update the corresponding record.</p>
	 * 
	 * @param r the {@link Restaurant} object containing updated information
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 */
	@Override
	public int updateRestaurant(Restaurant r) {
		int status = 0;
		try {
			con.setAutoCommit(false); // Start transaction
			pstmt = con.prepareStatement(UPDATE_ON_ID);
			pstmt.setString(1, r.getRestaurantname());
			pstmt.setInt(2, r.getDeliverytime());
			pstmt.setString(3, r.getCuisinetype());
			pstmt.setString(4, r.getAddress());
			pstmt.setFloat(5, r.getRatings());
			pstmt.setString(6, r.getIsactive());
			pstmt.setString(7, r.getImagepath());
			pstmt.setInt(8, r.getAdminid_fk());
			pstmt.setInt(9, r.getRestaurantid());
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
			throw new RuntimeException("Failed to update restaurant.", e);
		}
		return status;
	}

	/**
	 * Deletes a restaurant from the database by its ID.
	 * <p>This method prepares an SQL {@code DELETE} statement with a specified ID and executes it to remove the corresponding {@link Restaurant} record.</p>
	 * 
	 * @param restaurantid the ID of the restaurant to be deleted
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 */
	@Override
	public int deleteRestaurant(int restaurantid, Connection con) throws SQLException {
		int status = 0;
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(DELETE_ON_ID);
			pstmt.setInt(1, restaurantid);
			status = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to delete restaurant.", e);
		} 
		return status;
	}

	/**
	 * Retrieves the ID of the last inserted restaurant from the database.
	 * <p>This method establishes a database connection, executes a query to obtain the last inserted ID, and returns it.</p>
	 * 
	 * @return the ID of the last inserted restaurant, or -1 if no ID is found
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public int getLastInsertedRestaurantId() throws SQLException {
		int lastInsertedId = -1; // Default value if no ID is found
		try (Connection con = DBUtils.myConnect();
				PreparedStatement stmt = con.prepareStatement(GET_LAST_ID);
				ResultSet rs = stmt.executeQuery()) {
			if (rs.next()) {
				lastInsertedId = rs.getInt(1); // Retrieve the last inserted ID
			} else {
				throw new SQLException("Failed to retrieve the last inserted restaurant ID.");
			}
		} catch (SQLException e) {
			// Log and handle the exception
			e.printStackTrace();
			throw new RuntimeException("Error while retrieving the last inserted restaurant ID.", e);
		}
		return lastInsertedId;
	}

	/**
	 * Extracts a list of {@link Restaurant} objects from the provided {@link ResultSet}.
	 * <p>This method iterates over the {@link ResultSet} and creates a list of {@link Restaurant} objects from the data.</p>
	 * 
	 * @param resultSet the {@link ResultSet} object containing restaurant data
	 * @return an {@link ArrayList} of {@link Restaurant} objects
	 */
	public ArrayList<Restaurant> extractRestaurantListFromResultSet(ResultSet resultSet) {
		ArrayList<Restaurant> restaurants = new ArrayList<>();
		try {
			while (resultSet.next()) {
				restaurants.add(new Restaurant(
						resultSet.getInt("restaurantid"),
						resultSet.getString("restaurantname"),
						resultSet.getInt("deliverytime"),
						resultSet.getString("cuisinetype"),
						resultSet.getString("address"),
						resultSet.getFloat("ratings"),
						resultSet.getString("isactive"),
						resultSet.getString("imagepath"),
						resultSet.getInt("adminid_fk")
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to extract restaurant list from result set.", e);
		}
		return restaurants;
	}

	/**
	 * Updates the `adminid_fk` for a specific restaurant in the database.
	 * <p>This method prepares an SQL {@code UPDATE} statement to modify the `adminid_fk` value for a given restaurant.</p>
	 * 
	 * @param con the {@link Connection} object used for database operations
	 * @param restaurant the {@link Restaurant} object containing the new `adminid_fk` and the restaurant ID
	 * @return {@code true} if the update was successful (i.e., one or more rows were affected), {@code false} otherwise
	 * @throws SQLException if a database access error occurs or the SQL statement fails
	 */
	public boolean updateRestaurantAdminId(Connection con, Restaurant restaurant) throws SQLException {
		try (PreparedStatement ps = con.prepareStatement(UPDATE_ADMINID)) {
			ps.setInt(1, restaurant.getAdminid_fk());
			ps.setInt(2, restaurant.getRestaurantid()); // Assumes `restaurantid` is available
			int affectedRows = ps.executeUpdate();
			return affectedRows > 0;
		}
	}
}
