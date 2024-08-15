<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="com.tapfoods.model.Admin, com.tapfoods.model.Restaurant"%>

<%
/**
 * <p>Retrieve the admin and restaurant objects from the session.</p>
 * <p>The <code>admin</code> object holds information about the currently logged-in admin.</p>
 * <p>The <code>restaurant</code> object holds information about the associated restaurant.</p>
 */
Admin admin = (Admin) session.getAttribute("admin");
Restaurant restaurant = (Restaurant) session.getAttribute("restaurant");

/**
 * <p>Check if the admin is logged in by validating the <code>admin</code> object and its ID.</p>
 * <p>If the <code>admin</code> object is null or its ID is missing, it indicates that the admin is not logged in.</p>
 * <p>In such cases, set cache control headers to prevent caching and redirect to the <code>adminSignIn.jsp</code> page.</p>
 */
if (admin == null || admin.getAdminid() == null) {
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
	response.sendRedirect("adminSignIn.jsp");
	return;
}
%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- 
    <p>Set the title of the page, incorporating the restaurant name if available.</p>
    <p>The title helps in identifying the page and improves user experience by reflecting the current restaurant name.</p>
 -->
<title><%=(restaurant != null && restaurant.getRestaurantname() != null && restaurant.getRestaurantname().length() > 0)
		? restaurant.getRestaurantname() + " - "
		: ""%> Admin Profile</title>
<link rel="icon" href="styles/images/favicon.png" type="image/png">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"
	rel="stylesheet">
<!-- 
    <p>Include Bootstrap CSS from a CDN for consistent styling and responsive design.</p>
 -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
	rel="stylesheet">
<!-- 
    <p>Include custom stylesheets for the layout, components, and profile forms.</p>
    <p>These stylesheets provide additional styling specific to this application, ensuring a consistent look and feel.</p>
 -->
<link rel="stylesheet" href="styles/layouts.css">
<link rel="stylesheet" href="styles/components.css">
<link rel="stylesheet" href="styles/forms-profile.css">
</head>

<body>
	<!--
    Navigation Bar: The navigation bar is fixed to the top of the page and includes:
    - A brand logo and name, which links to the home page.
    - A toggler button for collapsing the navbar on smaller screens.
    - Navigation links organized in dropdown menus for "Admin" and "Restaurant" sections.
