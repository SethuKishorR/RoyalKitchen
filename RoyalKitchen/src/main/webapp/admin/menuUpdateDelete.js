/**
 * Sets values for modal input fields.
 * @param {string} restaurantMenuId - The restaurant menu ID.
 * @param {string} menuName - The menu name.
 * @param {string} menuId - The menu ID.
 */
function setModalData(restaurantMenuId, menuName, menuId) {
    document.getElementById('restaurantMenuIdInput').value = restaurantMenuId;
    document.getElementById('menuNameInput').value = menuName;
    document.getElementById('menuIdInput').value = menuId;
    document.getElementById('restaurantMenuId').value = restaurantMenuId;
    document.getElementById('menuName').value = menuName;
}

/**
 * Updates hidden input fields with the values from visible fields.
 * @param {string} menuId - The menu ID.
 */
function updateHiddenInputs(menuId) {
    document.getElementById('menuNameInput' + menuId).value = document.getElementById('menuName' + menuId).value;
    document.getElementById('menuPriceInput' + menuId).value = document.getElementById('menuPrice' + menuId).value;
    document.getElementById('menuDescriptionInput' + menuId).value = document.getElementById('menuDescription' + menuId).value;
    document.getElementById('menuAvailabilityInput' + menuId).value = document.getElementById('menuAvailability' + menuId).value;
    document.getElementById('imagePathInput' + menuId).value = document.getElementById('imagePath' + menuId).value;
}