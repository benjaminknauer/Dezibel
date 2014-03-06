package de.dezibel.control;

import de.dezibel.ErrorCode;
import de.dezibel.data.Album;
import de.dezibel.data.Database;
import de.dezibel.data.Genre;
import de.dezibel.data.Label;
import de.dezibel.data.User;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
        if (!user.isArtist()) {
            
        }
        return db.addMedium(title, user, path, genre, label, album);
    }

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

    public Genre[] getSelectableGenres() {
        ArrayList<Genre> result = getGenresRecursive(db.getTopGenre());
        Genre[] resultArray = new Genre[result.size()];
        result.toArray(resultArray);
        return resultArray;
    }

    private ArrayList<Genre> getGenresRecursive(Genre topGenre) {
        ArrayList<Genre> result = new ArrayList<>();
        result.add(topGenre);
        for (Genre g : topGenre.getSubGenres()) {
            result.addAll(getGenresRecursive(g));
        }
        return result;
    }

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