-->
	<nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
		<div class="container-fluid">
			<a class="navbar-brand ms-4" href="#"> <img
				src="styles/images/logo.png" alt="" width="30" height="28"
				class="d-inline-block align-text-top"> <%=(restaurant != null && restaurant.getRestaurantname() != null && restaurant.getRestaurantname().length() > 0)
		? restaurant.getRestaurantname()
		: "RoyalKitchen"%> - Admin Profile
			</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<i class="fas fa-bars"></i>
			</button>
			<div class="collapse navbar-collapse mt-3 mt-lg-0"
				id="navbarSupportedContent">
				<ul class="navbar-nav ms-auto mb-2 mb-lg-0">
					<!-- Admin Dropdown Menu -->
					<!-- - Admin Details : Opens the profile details modal.
    					 - Update Admin : Opens the update admin modal.
    				 	 - Delete Admin : Opens the delete admin modal.
					-->
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" id="profileDropdown"
						role="button" data-bs-toggle="dropdown" aria-expanded="true">
							Admin </a>
						<ul class="dropdown-menu dropdown-menu-center custom-menu mt-lg-4"
							aria-labelledby="profileDropdown">
							<li class="dropdown-item" id="profileDetails"
								data-bs-toggle="modal" data-bs-target="#profileModal">
								Admin Details</li>
							<li class="dropdown-item" id="updateProfile"
								data-bs-toggle="modal" data-bs-target="#updateModal">
								Update Admin</li>
							<li class="dropdown-item" id="deleteProfile"
								data-bs-toggle="modal" data-bs-target="#deleteModal">
								Delete Admin</li>
						</ul></li>
					<!-- Restaurant Dropdown Menu -->
					<!-- - Restaurant Details : Opens the restaurant profile details modal.
				    - Update Restaurant : Opens the update restaurant modal.
				    - Delete Restaurant : Opens the delete restaurant modal.
					-->
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" id="restaurantDropdown"
						role="button" data-bs-toggle="dropdown" aria-expanded="true">
							Restaurant </a>
						<ul class="dropdown-menu dropdown-menu-center custom-menu mt-lg-4"
							aria-labelledby="restaurantDropdown">
							<li class="dropdown-item" id="restaurantDetails"
								data-bs-toggle="modal" data-bs-target="#restaurantProfileModal">
								Restaurant Details</li>
							<li class="dropdown-item" id="updateRestaurant"
								data-bs-toggle="modal" data-bs-target="#updateRestaurantModal">
								Update Restaurant</li>
							<li class="dropdown-item" id="deleteRestaurant"
								data-bs-toggle="modal" data-bs-target="#deleteRestaurantModal">
								Delete Restaurant</li>
						</ul></li>
				</ul>
			</div>
		</div>
	</nav>

	<!-- Profile Modal: Displays detailed information about the admin profile. -->
	<div class="modal fade show" id="profileModal" tabindex="-1"
		aria-labelledby="profileModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<!-- Modal Header -->
				<!-- - Title: "Profile Details".
				    - Close button with hover effect that changes background color.
					-->
				<div class="modal-header">
					<h5 class="modal-title" id="profileModalLabel">Profile Details</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"
						style="background-color: transparent; border: none;"
						onmouseover="this.style.backgroundColor='lightcoral';"
						onmouseout="this.style.backgroundColor='transparent';"></button>
				</div>
				<!-- Modal Body:
			   		- Table with fields displaying admin details:
			        - Admin ID: Non-editable field.
			        - Restaurant ID: Non-editable field.
			        - Admin Key: Non-editable field with a toggle to show/hide password.
			        - Password: Non-editable field with a toggle to show/hide password.
			   		- Logout button within a form that submits a hidden "logout" field to the "adminNavbar.jsp" page.
				-->
				<div class="modal-body">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Field</th>
								<th>Value</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Admin ID</td>
								<td><input type="text" id="profileAdminId"
									value="<%=admin != null ? admin.getAdminid() : ""%>"
									class="form-control field-editable" disabled
									style="border: 1px solid lightgray;"></td>
							</tr>
							<tr>
								<td>Restaurant ID</td>
								<td><input type="text" id="profileRestaurantId"
									value="<%=admin != null ? admin.getRestaurantid_fk() : ""%>"
									class="form-control field-editable" disabled
									style="border: 1px solid lightgray;"></td>
							</tr>
							<tr>
								<td>Admin Key</td>
								<td>
									<div class="input-group">
										<input type="password" id="profileAdminKey"
											value="<%=admin != null ? admin.getAdminkey() : ""%>"
											class="form-control field-editable" disabled
											style="border: 1px solid lightgray;">
										<div class="input-group-append" style="border-radius: 10px;">
											<span class="input-group-text" id="toggle-profile-admin-key"
												data-toggle="password" data-target="profileAdminKey"
												style="height: 38px; line-height: 38px; background-color: white;">
												<i class="fas fa-eye" id="profileAdminKeyEye"></i>
											</span>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td>Password</td>
								<td>
									<div class="input-group">
										<input type="password" id="profilePassword"
											value="<%=admin != null ? admin.getPassword() : ""%>"
											class="form-control field-editable" disabled
											style="border: 1px solid lightgray;">
										<div class="input-group-append" style="border-radius: 10px;">
											<span class="input-group-text" id="toggle-profile-password"
												data-toggle="password" data-target="profilePassword"
												style="height: 38px; line-height: 38px; background-color: white;">
												<i class="fas fa-eye" id="profilePasswordEye"></i>
											</span>
										</div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					<form action="adminNavbar.jsp" method="post"
						style="display: inline;">
						<input type="hidden" name="logout" value="true">
						<button type="submit" class="btn btn-warning">Logout</button>
					</form>
				</div>
				<!-- Modal Footer:
    - Close button to dismiss the modal.
