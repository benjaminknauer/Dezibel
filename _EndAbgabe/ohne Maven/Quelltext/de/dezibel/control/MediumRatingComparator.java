package de.dezibel.control;

import de.dezibel.data.Medium;
import java.util.Comparator;

/**
 * Comparator for the rating of a medium
 * 
 * @author Tobias
 */
public class MediumRatingComparator implements Comparator<Medium> {
    
    /**
     * Compares the two given Medias
     * @param media1 Medium
     * @param media2 Medium
     * @return 0, if the two media ratings are the same.
     *          postive if media1>media2.
     *          negative if media1<media2.
     */
    @Override
    public int compare(Medium media1, Medium media2) {
        if(media1 == null && media2 == null)
            return 0;
        else if(media1 == null)
            return -1;
        else if(media2 == null)
            return 1;
        if(media1.getAvgRating()==media2.getAvgRating())
            return 0;
        else if(media1.getAvgRating()<media2.getAvgRating())
            return -1;
        else 
            return 1;
    }
}