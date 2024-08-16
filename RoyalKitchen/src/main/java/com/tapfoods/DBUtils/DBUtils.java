package com.tapfoods.DBUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The DBUtils class provides utility methods for managing database connections.
 * It handles establishing a connection to the MySQL database using JDBC.
 */
public final class DBUtils {
	private static final String URL = "jdbc:mysql://localhost:3306/tapfoods";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "Sethukishor@9944750880";

	private DBUtils() {
		// Private constructor to prevent instantiation
	}

	/**
	 * Establishes a connection to the database using JDBC.
	 * 
	 * @return a {@link Connection} object representing the connection to the database
	 * @throws SQLException if a database access error occurs or the URL is invalid
	 * @throws ClassNotFoundException if the JDBC driver class is not found
	 */
	public static Connection myConnect() {
		Connection con = null;
		try {
			// Load the MySQL JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Create and return a connection to the database
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * Closes the provided database resources.
	 * 
	 * @param con the {@link Connection} object to be closed
	 * @param stmt the {@link Statement} object to be closed
	 * @param pstmt the {@link PreparedStatement} object to be closed
	 * @param rs the {@link ResultSet} object to be closed
	 */
	public static void closeResources(Connection con, Statement stmt, PreparedStatement pstmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}