<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<!--
    The character encoding for the HTML document
    <p>Sets the character encoding for the HTML document to UTF-8</p>
    -->
<meta charset="UTF-8">

<!--
    The viewport meta tag controls the layout on mobile browsers.
    <p>Ensures proper rendering and touch zooming for mobile devices</p>
    -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!--
    Favicon link
    <p>Specifies the icon to be used in the browser tab and bookmarks</p>
    -->
<link rel="icon" href="styles/images/favicon.png" type="image/png">

<!--
    The title of the document
    <p>Displayed on the browser tab</p>
    -->
<title>Sign Up</title>

<!--
    Link to the Bootstrap CSS file
    <p>Includes Bootstrap for styling</p>
    -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">

<!--
    Link to the custom stylesheet
    <p>Includes custom styles</p>
    -->
<link rel="stylesheet" href="styles/style.css">
</head>
<body>
	<!--
    Background and container setup
    <p>Centers the sign-up form on the page with a background</p>
    -->
	<div class="bg">
		<div
			class="container d-flex justify-content-center align-items-center my-5">
			<div class="glass-effect col-10 col-md-8 col-lg-6 col-xl-4">
				<!--
                The heading of the sign-up form
                <p>Displayed as the form title</p>
                -->
				<h3 class="text-center mt-4">Sign Up</h3>

				<!--
                The sign-up form
                <p>Collects user details for signing up</p>
                -->
				<form action="signUp" method="post">
					<div class="form-group">
						<!--
                        Email input field
                        <p>Collects the user's email address</p>
                        -->
						<label for="email">Email address</label> <input type="email"
							class="form-control" id="email" name="email"
							placeholder="Enter email" required>
					</div>
					<div class="form-group">
						<!--
                        Password input field
                        <p>Collects the user's password</p>
                        -->
						<label for="password">Password</label> <input type="password"
							class="form-control" id="password" name="password"
							placeholder="Password" required>
					</div>
					<div class="form-group">
						<!--
                        Confirm password input field
                        <p>Ensures the user confirms their password</p>
                        -->
						<label for="confirm-password">Confirm Password</label> <input
							type="password" class="form-control" id="confirm-password"
							name="confirmpassword" placeholder="Confirm Password" required>
					</div>
					<!--
                    Submit button
                    <p>Submits the form</p>
                    -->
					<button type="submit" class="btn btn-danger mt-2">Sign Up</button>
				</form>

				<!--
                Link to the sign-in page
                <p>Provides an option for existing users to sign in</p>
                -->
				<p class="text-center mt-3">
					Already a user? <a href="signIn.jsp" class="sign-link">Sign In</a>
				</p>
			</div>
		</div>
	</div>

	<!--
    jQuery library
    <p>Includes the jQuery library for JavaScript functionality</p>
    -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>

	<!--
    Popper.js library
    <p>Includes the Popper.js library for positioning tooltips and popovers</p>
    -->
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>

	<!--
    Bootstrap JavaScript library
    <p>Includes the Bootstrap JavaScript library for interactive components</p>
    -->
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>