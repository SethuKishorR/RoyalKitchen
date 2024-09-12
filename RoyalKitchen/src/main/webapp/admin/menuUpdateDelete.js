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