<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Sign Up</title>

<!-- Link to the favicon for the website -->
<link rel="icon" href="styles/images/favicon.png" type="image/png">

<!-- Font Awesome CSS for icons -->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"
	rel="stylesheet">

<!-- Bootstrap 4.5.2 CSS -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Bootstrap 5.0.2 CSS with integrity check -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">

<!-- Custom styles for the admin signup page -->
<link rel="stylesheet" href="styles/style.css">
<link rel="stylesheet" href="styles/admin.css">
</head>
<body>
	 <%
        session = request.getSession(false);
        if (session == null || session.getAttribute("isLoggedIn") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
	%>
	<!-- Initialize session variables for tracking signup steps -->
	<%
	session = request.getSession();
	Integer status = (Integer) session.getAttribute("status");
	Integer currentStep = (Integer) session.getAttribute("currentStep");
	String platformKey = (String) session.getAttribute("platformKey");

	// If session variables are not present, set default values
	if (currentStep == null) {
		currentStep = 1;
	}
	if (status == null) {
		status = 0;
	}
	%>

	<!-- Main container for the admin signup process -->
	<div class="admin py-5">
		<div
			class="container d-flex justify-content-start align-items-center my-5">
			<div
				class="card shadow-lg col-12 col-md-6 col-lg-4 col-xl-4 custom-styles">
				<div class="card-body py-4">
					<!-- Process description for each step of the signup -->
					<div class="process-description">
						<p class="text-danger pb-2" style="font-size: 16px;">
							Follow the below processes to Create an Account <i
								class="fas fa-user-plus" style="font-size: 14px;"></i>
						</p>
						<!-- Step 1: Enter the platform key -->
						<p id="step1-text">
							<i id="step1-tick" class="fas fa-check"
								style="display: <%=(currentStep > 1 ? "inline" : "none")%>; color: green;"></i>
							1. Enter the platform key.
						</p>
						<!-- Step 2: Create your admin account -->
						<p id="step2-text">
							<i id="step2-tick" class="fas fa-check"
								style="display: <%=(currentStep > 2 ? "inline" : "none")%>; color: green;"></i>
							2. Create your admin account with key, password, and
							confirmation.
						</p>
						<!-- Step 3: Provide restaurant name and address -->
						<p id="step3-text">
							<i id="step3-tick" class="fas fa-check"
								style="display: <%=(currentStep > 3 ? "inline" : "none")%>; color: green;"></i>
							3. Provide restaurant name and address.
						</p>
					</div>
					<!-- Progress bar to indicate signup progress -->
					<div class="progress mb-4">
						<div class="progress-bar bg-primary" id="progress-bar"
							style="width: <%=(currentStep - 1) * 33.3%>%; aria-valuenow="
							<%=(currentStep - 1) * 33.3%> aria-valuemin="0"
							aria-valuemax="100"><%=Math.round((currentStep - 1) * 33.3)%>
							Complete
						</div>
					</div>
					<!-- Button container for form navigation -->
					<div class="button-container mb-4 d-flex justify-content-end">
						<!-- Start button to initiate the signup process -->
						<button type="button" class="btn btn-success me-auto"
							id="startForm">Start</button>
						<!-- Next button to proceed to the next step -->
						<button type="button" class="btn btn-warning me-2" id="nextButton">Next</button>
						<!-- Form submission button to complete the signup process -->
						<form id="signup-form" action="adminSignUp" method="post"
							class="d-inline">
							<button type="submit" class="btn btn-success" id="btnSubmitAll"
								name="step" value="4">SignUp</button>
						</form>
					</div>
					<!-- Link for existing admins to sign in -->
					<p class="text-center mt-3" id="haveAccount"
						style="font-size: 14px;">
						Already have an account? <a href="adminSignIn.jsp"
							class="text-success" style="color: green; text-decoration: none;"
							onmouseover="this.style.color='darkgreen'; this.style.textDecoration='underline';"
							onmouseout="this.style.color='green'; this.style.textDecoration='none';">Sign
							In</a>
					</p>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal for entering the platform key (Step 1) -->
	<div class="modal fade" id="platformKeyModal" tabindex="-1"
		aria-labelledby="platformKeyModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="platformKeyModalLabel">Step 1:
						Platform Key</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<!-- Form for submitting the platform key -->
					<form id="platformKeyForm" action="adminSignUp" method="post">
						<input type="hidden" name="step" value="1">
						<!-- Input field for the platform key -->
						<div class="mb-3">
							<label for="platformKey" class="form-label">Platform
								Super Secret Key</label>
							<div class="input-group">
								<input type="password" class="form-control" id="platformKey"
									name="platformKey" placeholder="Enter the key"
									value="<%=platformKey != null ? platformKey : ""%>" required>
								<!-- Toggle button to show/hide the platform key -->
								<div class="input-group-append">
									<span class="input-group-text" id="toggle-platform-key"
										data-toggle="password" data-target="platformKey" style="cursor: pointer;"> <i
										class="fas fa-eye"></i>
									</span>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-success"
								id="savePlatformKey">Save Platform Key</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal for creating the admin account (Step 2) -->
	<div class="modal fade" id="createAccountModal" tabindex="-1"
		aria-labelledby="createAccountModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="createAccountModalLabel">Step 2:
						Create Account</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<!-- Form for creating the admin account -->
					<form id="createAccountForm" action="adminSignUp" method="post">
						<input type="hidden" name="step" value="2">
						<!-- Input field for creating the admin key -->
						<div class="mb-3">
							<label for="adminKey" class="form-label">Create Secret
								Admin Key</label>
							<div class="input-group">
								<input type="password" class="form-control" id="adminKey"
									name="adminkey" placeholder="Enter the admin key" required>
								<!-- Toggle button to show/hide the admin key -->
								<div class="input-group-append">
									<span class="input-group-text" id="toggle-admin-key"
										data-toggle="password" data-target="adminKey" style="cursor: pointer;"> <i
										class="fas fa-eye"></i>
									</span>
								</div>
							</div>
							<small class="format-info text-danger">Format:
								admin.adminname@restaurantname_address</small>
						</div>
						<!-- Input field for creating the admin password -->
						<div class="mb-3">
							<label for="password" class="form-label">Create Password</label>
							<div class="input-group">
								<input type="password" class="form-control" id="password"
									name="password" placeholder="Enter password" required>
								<!-- Toggle button to show/hide the admin password -->
								<div class="input-group-append">
									<span class="input-group-text" data-toggle="password"
										data-target="password" style="cursor: pointer;"> <i class="fas fa-eye"></i>
									</span>
								</div>
							</div>
							<small class="format-info text-danger">Password must be
								at least 8 characters long and include a mix of letters,
								numbers, and special characters.</small>
						</div>

						<!-- Input field for confirming the admin password -->
						<div class="mb-3">
							<label for="confirm-password" class="form-label">Confirm
								Password</label>
							<div class="input-group">
								<input type="password" class="form-control"
									id="confirm-password" name="confirmpassword"
									placeholder="Confirm password" required>
								<div class="input-group-append">
									<span class="input-group-text" data-toggle="password"
										data-target="confirm-password" style="cursor: pointer;"> <i class="fas fa-eye"></i>
									</span>
								</div>
							</div>
							<small class="format-info text-danger">Password and
								confirm password should match.</small>
						</div>

						<div class="modal-footer">
							<button type="submit" class="btn btn-success" id="saveAccount">Save
								Account</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal for providing the restaurant details (Step 3) -->
	<div class="modal fade" id="restaurantDetailsModal" tabindex="-1"
		aria-labelledby="restaurantDetailsModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="restaurantDetailsModalLabel">Step
						3: Restaurant Details</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<!-- Form for providing the restaurant details -->
					<form id="restaurantDetailsForm" action="adminSignUp" method="post">
						<input type="hidden" name="step" value="3">
						<!-- Input field for the restaurant name -->
						<div class="mb-3">
							<label for="restaurantName" class="form-label">Restaurant
								Name</label> <input type="text" class="form-control" id="restaurantName"
								name="restaurantname" placeholder="Enter the restaurant name"
								required>
						</div>
						<!-- Input field for the restaurant address -->
						<div class="mb-3">
							<label for="restaurantAddress" class="form-label">Restaurant
								Address</label> <input type="text" class="form-control"
								id="restaurantAddress" name="address"
								placeholder="Enter the restaurant address" required>
						</div>
						<div class="modal-footer">
							<!-- Save button for the restaurant details -->
							<button type="submit" class="btn btn-success"
								id="saveRestaurantDetails">Save Restaurant Details</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script>
		let currentStep =
	<%=currentStep%>
		;
	</script>
	<!-- Custom JavaScript for handling modal interactions and form submission -->
	<script type="text/javascript" src="styles/password.js"></script>
	<script type="text/javascript" src="createAdmin.js"></script>
	<script type="text/javascript" src="admin/styles/password.js"></script>
	<!-- jQuery and Bootstrap JS for modals and other features -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>