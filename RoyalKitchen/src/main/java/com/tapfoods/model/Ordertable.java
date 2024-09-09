package com.tapfoods.model;

import java.sql.Timestamp;

/**
 * The Ordertable class represents an order entity with attributes such as order ID,
 * foreign key for restaurant ID, foreign key for user ID, total amount, status, and payment mode.
 */
public class Ordertable {
	private int orderid;
	private int fk_restaurantid;
	private int fk_userid;
	private Timestamp orderdate;
	private float totalamount;
	private String status;
	private String paymentmode;
	private float feedIndia;
	private float tips;
	private float platformFee;

	/**
	 * Default constructor.
	 */
	public Ordertable() {
		super();
	}

	/**
	 * Parameterized constructor.
	 * 
	 * <p>
	 * This constructor initializes the Ordertable object with the specified values.
	 * </p>
	 * 
	 * @param orderid the order ID
	 * @param fk_restaurantid the foreign key for restaurant ID
	 * @param fk_userid the foreign key for user ID
	 * @param orderdate the date and time when the order was placed
	 * @param totalamount the total amount
	 * @param status the status of the order
	 * @param paymentmode the payment mode
	 * @param feedIndia the feed for India
	 * @param tips the tips given
	 * @param platformFee the platform fee
	 */
	public Ordertable(int orderid, int fk_restaurantid, int fk_userid, Timestamp orderdate, float totalamount, String status,
			String paymentmode, float feedIndia, float tips, float platformFee) {
		super();
		this.orderid = orderid;
		this.fk_restaurantid = fk_restaurantid;
		this.fk_userid = fk_userid;
		this.orderdate = orderdate;
		this.totalamount = totalamount;
		this.status = status;
		this.paymentmode = paymentmode;
		this.feedIndia = feedIndia;
		this.tips = tips;
		this.platformFee = platformFee;
	}

	/**
	 * Parameterized constructor without order ID.
	 * 
	 * <p>
	 * This constructor initializes the Ordertable object with the specified values, except for the order ID.
	 * </p>
	 * 
	 * @param fk_restaurantid the foreign key for restaurant ID
	 * @param fk_userid the foreign key for user ID
	 * @param totalamount the total amount
	 * @param status the status of the order
	 * @param paymentmode the payment mode
	 * @param feedIndia the feed for India
	 * @param tips the tips given
	 * @param platformFee the platform fee
	 */
	public Ordertable(int fk_restaurantid, int fk_userid, float totalamount, String status, String paymentmode, float feedIndia, float tips, float platformFee) {
		super();
		this.fk_restaurantid = fk_restaurantid;
		this.fk_userid = fk_userid;
		this.totalamount = totalamount;
		this.status = status;
		this.paymentmode = paymentmode;
		this.feedIndia = feedIndia;
		this.tips = tips;
		this.platformFee = platformFee;
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
	public int getOrderid() {
		return orderid;
	}

	/**
	 * Sets the order ID.
	 * 
	 * <p>
	 * This method sets the unique identifier for the order.
	 * </p>
	 * 
	 * @param orderid the order ID to set
	 */
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	/**
	 * Gets the foreign key for restaurant ID.
	 * 
	 * <p>
	 * This method returns the foreign key for the restaurant associated with the order.
	 * </p>
	 * 
	 * @return the foreign key for restaurant ID
	 */
	public int getFk_restaurantid() {
		return fk_restaurantid;
	}

	/**
	 * Sets the foreign key for restaurant ID.
	 * 
	 * <p>
	 * This method sets the foreign key for the restaurant associated with the order.
	 * </p>
	 * 
	 * @param fk_restaurantid the foreign key for restaurant ID to set
	 */
	public void setFk_restaurantid(int fk_restaurantid) {
		this.fk_restaurantid = fk_restaurantid;
	}

	/**
	 * Gets the foreign key for user ID.
	 * 
	 * <p>
	 * This method returns the foreign key for the user associated with the order.
	 * </p>
	 * 
	 * @return the foreign key for user ID
	 */
	public int getFk_userid() {
		return fk_userid;
	}

	/**
	 * Sets the foreign key for user ID.
	 * 
	 * <p>
	 * This method sets the foreign key for the user associated with the order.
	 * </p>
	 * 
	 * @param fk_userid the foreign key for user ID to set
	 */
	public void setFk_userid(int fk_userid) {
		this.fk_userid = fk_userid;
	}

	public Timestamp getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Timestamp orderdate) {
		this.orderdate = orderdate;
	}

	/**
	 * Gets the total amount.
	 * 
	 * <p>
	 * This method returns the total amount of the order.
	 * </p>
	 * 
	 * @return the total amount
	 */
	public float getTotalamount() {
		return totalamount;
	}

	/**
	 * Sets the total amount.
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
	 * Gets the payment mode.
	 * 
	 * <p>
	 * This method returns the payment mode used for the order.
	 * </p>
	 * 
	 * @return the payment mode
	 */
	public String getPaymentmode() {
		return paymentmode;
	}

	/**
	 * Sets the payment mode.
	 * 
	 * <p>
	 * This method sets the payment mode used for the order.
	 * </p>
	 * 
	 * @param paymentmode the payment mode to set
	 */
	public void setPaymentmode(String paymentmode) {
		this.paymentmode = paymentmode;
	}

	/**
	 * Gets the feed for India.
	 * 
	 * <p>
	 * This method returns the feed amount for India associated with the order.
	 * </p>
	 * 
	 * @return the feed for India
	 */
	public float getFeedIndia() {
		return feedIndia;
	}

	/**
	 * Sets the feed for India.
	 * 
	 * <p>
	 * This method sets the feed amount for India associated with the order.
	 * </p>
	 * 
	 * @param feedIndia the feed amount to set
	 */
	public void setFeedIndia(int feedIndia) {
		this.feedIndia = feedIndia;
	}

	/**
	 * Gets the tips given.
	 * 
	 * <p>
	 * This method returns the tips given with the order.
	 * </p>
	 * 
	 * @return the tips given
	 */
	public float getTips() {
		return tips;
	}

	/**
	 * Sets the tips given.
	 * 
	 * <p>
	 * This method sets the tips given with the order.
	 * </p>
	 * 
	 * @param tips the tips to set
	 */
	public void setTips(int tips) {
		this.tips = tips;
	}

	/**
	 * Gets the platform fee.
	 * 
	 * <p>
	 * This method returns the platform fee associated with the order.
	 * </p>
	 * 
	 * @return the platform fee
	 */
	public float getPlatformFee() {
		return platformFee;
	}

	/**
	 * Sets the platform fee.
	 * 
	 * <p>
	 * This method sets the platform fee associated with the order.
	 * </p>
	 * 
	 * @param platformFee the platform fee to set
	 */
	public void setPlatformFee(float platformFee) {
		this.platformFee = platformFee;
	}

	/**
	 * Returns a string representation of the order object.
	 * 
	 * <p>
	 * This method provides a string representation of the Ordertable object, including its attributes.
	 * </p>
	 * 
	 * @return a string representation of the order object
	 */
	@Override
	public String toString() {
		return "Ordertable [orderid=" + orderid + ", fk_restaurantid=" + fk_restaurantid + ", fk_userid=" + fk_userid
				+ ", totalamount=" + totalamount + ", status=" + status + ", paymentmode=" + paymentmode
				+ ", feedIndia=" + feedIndia + ", tips=" + tips + ", platformFee=" + platformFee + "]";
	}
}

