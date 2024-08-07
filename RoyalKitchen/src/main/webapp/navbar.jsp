<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tapfoods.model.User"%>

<%
/**
 * <p>This code block retrieves the user object from the session. If the user is not authenticated or their email is not set, 
 * it redirects the user to the sign-in page.</p>
 * 
 * <p>The check ensures that the user is logged in and has a valid email address before allowing access to the current page. 
 * If the user is not authenticated, they are redirected to the sign-in page to log in.</p>
 * 
 * <p>The cache control headers are set to prevent caching of the redirection page. This ensures that the browser or any 
 * intermediary caches do not store the redirected page and always fetches the latest version when the user is redirected 
 * to the sign-in page.</p>
 */
User user = (User) session.getAttribute("user");
if (user == null || user.getEmail() == null || user.getEmail().isEmpty()) {
	// Ensure proper cache control for redirected pages
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	response.setDateHeader("Expires", 0); // Proxies

	// Redirect to sign-in page
	response.sendRedirect("signIn.jsp");
	// Ensure no further content is processed
	return;
}
%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="icon" href="styles/images/favicon.png" type="image/png">

<!--
<p>This section includes the necessary stylesheets and external resources required for the page.</p>

<p>The Bootstrap CSS file is included for responsive design and component styling. The Font Awesome CSS file 
provides icons used throughout the site. Additionally, custom stylesheets are linked to handle layout, component, 
and form-specific styles.</p>

<ul>
  <li><code>https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css</code>: Includes Bootstrap 5.0.2 for styling 
  and responsive design.</li>
  <li><code>https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css</code>: Includes Font Awesome icons.</li>
  <li><code>styles/layouts.css</code>: Custom stylesheet for layout styling.</li>
  <li><code>styles/components.css</code>: Custom stylesheet for component-specific styling.</li>
  <li><code>styles/forms-profile.css</code>: Custom stylesheet for form and profile-related styles.</li>
</ul>
-->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="styles/layouts.css">
<link rel="stylesheet" href="styles/components.css">
<link rel="stylesheet" href="styles/forms-profile.css">
</head>

