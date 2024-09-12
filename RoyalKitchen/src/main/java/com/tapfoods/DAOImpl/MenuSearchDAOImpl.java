package com.tapfoods.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tapfoods.DAO.MenuSearchDAO;
import com.tapfoods.DBUtils.DBUtils;
import com.tapfoods.model.Menu;

/**
 * The {@code MenuSearchDAOImpl} class implements the {@link MenuSearchDAO} interface
 * and provides methods for searching menus in the database.
 */
public class MenuSearchDAOImpl implements MenuSearchDAO {
	private Connection con;
	/**
	 * Constructs a {@code MenuSearchDAOImpl} and establishes a connection to the database.
	 * 
	 * @throws SQLException if a database access error occurs
	 */
	public MenuSearchDAOImpl() throws SQLException {
		try {
			con = DBUtils.myConnect();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to connect to the database.", e);
		}
	}

	/**
	 * Retrieves menu items based on restaurant ID and a list of filters.
	 * <p>
	 * Constructs an SQL query dynamically based on the provided filters. Filters may include price ranges and availability.
	 * The query retrieves menu items for a specific restaurant and applies all the provided filters.
	 * </p>
	 * 
	 * @param restaurantId the ID of the restaurant whose menu items are to be retrieved
	 * @param filters a list of filters to apply to the search, such as price range and availability
	 * @return a list of {@link Menu} objects that match the search criteria
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public List<Menu> getMenusByRestaurantId(int restaurantId, List<String> filters) throws SQLException {
		List<Menu> menuItems = new ArrayList<>();
		if (filters.isEmpty()) {
			return menuItems;
		}

		StringBuilder sql = new StringBuilder("SELECT * FROM `menu` WHERE `restaurantid` = ?");
		List<String> conditions = new ArrayList<>();
		boolean hasAvailableFilter = false;

		// Construct query based on filters
		for (String filter : filters) {
			if (filter.equalsIgnoreCase("< Rs.150")) {
				conditions.add("price < 150");
			} else if (filter.equalsIgnoreCase("Rs.150-250")) {
				conditions.add("price BETWEEN 150 AND 250");
			} else if (filter.equalsIgnoreCase("Rs.250-350")) {
				conditions.add("price BETWEEN 250 AND 350");
			} else if (filter.equalsIgnoreCase("> Rs.350")) {
				conditions.add("price > 350");
			} else if (filter.equalsIgnoreCase("Available")) {
				hasAvailableFilter = true;  // Mark that we want to filter by availability
			} else {
				// General queries for menu name or description
				conditions.add("(LOWER(menuname) LIKE ? OR LOWER(description) LIKE ?)");
			}
		}

		// Combine the conditions with AND (since we need to apply all filters together)
		if (!conditions.isEmpty()) {
			sql.append(" AND (").append(String.join(" OR ", conditions)).append(")");
		}

		// If the available filter is selected, add it with an AND condition
		if (hasAvailableFilter) {
			sql.append(" AND LOWER(isavailable) = 'available'");
		}

		// Order results if necessary
		sql.append(" ORDER BY price");

		try (Connection conn = DBUtils.myConnect();
				PreparedStatement ps = conn.prepareStatement(sql.toString())) {

			ps.setInt(1, restaurantId);  // Set the restaurantId parameter
			int index = 2;  // Start index for other parameters

			// Setting parameters for text search filters (e.g., menu name or description)
			for (String filter : filters) {
				if (!filter.equalsIgnoreCase("< Rs.150") && 
						!filter.equalsIgnoreCase("Rs.150-250") && 
						!filter.equalsIgnoreCase("Rs.250-350") &&
						!filter.equalsIgnoreCase("> Rs.350") &&
						!filter.equalsIgnoreCase("Available")) {

					String filterPattern = "%" + filter.toLowerCase() + "%";
					ps.setString(index++, filterPattern);
					ps.setString(index++, filterPattern);
				}
			}

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Menu menu = new Menu();
					menu.setMenuid(rs.getInt("menuid"));
					menu.setMenuname(rs.getString("menuname"));
					menu.setDescription(rs.getString("description"));
					menu.setPrice(rs.getFloat("price"));
					menu.setImagepath(rs.getString("imagepath"));
					menu.setRestaurantid(rs.getInt("restaurantid"));
					menu.setIsavailable(rs.getString("isavailable"));
					menuItems.add(menu);
				}
			}
		}

		return menuItems;
	}

	/**
	 * Performs a global search for menu items based on a query string.
	 * <p>
	 * Searches across menu name, description, and price. The search is case-insensitive and matches partial strings.
	 * </p>
	 * 
	 * @param query the search query string
	 * @return a list of {@link Menu} objects that match the search criteria
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public List<Menu> globalSearch(String query) throws SQLException {
		List<Menu> menus = new ArrayList<>();
		String sql = "SELECT * FROM `menu` WHERE `menuname` LIKE ? OR `description` LIKE ? OR `price` LIKE ? OR `isavailable` LIKE ?";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			String searchQuery = "%" + query.toLowerCase() + "%";
			String availability = query.toLowerCase();
			stmt.setString(1, searchQuery);
			stmt.setString(2, searchQuery);
			stmt.setString(3, searchQuery);
			stmt.setString(4, availability);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Menu menu = new Menu(
							rs.getInt("menuid"),
							rs.getInt("restaurantid"),  // Include restaurantid here
							rs.getString("menuname"),
							rs.getFloat("price"),
							rs.getString("description"),
							rs.getString("isavailable"),
							rs.getString("imagepath")
							);
					menus.add(menu);
				}
			}
		}

		return menus;
	}
}