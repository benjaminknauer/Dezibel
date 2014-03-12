package de.dezibel.control;

import de.dezibel.data.Album;
import de.dezibel.data.Database;
import de.dezibel.data.Label;
import de.dezibel.data.Medium;
import de.dezibel.data.User;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Is able to search for different kind of data and return all sorted hits
 * 
 * @author Tobias, Richard
 */
public class Search {

    public final int alphabetical = 0;
    public final int rating = 1;
    public final int uploadDate = 2;

    /**
     * Search all media for the searchTerm and returns all sorted hits
     * @param searchTerm The term to search for
     * @param sorting indicates how the result should be sorted
     * @return sorted LinkedList
     */
    public LinkedList<Medium> searchForMedia(String searchTerm, int sorting) {
        boolean admin = Database.getInstance().getLoggedInUser().isAdmin();
        searchTerm = searchTerm.toLowerCase();
        LinkedList<Medium> media = Database.getInstance().getMedia();
        Comparator c;
        switch (sorting) {
            case alphabetical:
                c = new MediumNameComparator();
                break;
            case rating:
                c = new MediumRatingComparator();
                break;
            case uploadDate:
                c = new MediumUploadDateComparator();
                break;
            default:
                c = new MediumNameComparator();
                break;
        }
        LinkedList<Medium> result = new LinkedList<>();
        Iterator<Medium> it = media.iterator();
        while (it.hasNext()) {
            Medium tmp = it.next();
            if(tmp.isLocked() && !admin)
                continue;
            if(tmp.getTitle().toLowerCase().contains(searchTerm)) {
                result.add(tmp);
                continue;
            }
            if(tmp.getArtist().getPseudonym().toLowerCase().contains(searchTerm)) {
                result.add(tmp);
                continue;
            }
            if(tmp.getAlbum() != null && tmp.getAlbum().getTitle().toLowerCase().contains(searchTerm)) {
                result.add(tmp);
                continue;
            }
            if(tmp.getGenre().getName().toLowerCase().contains(searchTerm))
                result.add(tmp);
        }
        Collections.sort(result, c);
        return result;

    }

    /**
     * Search all Users for the searchTerm and returns all sorted hits
     * @param searchTerm The term to search for
     * @param sorting indicates how the result should be sorted
     * @return sorted LinkedList
     */
    public LinkedList<User> searchForUsers(String searchTerm, int sorting) {
        boolean admin = Database.getInstance().getLoggedInUser().isAdmin();
        searchTerm = searchTerm.toLowerCase();
        LinkedList<User> users = Database.getInstance().getUsers();
        Comparator c;
        switch (sorting) {
            case alphabetical:
                c = new UserNameComparator();
                break;
            default:
                c = new UserNameComparator();
                break;
        }
        LinkedList<User> result = new LinkedList<>();
        Iterator<User> it = users.iterator();
        while (it.hasNext()) {
            User tmp = it.next();
            if(tmp.isLocked() && !admin)
                continue;
            if(tmp.getFirstname().toLowerCase().contains(searchTerm)) {
                result.add(tmp);
                continue;
            }
            if(tmp.getLastname().toLowerCase().contains(searchTerm)) {
                result.add(tmp);
                continue;
            }
            if(tmp.getPseudonym() != null && tmp.getPseudonym().toLowerCase().contains(searchTerm)) {
                result.add(tmp);
                continue;
            }
            if(tmp.getCity() != null && tmp.getCity().toLowerCase().contains(searchTerm)) {
                result.add(tmp);
                continue;
            }
            if(tmp.getCountry() != null && tmp.getCountry().toLowerCase().contains(searchTerm)) {
                result.add(tmp);
                continue;
            }
            if(tmp.getEmail().toLowerCase().contains(searchTerm)) {
                result.add(tmp);
                continue;
            }
            if(tmp.getDescription() != null && tmp.getDescription().toLowerCase().contains(searchTerm))
                result.add(tmp);
        }
        Collections.sort(result, c);
        return result;
    }

    /**
     * Search all labels for the searchTerm and returns all sorted hits
     * @param searchTerm The term to search for
     * @param sorting indicates how the result should be sorted
     * @return sorted LinkedList
     */
    public LinkedList<Label> searchForLabels(String searchTerm, int sorting) {
        boolean admin = Database.getInstance().getLoggedInUser().isAdmin();
        searchTerm = searchTerm.toLowerCase();
        LinkedList<Label> labels = Database.getInstance().getLabels();
        Comparator c;
        switch (sorting) {
            case alphabetical:
                c = new LabelNameComparator();
                break;
            default:
                c = new LabelNameComparator();
                break;
        }
        LinkedList<Label> result = new LinkedList<>();
        Iterator<Label> it = labels.iterator();
        while (it.hasNext()) {
            Label tmp = it.next();
            if(tmp.isLocked() && !admin)
                continue;
            if(tmp.getName().toLowerCase().contains(searchTerm))
                result.add(tmp);
        }
        Collections.sort(result, c);
        return result;
    }

    /**
     * Search all albums for the searchTerm and returns all sorted hits
     * @param searchTerm The term to search for
     * @param sorting indicates how the result should be sorted
     * @return sorted LinkedList
     */
    public LinkedList<Album> searchForAlbums(String searchTerm, int sorting) {
        searchTerm = searchTerm.toLowerCase();
        LinkedList<Album> albums = Database.getInstance().getAlbums();
        Comparator c;
        switch (sorting) {
            case alphabetical:
                c = new AlbumNameComparator();
                break;
            default:
                c = new AlbumNameComparator();
                break;
        }
        LinkedList<Album> result = new LinkedList<>();
        Iterator<Album> it = albums.iterator();
        while (it.hasNext()) {
            Album tmp = it.next();
            if(tmp.getTitle().toLowerCase().contains(searchTerm))
                result.add(tmp);
        }
        Collections.sort(result, c);
        return result;
    }
}
