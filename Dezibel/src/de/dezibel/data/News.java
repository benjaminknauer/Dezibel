package de.dezibel.data;

import java.util.Date;
import java.util.ArrayList;

public class News implements Commentable {

	private String text;

	private String titel;

	private Date creationDate;

	private Comment comment;

	public News(String titel, String text, User author) {

	}

	public News(String titel, String text, Label author) {

	}


	/**
	 * @see Commentable#comment(Comment)
	 */
	public void comment(Comment comment) {

	}


	/**
	 * @see Commentable#getComments()
	 */
	public ArrayList<Comment> getComments() {
		return null;
	}

}
