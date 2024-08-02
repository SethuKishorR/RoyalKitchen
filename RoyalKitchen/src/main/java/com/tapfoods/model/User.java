package com.tapfoods.model;

/**
 * The User class represents a user entity with attributes such as user ID, 
 * username, email, phone number, password, and address.
 */
public class User {
	private int userid;
	private String username;
	private String email;
	private String phonenumber;
	private String password;
	private String address;

	/**
	 * Default constructor.
	 */
	public User() {
		super();
	}

	/**
	 * Parameterized constructor.
	 * 
	 * <p>
	 * This constructor initializes the User object with the specified values.
	 * </p>
	 * 
	 * @param userid the user ID
	 * @param username the username
	 * @param email the email address
	 * @param phonenumber the phone number
	 * @param password the password
	 * @param address the address
	 */
	public User(int userid, String username, String email, String phonenumber, String password, String address) {
		super();
		this.userid = userid;
		this.username = username;
		this.email = email;
		this.phonenumber = phonenumber;
		this.password = password;
		this.address = address;
	}

	/**
	 * Parameterized constructor without user ID.
	 * 
	 * <p>
	 * This constructor initializes the User object with the specified values, except for the user ID.
	 * </p>
	 * 
	 * @param username the username
	 * @param email the email address
	 * @param phonenumber the phone number
	 * @param password the password
	 * @param address the address
	 */
	public User(String username, String email, String phonenumber, String password, String address) {
		super();
		this.username = username;
		this.email = email;
		this.phonenumber = phonenumber;
		this.password = password;
		this.address = address;
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
	public int getUserid() {
		return userid;
	}

	/**
	 * Sets the user ID.
	 * 
	 * <p>
	 * This method sets the unique identifier for the user.
	 * </p>
	 * 
	 * @param userid the user ID to set
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}

	/**
	 * Gets the username.
	 * 
	 * <p>
	 * This method returns the username of the user.
	 * </p>
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 * 
	 * <p>
	 * This method sets the username of the user.
	 * </p>
	 * 
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the email address.
	 * 
	 * <p>
	 * This method returns the email address of the user.
	 * </p>
	 * 
	 * @return the email address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email address.
	 * 
	 * <p>
	 * This method sets the email address of the user.
	 * </p>
	 * 
	 * @param email the email address to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the phone number.
	 * 
	 * <p>
	 * This method returns the phone number of the user.
	 * </p>
	 * 
	 * @return the phone number
	 */
	public String getPhonenumber() {
		return phonenumber;
	}

	/**
	 * Sets the phone number.
	 * 
	 * <p>
	 * This method sets the phone number of the user.
	 * </p>
	 * 
	 * @param phonenumber the phone number to set
	 */
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	/**
	 * Gets the password.
	 * 
	 * <p>
	 * This method returns the password of the user.
	 * </p>
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * 
	 * <p>
	 * This method sets the password of the user.
	 * </p>
	 * 
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the address.
	 * 
	 * <p>
	 * This method returns the address of the user.
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
	 * This method sets the address of the user.
	 * </p>
	 * 
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Returns a string representation of the user object.
	 * 
	 * <p>
	 * This method provides a string representation of the User object, including its attributes.
	 * </p>
	 * 
	 * @return a string representation of the user object
	 */
	@Override
	public String toString() {
		return "User [userid=" + userid + ", username=" + username + ", email=" + email + ", phonenumber=" + phonenumber
				+ ", password=" + password + ", address=" + address + "]";
	}
}