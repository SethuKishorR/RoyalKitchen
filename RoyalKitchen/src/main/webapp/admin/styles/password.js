/**
 * Password Visibility Toggle
 * <p>
 * This script toggles the visibility of password fields when a user clicks on
 * the associated toggle icon. It changes the type attribute of the password
 * field between 'password' and 'text', and updates the icon class to reflect
 * the current state (eye or eye-slash).
 * </p>
 */
document.addEventListener('DOMContentLoaded', function () {
    var passwordToggles = document.querySelectorAll('[data-toggle="password"]');

    passwordToggles.forEach(function (toggle) {
        toggle.addEventListener('click', function () {
            var targetId = toggle.getAttribute('data-target');
            var passwordField = document.getElementById(targetId);
            var icon = toggle.querySelector('i');

            // Toggle the type attribute
            if (passwordField.type === 'password') {
                passwordField.type = 'text';
                icon.classList.remove('fa-eye');
                icon.classList.add('fa-eye-slash');
            } else {
                passwordField.type = 'password';
                icon.classList.remove('fa-eye-slash');
                icon.classList.add('fa-eye');
            }
        });
    });
});