package de.dezibel.data;

import de.dezibel.io.MediumLoader;
import de.dezibel.ErrorCode;
import de.dezibel.io.MailUtil;
import java.util.Date;
import java.io.File;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Stores information about a music file, which can be uploaded, played, deleted
 * and locked by fitting users.
 *
 * @author Pascal, Bastian, Tobias
 */
public class Medium implements Commentable, Lockable {

    private static final MediumLoader mediumLoader = new MediumLoader();
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
    private boolean addingPL;
    private boolean removingPL;
    private boolean settingAlbum;
    private String lockText;
    private final HashMap<Integer, Rating> ratingList;
    private final LinkedList<Comment> commentList;
    private final LinkedList<Playlist> playlistList;

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

        if (path != null) {
            this.upload(path);
        }
        else
            Database.getInstance().addNews("Ankündigung", artist.getPseudonym() 
                    + " wird bald " + title + " veröffentlichen!", artist);
        artist.addCreatedMedium(this);
    }

    /**
     * Checks if the medium is playable.
     *
     * @return <code>true</code> if the medium is playable, <code>false</code>
     * otherwise
     */
    public boolean isAvailable() {
        return !(this.isLocked()) && !(this.deleted) && this.isMediumSet();
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
        this.path = Medium.mediumLoader.upload(path);
        if (!path.isEmpty()) {
            return ErrorCode.SUCCESS;
        }
        return ErrorCode.UPLOAD_ERROR;
    }

    /**
     * Marks the medium as deleted so no user can access it in any way (except
     * admins)
     *
     * @post no user except the admin can access the medium
     */
    public void markAsDeleted() {
        this.deleted = true;
    }

    /**
     * Adds a new rating/edits the existing one with points.
     *
     * @param points value how high it is rated
     * @param rater user who rates the medium
     * @pre points is between 1 and 5
     * @post medium has a rating
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

        while (iterator.hasNext()) {
            average += (double) iterator.next().getPoints();
        }

        this.avgRating = Math.round(average / this.ratingList.size() * 100.0) / 100.0;
    }

    /**
     * Checks if a file is set for the medium.
     *
     * @return <code>true</code> if there is a File for the medium,
     * <code>false</code> otherwise
     */
    public boolean isMediumSet() {
        return this.path != null && !(this.path.equals(""));
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
     * @see Commentable#comment(Comment)
     */
    @Override
    public void comment(Comment comment) {
        this.commentList.add(comment);
    }

    /**
     * @see Commentable#comment(Comment)
     */
    @Override
    public void deleteComment(Comment comment) {
        this.commentList.remove(comment);
        if (comment != null && !comment.isMarkedForDeletion()) {
            comment.delete();
        }
    }

    /**
     * @see Lockable#lock()
     */
    @Override
    public void lock() {
        lock(null);
    }

    /**
     * @see Lockable#lock(java.lang.String)
     */
    @Override
    public void lock(String text) {
        this.locked = true;
        this.lockText = text;
        MailUtil.sendMail("Medium gesperrt",
                "Hallo " + this.getArtist().getFirstname() + ",\n\n"
                        + "dein Medium \"" + this.getTitle() + "\" wurde gesperrt."
                        + "Folgender Grund wurde angegeben:\n"
                        + "--------------------------------------------------\n"
                        + this.lockText + "\n"
                        + "--------------------------------------------------\n"
                        + "Bitte wende dich an einen Administrator, um weitere "
                        + "Informationen zu bekommen.",
                this.getArtist().getEmail());
    }

    /**
     * @see Lockable#unlock()
     */
    @Override
    public void unlock() {
        this.locked = false;
        MailUtil.sendMail("Medium entsperrt",
                "Hallo " + this.getArtist().getFirstname() + ",\n\n"
                        + "dein Medium \"" + this.getTitle() + "\" wurde entsperrt.",
                this.getArtist().getEmail());
    }

    /**
     * Adds a playlist to the list of playlists which contain the medium
     *
     * @param list new playlist which should contain medium
     */
    public void addPlaylist(Playlist list) {
        if(addingPL)
            return;
        this.addingPL = true;
        this.playlistList.add(list);
        
        list.addMedium(this);

        this.addingPL = false;
    }

    /**
     * Checks if an adding process is currently running.
     *
     * @return <code>true</code> if medium and playlist are currently in an
     * adding process, <code>false</code> otherwise
     */
    public boolean isAddingPL() {
        return this.addingPL;
    }

    /**
     * Removes list from the list of playlists which contain the medium and
     * removes all occurences of this medium in <code>list</code>.
     *
     * @param list list which should be removed
     * @pre playlistList is not empty
     * @post playlistLists size is reduced by 1 and list.contains(this) is false
     */
    public void removePlaylist(Playlist list) {
        if(!removingPL){
            removingPL = true;  
            
            this.playlistList.remove(list);
            list.removeMedium(this);
            
            removingPL = false;
        }
    }
    
    /**
     * Removes association to the album the medium was associated with.
     */
    public void removeAlbum() {
        if(album == null)
            return;
        Album a = this.album;
        this.album = null;
        a.removeMedium(this);        
    }
    
    /**
     * @see Commentable#getComments()
     */
    @Override
    public LinkedList<Comment> getComments() {
        return (LinkedList<Comment>) this.commentList.clone();
    }

    /**
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
        if(this.settingAlbum)
            return;
        this.settingAlbum = true;
        if(this.album != null)
            this.album.removeMedium(this);
        this.album = album;
        if(album != null)
            album.addMedium(this);
        this.settingAlbum = false;
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
        if (!genre.getMedia().contains(this)) {
            genre.addMedium(this);
        }
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
        return (HashMap<Integer, Rating>) this.ratingList.clone();
    }

    public LinkedList<Playlist> getPlaylistList() {
        return (LinkedList<Playlist>) this.playlistList.clone();
    }
    
}
