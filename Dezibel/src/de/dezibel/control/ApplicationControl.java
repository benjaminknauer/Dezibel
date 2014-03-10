/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dezibel.control;

import de.dezibel.data.Application;
import de.dezibel.data.Database;
import de.dezibel.data.Label;
import de.dezibel.data.User;

/**
 * Controls functions concerning the application feature.
 * @author Henner
 */
public class ApplicationControl {
    
    /**
     * Creates a new application with the given parameters. If a label manager applied
     * on his own label, no acception is needed.
     * @param applicationFromArtist true, if the artist created the application, else false
     * @param text The text of the application
     * @param artist The assigned artist
     * @param label The assigned label
     */
    public void createApplication(boolean applicationFromArtist, String text, User artist, Label label){
        // A label-manager's application does not need to be accepted.
        if(label.getLabelManagers().contains(artist)){
            artist.addArtistLabel(label);
            label.addArtist(artist);
            return;
        }
        Database.getInstance().addApplication(applicationFromArtist, text, artist, label);
    }
    
    /**
     * Accepts the submitted application.
     * @param application The application to accept
     */
    public void acceptApplication(Application application){
        application.accept();
    }
    
    /**
     * Declines the submitted application.
     * @param application The application to decline
     */
    public void declineApplication(Application application) {
        application.decline();
    }
    
}
