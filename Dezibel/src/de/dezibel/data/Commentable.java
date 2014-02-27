package de.dezibel.data;

import java.util.LinkedList;
/**
 * This interface must be implemented by all classes that can be commented.
 * @author Henner
 */
public interface Commentable {

    /**
     * Add the given <p>comment</p> to this object.
     * @param comment The comment to be added to this object.
     * @pre self.comment != null
     * @post true
     */
    public abstract void comment(Comment comment);

    /**
     * Get all comments posted to this object.
     * @return ArrayList containing all the posted comments.
     * @pre true
     * @post true
     */
    public abstract LinkedList<Comment> getComments();

}
