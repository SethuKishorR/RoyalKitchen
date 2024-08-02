package com.tapfoods.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.tapfoods.DAO.OrderItemDAO;
import com.tapfoods.DBUtils.DBUtils;
import com.tapfoods.model.Orderitem;

/**
 * The OrderItemDAOImpl class implements the {@link OrderItemDAO} interface.
 * <p>This class provides the actual implementation of data access operations for {@link Orderitem} entities using JDBC.</p>
 */
public class OrderItemDAOImpl implements OrderItemDAO {
    private Connection con;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet resultSet;
    private Orderitem orderitem;

    private ArrayList<Orderitem> orderitemList = new ArrayList<Orderitem>();

    private static final String ADD_ORDER_ITEM = "INSERT INTO `orderitem` (`orderid`, `menuid`, `quantity`, `subtotal`) VALUES (?, ?, ?, ?)";
    private static final String GET_ALL_ORDER_ITEM = "SELECT * FROM `orderitem`";
    private static final String GET_ON_ID = "SELECT * FROM `orderitem` WHERE `orderitemid`=?";

    private int status = 0;

    /**
     * Constructs a new {@code OrderItemDAOImpl} instance and establishes a database connection.
     * <p>This constructor initializes the database connection using {@link DBUtils#myConnect()}.</p>
     */
    public OrderItemDAOImpl() {
        try {
            con = DBUtils.myConnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new order item to the database.
     * <p>This method prepares an SQL {@code INSERT} statement and executes it to add an {@link Orderitem} object to the database.</p>
     * 
     * @param oi the {@link Orderitem} object to be added
     * @return an integer indicating the result of the operation (e.g., the number of rows affected)
     */
    @Override
    public int addOrderItem(Orderitem oi) {
        try {
            pstmt = con.prepareStatement(ADD_ORDER_ITEM);
            pstmt.setInt(1, oi.getOrderid());
            pstmt.setInt(2, oi.getMenuid());
            pstmt.setInt(3, oi.getQuantity());
            pstmt.setFloat(4, oi.getSubtotal());
            status = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * Retrieves all order items from the database.
     * <p>This method executes an SQL {@code SELECT} statement to retrieve all {@link Orderitem} records and returns them as an {@link ArrayList}.</p>
     * 
     * @return an {@link ArrayList} of {@link Orderitem} objects representing all order items
     */
    @Override
    public ArrayList<Orderitem> getAllOrderitem() {
        try {
            stmt = con.createStatement();
            resultSet = stmt.executeQuery(GET_ALL_ORDER_ITEM);
            orderitemList = extractOrderitemListFromResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderitemList;
    }

    /**
     * Retrieves an order item from the database by its ID.
     * <p>This method prepares an SQL {@code SELECT} statement with a specified order item ID and retrieves the corresponding {@link Orderitem} record.</p>
     * 
     * @param orderitemid the ID of the order item to be retrieved
     * @return the {@link Orderitem} object corresponding to the specified ID, or {@code null} if no order item is found
     */
    @Override
    public Orderitem getOrderitem(int orderitemid) {
        try {
            pstmt = con.prepareStatement(GET_ON_ID);
            pstmt.setInt(1, orderitemid);
            resultSet = pstmt.executeQuery();
            orderitemList = extractOrderitemListFromResultSet(resultSet);
            if (!orderitemList.isEmpty()) {
                orderitem = orderitemList.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderitem;
    }

    /**
     * Extracts a list of {@link Orderitem} objects from the provided {@link ResultSet}.
     * <p>This method iterates over the {@link ResultSet} and creates a list of {@link Orderitem} objects from the data.</p>
     * 
     * @param resultSet the {@link ResultSet} object containing order item data
     * @return an {@link ArrayList} of {@link Orderitem} objects
     */
    public ArrayList<Orderitem> extractOrderitemListFromResultSet(ResultSet resultSet) {
        ArrayList<Orderitem> orderitems = new ArrayList<>();
        try {
            while (resultSet.next()) {
                orderitems.add(new Orderitem(
                        resultSet.getInt("orderitemid"),
                        resultSet.getInt("orderid"),
                        resultSet.getInt("menuid"),
                        resultSet.getInt("quantity"),
                        resultSet.getFloat("subtotal")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderitems;
    }
}
