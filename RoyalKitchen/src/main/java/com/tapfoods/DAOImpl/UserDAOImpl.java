package com.tapfoods.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.tapfoods.DAO.UserDAO;
import com.tapfoods.DBUtils.DBUtils;
import com.tapfoods.model.User;

/**
 * The UserDAOImpl class implements the {@link UserDAO} interface.
 * <p>This class provides the actual implementation of data access operations for {@link User} entities using JDBC.</p>
 */
public class UserDAOImpl implements UserDAO {
    private Connection con;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet resultSet;
    private User user;

    private ArrayList<User> userList = new ArrayList<User>();

    private static final String ADD_USER = "INSERT INTO `user` (`name`, `email`, `phonenumber`, `password`, `address`) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_ALL_USER = "SELECT * FROM `user`";
    private static final String GET_ON_EMAIL = "SELECT * FROM `user` WHERE `email`=?";
    private static final String UPDATE_ON_EMAIL = "UPDATE `user` SET `username`=?, `phonenumber`=?, `password`=?, `address`=? WHERE `email`=?";
    private static final String DELETE_ON_EMAIL = "DELETE FROM `user` WHERE `email`=?";

    private int status = 0;

    /**
     * Constructs a new {@code UserDAOImpl} instance and establishes a database connection.
     * <p>This constructor initializes the database connection using {@link DBUtils#myConnect()}.</p>
     */
    public UserDAOImpl() {
        try {
            con = DBUtils.myConnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new user to the database.
     * <p>This method prepares an SQL {@code INSERT} statement and executes it to add a {@link User} object to the database.</p>
     * 
     * @param u the {@link User} object to be added
     * @return an integer indicating the result of the operation (e.g., the number of rows affected)
     */
    @Override
    public int addUser(User u) {
        try {
            pstmt = con.prepareStatement(ADD_USER);
            pstmt.setString(1, u.getUsername());
            pstmt.setString(2, u.getEmail());
            pstmt.setString(3, u.getPhonenumber());
            pstmt.setString(4, u.getPassword());
            pstmt.setString(5, u.getAddress());
            status = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * Retrieves all users from the database.
     * <p>This method executes an SQL {@code SELECT} statement to retrieve all {@link User} records and returns them as an {@link ArrayList}.</p>
     * 
     * @return an {@link ArrayList} of {@link User} objects representing all users
     */
    @Override
    public ArrayList<User> getAllUser() {
        try {
            stmt = con.createStatement();
            resultSet = stmt.executeQuery(GET_ALL_USER);
            userList = extractUserListFromResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    /**
     * Retrieves a user from the database by their email.
     * <p>This method prepares an SQL {@code SELECT} statement with a specified email and retrieves the corresponding {@link User} record.</p>
     * 
     * @param email the email address of the user to be retrieved
     * @return the {@link User} object corresponding to the specified email, or {@code null} if no user is found
     */
    @Override
    public User getUser(String email) {
        try {
            pstmt = con.prepareStatement(GET_ON_EMAIL);
            pstmt.setString(1, email);
            resultSet = pstmt.executeQuery();
            userList = extractUserListFromResultSet(resultSet);
            if (!userList.isEmpty()) {
                user = userList.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Updates an existing user's information in the database.
     * <p>This method prepares an SQL {@code UPDATE} statement with the new user information and executes it to update the corresponding record.</p>
     * 
     * @param u the {@link User} object containing updated information
     * @return an integer indicating the result of the operation (e.g., the number of rows affected)
     */
    @Override
    public int updateUser(User u) {
        try {
            pstmt = con.prepareStatement(UPDATE_ON_EMAIL);
            pstmt.setString(1, u.getUsername());
            pstmt.setString(2, u.getPhonenumber());
            pstmt.setString(3, u.getPassword());
            pstmt.setString(4, u.getAddress());
            pstmt.setString(5, u.getEmail());
            status = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * Deletes a user from the database by their email.
     * <p>This method prepares an SQL {@code DELETE} statement with a specified email and executes it to remove the corresponding {@link User} record.</p>
     * 
     * @param email the email address of the user to be deleted
     * @return an integer indicating the result of the operation (e.g., the number of rows affected)
     */
    @Override
    public int deleteUser(String email) {
        try {
            pstmt = con.prepareStatement(DELETE_ON_EMAIL);
            pstmt.setString(1, email);
            status = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * Extracts a list of {@link User} objects from the provided {@link ResultSet}.
     * <p>This method iterates over the {@link ResultSet} and creates a list of {@link User} objects from the data.</p>
     * 
     * @param resultSet the {@link ResultSet} object containing user data
     * @return an {@link ArrayList} of {@link User} objects
     */
    public ArrayList<User> extractUserListFromResultSet(ResultSet resultSet) {
        ArrayList<User> users = new ArrayList<>();
        try {
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt("userid"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("phonenumber"),
                        resultSet.getString("password"),
                        resultSet.getString("address")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}