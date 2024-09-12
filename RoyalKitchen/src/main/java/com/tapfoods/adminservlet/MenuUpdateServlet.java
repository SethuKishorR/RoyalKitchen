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

import com.tapfoods.DAO.MenuDAO;
import com.tapfoods.DAOImpl.MenuDAOImpl;
import com.tapfoods.model.Admin;
import com.tapfoods.model.Menu;

/**
 * Servlet implementation class MenuUpdateServlet
 * <p>
 * This servlet handles the process of updating menu item information. It ensures that the admin is logged in,
 * retrieves the current menu details, validates and updates the provided information, and attempts to update
 * the menu item in the database. Based on the result, it either forwards to a success page or an error page
 * with an appropriate message.
 * </p>
 */
@WebServlet("/admin/updateMenu")
@MultipartConfig(
		fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
		maxFileSize = 1024 * 1024 * 10,       // 10MB
		maxRequestSize = 1024 * 1024 * 50     // 50MB
		)
public class MenuUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles POST requests for updating the menu item.
	 * <p>
	 * This method retrieves the admin object from the session, checks if the admin is logged in, and retrieves the current
	 * menu details from the database. It then updates the menu fields based on the provided form data and
	 * attempts to update the menu item in the database. Depending on the outcome of the update operation, it either
	 * forwards to a success page or an error page with an appropriate message.
	 * </p>
	 * 
	 * @param request  the HttpServletRequest object containing the request data
	 * @param response the HttpServletResponse object for sending the response
	 * @throws ServletException if an error occurs during request handling
	 * @throws IOException      if an input or output error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Admin sessionAdmin = (Admin) session.getAttribute("admin");

		/**
		 * Checks if the admin is logged in by verifying the presence of an admin object in the session. 
		 * <p>
		 * If no admin is found, it sets an error message and redirects to the sign-in page. If the admin is not 
		 * found in the session, it forwards to the error page with a message prompting the admin to log in again.
		 * </p>
		 */
		if (sessionAdmin == null) {
			request.setAttribute("message", "Admin not found. Please log in again.");
			request.setAttribute("redirectUrl", "adminSignIn.jsp");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}

		// Retrieve form parameters
		String menuIdStr = request.getParameter("menuid");
		String menuName = request.getParameter("menuName");
		String priceStr = request.getParameter("menuPrice");
		String description = request.getParameter("menuDescription");
		String isAvailable = request.getParameter("menuAvailability");

		System.out.println("In do post method of Add Image servlet.");
	
		Part file = request.getPart("imagepath");
		String imagepath = null;
		if (file != null && file.getSize() > 0) {
		    imagepath = file.getSubmittedFileName();  // Get the selected image file name
		    System.out.println("Selected Image File Name : " + imagepath);
		    
		    // Define the upload path where the image will be stored
		    String uploadPath = "C:\\Users\\jeeva\\git\\RoyalKitchen\\RoyalKitchen\\src\\main\\webapp\\admin\\styles\\images\\" + imagepath;
		    System.out.println("Upload Path : " + uploadPath);
		    
		    try {
		        // Write the file to the specified path
		        FileOutputStream fos = new FileOutputStream(uploadPath);
		        InputStream is = file.getInputStream();
		        
		        // Read and write the file data
		        byte[] data = new byte[is.available()];
		        is.read(data);
		        fos.write(data);
		        fos.close();
		    } catch (Exception e) {
		        e.printStackTrace();  // Log the exception
		    }
		} else {
		    System.out.println("No image file selected.");
		}
		
		int menuId;
		MenuDAO menuDAO = null;
		Menu currentMenu = null;

		/**
		 * Retrieves the current menu details based on the menu ID.
		 * <p>
		 * If the menu ID is invalid or the menu is not found, it sets an error message and redirects to the menu page. 
		 * If the menu is not found in the database, the request is forwarded to an error page with an appropriate message.
		 * </p>
		 */
		try {
			menuDAO = new MenuDAOImpl();
			if (menuIdStr != null && !menuIdStr.trim().isEmpty()) {
				menuId = Integer.parseInt(menuIdStr);
				currentMenu = menuDAO.getMenu(menuId);
			} else {
				throw new NumberFormatException("Menu ID is missing.");
			}
		} catch (NumberFormatException | SQLException e) {
			request.setAttribute("message", "Invalid menu ID.");
			request.setAttribute("redirectUrl", "AdminRestaurant");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}

		if (currentMenu == null) {
			request.setAttribute("message", "Menu item not found. Please try again.");
			request.setAttribute("redirectUrl", "AdminRestaurant");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}

		/**
		 * Updates the menu fields only if new values are provided.
		 * <p>
		 * Validates and converts input values before updating. Updates are applied to the menu object based on provided 
		 * form data. If new values are provided, they are used to update the menu item.
		 * </p>
		 */
		if (menuName != null && !menuName.trim().isEmpty()) {
			currentMenu.setMenuname(menuName);
		}
		if (priceStr != null && !priceStr.trim().isEmpty()) {
			try {
				currentMenu.setPrice(Float.parseFloat(priceStr));
			} catch (NumberFormatException e) {
				request.setAttribute("message", "Invalid price format.");
				request.setAttribute("redirectUrl", "AdminRestaurant");
				request.getRequestDispatcher("error.jsp").forward(request, response);
				return;
			}
		}
		if (description != null && !description.trim().isEmpty()) {
			currentMenu.setDescription(description);
		}
		if (isAvailable != null && !isAvailable.trim().isEmpty()) {
			currentMenu.setIsavailable(isAvailable);
		}
		if (imagepath != null && !imagepath.trim().isEmpty()) {
			currentMenu.setImagepath(imagepath);
		}

		int status = 0;
		try {
			status = menuDAO.updateMenu(currentMenu);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("message", "Failed to update menu item. Please try again.");
			request.setAttribute("redirectUrl", "AdminRestaurant");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}

		/**
		 * Attempts to update the menu item in the database.
		 * <p>
		 * If the update is unsuccessful, it sets an error message and redirects to the menu page. If successful, it 
		 * updates the session with the new menu details and forwards to a success page.
		 * </p>
		 */
		if (status == 0) {
			request.setAttribute("message", "Menu item update failed. Please try again.");
			request.setAttribute("redirectUrl", "AdminRestaurant");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		} else {
			session.setAttribute("menu", currentMenu);
			request.setAttribute("message", "Menu item updated successfully.");
			request.setAttribute("redirectUrl", "AdminRestaurant");
			request.getRequestDispatcher("success.jsp").forward(request, response);
		}
	}

	/**
	 * Handles GET requests by delegating to the POST request handler.
	 * <p>
	 * This method ensures that GET requests are processed in the same way as POST requests by calling the 
	 * {@link #doPost(HttpServletRequest, HttpServletResponse)} method.
	 * </p>
	 * 
	 * @param request  the HttpServletRequest object containing the request data
	 * @param response the HttpServletResponse object for sending the response
	 * @throws ServletException if an error occurs during request handling
	 * @throws IOException      if an input or output error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}