<body>
	<!--
    <p>This section defines the navigation bar for the website. The navbar is fixed at the top and uses Bootstrap's 
    responsive design classes for layout and styling.</p>

    <p>The navbar includes the following components:</p>
    <ul>
        <li><strong>Branding:</strong> Displays the site logo and name ("RoyalKitchen"). The logo image is included using 
        an <code>&lt;img&gt;</code> tag.</li>
        <li><strong>Toggler Button:</strong> A button that toggles the collapse of the navbar on smaller screens. It uses 
        the Font Awesome bars icon for representation.</li>
        <li><strong>Search Dropdown:</strong> A dropdown menu that contains a search form with an input field and a search 
        button.</li>
        <li><strong>Profile Dropdown:</strong> A dropdown menu that provides options for profile management, including 
        viewing profile details, updating the profile, and deleting the profile. Each option opens a corresponding modal.</li>
        <li><strong>Additional Links:</strong> Links to offers, reviews, help, and cart.</li>
    </ul>
    -->
	<nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
		<div class="container-fluid">
			<a class="navbar-brand ms-4" href="#"> <img
				src="styles/images/logo.png" alt="" width="30" height="28"
				class="d-inline-block align-text-top"> RoyalKitchen
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
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
						role="button" data-bs-toggle="dropdown" aria-expanded="false">
							Search </a>
						<ul class="dropdown-menu mt-lg-4" aria-labelledby="navbarDropdown">
							<li>
								<form class="d-flex p-2">
									<input class="form-control me-2" type="search"
										placeholder="Search" aria-label="Search"
										style="border: none; outline: none;">
									<button class="btn outline-primary" type="submit">
										<i class="fas fa-search"></i>
									</button>
								</form>
							</li>
						</ul></li>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" id="profileDropdown"
						role="button" data-bs-toggle="dropdown" aria-expanded="true">
							Profile </a>
						<ul class="dropdown-menu custom-menu mt-lg-4"
							aria-labelledby="profileDropdown">
							<li class="dropdown-item" id="profileDetails"
								data-bs-toggle="modal" data-bs-target="#profileModal">Profile
								Details</li>
							<li class="dropdown-item" id="updateProfile"
								data-bs-toggle="modal" data-bs-target="#updateModal">Update
								Profile</li>
							<li class="dropdown-item" id="deleteProfile"
								data-bs-toggle="modal" data-bs-target="#deleteModal">Delete
								Profile</li>
						</ul></li>
					<li class="nav-item"><a class="nav-link" href="#">Offers</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Reviews</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Help</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Cart</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<!-- Profile Modal -->
	<div class="modal fade show" id="profileModal" tabindex="-1"
		aria-labelledby="profileModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<!--
                <p>This section defines the header of the modal. It includes the title "Profile Details" and a close button 
                to dismiss the modal.</p>
                -->
					<h5 class="modal-title" id="profileModalLabel">Profile Details</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<!--
                <p>This section contains the body of the modal, which displays user profile details in a table format. If no 
                user information is found, a message is shown indicating that no user details are available.</p>
                -->
					<%
					user = (User) session.getAttribute("user");
					if (user != null) {
						if (user.getEmail() == null || user.getEmail().isEmpty()) {
							// If email is missing, show an alert
							out.print("<script>alert('User email is missing. Please log in again.');</script>");
						}
					%>
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Field</th>
								<th>Value</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>User ID</td>
								<td><%=user.getUserid()%></td>
							</tr>
							<tr>
								<td>Email</td>
								<td><%=user.getEmail()%></td>
							</tr>
							<tr>
								<td>Username</td>
								<td><%=user.getUsername()%></td>
							</tr>
							<tr>
								<td>Phone Number</td>
								<td><%=user.getPhonenumber()%></td>
							</tr>
							<tr>
								<td>Address</td>
								<td><%=user.getAddress()%></td>
							</tr>
							<tr>
								<td>Password</td>
								<td>*****</td>
							</tr>
						</tbody>
					</table>
					<%
					} else {
					%>
					<p>No user details available.</p>
					<%
					}
					%>
					<!--
                <p>This section includes a form for logging out. The form is styled to be displayed inline, and when submitted, 
                it triggers the logout process.</p>
                -->
					<form action="navbar.jsp" method="post" style="display: inline;">
						<input type="hidden" name="logout" value="true">
						<button type="submit" class="btn btn-warning">Logout</button>
					</form>
				</div>
				<div class="modal-footer">
					<!--
                <p>This section defines the footer of the modal, including a button to close the modal.</p>
                -->
					<button type="button" class="btn btn-danger"
						data-bs-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<%
	/**
	 * This section handles the logout process and controls caching behavior.
	 *
	 * <p>Logout Handling:</p>
	 * <p>This block of code checks if the current request is a POST request and if the `logout` parameter is set to "true". 
	 * If both conditions are met, it performs the following actions:</p>
	 * <ul>
	 *   <li><code>session.invalidate();</code> - Invalidates the current user session, logging the user out by removing 
	 *   all session attributes.</li>
	 *   <li><code>response.sendRedirect("signIn.jsp");</code> - Redirects the user to the sign-in page so they can log in 
	 *   again.</li>
	 * </ul>
	 *
	 * <p>Cache Control Headers:</p>
	 * <p>This part of the code sets HTTP headers to control caching behavior for the response:</p>
	 * <ul>
	 *   <li><code>response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");</code> - This HTTP 1.1 header 
	 *   instructs the browser and any intermediate caches to not cache the response. It ensures that the page is always fetched 
	 *   fresh from the server.</li>
	 *   <li><code>response.setHeader("Pragma", "no-cache");</code> - This HTTP 1.0 header provides backward compatibility 
	 *   with older HTTP standards, ensuring that the response is not cached.</li>
	 *   <li><code>response.setDateHeader("Expires", 0);</code> - This header is used to specify that the response has already 
	 *   expired, which prevents caching by proxies.</li>
	 * </ul>
	 */
	if ("POST".equalsIgnoreCase(request.getMethod()) && "true".equals(request.getParameter("logout"))) {
		session.invalidate();
		response.sendRedirect("signIn.jsp");
	}
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	response.setDateHeader("Expires", 0); // Proxies
	%>

	<!--
<p>This section defines the Update Profile modal window, which allows users to update their profile details.</p>

<p>The modal is designed with the following components:</p>
<ul>
  <li><strong>Modal Header:</strong> Displays the title "Update Profile" and a close button.</li>
  <li><strong>Modal Body:</strong> Contains a form with a table displaying the profile fields and editable options.</li>
  <li><strong>Modal Footer:</strong> Provides a button to close the modal.</li>
</ul>

<p><strong>Modal Body Form:</strong> The form allows users to update their profile details. It includes the following fields:</p>
<ul>
  <li><strong>Email:</strong> Displays the email address as read-only with a "Cannot Edit" button.</li>
  <li><strong>Username:</strong> Shows the username with an "Edit" button to enable editing.</li>
  <li><strong>Phone Number:</strong> Displays the phone number with an "Edit" button to enable editing.</li>
  <li><strong>Address:</strong> Shows the address with an "Edit" button to enable editing.</li>
  <li><strong>Password:</strong> Displays the password with an "Edit" button to enable editing.</li>
</ul>

<p>The buttons for editing fields are initially disabled and will enable the corresponding fields for editing when clicked. The "Update" button submits the form to save changes.</p>

