package com.tapfoods.adminservlet;

import com.tapfoods.DAO.AdminDAO;
import com.tapfoods.DAO.RestaurantDAO;
import com.tapfoods.DAOImpl.AdminDAOImpl;
import com.tapfoods.DAOImpl.RestaurantDAOImpl;
import com.tapfoods.DBUtils.DBUtils;
import com.tapfoods.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p>Servlet implementation for handling admin profile deletion.</p>
 * <p>This servlet manages the deletion of an admin profile along with its associated restaurant.
 * It ensures that the admin is authenticated and the restaurant is removed before deleting the admin profile.
 * If any operation fails, appropriate error messages are provided.</p>
 */
@WebServlet("/admin/deleteAdminProfile")
public class AdminDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * <p>Handles the HTTP POST request to delete an admin profile.</p>
     * <p>This method performs the following operations:
     * <ul>
     * <li>Validates the session and retrieves the admin object.</li>
     * <li>Checks the password provided by the user.</li>
     * <li>Begins a database transaction to delete the associated restaurant and the admin profile.</li>
     * <li>Commits the transaction if both deletions are successful; otherwise, it rolls back.</li>
     * <li>Redirects the user to the appropriate page based on the outcome of the operation.</li>
     * </ul></p>
     *
     * @param request  The {@link HttpServletRequest} object containing the client request.
     * @param response The {@link HttpServletResponse} object containing the response to be sent to the client.
     * @throws ServletException If an error occurs during servlet processing.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            request.setAttribute("message", "Session expired. Please log in again.");
            request.setAttribute("redirectUrl", "adminSignIn.jsp"); // Redirect to sign-in page
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            request.setAttribute("message", "Admin not found. Please log in again.");
            request.setAttribute("redirectUrl", "adminSignIn.jsp"); // Redirect to sign-in page
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        String enteredPassword = request.getParameter("password");
        int adminId = admin.getAdminid();
        int restaurantId = admin.getRestaurantid_fk(); // Assuming admin has a reference to the restaurant

        // Retrieve stored password from the admin object in the session
        String storedPassword = admin.getPassword();

        if (!storedPassword.equals(enteredPassword)) {
            request.setAttribute("message", "Incorrect password. Cannot delete admin profile.");
            request.setAttribute("redirectUrl", "AdminRestaurant"); // Redirect to profile page or retry
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        Connection con = null;
        AdminDAO adminDAO = null;
        RestaurantDAO restaurantDAO = null;
		try {
			adminDAO = new AdminDAOImpl();
			restaurantDAO = new RestaurantDAOImpl();
		} catch (SQLException e) {
			e.printStackTrace();
		}

        try {
            con = DBUtils.myConnect(); // Get connection from DBUtils

            // Start transaction
            con.setAutoCommit(false);

            // Delete restaurant first
            if (restaurantId > 0) {
                int restaurantResult = restaurantDAO.deleteRestaurant(con, restaurantId);
                if (restaurantResult <= 0) {
                    con.rollback(); // Rollback if restaurant deletion fails
                    request.setAttribute("message", "Failed to delete associated restaurant.");
                    request.setAttribute("redirectUrl", "AdminRestaurant"); // Redirect to profile page or retry
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }
            }

            // Delete admin
            int adminResult = adminDAO.deleteAdmin(con, adminId);
            if (adminResult > 0) {
                con.commit(); // Commit transaction if deletion is successful
                session.invalidate(); // Invalidate session after successful deletion
                request.setAttribute("message", "Admin and associated restaurant deleted successfully.");
                request.setAttribute("redirectUrl", "adminSignUp.jsp"); // Redirect to home or result page
                request.getRequestDispatcher("success.jsp").forward(request, response);
            } else {
                con.rollback(); // Rollback transaction if deletion fails
                request.setAttribute("message", "Failed to delete admin.");
                request.setAttribute("redirectUrl", "AdminRestaurant"); // Redirect to profile page or retry
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Rollback transaction on error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            request.setAttribute("message", "An error occurred while deleting admin and restaurant.");
            request.setAttribute("redirectUrl", "AdminRestaurant"); // Redirect to profile page or retry
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } finally {
            if (con != null) {
                try {
                    con.close(); // Close connection
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * <p>Handles the HTTP GET request by forwarding it to the doPost method.</p>
     * <p>This method ensures that GET requests are processed in the same way as POST requests.</p>
     *
     * @param request  The {@link HttpServletRequest} object containing the client request.
     * @param response The {@link HttpServletResponse} object containing the response to be sent to the client.
     * @throws ServletException If an error occurs during servlet processing.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}