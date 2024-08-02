package com.tapfoods.model;

/**
 * The Restaurant class represents a restaurant entity with attributes such as restaurant ID,
 * restaurant name, delivery time, cuisine type, address, ratings, active status, admin ID, and image path.
 */
public class Restaurant {
	private int restaurantid;
	private String restaurantname;
	private int deliverytime;
	private String cuisinetype;
	private String address;
	private float ratings;
	private String isactive;
	private int adminid;
	private String imagepath;

	/**
	 * Default constructor.
	 */
	public Restaurant() {
		super();
	}

	/**
	 * Parameterized constructor.
	 * 
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
	 * @param adminid the admin ID
	 * @param imagepath the image path
	 */
	public Restaurant(int restaurantid, String restaurantname, int deliverytime, String cuisinetype, String address,
			float ratings, String isactive, int adminid, String imagepath) {
		super();
		this.restaurantid = restaurantid;
		this.restaurantname = restaurantname;
		this.deliverytime = deliverytime;
		this.cuisinetype = cuisinetype;
		this.address = address;
		this.ratings = ratings;
		this.isactive = isactive;
		this.adminid = adminid;
		this.imagepath = imagepath;
	}

	/**
	 * Parameterized constructor without restaurant ID.
	 * 
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
	 * @param adminid the admin ID
	 * @param imagepath the image path
	 */
	public Restaurant(String restaurantname, int deliverytime, String cuisinetype, String address, float ratings,
			String isactive, int adminid, String imagepath) {
		super();
		this.restaurantname = restaurantname;
		this.deliverytime = deliverytime;
		this.cuisinetype = cuisinetype;
		this.address = address;
		this.ratings = ratings;
		this.isactive = isactive;
		this.adminid = adminid;
		this.imagepath = imagepath;
	}

	/**
	 * Gets the restaurant ID.
	 * 
	 * <p>
	 * This method returns the unique identifier for the restaurant.
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
	 * <p>
	 * This method sets the rating of the restaurant.
	 * </p>
	 * 
	 * @param ratings the ratings to set
	 */
	public void setRatings(float ratings) {
		this.ratings = ratings;
	}

	/**
	 * Gets the active status.
	 * 
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
	 * 
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
	 * Gets the admin ID.
	 * 
	 * <p>
	 * This method returns the ID of the admin associated with the restaurant.
	 * </p>
	 * 
	 * @return the admin ID
	 */
	public int getAdminid() {
		return adminid;
	}

	/**
	 * Sets the admin ID.
	 * 
	 * <p>
	 * This method sets the ID of the admin associated with the restaurant.
	 * </p>
	 * 
	 * @param adminid the admin ID to set
	 */
	public void setAdminid(int adminid) {
		this.adminid = adminid;
	}

	/**
	 * Gets the image path.
	 * 
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
	 * 
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
	 * Returns a string representation of the restaurant object.
	 * 
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
				+ ", isactive=" + isactive + ", adminid=" + adminid + ", imagepath=" + imagepath + "]";
	}
}