package de.dezibel.data;

import de.dezibel.ErrorCode;
import de.dezibel.io.ImageLoader;
import java.awt.Image;
// TODO: Album überarbeiten. Vererbung löschen? Stattdessen Assoziation? Wäre besser :D

/**
 * This class represents an Album. An Album is a special kind of Playlist, being
 * able to have a cover.
 *
 * @author Henner, Tobias
 * @inv An album contains at least 1 medium
 */
public class Album extends Playlist {

    /**
     * The ImageLoader all Album objects use to allow use of getCover().
     */
    private static ImageLoader imageLoader;

    private String coverPath;
    private Label label;

    /**
     * Creates a new non empty Album with the given <code>medium</code>,
     * <code>title</code>. <code>user</code> is set as the creator (uploader) of
     * the Album.
     *
     * @param medium The first Medium in the Album.
     * @param title The Album's title.
     * @param creator The creator (uploader) of the Album.
     */
    public Album(Medium medium, String title, User creator) {
        super(medium, title, creator);
        this.setTitel(title);
        this.addMedium(medium);

        if (Album.imageLoader == null) {
            Album.imageLoader = new ImageLoader();
        }

        medium.setAlbum(this);
    }

    @Override
    public boolean addListToCreatorOnCreation() {
        return false;
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

    public void setLabel(Label label) {
        this.label = label;
    }

    public Label getLabel() {
        return label;
    }
}
