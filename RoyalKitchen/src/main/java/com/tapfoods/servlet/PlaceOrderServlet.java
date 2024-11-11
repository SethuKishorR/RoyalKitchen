package com.tapfoods.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tapfoods.DAO.MenuDAO;
import com.tapfoods.DAO.OrderItemDAO;
import com.tapfoods.DAO.OrderTableDAO;
import com.tapfoods.DAO.OrderHistoryDAO;  // Add OrderHistoryDAO
import com.tapfoods.DAO.UserDAO;
import com.tapfoods.DAOImpl.MenuDAOImpl;
import com.tapfoods.DAOImpl.OrderItemDAOImpl;
import com.tapfoods.DAOImpl.OrderTableDAOImpl;
import com.tapfoods.DAOImpl.OrderHistoryDAOImpl;  // Add OrderHistoryDAOImpl
import com.tapfoods.DAOImpl.UserDAOImpl;
import com.tapfoods.DBUtils.DBUtils;
import com.tapfoods.model.Menu;
import com.tapfoods.model.Orderitem;
import com.tapfoods.model.Ordertable;
import com.tapfoods.model.OrderHistory;  // Add OrderHistory model
import com.tapfoods.model.User;

/**
 * Servlet to handle the placement of orders.
 * <p>
 * This servlet processes the order placement request, including cart items, delivery tips,
 * platform charges, and donations. It organizes the orders by restaurant, calculates totals,
 * and persists the order data to the database. It also handles errors and transaction management.
 * </p>
 */
@WebServlet("/placeOrderServlet")
public class PlaceOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private MenuDAO menuDAO;
    private UserDAO userDAO;
    private OrderTableDAO orderTableDAO;
    private OrderItemDAO orderItemDAO;
    private OrderHistoryDAO orderHistoryDAO;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Initialize DAO objects
        try {
            this.menuDAO = new MenuDAOImpl();
            this.userDAO = new UserDAOImpl();
            this.orderTableDAO = new OrderTableDAOImpl();
            this.orderItemDAO = new OrderItemDAOImpl();
            this.orderHistoryDAO = new OrderHistoryDAOImpl();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to initialize data access objects.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        // Get session and retrieve user and cart
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ArrayList<Integer> cart = (ArrayList<Integer>) session.getAttribute("cart");

        // Check if cart is empty or null
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }

        // Get delivery tip and other charges from the form
        int deliveryTip = 0;
        int platformCharge = 10;  // Fixed platform charge
        try {
            deliveryTip = Integer.parseInt(request.getParameter("deliveryTip"));
        } catch (NumberFormatException e) {
        }

        String paymentMode = request.getParameter("paymentMode");
        int feedDonation = request.getParameter("feedIndiaDonation") != null ? 1 : 0;

        // Initialize data structures for order processing
        Map<Integer, Float> restaurantOrderTotals = new HashMap<>();
        Map<Integer, ArrayList<Menu>> restaurantOrders = new HashMap<>();
        Map<Integer, ArrayList<Integer>> restaurantQuantities = new HashMap<>();

        // Iterate over the cart and organize orders by restaurant
        for (Integer menuId : cart) {
            try {
                Menu menu = menuDAO.getMenu(menuId);
                String quantityStr = request.getParameter("quantity_" + menuId);
                int quantity = Integer.parseInt(quantityStr);
                int restaurantId = menu.getRestaurantid();

                // Calculate total amount for the specific restaurant
                float itemTotal = quantity * menu.getPrice();
                restaurantOrderTotals.put(restaurantId, restaurantOrderTotals.getOrDefault(restaurantId, 0f) + itemTotal);

                // Organize ordered menu and quantities
                restaurantOrders.computeIfAbsent(restaurantId, k -> new ArrayList<>()).add(menu);
                restaurantQuantities.computeIfAbsent(restaurantId, k -> new ArrayList<>()).add(quantity);

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Failed to process cart item: " + e.getMessage());
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }
        }
        // Calculate the number of restaurants
        int numRestaurants = restaurantOrderTotals.size();
        
        // Divide the charges by the number of restaurants
        float dividedTip = (float) deliveryTip / numRestaurants;
        float dividedPlatformCharge = (float) platformCharge / numRestaurants;
        float dividedFeedDonation = (float) feedDonation / numRestaurants;

        // Print order details to the console
        for (Integer restaurantId : restaurantOrderTotals.keySet()) {
            ArrayList<Menu> menus = restaurantOrders.get(restaurantId);
            ArrayList<Integer> quantities = restaurantQuantities.get(restaurantId);
        }

        // Process orders for each restaurant
        Connection con = null;
        try {
            // Establish a database connection
            con = DBUtils.myConnect();
            con.setAutoCommit(false); // Start transaction

            for (Integer restaurantId : restaurantOrderTotals.keySet()) {
            	 float totalAmount = restaurantOrderTotals.get(restaurantId) + dividedTip + dividedPlatformCharge + dividedFeedDonation;

                // Create and add the order to the database
                if (user != null) {
                    Ordertable order = new Ordertable(restaurantId, user.getUserid(), totalAmount, "Pending..", paymentMode, dividedFeedDonation, dividedTip, dividedPlatformCharge);
                    int orderId = 0;
                    try {
                        orderId = orderTableDAO.addOrderTable(order);  // Assuming addOrderTable returns the generated order ID
                    } catch (Exception e) {
                        e.printStackTrace();
                        request.setAttribute("error", "Failed to place the order.");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                        return;
                    }

                    // Add order items to the database
                    ArrayList<Menu> menus = restaurantOrders.get(restaurantId);
                    ArrayList<Integer> quantities = restaurantQuantities.get(restaurantId);
                    for (int i = 0; i < menus.size(); i++) {
                        Menu menu = menus.get(i);
                        int quantity = quantities.get(i);
                        float subtotal = quantity * menu.getPrice();
                        Orderitem orderItem = new Orderitem(orderId, menu.getMenuid(), menu.getMenuname(), quantity, subtotal);
                        try {
                            orderItemDAO.addOrderItem(orderItem);
                        } catch (Exception e) {
                            e.printStackTrace();
                            request.setAttribute("error", "Failed to add order items.");
                            request.getRequestDispatcher("error.jsp").forward(request, response);
                            return;
                        }
                    }

                    // Record order history
                    try {
                        OrderHistory orderHistory = new OrderHistory(orderId, user.getUserid(), totalAmount, "Pending..");
                        orderHistoryDAO.addOrderHistory(orderHistory);
                    } catch (Exception e) {
                        e.printStackTrace();
                        request.setAttribute("error", "Failed to record order history.");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                        return;
                    }
                }
            }

            con.commit(); // Commit transaction
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Rollback transaction
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            request.setAttribute("error", "Failed to place the order.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true); // Reset auto-commit mode
                    con.close(); // Close connection
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }

        // Store order details in session for receipt
        session.setAttribute("restaurantOrders", restaurantOrders);
        session.setAttribute("restaurantQuantities", restaurantQuantities);
        session.setAttribute("paymentMode", paymentMode);
        session.setAttribute("deliveryTip", deliveryTip);
        session.setAttribute("feedDonation", feedDonation);
        session.setAttribute("platformCharge", platformCharge);

        // Redirect to the receipt page
        response.sendRedirect("orderPlaced.jsp");
    }
}