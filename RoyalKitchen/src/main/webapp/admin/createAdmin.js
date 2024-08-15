document.addEventListener('DOMContentLoaded', function() {
    // References to DOM elements for progress bar and buttons
    const progressBar = document.getElementById('progress-bar');
    const btnSubmitAll = document.getElementById('btnSubmitAll');
    const nextButton = document.getElementById('nextButton');
    const startButton = document.getElementById('startForm');
	const haveAccount = document.getElementById('haveAccount');
    
    /**
     * Updates the progress bar based on the current step.
     * <p>
     * Adjusts the width, color, and text of the progress bar. 
     * Controls the visibility of buttons and elements such as the start button, 
     * next button, and submission button based on the current step.
     * </p>
     */
    function updateProgressBar() {
        const progressValue = (currentStep - 1) * 33.3;
        progressBar.style.width = progressValue + '%';
        progressBar.setAttribute('aria-valuenow', progressValue);
        progressBar.innerText = Math.round(progressValue) + '% Complete';

        // Show the start button and 'Have an Account' link on step 1, otherwise hide them
        if (currentStep === 1) {
            startButton.style.display = 'block';
			haveAccount.style.display = 'block';
        } else if (currentStep > 1 && currentStep < 4) {
            startButton.style.display = 'none';
            nextButton.style.display = 'block';
            btnSubmitAll.style.display = 'none';
			haveAccount.style.display = 'none';
        } else {
            startButton.style.display = 'none';
            nextButton.style.display = 'none';
            btnSubmitAll.style.display = 'block';
			haveAccount.style.display = 'none';
        }

        // Define classes for step completion
        const stepCompletedClass = 'completed';
        const tickIconDisplay = 'inline';

        // Reset all steps' colors and icons
        document.getElementById('step1-text').classList.remove(stepCompletedClass);
        document.getElementById('step2-text').classList.remove(stepCompletedClass);
        document.getElementById('step3-text').classList.remove(stepCompletedClass);
        document.getElementById('step1-tick').style.display = 'none';
        document.getElementById('step2-tick').style.display = 'none';
        document.getElementById('step3-tick').style.display = 'none';

        // Update the progress bar color and steps based on current progress
        if (progressValue >= 33.3) {
            document.getElementById('step1-text').classList.add(stepCompletedClass); // Mark step 1 as completed
            document.getElementById('step1-tick').style.display = tickIconDisplay; // Show tick icon for step 1
        }
        if (progressValue >= 66.6) {
            document.getElementById('step2-text').classList.add(stepCompletedClass); // Mark step 2 as completed
            document.getElementById('step2-tick').style.display = tickIconDisplay; // Show tick icon for step 2
        }
        if (progressValue > 90) {
            document.getElementById('step3-text').classList.add(stepCompletedClass); // Mark step 3 as completed
            document.getElementById('step3-tick').style.display = tickIconDisplay; // Show tick icon for step 3
        }

        // Update the progress bar color
        if (progressValue === 33.3) {
            progressBar.classList.remove('bg-primary', 'bg-warning', 'bg-success');
            progressBar.classList.add('bg-danger');
        } else if (progressValue === 66.6) {
            progressBar.classList.remove('bg-danger', 'bg-warning', 'bg-success');
            progressBar.classList.add('bg-warning');
        } else if (progressValue > 90) {
            progressBar.classList.remove('bg-danger', 'bg-warning', 'bg-success');
            progressBar.classList.add('bg-success');
        }
    }

    /**
     * Displays the next step's modal based on the current step.
     * <p>
     * The first step opens the platform key modal, the second step opens the create account modal, 
     * and the third step opens the restaurant details modal.
     * </p>
     */
    function showNextStep() {
        if (currentStep === 1) {
            const platformKeyModal = new bootstrap.Modal(document.getElementById('platformKeyModal'));
            platformKeyModal.show();
        } else if (currentStep === 2) {
            const createAccountModal = new bootstrap.Modal(document.getElementById('createAccountModal'));
            createAccountModal.show();
        } else if (currentStep === 3) {
            const restaurantDetailsModal = new bootstrap.Modal(document.getElementById('restaurantDetailsModal'));
            restaurantDetailsModal.show();
        }
    }

    // Add click event listener to the start button to trigger the next step
    startButton.addEventListener('click', function() {
        showNextStep();
    });

    // Add click event listener to the next button to trigger the next step
    nextButton.addEventListener('click', function() {
        showNextStep();
    });

    /**
     * Handles the form submission for the platform key step.
     * <p>
     * Sends the form data to the 'adminSignUp' endpoint via POST request. 
     * If successful, advances to the next step and updates the progress bar.
     * </p>
     */
    document.getElementById('platformKeyForm').addEventListener('submit', function(event) {
        const formData = new FormData(event.target);
        fetch('adminSignUp', {
            method: 'POST',
            body: formData
        }).then(response => response.text())
          .then(result => {
              if (result === 'success') {
                  currentStep++;
                  updateProgressBar();
              }
          });
    });

    /**
     * Handles the form submission for the create account step.
     * <p>
     * Sends the form data to the 'adminSignUp' endpoint via POST request. 
     * If successful, advances to the next step and updates the progress bar.
     * </p>
     */
    document.getElementById('createAccountForm').addEventListener('submit', function(event) {
        const formData = new FormData(event.target);
        fetch('adminSignUp', {
            method: 'POST',
            body: formData
        }).then(response => response.text())
          .then(result => {
              if (result === 'success') {
                  currentStep++;
                  updateProgressBar();
              }
          });
    });

    /**
     * Handles the form submission for the restaurant details step.
     * <p>
     * Sends the form data to the 'adminSignUp' endpoint via POST request. 
     * If successful, advances to the next step and updates the progress bar.
     * </p>
     */
    document.getElementById('restaurantDetailsForm').addEventListener('submit', function(event) {
        const formData = new FormData(event.target);
        fetch('adminSignUp', {
            method: 'POST',
            body: formData
        }).then(response => response.text())
          .then(result => {
              if (result === 'success') {
                  currentStep++;
                  updateProgressBar();
              }
          });
    });

    /**
     * Handles the final form submission after all steps are completed.
     * <p>
     * Sends the form data to the 'adminSignUp' endpoint via POST request. 
     * If successful, alerts the user that the signup was successful.
     * </p>
     */
    document.getElementById('signup-form').addEventListener('submit', function(event) {
        const formData = new FormData(event.target);
        fetch('adminSignUp', {
            method: 'POST',
            body: formData
        }).then(response => response.text())
          .then(result => {
              if (result === 'success') {
                  alert('Sign up successful!');
              }
          });
    });

    // Initialize the progress bar on page load
    updateProgressBar();
});