package de.dezibel.player;

import de.dezibel.data.Medium;

/**
 * Oberserver interface for the player.
 *
 * @author Richard, Tobias
 */
public interface PlayerObserver {

    /**
     * Gets called if the track changes
     * @param newMedium The new medium that plays
     */
    public void onTrackChanged(Medium newMedium);
}
