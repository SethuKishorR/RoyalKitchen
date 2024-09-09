<%@ page import="java.util.ArrayList"%>
<%@ page import="com.tapfoods.model.Menu"%>
<%@ page import="com.tapfoods.model.Restaurant"%>
<%@ include file="adminNavbar.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Menu and Restaurant Details</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="icon" href="images/logo.png" type="image/png">
<link rel="stylesheet" href="styles/menuItem.css">
<style>
</style>
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
		<a href="AdminRestaurant" class="text-danger">>>Back</a>
		<div class="row justify-content-center">
			<div class="col-md-12 mb-4 mt-2">
				<%
				// Fetch menu and restaurant from session if not already set
				Menu menu = (Menu) request.getAttribute("menu");
			    Integer restaurantId = (Integer) session.getAttribute("restaurantId");

				if (menu == null) {
					menu = (Menu) session.getAttribute("menu");
				}
				if (restaurant == null) {
					restaurant = (Restaurant) session.getAttribute("restaurant");
				}

				if (menu != null && restaurant != null) {
					session.setAttribute("menu", menu);
					session.setAttribute("restaurant", restaurant);
				%>

				<!-- Card for Menu Details -->
				<div class="row mt-4">
					<div class="col-md-12 mb-4">
						<div class="card menu-card px-4 py-2 bg-transparent"
							style="outline: none; border: none;">
							<div class="card-body">
								<!-- Display Restaurant Ratings -->
								<div class="star-rating">
									<%
									double rating = restaurant.getRatings();
									int fullStars = (int) rating;
									int halfStars = (rating - fullStars >= 0.5) ? 1 : 0;
									int emptyStars = 5 - fullStars - halfStars;
									for (int i = 0; i < fullStars; i++) {
									%>
									<i class="fas fa-star"></i>
									<%
									}
									if (halfStars > 0) {
									%>
									<i class="fas fa-star-half-alt"></i>
									<%
									}
									for (int i = 0; i < emptyStars; i++) {
									%>
									<i class="far fa-star"></i>
									<%
									}
									%>
								</div>
								<!-- Menu Info -->
								<p class="info mt-3">
									<strong>Menu Name:</strong>
									<%=menu.getMenuname()%></p>
								<p class="info mt-3">
									<strong>Price:</strong>
									<%=menu.getPrice()%></p>
								<p class="info">
									<strong>Description:</strong>
									<%=menu.getDescription()%></p>
								<p class="info">
									<strong>Status:</strong>
									<%=menu.getIsavailable().equals("Available") ? "Available" : "Not Available"%></p>
							</div>
						</div>
					</div>
				</div>

				<!-- Restaurant and Menu Details Table -->
				<div class="row">
					<div class="col-md-12">
						<table class="table table-bordered table-striped mt-3">
							<thead class="table-dark">
								<tr>
									<th class="py-3">Detail</th>
									<th class="py-3">Value</th>
								</tr>
							</thead>
							<tbody>
								<tr class="table-dark">
									<td colspan="2" class="text-center py-3"
										style="font-weight: 600;">Restaurant Details</td>
								</tr>
								<tr>
									<td class="py-3">Restaurant Name</td>
									<td class="py-3"><%=restaurant.getRestaurantname()%></td>
								</tr>
								<tr>
									<td class="py-3">Restaurant Address</td>
									<td class="py-3"><%=restaurant.getAddress()%></td>
								</tr>
								<tr>
									<td class="py-3">Cuisine Type</td>
									<td class="py-3"><%=restaurant.getCuisinetype()%></td>
								</tr>
								<tr>
									<td class="py-3">Delivery Time</td>
									<td class="py-3"><%=restaurant.getDeliverytime()%> minutes</td>
								</tr>
								<tr>
									<td class="py-3">Ratings</td>
									<td class="py-3"><%=restaurant.getRatings()%></td>
								</tr>
								<tr class="table-dark">
									<td colspan="2" class="text-center py-3"
										style="font-weight: 600;">Menu Details</td>
								</tr>
								<tr>
									<td class="py-3">Menu Name</td>
									<td class="py-3"><%=menu.getMenuname()%></td>
								</tr>
								<tr>
									<td class="py-3">Price</td>
									<td class="py-3"><%=menu.getPrice()%></td>
								</tr>
								<tr>
									<td class="py-3">Description</td>
									<td class="py-3"><%=menu.getDescription()%></td>
								</tr>
								<tr>
									<td class="py-3">Availability</td>
									<td class="py-3"><%=menu.getIsavailable()%></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>

				<%
				} else {
				%>
				<div class="alert alert-warning mt-4" role="alert">No menu or
					restaurant data found.</div>
				<%
				}
				%>
			</div>
		</div>
	</div>
</body>
</html>