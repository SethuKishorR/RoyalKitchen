package com.tapfoods.DBUtils;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

/**
 * Application Lifecycle Listener implementation class AppContextListener.
 * <p>
 * This listener is responsible for performing clean-up tasks when the web application is undeployed or the server is shutting down.
 * Specifically, it deregisters JDBC drivers and shuts down the MySQL Abandoned Connection Cleanup Thread to prevent potential memory leaks.
 * </p>
 */
@WebListener
public class AppContextListener implements ServletContextListener {

	/**
	 * This method is called when the servlet context is initialized (when the web application is deployed).
	 * <p>
	 * Currently, this method does not perform any actions during initialization, but it can be used for application-specific startup logic.
	 * </p>
	 *
	 * @param sce The {@link ServletContextEvent} containing the context for the web application.
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// No initialization actions required at this time
	}

	/**
	 * This method is called when the servlet context is destroyed (when the web application is undeployed or the server is shutting down).
	 * <p>
	 * It performs the following actions:
	 * <ul>
	 *   <li>Deregisters all JDBC drivers that were registered by the web application.</li>
	 *   <li>Shuts down the MySQL Abandoned Connection Cleanup Thread.</li>
	 * </ul>
	 * These actions help to prevent potential memory leaks when the application is stopped or undeployed.
	 * </p>
	 *
	 * @param sce The {@link ServletContextEvent} containing the context for the web application.
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			// Deregister all JDBC drivers registered by this application
			Enumeration<Driver> drivers = DriverManager.getDrivers();
			while (drivers.hasMoreElements()) {
				Driver driver = drivers.nextElement();
				DriverManager.deregisterDriver(driver);
				System.out.println("Deregistered driver: " + driver);
			}

			// Shutdown MySQL Abandoned Connection Cleanup Thread
			AbandonedConnectionCleanupThread.checkedShutdown();
			System.out.println("AbandonedConnectionCleanupThread shut down.");

		} catch (SQLException e) {
			// Handle SQL exceptions during deregistration and shutdown
			e.printStackTrace();
		}
	}
}