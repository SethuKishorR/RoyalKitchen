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
	private Restaurant restaurant;

	private ArrayList<Restaurant> restaurantList = new ArrayList<Restaurant>();

	private static final String ADD_RESTAURANT = "INSERT INTO `restaurant` (`restaurantname`, `deliverytime`, `cuisinetype`, `address`, `ratings`, `isactive`, `adminid`, `imagepath`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_RESTAURANT = "SELECT * FROM `restaurant`";
	private static final String GET_ON_ID = "SELECT * FROM `restaurant` WHERE `restaurantid`=?";
	private static final String UPDATE_ON_ID = "UPDATE `restaurant` SET `restaurantname`=?, `deliverytime`=?, `cuisinetype`=?, `address`=?, `ratings`=?, `isactive`=?, `adminid`=?, `imagepath`=? WHERE `restaurantid`=?";
	private static final String DELETE_ON_ID = "DELETE FROM `restaurant` WHERE `restaurantid`=?";

	private int status = 0;

	/**
	 * Constructs a new {@code RestaurantDAOImpl} instance and establishes a database connection.
	 * <p>This constructor initializes the database connection using {@link DBUtils#myConnect()}.</p>
	 */
	public RestaurantDAOImpl() {
		try {
			con = DBUtils.myConnect();
		} catch (Exception e) {
			e.printStackTrace();
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
		try {
			pstmt = con.prepareStatement(ADD_RESTAURANT);
			pstmt.setString(1, r.getRestaurantname());
			pstmt.setInt(2, r.getDeliverytime());
			pstmt.setString(3, r.getCuisinetype());
			pstmt.setString(4, r.getAddress());
			pstmt.setFloat(5, r.getRatings());
			pstmt.setString(6, r.getIsactive());
			pstmt.setInt(7, r.getAdminid());
			pstmt.setString(8, r.getImagepath());
			status = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
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
		} catch (Exception e) {
			e.printStackTrace();
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
				restaurant = restaurantList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return restaurant;
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
		try {
			pstmt = con.prepareStatement(UPDATE_ON_ID);
			pstmt.setString(1, r.getRestaurantname());
			pstmt.setInt(2, r.getDeliverytime());
			pstmt.setString(3, r.getCuisinetype());
			pstmt.setString(4, r.getAddress());
			pstmt.setFloat(5, r.getRatings());
			pstmt.setString(6, r.getIsactive());
			pstmt.setInt(7, r.getAdminid());
			pstmt.setString(8, r.getImagepath());
			pstmt.setInt(9, r.getRestaurantid());
			status = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
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
	public int deleteRestaurant(int restaurantid) {
		try {
			pstmt = con.prepareStatement(DELETE_ON_ID);
			pstmt.setInt(1, restaurantid);
			status = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
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
						resultSet.getInt("adminid"),
						resultSet.getString("imagepath")
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return restaurants;
	}
}