<p><strong>Note:</strong> This modal uses Bootstrap classes for styling and responsiveness, and Font Awesome icons for visual elements.</p>
-->
	<!-- Update Modal -->
	<div class="modal fade" id="updateModal" tabindex="-1"
		aria-labelledby="updateModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="updateModalLabel">Update Profile</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form action="updateProfile" method="post">
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
									<td>Email</td>
									<td><input type="email" id="emailField" name="email"
										value="<%=user != null ? user.getEmail() : ""%>"
										class="form-control field-editable" readonly disabled
										style="border: 1px solid lightgray;"></td>
									<td class="text-center">
										<button type="button" class="btn btn-secondary btn-sm"
											disabled>
											<i class="fas fa-lock"></i> Cannot Edit
										</button>
									</td>
								</tr>
								<tr>
									<td>Username</td>
									<td><input type="text" id="usernameField" name="username"
										value="<%=user != null ? user.getUsername() : ""%>"
										class="form-control field-editable" disabled
										style="border: 1px solid lightgray;"></td>
									<td class="text-center">
										<button type="button" id="editUsernameBtn"
											class="btn btn-warning btn-sm">
											<i class="fas fa-edit"></i> Edit
										</button>
									</td>
								</tr>
								<tr>
									<td>Phone Number</td>
									<td><input type="text" id="phoneNumberField"
										name="phonenumber"
										value="<%=user != null ? user.getPhonenumber() : ""%>"
										class="form-control field-editable" disabled
										style="border: 1px solid lightgray;"></td>
									<td class="text-center">
										<button type="button" id="editPhoneNumberBtn"
											class="btn btn-warning btn-sm">
											<i class="fas fa-edit"></i> Edit
										</button>
									</td>
								</tr>
								<tr>
									<td>Address</td>
									<td><input type="text" id="addressField" name="address"
										value="<%=user != null ? user.getAddress() : ""%>"
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
									<td>Password</td>
									<td><input type="password" id="passwordField"
										name="password"
										value="<%=user != null ? user.getPassword() : ""%>"
										class="form-control field-editable" disabled
										style="border: 1px solid lightgray;"></td>
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
<p>This section includes two modal dialogs for profile deletion:</p>

<ul>
  <li><strong>Delete Modal:</strong> Prompts the user to confirm their intention to delete their profile.</li>
  <li><strong>Confirmation Modal:</strong> Requests confirmation from the user by asking for their email and password before proceeding with the deletion.</li>
</ul>

<p><strong>Delete Modal:</strong> This modal is displayed first when the user initiates a profile deletion request.</p>
<ul>
  <li><strong>Modal Header:</strong> Displays the title "Delete Profile" and a close button.</li>
  <li><strong>Modal Body:</strong> Contains a confirmation message asking if the user is sure they want to delete their profile.</li>
  <li><strong>Modal Footer:</strong> Includes "Cancel" and "Yes, Delete Profile" buttons. Clicking "Yes, Delete Profile" opens the Confirmation Modal, while "Cancel" closes the modal.</li>
</ul>

<p><strong>Confirmation Modal:</strong> This modal is displayed after the user clicks "Yes, Delete Profile" in the Delete Modal.</p>
<ul>
  <li><strong>Modal Header:</strong> Displays the title "Confirm Delete Profile" and a close button.</li>
  <li><strong>Modal Body:</strong> Contains a form that requires the user to enter their email and password to confirm the deletion. The email field is pre-filled and read-only, while the password field is required for confirmation.</li>
  <li><strong>Modal Footer:</strong> Includes "Cancel" and "Confirm Delete" buttons. Clicking "Confirm Delete" submits the form to delete the profile, while "Cancel" closes the modal.</li>
</ul>

<p><strong>Note:</strong> Both modals use Bootstrap classes for styling and responsiveness, and Font Awesome icons for visual elements.</p>
-->
	<!-- Delete Modal -->
	<div class="modal fade" id="deleteModal" tabindex="-1"
		aria-labelledby="deleteModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="deleteModalLabel">Delete Profile</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<p>Are you sure you want to delete your profile?</p>
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
	<!-- Confirmation Modal -->
	<div class="modal fade" id="confirmDeleteModal" tabindex="-1"
		aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="confirmDeleteModalLabel">Confirm
						Delete Profile</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form action="deleteProfile" method="post">
						<div class="mb-3">
							<label for="email" class="form-label">Email</label> <input
								type="email" class="form-control" id="email" name="email"
								required style="border: 1px solid lightgray;"
								value="<%=user != null ? user.getEmail() : ""%>" readonly>
						</div>
						<div class="mb-3">
							<label for="password" class="form-label">Password</label> <input
								type="password" class="form-control" id="password"
								name="password" required style="border: 1px solid lightgray;">
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

	<!-- Include external JavaScript files for functionality and interactivity -->

	<!-- Link to the local JavaScript file for profile-specific functionality -->
	<script type="text/javascript" src="profile.js"></script>

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
</body>
</html>