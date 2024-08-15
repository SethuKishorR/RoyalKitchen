package com.tapfoods.model;

/**
 * Represents an admin entity.
 * <p>This class is a plain old Java object (POJO) that defines the properties of an admin 
 * and provides getter and setter methods to access and modify these properties.</p>
 */
public class Admin {
    private Integer adminid;
    private String adminkey;
    private String password;
    private int restaurantid_fk;

    /**
     * Constructs a new Admin instance with no initial values.
     */
    public Admin() {
        super();
    }

    /**
     * Constructs a new Admin instance for signup with the specified parameters.
     * <p>This constructor is used when creating a new admin without the restaurant ID, 
     * which can be set later.</p>
     * 
     * @param adminkey the admin key
     * @param password the admin password
     */
    public Admin(String adminkey, String password) {
        super();
        this.adminkey = adminkey;
        this.password = password;
    }

    /**
     * Constructs a new Admin instance with the specified values.
     * 
     * @param adminid the admin ID
     * @param adminkey the admin key
     * @param password the admin password
     * @param restaurantid_fk the foreign key referencing the restaurant ID
     */
    public Admin(int adminid, String adminkey, String password, int restaurantid_fk) {
        super();
        this.adminid = adminid;
        this.adminkey = adminkey;
        this.password = password;
        this.restaurantid_fk = restaurantid_fk;
    }

    /**
     * Returns the admin ID.
     * 
     * @return the admin ID
     */
    public Integer getAdminid() {
        return adminid;
    }

    /**
     * Sets the admin ID.
     * 
     * @param adminid the admin ID to set
     */
    public void setAdminid(int adminid) {
        this.adminid = adminid;
    }

    /**
     * Returns the admin key.
     * 
     * @return the admin key
     */
    public String getAdminkey() {
        return adminkey;
    }

    /**
     * Sets the admin key.
     * 
     * @param adminkey the admin key to set
     */
    public void setAdminkey(String adminkey) {
        this.adminkey = adminkey;
    }

    /**
     * Returns the admin password.
     * 
     * @return the admin password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the admin password.
     * 
     * @param password the admin password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the foreign key referencing the restaurant ID.
     * 
     * @return the foreign key referencing the restaurant ID
     */
    public int getRestaurantid_fk() {
        return restaurantid_fk;
    }

    /**
     * Sets the foreign key referencing the restaurant ID.
     * 
     * @param restaurantid_fk the foreign key referencing the restaurant ID to set
     */
    public void setRestaurantid_fk(int restaurantid_fk) {
        this.restaurantid_fk = restaurantid_fk;
    }

    /**
     * Returns a string representation of the admin object.
     * <p>This method provides a string representation of the Admin object, including its attributes.</p>
     * 
     * @return a string representation of the admin object
     */
    @Override
    public String toString() {
        return "Admin [adminid=" + adminid + ", adminkey=" + adminkey + ", password=" + password
                + ", restaurantid_fk=" + restaurantid_fk + "]";
    }
}