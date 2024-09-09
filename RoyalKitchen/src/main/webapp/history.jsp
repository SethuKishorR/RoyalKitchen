<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.tapfoods.model.OrderHistory" %>
<%@ page session="true" %>
<%@ include file="navbar.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="admin/styles/images/favicon.png" type="image/png">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="admin/styles/menuItem.css">
    <title>Order Histories</title>
</head>
<body class="bg-light">
    <div class="area">
        <ul class="circles">
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
        </ul>
    </div>
    <div class="container mt-5 py-5">
        <div class="d-flex justify-content-center">
            <div class="card mb-2 col-md-12"
                style="background: rgba(0, 128, 0, 0.3); backdrop-filter: blur(10px); border-radius: 10px; border: 1px solid rgba(144, 238, 144, 0.5); color: #fff;">
                <div class="card-body d-flex align-items-center justify-content-center" style="height: 70px;">
                    <h5 class="mb-0 text-white">Order History</h5>
                </div>
            </div>
        </div>

        <!-- Order History Table -->
        <table class="table table-bordered table-striped mt-3">
            <thead class="table-dark">
                <tr>
                    <th class="py-4">Order ID</th>
                    <th class="py-4">Date</th>
                    <th class="py-4">Time</th>
                    <th class="py-4">Total Amount</th>
                    <th class="py-4">Status</th>
                    <th class="py-4">Bill</th>
                </tr>
            </thead>
            <tbody>
                <%
                boolean userLoggedIn = session.getAttribute("user") != null;

                if (userLoggedIn) {
                    // Retrieve order history from request attribute
                    ArrayList<OrderHistory> orderHistoryList = (ArrayList<OrderHistory>) request.getAttribute("orderHistoryList");

                    if (orderHistoryList != null && !orderHistoryList.isEmpty()) {
                        // Create separate formatters for date and time
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm:ss aa");

                        for (OrderHistory order : orderHistoryList) {
                            // Assuming order.getOrderdate() now returns java.sql.Timestamp (for both date and time)
                            java.sql.Timestamp orderTimestamp = order.getOrderdate(); // Change this to the actual getter if necessary

                            // Convert to java.util.Date for formatting
                            java.util.Date utilDate = new java.util.Date(orderTimestamp.getTime());

                            // Format the date and time separately
                            String formattedDate = dateFormatter.format(utilDate);
                            String formattedTime = timeFormatter.format(utilDate);
                %>
                <tr>
                    <td class="py-3"><%=order.getF_orderid()%></td>
                    <td class="py-3"><%=formattedDate%></td>
                    <td class="py-3"><%=formattedTime%></td>
                    <td class="py-3"><%=order.getTotalamount()%></td>
                    <td class="py-3"><%=order.getStatus()%></td>
                    <td>
                        <form action="receiptServlet" method="post" style="display: inline;">
                            <input type="hidden" name="orderid" value="<%=order.getF_orderid()%>">
                            <button type="submit" class="btn btn-danger btn-sm">
                                <i class="fas fa-external-link-alt"></i>
                            </button>
                        </form>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="6" class="text-center py-3">No order history found.</td>
                </tr>
                <% 
                    }
                } else {
                %>
                <tr>
                    <td colspan="6" class="text-center py-3">You are in guest mode. Please log in to view your order history.</td>
                </tr>
                <% 
                }
                %>
            </tbody>
        </table>

        <!-- Back to Home or another page -->
        <div class="text-end mt-3">
            <a href="searchRestaurants" class="btn btn-success">Home</a>
        </div>
    </div>
</body>
</html>