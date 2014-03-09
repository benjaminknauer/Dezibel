/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dezibel.control;

import de.dezibel.data.Database;
import de.dezibel.data.Label;
import de.dezibel.data.User;

/**
 *
 * @author Henner
 */
public class ApplicationControl {
    
    public ApplicationControl(){
        
    }
    
    public void createApplication(boolean applicationFromArtist, String text, User artist, Label label){
        Database.getInstance().addApplication(applicationFromArtist, text, artist, label);
    }
    
}
