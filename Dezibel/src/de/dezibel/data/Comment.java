package de.dezibel.data;

import java.util.Date;

/**
 * This class represents the comments users can post to Media, Albums etc.
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

    // Boolean to tell the database that this instance of Comment may be deleted.
    // Only set to true if all associations are cleared!
    private boolean markedForDeletion = false;

    /**
     * Creates a new comment consisting of <code>text</code> written by
     * <code>author</code> and posted to <code>commentable</code>.
     *
     * @param text The text of the comment. Must not be null or the empty String
     * after being trimmed.
     * @param commentable The commentable object this comment is being posted
     * to.
     * @param author The user who wrote the comment.
     */
    public Comment(String text, Commentable commentable, User author) {
        this.text = text;
        this.commentable = commentable;
        this.author = author;
        this.creationDate = new Date();
        author.addCreatedComments(this);
    }

    /**
     * This method completely deletes the comment from the database and clears
     * all its associations.
     */
    public void delete() {
        if(markedForDeletion)
            return;
        markedForDeletion = true;
        this.author.deleteComment(this);
        this.commentable.deleteComment(this);
        Database.getInstance().deleteComment(this);
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

    /**
     * Returns true if this comment is marked for deletion, false otherwise.
     * @return true if this comment is marked for deletion else false
     */
    public boolean isMarkedForDeletion() {
        return markedForDeletion;
    }

}
