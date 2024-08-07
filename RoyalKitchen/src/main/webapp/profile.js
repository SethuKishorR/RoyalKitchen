/**
 * @file profile.js
 * @description Handles interactive functionalities for the profile management page.
 * Enables fields for editing upon clicking edit buttons and ensures all fields are enabled
 * before submitting the form.
 */

document.addEventListener('DOMContentLoaded', function() {
    /**
     * Edit buttons
     * References to the buttons used for enabling and disabling input fields.
     */
    const editUsernameBtn = document.getElementById('editUsernameBtn');
    const editPhoneNumberBtn = document.getElementById('editPhoneNumberBtn');
    const editAddressBtn = document.getElementById('editAddressBtn');
    const editPasswordBtn = document.getElementById('editPasswordBtn');

    /**
     * Input fields
     * References to the input fields in the profile form.
     */
    const emailField = document.getElementById('emailField');
    const usernameField = document.getElementById('usernameField');
    const phoneNumberField = document.getElementById('phoneNumberField');
    const addressField = document.getElementById('addressField');
    const passwordField = document.getElementById('passwordField');

    /**
     * Submit button
     * Reference to the button used for submitting the profile update form.
     */
    const saveChangesBtn = document.getElementById('saveChanges');

    /**
     * Event Listener for Edit Username Button
     * Toggles the editable state of the username field.
     */
    editUsernameBtn.addEventListener('click', () => toggleField(usernameField, editUsernameBtn));

    /**
     * Event Listener for Edit Phone Number Button
     * Toggles the editable state of the phone number field.
     */
    editPhoneNumberBtn.addEventListener('click', () => toggleField(phoneNumberField, editPhoneNumberBtn));

    /**
     * Event Listener for Edit Address Button
     * Toggles the editable state of the address field.
     */
    editAddressBtn.addEventListener('click', () => toggleField(addressField, editAddressBtn));

    /**
     * Event Listener for Edit Password Button
     * Toggles the editable state of the password field.
     */
    editPasswordBtn.addEventListener('click', () => toggleField(passwordField, editPasswordBtn));

    /**
     * Toggles the disabled state of a given input field and updates the button state.
     * @param {HTMLElement} field - The input field to toggle.
     * @param {HTMLElement} button - The button to update.
     */
    function toggleField(field, button) {
        if (field.hasAttribute('disabled')) {
            field.removeAttribute('disabled');
            button.innerHTML = '<i class="fas fa-save"></i> Save';
            button.classList.remove('btn-warning');
            button.classList.add('btn-success');
        } else {
            field.setAttribute('disabled', 'disabled');
            button.innerHTML = '<i class="fas fa-edit"></i> Edit';
            button.classList.remove('btn-success');
            button.classList.add('btn-warning');
        }
    }

    /**
     * Event Listener for Save Changes Button
     * Enables all input fields before form submission.
     */
    saveChangesBtn.addEventListener('click', function() {
        emailField.removeAttribute('disabled');
        usernameField.removeAttribute('disabled');
        phoneNumberField.removeAttribute('disabled');
        addressField.removeAttribute('disabled');
        passwordField.removeAttribute('disabled');
    });
});