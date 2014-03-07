package de.dezibel.control;

import de.dezibel.ErrorCode;
import de.dezibel.data.Album;
import de.dezibel.data.Database;
import de.dezibel.data.Genre;
import de.dezibel.data.Label;
import de.dezibel.data.User;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 * Controls the complete upload process.
 * @author Tobias, Richard
 */
public class UploadControl {

    private Database db;

    public UploadControl() {
        db = Database.getInstance();
    }
    
    /**
     * Tries to upload the medium with the given parameters.
     * @param title The title of the song
     * @param user The publishing artist
     * @param path The path of the file or null, if no file was selected
     * @param genre The genre of the song
     * @param label The publishing label
     * @param album The album to add this song to
     * @return Returns a fitting error code
     */
    public ErrorCode upload(String title, User user, String path, Genre genre, Label label, Album album) {
        if (!user.isArtist()) {
            return ErrorCode.USER_IS_NOT_ARTIST;
        }
        return db.addMediumToAlbum(title, user, path, genre, label, album);
    }
    
    /**
     * Asks the user to input a pseudonym if no pseudonym was set before.
     * @param parent The parent component that shows the dialog
     * @param user The user that gets promoted
     */
    public void promoteUserToArtist(Component parent, User user) {
        String result = JOptionPane.showInputDialog(parent, "Pseudonym angeben:",
                "Pseudonym fehlt", JOptionPane.WARNING_MESSAGE);
        if (result != null && !result.isEmpty()) {
            user.setPseudonym(result);
            user.promoteToArtist();
        }
    }
    
    /**
     * Gets the users that can get selected in the upload dialog, depending on the
     * selected label.
     * @param label The currently selected label
     * @return An array containing all selectable users
     */
    public User[] getSelectableUsers(Label label) {
        User u = db.getLoggedInUser();
        User selectableUsers[];
        if (label == null) {
            selectableUsers = new User[1];
            selectableUsers[0] = u;
        } else {
            selectableUsers = new User[label.getArtists().size() + 1];
            selectableUsers[0] = u;
            for (int i = 1; i < selectableUsers.length; i++) {
                selectableUsers[i] = label.getArtists().get(i - 1);
            }
        }
        return selectableUsers;
    }
    
    /**
     * Gets all genres that can be selected in the upload dialog.
     * @return  An array containing all selectable genres
     */
    public Genre[] getSelectableGenres() {
        ArrayList<Genre> result = getGenresRecursive(db.getTopGenre());
        Genre[] resultArray = new Genre[result.size()];
        result.toArray(resultArray);
        return resultArray;
    }
    
    /**
     * Gets all genres that are subgenres of the submitted topGenre.
     * @param topGenre The genre to process
     * @return An ArrayList containing all subgenres
     */
    private ArrayList<Genre> getGenresRecursive(Genre topGenre) {
        ArrayList<Genre> result = new ArrayList<>();
        result.add(topGenre);
        for (Genre g : topGenre.getSubGenres()) {
            result.addAll(getGenresRecursive(g));
        }
        return result;
    }
    
    /**
     * Gets all labels that can be selected in the upload dialog.
     * @return  An array containing all selectable labels
     */
    public Label[] getSelectableLabels() {
        User u = db.getLoggedInUser();
        ArrayList<Label> result = new ArrayList<Label>();
        result.add(null);
        result.addAll(u.getManagedLabels());
        result.addAll(u.getPublishingLabels());
        Label[] resultArray = new Label[result.size()];
        result.toArray(resultArray);
        return resultArray;
    }
    
    /**
     * Gets all albums that can be selected in the upload dialog.
     * @return  An array containing all selectable albums
     */
    public Album[] getSelectableAlbums() {
        User u = db.getLoggedInUser();
        Set<Album> result = new HashSet<Album>();
        result.add(null);
        result.addAll(u.getCreatedAlbums());
        for (Label l : u.getManagedLabels()) {
            for (User lu : l.getArtists()) {
                result.addAll(lu.getCreatedAlbums());
            }
        }
        Album[] resultArray = new Album[result.size()];
        result.toArray(resultArray);
        return resultArray;
    }

}
