package com.tapfoods.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	private Ordertable ordertable;

	private ArrayList<Ordertable> ordertableList = new ArrayList<Ordertable>();

	private static final String ADD_ORDER_TABLE = "INSERT INTO `ordertable` (`fk_restaurantid`, `fk_userid`, `totalamount`, `status`, `paymentmode`) VALUES (?, ?, ?, ?, ?)";
	private static final String GET_ALL_ORDER_TABLE = "SELECT * FROM `ordertable`";
	private static final String GET_ON_ID = "SELECT * FROM `ordertable` WHERE `orderid`=?";

	private int status = 0;

	/**
	 * Constructs a new {@code OrderTableDAOImpl} instance and establishes a database connection.
	 * <p>This constructor initializes the database connection using {@link DBUtils#myConnect()}.</p>
	 */
	public OrderTableDAOImpl() {
		try {
			con = DBUtils.myConnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a new order to the database.
	 * <p>This method prepares an SQL {@code INSERT} statement and executes it to add an {@link Ordertable} object to the database.</p>
	 * 
	 * @param ot the {@link Ordertable} object to be added
	 * @return an integer indicating the result of the operation (e.g., the number of rows affected)
	 */
	@Override
	public int addOrderTable(Ordertable ot) {
		try {
			pstmt = con.prepareStatement(ADD_ORDER_TABLE);
			pstmt.setInt(1, ot.getFk_restaurantid());
			pstmt.setInt(2, ot.getFk_userid());
			pstmt.setFloat(3, ot.getTotalamount());
			pstmt.setString(4, ot.getStatus());
			pstmt.setString(5, ot.getPaymentmode());
			status = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * Retrieves all orders from the database.
	 * <p>This method executes an SQL {@code SELECT} statement to retrieve all {@link Ordertable} records and returns them as an {@link ArrayList}.</p>
	 * 
	 * @return an {@link ArrayList} of {@link Ordertable} objects representing all orders
	 */
	@Override
	public ArrayList<Ordertable> getAllOrderTable() {
		try {
			stmt = con.createStatement();
			resultSet = stmt.executeQuery(GET_ALL_ORDER_TABLE);
			ordertableList = extractOrdertableListFromResultSet(resultSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ordertableList;
	}

	/**
	 * Retrieves an order from the database by its ID.
	 * <p>This method prepares an SQL {@code SELECT} statement with a specified order ID and retrieves the corresponding {@link Ordertable} record.</p>
	 * 
	 * @param orderid the ID of the order to be retrieved
	 * @return the {@link Ordertable} object corresponding to the specified ID, or {@code null} if no order is found
	 */
	@Override
	public Ordertable getOrderTable(int orderid) {
		try {
			pstmt = con.prepareStatement(GET_ON_ID);
			pstmt.setInt(1, orderid);
			resultSet = pstmt.executeQuery();
			ordertableList = extractOrdertableListFromResultSet(resultSet);
			if (!ordertableList.isEmpty()) {
				ordertable = ordertableList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ordertable;
	}

	/**
	 * Extracts a list of {@link Ordertable} objects from the provided {@link ResultSet}.
	 * <p>This method iterates over the {@link ResultSet} and creates a list of {@link Ordertable} objects from the data.</p>
	 * 
	 * @param resultSet the {@link ResultSet} object containing order data
	 * @return an {@link ArrayList} of {@link Ordertable} objects
	 */
	public ArrayList<Ordertable> extractOrdertableListFromResultSet(ResultSet resultSet) {
		ArrayList<Ordertable> ordertables = new ArrayList<>();
		try {
			while (resultSet.next()) {
				ordertables.add(new Ordertable(
						resultSet.getInt("orderid"),
						resultSet.getInt("fk_restaurantid"),
						resultSet.getInt("fk_userid"),
						resultSet.getFloat("totalamount"),
						resultSet.getString("status"),
						resultSet.getString("paymentmode")
						));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ordertables;
	}
}
