<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<!-- 
		<p>Specifies the character encoding for the HTML document.</p>
	-->
<meta charset="UTF-8">

<!-- 
		<p>Sets the viewport to ensure proper scaling on mobile devices.</p>
	-->
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- 
		<p>Links to the website's favicon, an icon displayed in the browser tab.</p>
	-->
<link rel="icon" href="admin/styles/images/favicon.png" type="image/png">

<!-- 
		<p>Title of the webpage displayed in the browser tab.</p>
	-->
<title>RoyalKitchen</title>

<!-- 
		<p>Link to Font Awesome CSS for adding scalable vector icons.</p>
	-->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
	rel="stylesheet">

<!-- 
		<p>Link to Bootstrap 4.5.2 CSS for responsive design and layout.</p>
	-->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">

<!-- 
		<p>Link to Bootstrap 5.0.2 CSS for responsive design and layout.</p>
	-->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">

<!-- 
		<p>Link to custom stylesheets for layout, components, and form profile management.</p>
	-->
<link rel="stylesheet" href="admin/styles/layouts.css">
<link rel="stylesheet" href="admin/styles/components.css">
<link rel="stylesheet" href="admin/styles/forms-profile.css">
</head>

<body>
	<!-- 
		<p>Navbar section for navigation and branding.</p>
	-->
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">
			<!-- 
				<p>Brand logo and name displayed in the navbar.</p>
			-->
			<a class="navbar-brand ms-4" href="#"> <img
				src="admin/styles/images/logo.png" alt="RoyalKitchen Logo"
				width="30" height="28" class="d-inline-block align-text-top">
				RoyalKitchen
			</a>

			<!-- 
				<p>Button for toggling the navigation menu on smaller screens.</p>
			-->
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<!-- 
				<p>Navigation menu with links for user roles.</p>
			-->
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav ms-auto">
					<!-- 
						<p>Dropdown menu for user role selection.</p>
					-->
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
						role="button" data-bs-toggle="dropdown" aria-expanded="false">
							User </a>
						<ul class="dropdown-menu dropdown-menu-center custom-menu mt-lg-4"
							aria-labelledby="navbarDropdown">
							<li><a class="dropdown-item" href="#" data-bs-toggle="modal"
								data-bs-target="#adminModal">Admin</a></li>
							<li><a class="dropdown-item" href="signIn.jsp">User</a></li>
							<li><a class="dropdown-item" href="restaurant.jsp">Guest</a></li>
						</ul></li>
					<li class="nav-item"><a class="nav-link" target="_blank"
						href="https://sethukishor.vercel.app/">Administrator</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<!-- 
		<p>Modal for administrator access, including a form for entering a private key.</p>
	-->
	<div class="modal fade" id="adminModal" tabindex="-1"
		aria-labelledby="adminModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="adminModalLabel">Administrator
						Access</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"
						style="background-color: transparent; border: none;"
						onmouseover="this.style.backgroundColor='lightcoral';"
						onmouseout="this.style.backgroundColor='transparent';"></button>
				</div>
				<div class="modal-body">
					<form action="AdminAccessServlet" method="post">
						<div class="mb-3">
							<label for="privateKey" class="form-label">Enter
								Administrator Private Key</label>
							<div class="input-group">
								<input type="password" class="form-control" id="privateKey"
									name="privateKey" required style="border: solid 1px lightgray;">
								<div class="input-group-append">
									<span class="input-group-text" id="toggle-privateKey"
										data-toggle="password" data-target="privateKey"> <i
										class="fas fa-eye"></i>
									</span>
								</div>
							</div>
						</div>
						<small class="info-message mt-3"
							style="color: red; font-size: 13px;"> ** This field will
							redirect you to the admin sign-in page. Make sure to enter the
							correct password. If you don't know it, please contact the
							administrator. ** </small>
						<button type="submit" class="btn btn-success"
							style="margin-left: auto; display: flex;">Submit</button>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- 
		<p>JavaScript for handling form functionalities, popovers, tooltips, and Bootstrap components.</p>
	-->
	<script type="text/javascript" src="admin/styles/password.js"></script>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.2/js/bootstrap.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
</body>

</html>