package de.dezibel.data;

import de.dezibel.ErrorCode;
import de.dezibel.io.ImageLoader;
import java.awt.Image;
import java.util.LinkedList;
// TODO: Album überarbeiten. Vererbung löschen? Stattdessen Assoziation? Wäre besser :D

/**
 * This class represents an Album.
 *
 * @author Henner, Tobias
 * @inv An album contains at least 1 medium
 */
public class Album implements Commentable {

    /**
     * The ImageLoader all Album objects use to allow use of getCover().
     */
    private static ImageLoader imageLoader;

    private String coverPath;
    private String title;
    private Label label;
    private User artist;
    private LinkedList<Medium> mediaList;
    private LinkedList<Comment> comments;
    private boolean isAuthorLabel;
    private boolean addingMed;
    private boolean removingMed;

    // Bool to tell the database that this instance of Playlist may be deleted.
    // Only set to true if all associations are cleared!
    private boolean markedForDeletion = false;

    /**
     * Creates a new non empty Album with the given <code>medium</code>,
     * <code>title</code>. <code>label</code> is set as the publisher of the
     * Album.
     *
     * @param medium The first Medium in the Album.
     * @param title The Album's title.
     * @param publisher The label the Album is published under.
     */
    public Album(Medium medium, String title, Label publisher) {
        this.mediaList = new LinkedList<>();
        this.mediaList.add(medium);
        this.comments = new LinkedList<>();
        this.title = title;
        this.label = publisher;
        isAuthorLabel = true;
        publisher.addAlbum(this);
    }

    /**
     * Creates a new non empty Album with the given <code>medium</code>,
     * <code>title</code>. <code>user</code> is set as the creator of the Album.
     *
     * @param medium The first Medium in the Album.
     * @param title The Album's title.
     * @param creator The Artist who created the Album.
     */
    public Album(Medium medium, String title, User creator) {
        this.mediaList = new LinkedList<>();
        this.mediaList.add(medium);
        this.comments = new LinkedList<>();
        this.title = title;
        this.artist = creator;
        isAuthorLabel = false;
        creator.addAlbum(this);
    }

    public void addMedium(Medium medium) {
        if(this.addingMed)
            return;
        
        this.addingMed = true;

        if (this.mediaList.contains(medium)) {
            return;
        }

        this.mediaList.add(medium);
        medium.addAlbum(this);

        this.addingMed = false;
    }

    public void removeMedium(Medium medium) {
        if (this.removingMed) {
            return;
        }
        removingMed = true;
        this.mediaList.remove(medium);
        medium.removeAlbum(this);
        removingMed = false;
    }

    /**
     * Removes the label associated with this Album if and only if
     * <code>label</code> equals this.getLabel(). If this label was the creator
     * of the album, the album will be deleted.
     *
     * @param label The label to be removed from this album. Must be equal to this.getLabel() in order to work.
     */
    public void removeLabel(Label label) {
        if (this.label.equals(label) && label != null) {
            this.label = null;
            label.removeAlbum(this);
            if (isAuthorLabel) {
                delete();
            }
        }

    }

    public void delete() {
        if (this.markedForDeletion) {
            return;
        }
        this.markedForDeletion = true;
        
        if(this.label != null)
            this.label.removeAlbum(this);
        if(this.artist != null)
            this.artist.removeAlbum(this);
        this.label = null;
        this.artist = null;
        
        for (Medium currentMedium : (LinkedList<Medium>) mediaList.clone()) {
            currentMedium.removeAlbum(this);
        }
        this.mediaList.clear();
        
        for (Comment currentComment : (LinkedList<Comment>) comments.clone()) {
            this.comments.remove(currentComment);
            this.deleteComment(currentComment);
        }
        this.comments.clear();
        Database.getInstance().deleteAlbum(this);
    }

    /**
     * Upload the image file specified by the path into the system and set it as
     * this Album's cover.
     *
     * @param path The path to the desired image.
     * @return ErrorCode depending on the outcome of the method.
     * @post The attribute coverPath is now set to <code>path</code>.
     */
    public ErrorCode uploadCover(String path) {
        String uploadPath = imageLoader.upload(path);
        if (uploadPath.isEmpty()) {
            return ErrorCode.UPLOAD_ERROR;
        }
        return ErrorCode.SUCCESS;
    }

    /**
     * This Method adds a comment to the playlist
     *
     * @see Commentable#comment(Comment)
     * @param comment comment to add
     */
    @Override
    public void comment(Comment comment) {
        this.comments.add(comment);
    }

    /**
     * @see Commentable#getComments()
     */
    @Override
    public LinkedList<Comment> getComments() {
        return (LinkedList<Comment>) this.comments.clone();
    }

    /**
     *
     * @see Commentable#deleteComment(Comment)
     */
    @Override
    public void deleteComment(Comment comment) {
        this.comments.remove(comment);
        if (comment != null) {
            comment.delete();
        }
    }

    /**
     * Returns this Album's cover as an Image object if there is one. Use
     * hasCover() to make sure that a cover is specified.
     *
     * @return The Image object representing this album's cover.
     * @see Album#hasCover()
     * @pre self.hasCover
     */
    public Image getCover() {
        return Album.imageLoader.getImage(coverPath);
    }

    /**
     * Returns true if this Album has a cover specified, otherwise false.
     *
     * @return True if this Album's cover is set, false otherwise.
     */
    public boolean hasCover() {
        return coverPath != null;
    }

    public Label getLabel() {
        return this.label;
    }

    public User getArtist() {
        return this.artist;
    }

    public LinkedList<Medium> getMediaList() {
        return (LinkedList<Medium>) this.mediaList.clone();
    }
}
