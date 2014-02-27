package de.dezibel.data;

import java.util.LinkedList;

// TODO: Assoziationen

public class Playlist implements Commentable {

    private String titel;
    private LinkedList<Comment> comments;
    private LinkedList<Medium> mediumList;
    private User user;
            
    public Playlist(Medium medium, String titel, User user) {
        this.titel = titel;
        this.mediumList = new LinkedList<>();
        this.mediumList.add(medium);
        this.user = user;
        this.user.addPlaylist(this);
    }

    public void addMedium(Medium medium) {
        this.mediumList.add(medium);
    }

    public void removeMedium(int index) {
        mediumList.remove(index);
    }

    /**
     * This method moves a mediaobject from it's current position to a new one.
     * @param currentPos The current position of the mediaobject which is to be moved.
     * @param newPos The position the mediaobject is supposed to be moved to.
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

    /**
     * @see Commentable#comment(Comment)
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
