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
public class Album {

    /**
     * The ImageLoader all Album objects use to allow use of getCover().
     */
    private static ImageLoader imageLoader;

    private String coverPath;
    private String title;
    private Label label;
    private User artist;
    private LinkedList<Medium> mediaList;

    /**
     * Creates a new non empty Album with the given <code>medium</code>,
     * <code>title</code>. <code>label</code> is set as the publisher of
     * the Album.
     *
     * @param medium The first Medium in the Album.
     * @param title The Album's title.
     * @param publisher The label the Album is published under.
     */
    public Album(Medium medium, String title, Label publisher) {
        this.mediaList = new LinkedList<>();
        this.mediaList.add(medium);
        this.title = title;
        this.label = publisher;
    }

    /**
     * Creates a new non empty Album with the given <code>medium</code>,
     * <code>title</code>. <code>user</code> is set as the creator of
     * the Album.
     *
     * @param medium The first Medium in the Album.
     * @param title The Album's title.
     * @param creator The Artist who created the Album.
     */
    public Album(Medium medium, String title, User creator) {
        this.mediaList = new LinkedList<>();
        this.mediaList.add(medium);
        this.title = title;
        this.artist = creator;
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

    public Label getLabel(){
        return this.label;
    }
    
    public User getArtist(){
        return this.artist;
    }
}
