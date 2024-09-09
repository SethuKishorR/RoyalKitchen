<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!--
    Favicon
    <p>Sets the favicon for the admin sign-in page</p>
    -->
<link rel="icon" href="styles/images/favicon.png" type="image/png">

<!--
    Font Awesome CSS
    <p>Includes the Font Awesome library for using icon fonts</p>
    -->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"
	rel="stylesheet">

<title>Admin Sign In</title>

<!--
    Bootstrap CSS
    <p>Includes the Bootstrap 4.5.2 CSS for responsive layout and design</p>
    -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">

<!--
    Custom Stylesheet
    <p>Includes custom CSS for additional styling</p>
    -->
<link rel="stylesheet" href="styles/style.css">

</head>

<body>
	<%
	session = request.getSession(false);

	// Check if the user is logged in
	if (session == null || session.getAttribute("isLoggedIn") == null) {
		// Redirect to the sign-in page if the user is not logged in
		response.sendRedirect(request.getContextPath() + "/index.jsp");
		return;
	}

	// Check if the logout request is made
	if ("POST".equalsIgnoreCase(request.getMethod()) && "true".equals(request.getParameter("logout"))) {
		session.invalidate(); // Invalidate the session
		response.sendRedirect(request.getContextPath() + "/index.jsp"); // Redirect to sign-in page
		return;
	}

	// Prevent caching to ensure that the logout is effective
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	response.setDateHeader("Expires", 0); // Proxies
	%>

	<!-- Logout button positioned at the top right -->
	<form method="post" style="display: inline;">
		<input type="hidden" name="logout" value="true">
		<button type="submit" class="btn btn-danger logout-btn">Logout</button>
	</form>

	<!--
    Admin Sign In Container
    <p>Main container for the admin sign-in form, centered and padded for a clean look</p>
    -->
	<div class="admin py-5">
		<div
			class="container d-flex justify-content-start align-items-center my-5">
			<div
				class="card shadow-lg col-12 col-md-6 col-lg-4 col-xl-4 custom-styles">
				<div class="card-body py-4">

					<!--
                    Sign-In Instructions
                    <p>Provides instructions for signing in</p>
                    -->
					<p class="text-danger pb-1" style="font-size: 16px;">
						Follow the steps below to Sign In <i class="fas fa-user-shield"
							style="font-size: 14px;"></i>
					</p>

					<!--
                    Sign-In Form
                    <p>Form for admin sign-in, containing fields for the admin key and password</p>
                    -->
					<form action="adminSignIn" method="post">
						<div class="form-group">
							<label for="adminKey" style="font-size: 14px;">Secret
								Admin Key</label>
							<div class="input-group">
								<input type="password" class="form-control" id="adminKey"
									name="adminkey" placeholder="Enter key" required
									style="border: solid 1px lightgray">

								<!--
                                Toggle Admin Key Visibility
                                <p>Icon to toggle visibility of the admin key input</p>
                                -->
								<div class="input-group-append">
									<span class="input-group-text" id="toggle-admin-key"
										data-toggle="password" data-target="adminKey" style="cursor: pointer;"> <i
										class="fas fa-eye"></i>
									</span>
								</div>
							</div>
							<small class="form-text text-danger">Format:
								admin.username@restaurantname_address</small>
						</div>

						<div class="form-group">
							<label for="password" style="font-size: 14px;">Password</label>
							<div class="input-group">
								<input type="password" class="form-control" id="password"
									name="password" placeholder="Enter password" required
									style="border: solid 1px lightgray">

								<!--
                                Toggle Password Visibility
                                <p>Icon to toggle visibility of the password input</p>
                                -->
								<div class="input-group-append">
									<span class="input-group-text" id="toggle-password"
										data-toggle="password" data-target="password" style="cursor: pointer;"> <i
										class="fas fa-eye"></i>
									</span>
								</div>
							</div>
						</div>

						<!--
                        Submit Button
                        <p>Button to submit the sign-in form</p>
                        -->
						<button type="submit" class="btn btn-success mt-2">Sign
							In</button>
					</form>

					<!--
                    Sign-Up Link
                    <p>Link to the admin sign-up page for those who are not yet admins</p>
                    -->
					<p class="text-center mt-3" style="font-size: 14px;">
						Not an admin? <a href="adminSignUp.jsp" class="text-success"
							style="color: green; text-decoration: none;"
							onmouseover="this.style.color='darkgreen'; this.style.textDecoration='underline';"
							onmouseout="this.style.color='green'; this.style.textDecoration='none';">Sign
							Up</a>
					</p>
				</div>
			</div>
		</div>
	</div>

	<!--
    Custom JavaScript for Password Visibility Toggle
    <p>Includes a custom script to handle password visibility toggle</p>
    -->
	<script type="text/javascript" src="styles/password.js"></script>

	<!--
    jQuery Library
    <p>Includes the jQuery library for handling JavaScript actions</p>
    -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>

	<!--
    Popper.js Library
    <p>Includes the Popper.js library for positioning tooltips and popovers</p>
    -->
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>

	<!--
    Bootstrap JavaScript Library
    <p>Includes the Bootstrap JavaScript library for interactive components</p>
    -->
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

</html>