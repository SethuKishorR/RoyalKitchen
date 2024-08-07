<%@ include file="navbar.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
/**
 * <p>This code block retrieves the user object from the session. If the user is not authenticated or their email is not set, 
 * it redirects the user to the sign-in page.</p>
 * 
 * <p>The check ensures that the user is logged in and has a valid email address before allowing access to the current page. 
 * If the user is not authenticated, they are redirected to the sign-in page to log in.</p>
 */
user = (User) session.getAttribute("user");
if (user == null || user.getEmail() == null || user.getEmail().isEmpty()) {
	response.sendRedirect("signIn.jsp");
	return;
}
%>
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
    The title of the document
    <p>Displayed on the browser tab</p>
    -->
<title>Restaurant</title>

<!--
    Favicon for the website
    <p>Displays the website icon in the browser tab</p>
    -->
<link rel="icon" href="images/logo.png" type="image/png">

<!--
    Link to the Bootstrap CSS file
    <p>Includes Bootstrap for styling</p>
    -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.2/css/bootstrap.min.css"
	rel="stylesheet">

<!--
    Link to the custom stylesheet
    <p>Includes custom styles</p>
    -->
<link rel="stylesheet" href="">

<!--
    Inline CSS for basic styling
    <p>Defines the height for the container to fill the viewport</p>
    -->
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
<body>

	<!--
    Main content container
    <p>Centers the content vertically and horizontally in the viewport</p>
    -->
	<div
		class="container d-flex align-items-center justify-content-center full-height-container">
		<h1>Restaurant Body</h1>
	</div>

	<!--
    Popper.js library
    <p>Includes the Popper.js library for positioning tooltips and popovers</p>
    -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/2.9.2/umd/popper.min.js"></script>

	<!--
    Bootstrap JavaScript library
    <p>Includes the Bootstrap JavaScript library for interactive components</p>
    -->
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.2/js/bootstrap.min.js"></script>
</body>
</html>