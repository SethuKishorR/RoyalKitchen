<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.tapfoods.model.Ordertable"%>
<%@ page session="true"%>
<%@ include file="adminNavbar.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="icon" href="styles/images/favicon.png" type="image/png">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="styles/menuItem.css">
<title>Order Histories - Admin</title>
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
				<div
					class="card-body d-flex align-items-center justify-content-center"
					style="height: 70px;">
					<h5 class="mb-0 text-white">Admin - Order History</h5>
				</div>
			</div>
		</div>
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
		<!-- Order History Table -->
		<table class="table table-bordered table-striped mt-3">
			<thead class="table-dark">
				<tr>
					<th class="py-4">Order ID</th>
					<th class="py-4">Date</th>
					<th class="py-4">Time</th>
					<th class="py-4">Total Amount</th>
					<th class="py-4">Status</th>
					<th class="py-4">Payment</th>
					<th class="py-4">Bill</th>
				</tr>
			</thead>
			<tbody>
				<%
                    ArrayList<Ordertable> orderTableList = (ArrayList<Ordertable>) request.getAttribute("orderTableList");
                    if (orderTableList != null && !orderTableList.isEmpty()) {
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");

                        for (Ordertable order : orderTableList) {
                            Timestamp orderTimestamp = order.getOrderdate();
                            java.util.Date utilDate = new java.util.Date(orderTimestamp.getTime());

                            String formattedDate = dateFormatter.format(utilDate);
                            String formattedTime = timeFormatter.format(utilDate);
                  %>
				<tr>
					<td class="py-3"><%= order.getOrderid() %></td>
					<td class="py-3"><%= formattedDate %></td>
					<td class="py-3"><%= formattedTime %></td>
					<td class="py-3"><%= order.getTotalamount() %></td>
					<td class="py-3"><%= order.getStatus() %></td>
					<td class="py-3"><%= order.getPaymentmode() %></td>
					<td class="py-3">
						<form action="adminReceiptServlet" method="post"
							style="display: inline;">
							<input type="hidden" name="orderid"
								value="<%= order.getOrderid() %>">
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
					<td colspan="6" class="text-center py-3">No order history
						found.</td>
				</tr>
				<% 
                    }
                %>
			</tbody>
		</table>
	</div>
</body>
</html>