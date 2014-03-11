/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dezibel.control;

import de.dezibel.data.Album;
import de.dezibel.data.Database;
import de.dezibel.data.Label;
import de.dezibel.data.Medium;
import de.dezibel.data.User;

/**
 * Manages the creation of albums.
 * @author Benny
 */
public class AlbumControl {
    
    /**
     * Creates a new album with the given parameters.
     * @param title The title of the new album
     * @param firstMedium The first medium to add to the album
     * @param coverPath The path of the cover file
     */
    public void createAlbum(String title, Medium firstMedium, User user, String coverPath){
               Database.getInstance().addAlbum(firstMedium, title, user, coverPath, true);
    }
    
     public void createAlbum(String title, Medium firstMedium, Label label, String coverPath){
               Database.getInstance().addAlbum(firstMedium, title, label, coverPath, true);
               
               
    }
    
    /**
     * Adds the given medium to the submitted album.
     * @param medium The medium to add
     * @param album The album to add to
     */
    public void addMediumToAlbum(Medium medium, Album album){
        album.addNewMedium(medium);
    }
}
