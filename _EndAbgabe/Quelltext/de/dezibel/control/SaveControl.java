package de.dezibel.control;

import de.dezibel.data.Database;

/**
 * Initiates the saving process
 * 
 * @author Tobias
 */
public class SaveControl {
    
    /**
     * Initiates the saving process of the database
     */
    public void save() {
        Database.getInstance().save();
    }
}
