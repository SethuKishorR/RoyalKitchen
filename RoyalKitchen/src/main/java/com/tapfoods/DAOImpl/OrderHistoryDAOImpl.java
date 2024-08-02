package com.tapfoods.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
	private OrderHistory orderHistory;

	private ArrayList<OrderHistory> orderHistoryList = new ArrayList<OrderHistory>();

	private static final String ADD_ORDER_HISTORY = "INSERT INTO `orderhistory` (`f_orderid`, `f_userid`, `orderdate`, `totalamount`, `status`) VALUES (?, ?, ?, ?, ?)";
	private static final String GET_ALL_ORDER_HISTORIES = "SELECT * FROM `orderhistory`";
	private static final String GET_ON_ID = "SELECT * FROM `orderhistory` WHERE `orderhistoryid`=?";

	private int status = 0;

	/**
	 * Constructs a new {@code OrderHistoryDAOImpl} instance and establishes a database connection.
	 * <p>This constructor initializes the database connection using {@link DBUtils#myConnect()}.</p>
	 */
	public OrderHistoryDAOImpl() {
		try {
			con = DBUtils.myConnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a new order history to the database.
	 * <p>This method prepares an SQL {@code INSERT} statement and executes it to add an {@link OrderHistory} object to the database.</p>
	 * 
	 * @param oh the {@link OrderHistory} object to be added
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 */
	@Override
	public int addOrderHistory(OrderHistory oh) {
		try {
			pstmt = con.prepareStatement(ADD_ORDER_HISTORY);
			pstmt.setInt(1, oh.getF_orderid());
			pstmt.setInt(2, oh.getF_userid());
			pstmt.setDate(3, new java.sql.Date(oh.getOrderdate().getTime()));
			pstmt.setFloat(4, oh.getTotalamount());
			pstmt.setString(5, oh.getStatus());
			status = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * Retrieves all order histories from the database.
	 * <p>This method executes an SQL {@code SELECT} statement to retrieve all {@link OrderHistory} records and returns them as an {@link ArrayList}.</p>
	 * 
	 * @return an {@link ArrayList} of {@link OrderHistory} objects representing all order histories
	 */
	@Override
	public ArrayList<OrderHistory> getAllOrderHistories() {
		try {
			stmt = con.createStatement();
			resultSet = stmt.executeQuery(GET_ALL_ORDER_HISTORIES);
			orderHistoryList = extractOrderHistoryListFromResultSet(resultSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderHistoryList;
	}

	/**
	 * Retrieves an order history from the database by its ID.
	 * <p>This method prepares an SQL {@code SELECT} statement with a specified ID and retrieves the corresponding {@link OrderHistory} record.</p>
	 * 
	 * @param orderhistoryid the ID of the order history to be retrieved
	 * @return the {@link OrderHistory} object corresponding to the specified ID, or {@code null} if no order history is found
	 */
	@Override
	public OrderHistory getOrderHistory(int orderhistoryid) {
		try {
			pstmt = con.prepareStatement(GET_ON_ID);
			pstmt.setInt(1, orderhistoryid);
			resultSet = pstmt.executeQuery();
			orderHistoryList = extractOrderHistoryListFromResultSet(resultSet);
			if (!orderHistoryList.isEmpty()) {
				orderHistory = orderHistoryList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderHistory;
	}

	/**
	 * Extracts a list of {@link OrderHistory} objects from the provided {@link ResultSet}.
	 * <p>This method iterates over the {@link ResultSet} and creates a list of {@link OrderHistory} objects from the data.</p>
	 * 
	 * @param resultSet the {@link ResultSet} object containing order history data
	 * @return an {@link ArrayList} of {@link OrderHistory} objects
	 */
	public ArrayList<OrderHistory> extractOrderHistoryListFromResultSet(ResultSet resultSet) {
		ArrayList<OrderHistory> orderHistories = new ArrayList<>();
		try {
			while (resultSet.next()) {
				orderHistories.add(new OrderHistory(
						resultSet.getInt("orderhistoryid"),
						resultSet.getInt("f_orderid"),
						resultSet.getInt("f_userid"),
						resultSet.getDate("orderdate"),
						resultSet.getFloat("totalamount"),
						resultSet.getString("status")
						));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderHistories;
	}
}