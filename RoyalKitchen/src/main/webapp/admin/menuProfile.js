document.addEventListener('DOMContentLoaded', function() {
    /**
     * Toggles the enabled/disabled state of input fields in the row.
     * @param {string} rowId - The ID of the table row.
     */
    function toggleRowFields(rowId) {
        const row = document.getElementById(rowId);
        const inputs = row.querySelectorAll('.field-editable');
        inputs.forEach(input => {
            if (input.hasAttribute('disabled')) {
                input.removeAttribute('disabled');
            } else {
                input.setAttribute('disabled', 'disabled');
            }
        });
    }

    /**
     * Toggles the appearance of the edit button.
     * @param {HTMLElement} button - The button being clicked.
     */
    function toggleButtonAppearance(button) {
        button.classList.toggle('btn-warning');
        button.classList.toggle('btn-secondary');
    }

    // Get all edit buttons and add event listeners to them
    const editButtons = document.querySelectorAll('.edit-button');
    editButtons.forEach(button => {
        const rowId = button.closest('tr').id;
        button.addEventListener('click', function() {
            toggleButtonAppearance(button);
            toggleRowFields(rowId);
        });
    });

    // If necessary, add similar logic for update and delete buttons
});