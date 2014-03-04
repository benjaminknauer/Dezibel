package de.dezibel.data;

import java.util.LinkedList;

/**
 * This class represents a playlist, which can be created by a creator.
 *
 * @author Alexander Trahe, Benjamin Knauer, Tobias
 * @inv A playlist contains at least 1 medium.
 */
public class Playlist implements Commentable {

    private String title;
    private LinkedList<Comment> comments;
    private LinkedList<Medium> mediumList;
    private User creator;
    private boolean addingMed;

    // Bool to tell the database that this instance of Playlist may be deleted.
    // Only set to true if all associations are cleared!
    private boolean markedForDeletion = false;

    /**
     * Constructor of the playlistclass
     *
     * @param medium first Medium in the playlist
     * @param title title of the playlist
     * @param user owner of the playlist
     */
    public Playlist(Medium medium, String title, User user) {
        this.mediumList = new LinkedList<>();
        this.comments = new LinkedList<>();

        this.title = title;
        medium.addPlaylist(this);
        this.creator = user;
        this.creator.addCreatedPlaylist(this);
    }

    /**
     * This method adds a medium to the playlist.
     *
     * @param medium medium to add
     */
    public void addMedium(Medium medium) {
        this.addingMed = true;
        this.mediumList.add(medium);

        medium.addPlaylist(this);

        this.addingMed = false;
    }

    public boolean isAddingMed() {
        return this.addingMed;
    }

    /**
     * This method removes a medium from the playlist.
     *
     * @param index index of the medium in the list
     * @pre list is not empty, 0 <= index < self.size() @post
     * The size of the list has been reduced by 1.
     *
     */
    public void removeMediumAt(int index) {
        Medium m = this.mediumList.get(index);
        this.mediumList.remove(index);
        if (this.mediumList.indexOf(m) < 0) {
            m.removePlaylist(this);
        }
        if (mediumList.isEmpty()) {
            this.delete();
        }
    }
    
    public void removeMedium(Medium medium) {
        while(this.mediumList.contains(medium)){
            this.mediumList.remove(medium);
        }
        medium.removePlaylist(this);
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
        Medium temp = this.mediumList.get(currentPos);
        this.mediumList.add(newPos, temp);
        if (currentPos <= newPos) {
            this.mediumList.remove(currentPos);
        } else {
            this.mediumList.remove(currentPos + 1);
        }
    }

    public void delete() {
        if (this.markedForDeletion) {
            return;
        }
        this.markedForDeletion = true;
        this.creator.removePlaylist(this);
        for (Medium currentMedium : mediumList) {
            currentMedium.removePlaylist(this);
        }
        this.mediumList.clear();
        for (Comment currentComment : comments) {
            this.comments.remove(currentComment);
            this.deleteComment(currentComment);
        }
        this.comments.clear();
        Database.getInstance().deletePlaylist(this);
    }

    /**
     * This method returns the size of the playlist.
     *
     * @return size of playlist
     */
    public int size() {
        return this.mediumList.size();
    }

    public LinkedList<Medium> getList() {
        return (LinkedList<Medium>) this.mediumList.clone();
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getCreator() {
        return this.creator;
    }

    public boolean isMarkedForDeletion() {
        return this.markedForDeletion;
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
}
