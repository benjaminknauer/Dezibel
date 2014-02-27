package de.dezibel.data;

import java.util.Date;
import java.util.LinkedList;

/**
 * This class represents news.
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

    /**
     * The constructor for a news written by a user.
     * @param title title of the news
     * @param text text of the news
     * @param author author of the news
     */
    public News(String title, String text, User author) {
        this.title = title;
        this.text = text;
        this.author = author;
        this.creationDate = new Date();
    }

    /**
     * The constructor for a news written by a label.
     * @param title title of the news
     * @param text text of the news
     * @param author author of the news
     */
    public News(String title, String text, Label author) {
        this(title, text, (User) null);
        this.label = author;
    }

    /**
     * This Method adds a comment to the news.
     * @param comment commet to add
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

    /**
     * This method returns true if the author is a Label and false if the author is a user.
     * @return true if author is label, else false
     */
    public boolean isAuthorLabel() {
        if (author == null) {
            return true;
        } else {
            return false;
        }
    }
}
