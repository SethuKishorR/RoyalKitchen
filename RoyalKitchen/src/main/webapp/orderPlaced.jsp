<%@ page import="java.util.ArrayList"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    ArrayList<Integer> cart = (ArrayList<Integer>) session.getAttribute("cart");
    if (cart != null) {
        cart.clear();
    }
    session.setAttribute("cart", new ArrayList<Integer>());
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
 <link rel="icon" href="admin/styles/images/favicon.png" type="image/png">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<title>Order Placed</title>
<link
	href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@300&display=swap"
	rel="stylesheet">
<style>
body {
	background: #e5e5e5;
	font-family: 'Source Sans Pro', sans-serif;
}

#card {
	width: 320px;
	text-align: center;
	box-shadow: 0px 10px 20px rgba(0, 0, 0, 0.3); /* Added box shadow */
	border-radius: 8px; /* Rounded corners for a smoother look */
}

#upper-side {
	padding: 2em;
	background-color: #8BC34A;
	display: block;
	color: #fff;
	border-top-right-radius: 8px;
	border-top-left-radius: 8px;
}

#checkmark {
	font-size: 4em;
	margin: 0 auto;
	color: #fff;
}

#status {
	font-weight: lighter;
	text-transform: uppercase;
	letter-spacing: 2px;
	font-size: 1.2em;
	margin-top: -.2em;
	margin-bottom: 0;
}

#lower-side {
	padding: 2em 2em 5em 2em;
	background: #fff;
	display: block;
	border-bottom-right-radius: 8px;
	border-bottom-left-radius: 8px;
}

#message {
	margin-top: -.5em;
	color: #757575;
	letter-spacing: 1px;
}

#contBtn {
	position: relative;
	top: 1.5em;
	text-decoration: none;
	background: #8bc34a;
	color: #fff;
	margin: auto;
	padding: .8em 3em;
	box-shadow: 0px 15px 30px rgba(50, 50, 50, 0.21);
	border-radius: 25px;
	transition: all .4s ease;
}

#contBtn:hover {
	box-shadow: 0px 15px 30px rgba(50, 50, 50, 0.41);
	transition: all .4s ease;
}
</style>
</head>
<body>
<div class="d-flex justify-content-center align-items-center vh-100">
    <div id="card">
        <div id="upper-side">
            <div id="checkmark">✓</div>
            <h3 id="status">Success</h3>
        </div>
        <div id="lower-side">
            <p id="message">Your order has been placed successfully!</p>
            <a href="OrderHistory" id="contBtn">Continue</a>
        </div>
    </div>
</div>
</body>
</html>
