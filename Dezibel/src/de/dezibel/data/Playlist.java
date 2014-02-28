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
    private boolean addingMed;

    // Bool to tell the database that this instance of Playlist may be deleted.
    // Only set to true if all associations are cleared!
    private boolean markedForDeletion = false;

    /**
     * Constructor of the playlistclass
     *
     * @param medium first Medium in the playlist
     * @param titel title of the playlist
     * @param user owner of the playlist
     */
    public Playlist(Medium medium, String titel, User user) {
        this.mediumList = new LinkedList<>();
        this.comments = new LinkedList<>();

        this.titel = titel;
        medium.addPlaylist(this);
        this.user = user;
        this.user.addCreatedPlaylist(this);
    }

    /**
     * This method adds a medium to the playlist.
     *
     * @param medium medium to add
     */
    public void addMedium(Medium medium) {
        this.addingMed = true;
        this.mediumList.add(medium);

        if (medium.isAddingPL() == false) {
            medium.addPlaylist(this);
        }

        this.addingMed = false;
    }

    public boolean isAddingMed() {
        return this.addingMed;
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
                delete();
            }
        }
    }

    /**
     * This method moves a mediaobject from it's current position to a new one.
     *
     * @param currentPos The current position of the mediaobject which is to be
     * moved.
     * @param newPos The position the mediaobject is supposed to be moved to.
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

    public void delete() {
        if (markedForDeletion) {
            return;
        }
        markedForDeletion = true;
        user.removePlaylist(this);
        for (Medium currentMedium : mediumList) {
            currentMedium.removePlaylist(this);
        }
        mediumList = null;
        for (Comment currentComment : comments) {
            comments.remove(currentComment);
            deleteComment(currentComment);
        }
        comments = null;
        Database.getInstance().removePlaylist(this);
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

    public boolean isMarkedForDeletion() {
        return markedForDeletion;
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
}
