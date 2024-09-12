<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ include file="navbar.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="com.tapfoods.model.Restaurant"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Restaurant Home</title>
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
		<!-- Display active filters -->
		<div class="mb-4">
			<%
			List<String> activeQueries = (List<String>) session.getAttribute("activeQueries");
			if (activeQueries != null && !activeQueries.isEmpty()) {
			%>
			<div class="filter-tags">
				<%
				for (String q : activeQueries) {
				%>
				<div class="filter-tag">
					<span><%=q%></span>
					<form action="searchRestaurants" method="post"
						style="display: inline;">
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

		<!-- Default filters with option to add -->
		<div class="mb-4">
			<p class="text-danger">Trending Restaurants</p>
			<div class="default-filters">
				<%
				List<String> defaultFilters = List.of("Spice", "Biryani", "Gourmet", "Savory", "Flavors", "Active");
				for (String filter : defaultFilters) {
				%>
				<div class="filter-tag">
					<form action="searchRestaurants" method="post"
						style="display: inline;">
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

		<!-- Default time filters with option to add -->
		<div class="mb-4">
			<p class="text-danger">Fast Feasts & Leisurely Delights</p>
			<div class="default-filters">
				<%
				List<String> timeFilters = List.of("20-30mins", "30-40mins", "40-50mins", "> 50mins");
				for (String filter : timeFilters) {
				%>
				<div class="filter-tag">
					<form action="searchRestaurants" method="post"
						style="display: inline;">
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

		<!-- Display list of restaurants as Bootstrap cards -->
		<div class="row mt-2">
			<%
			List<Restaurant> restaurants = (List<Restaurant>) session.getAttribute("restaurantList");
			if (restaurants != null && !restaurants.isEmpty()) {
				for (Restaurant r : restaurants) {
					session.setAttribute("restaurantId", r.getRestaurantid());
					// Determine if the restaurant is active or not
					boolean isActive = "active".equalsIgnoreCase(r.getIsactive());
					String cardClass = isActive ? "" : "card-inactive";
					String labelClass = isActive ? "d-none" : ""; // Show label if inactive
					String clickClass = isActive ? "" : "click-disabled"; // Disable click if inactive
			%>
			<div class="col-md-3 mb-4">
				<form id="searchMenuForm<%=r.getRestaurantid()%>"
					action="searchMenu" method="POST" style="display: none;">
					<input type="hidden" name="restaurantId"
						value="<%=r.getRestaurantid()%>">
					<input type="hidden" name="restaurantName"
						value="<%=r.getRestaurantname()%>">
				</form>

				<div class="card <%=cardClass%> <%=clickClass%>"
					onclick="submitSearchMenuForm('<%=r.getRestaurantid()%>')"
					style="height: 250px; display: flex; flex-direction: column; overflow: hidden; cursor: pointer;">
					<!-- Not Available label -->
					<div class="position-relative">
						<div
							class="badge bg-danger text-light position-absolute top-0 start-0 m-2 <%=labelClass%>"
							style="font-size: 0.875rem;">Not Available</div>

						<!-- Image at the top of the card -->
						<img
							src="admin/styles/images/<%=r.getImagepath() != null ? r.getImagepath() : "defaultImg.png"%>"
							class="card-img-top" alt="<%=r.getRestaurantname()%>"
							style="min-height: 150px; height: 150px; object-fit: cover; width: 100%;">

						<!-- Card body -->
						<div class="card-body d-flex flex-column"
							style="flex-grow: 1; overflow: hidden; padding: 15px; display: flex; flex-direction: column;">
							<div
								class="d-flex justify-content-between align-items-center mb-2"
								style="margin-top: auto;">
								<div>
									<h5 class="card-title" style="font-size: 1.1rem; margin: 0;"><%=r.getRestaurantname()%></h5>
									<p class="card-text" style="font-size: 0.9rem; margin: 0;"><%=r.getCuisinetype()%></p>
								</div>
								<div class="badge bg-light text-dark"
									style="font-size: 0.875rem;">
									<%=r.getDeliverytime()%>
									mins
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<script>
				function submitSearchMenuForm(restaurantId) {
					document.getElementById('searchMenuForm' + restaurantId)
							.submit();
				}
			</script>

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
								role="alert">No restaurants match your search criteria.</div>
						</div>
					</div>
				</div>
			</div>
			<%
			}
			%>
		</div>
	</div>
	<%
	ArrayList<Integer> cart = (ArrayList<Integer>) session.getAttribute("cart");
	if (cart != null && cart.size() > 0) { // Show if cart has items
	%>
	<a href="cart.jsp" class="text-decoration-none">
		<div class="d-flex justify-content-center">
			<div class="card floating-cart col-md-8"
				style="background-color: lightgreen; position: fixed;">
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
	<%
	}
	%>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/2.9.2/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.2/js/bootstrap.bundle.min.js"></script>
</body>
</html>