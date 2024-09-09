<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="navbar.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Help & Support - Food Ordering</title>
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.2/css/bootstrap.min.css"
	rel="stylesheet">
<style>
.faq-section {
	margin-top: 50px;
}

.contact-section {
	margin-top: 30px;
	padding: 20px;
	background-color: #f9f9f9;
}
</style>
</head>
<body class="bg-light">
	<div class="mt-5 py-5 container">
		<div class="d-flex justify-content-center mt-3">
			<div class="card col-md-12 bg-danger"
				style="backdrop-filter: blur(10px); border-radius: 10px; border: 1px solid rgba(144, 238, 144, 0.5); color: #fff;">
				<div
					class="card-body d-flex align-items-center justify-content-center"
					style="height: 70px;">
					<h5 class="mb-0 text-white">Help & Support</h5>
				</div>
			</div>
		</div>
		<!-- FAQ Section -->
		<div class="container faq-section">
			<div class="accordion" id="faqAccordion">
				<div class="card">
					<p class="px-3 pt-3" style="font-size: 18px;">Frequently Asked
						Questions</p>
					<div class="card-header" id="headingOne">
						<h5 class="mb-0">
							<button class="btn btn-link" type="button" data-toggle="collapse"
								data-target="#collapseOne" aria-expanded="true"
								aria-controls="collapseOne">How do I sign up and log
								in?</button>
						</h5>
					</div>
					<div id="collapseOne" class="collapse show"
						aria-labelledby="headingOne" data-parent="#faqAccordion">
						<div class="card-body">To sign up, click the login button on
							the homepage, and then choose "Not a user? Sign Up." After
							successfully signing up, you'll be automatically redirected to
							the sign-in page. Once signed in, you'll be redirected to the
							restaurant page.</div>
					</div>
				</div>

				<div class="card">
					<div class="card-header" id="headingTwo">
						<h5 class="mb-0">
							<button class="btn btn-link collapsed" type="button"
								data-toggle="collapse" data-target="#collapseTwo"
								aria-expanded="false" aria-controls="collapseTwo">How
								do I manage my profile and log out?</button>
						</h5>
					</div>
					<div id="collapseTwo" class="collapse" aria-labelledby="headingTwo"
						data-parent="#faqAccordion">
						<div class="card-body">Inside the profile dropdown in the
							navigation bar, you'll find options to view, update, or delete
							your profile. To log out, click "Show Profile" and you'll find
							the logout button at the bottom of the profile modal popup.</div>
					</div>
				</div>

				<!-- Guest Mode Questions -->
				<div class="card">
					<div class="card-header" id="headingThree">
						<h5 class="mb-0">
							<button class="btn btn-link collapsed" type="button"
								data-toggle="collapse" data-target="#collapseThree"
								aria-expanded="false" aria-controls="collapseThree">Can
								I use the platform without signing up?</button>
						</h5>
					</div>
					<div id="collapseThree" class="collapse"
						aria-labelledby="headingThree" data-parent="#faqAccordion">
						<div class="card-body">Yes, you can browse restaurants and
							menus as a guest, but you won't be able to add items to your
							cart, place an order, or access order history. To access these
							features, you must sign up and log in.</div>
					</div>
				</div>

				<div class="card">
					<div class="card-header" id="headingFour">
						<h5 class="mb-0">
							<button class="btn btn-link collapsed" type="button"
								data-toggle="collapse" data-target="#collapseFour"
								aria-expanded="false" aria-controls="collapseFour">What
								features are restricted in guest mode?</button>
						</h5>
					</div>
					<div id="collapseFour" class="collapse"
						aria-labelledby="headingFour" data-parent="#faqAccordion">
						<div class="card-body">In guest mode, you cannot add items
							to the cart, place orders, or view order history. Signing up
							provides access to these features.</div>
					</div>
				</div>

				<div class="card">
					<div class="card-header" id="headingFive">
						<h5 class="mb-0">
							<button class="btn btn-link collapsed" type="button"
								data-toggle="collapse" data-target="#collapseFive"
								aria-expanded="false" aria-controls="collapseFive">How
								do I switch from guest mode to a registered account?</button>
						</h5>
					</div>
					<div id="collapseFive" class="collapse"
						aria-labelledby="headingFive" data-parent="#faqAccordion">
						<div class="card-body">You can switch by clicking on the
							login button in the navigation bar and completing the sign-up
							process. Once logged in, you'll be able to use all the features.</div>
					</div>
				</div>

				<!-- Ordering Process Questions -->
				<div class="card">
					<div class="card-header" id="headingSix">
						<h5 class="mb-0">
							<button class="btn btn-link collapsed" type="button"
								data-toggle="collapse" data-target="#collapseSix"
								aria-expanded="false" aria-controls="collapseSix">How
								do I search for restaurants or menus and apply filters?</button>
						</h5>
					</div>
					<div id="collapseSix" class="collapse" aria-labelledby="headingSix"
						data-parent="#faqAccordion">
						<div class="card-body">Use the search bar to search for
							restaurants or menu items globally. You can also apply trending
							and time filters (e.g., restaurants delivering in less than 20
							minutes) and filter active restaurants.</div>
					</div>
				</div>

				<div class="card">
					<div class="card-header" id="headingSeven">
						<h5 class="mb-0">
							<button class="btn btn-link collapsed" type="button"
								data-toggle="collapse" data-target="#collapseSeven"
								aria-expanded="false" aria-controls="collapseSeven">How
								do I add items to my cart and place an order?</button>
						</h5>
					</div>
					<div id="collapseSeven" class="collapse"
						aria-labelledby="headingSeven" data-parent="#faqAccordion">
						<div class="card-body">Select any restaurant to view
							available menus. Click on a menu item to view details and add it
							to your cart. In the cart, you can adjust the quantity, remove
							items, and optionally provide a tip or donation. You must update
							your phone number and password before placing an order. Once an
							order is placed, it moves to the order confirmation page, and
							clicking "Continue" will redirect you to the restaurant page.</div>
					</div>
				</div>

				<div class="card">
					<div class="card-header" id="headingEight">
						<h5 class="mb-0">
							<button class="btn btn-link collapsed" type="button"
								data-toggle="collapse" data-target="#collapseEight"
								aria-expanded="false" aria-controls="collapseEight">
								What happens when I place an order from multiple restaurants?</button>
						</h5>
					</div>
					<div id="collapseEight" class="collapse"
						aria-labelledby="headingEight" data-parent="#faqAccordion">
						<div class="card-body">If you place an order from multiple
							restaurants, the order is split based on how many restaurants are
							selected. Platform fees, tips, and donations are split equally
							across the orders.</div>
					</div>
				</div>

				<div class="card">
					<div class="card-header" id="headingNine">
						<h5 class="mb-0">
							<button class="btn btn-link collapsed" type="button"
								data-toggle="collapse" data-target="#collapseNine"
								aria-expanded="false" aria-controls="collapseNine">How
								do I track my order status and view my order history?</button>
						</h5>
					</div>
					<div id="collapseNine" class="collapse"
						aria-labelledby="headingNine" data-parent="#faqAccordion">
						<div class="card-body">Once you've placed an order, click on
							"My Orders" in the profile dropdown to track your order and view
							the delivery status. You can also access your order history for
							previous orders.</div>
					</div>
				</div>

				<!-- Admin Questions -->
				<div class="card">
					<div class="card-header" id="headingTen">
						<h5 class="mb-0">
							<button class="btn btn-link collapsed" type="button"
								data-toggle="collapse" data-target="#collapseTen"
								aria-expanded="false" aria-controls="collapseTen">How
								do I add a new restaurant as an admin?</button>
						</h5>
					</div>
					<div id="collapseTen" class="collapse" aria-labelledby="headingTen"
						data-parent="#faqAccordion">
						<div class="card-body">After logging in as an admin, go to
							the "Add Restaurant" section. Fill out the restaurant details,
							including name, address, delivery time, cuisine type, and rating.
							Once completed, the restaurant will appear on the platform.</div>
					</div>
				</div>

				<div class="card">
					<div class="card-header" id="headingEleven">
						<h5 class="mb-0">
							<button class="btn btn-link collapsed" type="button"
								data-toggle="collapse" data-target="#collapseEleven"
								aria-expanded="false" aria-controls="collapseEleven">
								How do I manage existing restaurants as an admin?</button>
						</h5>
					</div>
					<div id="collapseEleven" class="collapse"
						aria-labelledby="headingEleven" data-parent="#faqAccordion">
						<div class="card-body">From the admin dashboard, you can
							update restaurant details, including name, cuisine type, address,
							delivery time, ratings, and active status. You can also delete
							restaurants from this section.</div>
					</div>
				</div>

				<!-- Additional Questions -->
				<!-- Add more questions following the same structure -->

			</div>
		</div>

		<!-- Contact Section -->
		<div class="container contact-section mb-3">
			<p class="px-3" style="font-size: 18px;">Contact Us</p>
			<div class="accordion" id="contactAccordion">
				<div class="card">
					<div class="card-header" id="headingContact">
						<h5 class="mb-0">
							<button class="btn btn-link" type="button" data-toggle="collapse"
								data-target="#collapseContact" aria-expanded="true"
								aria-controls="collapseContact">Contact Information</button>
						</h5>
					</div>
					<div id="collapseContact" class="collapse show"
						aria-labelledby="headingContact" data-parent="#contactAccordion">
						<div class="card-body">
							<p>
								Email: <a href="mailto:sethukishorramasamy@gmail.com">sethukishorramasamy@gmail.com</a>
							</p>
							<p>Phone: 8925638843</p>
							<p>
								LinkedIn: <a
									href="https://www.linkedin.com/in/sethu-kishor-380872255/"
									target="_blank">https://www.linkedin.com/in/sethu-kishor-380872255/</a>
							</p>
							<p>
								Portfolio: <a href="https://sethukishor.vercel.app/"
									target="_blank">https://sethukishor.vercel.app/</a>
							</p>
							<p>Availability: Contact via LinkedIn</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<footer class="text-center py-2 fixed-bottom"
		style="background: rgba(255, 255, 255, 0.1); /* Light gray translucent background */ backdrop-filter: blur(10px); /* Frosted glass effect */ border-top: 1px solid rgba(255, 255, 255, 0.3); /* Slight border on top */ box-shadow: 0 -1px 2px rgba(0, 0, 0, 0.1); /* Soft shadow to give depth */ color: #333; /* Text color */ width: 100%; /* Full width of the page */ position: fixed; /* Fix the footer at the bottom */ bottom: 0; /* Ensure it sticks to the bottom */ padding: 15px; text-align: center;">
		<p class="mt-2">Designed and developed by @SethuKishor</p>
	</footer>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/2.9.2/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.2/js/bootstrap.bundle.min.js"></script>
</body>
</html>