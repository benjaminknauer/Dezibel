package de.dezibel.player;

import java.util.LinkedList;
import de.dezibel.data.Medium;
import de.dezibel.data.Playlist;
import java.io.File;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * Implements all functionality needed to play music. It acts as a fassade for
 * the JavaFX library.
 *
 * @author Richard, Tobias
 */
// TODO Auf null prüfen
public class Player {

    private static Player instance = null;

    private LinkedList<Medium> currentPlaylist;
    private int currentPosition;
    private MediaPlayer player;
    private final LinkedList<PlayerObserver> observer;

    /**
     * Constructor that creates the initial Player instance.
     */
    private Player() {
        // Initialize JavaFX
        JFXPanel fxPanel = new JFXPanel();
        this.currentPosition = 0;
        this.currentPlaylist = new LinkedList<>();
        this.observer = new LinkedList<>();
    }

    /**
     * Returns an instance of Player.
     *
     * @return The player instance
     */
    public synchronized static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    /**
     * Starts playing the current media. If there is nothing to play or the
     * player is currently playing, nothing happens
     */
    public void play() {
        if (this.player == null) {
            createPlayer(this.currentPlaylist.get(this.currentPosition));
        }
        this.player.play();
        notifyObserver(this.currentPlaylist.get(this.currentPosition));
    }

    /**
     * Pauses the current media. If there is nothing to pause or the player is
     * already paused, nothing happens
     */
    public void pause() {
        if (this.player != null) {
            this.player.pause();
            notifyObserver(this.currentPlaylist.get(this.currentPosition));
        }
    }

    /**
     * Stops the current media. If there is nothing to stop or the player has
     * already stopped, nothing happens
     */
    public void stop() {
        if (this.player != null) {
            this.player.stop();
            this.currentPosition = 0;
            this.createPlayer(this.currentPlaylist.get(this.currentPosition));
            notifyObserver(this.currentPlaylist.get(this.currentPosition));
        }
    }

    /**
     * Jumps to the submitted position in the playlist.
     *
     * @param pos The position to jumo to
     */
    public void jumpTo(int pos) {
        if (this.player != null) {
            this.player.seek(new Duration(pos * 1000));
        }
    }

    /**
     * Jumps to the next medium in the playlist. If the playlist is empty,
     * nothing happens.
     */
    public void next() {
        this.currentPosition = (++this.currentPosition) % this.currentPlaylist.size();
        Medium newMedium = this.currentPlaylist.get(this.currentPosition);
        if (newMedium.isAvailable()) {
            this.createPlayer(newMedium);
        } else {
            // TODO - Sperrung bzw. Löschung für 3 Sekunden anzeigen
            // Eventuell durch eigene Sperr- bzw. Lösch-Medien realisieren.
        }
        this.play();
    }

    /**
     * Jumps to the previous medium in the playlist. If the playlist is empty,
     * nothing happens.
     */
    public void previous() {
        this.currentPosition = --this.currentPosition;
        if (this.currentPosition < 0) {
            this.currentPosition = this.currentPlaylist.size() - 1;
        }
        Medium newMedium = this.currentPlaylist.get(this.currentPosition);
        if (newMedium.isAvailable()) {
            this.createPlayer(newMedium);
        } else {
            // TODO - Sperrung bzw. Löschung für 3 Sekunden anzeigen
            // Eventuell durch eigene Sperr- bzw. Lösch-Medien realisieren.
        }
        this.play();
    }

    /**
     * Returns the volume.
     *
     * @return The volume
     */
    public int getVolume() {
        if (this.player == null) {
            return 0;
        } else {
            return (int) Math.round(this.player.getVolume() * 100);
        }
    }

    /**
     * Sets the current volume.
     *
     * @param volume The new volume. It's effect will be clamped to the range
     * [0, 100]
     */
    public void setVolume(int volume) {
        if (this.player != null) {
            if (volume > 100) {
                volume = 100;
            } else if (volume < 0) {
                volume = 0;
            }
            this.player.setVolume(volume / 100.0);
        }
    }

