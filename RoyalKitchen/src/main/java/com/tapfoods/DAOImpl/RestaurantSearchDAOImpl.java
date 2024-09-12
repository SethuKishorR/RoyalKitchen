package com.tapfoods.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tapfoods.DAO.RestaurantSearchDAO;
import com.tapfoods.DBUtils.DBUtils;
import com.tapfoods.model.Restaurant;

/**
 * The {@code RestaurantSearchDAOImpl} class implements the {@link RestaurantSearchDAO} interface
 * and provides methods for searching restaurants in the database.
 */
public class RestaurantSearchDAOImpl implements RestaurantSearchDAO {
	private Connection con;
	/**
	 * Constructs a {@code RestaurantSearchDAOImpl} and establishes a connection to the database.
	 * 
	 * @throws SQLException if a database access error occurs
	 */
	public RestaurantSearchDAOImpl() throws SQLException {
		try {
			con = DBUtils.myConnect();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to connect to the database.", e);
		}
	}
	/**
	 * Retrieves restaurants based on a list of queries.
	 * <p>
	 * Constructs an SQL query dynamically based on the provided queries. Queries may include delivery time ranges and 
	 * restaurant activity status. The query retrieves restaurants that match the specified criteria.
	 * </p>
	 * 
	 * @param queries a list of queries to apply to the search, such as delivery time ranges and activity status
	 * @return a list of {@link Restaurant} objects that match the search criteria
	 * @throws SQLException if a database access error occurs
	 */
	public List<Restaurant> searchRestaurants(List<String> queries) throws SQLException {
		List<Restaurant> restaurants = new ArrayList<>();
		if (queries.isEmpty()) {
			return restaurants;
		}

		StringBuilder sql = new StringBuilder("SELECT * FROM `restaurant` WHERE ");
		List<String> conditions = new ArrayList<>();
		boolean hasActiveFilter = false;

		for (String query : queries) {
			if (query.equalsIgnoreCase("< 20mins")) {
				conditions.add("deliverytime < 20");
			} else if (query.equalsIgnoreCase("20-30mins")) {
				conditions.add("deliverytime BETWEEN 20 AND 30");
			} else if (query.equalsIgnoreCase("30-40mins")) {
				conditions.add("deliverytime BETWEEN 30 AND 40");
			} else if (query.equalsIgnoreCase("40-50mins")) {
				conditions.add("deliverytime BETWEEN 40 AND 50");
			} else if (query.equalsIgnoreCase("> 50mins")) {
				conditions.add("deliverytime > 50");
			} else if (query.equalsIgnoreCase("active")) {
				hasActiveFilter = true;  // Mark that we want to filter by active status
			} else {
				// General queries for restaurant name or cuisine type
				conditions.add("(LOWER(restaurantname) LIKE ? OR LOWER(cuisinetype) LIKE ?)");
			}
		}

		// Combine the conditions with OR
		if (!conditions.isEmpty()) {
			sql.append("(").append(String.join(" OR ", conditions)).append(")");
		}

		// If the active filter is selected, add it with an AND condition
		if (hasActiveFilter) {
			if (!conditions.isEmpty()) {
				sql.append(" AND ");
			}
			sql.append("LOWER(isactive) = 'active'");
		}

		sql.append(" ORDER BY deliverytime");

		try (PreparedStatement ps = con.prepareStatement(sql.toString())) {
			int index = 1;
			for (String query : queries) {
				if (!query.equalsIgnoreCase("< 20mins") && 
						!query.equalsIgnoreCase("20-30mins") && 
						!query.equalsIgnoreCase("30-40mins") && 
						!query.equalsIgnoreCase("40-50mins") && 
						!query.equalsIgnoreCase("> 50mins") &&
						!query.equalsIgnoreCase("active")) {  // Skip setting parameter for 'active'
					String queryPattern = "%" + query.toLowerCase() + "%";
					// Set parameters for restaurant name and cuisine type
					ps.setString(index++, queryPattern);
					ps.setString(index++, queryPattern);
				}
			}

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Restaurant restaurant = new Restaurant(
							rs.getInt("restaurantid"),
							rs.getString("restaurantname"),
							rs.getInt("deliverytime"),
							rs.getString("cuisinetype"),
							rs.getString("address"),
							rs.getFloat("ratings"),
							rs.getString("isactive"),
							rs.getString("imagepath"),
							rs.getInt("adminid_fk")
							);
					restaurants.add(restaurant);
				}
			}
		}

		return restaurants;
	}
	/**
	 * Performs a global search for restaurants based on a query string.
	 * <p>
	 * Searches across restaurant name, cuisine type, address, and delivery time. The search is case-insensitive and matches partial strings.
	 * </p>
	 * 
	 * @param query the search query string
	 * @return a list of {@link Restaurant} objects that match the search criteria
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public List<Restaurant> globalSearch(String query) throws SQLException {
		List<Restaurant> restaurants = new ArrayList<>();
		String sql = "SELECT * FROM `restaurant` WHERE `restaurantname` LIKE ? OR `cuisinetype` LIKE ? OR `address` LIKE ? OR `deliverytime` LIKE ? OR `isactive` LIKE ?";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			String searchQuery = "%" + query.toLowerCase() + "%";
			String availability = query.toLowerCase();
			stmt.setString(1, searchQuery);
			stmt.setString(2, searchQuery);
			stmt.setString(3, searchQuery);
			stmt.setString(4, searchQuery);
			stmt.setString(5, availability);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Restaurant restaurant = new Restaurant(
							rs.getInt("restaurantid"),
							rs.getString("restaurantname"),
							rs.getInt("deliverytime"),
							rs.getString("cuisinetype"),
							rs.getString("address"),
							rs.getFloat("ratings"),
							rs.getString("isactive"),
							rs.getString("imagepath"),
							rs.getInt("adminid_fk")
							);
					restaurants.add(restaurant);
				}
			}
		}

		return restaurants;
	}
}