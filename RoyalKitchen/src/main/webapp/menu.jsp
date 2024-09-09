<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="navbar.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="com.tapfoods.model.Menu"%>
<%
Integer restaurantId = (Integer) session.getAttribute("restaurantId");
String restaurantName = (String)session.getAttribute("restaurantName");
if (restaurantId == null) {
	restaurantId = 0; // Default value if not set
}
List<Menu> menuItems = (List<Menu>) session.getAttribute("menuList");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title><%=restaurantName!=null?restaurantName : "MenuItems" %></title>
 <link rel="icon" href="admin/styles/images/favicon.png" type="image/png">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"
	rel="stylesheet">
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.2/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="admin/styles/restaurant.css">
<link rel="stylesheet" href="admin/styles/menuItem.css">
</head>
<body>
	<div class="container mt-5 py-5">
		<a href="searchRestaurants" class="text-danger">>>Back</a>

		<!-- Display active filters -->
		<div class="mb-4 mt-3">
			<%
			List<String> activeQueries = (List<String>) session.getAttribute("activeQueries");
			// Retrieve the restaurantId from session
			if (activeQueries != null && !activeQueries.isEmpty()) {
			%>
			<div class="filter-tags">
				<%
				for (String q : activeQueries) {
				%>
				<div class="filter-tag">
					<span><%=q%></span>
					<form action="searchMenu" method="post" style="display: inline;">
						<!-- Assuming restaurantId needs to be dynamically retrieved from menuItems -->
						<input type="hidden" name="restaurantId" value="<%=restaurantId%>">
						<input type="hidden" name="removeQuery" value="<%=q%>">
						<button type="submit" class="btn-close" aria-label="Close"></button>
					</form>
				</div>
				<%
				}
				%>
			</div>
			<%
			}
			%>
		</div>

		<!-- Default cuisine filters with option to add -->
		<div class="mb-4">
			<p class="text-danger">Explore Trending Flavors</p>
			<div class="default-filters">
				<%
				List<String> cuisineFilters = List.of("Available", "< Rs.150", "Rs.150-250", "Rs.250-350", "> Rs.350");
				for (String filter : cuisineFilters) {
				%>
				<div class="filter-tag">
					<form action="searchMenu" method="post" style="display: inline;">
						<!-- Assuming restaurantId needs to be dynamically retrieved from menuItems -->
						<input type="hidden" name="restaurantId" value="<%=restaurantId%>">
						<input type="hidden" name="addQuery" value="<%=filter%>">
						<button type="submit" class="btn btn-sm">
							<span><%=filter%></span> +
						</button>
					</form>
				</div>
				<%
				}
				%>
			</div>
		</div>

		<!-- Display list of menu items as Bootstrap cards -->
		<div class="row mt-2">
			<%
			if (menuItems != null && !menuItems.isEmpty()) {
				for (Menu item : menuItems) {
					Integer itemRestaurantId = item.getRestaurantid(); // Get restaurantId from each Menu item
					boolean isAvailable = "Available".equalsIgnoreCase(item.getIsavailable()); // Check availability
					String cardClass = isAvailable ? "" : "card-inactive";
					String labelClass = isAvailable ? "d-none" : ""; // Show label if unavailable
					String clickClass = isAvailable ? "" : "click-disabled"; // Disable click if unavailable
			%>
			<div class="col-md-3 mb-4">
				<form action="menuDetails" method="post">
					<input type="hidden" name="restaurantId"
						value="<%=itemRestaurantId%>"> <input type="hidden"
						name="id" value="<%=item.getMenuid()%>">
						<input type="hidden"
						name="menuName" value="<%=item.getMenuname()%>">
					<div class="card <%=cardClass%> <%=clickClass%>"
						style="height: 270px; display: flex; flex-direction: column; overflow: hidden; cursor: pointer;"
						onclick="this.closest('form').submit();">
						<!-- Not Available label -->
						<div class="position-relative">
							<div
								class="badge bg-danger text-light position-absolute d-flex top-0 start-0 m-2 <%=labelClass%>"
								style="font-size: 0.875rem;">Not Available</div>

							<!-- Image at the top of the card -->
							<img
								src="<%=item.getImagepath() != null ? item.getImagepath() : "admin/styles/images/defaultImg.png"%>"
								class="card-img-top" alt="<%=item.getMenuname()%>"
								style="min-height: 150px; height: 150px; object-fit: cover; width: 100%;">

							<!-- Card body -->
							<div class="card-body d-flex flex-column"
								style="flex: 1; overflow: hidden; padding: 15px;">
								<div
									class="d-flex justify-content-between align-items-center mb-2">
									<div>
										<h5 class="card-title" style="font-size: 1.1rem; margin: 0;"><%=item.getMenuname()%></h5>
										<p class="card-text" style="font-size: 0.9rem; margin: 0;"><%=item.getDescription()%></p>
									</div>
									<div class="badge bg-light text-dark"
										style="font-size: 0.875rem;">
										<%=item.getPrice()%>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
			<%
			}
			} else {
			%>
			<div class="col-12">
				<div class="row mt-2 justify-content-center align-items-center">
					<div class="col-md-6 position-relative">
						<!-- Image container -->
						<img src="admin/styles/images/notFound.jpg" alt="No Results Found"
							class="img-fluid w-100" style="height: auto;">

						<!-- Overlay message -->
						<div
							class="position-absolute top-50 start-50 translate-middle text-center w-100">
							<div class="alert alert-info mb-0" style="opacity: 0.85;"
								role="alert">No menu items match your search criteria.</div>
						</div>
					</div>
				</div>
			</div>
			<%
			}
			%>
			<%
			ArrayList<Integer> cart = (ArrayList<Integer>) session.getAttribute("cart");
			if (cart != null && cart.size() > 0) { // Show if cart has items
			%>
			<a href="cart.jsp" class="text-decoration-none">
				<div class="d-flex justify-content-center">
					<div class="card floating-cart col-md-8"
						style="background-color: lightgreen;">
						<div
							class="card-body d-flex align-items-center justify-content-center"
							style="height: 70px;">
							<!-- Adjust height as needed -->
							<p class="mb-0 text-white">
								<%=cart.size()%>
								items added, <span>view in cart</span> <i
									class="fas fa-shopping-basket"></i>
							</p>
						</div>
					</div>
				</div>
			</a>
		</div>
		<%
		}
		%>

		<script
			src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/2.9.2/umd/popper.min.js"></script>
		<script
			src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.2/js/bootstrap.bundle.min.js"></script>
</body>
</html>