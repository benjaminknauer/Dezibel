package de.dezibel.control;

import de.dezibel.data.Album;
import java.util.Comparator;

/**
 * Comparator for the name of albums with title as key.
 * 
 * @author Tobias, Richard
 */
public class AlbumNameComparator implements Comparator<Album> {
    
    /**
     * Compares the two given albums
     * @param album1 Album
     * @param album2 Album
     * @return 0, if the two albums names are the same.
     *          postive if album1>album2.
     *          negative if album1<album2.
     */
    @Override
    public int compare(Album album1, Album album2) {
        if(album1 == null && album2 == null)
            return 0;
        else if(album1 == null)
            return -1;
        else if(album2 == null)
            return 1;
        if(album1.getTitle().compareTo(album2.getTitle()) == 0)
            return album1.getTitle().compareTo(album2.getTitle());
        return album1.getTitle().compareTo(album2.getTitle());
    }
}