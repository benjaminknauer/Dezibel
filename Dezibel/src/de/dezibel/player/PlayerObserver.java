package de.dezibel.player;

import de.dezibel.data.Medium;

/**
 * Oberserver interface for the player.
 *
 * @author Richard, Tobias
 */
public interface PlayerObserver {

    /**
     * Gets called if the state changes
     * @param newMedium The new medium that plays
     */
    public void onStateChanged(Medium newMedium);
}
