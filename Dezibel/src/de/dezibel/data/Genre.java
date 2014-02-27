package de.dezibel.data;

import java.util.LinkedList;

/**
 * This class represents a genre which can be used to classify the <code>Medium</code> objects.
 * A genre can have a maximum of one super genre but any amount of sub-genres.
 * @author Henner
 * @inv
 */
public class Genre {

	private String name;
        
        private Genre superGenre;
        
        private LinkedList<Genre> subGenres;
        
        private LinkedList<Medium> media;

	public Genre(Genre superGenre) {

	}
        
        
        
        /**
         * Adds <code>subGenre</code> to the sub-genres of this genre.
         * If <code>subGenre</code> already was a sub-genre, nothing will happen.
         * @param subGenre
         * @pre subGenre must is not null.
         * @post self.hasSubGenre(subGenre)
         */
	public void addSubGenre(Genre subGenre) {
            if(!hasSubGenre(subGenre))
                addSubGenre(subGenre);
	}
        
        /**
         * Checks whether <code>subGenre</code> is a sub-genre of this genre.
         * @param subGenre The potential sub-genre.
         * @return True if <code>subGenre</code> is a sub-genre of this genre, otherwise false.
         * @pre subGenre is not null
         */
        public boolean hasSubGenre(Genre subGenre) {
            boolean subGenreFound = false;
            for(Genre currentGenre : subGenres){
                if(currentGenre.equals(subGenre)){
                    subGenreFound = true;
                    break;
                }
            }
            return subGenreFound;
        }
        
        /**
         * Removes <code>subGenre</code> from the sub-genres of this genre.
         * If <code>subGenre</code> wasn't a sub-genre in the first place, nothing
         * will happen.
         * @param subGenre The genre to be removed from this genre's sub-genres.
         * @pre subGenre is not null
         * @post !self.hasSubGenre(subGenre)
         */
	public void removeSubGenre(Genre subGenre) {
            subGenres.remove(subGenre);
	}
        
        /**
         * Removes all genres in <code>subGenres</code> from the sub-genres of this genre.
         * If a genre wasn't a sub-genre in the first place, nothing
         * will happen.
         * @param subGenres The list of genres to be removed from this genre's sub-genres.
         * @pre subGenres is not null
         * @post for all genres g in <code>subGenres</code> : !hasSubGenre(g)
         */        
        public void removeSubGenres(LinkedList<Genre> subGenres){
            this.subGenres.removeAll(subGenres);
        }
        
        public LinkedList<Medium> getMedia(){
            return media;
        }
        
        public LinkedList<Genre> getSubGenres(){
            return subGenres;
        }

}
