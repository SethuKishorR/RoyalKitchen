package com.tapfoods.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * The OrderHistory class represents the history of an order.
 * <p>This class includes details about the order, such as the order ID, user ID, order date, total amount, and status.</p>
 */
public class OrderHistory {
	private int orderhistoryid;
	private int f_orderid;
	private int f_userid;
	private Timestamp orderdate;
	private float totalamount;
	private String status;

	/**
	 * Default constructor.
	 */
	public OrderHistory() {
		super();
	}
	public OrderHistory( int f_orderid, int f_userid, float totalamount, String status) {
		super();
		this.f_orderid = f_orderid;
		this.f_userid = f_userid;
		this.totalamount = totalamount;
		this.status = status;
	}

	/**
	 * Parameterized constructor.
	 * 
	 * <p>
	 * This constructor initializes the OrderHistory object with the specified values.
	 * </p>
	 * 
	 * @param orderhistoryid the ID of the order history
	 * @param f_orderid the ID of the order
	 * @param f_userid the ID of the user
	 * @param orderdate the date of the order
	 * @param totalamount the total amount of the order
	 * @param status the status of the order
	 */
	public OrderHistory(int orderhistoryid, int f_orderid, int f_userid, Timestamp orderdate, float totalamount, String status) {
		super();
		this.orderhistoryid = orderhistoryid;
		this.f_orderid = f_orderid;
		this.f_userid = f_userid;
		this.orderdate = orderdate;
		this.totalamount = totalamount;
		this.status = status;
	}

	/**
	 * Gets the order history ID.
	 * 
	 * <p>
	 * This method returns the unique identifier for the order history.
	 * </p>
	 * 
	 * @return the order history ID
	 */
	public int getOrderhistoryid() {
		return orderhistoryid;
	}

	/**
	 * Sets the order history ID.
	 * 
	 * <p>
	 * This method sets the unique identifier for the order history.
	 * </p>
	 * 
	 * @param orderhistoryid the order history ID to set
	 */
	public void setOrderhistoryid(int orderhistoryid) {
		this.orderhistoryid = orderhistoryid;
	}

	/**
	 * Gets the order ID.
	 * 
	 * <p>
	 * This method returns the unique identifier for the order.
	 * </p>
	 * 
	 * @return the order ID
	 */
	public int getF_orderid() {
		return f_orderid;
	}

	/**
	 * Sets the order ID.
	 * 
	 * <p>
	 * This method sets the unique identifier for the order.
	 * </p>
	 * 
	 * @param f_orderid the order ID to set
	 */
	public void setF_orderid(int f_orderid) {
		this.f_orderid = f_orderid;
	}

	/**
	 * Gets the user ID.
	 * 
	 * <p>
	 * This method returns the unique identifier for the user.
	 * </p>
	 * 
	 * @return the user ID
	 */
	public int getF_userid() {
		return f_userid;
	}

	/**
	 * Sets the user ID.
	 * 
	 * <p>
	 * This method sets the unique identifier for the user.
	 * </p>
	 * 
	 * @param f_userid the user ID to set
	 */
	public void setF_userid(int f_userid) {
		this.f_userid = f_userid;
	}

	/**
	 * Gets the order date.
	 * 
	 * <p>
	 * This method returns the date when the order was placed.
	 * </p>
	 * 
	 * @return the order date
	 */
	public java.sql.Timestamp getOrderdate() {
		return orderdate;
	}

	/**
	 * Gets the total amount of the order.
	 * 
	 * <p>
	 * This method returns the total amount of the order.
	 * </p>
	 * 
	 * @return the total amount of the order
	 */
	public float getTotalamount() {
		return totalamount;
	}

	/**
	 * Sets the total amount of the order.
	 * 
	 * <p>
	 * This method sets the total amount of the order.
	 * </p>
	 * 
	 * @param totalamount the total amount to set
	 */
	public void setTotalamount(float totalamount) {
		this.totalamount = totalamount;
	}

	/**
	 * Gets the status of the order.
	 * 
	 * <p>
	 * This method returns the current status of the order.
	 * </p>
	 * 
	 * @return the status of the order
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status of the order.
	 * 
	 * <p>
	 * This method sets the current status of the order.
	 * </p>
	 * 
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Returns a string representation of the order history object.
	 * 
	 * <p>
	 * This method provides a string representation of the OrderHistory object, including its attributes.
	 * </p>
	 * 
	 * @return a string representation of the order history object
	 */
	@Override
	public String toString() {
		return "OrderHistory [orderhistoryid=" + orderhistoryid + ", f_orderid=" + f_orderid + ", f_userid=" + f_userid
				+ ", orderdate=" + orderdate + ", totalamount=" + totalamount + ", status=" + status + "]";
	}
}