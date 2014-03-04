package de.dezibel.control;

import de.dezibel.data.Playlist;
import java.util.Comparator;

/**
 * Comparator for the name of users with lastname as first key and
 * firstname as second
 * 
 * @author Tobias
 */
public class PlaylistNameComparator implements Comparator<Playlist> {
    
    /**
     * Compares the two given playlists
     * @param list1 Playlist
     * @param list2 Playlist
     * @return 0, if the two playlist names are the same.
     *          postive if list1>list2.
     *          negative if list1<list2.
     */
    @Override
    public int compare(Playlist list1, Playlist list2) {
        if(list1 == null && list2 == null)
            return 0;
        else if(list1 == null)
            return -1;
        else if(list2 == null)
            return 1;
        if(list1.getTitle().compareTo(list2.getTitle()) == 0)
            return list1.getTitle().compareTo(list2.getTitle());
        return list1.getTitle().compareTo(list2.getTitle());
    }
}