-->
				<div class="modal-footer">
					<button type="button" class="btn btn-danger"
						data-bs-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<!--
    This JSP file handles the display and functionality of the profile modal.
    It includes:
    - Modal for displaying profile details with editable fields.
    - Form for logging out the admin user.
    - JavaScript functions for handling password visibility toggle.
    - Cache control headers to prevent caching of the response.

    Components:
    - Profile Modal: Displays admin details and allows for logout.
    - Modal Header: Contains the title and close button.
    - Modal Body: Contains a table with admin details and a logout button.
    - Modal Footer: Contains the close button for the modal.
-->
	<%
	if ("POST".equalsIgnoreCase(request.getMethod()) && "true".equals(request.getParameter("logout"))) {
		session.invalidate();
		response.sendRedirect("adminSignIn.jsp");
	}
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
	%>

	<!--
    Update Admin Modal:
    This modal allows the admin to update their profile details. It includes:
    - A header with the modal title and a close button.
    - A body containing a form for updating profile details.
    - A table displaying various fields with corresponding values and actions.
    - Fields for Admin ID, Restaurant ID, Admin Key, and Password.
    - Buttons to edit or update specific fields, with associated icons.
    - A footer with a close button to dismiss the modal.

    Components:
    - Modal Header: Displays the title and a close button for the modal.
    - Modal Body: Contains the form with a table for displaying and editing profile information.
        - Admin ID and Restaurant ID fields are disabled and cannot be edited.
        - Admin Key and Password fields include a visibility toggle feature.
        - An "Edit" button is available for the Password field.
    - Modal Footer: Contains the close button to dismiss the modal.
