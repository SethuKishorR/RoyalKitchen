<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="admin/styles/images/favicon.png" type="image/png">
    <title>RoyalKitchen</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Links -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Merienda:wght@300..900&family=Stylish&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="admin/styles/animation.css">
    <style>
        body {
            margin: 0;
            padding: 0;
            overflow: hidden;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background: linear-gradient(135deg, 
                #3b0a0a, /* Very Dark Red */
                #6d0f0f, /* Dark Red */
                #8c1d1d, /* Deep Red */
                #a33c3c  /* Crimson Red */
            );
            background-size: 600% 600%;
            animation: gradientAnimation 4s ease infinite;
            font-family: 'Merienda', cursive;
            text-align: center;
        }
        .loading-text {
    	    font-family: 'Poppins', sans-serif;
    	    letter-spacing:2px;
	    }
    </style>
</head>

<body>
    <!-- Royal Kitchen Animation -->
    <div class="animation-container" id="text-container">
        <h1 class="animation-text">
            <span>Royal Kitchen</span>
        </h1>
        <p class="sub-text">Good food choices are good investments</p>
    </div>

    <!-- Pancake Animation -->
    <div class="loader" id="loader">
        <div class="tall-stack">
            <div class="butter falling-element"></div>
            <div class="pancake falling-element"></div>
            <div class="pancake falling-element"></div>
            <div class="pancake falling-element"></div>
            <div class="pancake falling-element"></div>
            <div class="pancake falling-element"></div>
            <div class="pancake falling-element"></div>
            <div class="plate">
                <div class="plate-bottom"></div>
                <div class="shadow"></div>
            </div>
        </div>
    </div>

    <!-- Loading Text -->
    <div class="loading-text" id="loadingText">Loading...</div>

   <!-- Hidden Form for Redirect -->
<form id="redirectForm" action="searchRestaurants" method="POST" style="display: none;"></form>

<script>
    window.onload = function() {
        // Step 1: Show Royal Kitchen text for 4 seconds
        setTimeout(function() {
            document.querySelector('.animation-container').style.display = 'none'; // Hide Royal Kitchen text
            document.querySelector('#loader').style.display = 'block'; // Show pancake loader

            // Step 2: Run pancake animation once (4.5 seconds)
            setTimeout(function() {
                document.querySelector('#loader').style.display = 'none'; // Hide loader after it runs
                document.querySelector('#loadingText').style.display = 'block'; // Show Loading... text

                // Step 3: Submit the form once the next page is ready
                document.getElementById("redirectForm").submit(); 
            }, 5000); // Pancake loader duration (matches the animation)
        }, 4000); // Time before starting the pancake loader
    };
</script>

</body>

</html>