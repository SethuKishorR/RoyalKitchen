package com.tapfoods.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.tapfoods.DAO.MenuDAO;
import com.tapfoods.DBUtils.DBUtils;
import com.tapfoods.model.Menu;

/**
 * The MenuDAOImpl class implements the {@link MenuDAO} interface.
 * <p>This class provides the actual implementation of data access operations for {@link Menu} entities using JDBC.</p>
 */
public class MenuDAOImpl implements MenuDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet resultSet;

	private static final String ADD_MENU = "INSERT INTO `menu` (`restaurantid`, `menuname`, `price`, `description`, `isavailable`, `imagepath`) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_MENU = "SELECT * FROM `menu`";
	private static final String GET_ON_ID = "SELECT * FROM `menu` WHERE `menuid`=?";
	private static final String UPDATE_ON_ID = "UPDATE `menu` SET `restaurantid`=?, `menuname`=?, `price`=?, `description`=?, `isavailable`=?, `imagepath`=? WHERE `menuid`=?";
	private static final String DELETE_ON_ID = "DELETE FROM `menu` WHERE `menuid`=?";

	private int status = 0;

	/**
	 * Constructs a new {@code MenuDAOImpl} instance and establishes a database connection.
	 * <p>This constructor initializes the database connection using {@link DBUtils#myConnect()}.</p>
	 */
	public MenuDAOImpl() throws SQLException {
		try {
			con = DBUtils.myConnect();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error establishing database connection.", e);
		}
	}

	/**
	 * Adds a new menu to the database.
	 * <p>This method prepares an SQL {@code INSERT} statement and executes it to add a {@link Menu} object to the database.</p>
	 * 
	 * @param m the {@link Menu} object to be added
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 * @throws SQLException if a database access error occurs or the SQL statement fails
	 */
	@Override
	public int addMenu(Menu m) throws SQLException {
		try {
			con = DBUtils.myConnect();
			pstmt = con.prepareStatement(ADD_MENU);
			pstmt.setInt(1, m.getRestaurantid());
			pstmt.setString(2, m.getMenuname());
			pstmt.setFloat(3, m.getPrice());
			pstmt.setString(4, m.getDescription());
			pstmt.setString(5, m.getIsavailable());
			pstmt.setString(6, m.getImagepath());
			status = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Error adding menu: " + e.getMessage(), e);
		} finally {
			DBUtils.closeResources(null, null, pstmt, null);
		}
		return status;
	}

	/**
	 * Retrieves all menus from the database.
	 * <p>This method executes an SQL {@code SELECT} statement to retrieve all {@link Menu} records and returns them as an {@link ArrayList}.</p>
	 * 
	 * @return an {@link ArrayList} of {@link Menu} objects representing all menus
	 * @throws SQLException if a database access error occurs or the SQL statement fails
	 */
	@Override
	public ArrayList<Menu> getAllMenu() throws SQLException {
		ArrayList<Menu> menuList = new ArrayList<Menu>();
		try {
			con = DBUtils.myConnect();
			stmt = con.createStatement();
			resultSet = stmt.executeQuery(GET_ALL_MENU);
			menuList = extractMenuListFromResultSet(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Error retrieving all menus: " + e.getMessage(), e);
		} finally {
			DBUtils.closeResources(null, stmt, null, resultSet);
		}
		return menuList;
	}

	/**
	 * Retrieves a menu from the database by its ID.
	 * <p>This method prepares an SQL {@code SELECT} statement with a specified menu ID and retrieves the corresponding {@link Menu} record.</p>
	 * 
	 * @param menuid the ID of the menu to be retrieved
	 * @return the {@link Menu} object corresponding to the specified ID, or {@code null} if no menu is found
	 * @throws SQLException if a database access error occurs or the SQL statement fails
	 */
	@Override
	public Menu getMenu(int menuid) throws SQLException {
		try {
			con = DBUtils.myConnect();
			pstmt = con.prepareStatement(GET_ON_ID);
			pstmt.setInt(1, menuid);
			resultSet = pstmt.executeQuery();
			ArrayList<Menu> menuList = extractMenuListFromResultSet(resultSet);
			if (!menuList.isEmpty()) {
				return menuList.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Error retrieving menu with ID " + menuid + ": " + e.getMessage(), e);
		} finally {
			DBUtils.closeResources(null, pstmt, null, resultSet);
		}
		return null;
	}

	/**
	 * Updates an existing menu's information in the database.
	 * <p>This method prepares an SQL {@code UPDATE} statement with the new menu information and executes it to update the corresponding record.</p>
	 * 
	 * @param m the {@link Menu} object containing updated information
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 * @throws SQLException if a database access error occurs or the SQL statement fails
	 */
	@Override
	public int updateMenu(Menu m) throws SQLException {
		try {
			con = DBUtils.myConnect();
			pstmt = con.prepareStatement(UPDATE_ON_ID);
			pstmt.setInt(1, m.getRestaurantid());
			pstmt.setString(2, m.getMenuname());
			pstmt.setFloat(3, m.getPrice());
			pstmt.setString(4, m.getDescription());
			pstmt.setString(5, m.getIsavailable());
			pstmt.setString(6, m.getImagepath());
			pstmt.setInt(7, m.getMenuid());
			status = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Error updating menu with ID " + m.getMenuid() + ": " + e.getMessage(), e);
		} finally {
			DBUtils.closeResources(null, null, pstmt, null);
		}
		return status;
	}

	/**
	 * Deletes a menu from the database by its ID.
	 * <p>This method prepares an SQL {@code DELETE} statement with a specified menu ID and executes it to remove the corresponding {@link Menu} record.</p>
	 * 
	 * @param menuid the ID of the menu to be deleted
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 * @throws SQLException if a database access error occurs or the SQL statement fails
	 */
	@Override
	public int deleteMenu(int menuid) throws SQLException {
		try {
			con = DBUtils.myConnect();
			pstmt = con.prepareStatement(DELETE_ON_ID);
			pstmt.setInt(1, menuid);
			status = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Error deleting menu with ID " + menuid + ": " + e.getMessage(), e);
		} finally {
			DBUtils.closeResources(null, null, pstmt, null);
		}
		return status;
	}

	/**
	 * Extracts a list of {@link Menu} objects from the provided {@link ResultSet}.
	 * <p>This method iterates over the {@link ResultSet} and creates a list of {@link Menu} objects from the data.</p>
	 * 
	 * @param resultSet the {@link ResultSet} object containing menu data
	 * @return an {@link ArrayList} of {@link Menu} objects
	 * @throws SQLException if a database access error occurs or the SQL statement fails
	 */
	public ArrayList<Menu> extractMenuListFromResultSet(ResultSet resultSet) throws SQLException {
		ArrayList<Menu> menus = new ArrayList<>();
		try {
			while (resultSet.next()) {
				menus.add(new Menu(
						resultSet.getInt("menuid"),
						resultSet.getInt("restaurantid"),
						resultSet.getString("menuname"),
						resultSet.getFloat("price"),
						resultSet.getString("description"),
						resultSet.getString("isavailable"),
						resultSet.getString("imagepath")
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Error extracting menu list from result set: " + e.getMessage(), e);
		}
		return menus;
	}
}