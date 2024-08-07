<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">

<head>
<!--
    Character encoding and viewport meta tags
    <p>Sets character encoding to UTF-8 and viewport for responsive design</p>
    -->
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!--
    Favicon link
    <p>Specifies the icon to be used in the browser tab and bookmarks</p>
    -->
<link rel="icon" href="styles/images/favicon.png" type="image/png">

<!--
    Page title
    <p>Sets the title of the page to "Sign In"</p>
    -->
<title>Sign In</title>

<!--
    Bootstrap CSS
    <p>Includes Bootstrap 4.5.2 for styling</p>
    -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">

<!--
    Custom CSS
    <p>Includes custom styles for the sign-in page</p>
    -->
<link rel="stylesheet" href="styles/style.css">
</head>

<body>
	<!--
    Background and container setup
    <p>Contains the sign-in form with a glass effect and centers it on the page</p>
    -->
	<div class="bg">
		<div
			class="container d-flex justify-content-center align-items-center">
			<div class="glass-effect col-10 col-md-8 col-lg-6 col-xl-4">
				<!--
                Sign-in header
                <p>Displays the title "Sign In" centered at the top of the form</p>
                -->
				<h3 class="text-center mt-4">Sign In</h3>

				<!--
                Sign-in form
                <p>Form for users to enter their email and password to sign in</p>
                -->
				<form action="signIn" method="post">
					<div class="form-group">
						<label for="email">Email address</label>
						<!--
                        Email input field
                        <p>Allows users to enter their email address</p>
                        -->
						<input type="email" class="form-control" id="email" name="email"
							placeholder="Enter email" required>
					</div>
					<div class="form-group">
						<label for="password">Password</label>
						<!--
                        Password input field
                        <p>Allows users to enter their password</p>
                        -->
						<input type="password" class="form-control" id="password"
							name="password" placeholder="Password" required>
					</div>
					<!--
                    Sign-in button
                    <p>Submits the form to sign in</p>
                    -->
					<button type="submit" class="btn btn-danger mt-2">Sign In</button>
				</form>

				<!--
                Sign-up link
                <p>Provides a link for users who are not registered to sign up</p>
                -->
				<p class="text-center mt-1">
					Not a user? <a href="signUp.jsp" class="sign-link">Sign Up</a>
				</p>
			</div>
		</div>
	</div>

	<!--
    JavaScript files
    <p>Includes jQuery, Popper, and Bootstrap JavaScript files for functionality</p>
    -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

</html>