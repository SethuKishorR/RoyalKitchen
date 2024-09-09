package com.tapfoods.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tapfoods.model.User;

@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	  /**
     * Handles POST requests to add or remove items from the cart.
     * <p>
     * This method processes the request to add or remove items from the cart
     * based on the provided menu ID. It first checks if the user is logged in;
     * if not, it forwards the request to an error page. It also handles invalid
     * or missing parameters, updates the cart in the session, and redirects the
     * user to the appropriate page.
     * </p>
     * 
     * @param request the HttpServletRequest object containing the request data
     * @param response the HttpServletResponse object used to send the response
     * @throws ServletException if an error occurs during request processing
     * @throws IOException if an I/O error occurs
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ServletCalled");
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");

		if (user == null) {
			// Set error message and redirect URL
			request.setAttribute("message", "To access the cart, please sign in to your account.");
			request.setAttribute("redirectUrl", "signIn.jsp");

			// Forward to error.jsp with the message and redirect URL
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return; // Stop further execution
		}

		// Retrieve the menuId parameter
		String menuIdParam = request.getParameter("menuId");
		if (menuIdParam == null || menuIdParam.isEmpty()) {
			// Handle the missing parameter situation
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Menu ID is missing.");
			return;
		}

		int menuId;
		try {
			menuId = Integer.parseInt(menuIdParam);
		} catch (NumberFormatException e) {
			// Handle invalid number format
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Menu ID format.");
			return;
		}

		// Retrieve or create the cart ArrayList
		ArrayList<Integer> cart = (ArrayList<Integer>) session.getAttribute("cart");
		if (cart == null) {
			cart = new ArrayList<>();
			session.setAttribute("cart", cart);
		}

		// Add or remove the menuId
		if (cart.contains(menuId)) {
			cart.remove(Integer.valueOf(menuId));
		} else {
			cart.add(menuId);
		}

		// Debugging: print to console
		System.out.println("Current Cart: " + cart);

		// Get redirectUrl from the request
		String redirectUrl = request.getParameter("redirectUrl");
		if (redirectUrl == null || redirectUrl.isEmpty()) {
			// Default redirect if no redirectUrl is provided
			redirectUrl = "menuItem.jsp";
		}

		// Redirect to the provided URL or the default one
		response.sendRedirect(redirectUrl);
	}

}