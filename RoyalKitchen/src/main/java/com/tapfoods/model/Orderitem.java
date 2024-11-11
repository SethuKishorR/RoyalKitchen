package com.tapfoods.model;

/**
 * The Orderitem class represents an order item entity with attributes such as order item ID,
 * order ID, menu ID, quantity, and subtotal.
 */
public class Orderitem {
	private int orderitemid;
	private int orderid;
	private int menuid;
	private String menuname;
	private int quantity;
	private float subtotal;

	/**
	 * Default constructor.
	 */
	public Orderitem() {
		super();
	}

	/**
	 * Parameterized constructor.
	 * 
	 * <p>
	 * This constructor initializes the Orderitem object with the specified values.
	 * </p>
	 * 
	 * @param orderitemid the order item ID
	 * @param orderid the order ID
	 * @param menuid the menu ID
	 * @param quantity the quantity of the item
	 * @param subtotal the subtotal for the item
	 */
	public Orderitem(int orderitemid, int orderid, int menuid, String menuname, int quantity, float subtotal) {
		super();
		this.orderitemid = orderitemid;
		this.orderid = orderid;
		this.menuid = menuid;
		this.menuname = menuname;
		this.quantity = quantity;
		this.subtotal = subtotal;
	}

	/**
	 * Parameterized constructor without order item ID.
	 * 
	 * <p>
	 * This constructor initializes the Orderitem object with the specified values, except for the order item ID.
	 * </p>
	 * 
	 * @param orderid the order ID
	 * @param menuid the menu ID
	 * @param quantity the quantity of the item
	 * @param subtotal the subtotal for the item
	 */
	public Orderitem(int orderid, int menuid, String menuname, int quantity, float subtotal) {
		super();
		this.orderid = orderid;
		this.menuid = menuid;
		this.menuname = menuname;
		this.quantity = quantity;
		this.subtotal = subtotal;
	}

	/**
	 * Gets the order item ID.
	 * 
	 * <p>
	 * This method returns the unique identifier for the order item.
	 * </p>
	 * 
	 * @return the order item ID
	 */
	public int getOrderitemid() {
		return orderitemid;
	}

	/**
	 * Sets the order item ID.
	 * 
	 * <p>
	 * This method sets the unique identifier for the order item.
	 * </p>
	 * 
	 * @param orderitemid the order item ID to set
	 */
	public void setOrderitemid(int orderitemid) {
		this.orderitemid = orderitemid;
	}

	/**
	 * Gets the order ID.
	 * 
	 * <p>
	 * This method returns the unique identifier for the order associated with the item.
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
	 * This method sets the unique identifier for the order associated with the item.
	 * </p>
	 * 
	 * @param orderid the order ID to set
	 */
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	/**
	 * Gets the menu ID.
	 * 
	 * <p>
	 * This method returns the unique identifier for the menu item associated with the order.
	 * </p>
	 * 
	 * @return the menu ID
	 */
	public int getMenuid() {
		return menuid;
	}

	/**
	 * Sets the menu ID.
	 * 
	 * <p>
	 * This method sets the unique identifier for the menu item associated with the order.
	 * </p>
	 * 
	 * @param menuid the menu ID to set
	 */
	public void setMenuid(int menuid) {
		this.menuid = menuid;
	}
	
	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	/**
	 * Gets the quantity of the item.
	 * 
	 * <p>
	 * This method returns the quantity of the menu item in the order.
	 * </p>
	 * 
	 * @return the quantity of the item
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Sets the quantity of the item.
	 * 
	 * <p>
	 * This method sets the quantity of the menu item in the order.
	 * </p>
	 * 
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Gets the subtotal for the item.
	 * 
	 * <p>
	 * This method returns the subtotal amount for the menu item in the order.
	 * </p>
	 * 
	 * @return the subtotal for the item
	 */
	public float getSubtotal() {
		return subtotal;
	}

	/**
	 * Sets the subtotal for the item.
	 * 
	 * <p>
	 * This method sets the subtotal amount for the menu item in the order.
	 * </p>
	 * 
	 * @param subtotal the subtotal to set
	 */
	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}

	/**
	 * Returns a string representation of the order item object.
	 * 
	 * <p>
	 * This method provides a string representation of the Orderitem object, including its attributes.
	 * </p>
	 * 
	 * @return a string representation of the order item object
	 */
	@Override
	public String toString() {
		return "Orderitem [orderitemid=" + orderitemid + ", orderid=" + orderid + ", menuid=" + menuid + ", menuname=" + menuname+ ", quantity="
				+ quantity + ", subtotal=" + subtotal + "]";
	}
}