<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.tapfoods.model.Orderitem"%>
<%@ page import="com.tapfoods.model.Ordertable"%>
<%@ page session="true"%>
<%@ include file="navbar.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="icon" href="admin/styles/images/favicon.png" type="image/png">
<link rel="stylesheet" href="admin/styles/menuItem.css">
<title>Order Receipt</title>
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
		<a id="back-link" href="OrderHistory" class="text-danger">>>Back</a>
		<div class="d-flex justify-content-center mt-3">
			<div class="card mb-2 col-md-12"
				style="background: rgba(0, 128, 0, 0.3); backdrop-filter: blur(10px); border-radius: 10px; border: 1px solid rgba(144, 238, 144, 0.5); color: #fff;">
				<div
					class="card-body d-flex align-items-center justify-content-center"
					style="height: 70px;">
					<h5 class="mb-0 text-white">Order Receipt</h5>
				</div>
			</div>
		</div>

		<!-- Receipt Table -->
		<table class="table table-bordered table-striped mt-3">
			<thead class="table-dark">
				<tr>
					<th class="py-4" style="width: 20%;">Item id</th>
					<th class="py-4" style="width: 20%;">Item name</th>
					<th class="py-4" style="width: 20%;">Quantity</th>
					<th class="py-4" style="width: 20%;">Price</th>
					<th class="py-4" style="width: 20%;">Subtotal</th>
				</tr>
			</thead>
			<tbody>
				<%
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
                Ordertable orderTable = (Ordertable) request.getAttribute("orderTable");
                ArrayList<Orderitem> orderItems = (ArrayList<Orderitem>) request.getAttribute("orderItems");
                boolean hasItems = (orderItems != null && !orderItems.isEmpty());
                boolean userLoggedIn = session.getAttribute("user") != null;

                if (userLoggedIn) {
                %>
				<% if (hasItems) { %>
				<% for (Orderitem item : orderItems) { %>
				<tr>
					<td class="py-3"><%=item.getMenuid()%></td>
					<td class="py-3"><%=item.getMenuname()%></td>
					<td class="py-3"><%=item.getQuantity()%></td>
					<td class="py-3"><%=currencyFormat.format(item.getSubtotal() / item.getQuantity())%></td>
					<td class="py-3"><%=currencyFormat.format(item.getSubtotal())%></td>
				</tr>
				<% } %>
				<% } else { %>
				<tr>
					<td colspan="5" class="text-center py-3">No items found in the
						order.</td>
				</tr>
				<% } %>

				<% if (hasItems) { %>
				<% if (orderTable.getTips() > 0) { %>
				<tr>
					<td colspan="4" class="text-start py-3">Delivery Tip</td>
					<td class="py-3"><%=currencyFormat.format(orderTable.getTips())%></td>
				</tr>
				<% } %>
				<% if (orderTable.getFeedIndia() > 0) { %>
				<tr>
					<td colspan="4" class="text-start py-3">Feed India Donation</td>
					<td class="py-3"><%=currencyFormat.format(orderTable.getFeedIndia())%></td>
				</tr>
				<% } %>
				<tr>
					<td colspan="4" class="text-start py-3">Platform Charge</td>
					<td class="py-3"><%=currencyFormat.format(orderTable.getPlatformFee()) %></td>
				</tr>

				<tr class="table-dark">
					<td colspan="4" class="text-start py-3">Grand Total</td>
					<td class="py-3"><strong> <%=currencyFormat.format(orderTable != null ? orderTable.getTotalamount() : 0) %>
					</strong></td>
				</tr>
				<tr>
					<td colspan="4" class="text-start py-3">Payment Mode</td>
					<td class="py-3"><%=orderTable != null ? (orderTable.getPaymentmode().equals("cashondelivery") ? "Cash on delivery" : "Online mode") : "Not Available" %></td>
				</tr>
				<tr>
					<td colspan="4" class="text-start py-3">Status</td>
					<td class="py-3"><%= orderTable.getStatus()%></td>
				</tr>
				<% } %>
				<% } else { %>
				<tr>
					<td colspan="5" class="text-center py-3">You are in guest
						mode. Please log in to get details.</td>
				</tr>
				<% } %>
			</tbody>
		</table>
		<!-- Back to Home or another page -->
		<div class="text-end mt-3">
			<a href="searchRestaurants" class="btn btn-success">Home</a>
		</div>
	</div>
</body>
</html>