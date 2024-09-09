<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.tapfoods.model.Ordertable" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order History - Admin</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="admin/styles/menuItem.css">
    <style>
      
    </style>
</head>
<body class="bg-light">
    <div class="container mt-5 py-5">
        <div class="d-flex justify-content-center">
            <div class="card mb-2 col-md-12"
                style="background: rgba(0, 128, 0, 0.3); backdrop-filter: blur(10px); border-radius: 10px; border: 1px solid rgba(144, 238, 144, 0.5); color: #fff;">
                <div class="card-body d-flex align-items-center justify-content-center" style="height: 70px;">
                    <h5 class="mb-0 text-white-custom">Order History</h5>
                </div>
            </div>
        </div>

        <%-- Display error message if there is one --%>
        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null && !errorMessage.isEmpty()) {
        %>
            <div class="alert alert-danger" role="alert">
                <strong><%= errorMessage %></strong>
            </div>
        <%
            }
        %>

        <%-- Check if there is any order data --%>
        <%
            ArrayList<Ordertable> orderTableList = (ArrayList<Ordertable>) request.getAttribute("orderTableList");
            if (orderTableList != null && !orderTableList.isEmpty()) {
                // Create separate formatters for date and time
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        %>
            <table class="table table-bordered table-striped mt-3">
                <thead class="bg-dark-custom text-white-custom">
                    <tr>
                        <th class="py-4">Order ID</th>
                        <th class="py-4">Date</th>
                        <th class="py-4">Time</th>
                        <th class="py-4">Total Amount</th>
                        <th class="py-4">Payment</th>
                    </tr>
                </thead>
                <tbody>
                    <%-- Iterate through the orderTableList and display each order --%>
                    <%
                        for (Ordertable order : orderTableList) {
                            // Formatting the order date and time if needed
                            Timestamp orderTimestamp = order.getOrderdate();
                            java.util.Date utilDate = new java.util.Date(orderTimestamp.getTime());

                            // Format the date and time separately
                            String formattedDate = dateFormatter.format(utilDate);
                            String formattedTime = timeFormatter.format(utilDate);
                    %>
                        <tr>
                            <td class="py-3"><%= order.getOrderid() %></td>
                            <td class="py-3"><%= formattedDate %></td>
                            <td class="py-3"><%= formattedTime %></td>
                            <td class="py-3"><%= order.getTotalamount() %></td>
                            <td class="py-3"><%= order.getPaymentmode() %></td>
                        </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        <%
            } else {
        %>
            <p class="text-center py-3">No orders found for the selected restaurant.</p>
        <%
            }
        %>
    </div>

    <!-- Bootstrap JavaScript and dependencies -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>