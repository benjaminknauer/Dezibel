package de.dezibel.data;

import de.dezibel.io.MediumLoader;
import java.util.Date;
import java.util.ArrayList;
import java.io.File;
import java.util.LinkedList;
import java.util.HashMap;

public class Medium implements Commentable, Lockable {

        private static MediumLoader mediumLoader = null;
	private String path;
	private String title;
	private Album album;
	private Date uploadDate;
	private double avgRating;
	private Comment comment;
        private User artist;
        private Genre genre;
        private Label label;
        private Boolean isDeleted;
        private HashMap<Integer, Rating> ratingList;
        private LinkedList<Comment> commentList;
        private LinkedList<Playlist> playlistList;

        /**
         * 
         * Constructor, which is used if its user choose a filepath.
         * @param title
         * @param artist
         * @param path 
         */
	public Medium(String title, User artist, String path) {
            this.title = title;
            this.artist = artist;
            this.path = path;      
	}

        /**
         * Constructor, which is used if its user doesn't choose a filepath.
         * A placeholder gets created for loading its file if it is used.
         * @param title
         * @param artist 
         */
	public Medium(String title, User artist) {
            this.title = title;
            this.artist = artist;
	}

        /**
         * Checks if the medium is playable.
         * @return <code>true</code> if the medium is playable, 
         * <code>false</code> otherwise.
         */
	public boolean isAvailable() {
		return (!this.isLocked()) || (!isDeleted);
	}

        /**
         * 
         * @param path
         * @return 
         */
	public ErrorCode upload(String path) {
		return null;
	}

        /**
         * 
         * @return 
         */
	public boolean isMediumSet() {
		return false;
	}

        /**
         * 
         * @return 
         */
	public File getFile() {
		return null;
	}

	/**
         * 
         * @param comment
	 * @see Commentable#comment(Comment)
	 */
        @Override
	public void comment(Comment comment) {

	}

	/**
         * 
         * @return
	 * @see Commentable#getComments()
	 */
        @Override
	public LinkedList<Comment> getComments() {
		return null;
	}

	/**
         * 
	 * @see Lockable#lock()
	 */
        @Override
	public void lock() {

	}

	/**
         * 
         * @param text
	 * @see Lockable#lock(java.lang.String)
	 */
        @Override
	public void lock(String text) {

	}

	/**
         * 
	 * @see Lockable#unlock()
	 */
        @Override
	public void unlock() {

	}

	/**
         * 
         * @return
	 * @see Lockable#isLocked()
	 */
        @Override
	public boolean isLocked() {
		return false;
	}

	/**
         * 
         * @return
	 * @see Lockable#getLockText()
	 */
        @Override
	public String getLockText() {
		return null;
	}

}
