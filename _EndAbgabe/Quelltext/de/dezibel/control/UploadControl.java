package de.dezibel.control;

import de.dezibel.ErrorCode;
import de.dezibel.data.Album;
import de.dezibel.data.Database;
import de.dezibel.data.Genre;
import de.dezibel.data.Label;
import de.dezibel.data.Medium;
import de.dezibel.data.User;
import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javax.swing.JOptionPane;

/**
 * Controls the complete upload process.
 *
 * @author Tobias, Richard
 */
public class UploadControl {

    private Database db;

    public UploadControl() {
        db = Database.getInstance();
    }

    /**
     * Tries to upload the medium with the given parameters.
     *
     * @param title The title of the song
     * @param user The publishing artist
     * @param path The path of the file or null, if no file was selected
     * @param genre The genre of the song
     * @param label The publishing label
     * @param album The album to add this song to
     * @param newAlbumName The name for the album if it has to be created
     * @param coverPath The path of the cover if the album has to be created
     * @param albumCreator The creator of the new album
     * @return Returns a fitting error code
     */
    public ErrorCode upload(String title, User user, String path, Genre genre, Label label, Album album,
            String newAlbumName, String coverPath, Object albumCreator) {
        if (!user.isArtist()) {
            return ErrorCode.USER_IS_NOT_ARTIST;
        }
        if (album != null) {
            return db.addMediumToAlbum(title, user, path, genre, label, album);
        } else {
            if (newAlbumName == null || newAlbumName.isEmpty()) {
                return db.addMedium(title, user, path, genre, label);
            } else {
                db.addMedium(title, user, path, genre, label);
                Medium m = db.getMedia().get(db.getMedia().size() - 1);
                if (albumCreator instanceof User) {
                    db.addAlbum(m, newAlbumName, (User) albumCreator, coverPath, false);
                } else if (albumCreator instanceof Label) {
                    db.addAlbum(m, newAlbumName, (Label) albumCreator, coverPath, false);
                }
                return ErrorCode.SUCCESS;
            }
        }
    }
    
    /**
     * Checks if the given file is playable.
     * @param f The file to check
     * @return true, if the file is playable, else false
     */
    public boolean isPlayable(File f) {
        try {
            Media m = new Media(f.toURI().toString());
            MediaPlayer tmpPlayer = new MediaPlayer(m);
            tmpPlayer.setVolume(0);
            tmpPlayer.play();
            tmpPlayer.stop();
            return true;
        }
        catch(MediaException me){
            return false;
        }
    }

    /**
     * Asks the user to input a pseudonym if no pseudonym was set before.
     *
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
     * Gets the users that can get selected in the upload dialog, depending on
     * the selected label.
     *
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
     *
     * @return An array containing all selectable genres
     */
    public Genre[] getSelectableGenres() {
        ArrayList<Genre> result = getGenresRecursive(db.getTopGenre());
        Genre[] resultArray = new Genre[result.size()];
        result.toArray(resultArray);
        return resultArray;
    }

    /**
     * Gets all genres that are subgenres of the submitted topGenre.
     *
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
     *
     * @return An array containing all selectable labels
     */
    public Label[] getSelectableLabels() {
        User u = db.getLoggedInUser();
        Set<Label> result = new HashSet<>();
        result.add(null);
        result.addAll(u.getManagedLabels());
        result.addAll(u.getPublishingLabels());
        Label[] resultArray = new Label[result.size()];
        result.toArray(resultArray);
        return resultArray;
    }

    /**
     * Gets all albums that can be selected in the upload dialog.
     *
     * @return An array containing all selectable albums
     */
    public Album[] getSelectableAlbums() {
        User u = db.getLoggedInUser();
        Set<Album> result = new HashSet<>();
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
