package de.dezibel.control;

import de.dezibel.data.Database;
import de.dezibel.data.Label;
import de.dezibel.data.Medium;
import de.dezibel.data.Playlist;
import de.dezibel.data.User;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

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
     * @return sorted TreeSet
     */
    public TreeSet<Medium> searchForMedia(String searchTerm, int sorting) {
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
        TreeSet<Medium> result = new TreeSet<>(c);
        Iterator<Medium> it = media.iterator();
        while (it.hasNext()) {
            Medium tmp = it.next();
            if(tmp.isLocked())
                continue;
            if(tmp.getTitle().toLowerCase().contains(searchTerm)) {
                result.add(tmp);
                continue;
            }
            if(tmp.getArtist().getPseudonym().toLowerCase().contains(searchTerm)) {
                result.add(tmp);
                continue;
            }
//            if(tmp.getAlbum().getTitle().toLowerCase().contains(searchTerm)) {
//                result.add(tmp);
//                continue;
//            }
            if(tmp.getGenre().getName().toLowerCase().contains(searchTerm))
                result.add(tmp);
        }
        return result;

    }

    /**
     * Search all Users for the searchTerm and returns all sorted hits
     * @param searchTerm The term to search for
     * @param sorting indicates how the result should be sorted
     * @return sorted TreeSet
     */
    public TreeSet<User> searchForUsers(String searchTerm, int sorting) {
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
        TreeSet<User> result = new TreeSet<>(c);
        Iterator<User> it = users.iterator();
        while (it.hasNext()) {
            User tmp = it.next();
            if(tmp.isLocked())
                continue;
            if(tmp.getFirstname().toLowerCase().contains(searchTerm)) {
                result.add(tmp);
                continue;
            }
            if(tmp.getLastname().toLowerCase().contains(searchTerm)) {
                result.add(tmp);
                continue;
            }
            if(tmp.getPseudonym().toLowerCase().contains(searchTerm)) {
                result.add(tmp);
                continue;
            }
            if(tmp.getCity().toLowerCase().contains(searchTerm)) {
                result.add(tmp);
                continue;
            }
            if(tmp.getCountry().toLowerCase().contains(searchTerm)) {
                result.add(tmp);
                continue;
            }
            if(tmp.getEmail().toLowerCase().contains(searchTerm)) {
                result.add(tmp);
                continue;
            }
            if(tmp.getDescription().toLowerCase().contains(searchTerm))
                result.add(tmp);
        }
        return result;
    }

    /**
     * Search all labels for the searchTerm and returns all sorted hits
     * @param searchTerm The term to search for
     * @param sorting indicates how the result should be sorted
     * @return sorted TreeSet
     */
    public TreeSet<Label> searchForLabels(String searchTerm, int sorting) {
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
        TreeSet<Label> result = new TreeSet<>(c);
        Iterator<Label> it = labels.iterator();
        while (it.hasNext()) {
            Label tmp = it.next();
            if(tmp.isLocked())
                continue;
            if(tmp.getName().toLowerCase().contains(searchTerm))
                result.add(tmp);
        }
        return result;
    }

    /**
     * Search all playlists for the searchTerm and returns all sorted hits
     * @param searchTerm The term to search for
     * @param sorting indicates how the result should be sorted
     * @return sorted TreeSet
     */
    public TreeSet<Playlist> searchForPlaylists(String searchTerm, int sorting) {
        searchTerm = searchTerm.toLowerCase();
        LinkedList<Playlist> playlists = Database.getInstance().getPlaylists();
        Comparator c;
        switch (sorting) {
            case alphabetical:
                c = new LabelNameComparator();
                break;
            default:
                c = new LabelNameComparator();
                break;
        }
        TreeSet<Playlist> result = new TreeSet<>(c);
        Iterator<Playlist> it = playlists.iterator();
        while (it.hasNext()) {
            Playlist tmp = it.next();
            if(tmp.getTitle().toLowerCase().contains(searchTerm))
                result.add(tmp);
        }
        return result;
    }
}
