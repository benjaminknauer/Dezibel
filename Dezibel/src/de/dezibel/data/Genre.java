package de.dezibel.data;

import java.util.LinkedList;

/**
 * This class represents a genre which can be used to classify the
 * <code>Medium</code> objects. A genre can have a maximum of one super genre
 * but any amount of sub-genres.
 *
 * @author Henner
 * @inv
 */
public class Genre {

    private String name;

    private Genre superGenre;

    private LinkedList<Genre> subGenres;

    private LinkedList<Medium> media;
    
    private boolean markedForDeletion = false;

    public Genre(String name, Genre superGenre) {
        this.name = name;
        this.superGenre = superGenre;
        subGenres = new LinkedList<>();
        media = new LinkedList<>();
        if (superGenre != null) {
            superGenre.addSubGenre(this);
        }
    }

    /**
     * Completely removes this genre from the database and all associated
     * genres. Will do nothing if this genre is the default top-genre.
     *
     * @pre This genre is not the default top-genre.
     * @post This genre is deleted from the database, its super-genre's
     * sub-genres and its sub-genres' super-genre.
     */
    public void delete() {
        // Already deleting? And: can't delete the top-genre
        if (markedForDeletion || this.equals(Database.getInstance().getTopGenre()))
            return;
        
        markedForDeletion = true;

        // Handle all sub-genres
        for (Genre currentGenre : subGenres) {
            currentGenre.setSuperGenre(null);
        }

        // Handle the super-genre
        this.getSuperGenre().removeSubGenre(this);

        // Remove me from the database.
        Database.getInstance().deleteGenre(this);
    }

    /**
     * Adds <code>medium</code> to the media of this genre. If
     * <code>medium</code> already was associated with this genre, nothing will
     * happen.
     *
     * @param medium The medium you wish to add to this genre.
     * @pre medium must is not null.
     * @post self.hasMedium(medium)
     */
    public void addMedium(Medium medium) {
        if (!hasMedium(medium)) {
            this.media.add(medium);
        }
    }

    /**
     * Checks whether <code>medium</code> is associated with this genre.
     *
     * @param medium The potentially associated medium.
     * @return True if <code>medium</code> is associated with this genre,
     * otherwise false.
     * @pre medium is not null
     */
    public boolean hasMedium(Medium medium) {
        boolean mediumFound = false;
        for (Medium currentMedium : this.media) {
            if (currentMedium.equals(medium)) {
                mediumFound = true;
                break;
            }
        }
        return mediumFound;
    }

    /**
     * Removes <code>medium</code> from the media of this genre. If
     * <code>medium</code> wasn't associated with this genre in the first place,
     * nothing will happen.
     *
     * @param medium The medium to be removed from this genre's media.
     * @pre medium is not null
     * @post !self.hasMedium(medium)
     */
    public void removeMedium(Medium medium) {
        this.media.remove(medium);
    }

    /**
     * Adds <code>subGenre</code> to the sub-genres of this genre. If
     * <code>subGenre</code> already was a sub-genre, nothing will happen.
     *
     * @param subGenre The genre you wish to add to this genre's sub-genres.
     * @pre subGenre must is not null.
     * @post self.hasSubGenre(subGenre)
     */
    public void addSubGenre(Genre subGenre) {
        if (!hasSubGenre(subGenre) && subGenre.getSuperGenre().equals(this)) {
            subGenres.add(subGenre);
        }
    }

    /**
     * Checks whether <code>subGenre</code> is a sub-genre of this genre.
     *
     * @param subGenre The potential sub-genre.
     * @return True if <code>subGenre</code> is a sub-genre of this genre,
     * otherwise false.
     * @pre subGenre is not null
     */
    public boolean hasSubGenre(Genre subGenre) {
        boolean subGenreFound = false;
        for (Genre currentGenre : this.subGenres) {
            if (currentGenre.equals(subGenre)) {
                subGenreFound = true;
                break;
            }
        }
        return subGenreFound;
    }

    /**
     * Removes <code>subGenre</code> from the sub-genres of this genre. If
     * <code>subGenre</code> wasn't a sub-genre in the first place, nothing will
     * happen.
     *
     * @param subGenre The genre to be removed from this genre's sub-genres.
     * @pre subGenre is not null
     * @post !self.hasSubGenre(subGenre)
     */
    public void removeSubGenre(Genre subGenre) {
        this.subGenres.remove(subGenre);
        subGenre.setSuperGenre(null);
    }

    /**
     * Removes all genres in <code>subGenres</code> from the sub-genres of this
     * genre. If a genre wasn't a sub-genre in the first place, nothing will
     * happen.
     *
     * @param subGenres The list of genres to be removed from this genre's
     * sub-genres.
     * @pre subGenres is not null
     * @post for all genres g in <code>subGenres</code> : !hasSubGenre(g)
     */
    public void removeSubGenres(LinkedList<Genre> subGenres) {
        this.subGenres.removeAll(subGenres);
    }

    public LinkedList<Medium> getMedia() {
        return this.media;
    }

    public LinkedList<Genre> getSubGenres() {
        return this.subGenres;
    }

    /**
     * Sets this genre's super-genre to <code>superGenre</code>. Will do nothing
     * if this genre equals the default top-genre. If <code>superGenre</code>
     * equals null the default top-genre from the database is chosen instead.
     *
     * @param superGenre This genre's new super genre.
     * @pre This genre must not be the default top-genre.
     * @post This genre's super-genre is now <code>superGenre</code> or the
     * default top-genre.
     */
    public void setSuperGenre(Genre superGenre) {
        // Set the default topGenre?
        if (superGenre == null) {
            superGenre = Database.getInstance().getTopGenre();
        }

        // The topGenre can't have a superGenre.
        if (this.equals(Database.getInstance().getTopGenre())) {
            return;
        }
        this.superGenre.removeSubGenre(this);
        this.superGenre = superGenre;
        superGenre.addSubGenre(this);
    }

    public Genre getSuperGenre() {
        return this.superGenre;
    }

    public String getName() {
        return this.name;
    }
    
    public boolean isMarkedForDeletion(){
        return markedForDeletion;
    }

}
