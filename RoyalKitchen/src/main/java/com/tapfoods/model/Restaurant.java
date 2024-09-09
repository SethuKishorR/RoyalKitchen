package com.tapfoods.model;

import java.io.Serializable;

/**
 * The Restaurant class represents a restaurant entity with attributes such as restaurant ID,
 * restaurant name, delivery time, cuisine type, address, ratings, active status, user ID, admin ID, and image path.
 */
public class Restaurant implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer restaurantid;
	private String restaurantname;
	private int deliverytime;
	private String cuisinetype;
	private String address;
	private float ratings;
	private String isactive;
	private String imagepath;
	private Integer adminid_fk;

	/**
	 * Default constructor.
	 * <p>
	 * Initializes a new instance of the Restaurant class with default values.
	 * </p>
	 */
	public Restaurant() {
		super();
	}

	/**
	 * Parameterized constructor for minimal signup information.
	 * <p>
	 * This constructor initializes the Restaurant object with minimal parameters: restaurant name and address.
	 * Other fields are left uninitialized.
	 * </p>
	 * 
	 * @param restaurantname the restaurant name
	 * @param address the address
	 */
	public Restaurant(String restaurantname, String address) {
		super();
		this.restaurantname = restaurantname;
		this.address = address;
	}

	/**
	 * Parameterized constructor.
	 * <p>
	 * This constructor initializes the Restaurant object with the specified values.
	 * </p>
	 * 
	 * @param restaurantid the restaurant ID
	 * @param restaurantname the restaurant name
	 * @param deliverytime the delivery time
	 * @param cuisinetype the cuisine type
	 * @param address the address
	 * @param ratings the ratings
	 * @param isactive the active status
	 * @param imagepath the image path
	 * @param adminid_fk the foreign key referencing the admin ID
	 */
	public Restaurant(int restaurantid, String restaurantname, int deliverytime, String cuisinetype, String address,
			float ratings, String isactive, String imagepath, int adminid_fk) {
		super();
		this.restaurantid = restaurantid;
		this.restaurantname = restaurantname;
		this.deliverytime = deliverytime;
		this.cuisinetype = cuisinetype;
		this.address = address;
		this.ratings = ratings;
		this.isactive = isactive;
		this.imagepath = imagepath;
		this.adminid_fk = adminid_fk;
	}

	/**
	 * Parameterized constructor without restaurant ID.
	 * <p>
	 * This constructor initializes the Restaurant object with the specified values, except for the restaurant ID.
	 * </p>
	 * 
	 * @param restaurantname the restaurant name
	 * @param deliverytime the delivery time
	 * @param cuisinetype the cuisine type
	 * @param address the address
	 * @param ratings the ratings
	 * @param isactive the active status
	 * @param imagepath the image path
	 * @param adminid_fk the foreign key referencing the admin ID
	 */
	public Restaurant(String restaurantname, int deliverytime, String cuisinetype, String address, float ratings,
			String isactive, String imagepath, int adminid_fk) {
		super();
		this.restaurantname = restaurantname;
		this.deliverytime = deliverytime;
		this.cuisinetype = cuisinetype;
		this.address = address;
		this.ratings = ratings;
		this.isactive = isactive;
		this.imagepath = imagepath;
		this.adminid_fk = adminid_fk;
	}

	/**
	 * Gets the restaurant ID.
	 * <p>
	 * This method returns the unique identifier for the restaurant.
	 * </p>
	 * 
	 * @return the restaurant ID
	 */
	public Integer getRestaurantid() {
		return restaurantid;
	}

	/**
	 * Sets the restaurant ID.
	 * <p>
	 * This method sets the unique identifier for the restaurant.
	 * </p>
	 * 
	 * @param restaurantid the restaurant ID to set
	 */
	public void setRestaurantid(int restaurantid) {
		this.restaurantid = restaurantid;
	}

	/**
	 * Gets the restaurant name.
	 * <p>
	 * This method returns the name of the restaurant.
	 * </p>
	 * 
	 * @return the restaurant name
	 */
	public String getRestaurantname() {
		return restaurantname;
	}

	/**
	 * Sets the restaurant name.
	 * <p>
	 * This method sets the name of the restaurant.
	 * </p>
	 * 
	 * @param restaurantname the restaurant name to set
	 */
	public void setRestaurantname(String restaurantname) {
		this.restaurantname = restaurantname;
	}

	/**
	 * Gets the delivery time.
	 * <p>
	 * This method returns the estimated delivery time for the restaurant.
	 * </p>
	 * 
	 * @return the delivery time
	 */
	public int getDeliverytime() {
		return deliverytime;
	}

	/**
	 * Sets the delivery time.
	 * <p>
	 * This method sets the estimated delivery time for the restaurant.
	 * </p>
	 * 
	 * @param deliverytime the delivery time to set
	 */
	public void setDeliverytime(int deliverytime) {
		this.deliverytime = deliverytime;
	}

	/**
	 * Gets the cuisine type.
	 * <p>
	 * This method returns the type of cuisine offered by the restaurant.
	 * </p>
	 * 
	 * @return the cuisine type
	 */
	public String getCuisinetype() {
		return cuisinetype;
	}

	/**
	 * Sets the cuisine type.
	 * <p>
	 * This method sets the type of cuisine offered by the restaurant.
	 * </p>
	 * 
	 * @param cuisinetype the cuisine type to set
	 */
	public void setCuisinetype(String cuisinetype) {
		this.cuisinetype = cuisinetype;
	}

	/**
	 * Gets the address.
	 * <p>
	 * This method returns the address of the restaurant.
	 * </p>
	 * 
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 * <p>
	 * This method sets the address of the restaurant.
	 * </p>
	 * 
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the ratings.
	 * <p>
	 * This method returns the rating of the restaurant.
	 * </p>
	 * 
	 * @return the ratings
	 */
	public float getRatings() {
		return ratings;
	}

	/**
	 * Sets the ratings.
	 * <p>
	 * This method sets the rating of the restaurant.
	 * </p>
	 * 
	 * @param ratings the ratings to set
	 */
	public void setRatings(Float ratings) {
		this.ratings = ratings;
	}

	/**
	 * Gets the active status.
	 * <p>
	 * This method returns the active status of the restaurant.
	 * </p>
	 * 
	 * @return the active status
	 */
	public String getIsactive() {
		return isactive;
	}

	/**
	 * Sets the active status.
	 * <p>
	 * This method sets the active status of the restaurant.
	 * </p>
	 * 
	 * @param isactive the active status to set
	 */
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	/**
	 * Gets the image path.
	 * <p>
	 * This method returns the path to the image of the restaurant.
	 * </p>
	 * 
	 * @return the image path
	 */
	public String getImagepath() {
		return imagepath;
	}

	/**
	 * Sets the image path.
	 * <p>
	 * This method sets the path to the image of the restaurant.
	 * </p>
	 * 
	 * @param imagepath the image path to set
	 */
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	/**
	 * Gets the foreign key referencing the admin ID.
	 * <p>
	 * This method returns the foreign key that references the admin ID.
	 * </p>
	 * 
	 * @return the foreign key referencing the admin ID
	 */
	public Integer getAdminid_fk() {
		return adminid_fk;
	}

	/**
	 * Sets the foreign key referencing the admin ID.
	 * <p>
	 * This method sets the foreign key that references the admin ID.
	 * </p>
	 * 
	 * @param adminid_fk the foreign key referencing the admin ID to set
	 */
	public void setAdminid_fk(int adminid_fk) {
		this.adminid_fk = adminid_fk;
	}

	/**
	 * Returns a string representation of the restaurant object.
	 * <p>
	 * This method provides a string representation of the Restaurant object, including its attributes.
	 * </p>
	 * 
	 * @return a string representation of the restaurant object
	 */
	@Override
	public String toString() {
		return "Restaurant [restaurantid=" + restaurantid + ", restaurantname=" + restaurantname + ", deliverytime="
				+ deliverytime + ", cuisinetype=" + cuisinetype + ", address=" + address + ", ratings=" + ratings
				+ ", isactive=" + isactive + ", imagepath=" + imagepath + ", adminid_fk=" + adminid_fk + "]";
	}
}