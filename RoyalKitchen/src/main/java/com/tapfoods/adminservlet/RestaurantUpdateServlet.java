package com.tapfoods.adminservlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tapfoods.DAO.RestaurantDAO;
import com.tapfoods.DAOImpl.RestaurantDAOImpl;
import com.tapfoods.model.Admin;
import com.tapfoods.model.Restaurant;

/**
 * Servlet implementation class RestaurantUpdateServlet
 * <p>
 * This servlet handles the process of updating restaurant profile information. It ensures that the admin is logged in, 
 * retrieves the current restaurant details, validates and updates the provided information, and attempts to update 
 * the restaurant profile in the database. Based on the result, it either forwards to a success page or an error page 
 * with an appropriate message.
 * </p>
 */
@WebServlet("/admin/updateRestaurantProfile")
public class RestaurantUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles POST requests for updating the restaurant profile.
	 * <p>
	 * This method retrieves the admin object from the session, checks if the admin is logged in, and retrieves the current 
	 * restaurant details from the database. It then updates the restaurant fields based on the provided form data and 
	 * attempts to update the restaurant profile in the database. Depending on the outcome of the update operation, it either 
	 * forwards to a success page or an error page with an appropriate message.
	 * </p>
	 * 
	 * @param req  the HttpServletRequest object containing the request data
	 * @param resp the HttpServletResponse object for sending the response
	 * @throws ServletException if an error occurs during request handling
	 * @throws IOException      if an input or output error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Admin sessionAdmin = (Admin) session.getAttribute("admin");

		/**
		 * Checks if the admin is logged in by verifying the presence of an admin object in the session. If no admin is found, 
		 * it sets an error message and redirects to the sign-in page.
		 * <p>
		 * If the admin is not found in the session, it forwards to the error page with a message prompting the admin to log in again.
		 * </p>
		 */
		if (sessionAdmin == null) {
			req.setAttribute("message", "Admin not found. Please log in again.");
			req.setAttribute("redirectUrl", "adminSignIn.jsp");
			req.getRequestDispatcher("error.jsp").forward(req, resp);
			return;
		}

		int restaurantId = sessionAdmin.getRestaurantid_fk();

		RestaurantDAO restaurantDAO = new RestaurantDAOImpl();
		Restaurant currentRestaurant = restaurantDAO.getRestaurant(restaurantId);

		/**
		 * Retrieves the current restaurant details based on the restaurant ID. If the restaurant is not found, it sets 
		 * an error message and redirects to the restaurant page.
		 * <p>
		 * If the restaurant is not found in the database, the request is forwarded to an error page with an appropriate message.
		 * </p>
		 */
		if (currentRestaurant == null) {
			req.setAttribute("message", "Restaurant not found. Please try again.");
			req.setAttribute("redirectUrl", "adminRestaurant.jsp");
			req.getRequestDispatcher("error.jsp").forward(req, resp);
			return;
		}

		// Retrieve form parameters
		String restaurantName = req.getParameter("restaurantname");
		String deliveryTimeStr = req.getParameter("deliverytime");
		String cuisineType = req.getParameter("cuisinetype");
		String address = req.getParameter("address");
		String ratingsStr = req.getParameter("ratings");
		String isActive = req.getParameter("isactive");
		String imagePath = req.getParameter("imagepath");

		/**
		 * Updates the restaurant fields only if new values are provided. Validates and converts input values before updating.
		 * <p>
		 * Updates are applied to the restaurant object based on provided form data. If new values are provided, they are used
		 * to update the restaurant profile.
		 * </p>
		 */
		if (restaurantName != null && !restaurantName.trim().isEmpty()) {
			currentRestaurant.setRestaurantname(restaurantName);
		}
		if (deliveryTimeStr != null && !deliveryTimeStr.trim().isEmpty()) {
			currentRestaurant.setDeliverytime(Integer.parseInt(deliveryTimeStr));
		}
		if (cuisineType != null && !cuisineType.trim().isEmpty()) {
			currentRestaurant.setCuisinetype(cuisineType);
		}
		if (address != null && !address.trim().isEmpty()) {
			currentRestaurant.setAddress(address);
		}
		if (ratingsStr != null && !ratingsStr.trim().isEmpty()) {
			currentRestaurant.setRatings(Float.parseFloat(ratingsStr));
		}
		if (isActive != null && !isActive.trim().isEmpty()) {
			currentRestaurant.setIsactive(isActive);
		}
		if (imagePath != null && !imagePath.trim().isEmpty()) {
			currentRestaurant.setImagepath(imagePath);
		}

		int status = restaurantDAO.updateRestaurant(currentRestaurant);

		/**
		 * Attempts to update the restaurant profile in the database. If the update is unsuccessful, it sets an error message 
		 * and redirects to the restaurant page. If successful, it updates the session with the new restaurant details and 
		 * forwards to a success page.
		 * <p>
		 * Based on the result of the update operation, the user is either redirected to a success page or shown an error message.
		 * </p>
		 */
		if (status == 0) {
			req.setAttribute("message", "Restaurant update failed. Please try again.");
			req.setAttribute("redirectUrl", "adminRestaurant.jsp");
			req.getRequestDispatcher("error.jsp").forward(req, resp);
		} else {
			session.setAttribute("restaurant", currentRestaurant);
			req.setAttribute("message", "Restaurant profile updated successfully.");
			req.setAttribute("redirectUrl", "adminRestaurant.jsp");
			req.getRequestDispatcher("success.jsp").forward(req, resp);
		}
	}

	/**
	 * Handles GET requests by delegating to the POST request handler.
	 * <p>
	 * This method ensures that GET requests are processed in the same way as POST requests by calling the {@link #doPost(HttpServletRequest, HttpServletResponse)} method.
	 * </p>
	 * 
	 * @param req  the HttpServletRequest object containing the request data
	 * @param resp the HttpServletResponse object for sending the response
	 * @throws ServletException if an error occurs during request handling
	 * @throws IOException      if an input or output error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}