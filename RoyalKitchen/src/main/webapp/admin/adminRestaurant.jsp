<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="adminNavbar.jsp"%>
<%@ page import="com.tapfoods.model.Admin, com.tapfoods.model.Restaurant"%>

<% 
// Session validation for admin
admin = (Admin) session.getAttribute("admin");
if (admin == null || admin.getAdminid() == null) {
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    response.sendRedirect("adminSignIn.jsp");
    return;
}
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin</title>

    <!--
    Favicon
    <p>Sets the favicon for the admin page</p>
    -->
    <link rel="icon" href="styles/images/favicon.png" type="image/png">

    <!--
    Bootstrap CSS
    <p>Includes the Bootstrap 5.0.2 CSS for responsive layout and design</p>
    -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.2/css/bootstrap.min.css" rel="stylesheet">

    <style>
        /* 
        Full height for HTML and body elements 
        <p>Ensures that the HTML and body elements occupy the full height of the viewport</p>
        */
        html, body {
            height: 100%;
            margin: 0;
        }

        /* 
        Full height container 
        <p>Ensures the container div takes up the full height of the viewport</p>
        */
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
    <div class="container d-flex align-items-center justify-content-center full-height-container">
        <h1>Admin Body</h1>
    </div>

    <!--
    Popper.js library
    <p>Includes the Popper.js library for positioning tooltips and popovers</p>
    -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/2.9.2/umd/popper.min.js"></script>

    <!--
    Bootstrap JavaScript library
    <p>Includes the Bootstrap JavaScript library for interactive components</p>
    -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.2/js/bootstrap.min.js"></script>
</body>
</html>