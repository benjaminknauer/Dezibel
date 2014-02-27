package de.dezibel.data;

import java.util.LinkedList;

/**
 * This class represents a playlist, which can be created by a user.
 *
 * @author Alexander Trahe, Benjamin Knauer
 * @inv A playlist contains at least 1 medium.
 */
public class Playlist implements Commentable {

    private String titel;
    private LinkedList<Comment> comments;
    private LinkedList<Medium> mediumList;
    private User user;

    /**
     * Constructor of the playlistclass
     *
     * @param medium first Medium in the playlist
     * @param titel title of the playlist
     * @param user owner of the playlist
     */
    public Playlist(Medium medium, String titel, User user) {
        this.titel = titel;
        this.mediumList = new LinkedList<>();
        this.mediumList.add(medium);
        this.user = user;
        this.user.addPlaylist(this);
    }

    /**
     * This method adds a medium to the playlist.
     *
     * @param medium medium to add
     */
    public void addMedium(Medium medium) {
        this.mediumList.add(medium);
    }

    /**
     * This method removes a medium from the playlist.
     *
     * @param index index of the medium in the list
     * @pre list is not empty
     * @post The size of the list has been reduced by 1.
     */
    public void removeMedium(int index) {
        if (index != -1) { // is a medium selected?
            mediumList.get(index).removePlaylist(this);
            mediumList.remove(index);
            if (mediumList.isEmpty()) {
                Database.getInstance().removePlaylist(this);
                user.removePlaylist(this);
            }
        }

        /**
         * This method moves a mediaobject from it's current position to a new
         * one.
         *
         * @param currentPos The current position of the mediaobject which is to
         * be moved.
         * @param newPos The position the mediaobject is supposed to be moved
         * to.
         * @pre currentPos and newPos are in range of 0 to mediumList.size()-1
         * @post the medium is at newPos in mediumList
         */
    

    public void move(int currentPos, int newPos) {
        Medium temp = mediumList.get(currentPos);
        mediumList.add(newPos, temp);
        if (currentPos <= newPos) {
            mediumList.remove(currentPos);
        } else {
            mediumList.remove(currentPos + 1);
        }
    }

    /**
     * This method returns the size of the playlist.
     *
     * @return size of playlist
     */
    public int size() {
        return mediumList.size();
    }

    public LinkedList<Medium> getList() {
        return (LinkedList<Medium>) mediumList.clone();
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public User getUser() {
        return user;
    }

    /**
     * This Method adds a comment to the playlist
     *
     * @see Commentable#comment(Comment)
     * @param comment comment to add
     */
    @Override
    public void comment(Comment comment) {
        comments.add(comment);
    }

    /**
     * @see Commentable#getComments()
     */
    @Override
    public LinkedList<Comment> getComments() {
        return (LinkedList<Comment>) comments.clone();
    }
}
