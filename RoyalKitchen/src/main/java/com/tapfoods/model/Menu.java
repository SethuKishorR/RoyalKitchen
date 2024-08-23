package com.tapfoods.model;

/**
 * The Menu class represents a menu item entity with attributes such as menu ID,
 * restaurant ID, menu name, price, description, availability status, and image path.
 */
public class Menu {
	private int menuid;
	private int restaurantid;
	private String menuname;
	private float price;
	private String description;
	private String isavailable;
	private String imagepath;

	/**
	 * Default constructor.
	 */
	public Menu() {
		super();
	}

	/**
	 * Parameterized constructor.
	 * 
	 * <p>
	 * This constructor initializes the Menu object with the specified values.
	 * </p>
	 * 
	 * @param menuid the menu ID
	 * @param restaurantid the restaurant ID
	 * @param menuname the name of the menu item
	 * @param price the price of the menu item
	 * @param description the description of the menu item
	 * @param isavailable the availability status of the menu item
	 * @param imagepath the image path of the menu item
	 */
	public Menu(int menuid, int restaurantid, String menuname, float price, String description, String isavailable,
			String imagepath) {
		super();
		this.menuid = menuid;
		this.restaurantid = restaurantid;
		this.menuname = menuname;
		this.price = price;
		this.description = description;
		this.isavailable = isavailable;
		this.imagepath = imagepath;
	}

	/**
	 * Parameterized constructor without menu ID.
	 * 
	 * <p>
	 * This constructor initializes the Menu object with the specified values, except for the menu ID.
	 * </p>
	 * 
	 * @param restaurantid the restaurant ID
	 * @param menuname the name of the menu item
	 * @param price the price of the menu item
	 * @param description the description of the menu item
	 * @param isavailable the availability status of the menu item
	 * @param imagepath the image path of the menu item
	 */
	public Menu(String menuname, int restaurantid, float price, String description, String isavailable,
			String imagepath) {
		super();
		this.restaurantid = restaurantid;
		this.menuname = menuname;
		this.price = price;
		this.description = description;
		this.isavailable = isavailable;
		this.imagepath = imagepath;
	}

	/**
	 * Gets the menu ID.
	 * 
	 * <p>
	 * This method returns the unique identifier for the menu item.
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
	 * This method sets the unique identifier for the menu item.
	 * </p>
	 * 
	 * @param menuid the menu ID to set
	 */
	public void setMenuid(int menuid) {
		this.menuid = menuid;
	}

	/**
	 * Gets the restaurant ID.
	 * 
	 * <p>
	 * This method returns the identifier of the restaurant associated with the menu item.
	 * </p>
	 * 
	 * @return the restaurant ID
	 */
	public int getRestaurantid() {
		return restaurantid;
	}

	/**
	 * Sets the restaurant ID.
	 * 
	 * <p>
	 * This method sets the identifier of the restaurant associated with the menu item.
	 * </p>
	 * 
	 * @param restaurantid the restaurant ID to set
	 */
	public void setRestaurantid(int restaurantid) {
		this.restaurantid = restaurantid;
	}

	/**
	 * Gets the name of the menu item.
	 * 
	 * <p>
	 * This method returns the name of the menu item.
	 * </p>
	 * 
	 * @return the name of the menu item
	 */
	public String getMenuname() {
		return menuname;
	}

	/**
	 * Sets the name of the menu item.
	 * 
	 * <p>
	 * This method sets the name of the menu item.
	 * </p>
	 * 
	 * @param menuname the name of the menu item to set
	 */
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	/**
	 * Gets the price of the menu item.
	 * 
	 * <p>
	 * This method returns the price of the menu item.
	 * </p>
	 * 
	 * @return the price of the menu item
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * Sets the price of the menu item.
	 * 
	 * <p>
	 * This method sets the price of the menu item.
	 * </p>
	 * 
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * Gets the description of the menu item.
	 * 
	 * <p>
	 * This method returns a description of the menu item.
	 * </p>
	 * 
	 * @return the description of the menu item
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the menu item.
	 * 
	 * <p>
	 * This method sets the description of the menu item.
	 * </p>
	 * 
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the availability status of the menu item.
	 * 
	 * <p>
	 * This method returns the availability status of the menu item.
	 * </p>
	 * 
	 * @return the availability status of the menu item
	 */
	public String getIsavailable() {
		return isavailable;
	}

	/**
	 * Sets the availability status of the menu item.
	 * 
	 * <p>
	 * This method sets the availability status of the menu item.
	 * </p>
	 * 
	 * @param isavailable the availability status to set
	 */
	public void setIsavailable(String isavailable) {
		this.isavailable = isavailable;
	}

	/**
	 * Gets the image path of the menu item.
	 * 
	 * <p>
	 * This method returns the path to the image of the menu item.
	 * </p>
	 * 
	 * @return the image path of the menu item
	 */
	public String getImagepath() {
		return imagepath;
	}

	/**
	 * Sets the image path of the menu item.
	 * 
	 * <p>
	 * This method sets the path to the image of the menu item.
	 * </p>
	 * 
	 * @param imagepath the image path to set
	 */
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	/**
	 * Returns a string representation of the menu item object.
	 * 
	 * <p>
	 * This method provides a string representation of the Menu object, including its attributes.
	 * </p>
	 * 
	 * @return a string representation of the menu item object
	 */
	@Override
	public String toString() {
		return "Menu [menuid=" + menuid + ", restaurantid=" + restaurantid + ", menuname=" + menuname + ", price="
				+ price + ", description=" + description + ", isavailable=" + isavailable + ", imagepath=" + imagepath
				+ "]";
	}
}