-->
	<div class="modal fade" id="updateModal" tabindex="-1"
		aria-labelledby="updateModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="updateModalLabel">Update Profile</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"
						style="background-color: transparent; border: none;"
						onmouseover="this.style.backgroundColor='lightcoral';"
						onmouseout="this.style.backgroundColor='transparent';"></button>
				</div>
				<div class="modal-body">
					<form action="updateAdminProfile" method="post">
						<table class="table table-striped">
							<thead>
								<tr>
									<th>Field</th>
									<th>Value</th>
									<th class="text-center">Actions</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>Admin ID</td>
									<td><input type="text" id="adminIdField" name="adminid"
										value="<%=admin != null ? admin.getAdminid() : ""%>"
										class="form-control field-editable" disabled
										style="border: 1px solid lightgray;"></td>
									<td class="text-center">
										<button type="button" class="btn btn-secondary btn-sm"
											disabled>
											<i class="fas fa-lock"></i> Cannot Edit
										</button>
									</td>
								</tr>
								<tr>
									<td>Restaurant ID</td>
									<td><input type="text" id="restaurantIdField"
										name="restaurantid"
										value="<%=admin != null ? admin.getRestaurantid_fk() : ""%>"
										class="form-control field-editable" disabled
										style="border: 1px solid lightgray;"></td>
									<td class="text-center">
										<button type="button" class="btn btn-secondary btn-sm"
											disabled>
											<i class="fas fa-lock"></i> Cannot Edit
										</button>
									</td>
								</tr>
								<tr>
									<td>Admin Key</td>
									<td>
										<div class="input-group">
											<input type="password" id="adminKeyField" name="adminkey"
												value="<%=admin != null ? admin.getAdminkey() : ""%>"
												class="form-control field-editable" disabled
												style="border: 1px solid lightgray;">
											<div class="input-group-append" style="border-radius: 10px;">
												<span class="input-group-text" id="toggle-admin-key-field"
													data-toggle="password" data-target="adminKeyField"
													style="height: 38px; line-height: 38px; background-color: white;">
													<i class="fas fa-eye" id="adminKeyEye"></i>
												</span>
											</div>
										</div>
									</td>
									<td class="text-center">
										<button type="button" class="btn btn-secondary btn-sm"
											disabled>
											<i class="fas fa-lock"></i> Cannot Edit
										</button>
									</td>
								</tr>
								<tr>
									<td>Password</td>
									<td>
										<div class="input-group">
											<input type="password" id="passwordField" name="password"
												value="<%=admin != null ? admin.getPassword() : ""%>"
												class="form-control field-editable" disabled
												style="border: 1px solid lightgray;">
											<div class="input-group-append" style="border-radius: 10px;">
												<span class="input-group-text" id="toggle-password-field"
													data-toggle="password" data-target="passwordField"
													style="height: 38px; line-height: 38px; background-color: white;">
													<i class="fas fa-eye" id="passwordEye"></i>
												</span>
											</div>
										</div>
									</td>
									<td class="text-center">
										<button type="button" id="editPasswordBtn"
											class="btn btn-warning btn-sm">
											<i class="fas fa-edit"></i> Edit
										</button>
									</td>
								</tr>
							</tbody>
						</table>
						<button type="submit" class="btn btn-success" id="saveChanges">Update</button>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger"
						data-bs-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<!--
    Delete Admin Modal:
    This modal prompts the admin to confirm their decision to delete their profile. It includes:
    - A header with the modal title and a close button.
    - A body with a confirmation message indicating that deleting the admin profile will also delete the associated restaurant profile.
    - A footer with "Cancel" and "Yes, Delete Profile" buttons.
        - "Cancel" closes the modal.
        - "Yes, Delete Profile" opens the Confirmation Modal to confirm the delete action.
	-->
	<div class="modal fade" id="deleteModal" tabindex="-1"
		aria-labelledby="deleteModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="deleteModalLabel">Delete Admin
						Profile</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"
						style="background-color: transparent; border: none;"
						onmouseover="this.style.backgroundColor='lightcoral';"
						onmouseout="this.style.backgroundColor='transparent';"></button>
				</div>
				<div class="modal-body">
					<p>Are you sure you want to delete your admin profile? If you
						proceed, your restaurant profile will also be deleted.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-danger" data-bs-toggle="modal"
						data-bs-target="#confirmDeleteModal">Yes, Delete Profile</button>
				</div>
			</div>
		</div>
	</div>

	<!--
    Confirmation Modal:
    This modal appears after the admin confirms the deletion of their profile. It includes:
    - A header with the modal title and a close button.
    - A body with a form that requires the admin to enter their Admin Key and Password for final confirmation.
        - Admin Key input field: Pre-filled with the current Admin Key and includes a visibility toggle.
        - Password input field: Requires the admin to enter their password to confirm the deletion.
    - A footer with "Cancel" and "Confirm Delete" buttons.
        - "Cancel" closes the modal.
        - "Confirm Delete" submits the form to delete the admin profile.
	-->
	<div class="modal fade" id="confirmDeleteModal" tabindex="-1"
		aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="confirmDeleteModalLabel">Confirm
						Delete Admin Profile</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"
						style="background-color: transparent; border: none;"
						onmouseover="this.style.backgroundColor='lightcoral';"
						onmouseout="this.style.backgroundColor='transparent';"></button>
				</div>
				<div class="modal-body">
					<form action="deleteAdminProfile" method="post">
						<div class="mb-3">
							<label for="adminKey" class="form-label">Admin Key</label>
							<div class="input-group">
								<input type="password" class="form-control" id="adminKey"
									name="adminkey" required
									style="border: 1px solid lightgray; background-color: lightcoral;"
									value="<%=admin != null ? admin.getAdminkey() : ""%>" readonly>
								<div class="input-group-append">
									<span class="input-group-text" id="toggle-admin-key"
										data-toggle="password" data-target="adminKey"> <i
										class="fas fa-eye" id="adminKeyEye"></i>
									</span>
								</div>
							</div>
						</div>
						<div class="mb-3">
							<label for="password" class="form-label">Password</label>
							<div class="input-group">
								<input type="password" class="form-control" id="password"
									name="password" placeholder="Enter password" required
									style="border: solid 1px lightgray;">
								<div class="input-group-append">
									<span class="input-group-text" id="toggle-password"
										data-toggle="password" data-target="password"> <i
										class="fas fa-eye"></i>
									</span>
								</div>
							</div>
						</div>
						<button type="submit" class="btn btn-danger">Confirm
							Delete</button>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Cancel</button>
				</div>
			</div>
		</div>
	</div>

	<!--
    Restaurant Profile Modal:
    This modal displays detailed information about a restaurant. It includes:
    - A header with the modal title ("Restaurant Details") and a close button.
    - A body containing a table with the following details:
        - Restaurant ID
        - Restaurant Name
        - Delivery Time (in minutes)
        - Cuisine Type
        - Address
        - Ratings
        - Active Status
        - Image Path
      Each field displays information from the `restaurant` object, with default empty values if `restaurant` is null.
    - A footer with a "Close" button to dismiss the modal.
    - The modal content is scrollable if needed (with the `custom-scroll` class).
	-->
	<div class="modal fade" id="restaurantProfileModal" tabindex="-1"
		aria-labelledby="restaurantProfileModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="restaurantProfileModalLabel">Restaurant
						Details</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"
						style="background-color: transparent; border: none;"
						onmouseover="this.style.backgroundColor='lightcoral';"
						onmouseout="this.style.backgroundColor='transparent';"></button>
				</div>
				<div class="modal-body custom-scroll">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Field</th>
								<th>Value</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Restaurant ID</td>
								<td><%=restaurant != null ? restaurant.getRestaurantid() : ""%></td>
							</tr>
							<tr>
								<td>Restaurant Name</td>
								<td><%=restaurant != null ? restaurant.getRestaurantname() : ""%></td>
							</tr>
							<tr>
								<td>Delivery Time</td>
								<td><%=restaurant != null ? restaurant.getDeliverytime() + " mins" : ""%></td>
							</tr>
							<tr>
								<td>Cuisine Type</td>
								<td><%=restaurant != null ? restaurant.getCuisinetype() : ""%></td>
							</tr>
							<tr>
								<td>Address</td>
								<td><%=restaurant != null ? restaurant.getAddress() : ""%></td>
							</tr>
							<tr>
								<td>Ratings</td>
								<td><%=restaurant != null ? restaurant.getRatings() : ""%></td>
							</tr>
							<tr>
								<td>Active Status</td>
								<td><%=restaurant != null ? restaurant.getIsactive() : ""%></td>
							</tr>
							<tr>
								<td>Image Path</td>
								<td><%=restaurant != null ? restaurant.getImagepath() : ""%></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger"
						data-bs-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<!--
    Update Restaurant Modal:
    This modal allows updating restaurant profile information. It includes:
    - A header with the modal title ("Update Restaurant Profile") and a close button.
    - A body containing a form with fields for updating various restaurant details:
        - Restaurant ID (disabled and non-editable)
        - Restaurant Name
        - Delivery Time
        - Cuisine Type
        - Address
        - Ratings
        - Active Status (dropdown with "Active" and "Inactive" options)
        - Image Path
      Each editable field is initially disabled and has an "Edit" button to enable editing. The "Edit" buttons are styled with a warning color. 
    - A submit button ("Update") to save changes to the restaurant profile.
    - A footer with a "Close" button to dismiss the modal.
    - The modal content is scrollable if needed (with the `custom-scroll` class).
	-->
	<div class="modal fade" id="updateRestaurantModal" tabindex="-1"
		aria-labelledby="updateRestaurantModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="updateRestaurantModalLabel">Update
						Restaurant Profile</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"
						style="background-color: transparent; border: none;"
						onmouseover="this.style.backgroundColor='lightcoral';"
						onmouseout="this.style.backgroundColor='transparent';"></button>
				</div>
				<div class="modal-body custom-scroll">
					<form action="updateRestaurantProfile" method="post">
						<table class="table table-striped">
							<thead>
								<tr>
									<th>Field</th>
									<th>Value</th>
									<th class="text-center">Actions</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>Restaurant ID</td>
									<td><input type="text" id="restaurantIdField"
										name="restaurantid"
										value="<%=restaurant != null ? restaurant.getRestaurantid() : ""%>"
										class="form-control field-editable" disabled
										style="border: 1px solid lightgray;"></td>
									<td class="text-center">
										<button type="button" class="btn btn-secondary btn-sm"
											disabled>
											<i class="fas fa-lock"></i> Cannot Edit
										</button>
									</td>
								</tr>
								<tr>
									<td>Restaurant Name</td>
									<td><input type="text" id="restaurantNameField"
										name="restaurantname"
										value="<%=restaurant != null ? restaurant.getRestaurantname() : ""%>"
										class="form-control field-editable" disabled
										style="border: 1px solid lightgray;"></td>
									<td class="text-center">
										<button type="button" id="editRestaurantNameBtn"
											class="btn btn-warning btn-sm">
											<i class="fas fa-edit"></i> Edit
										</button>
									</td>
								</tr>
								<tr>
									<td>Delivery Time</td>
									<td><input type="text" id="deliveryTimeField"
										name="deliverytime"
										value="<%=restaurant != null ? restaurant.getDeliverytime() : ""%>"
										class="form-control field-editable" disabled
										style="border: 1px solid lightgray;"></td>
									<td class="text-center">
										<button type="button" id="editDeliveryTimeBtn"
											class="btn btn-warning btn-sm">
											<i class="fas fa-edit"></i> Edit
										</button>
									</td>
								</tr>
								<tr>
									<td>Cuisine Type</td>
									<td><input type="text" id="cuisineTypeField"
										name="cuisinetype"
										value="<%=restaurant != null ? restaurant.getCuisinetype() : ""%>"
										class="form-control field-editable" disabled
										style="border: 1px solid lightgray;"></td>
									<td class="text-center">
										<button type="button" id="editCuisineTypeBtn"
											class="btn btn-warning btn-sm">
											<i class="fas fa-edit"></i> Edit
										</button>
									</td>
								</tr>
								<tr>
									<td>Address</td>
									<td><input type="text" id="addressField" name="address"
										value="<%=restaurant != null ? restaurant.getAddress() : ""%>"
										class="form-control field-editable" disabled
										style="border: 1px solid lightgray;"></td>
									<td class="text-center">
										<button type="button" id="editAddressBtn"
											class="btn btn-warning btn-sm">
											<i class="fas fa-edit"></i> Edit
										</button>
									</td>
								</tr>
								<tr>
									<td>Ratings</td>
									<td><input type="text" id="ratingsField" name="ratings"
										value="<%=restaurant != null ? restaurant.getRatings() : ""%>"
										class="form-control field-editable" disabled
										style="border: 1px solid lightgray;"></td>
									<td class="text-center">
										<button type="button" id="editRatingsBtn"
											class="btn btn-warning btn-sm">
											<i class="fas fa-edit"></i> Edit
										</button>
									</td>
								</tr>
								<tr>
									<td>Active Status</td>
									<td><select id="isActiveField" name="isactive"
										class="form-control field-editable" disabled
										style="border: 1px solid lightgray;">
											<!-- Default option for when no status is selected -->
											<option value="" disabled
												<%=restaurant == null || restaurant.getIsactive() == null ? "selected" : ""%>>Select
												Status</option>
											<!-- Check with "Active" and "Inactive" strings instead of 0 and 1 -->
											<option value="Active"
												<%=restaurant != null && "Active".equals(restaurant.getIsactive()) ? "selected" : ""%>>Active</option>
											<option value="Inactive"
												<%=restaurant != null && "Inactive".equals(restaurant.getIsactive()) ? "selected" : ""%>>Inactive</option>
									</select></td>
									<td class="text-center">
										<button type="button" id="editIsActiveBtn"
											class="btn btn-warning btn-sm">
											<i class="fas fa-edit"></i> Edit
										</button>
									</td>
								</tr>
								<tr>
									<td>Image Path</td>
									<td><input type="text" id="imagePathField"
										name="imagepath"
										value="<%=restaurant != null ? restaurant.getImagepath() : ""%>"
										class="form-control field-editable" disabled
										style="border: 1px solid lightgray;"></td>
									<td class="text-center">
										<button type="button" id="editImagePathBtn"
											class="btn btn-warning btn-sm">
											<i class="fas fa-edit"></i> Edit
										</button>
									</td>
								</tr>
							</tbody>
						</table>
						<button type="submit" class="btn btn-success" id="saveChanges">Update
						</button>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger"
						data-bs-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<!--
    Delete Restaurant Modal:
    This modal provides a notification that deleting the restaurant profile directly from this modal is not possible due to foreign key constraints with the admin profile.
    Instead, it directs the user to the admin profile for deletion.
    - A header with the modal title ("Delete Restaurant Profile") and a close button.
    - A body containing a message explaining the restriction and directing the user to the admin profile for the deletion process.
    - A footer with a "Close" button to dismiss the modal.
	-->
	<div class="modal fade" id="deleteRestaurantModal" tabindex="-1"
		aria-labelledby="deleteRestaurantModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="deleteRestaurantModalLabel">Delete
						Restaurant Profile</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"
						style="background-color: transparent; border: none;"
						onmouseover="this.style.backgroundColor='lightcoral';"
						onmouseout="this.style.backgroundColor='transparent';"></button>
				</div>
				<div class="modal-body">
					<p>Due to the foreign key constraints with the admin profile,
						you cannot delete the restaurant profile directly here. Please
						visit the admin profile to perform the deletion.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger"
						data-bs-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="adminProfile.js"></script>
	<script type="text/javascript" src="restaurantProfile.js"></script>
	<script type="text/javascript" src="styles/password.js"></script>
	<!-- Include jQuery slim version from CDN (Content Delivery Network) -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>

	<!-- Include Popper.js for handling popovers and tooltips -->
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>

	<!-- Include Bootstrap 5.0.2 JavaScript for Bootstrap components -->
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.2/js/bootstrap.min.js"></script>

	<!-- Include Bootstrap 5.0.2 JavaScript bundle with Popper.js integrated -->

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
	<!--
    External JavaScript Files:
    - `adminProfile.js`: Contains JavaScript functions and logic specific to admin profile operations.
    - `restaurantProfile.js`: Contains JavaScript functions and logic specific to restaurant profile operations.
    - `styles/password.js`: Contains JavaScript for handling password visibility toggle.

    External Libraries and Frameworks:
    - jQuery (Slim version): Provides a lightweight and efficient way to handle DOM manipulation and events.
    - Popper.js: Required for Bootstrap's tooltips and popovers.
    - Bootstrap JavaScript (v5.0.2): Provides Bootstrap's interactive components such as modals, dropdowns, and tooltips.
    - Bootstrap JavaScript Bundle (v5.0.2): Includes Bootstrap's JavaScript components along with Popper.js.

    Note: Ensure that `bootstrap.bundle.min.js` is included last to avoid conflicts with other scripts and to ensure all components are initialized properly.
	-->

</body>
</html>