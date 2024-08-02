package com.tapfoods.DBUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The DBUtils class provides utility methods for managing database connections.
 * It handles establishing a connection to the MySQL database using JDBC.
 */
public final class DBUtils {
    private static Connection con;
    private static final String URL = "jdbc:mysql://localhost:3306/tapfoods";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Sethukishor@9944750880";

    /**
     * Establishes a connection to the database using JDBC.
     * 
     * <p>This method loads the MySQL JDBC driver and creates a connection to
     * the database specified by the URL, username, and password.</p>
     * 
     * @return a {@link Connection} object representing the connection to the database
     * @throws SQLException if a database access error occurs or the URL is invalid
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    public static Connection myConnect() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Create and return a connection to the database
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            // Handle exception when the JDBC driver class is not found
            e.printStackTrace();
        } catch (SQLException e) {
            // Handle exception when a database access error occurs
            e.printStackTrace();
        }
        return con;
    }
}