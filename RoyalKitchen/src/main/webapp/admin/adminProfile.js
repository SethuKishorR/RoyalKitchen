document.addEventListener('DOMContentLoaded', function() {
    /**
     * Toggles the disabled state of a form field and updates the corresponding button's appearance and text.
     * <p>
     * When a field is disabled, the button is set to "Edit" with a yellow warning style. When the field is enabled,
     * the button is set to "Save" with a green success style.
     * </p>
     * 
     * @param {HTMLElement} field - The form field to enable or disable.
     * @param {HTMLElement} button - The button that triggers the toggle action.
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
     * Enables a list of form fields by removing the 'disabled' attribute from each field.
     * 
     * @param {HTMLElement[]} fields - An array of form fields to enable.
     */
    function enableFields(fields) {
        fields.forEach(field => field.removeAttribute('disabled'));
    }

    // Admin Modal

    // Get references to the edit password button and form fields.
    const editPasswordBtn = document.getElementById('editPasswordBtn');
    const adminIdField = document.getElementById('adminIdField');
    const restaurantIdField = document.getElementById('restaurantIdField');
    const adminKeyField = document.getElementById('adminKeyField');
    const passwordField = document.getElementById('passwordField');
    const saveAdminChangesBtn = document.getElementById('saveChanges');

    /**
     * Adds a click event listener to the edit password button, which toggles the disabled state of the password field.
     */
    editPasswordBtn.addEventListener('click', () => toggleField(passwordField, editPasswordBtn));

    /**
     * Adds a click event listener to the save button that enables all the form fields before submitting the form.
     */
    saveAdminChangesBtn.addEventListener('click', function() {
        enableFields([adminIdField, restaurantIdField, adminKeyField, passwordField]);
    });
});