package de.dezibel.control;

import de.dezibel.data.Database;
import de.dezibel.data.Genre;
import de.dezibel.data.Medium;
import de.dezibel.data.User;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Controls the process of getting recommendations for the currently logged in user.
 * @author Richard
 */
public class AdsControl {
    
    /**
     * Gets recommendations for the currently logged in user.
     * @return An array containing the recommendations
     */
    public Medium[] getRecommendedMedia() {
        User u = Database.getInstance().getLoggedInUser();
        LinkedList<Medium> recommendedMedia = new LinkedList<>();
        
        // Get favorite genre
        HashMap<Genre, Integer> mediumCounts = new HashMap<>();
        Integer count;
        int maxCount = -1;
        Genre favoriteGenre = null;
        for (Medium m : u.getFavoriteMediums()) {
            count = mediumCounts.get(m.getGenre());
            if (count == null) {
                count = 1;
            } else {
                count++;
            }
            mediumCounts.put(m.getGenre(), count);
            if (count > maxCount) {
                maxCount = count;
                favoriteGenre = m.getGenre();
            }
        }
        // Use topgenre if no favorite genre was found
        favoriteGenre = Database.getInstance().getTopGenre();
        
        // Get a song out of the favorite genre that is not rated yet
        for (Medium m : favoriteGenre.getMedia()) {
            if (m.getRatingList().get(u.hashCode()) == null) {
                recommendedMedia.add(m);
            }
        }
        
        // Return random top 10
        Collections.shuffle(recommendedMedia);
        recommendedMedia = (LinkedList<Medium>) recommendedMedia.subList(0, Math.min(recommendedMedia.size(), 10));
        Medium[] result = new Medium[recommendedMedia.size()];
        recommendedMedia.toArray(result);
        return result;
    }
    
}
