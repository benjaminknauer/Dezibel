/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dezibel.control;

import de.dezibel.data.Album;
import de.dezibel.data.Database;
import de.dezibel.data.Medium;
import de.dezibel.data.Playlist;
import de.dezibel.data.User;

/**
 *
 * @author Benny
 */
public class AlbumControl {
    
    public void createAlbum(String title, Medium firstMedium, String coverPath){
               Database.getInstance().addAlbum(firstMedium, title, Database.getInstance()
                .getLoggedInUser(), coverPath, true);
    }
    
    public void addMediumToAlbum(Medium medium, Album album){
        album.addNewMedium(medium);
    }
}
