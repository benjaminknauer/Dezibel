package de.dezibel.data;

import de.dezibel.io.MediumLoader;
import de.dezibel.ErrorCode;
import java.util.Date;
import java.io.File;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;

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
    private User artist;
    private Genre genre;
    private Label label;
    private boolean deleted;
    private boolean locked;
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

        this.ratingList = new HashMap<>();
        this.commentList = new LinkedList<>();
        this.playlistList = new LinkedList<>();

        if (mediumLoader == null) {
            mediumLoader = new MediumLoader();
        }
        
        this.upload(path);
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

        this.ratingList = new HashMap<>();
        this.commentList = new LinkedList<>();
        this.playlistList = new LinkedList<>();

        if (mediumLoader == null) {
            mediumLoader = new MediumLoader();
        }
    }

    /**
     * Checks if the medium is playable.
     *
     * @return <code>true</code> if the medium is playable, <code>false</code>
     * otherwise
     */
    public boolean isAvailable() {
        if(!(this.isLocked()) && !(this.deleted) && this.isMediumSet()){
            return true;
        }
        else return false;
    }

    /**
     * Uploads the file given by the filepath to our database and makes the
     * medium playable.
     *
     * @param path the filepath where the medium which should be uploaded is
     * stored
     * @return returns an errorcode which shows if the upload worked fine or if
     * a problem occured and which kind of problem that was
     * @pre path leads to a valid file
     * @post medium is now playabale and upload-date is set to the current date
     */
    public synchronized ErrorCode upload(String path) {
        this.uploadDate = new Date();
        this.path = this.mediumLoader.upload(path);
        if(!path.isEmpty()) {
            return ErrorCode.SUCCESS;
        }
        return ErrorCode.UPLOAD_ERROR;
    }

    /**
     * Adds a new rating/edits the existing one with points.
     *
     * @param points value how high it is rated
     * @param rater user who rates the medium
     */
    public void rate(int points, User rater) {
        if (this.ratingList.containsKey(rater.hashCode())) {
            ratingList.get(rater.hashCode()).setPoints(points);
        } else {
            ratingList.put(rater.hashCode(), new Rating(points));
        }
        
        // Re-calculate average rating.
        double average = 0;
        Iterator<Rating> iterator = this.ratingList.values().iterator();
        
        while(iterator.hasNext()){
            average += (double) iterator.next().getPoints();
        }
        
        this.avgRating = average/this.ratingList.size();
    }

    /**
     * Checks if a file is set for the medium.
     *
     * @return <code>true</code> if there is a File for the medium,
     * <code>false</code> otherwise
     */
    public boolean isMediumSet() {
        if(this.path != null && !(this.path.equals(""))){
            return true;
        }
        else return false;
    }

    /**
     * Returns the mediums file
     *
     * @return the mediums file
     */
    public File getFile() {
        if (this.path != null) {
            return Medium.mediumLoader.getFile(this.path);
        }
        return null;
    }

    /**
     *
     * @see Commentable#comment(Comment)
     */
    @Override
    public void comment(Comment comment) {
        this.commentList.add(comment);
    }

    /**
     *
     * @see Lockable#lock()
     */
    @Override
    public void lock() {
        this.locked = true;
    }

    /**
     * @see Lockable#lock(java.lang.String)
     */
    @Override
    public void lock(String text) {
        this.locked = true;
        this.lockText = text;
        // TODO: Add sending of emails.
    }

    /**
     *
     * @see Lockable#unlock()
     */
    @Override
    public void unlock() {
        this.locked = false;
    }

    /**
     * 
     * @param list 
     */
    public void addPlaylist(Playlist list){
        this.playlistList.add(list);
    }
    
    /**
     * 
     * @param list 
     */
    public void removePlaylist(Playlist list){
        this.playlistList.remove(list);
    }
    
    /**
     * @see Commentable#getComments()
     */
    @Override
    public LinkedList<Comment> getComments() {
        return (LinkedList<Comment>) this.commentList.clone();
    }

    /**
     * Returns if the medium is locked.
     *
     * @return <code>true</code> if its locked, <code>false</code> otherwise
     * @see Lockable#isLocked()
     */
    @Override
    public boolean isLocked() {
        return this.locked;
    }

    /**
     * @see Lockable#getLockText()
     */
    @Override
    public String getLockText() {
        return this.lockText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public User getArtist() {
        return artist;
    }

    public void setArtist(User artist) {
        this.artist = artist;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public String getPath() {
        return path;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public HashMap<Integer, Rating> getRatingList() {
        return (HashMap<Integer, Rating>)this.ratingList.clone();
    }

    public LinkedList<Playlist> getPlaylistList() {
        return (LinkedList<Playlist>)this.playlistList.clone();
    }

}
