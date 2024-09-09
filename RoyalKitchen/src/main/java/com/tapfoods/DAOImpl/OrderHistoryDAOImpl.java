package com.tapfoods.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import com.tapfoods.DAO.OrderHistoryDAO;
import com.tapfoods.DBUtils.DBUtils;
import com.tapfoods.model.OrderHistory;

/**
 * The OrderHistoryDAOImpl class implements the {@link OrderHistoryDAO} interface.
 * <p>This class provides the actual implementation of data access operations for {@link OrderHistory} entities using JDBC.</p>
 */
public class OrderHistoryDAOImpl implements OrderHistoryDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet resultSet;

	private static final String ADD_ORDER_HISTORY = "INSERT INTO `orderhistory` (`f_orderid`, `f_userid`, `totalamount`, `status`) VALUES (?, ?, ?, ?)";
	private static final String GET_ALL_ORDER_HISTORIES = "SELECT * FROM `orderhistory`";
	private static final String GET_ON_ID = "SELECT * FROM `orderhistory` WHERE `orderhistoryid`=?";
	private static final String GET_ORDER_HISTORY_BY_USER_ID = "SELECT * FROM `orderhistory` WHERE `f_userid`=?";

	private int status = 0;

	/**
	 * Constructs a new {@code OrderHistoryDAOImpl} instance and establishes a database connection.
	 * <p>This constructor initializes the database connection using {@link DBUtils#myConnect()}.</p>
	 */
	public OrderHistoryDAOImpl() throws SQLException {
		try {
			con = DBUtils.myConnect();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to connect to the database", e);
		}
	}

	/**
	 * Adds a new order history to the database.
	 * <p>This method prepares an SQL {@code INSERT} statement and executes it to add an {@link OrderHistory} object to the database.</p>
	 * 
	 * @param oh the {@link OrderHistory} object to be added
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public int addOrderHistory(OrderHistory oh) throws SQLException {
		try {
			con = DBUtils.myConnect();
			pstmt = con.prepareStatement(ADD_ORDER_HISTORY);
			pstmt.setInt(1, oh.getF_orderid());
			pstmt.setInt(2, oh.getF_userid());
			pstmt.setFloat(3, oh.getTotalamount());
			pstmt.setString(4, oh.getStatus());
			status = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Failed to add order history", e);
		} finally {
			DBUtils.closeResources(con, null, pstmt, null);
		}
		return status;
	}

	/**
	 * Retrieves all order histories from the database.
	 * <p>This method executes an SQL {@code SELECT} statement to retrieve all {@link OrderHistory} records and returns them as an {@link ArrayList}.</p>
	 * 
	 * @return an {@link ArrayList} of {@link OrderHistory} objects representing all order histories
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public ArrayList<OrderHistory> getAllOrderHistories() throws SQLException {
		ArrayList<OrderHistory> orderHistoryList = new ArrayList<>();
		try {
			con = DBUtils.myConnect();
			stmt = con.createStatement();
			resultSet = stmt.executeQuery(GET_ALL_ORDER_HISTORIES);
			orderHistoryList = extractOrderHistoryListFromResultSet(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Failed to retrieve all order histories", e);
		} finally {
			DBUtils.closeResources(con, stmt, null, resultSet);
		}
		return orderHistoryList;
	}

	/**
	 * Retrieves an order history from the database by its ID.
	 * <p>This method prepares an SQL {@code SELECT} statement with a specified ID and retrieves the corresponding {@link OrderHistory} record.</p>
	 * 
	 * @param orderhistoryid the ID of the order history to be retrieved
	 * @return the {@link OrderHistory} object corresponding to the specified ID, or {@code null} if no order history is found
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public OrderHistory getOrderHistory(int orderhistoryid) throws SQLException {
		try {
			con = DBUtils.myConnect();
			pstmt = con.prepareStatement(GET_ON_ID);
			pstmt.setInt(1, orderhistoryid);
			resultSet = pstmt.executeQuery();
			ArrayList<OrderHistory> orderHistoryList = extractOrderHistoryListFromResultSet(resultSet);
			if (!orderHistoryList.isEmpty()) {
				return orderHistoryList.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Failed to retrieve order history by ID", e);
		} finally {
			DBUtils.closeResources(con, null, pstmt, resultSet);
		}
		return null;
	}

	/**
	 * Retrieves all order histories for a specific user from the database.
	 * <p>This method prepares an SQL {@code SELECT} statement with the user ID and retrieves the corresponding {@link OrderHistory} records.</p>
	 * 
	 * @param userId the ID of the user for whom order histories are to be retrieved
	 * @return an {@link ArrayList} of {@link OrderHistory} objects representing all order histories for the user
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public ArrayList<OrderHistory> getOrderHistoryByUserId(int userId) throws SQLException {
		ArrayList<OrderHistory> orderHistoryList = new ArrayList<>();
		try {
			pstmt = con.prepareStatement(GET_ORDER_HISTORY_BY_USER_ID);
			pstmt.setInt(1, userId);
			resultSet = pstmt.executeQuery();
			orderHistoryList = extractOrderHistoryListFromResultSet(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Failed to retrieve order histories by user ID", e);
		} finally {
			DBUtils.closeResources(con, null, pstmt, resultSet);
		}
		return orderHistoryList;
	}

	/**
	 * Extracts a list of {@link OrderHistory} objects from the provided {@link ResultSet}.
	 * <p>This method iterates over the {@link ResultSet} and creates a list of {@link OrderHistory} objects from the data.</p>
	 * 
	 * @param resultSet the {@link ResultSet} object containing order history data
	 * @return an {@link ArrayList} of {@link OrderHistory} objects
	 * @throws SQLException if a database access error occurs
	 */
	private ArrayList<OrderHistory> extractOrderHistoryListFromResultSet(ResultSet resultSet) throws SQLException {
		ArrayList<OrderHistory> orderHistories = new ArrayList<>();
		try {
			while (resultSet.next()) {
				orderHistories.add(new OrderHistory(
						resultSet.getInt("orderhistoryid"),
						resultSet.getInt("f_orderid"),
						resultSet.getInt("f_userid"),
						resultSet.getTimestamp("orderdate"),
						resultSet.getFloat("totalamount"),
						resultSet.getString("status")
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Failed to extract order histories from ResultSet", e);
		}
		return orderHistories;
	}
}