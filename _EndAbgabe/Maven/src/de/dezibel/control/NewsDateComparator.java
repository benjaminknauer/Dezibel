package de.dezibel.control;

import de.dezibel.data.News;
import java.util.Comparator;

/**
 * Compares news by their creation date.
 *
 * @author Pascal
 */
public class NewsDateComparator implements Comparator<News> {

    /**
     * Compares two news depending on their creation date.
     */
    @Override
    public int compare(News o1, News o2) {
        return (o1.getCreationDate().compareTo(o2.getCreationDate()));
    }

}
