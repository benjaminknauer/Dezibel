
package de.dezibel.control;

/**
 * Observer interface for playlist observer.
 * @author Benny
 */
public interface PlaylistObserver {
    
    /**
     * Gets called if the playlist changes.
     */
    public void onStateChanged();
    
}
