/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dezibel.data;

import java.util.LinkedList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Richard
 */
public class PlaylistTest {
    private User user;
    private Medium medium1;
    private Medium medium2;
    private Medium medium3;
    private Playlist playlist;
    
    @Before
    public void setUp() {
        user = new User("mail@mail.com", "Hans", "Peter", "123", true);
        medium1 = new Medium("Medium1", user, "");
        medium2 = new Medium("Medium1", user, "");
        medium3 = new Medium("Medium1", user, "");
        playlist = new Playlist(medium1, "Playlist1", user);
    }

    /**
     * Test of addMedium method, of class Playlist.
     */
    @Test
    public void testAddMedium() {
        System.out.println("addMedium");
        Medium medium = medium2;
        Playlist instance = playlist;
        instance.addMedium(medium);
        assertTrue(instance.getList().contains(medium));
        assertTrue(medium.getPlaylistList().contains(instance));
    }

    /**
     * Test of removeCreatedMediumAt method, of class Playlist.
     */
    @Test
    public void testRemoveMedium() {
        System.out.println("removeMedium");
        playlist.addMedium(medium2);
        int index = 1;
        Playlist instance = playlist;
        instance.removeMediumAt(index);
        assertFalse(instance.getList().contains(medium2));
        assertFalse(medium2.getPlaylistList().contains(instance));
    }

    /**
     * Test of move method, of class Playlist.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        playlist.addMedium(medium2);
        playlist.addMedium(medium3);
        int currentPos = 0;
        int newPos = 2;
        Playlist instance = playlist;
        instance.move(currentPos, newPos);
        assertEquals(medium2, playlist.getList().get(0));
        assertEquals(medium1, playlist.getList().get(1));
        assertEquals(medium3, playlist.getList().get(2));
        
        currentPos = 1;
        newPos = 0;
        instance.move(currentPos, newPos);
        assertEquals(medium1, playlist.getList().get(0));
        assertEquals(medium2, playlist.getList().get(1));
        assertEquals(medium3, playlist.getList().get(2));
    }

    /**
     * Test of delete method, of class Playlist.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        Database.getInstance().addPlaylist(medium1, "Playlist1", user);
        Playlist instance = Database.getInstance().getPlaylists()
                .get(Database.getInstance().getPlaylists().size() - 1);
        Database.getInstance().deletePlaylist(instance);
        assertFalse(Database.getInstance().getPlaylists().contains(instance));
    }

    /**
     * Test of size method, of class Playlist.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        Playlist instance = playlist;
        playlist.addMedium(medium2);
        playlist.addMedium(medium3);
        int expResult = 3;
        int result = instance.size();
        assertEquals(expResult, result);
    }

    /**
     * Test of getList method, of class Playlist.
     */
    @Test
    public void testGetList() {
        System.out.println("getList");
        Playlist instance = playlist;
        instance.addMedium(medium2);
        LinkedList<Medium> expResult = new LinkedList<>();
        expResult.add(medium1);
        expResult.add(medium2);
        LinkedList<Medium> result = instance.getList();
        assertEquals(expResult, result);
    }
}
