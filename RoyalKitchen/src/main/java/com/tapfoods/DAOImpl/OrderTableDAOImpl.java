package com.tapfoods.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.tapfoods.DAO.OrderTableDAO;
import com.tapfoods.DBUtils.DBUtils;
import com.tapfoods.model.Ordertable;

/**
 * The OrderTableDAOImpl class implements the {@link OrderTableDAO} interface.
 * <p>This class provides the actual implementation of data access operations for {@link Ordertable} entities using JDBC.</p>
 */
public class OrderTableDAOImpl implements OrderTableDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet resultSet;

	private static final String ADD_ORDER_TABLE = "INSERT INTO `ordertable` (`fk_restaurantid`, `fk_userid`, `totalamount`, `status`, `paymentmode`, `feedIndia`, `tips`, `platformFee`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_ORDER_TABLE = "SELECT * FROM `ordertable`";
	private static final String GET_ON_ID = "SELECT * FROM `ordertable` WHERE `orderid`=?";
	private static final String GET_ON_RESTAURANT_ID = "SELECT * FROM `ordertable` WHERE `fk_restaurantid`=?";
	private static final String UPDATE_ORDER_STATUS = "UPDATE `ordertable` SET `status`=? WHERE `orderid`=?";
	   
	/**
	 * Constructs a new {@code OrderTableDAOImpl} instance and establishes a database connection.
	 * <p>This constructor initializes the database connection using {@link DBUtils#myConnect()}.</p>
	 */
	public OrderTableDAOImpl() throws SQLException {
		try {
			con = DBUtils.myConnect();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to connect to the database", e);
		}
	}
	

    /**
     * Updates the status of an order in the database.
     * 
     * @param orderid the ID of the order to be updated
     * @param status the new status to be set
     * @throws SQLException if a database access error occurs
     */
	@Override
	public boolean updateOrderStatus(int orderid, String status) throws SQLException {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    boolean isUpdated = false;

	    try {
	        con = DBUtils.myConnect();
	        pstmt = con.prepareStatement(UPDATE_ORDER_STATUS);
	        pstmt.setString(1, status);
	        pstmt.setInt(2, orderid);
	        int rowsAffected = pstmt.executeUpdate();
	        isUpdated = rowsAffected > 0; // Return true if at least one row was affected
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new SQLException("Failed to update order status", e);
	    } finally {
	        DBUtils.closeResources(con, null, pstmt, null);
	    }

	    return isUpdated;
	}


	/**
	 * Adds a new order to the database and retrieves the generated order ID.
	 * <p>This method prepares an SQL {@code INSERT} statement and executes it to add an {@link Ordertable} object to the database.</p>
	 * 
	 * @param ot the {@link Ordertable} object to be added
	 * @return the generated order ID
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public int addOrderTable(Ordertable ot) throws SQLException {
		ResultSet generatedKeys = null;
		try {
			con = DBUtils.myConnect();
			pstmt = con.prepareStatement(ADD_ORDER_TABLE, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, ot.getFk_restaurantid());
			pstmt.setInt(2, ot.getFk_userid());
			pstmt.setFloat(3, ot.getTotalamount());
			pstmt.setString(4, ot.getStatus());
			pstmt.setString(5, ot.getPaymentmode());
			pstmt.setFloat(6, ot.getFeedIndia());
			pstmt.setFloat(7, ot.getTips());
			pstmt.setFloat(8, ot.getPlatformFee());
			pstmt.executeUpdate();

			// Retrieve generated order ID
			generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				return generatedKeys.getInt(1);
			} else {
				throw new SQLException("Failed to retrieve generated order ID.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Failed to add order table", e);
		} finally {
			DBUtils.closeResources(con, null, pstmt, generatedKeys);
		}
	}

	/**
	 * Retrieves all orders from the database.
	 * <p>This method executes an SQL {@code SELECT} statement to retrieve all {@link Ordertable} records and returns them as an {@link ArrayList}.</p>
	 * 
	 * @return an {@link ArrayList} of {@link Ordertable} objects representing all orders
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public ArrayList<Ordertable> getAllOrderTable() throws SQLException {
		ArrayList<Ordertable> ordertableList = new ArrayList<>();
		try {
			con = DBUtils.myConnect();
			stmt = con.createStatement();
			resultSet = stmt.executeQuery(GET_ALL_ORDER_TABLE);
			ordertableList = extractOrdertableListFromResultSet(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Failed to retrieve all order tables", e);
		} finally {
			DBUtils.closeResources(con, stmt, null, resultSet);
		}
		return ordertableList;
	}

	@Override
	public ArrayList<Ordertable> getOrderTableByRestaurantId(int restaurantid) throws SQLException {
		ArrayList<Ordertable> orderTableList = new ArrayList<>();
		try {
			pstmt = con.prepareStatement(GET_ON_RESTAURANT_ID);
			pstmt.setInt(1, restaurantid);
			resultSet = pstmt.executeQuery();
			orderTableList = extractOrdertableListFromResultSet(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Failed to retrieve order tablees by restaurant ID", e);
		} finally {
			DBUtils.closeResources(con, null, pstmt, resultSet);
		}
		return orderTableList;
	}

	/**
	 * Retrieves an order from the database by its ID.
	 * <p>This method prepares an SQL {@code SELECT} statement with a specified order ID and retrieves the corresponding {@link Ordertable} record.</p>
	 * 
	 * @param orderid the ID of the order to be retrieved
	 * @return the {@link Ordertable} object corresponding to the specified ID, or {@code null} if no order is found
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public Ordertable getOrderTable(int orderid) throws SQLException {
		try {
			con = DBUtils.myConnect();
			pstmt = con.prepareStatement(GET_ON_ID);
			pstmt.setInt(1, orderid);
			resultSet = pstmt.executeQuery();
			ArrayList<Ordertable> ordertableList = extractOrdertableListFromResultSet(resultSet);
			if (!ordertableList.isEmpty()) {
				return ordertableList.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Failed to retrieve order table by ID", e);
		} finally {
			DBUtils.closeResources(con, null, pstmt, resultSet);
		}
		return null;
	}

	/**
	 * Extracts a list of {@link Ordertable} objects from the provided {@link ResultSet}.
	 * <p>This method iterates over the {@link ResultSet} and creates a list of {@link Ordertable} objects from the data.</p>
	 * 
	 * @param resultSet the {@link ResultSet} object containing order data
	 * @return an {@link ArrayList} of {@link Ordertable} objects
	 * @throws SQLException if a database access error occurs
	 */
	private ArrayList<Ordertable> extractOrdertableListFromResultSet(ResultSet resultSet) throws SQLException {
		ArrayList<Ordertable> ordertables = new ArrayList<>();
		try {
			while (resultSet.next()) {
				ordertables.add(new Ordertable(
						resultSet.getInt("orderid"),
						resultSet.getInt("fk_restaurantid"),
						resultSet.getInt("fk_userid"),
						resultSet.getTimestamp("orderdate"),
						resultSet.getFloat("totalamount"),
						resultSet.getString("status"),
						resultSet.getString("paymentmode"),
						resultSet.getFloat("feedIndia"),
						resultSet.getFloat("tips"),
						resultSet.getFloat("platformFee")
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Failed to extract order tables from ResultSet", e);
		}
		return ordertables;
	}
}