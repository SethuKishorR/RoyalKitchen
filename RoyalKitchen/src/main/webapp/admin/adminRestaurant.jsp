<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="adminNavbar.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="com.tapfoods.model.Menu"%>

<%
admin = (Admin) session.getAttribute("admin");
Integer restaurantId = (Integer) session.getAttribute("restaurantId");
if (admin == null || admin.getAdminid() == null) {
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
	response.sendRedirect("adminSignIn.jsp");
	return;
}
List<Menu> menuItems = (List<Menu>) request.getAttribute("menuList");
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin</title>
<link rel="icon" href="styles/images/favicon.png" type="image/png">
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.2/css/bootstrap.min.css"
	rel="stylesheet">

<link rel="stylesheet" href="styles/restaurant.css">
<link rel="stylesheet" href="styles/menuItem.css">
<style>
html, body {
	height: 100%;
	margin: 0;
}

.full-height-container {
	height: 100vh;
}
</style>
</head>
<body class="bg-light">
	<div class="container mt-5 py-5">
		<div class="d-flex justify-content-center mt-3">
			<div class="card mb-2 col-md-12"
				style="background: #cb2c2c; backdrop-filter: blur(10px); border-radius: 10px; border: 1px solid rgba(255, 182, 193, 0.5); color: #fff;">
				<div
					class="card-body d-flex align-items-center justify-content-center"
					style="height: 70px;">
					<h5 class="mb-0 text-white">Admin Restaurant Menu</h5>
				</div>
			</div>
		</div>
		<div class="row mt-4">
			<%
			if (menuItems != null && !menuItems.isEmpty()) {
				for (Menu item : menuItems) {
					Integer itemRestaurantId = item.getRestaurantid();
					boolean isAvailable = "Available".equalsIgnoreCase(item.getIsavailable());
					String cardClass = isAvailable ? "" : "card-inactive";
					String labelClass = isAvailable ? "d-none" : "";
					String clickClass = isAvailable ? "" : "click-disabled";
			%>

			<div class="col-md-3 mb-4">
				<form action="adminmenuDetails" method="post">
					<input type="hidden" name="restaurantId"
						value="<%=restaurantId%>"> <input type="hidden"
						name="id" value="<%=item.getMenuid()%>">
					<div class="card <%=cardClass%> <%=clickClass%>"
						style="height: 270px; display: flex; flex-direction: column; overflow: hidden; cursor: pointer;"
						onclick="this.closest('form').submit();">
						<div class="position-relative">
							<div
								class="badge bg-danger text-light position-absolute d-flex top-0 start-0 m-2 <%=labelClass%>"
								style="font-size: 0.875rem;">Not Available</div>
							<img
								src="<%=item.getImagepath() != null ? item.getImagepath() : "styles/images/defaultImg.png"%>"
								class="card-img-top" alt="<%=item.getMenuname()%>"
								style="min-height: 150px; height: 150px; object-fit: cover; width: 100%;">
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
						<img src="styles/images/notFound.jpg" alt="No Results Found"
							class="img-fluid w-100" style="height: auto;">
						<div
							class="position-absolute top-50 start-50 translate-middle text-center w-100">
							<div class="alert alert-info mb-0" style="opacity: 0.85;"
								role="alert">No menu items found.</div>
						</div>
					</div>
				</div>
			</div>
			<%
			}
			%>
		</div>
	</div>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/2.9.2/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.2/js/bootstrap.min.js"></script>
</body>
</html>