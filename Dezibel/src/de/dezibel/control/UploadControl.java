package de.dezibel.control;

import de.dezibel.ErrorCode;
import de.dezibel.data.Album;
import de.dezibel.data.Database;
import de.dezibel.data.Genre;
import de.dezibel.data.Label;
import de.dezibel.data.User;

/**
 *
 * @author Tobias, Richard
 */
public class UploadControl {
    
    private Database db;
    
    public UploadControl() {
        db = Database.getInstance();
    }
    
    public ErrorCode upload(String title, User user, String path, Genre genre, Label label, Album album) {
        return db.addMedium(title, user, path, genre, label, album);
    }
    
    public User[] getSelectableUsers(Label label) {
        User u = db.getLoggedInUser();
        User selectableUsers[];
        if(label == null) {
            selectableUsers = new User[1];
            selectableUsers[0] = u;
        }
        else {
            selectableUsers = new User[label.getArtists().size()+1];
            selectableUsers[0] = u;
            for(int i=1; i<selectableUsers.length; i++)
                selectableUsers[i] = label.getArtists().get(i-1);
        }
        return selectableUsers;
    }
    
    public Genre[] getSelectableGenres(Genre topGenre) {
        if(topGenre.getSubGenres().isEmpty())
            return 
    }
    
    public Label[] getSelectableLabels() {
        
    }
    
    public Album[] getSelectableAlbums() {
        
    }
    
}
