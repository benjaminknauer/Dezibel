package de.dezibel.data;

import java.util.Date;
import java.util.LinkedList;

/**
 * This class represents news.
 *
 * @author Alexander Trahe, Benjamin Knauer
 * @inv either author or label is not null
 */
public class News implements Commentable {

    private String text;
    private String title;
    private Date creationDate;
    private LinkedList<Comment> comments;
    private User author;
    private Label label;

    // Bool to tell the database that this instance of News may be deleted.
    // Only set to true if all associations are cleared!
    private boolean markedForDeletion = false;

    /**
     * The constructor for a news written by a user.
     *
     * @param title title of the news
     * @param text text of the news
     * @param author author of the news
     */
    public News(String title, String text, User author) {
        this.comments = new LinkedList<>();

        this.title = title;
        this.text = text;
        this.author = author;
        this.creationDate = new Date();

        author.addNews(this);
    }

    /**
     * The constructor for a news written by a label.
     *
     * @param title title of the news
     * @param text text of the news
     * @param author author of the news
     */
    public News(String title, String text, Label author) {
        this.comments = new LinkedList<>();

        this.title = title;
        this.text = text;
        this.label = author;
        this.creationDate = new Date();

        author.addNews(this);
    }

    /**
     * @see Commentable#comment(Comment)
     */
    @Override
    public void comment(Comment comment) {
        this.comments.add(comment);
    }

    /**
     * @see Commentable#getComments()
     */
    @Override
    public LinkedList<Comment> getComments() {
        return (LinkedList<Comment>) comments.clone();
    }

    /**
     * This method deletes the instance of News and all associations from the
     * database.
     */
    public void delete() {
        if (markedForDeletion) {
            return;
        }
        markedForDeletion = true;
        for (Comment currentComment : (LinkedList<Comment>) comments.clone()) {
            deleteComment(currentComment);
        }
        comments.clear();
        if (this.isAuthorLabel()) {
            label.deleteNews(this);
        } else {
            author.deleteNews(this);
        }
        this.label = null;
        this.author = null;
        Database.getInstance().deleteNews(this);
    }

    /**
     * @see Commentable#deleteComment(Comment)
     */
    @Override
    public void deleteComment(Comment comment) {
        this.comments.remove(comment);
        if (comment != null) {
            comment.delete();
        }
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public User getAuthor() {
        return author;
    }

    public Label getLabel() {
        return label;
    }

    public boolean isMarkedForDeletion() {
        return markedForDeletion;
    }

    /**
     * This method returns true if the author is a label and false if the author
     * is a user.
     *
     * @return <code>true</code> if author is label, <code>false</code>
     * otherwise
     */
    public boolean isAuthorLabel() {
        if (author == null) {
            return true;
        } else {
            return false;
        }
    }
}
