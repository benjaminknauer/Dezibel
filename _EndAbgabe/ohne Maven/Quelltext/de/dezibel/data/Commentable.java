package de.dezibel.data;

import java.util.LinkedList;

/**
 * This interface must be implemented by all classes that can be commented.
 *
 * @author Henner
 */
public interface Commentable {

    /**
     * Add the given <code>comment</code> to this object.
     *
     * @param comment The comment to be added to this object.
     * @pre self.comment != null
     * @post true
     */
    public abstract void comment(Comment comment);

    /**
     * Get all comments posted to this object.
     *
     * @return ArrayList containing all the posted comments.
     * @pre true
     * @post true
     */
    public abstract LinkedList<Comment> getComments();

    /**
     * Remove <code>comment</comment> from this object's comments and clear all
     * its associations. Does nothing if the specified comment wasn't a comment
     * of this object's comments anyway.
     *
     * @param comment The comment to be deleted.
     * @pre true
     * @post <code>comment</code> is not part of the object's comments.
     */
    public void deleteComment(Comment comment);

}
