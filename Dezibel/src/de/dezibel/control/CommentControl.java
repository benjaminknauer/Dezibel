package de.dezibel.control;

import de.dezibel.data.Database;
import de.dezibel.data.Medium;

/**
 * Handles the commenting of media
 * @author Tobias
 */
public class CommentControl {
    
    /**
     * Comments the given medium with the given text and sets the user as creator
     * @param m Medium to comment
     * @param text The comment
     */
    public void commentMedia(Medium m, String text) {
        Database.getInstance().addComment(text, m, Database.getInstance().getLoggedInUser());
    }
}
