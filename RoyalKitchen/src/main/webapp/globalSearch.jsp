<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="navbar.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.tapfoods.model.Restaurant"%>
<%@ page import="com.tapfoods.model.Menu"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Global Search</title>
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
	<%
	String query = request.getParameter("query");
	List<Restaurant> restaurantList = (List<Restaurant>) session.getAttribute("restaurantList");
	List<Menu> menuList = (List<Menu>) session.getAttribute("menuList");

	boolean hasRestaurants = (restaurantList != null && !restaurantList.isEmpty());
	boolean hasMenus = (menuList != null && !menuList.isEmpty());
	%>

	<div class="container mt-5 py-5">
		<a href="searchRestaurants" class="text-danger mb-4 d-block">>>Back</a>

		<div class="search-header">
			<strong>Search Results for:</strong> "<%=query%>"
		</div>

		<div class="search-results-container">
			<%
			if (hasRestaurants) {
			%>
			<!-- Restaurant Results -->
			<div class="mb-4">
				<p class="text-danger">Restaurants based on your search</p>
				<div class="row">
					<%
					for (Restaurant r : restaurantList) {
						session.setAttribute("restaurantId", r.getRestaurantid());
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
						</form>
						<div class="card <%=cardClass%> <%=clickClass%>"
							onclick="submitSearchMenuForm('<%=r.getRestaurantid()%>')"
							style="height: 250px; display: flex; flex-direction: column; overflow: hidden; cursor: pointer;">
							<div class="position-relative">
								<div
									class="badge bg-danger text-light position-absolute d-flex top-0 start-0 m-2 <%=labelClass%>"
									style="font-size: 0.875rem;">Not Available</div>
								<img
									src="<%=r.getImagepath() != null ? r.getImagepath() : "admin/styles/images/defaultImg.png"%>"
									class="card-img-top" alt="<%=r.getRestaurantname()%>"
									style="min-height: 150px; height: 150px; object-fit: cover; width: 100%;">
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
							document.getElementById(
									'searchMenuForm' + restaurantId).submit();
						}
					</script>

					<%
					}
					%>
				</div>
			</div>
			<%
			}
			%>

			<%
			if (hasMenus) {
			%>
			<!-- Menu Results -->
			<div class="mb-4">
				<p class="text-danger">Menus based on your search</p>
				<div class="row">
					<%
					for (Menu menu : menuList) {
						boolean isAvailable = "Available".equalsIgnoreCase(menu.getIsavailable());
						String cardClass = isAvailable ? "" : "card-inactive";
						String labelClass = isAvailable ? "d-none" : ""; // Show label if unavailable
						String clickClass = isAvailable ? "" : "click-disabled"; // Disable click if unavailable
					%>
					<div class="col-md-3 mb-4">
						<form action="menuDetails" method="post">
							<input type="hidden" name="restaurantId"
								value="<%=menu.getRestaurantid()%>"> <input
								type="hidden" name="id" value="<%=menu.getMenuid()%>">
							<div class="card <%=cardClass%> <%=clickClass%>"
								style="height: 270px; display: flex; flex-direction: column; overflow: hidden; cursor: pointer;"
								onclick="this.closest('form').submit();">
								<div class="position-relative">
									<div
										class="badge bg-danger text-light position-absolute d-flex top-0 start-0 m-2 <%=labelClass%>"
										style="font-size: 0.875rem;">Not Available</div>
									<img
										src="<%=menu.getImagepath() != null ? menu.getImagepath() : "admin/styles/images/defaultImg.png"%>"
										class="card-img-top" alt="<%=menu.getMenuname()%>"
										style="min-height: 150px; height: 150px; object-fit: cover; width: 100%;">
									<div class="card-body d-flex flex-column"
										style="flex: 1; overflow: hidden; padding: 15px;">
										<div
											class="d-flex justify-content-between align-items-center mb-2">
											<div>
												<h5 class="card-title" style="font-size: 1.1rem; margin: 0;"><%=menu.getMenuname()%></h5>
												<p class="card-text" style="font-size: 0.9rem; margin: 0;"><%=menu.getDescription()%></p>
											</div>
											<div class="badge bg-light text-dark"
												style="font-size: 0.875rem;">
												<%=menu.getPrice()%>
											</div>
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>


					<%
					}
					%>
				</div>
			</div>
			<%
			}
			%>

			<%
			if (!hasRestaurants && !hasMenus) {
			%>
			<!-- Both Restaurants and Menus Not Available -->
			<div class="col-12">
				<p class="text-center">No restaurants or menu items available
					for your search.</p>
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
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.2/js/bootstrap.min.js"></script>
</body>
</html>