    /**
     * Returns the total duration of the current song in seconds.
     *
     * @return The duration in seconds
     */
    public int getTotalDuration() {
        if (this.player == null) {
            return 0;
        } else {
            return (int) Math.round(this.player.getStopTime().toSeconds());
        }
    }

    /**
     * Returns the already played time of the media in seconds.
     *
     * @return The played time in seconds
     */
    public int getCurrentTime() {
        if (this.player == null) {
            return 0;
        } else {
            return (int) Math.round(this.player.getCurrentTime().toSeconds());
        }
    }

    /**
     * Returns if the player is currently playing.
     *
     * @return true if the player is currently playing, else false
     */
    public boolean isPlaying() {
        return (this.player != null && this.player.getStatus() == MediaPlayer.Status.PLAYING);
    }

    /**
     * Returns the medium that is currently playing.
     *
     * @return The current Medium
     */
    public Medium getCurrentMedium() {
        if (this.currentPlaylist == null) {
            return null;
        } else {
            return this.currentPlaylist.get(this.currentPosition);
        }
    }

    /**
     * Sets the currentMedia
     *
     * @param song The new currentMedia
     */
    public void setCurrentMedia(Medium song) {
        if (song != null) {
            this.currentPosition = this.currentPlaylist.indexOf(song);
            this.createPlayer(song);
        }
    }

    /**
     * Adds a medium to the currentPlaylist
     *
     * @param song The Medium to add
     */
    public void addMedium(Medium song) {
        if (song != null) {
            this.currentPlaylist.add(song);
        }
    }

    /**
     * Removes the given medium from the currentPlalist.
     *
     * @param index The index of the song to remove.
     */
    public void removeMedium(int index) {
        if (index >= 0 && index < this.currentPlaylist.size()) {
            this.currentPlaylist.remove(index);
        }
    }

    /**
     * Adds the playlist to the currentPlaylist, d.h. appends all songs of the
     * playlist
     *
     * @param playlist The playlist to add to the currentPlaylist
     */
    public void addPlaylist(Playlist playlist) {
        if (playlist != null) {
            this.currentPlaylist.addAll(playlist.getList());
        }
    }

    /**
     * Replaces the currentPlaylist with the given playlist
     *
     * @param playlist The new playlist
     */
    public void setPlaylist(Playlist playlist) {
        if (playlist != null) {
            this.currentPlaylist = playlist.getList();
        }
    }

    /**
     * Clears the currentPlaylist.
     *
     * @post currentPlaylist = empty
     */
    public void clearPlaylist() {
        this.stop();
        this.currentPlaylist.clear();
    }

    /**
     * Adds the submitted observer to the list of observers
     *
     * @param o The observer to add
     */
    public void addObserver(PlayerObserver o) {
        if (o != null) {
            this.observer.add(o);
        }
    }

    /**
     * Removes the submitted observer from the list of observers
     *
     * @param o The observer to remove
     */
    public void removeObserver(PlayerObserver o) {
        this.observer.remove(o);
    }

    /**
     * Creates a MediaPlayer object for the given medium.
     *
     * @param medium The medium to create a player for
     * @return The created player
     * @pre medium != null
     */
    private void createPlayer(Medium medium) {
        File f = medium.getFile();
        Media m = new Media(f.toURI().toString());
        MediaPlayer tmpPlayer = new MediaPlayer(m);
        if (this.player != null) {
            this.player.setOnEndOfMedia(null);
            if (this.isPlaying()) {
                tmpPlayer.play();
            }
            tmpPlayer.setVolume(this.player.getVolume());
            this.player.dispose();
        }

        tmpPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                Player.this.next();
            }
        });
        this.player = tmpPlayer;

        notifyObserver(medium);
    }

    /**
     * Notifies all observers.
     *
     * @param medium The currently playing medium
     */
    private void notifyObserver(Medium medium) {
        // Notify observer
        for (PlayerObserver o : observer) {
            o.onStateChanged(medium);
        }
    }
    
    
    public LinkedList<Medium> getPlaylist() {
        return this.currentPlaylist;
    }
    
    public int getCurrentIndex() {
        return currentPosition;
    }

}
