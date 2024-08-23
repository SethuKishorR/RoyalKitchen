/**
 *
 * <p>This script is responsible for handling the enabling and disabling of input fields in the
 * restaurant modal. It allows users to toggle fields between editable and non-editable states
 * using corresponding buttons. The script also enables all fields when the "Save" button is clicked,
 * preparing the form for submission.</p>
 *
 * <p>The script listens for the 'DOMContentLoaded' event to ensure that all elements are fully loaded
 * before attaching event listeners.</p>
 */
document.addEventListener('DOMContentLoaded', function() {

    /**
     * <p>toggleField</p>
     *
     * <p>Toggles the enabled/disabled state of an input field. If the field is currently disabled,
     * it will be enabled, and the corresponding button will switch to a "Save" state with a green color.
     * If the field is enabled, it will be disabled, and the button will switch back to an "Edit" state
     * with a yellow color.</p>
     *
     * @param {HTMLElement} field - The input field to toggle.
     * @param {HTMLElement} button - The button used to toggle the field.
     */
    function toggleField(field, button) {
        if (field.hasAttribute('disabled')) {
            field.removeAttribute('disabled');
            button.classList.remove('btn-warning');
            button.classList.add('btn-secondary');
        } else {
            field.setAttribute('disabled', 'disabled');
            button.classList.remove('btn-secondary');
            button.classList.add('btn-warning');
        }
    }

    /**
     * <p>enableFields</p>
     *
     * <p>Enables a list of input fields, making them all editable. This function is typically called
     * when the user clicks the "Save" button, ensuring that all fields are enabled for form submission.</p>
     *
     * @param {HTMLElement[]} fields - An array of input fields to enable.
     */
    function enableFields(fields) {
        fields.forEach(field => field.removeAttribute('disabled'));
    }

    // Restaurant Modal - Get references to all relevant buttons and fields in the modal
    const editRestaurantNameBtn = document.getElementById('editRestaurantNameBtn');
    const editDeliveryTimeBtn = document.getElementById('editDeliveryTimeBtn');
    const editCuisineTypeBtn = document.getElementById('editCuisineTypeBtn');
    const editAddressBtn = document.getElementById('editAddressBtn');
    const editRatingsBtn = document.getElementById('editRatingsBtn');
    const editIsActiveBtn = document.getElementById('editIsActiveBtn');
    const editImagePathBtn = document.getElementById('editImagePathBtn');

    const restaurantIdField = document.getElementById('restaurantIdField');
    const restaurantNameField = document.getElementById('restaurantNameField');
    const deliveryTimeField = document.getElementById('deliveryTimeField');
    const cuisineTypeField = document.getElementById('cuisineTypeField');
    const addressField = document.getElementById('addressField');
    const ratingsField = document.getElementById('ratingsField');
    const isActiveField = document.getElementById('isActiveField');
    const imagePathField = document.getElementById('imagePathField');

    const saveRestaurantChangesBtn = document.getElementById('saveChanges');

    // Attach event listeners to buttons, linking them to the corresponding fields
    editRestaurantNameBtn.addEventListener('click', () => toggleField(restaurantNameField, editRestaurantNameBtn));
    editDeliveryTimeBtn.addEventListener('click', () => toggleField(deliveryTimeField, editDeliveryTimeBtn));
    editCuisineTypeBtn.addEventListener('click', () => toggleField(cuisineTypeField, editCuisineTypeBtn));
    editAddressBtn.addEventListener('click', () => toggleField(addressField, editAddressBtn));
    editRatingsBtn.addEventListener('click', () => toggleField(ratingsField, editRatingsBtn));
    editIsActiveBtn.addEventListener('click', () => toggleField(isActiveField, editIsActiveBtn));
    editImagePathBtn.addEventListener('click', () => toggleField(imagePathField, editImagePathBtn));

    /**
     * <p>Save Changes Button</p>
     *
     * <p>Enables all fields when the "Save" button is clicked, preparing the form for submission. This
     * ensures that any changes made to the fields are not lost and can be submitted to the server.</p>
     */
    saveRestaurantChangesBtn.addEventListener('click', function() {
        enableFields([restaurantIdField, restaurantNameField, deliveryTimeField, cuisineTypeField, addressField, ratingsField, isActiveField, imagePathField]);
    });
});