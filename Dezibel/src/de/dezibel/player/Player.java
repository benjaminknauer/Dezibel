package de.dezibel.player;

import java.util.LinkedList;
import de.dezibel.data.Medium;
import de.dezibel.data.Playlist;

public class Player {

    private LinkedList<Medium> currentPlaylist;

    private static Player instance = null;

    private Player() {

    }

    public Player getInstance() {
        if(instance == null)
            instance = new Player();
        return instance;
    }

    public void play() {

    }

    public void pause() {

    }

    public void stop() {

    }

    public void jumpTo(int pos) {

    }

    public void next() {

    }

    public void previous() {

    }

    public int getVolume() {
        return 0;
    }

    public void setVolume(int volume) {

    }

    public boolean isPlaying() {
        return false;
    }

    public Medium getCurrentMedium() {
        return null;
    }

    public void addMedium(Medium song) {

    }

    public void removeMedium(Medium song) {

    }

    public void addPlaylist(Playlist playlist) {

    }

    public void setPlaylist(Playlist playlist) {

    }

    public void clearPlaylist() {

    }

}
