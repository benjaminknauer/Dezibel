package de.dezibel.data;

import java.util.Date;
import java.util.LinkedList;

/**
 * This class represents a 
 * @author Benny
 */
public class News implements Commentable {

    private String text;
    private String titel;
    private Date creationDate;
    private LinkedList<Comment> comments;
    private User author;
    private Label label;

    public News(String titel, String text, User author) {
        this.titel = titel;
        this.text = text;
        this.author = author;
        this.creationDate = new Date();
    }

    public News(String titel, String text, Label author) {
        this(titel, text, (User) null);
        this.label = author;
    }

    /**
     * @see Commentable#comment(Comment)
     */
    public void comment(Comment comment) {
        this.comments.add(comment);
    }

    /**
     * @see Commentable#getComments()
     */
    public LinkedList<Comment> getComments() {
        return (LinkedList<Comment>) comments.clone();
    }

    public String getText() {
        return text;
    }

    public String getTitel() {
        return titel;
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

    public boolean isAuthorLabel() {
        if (author == null) {
            return true;
        } else {
            return false;
        }
    }
}
