/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dezibel.control;

import de.dezibel.data.Database;
import de.dezibel.data.Medium;
import de.dezibel.data.Playlist;

/**
 *
 * @author Benny
 */
public class PlaylistControl {
    
    public void createPlaylist(String title, Medium firstMedium){
        Database.getInstance().addPlaylist(firstMedium, title,
                Database.getInstance().getLoggedInUser());
        
    }
    
    public void addMediumToPlaylist(Medium medium, Playlist playlist){
        playlist.addMedium(medium);
    }
}
