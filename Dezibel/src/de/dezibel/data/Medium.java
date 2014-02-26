package de.dezibel.data;

import de.dezibel.io.MediumLoader;
import java.util.Date;
import java.util.ArrayList;
import java.io.File;

public class Medium implements Commentable, Lockable {

	private String path;

	private String titel;

	private Album album;

	private Date uploadDate;

	private double avgRating;

	private static MediumLoader mediumLoader = null;

	private Comment comment;

	public Medium(String titel, User artist, String path) {

	}

	private Medium(String titel, User artist) {

	}

	public boolean isAvailable() {
		return false;
	}

	public void rate(int points) {

	}

	public ErrorCode upload(String path) {
		return null;
	}

	public boolean isMediumSet() {
		return false;
	}

	public File getFile() {
		return null;
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


	/**
	 * @see Lockable#lock()
	 */
	public void lock() {

	}


	/**
	 * @see Lockable#lock(java.lang.String)
	 */
	public void lock(String text) {

	}


	/**
	 * @see Lockable#unlock()
	 */
	public void unlock() {

	}


	/**
	 * @see Lockable#isLocked()
	 */
	public boolean isLocked() {
		return false;
	}


	/**
	 * @see Lockable#getLockText()
	 */
	public String getLockText() {
		return null;
	}

}
