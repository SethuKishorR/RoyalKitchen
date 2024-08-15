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
<link rel="icon" href="admin/styles/images/favicon.png" type="image/png">

<!--
    The title of the document
    <p>Displayed on the browser tab</p>
    -->
<title>Success</title>

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
<link rel="stylesheet" href="admin/styles/style.css">
</head>
<body>
	<!--
    Background and container setup
    <p>Centers the success message on the page with a background</p>
    -->
	<div class="bg">
		<div
			class="container d-flex justify-content-center align-items-center my-5">
			<div class="glass-effect col-10 col-md-8 col-lg-6 col-xl-4">
				<!--
                Success message heading
                <p>Displays the title of the success page</p>
                -->
				<h3 class="text-center mt-4">Success</h3>

				<!--
                Success message display
                <p>Shows the success message passed from the server</p>
                -->
				<div class="alert alert-success text-center"
					style="max-width: 100%; word-wrap: break-word; overflow-wrap: break-word;">
					<%
					String message = (String) request.getAttribute("message");
					if (message != null) {
						out.print(message);
					} else {
						out.print("Session expired. Please click 'Back' to sign in again.");
					}
					%>
				</div>


				<!--
                Back button
                <p>Provides a link to redirect the user to the specified URL</p>
                -->
				<p class="text-center mt-3">
					<a
						href="<%=request.getAttribute("redirectUrl") != null ? request.getAttribute("redirectUrl") : "signIn.jsp"%>"
						class="btn btn-danger">Back</a>
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