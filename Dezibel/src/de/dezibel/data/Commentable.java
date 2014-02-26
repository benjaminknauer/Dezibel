package de.dezibel.data;

import java.util.LinkedList;

public interface Commentable {

	public abstract void comment(Comment comment);

	public abstract LinkedList<Comment> getComments();

}
