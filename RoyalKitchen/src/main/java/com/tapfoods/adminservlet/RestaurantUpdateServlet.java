package com.tapfoods.adminservlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import com.tapfoods.DAO.RestaurantDAO;
import com.tapfoods.DAOImpl.RestaurantDAOImpl;
import com.tapfoods.model.Admin;
import com.tapfoods.model.Restaurant;

/**
 * Servlet implementation class RestaurantUpdateServlet
 * <p>
 * This servlet handles the process of updating restaurant profile information.
 * It ensures that the admin is logged in, retrieves the current restaurant details,
 * validates and updates the provided information, and attempts to update the restaurant
 * profile in the database.
 * </p>
 */
@WebServlet("/admin/updateRestaurantProfile")
@MultipartConfig(
		fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
		maxFileSize = 1024 * 1024 * 10,       // 10MB
		maxRequestSize = 1024 * 1024 * 50     // 50MB
		)
public class RestaurantUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * Handles POST requests to update restaurant profile information.
	 * <p>
	 * This method retrieves the logged-in admin's restaurant ID from the session,
	 * retrieves the current restaurant details, and updates the restaurant profile
	 * based on the submitted form data. It also handles file uploads for the restaurant
	 * image and updates the restaurant record in the database.
	 * </p>
	 * 
	 * @param req  The {@link HttpServletRequest} object that contains the request from the client.
	 * @param resp The {@link HttpServletResponse} object used to send a response to the client.
	 * @throws ServletException If an error occurs while processing the request.
	 * @throws IOException      If an I/O error occurs while processing the request or response.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Admin sessionAdmin = (Admin) session.getAttribute("admin");
		
		if (sessionAdmin == null) {
			req.setAttribute("message", "Admin not found. Please log in again.");
			req.setAttribute("redirectUrl", "adminSignIn.jsp");
			req.getRequestDispatcher("error.jsp").forward(req, resp);
			return;
		}

		int restaurantId = sessionAdmin.getRestaurantid_fk();

		RestaurantDAO restaurantDAO = null;
		Restaurant currentRestaurant = null;
		try {
			restaurantDAO = new RestaurantDAOImpl();
			currentRestaurant = restaurantDAO.getRestaurant(restaurantId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (currentRestaurant == null) {
			req.setAttribute("message", "Restaurant not found. Please try again.");
			req.setAttribute("redirectUrl", "AdminRestaurant");
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

		System.out.println("In do post method of Add Image servlet.");
		Part file=req.getPart("imagepath");
		
		String imagepath = file.getSubmittedFileName();  // get selected image file name
		System.out.println("Selected Image File Name : "+imagepath);
		String uploadPath = "C:\\Users\\jeeva\\git\\RoyalKitchen\\RoyalKitchen\\src\\main\\webapp\\admin\\styles\\images\\"+imagepath;  // upload path where we have to upload our actual image
		System.out.println("Upload Path : "+uploadPath);
		
		try
		{
		
		FileOutputStream fos = new FileOutputStream(uploadPath);
		InputStream is = file.getInputStream();
		
		byte[] data = new byte[is.available()];
		is.read(data);
		fos.write(data);
		fos.close();
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		// Update restaurant details
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
		if (imagepath != null && !imagepath.trim().isEmpty()) {
			currentRestaurant.setImagepath(imagepath);
		}

		int status = 0;
		try {
			status = restaurantDAO.updateRestaurant(currentRestaurant);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (status == 0) {
			req.setAttribute("message", "Restaurant update failed. Please try again.");
			req.setAttribute("redirectUrl", "AdminRestaurant");
			req.getRequestDispatcher("error.jsp").forward(req, resp);
		} else {
			session.setAttribute("restaurant", currentRestaurant);
			req.setAttribute("message", "Restaurant profile updated successfully.");
			req.setAttribute("redirectUrl", "AdminRestaurant");
			req.getRequestDispatcher("success.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}