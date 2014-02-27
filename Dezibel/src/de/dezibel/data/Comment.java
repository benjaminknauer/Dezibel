package de.dezibel.data;

import java.util.Date;
import java.util.ArrayList;

/**
 * This class represents the comments Users can post to Media, Albums etc.
 *
 * @author Henner
 * @inv self.text != null && !self.text.equals("") && self.creationDate != null
 * && self.commentable != null && self.authoer != null
 */
public class Comment {

    private String text;

    private Date creationDate;

    private Commentable commentable;

    private User author;

    /**
     * Creates a new comment consisting of
     * <p>text</p> written by <p>author</p> and posted to <p>commentable</p>.
     * @param text The text of the comment. Must not be null or the empty String
     * after being trimmed.
     * @param commentable The commentable object this comment is being posted
     * to.
     * @param author The user who wrote the comment.
     */
    public Comment(String text, Commentable commentable, User author) {
        this.text = text;
        this.commentable = commentable;

        this.creationDate = new Date();
    }

    public String getText() {
        return text;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Commentable getCommentable() {
        return commentable;
    }

    public User getAuthor() {
        return author;
    }

}
