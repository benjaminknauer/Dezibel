package de.dezibel.control;

import de.dezibel.data.Database;
import de.dezibel.data.Medium;
import de.dezibel.data.Playlist;

/**
 * Supplies functions for managing playlists.
 * @author Benny
 */
public class PlaylistControl {
    
    /**
     * Creates a playlist with the given parameters.
     * @param title The title of the playlist
     * @param firstMedium The first medium to add to the playlist
     */
    public void createPlaylist(String title, Medium firstMedium){
        Database.getInstance().addPlaylist(firstMedium, title,
                Database.getInstance().getLoggedInUser());
        
    }
    
    /**
     * Adds a medium to the submitted playlist.
     * @param medium The medium to add
     * @param playlist The playlist to add to
     */
    public void addMediumToPlaylist(Medium medium, Playlist playlist){
        playlist.addMedium(medium);
    }
    
    /**
     * Removes the medium at the given index.
     * @param playlist The playlist to remove from
     * @param index The index to remove
     */
    public void removeMediumAt(Playlist playlist, int index){
        playlist.removeMediumAt(index);
    }
    
    /**
     * Removes a medium from the submitted playlist.
     * @param playlist The playlist to remove from
     * @param medium The medium to remove
     */
    public void removeMedium(Playlist playlist, Medium medium) {
        playlist.removeMedium(medium);
    }
}
