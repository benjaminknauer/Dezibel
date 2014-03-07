/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dezibel.control;

import de.dezibel.data.Database;
import de.dezibel.data.User;

/**
 *
 * @author Bastian
 */
public class LabelControl {
    
    /**
     * Creates a new Label with user as its first labelmanager.
     * 
     * @param user first labelmanager
     * @param name labels name
     */
    public void createLabel(User user, String name){
        Database.getInstance().addLabel(user, name);
    }
    
        public void promoteUserToLabelManager(User user) {
            user.promoteToLabelManager();
    }
    
}
