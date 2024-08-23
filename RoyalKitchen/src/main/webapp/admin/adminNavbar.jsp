
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="com.tapfoods.model.Admin, com.tapfoods.model.Restaurant, com.tapfoods.model.Menu, java.util.ArrayList, com.tapfoods.DAO.MenuDAO, com.tapfoods.DAOImpl.MenuDAOImpl"%>

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
<%
ArrayList<Menu> menuList = new ArrayList<>();

if (admin != null) {
	try {
		MenuDAO menuDAO = new MenuDAOImpl();
		menuList = menuDAO.getMenusByRestaurantId(admin.getRestaurantid_fk());
	} catch (Exception e) {
		e.printStackTrace();
		request.setAttribute("errorMessage", "Error retrieving menu list: " + e.getMessage());
		request.getRequestDispatcher("error.jsp").forward(request, response);
		return;
	}
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
		: "RoyalKitchen"%>
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
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" id="menuDropdown"
						role="button" data-bs-toggle="dropdown" aria-expanded="true">
							Menu </a>
						<ul class="dropdown-menu dropdown-menu-center custom-menu mt-lg-4"
							aria-labelledby="menuDropdown">
							<li class="dropdown-item" id="addMenu" data-bs-toggle="modal"
								data-bs-target="#addMenuModal">Add Menu</li>
							<li class="dropdown-item" id="menuDetails" data-bs-toggle="modal"
								data-bs-target="#menuDetailsModal">Update Menu</li>
						</ul></li>
				</ul>
			</div>
		</div>
	</nav>

	<!--
    Add Menu Modal
    ----------------
    This modal is used for adding a new menu item to the restaurant. 
    It includes fields for menu name, price, description, availability, and image path.
    The Restaurant ID field is read-only and automatically populated based on the current restaurant.

    Key Elements:
    - Form action: "AddMenuServlet"
    - Form method: POST
    - Input fields: Menu Name, Price, Description, Availability, Image Path
    - Submit button: Inserts the menu item
-->
	<div class="modal fade" id="addMenuModal" tabindex="-1"
		aria-labelledby="addMenuModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="addMenuModalLabel">Add Menu Item</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"
						style="background-color: transparent; border: none;"
						onmouseover="this.style.backgroundColor='lightcoral';"
						onmouseout="this.style.backgroundColor='transparent';"></button>
				</div>
				<div class="modal-body custom-scroll">
					<form action="AddMenuServlet" method="post">
						<table class="table table-striped">
							<thead>
								<tr>
									<th>Field</th>
									<th>Value</th>
								</tr>
							</thead>
							<tbody>
								<!-- Restaurant ID (Disabled) -->
								<tr>
									<td>Restaurant ID</td>
									<td><input type="text" id="restaurantid"
										name="restaurantid"
										value="<%=restaurant != null ? restaurant.getRestaurantid() : ""%>"
										class="form-control field-editable" readonly
										style="border: 1px solid lightgray; background-color: transparent;"></td>
								</tr>
								<!-- Menu Name -->
								<tr>
									<td>Menu Name</td>
									<td><input type="text" id="menuname" name="menuname"
										class="form-control" placeholder="Enter menu name" required
										style="border: 1px solid lightgray; background-color: transparent;"></td>
								</tr>
								<!-- Price -->
								<tr>
									<td>Price</td>
									<td><input type="number" id="price" name="price"
										class="form-control" placeholder="Enter price" step="10"
										required
										style="border: 1px solid lightgray; background-color: transparent;"></td>
								</tr>
								<!-- Description -->
								<tr>
									<td>Description</td>
									<td><textarea id="description" name="description"
											class="form-control" rows="3" placeholder="Enter description"
											required
											style="border: 1px solid lightgray; background-color: transparent;"></textarea></td>
								</tr>
								<!-- Availability -->
								<tr>
									<td>Availability</td>
									<td><select id="isavailable" name="isavailable"
										class="form-select form-control" required
										style="border: 1px solid lightgray; background-color: transparent;">
											<option value="Available">Available</option>
											<option value="Unavailable">Unavailable</option>
									</select></td>
								</tr>
								<!-- Image Path -->
								<tr>
									<td>Image Path</td>
									<td><input type="text" id="imagepath" name="imagepath"
										class="form-control" placeholder="Enter image path"
										style="border: 1px solid lightgray; background-color: transparent;"></td>
								</tr>
							</tbody>
						</table>
						<button type="submit" class="btn btn-success">
							Insert <i class="fas fa-cloud-upload-alt"></i>
						</button>
					</form>
				</div>
				<div class="modal-footer d-flex">
					<button type="button" class="btn btn-danger"
						data-bs-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<!--
    Menu Details Modal
    ------------------
    This modal displays the details of all menu items for the current restaurant. 
    It includes a table showing menu items with options to edit or delete.

    Key Elements:
    - Displays a list of menu items if available
    - Each menu item has options to edit or delete
    - Form actions for editing and deleting menu items
    - If no menu items are found, a message with an image is shown

    Java Server Pages (JSP) Code:
    - Iterates over `menuList` to display each menu item's details
-->
	<div class="modal fade" id="menuDetailsModal" tabindex="-1"
		aria-labelledby="menuModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-xl modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="menuModalLabel">Menu Details</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"
						style="background-color: transparent; border: none;"
						onmouseover="this.style.backgroundColor='lightcoral';"
						onmouseout="this.style.backgroundColor='transparent';"></button>
				</div>
				<div class="modal-body">
					<%
					if (menuList != null && !menuList.isEmpty()) {
						int restaurantMenuId = 1;
						for (Menu menu : menuList) {
					%>
					<table class="table table-striped table-responsive">
						<thead>
							<tr>
								<td colspan="9">Restaurant Id: <%=admin.getRestaurantid_fk()%></td>
							</tr>
							<tr>
								<th>ID</th>
								<th>Name</th>
								<th>Price</th>
								<th>Description</th>
								<th>Availability</th>
								<th>ImagePath</th>
								<th class="text-center" colspan="3">Edit Menu</th>
							</tr>
						</thead>
						<tbody>
							<tr id="menuRow<%=restaurantMenuId%>">
								<td style="display: none;"><%=menu.getMenuid()%></td>
								<td class="p-md-2" style="width: 10%;"><input type="number"
									value="<%=restaurantMenuId%>"
									class="form-control input field-editable" readonly
									id="menuId<%=restaurantMenuId%>"
									style="border: solid 1px lightgray !important; background-color: transparent !important;"
									disabled /></td>
								<td class="p-md-2" style="width: 20%;"><input type="text"
									value="<%=menu.getMenuname()%>"
									class="form-control input field-editable"
									id="menuName<%=restaurantMenuId%>"
									style="border: solid 1px lightgray !important;" disabled /></td>
								<td class="p-md-2" style="width: 10%;"><input type="text"
									value="<%=menu.getPrice()%>"
									class="form-control input field-editable"
									id="menuPrice<%=restaurantMenuId%>"
									style="border: solid 1px lightgray !important;" disabled /></td>
								<td class="p-md-2" style="width: 30%;"><input type="text"
									value="<%=menu.getDescription()%>"
									class="form-control input field-editable"
									id="menuDescription<%=restaurantMenuId%>"
									style="border: solid 1px lightgray !important;" disabled /></td>
								<td class="p-md-2" style="width: 10%;"><select
									class="form-control input field-editable"
									id="menuAvailability<%=restaurantMenuId%>"
									name="menuAvailability"
									style="border: solid 1px lightgray !important;" disabled>
										<option value="Available" style="background-color: white;"
											<%="Available".equals(menu.getIsavailable()) ? "selected" : ""%>>Available</option>
										<option value="Not Available" style="background-color: white;"
											<%="Not Available".equals(menu.getIsavailable()) ? "selected" : ""%>>Not
											Available</option>
								</select></td>
								<td class="p-md-2" style="width: 10%;"><input type="text"
									value="<%=menu.getImagepath()%>"
									class="form-control input field-editable"
									id="imagePath<%=restaurantMenuId%>"
									style="border: solid 1px lightgray !important;" disabled /></td>
								<td class="p-md-2"
									style="text-align: center; vertical-align: middle;">
									<button type="button"
										class="btn btn-warning btn-sm edit-button"
										id="editButton<%=restaurantMenuId%>"
										style="height: 33px; width: 33px; border-radius: 4px; border: none; border-radius: 20px;"
										onclick="enableEditing(<%=restaurantMenuId%>)">
										<i class="fas fa-pencil-alt"></i>
									</button>
								</td>

								<td style="text-align: center; vertical-align: middle;">
									<form action="updateMenu" method="post"
										style="display: inline;">
										<input type="hidden" name="menuid"
											value="<%=menu.getMenuid()%>"> <input type="hidden"
											name="restaurantid" value="<%=admin.getRestaurantid_fk()%>">
										<input type="hidden" name="menuName"
											id="menuNameInput<%=restaurantMenuId%>"
											value="<%=menu.getMenuname()%>"> <input type="hidden"
											name="menuPrice" id="menuPriceInput<%=restaurantMenuId%>"
											value="<%=menu.getPrice()%>"> <input type="hidden"
											name="menuDescription"
											id="menuDescriptionInput<%=restaurantMenuId%>"
											value="<%=menu.getDescription()%>"> <input
											type="hidden" name="menuAvailability"
											id="menuAvailabilityInput<%=restaurantMenuId%>"
											value="<%=menu.getIsavailable()%>"> <input
											type="hidden" name="imagePath"
											id="imagePathInput<%=restaurantMenuId%>"
											value="<%=menu.getImagepath()%>">
										<button type="submit" class="btn btn-success btn-sm"
											onclick="updateHiddenInputs(<%=restaurantMenuId%>);"
											style="height: 33px; width: 33px; border-radius: 4px; border: none; border-radius: 20px;">
											<i class="fas fa-cloud-upload-alt"></i>
										</button>
									</form>
								</td>
								<td style="text-align: center; vertical-align: middle;"><a
									href="#" data-bs-toggle="modal"
									data-bs-target="#confirmDeleteMenuModal"
									class="btn btn-danger btn-sm delete-button"
									onclick="setModalData('<%=restaurantMenuId%>', '<%=menu.getMenuname()%>', '<%=menu.getMenuid()%>')"
									style="height: 33px; width: 33px; border-radius: 4px; border: none; border-radius: 20px;">
										<i class="fas fa-trash-alt"></i>
								</a></td>
							</tr>
						</tbody>
					</table>
					<%
					restaurantMenuId++;
					}
					} else {
					%>
					<div class="text-center" style="width: 100%;">
						<img src="styles/images/notFound.jpg" alt="Not Found"
							class="img-fluid" style="max-width: 50%; height: auto;">
						<p>No menus available for this restaurant.</p>
					</div>
					<%
					}
					%>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger"
						data-bs-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<!--
    Delete Confirmation Modal
    -------------------------
    This modal confirms the deletion of a menu item. It provides options to confirm or cancel the deletion.
    The menu ID to be deleted is passed to the servlet when the confirmation button is clicked.

    Key Elements:
    - Form action: "DeleteMenuServlet"
    - Form method: POST
    - Hidden input field: Menu ID (used by the servlet to identify which menu to delete)
    - Buttons: Confirm and Cancel
-->
	<div class="modal fade" id="confirmDeleteMenuModal" tabindex="-1"
		aria-labelledby="confirmDeleteMenuModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="confirmDeleteMenuModalLabel">Confirm
						Delete Menu Item</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"
						style="background-color: transparent; border: none;"
						onmouseover="this.style.backgroundColor='lightcoral';"
						onmouseout="this.style.backgroundColor='transparent';"></button>
				</div>
				<div class="modal-body">
					<form action="deleteMenu" method="post">
						<!-- Hidden fields to pass values to the servlet -->
						<input type="hidden" id="restaurantMenuIdInput"
							name="restaurantMenuId" /> <input type="hidden"
							id="menuNameInput" name="menuname" /> <input type="hidden"
							id="menuIdInput" name="menuid" />

						<div class="mb-3">
							<label for="restaurantMenuId" class="form-label">Menu ID</label>
							<input type="text" class="form-control" id="restaurantMenuId"
								name="restaurantMenuId" readonly style="border: none;" />
						</div>

						<div class="mb-3">
							<label for="menuName" class="form-label">Menu Name</label> <input
								type="text" class="form-control" id="menuName" name="menuname"
								readonly style="border: none;" />
						</div>

						<div class="mb-3">
							<label for="adminPassword" class="form-label">Admin
								Password</label>
							<div class="input-group">
								<input type="password" class="form-control" id="adminPassword"
									name="adminPassword" placeholder="Enter admin password"
									required style="border: solid 1px lightgray;" />
								<div class="input-group-append">
									<span class="input-group-text" id="toggle-password"
										data-toggle="password" data-target="adminPassword"> <i
										class="fas fa-eye" id="adminPasswordEye"></i>
									</span>
								</div>
							</div>
						</div>

						<button type="submit" class="btn btn-danger">
							Delete <i class="fas fa-trash-alt"></i>
						</button>
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
    Profile Modal: Displays detailed information about the admin profile.
    --------------------
    This modal shows the admin's profile details in a tabular format, including Admin ID, Restaurant ID, Admin Key, and Password.
    It includes a toggle feature to show or hide the Admin Key and Password fields.
    Additionally, a logout button is provided to end the current session.

    Key Elements:
    - Modal Header: Contains the title and a close button.
    - Modal Body: Displays profile details in a table and includes a logout form.
    - Modal Footer: Contains a close button to dismiss the modal.
-->
	<div class="modal fade show" id="profileModal" tabindex="-1"
		aria-labelledby="profileModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h5 class="modal-title" id="profileModalLabel">Profile Details</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"
						style="background-color: transparent; border: none;"
						onmouseover="this.style.backgroundColor='lightcoral';"
						onmouseout="this.style.backgroundColor='transparent';"></button>
				</div>
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
						<button type="submit" class="btn btn-warning">
							Logout <i class="fas fa-arrow-right" style="font-size: 13px;"></i>
						</button>
					</form>
				</div>
				<!-- Modal Footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-danger"
						data-bs-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<%
	// Check if the request method is POST and if the "logout" parameter is set to "true"
	if ("POST".equalsIgnoreCase(request.getMethod()) && "true".equals(request.getParameter("logout"))) {
		// Invalidate the current session to log out the user
		session.invalidate();
	%>
	<!--
            Use JavaScript to refresh the current page after invalidating the session.
            This ensures that the user is redirected to the login page or an appropriate page 
            after logging out.
        -->
	<script type="text/javascript">
            // Refresh the current page to reflect the logout action
            window.location.reload();
        </script>
	<!-- Terminate the current script execution -->
	return;
	<%
	}
	// Set HTTP response headers to prevent caching of the page
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
	%>

	<!--
    Update Admin Profile Modal: Provides a form to update the admin profile information.
    --------------------
    This modal allows the admin to update their profile information including Admin ID, Restaurant ID, Admin Key, and Password.
    Fields are presented in a table format, with the Admin ID and Restaurant ID fields disabled (non-editable).
    The Admin Key and Password fields include a visibility toggle to show or hide the values.

    Key Elements:
    - Modal Header: Contains the title and a close button.
    - Modal Body: Includes a form with fields for Admin ID, Restaurant ID, Admin Key, and Password, along with a button to toggle visibility.
    - Modal Footer: Contains a close button to dismiss the modal.
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
											disabled
											style="height: 33px; width: 33px; border-radius: 4px; border: none; border-radius: 20px;">
											<i class="fas fa-lock"></i>
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
											disabled
											style="height: 33px; width: 33px; border-radius: 4px; border: none; border-radius: 20px;">
											<i class="fas fa-lock"></i>
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
											disabled
											style="height: 33px; width: 33px; border-radius: 4px; border: none; border-radius: 20px;">
											<i class="fas fa-lock"></i>
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
											class="btn btn-warning btn-sm"
											style="height: 33px; width: 33px; border-radius: 4px; border: none; border-radius: 20px;">
											<i class="fas fa-pencil-alt"></i>
										</button>
									</td>
								</tr>
							</tbody>
						</table>
						<button type="submit" class="btn btn-success" id="saveChanges">
							Update <i class="fas fa-cloud-upload-alt"></i>
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
    Delete Admin Profile Modal: Confirms deletion of the admin profile.
    --------------------
    This modal asks for confirmation before deleting the admin profile. It provides an option to cancel or proceed with deletion.
    Note: If the profile is deleted, the associated restaurant profile will also be deleted.

    Key Elements:
    - Modal Header: Contains the title "Delete Admin Profile" and a close button.
    - Modal Body: Displays a confirmation message about deleting the admin profile and associated restaurant profile.
    - Modal Footer: Contains buttons to either cancel or proceed with the deletion.
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
    Confirm Delete Admin Profile Modal: Asks for admin key and password to confirm profile deletion.
    --------------------
    This modal prompts the user to confirm the deletion of their admin profile by entering their admin key and password.
    The form will submit the data to the server for deletion if confirmed.

    Key Elements:
    - Modal Header: Contains the title "Confirm Delete Admin Profile" and a close button.
    - Modal Body: Contains a form with fields for the admin key and password. Includes visibility toggle for the password fields.
    - Modal Footer: Contains a "Cancel" button to dismiss the modal.
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
						<button type="submit" class="btn btn-danger">
							Delete <i class="fas fa-trash-alt"></i>
						</button>
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
    Restaurant Details Modal: Displays detailed information about the restaurant.
    --------------------
    This modal shows the restaurant's details in a tabular format, including Restaurant ID, Name, Delivery Time, Cuisine Type, Address, Ratings, Active Status, and Image Path.

    Key Elements:
    - Modal Header: Contains the title "Restaurant Details" and a close button.
    - Modal Body: Displays restaurant details in a table.
    - Modal Footer: Contains a "Close" button to dismiss the modal.
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
    Update Restaurant Profile Modal: Allows for updating various details of a restaurant.
    --------------------
    This modal provides a form to update restaurant details such as Restaurant ID, Name, Delivery Time, Cuisine Type, Address, Ratings, Active Status, and Image Path.
    Fields are initially disabled and become editable when their respective edit button is clicked.

    Key Elements:
    - Modal Header: Contains the title "Update Restaurant Profile" and a close button.
    - Modal Body: Includes a table with editable fields and corresponding buttons for editing each field.
    - Modal Footer: Contains a "Close" button to dismiss the modal.
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
											disabled
											style="height: 33px; width: 33px; border-radius: 4px; border: none; border-radius: 20px;">
											<i class="fas fa-lock"></i>
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
											class="btn btn-warning btn-sm"
											style="height: 33px; width: 33px; border-radius: 4px; border: none; border-radius: 20px;">
											<i class="fas fa-pencil-alt"></i>
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
											class="btn btn-warning btn-sm"
											style="height: 33px; width: 33px; border-radius: 4px; border: none; border-radius: 20px;">
											<i class="fas fa-pencil-alt"></i>
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
											class="btn btn-warning btn-sm"
											style="height: 33px; width: 33px; border-radius: 4px; border: none; border-radius: 20px;">
											<i class="fas fa-pencil-alt"></i>
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
											class="btn btn-warning btn-sm"
											style="height: 33px; width: 33px; border-radius: 4px; border: none; border-radius: 20px;">
											<i class="fas fa-pencil-alt"></i>
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
											class="btn btn-warning btn-sm"
											style="height: 33px; width: 33px; border-radius: 4px; border: none; border-radius: 20px;">
											<i class="fas fa-pencil-alt"></i>
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
											class="btn btn-warning btn-sm"
											style="height: 33px; width: 33px; border-radius: 4px; border: none; border-radius: 20px;">
											<i class="fas fa-pencil-alt"></i>
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
											class="btn btn-warning btn-sm"
											style="height: 33px; width: 33px; border-radius: 4px; border: none; border-radius: 20px;">
											<i class="fas fa-pencil-alt"></i>
										</button>
									</td>
								</tr>
							</tbody>
						</table>
						<button type="submit" class="btn btn-success" id="saveChanges">
							Update <i class="fas fa-cloud-upload-alt"></i>
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
    Delete Restaurant Profile Modal: Displays a message about deletion restrictions due to foreign key constraints.
    --------------------
    This modal informs users that they cannot delete the restaurant profile directly here because of foreign key constraints with the admin profile. It directs users to visit the admin profile to perform the deletion.

    Key Elements:
    - Modal Header: Contains the title "Delete Restaurant Profile" and a close button.
    - Modal Body: Displays a message about the restriction and provides guidance to visit the admin profile for deletion.
    - Modal Footer: Contains a "Close" button to dismiss the modal.
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

	<script type="text/javascript" src="menuUpdateDelete.js"></script>
	<script type="text/javascript" src="adminProfile.js"></script>
	<script type="text/javascript" src="menuProfile.js"></script>
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