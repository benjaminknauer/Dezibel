package de.dezibel.control;

import de.dezibel.data.Album;
import de.dezibel.data.Database;
import de.dezibel.data.Medium;
import de.dezibel.data.News;
import de.dezibel.data.Playlist;

/**
 * Handles the commenting of media, album, playlists and news
 *
 * @author Tobias
 */
public class CommentControl {

    /**
     * Comments the given medium with the given text and sets the user as
     * creator
     *
     * @param m Medium to comment
     * @param text The comment
     */
    public void commentMedia(Medium m, String text) {
        Database.getInstance().addComment(text, m, Database.getInstance().getLoggedInUser());
    }

    /**
     * Comments the given album with the given text and sets the user as
     * creator
     *
     * @param a Album to comment
     * @param text The comment
     */
    public void commentAlbum(Album a, String text) {
        Database.getInstance().addComment(text, a, Database.getInstance().getLoggedInUser());
    }

    /**
     * Comments the given playlist with the given text and sets the user as
     * creator
     *
     * @param p Playlist to comment
     * @param text The comment
     */
    public void commentPlaylist(Playlist p, String text) {
        Database.getInstance().addComment(text, p, Database.getInstance().getLoggedInUser());
    }

    /**
     * Comments the given news with the given text and sets the user as
     * creator
     *
     * @param n News to comment
     * @param text The comment
     */
    public void commentNews(News n, String text) {
        Database.getInstance().addComment(text, n, Database.getInstance().getLoggedInUser());
    }
}
