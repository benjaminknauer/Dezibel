package de.dezibel.data;

import de.dezibel.io.MediumLoader;
import java.util.Date;
import java.io.File;
import java.util.LinkedList;
import java.util.HashMap;

/**
 * Stores information about a music file, which can be uploaded, played, deleted
 * and locked by fitting users.
 *
 * @author Pascal und Bastian
 */
public class Medium implements Commentable, Lockable {

    private static MediumLoader mediumLoader;
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
    private Boolean isLocked;
    private String lockText;
    private HashMap<Integer, Rating> ratingList;
    private LinkedList<Comment> commentList;
    private LinkedList<Playlist> playlistList;

    /**
     * Class Constructor, which is used if its user chooses a filepath.
     *
     * @param title the title of the created medium
     * @param artist the artist who made the medium
     * @param path the filepath, where the medium is stored
     */
    public Medium(String title, User artist, String path) {
        this.title = title;
        this.artist = artist;
        this.path = path;
    }

    /**
     * Class Constructor, which is used if its user doesn't choose a filepath. A
     * placeholder gets created for loading its file once its ready for upload.
     *
     * @param title the title of the created medium
     * @param artist the artist who made the medium
     */
    public Medium(String title, User artist) {
        this.title = title;
        this.artist = artist;
    }

    /**
     * Checks if the medium is playable.
     *
     * @return <code>true</code> if the medium is playable, <code>false</code>
     * otherwise
     */
    public boolean isAvailable() {
        return (!this.isLocked()) || (!isDeleted);
    }

    /**
     * Uploads the file given by the filepath to our database and makes the
     * medium playable.
     *
     * @param path the filepath where the medium which should be uploaded is
     * stored
     * @return returns an errorcode which shows if the upload worked fine or if
     * a problem occured and which kind of problem that was
     */
    public ErrorCode upload(String path) {
        return null;
    }

    /**
     * Checks if a file is set for the medium.
     *
     * @return <code>true</code> if there is a File for the medium,
     * <code>false</code> otherwise
     */
    public boolean isMediumSet() {
        return ((this.path.equals("")) || (this.path != null));
    }

    /**
     * Returns the mediums file
     *
     * @return the mediums file
     */
    public File getFile() {
        return null;
        // TODO: MediumLoader
    }

    /**
     * Adds a comment to the mediums list of comments.
     *
     * @param comment comment object which stores a text, the creation date and
     * its author
     * @see Commentable#comment(Comment)
     */
    @Override
    public void comment(Comment comment) {
        this.commentList.add(comment);
    }

    /**
     * Returns the mediums list of comments.
     *
     * @return a list of its comments
     * @see Commentable#getComments()
     */
    @Override
    public LinkedList<Comment> getComments() {
        return this.commentList;
    }

    /**
     * Locks the medium and makes it unavailable (not playable).
     *
     * @see Lockable#lock()
     */
    @Override
    public void lock() {
        this.isLocked = true;
    }

    /**
     * Locks the medium, makes it unaivalable(not playable) and sends a short
     * information to the authors email adress.
     *
     * @param text contains the message which should be sent
     * @see Lockable#lock(java.lang.String)
     */
    @Override
    public void lock(String text) {
        this.isLocked = true;
        // TODO: Add sending of emails.
    }

    /**
     * Unlocks the medium so it gets availalable(playable) again and deletes its
     * locktext (if there was any).
     *
     * @see Lockable#unlock()
     */
    @Override
    public void unlock() {
        this.isLocked = false;
    }

    /**
     * Returns if the medium is locked.
     *
     * @return <code>true</code> if its locked, <code>false</code> otherwise
     * @see Lockable#isLocked()
     */
    @Override
    public boolean isLocked() {
        return this.isLocked;
    }

    /**
     * Returns the lock information.
     *
     * @return lock information text
     * @see Lockable#getLockText()
     */
    @Override
    public String getLockText() {
        return this.lockText;
    }
}
