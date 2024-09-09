<%@ page import="java.util.ArrayList"%>
<%@ page import="com.tapfoods.model.Menu"%>
<%@ page import="com.tapfoods.DAO.MenuDAO"%>
<%@ page import="com.tapfoods.DAOImpl.MenuDAOImpl"%>
<%@ page import="com.tapfoods.model.Restaurant"%>
<%@ page import="com.tapfoods.model.User"%>
<%@ include file="navbar.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Shopping Cart</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
 <link rel="icon" href="admin/styles/images/favicon.png" type="image/png">
<link rel="stylesheet" href="admin/styles/menuItem.css">
<script>
	window.addEventListener("pageshow",function(event) {
	var historyTraversal = event.persisted || (typeof window.performance !== "undefined" && window.performance.navigation.type === 2);
	if (historyTraversal) { window.location.reload(); } });
</script>
</head>
<body class="bg-light">
	<div class="area">
		<ul class="circles">
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
		</ul>
	</div>
	<div class="container mt-5 py-5">
		<a id="back-link" href="#" class="text-danger">>>Back</a>
		<div class="row justify-content-center">
			<div class="col-md-12 mb-4 mt-4">
				<%
				// Retrieve cart items from session
				user = (User) session.getAttribute("user");
				ArrayList<Integer> cart = (ArrayList<Integer>) session.getAttribute("cart");

				if (cart != null && !cart.isEmpty() && user != null) {
					// Instantiate MenuDAOImpl
					MenuDAO menuDAO = new MenuDAOImpl();
				%>
				<div class="d-flex justify-content-center">
					<div class="card mb-2 col-md-12"
						style="background: rgba(255, 0, 0, 0.3); backdrop-filter: blur(10px); border-radius: 10px; border: 1px solid rgba(255, 182, 193, 0.5); color: #fff;">
						<div
							class="card-body d-flex align-items-center justify-content-center"
							style="height: 70px;">
							<h5 class="mb-0 text-white">Your Cart</h5>
						</div>
					</div>
				</div>

				<form action="placeOrderServlet" method="post" class="d-inline">
					<table class="table table-bordered table-striped mt-3">
						<thead class="table-dark">
							<tr>
								<th class="py-4">Name</th>
								<th class="py-4">Price</th>
								<th class="py-4">Quantity</th>
								<th class="py-4">Description</th>
								<th class="py-4 text-center"><i class="fas fa-trash-alt"></i></th>
							</tr>
						</thead>
						<tbody>
							<%
							double total = 0.0;
							for (Integer menuId : cart) {
								Menu menu = menuDAO.getMenu(menuId); // Fetch menu details
								if (menu != null) {
									total += menu.getPrice();
							%>
							<tr>
								<td class="py-3"><%=menu.getMenuname()%></td>
								<td class="py-3">Rupees <span class="item-total"
									id="item-total-<%=menu.getMenuid()%>"><%=menu.getPrice()%></span></td>
								<td>
									<div class="form-group">
										<select name="quantity_<%=menu.getMenuid()%>"
											class="form-control quantity-dropdown"
											onchange="updateItemTotal(this)"
											data-price="<%=menu.getPrice()%>"
											data-menuid="<%=menu.getMenuid()%>" required
											style="background: transparent;">
											<%
											for (int i = 1; i <= 10; i++) {
											%>
											<option value="<%=i%>" <%=(i == 1) ? "selected" : ""%>><%=i%></option>
											<%
											}
											%>
										</select>
									</div>
								</td>
								<td class="py-3"><%=menu.getDescription()%></td>
								<td class="py-3 text-center">
									<button type="button" class="btn btn-danger btn-sm remove-item"
										data-menuid="<%=menu.getMenuid()%>">
										<i class="fas fa-trash-alt"></i>
									</button>
								</td>

							</tr>
							<%
							}
							}
							%>

							<tr class="table-dark">
								<td class="text-center py-3" colspan="5"
									style="font-weight: 600;">Other Charges</td>
							</tr>

							<!-- Row for delivery tip with radio buttons -->
							<tr>
								<td class="py-3">Delivery Tip</td>
								<td colspan="4" class="py-3">
									<div class="d-flex justify-content-start">
										<div class="form-check mr-4">
											<input class="form-check-input radiobutton" type="radio"
												name="deliveryTip" value="0" checked onclick="updateTotal()">
											<label class="form-check-label">Rupee 0</label>
										</div>
										<div class="form-check mr-4">
											<input class="form-check-input radiobutton" type="radio"
												name="deliveryTip" value="10" onclick="updateTotal()">
											<label class="form-check-label">Rupees 10</label>
										</div>
										<div class="form-check mr-4">
											<input class="form-check-input radiobutton" type="radio"
												name="deliveryTip" value="20" onclick="updateTotal()">
											<label class="form-check-label">Rupees 20</label>
										</div>
										<div class="form-check mr-4">
											<input class="form-check-input radiobutton" type="radio"
												name="deliveryTip" value="30" onclick="updateTotal()">
											<label class="form-check-label">Rupees 30</label>
										</div>
									</div>
								</td>
							</tr>

							<tr>
								<td class="py-3">Platform Fee</td>
								<td colspan="4" class="py-3">Rupees 10</td>
							</tr>
							<tr>
								<td class="py-3">Feed India Donation</td>
								<td colspan="4" class="py-3"><input type="checkbox"
									id="feedIndiaDonation" name="feedIndiaDonation" value="1"
									onclick="updateTotal()"> Rupee 1</td>
							</tr>
							<tr>
								<td class="py-3 table-dark text-center" colspan="5"><strong>Delivery
										Details</strong></td>
							</tr>
							<tr>
								<td class="py-3">Email</td>
								<td colspan="4" class="py-3"><%=user.getEmail()%></td>
							</tr>
							<tr>
								<td class="py-3">Phone Number</td>
								<td colspan="4" class="py-3"><%=user.getPhonenumber()%></td>
							</tr>
							<tr>
								<td class="py-3">Address</td>
								<td colspan="4" class="py-3"><%=user.getAddress()%></td>
							</tr>
							<tr class="table-dark">
								<td class="py-3">Total Charges</td>
								<td colspan="4" class="py-3">Rupees <span id="totalAmount"><%=total + 10%></span></td>
							</tr>
							<tr>
								<td class="py-3">Payment Mode</td>
								<td colspan="4" class="py-3">
									<div class="d-flex">
										<div class="form-check mr-4">
											<input class="form-check-input radiobutton" type="radio"
												name="paymentMode" value="online" id="paymentModeOnline"
												required> <label class="form-check-label"
												for="paymentModeOnline">Online</label>
										</div>
										<div class="form-check mr-4">
											<input class="form-check-input radiobutton" type="radio"
												name="paymentMode" value="cashondelivery"
												id="paymentModeCOD" checked required> <label
												class="form-check-label" for="paymentModeCOD">Cash
												on Delivery</label>
										</div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					<%
					if (user.getPhonenumber() != null && user.getAddress() != null && !user.getAddress().trim().isEmpty()
							&& !user.getAddress().equals("null")) {
					%>
					<div class="text-end mt-2">
						<p class="text-danger">Verify your delivery details before
							placing the order</p>
						<button type="submit" class="btn btn-success mb-5 p-2">Proceed</button>
					</div>
				</form>
				<%
				} else {
				%>
				<p class="text-danger mt-2">Update your delivery details to
					place order</p>
				<p id="updateProfile" data-bs-toggle="modal"
					data-bs-target="#updateModal" class="btn btn-warning mb-5 p-2"
					style="background-color: #ffc107; border: 1px solid transparent;"
					onmouseover="this.style.backgroundColor='#ffc61a'; this.style.border='1px solid #ffc61a';"
					onmouseout="this.style.backgroundColor='#ffc107'; this.style.border='1px solid transparent';">
					Update</p>
				<%
				}
				%>
				<%
				} else {
				%>
				<div class="alert alert-warning mt-4" role="alert">Your cart
					is empty.</div>
				<%
				}
				if(user==null || session==null){
				%>
				 <div class="alert alert-danger mt-4" role="alert">You are not logged in!</div>
				<%
				}
				%>

				<a href="searchRestaurants" class="text-decoration-none">
					<div class="d-flex justify-content-center">
						<div class="card floating-cart col-md-8"
							style="background-color: lightgreen;">
							<div
								class="card-body d-flex align-items-center justify-content-center"
								style="height: 70px;">
								<p class="mb-0 text-white">
									<%=cart != null ? cart.size() : 0%>
									items in cart, Add more? <i class="fas fa-cart-plus"></i>
								</p>
							</div>
						</div>
					</div>
				</a>
			</div>
		</div>
	</div>
	<script>
		document.addEventListener("DOMContentLoaded", function() {
			document.querySelectorAll('.remove-item').forEach(function(button) {
				button.addEventListener('click', function() {
					var menuId = this.getAttribute('data-menuid');
					var form = document.createElement('form');
					form.method = 'post';
					form.action = 'AddToCartServlet';

					var inputMenuId = document.createElement('input');
					inputMenuId.type = 'hidden';
					inputMenuId.name = 'menuId';
					inputMenuId.value = menuId;
					form.appendChild(inputMenuId);

					var inputRedirectUrl = document.createElement('input');
					inputRedirectUrl.type = 'hidden';
					inputRedirectUrl.name = 'redirectUrl';
					inputRedirectUrl.value = 'cart.jsp';
					form.appendChild(inputRedirectUrl);

					document.body.appendChild(form);
					form.submit();
				});
			});
		});
	</script>

	<script>
		document.addEventListener("DOMContentLoaded", function() {
			var cart = <%=cart != null ? cart.size() : 0%>;
			var backLink = document.getElementById("back-link");
			if (cart === 0) {
				backLink.href = "searchRestaurants";
			} else {
				backLink.href = "menuItem.jsp";
			}
		});

		function updateItemTotal(selectElement) {
			let price = parseFloat(selectElement.getAttribute('data-price'));
			let quantity = parseInt(selectElement.value);
			let menuId = selectElement.getAttribute('data-menuid');

			// Calculate new item total
			let itemTotal = price * quantity;
			document.getElementById('item-total-' + menuId).innerText = itemTotal
					.toFixed(2);

			// Update the grand total
			updateGrandTotal();
		}

		function updateGrandTotal() {
			let grandTotal = 0;

			// Sum up all item totals
			document.querySelectorAll('.item-total').forEach(function(item) {
				grandTotal += parseFloat(item.innerText) || 0;
			});

			// Get additional charges
			let platformFee = 10; // Example fee; adjust if needed
			let deliveryTip = parseFloat(document
					.querySelector('input[name="deliveryTip"]:checked').value) || 0;
			let feedIndiaDonation = document
					.getElementById('feedIndiaDonation').checked ? 1 : 0;

			// Calculate new grand total
			let newGrandTotal = grandTotal + platformFee + deliveryTip
					+ feedIndiaDonation;

			// Update the displayed total
			document.getElementById('totalAmount').innerText = newGrandTotal
					.toFixed(2);
		}

		function updateTotal() {
			updateGrandTotal();
		}

		// Attach updateTotal to delivery tip radio buttons and donation checkbox
		document.querySelectorAll('input[name="deliveryTip"]').forEach(
				function(radio) {
					radio.addEventListener('change', updateTotal);
				});
		document.getElementById('feedIndiaDonation').addEventListener('change',
				updateTotal);

		// Ensure to call updateGrandTotal() initially to set the correct value on page load
		updateGrandTotal();
	</script>

</body>
</html>
