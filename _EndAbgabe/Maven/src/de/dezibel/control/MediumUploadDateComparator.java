package de.dezibel.control;

import de.dezibel.data.Medium;
import java.util.Comparator;

/**
 * Comparator for the upload date of a medium
 * 
 * @author Tobias
 */
public class MediumUploadDateComparator implements Comparator<Medium> {
    
    /**
     * Compares the two given Medias
     * @param media1 Medium
     * @param media2 Medium
     * @return 0, if the two medias upload dates are the same.
     *          postive if media1>media2.
     *          negative if media1<media2.
     */
    @Override
    public int compare(Medium media1, Medium media2) {
        if(media1 == null && media2 == null)
            return 0;
        else if(media1 == null || media1.getUploadDate() == null)
            return -1;
        else if(media2 == null || media2.getUploadDate() == null)
            return 1;
        return media1.getUploadDate().compareTo(media2.getUploadDate());
    }
}