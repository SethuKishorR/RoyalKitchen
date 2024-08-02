package com.tapfoods.DAO;

import java.util.ArrayList;

import com.tapfoods.model.Menu;

/**
 * The MenuDAO interface defines the data access operations for {@link Menu} entities.
 * <p>This interface provides methods for adding, retrieving, updating, and deleting menus.</p>
 */
public interface MenuDAO {

    /**
     * Adds a new menu to the database.
     * <p>This method takes a {@link Menu} object and inserts it into the database.</p>
     * 
     * @param m the {@link Menu} object to be added
     * @return an integer indicating the result of the operation (e.g., the number of rows affected)
     */
    int addMenu(Menu m);

    /**
     * Retrieves all menus from the database.
     * <p>This method returns a list of all {@link Menu} objects stored in the database.</p>
     * 
     * @return an {@link ArrayList} of {@link Menu} objects representing all menus
     */
    ArrayList<Menu> getAllMenu();

    /**
     * Retrieves a menu from the database by its ID.
     * <p>This method searches for a {@link Menu} with the specified menu ID.</p>
     * 
     * @param menuid the ID of the menu to be retrieved
     * @return the {@link Menu} object corresponding to the specified ID, or {@code null} if no menu is found
     */
    Menu getMenu(int menuid);

    /**
     * Updates an existing menu's information in the database.
     * <p>This method takes a {@link Menu} object with updated information and modifies the corresponding record in the database.</p>
     * 
     * @param m the {@link Menu} object containing updated information
     * @return an integer indicating the result of the operation (e.g., the number of rows affected)
     */
    int updateMenu(Menu m);

    /**
     * Deletes a menu from the database by its ID.
     * <p>This method removes the {@link Menu} record with the specified menu ID from the database.</p>
     * 
     * @param menuid the ID of the menu to be deleted
     * @return an integer indicating the result of the operation (e.g., the number of rows affected)
     */
    int deleteMenu(int menuid);
}
