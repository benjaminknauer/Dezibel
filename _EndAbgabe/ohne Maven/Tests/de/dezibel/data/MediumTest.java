/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dezibel.data;

import de.dezibel.ErrorCode;
import java.util.Date;
import java.util.LinkedList;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Benny
 */
public class MediumTest {

    User loggedUser;
    Medium instance;

    public MediumTest() {
    }

    @Before
    public void setUp() {
        loggedUser =  new User("max@mustermann.de", "Max", "Mustermann", "123", true);
        instance = new Medium("Mein Song", loggedUser, "");
        Database.getInstance().addUser("max@mustermann.de", "Max", "Mustermann",
                "123", new Date(1992, 11, 18), "Münster", "Deutschland", true);

        loggedUser.addCreatedMedium(instance);

    }

    /**
     * Test of isAvailable method, of class Medium.
     */
    @Test
    public void testIsAvailable() {
        System.out.println("isAvailable");
        instance.upload("testupload.rtf");
        assertTrue(instance.isAvailable());
        
        instance.lock();
        
        assertFalse(instance.isAvailable());
    }

    /**
     * Test of upload method, of class Medium.
     */
    @Test
    public void testUpload() {
        System.out.println("upload");
        String path = this.getClass().getResource("mp3/testmp3.mp3").toString();
        ErrorCode expResult = ErrorCode.SUCCESS;
        ErrorCode result = instance.upload(path);
        assertEquals(expResult, result);
    }

    /**
     * Test of rate method, of class Medium.
     */
    @Test
    public void testRate() {
        System.out.println("rate");
        int points = 5;
        User rater = loggedUser;
        instance.rate(points, rater);
        
        assertEquals(points, instance.getRatingList().get(rater.hashCode()).getPoints());
    }

    /**
     * Test of isMediumSet method, of class Medium.
     */
    @Test
    public void testIsMediumSet() {
        System.out.println("isMediumSet");
        boolean expResult = false;
        boolean result = instance.isMediumSet();
        assertEquals(expResult, result);
        
        instance.upload(this.getClass().getResource("mp3/testmp3.mp3").toString());
        expResult = true;
        result = instance.isMediumSet();
        assertEquals(expResult, result);   
    }

    /**
     * Test of comment method, of class Medium.
     */
    @Test
    public void testComment() {
        System.out.println("comment");
        Comment comment = new Comment("Dies ist ein Test", instance, loggedUser);
        instance.comment(comment);
        assertEquals("Dies ist ein Test", instance.getComments()
                .get(instance.getComments().indexOf(comment)).getText());
    }

    /**
     * Test of lock method, of class Medium.
     */
    @Test
    public void testLock() {
        System.out.println("lock");
        instance.lock();

        assertTrue(instance.isLocked());
    }

    /**
     * Test of lock method, of class Medium.
     */
    @Test
    public void testLock_String() {
        System.out.println("lock");
        String text = "kein gutes Lied";
        instance.lock(text);
        assertTrue(instance.isLocked());
        assertEquals(text, instance.getLockText());
    }

    /**
     * Test of unlock method, of class Medium.
     */
    @Test
    public void testUnlock() {
        System.out.println("unlock");

        instance.lock("Medium ist gesperrt");
        
        instance.unlock();
        assertFalse(instance.isLocked());
    }

    /**
     * Test of addPlaylist method, of class Medium.
     */
    @Test
    public void testAddPlaylist() {
        System.out.println("addPlaylist");
        Playlist list = new Playlist(instance, "TestList", loggedUser);
        
        assertTrue(list.getList().contains(instance));

    }

    /**
     * Test of deletePlaylist method, of class Medium.
     */
    @Test
    public void testRemovePlaylist() {
        System.out.println("removePlaylist");
        Playlist testList = new Playlist(instance, "TestList2", loggedUser);

        
        instance.removePlaylist(testList);
        assertTrue(instance.getPlaylistList().indexOf(testList) < 0);
    }

    /**
     * Test of getComments method, of class Medium.
     */
    @Test
    public void testGetComments() {
        System.out.println("getComments");
        
        Comment comment1 = new Comment("Test1", instance, loggedUser);
        Comment comment2 = new Comment("Test2", instance, loggedUser);
        
        instance.comment(comment1);
        instance.comment(comment2);
        
        assertEquals(instance.getComments().get(0), comment1);
        assertEquals(instance.getComments().get(1), comment2);
    }

    /**
     * Test of isLocked method, of class Medium.
     */
    @Test
    public void testIsLocked() {
        System.out.println("isLocked");
        
        assertFalse(instance.isLocked());
        
        instance.lock();
        assertTrue(instance.isLocked());
 
        
    }

    /**
     * Test of getLockText method, of class Medium.
     */
    @Test
    public void testGetLockText() {
        System.out.println("getLockText");
        
        String text = "locked";
        instance.lock(text);
        
        assertEquals(text, instance.getLockText());

    }

    /**
     * Test of getTitle method, of class Medium.
     */
    @Test
    public void testGetTitle() {
        System.out.println("getTitle");
        
        String expResult = "Mein Song";
        String result = instance.getTitle();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTitle method, of class Medium.
     */
    @Test
    public void testSetTitle() {
        System.out.println("setTitle");
        String title = "Mein neuer Title";
        
        instance.setTitle(title);
        assertEquals(title, instance.getTitle());
    }

    /**
     * Test of getAlbum method, of class Medium.
     */
    @Test
    public void testGetAlbum() {
        System.out.println("getAlbum");
        
        Album album = new Album(instance, "Mein Album", loggedUser, true);
        instance.setAlbum(album);

        assertEquals(album.getTitle(), instance.getAlbum().getTitle());
        assertEquals(album.getArtist(), instance.getAlbum().getArtist());
        assertEquals(album.getMediaList(), instance.getAlbum().getMediaList());
    }

    /**
     * Test of setAlbum method, of class Medium.
     */
    @Test
    public void testSetAlbum() {
        System.out.println("setAlbum");
        Album album = new Album(instance, "Mein Album", loggedUser, true);
        
        instance.setAlbum(album);

        assertEquals(album, instance.getAlbum());
        
    }

    /**
     * Test of getAvgRating method, of class Medium.
     */
    @Test
    public void testGetAvgRating() {
        System.out.println("getAvgRating");
        
        instance.rate(3, loggedUser);
        instance.rate(2, new User("test", "test", "test", "test", true));
        instance.rate(5, new User("test2", "test2", "test2", "test2", false));
        double expResult = 3.33;
        double result = instance.getAvgRating();
        assertEquals(expResult, result, 0.0);

    }

    /**
     * Test of getArtist method, of class Medium.
     */
    @Test
    public void testGetArtist() {
        System.out.println("getArtist");
        
        assertEquals(loggedUser, instance.getArtist());
    }

    /**
     * Test of setArtist method, of class Medium.
     */
    @Test
    public void testSetArtist() {
        System.out.println("setArtist");
        User testUser = new User("test", "test", "test", "test", true);
        
        instance.setArtist(testUser);

        assertEquals(testUser, instance.getArtist());
    }

    /**
     * Test of getGenre and setGenre method, of class Medium.
     */
    @Test
    public void testGetAndSetGenre() {
        System.out.println("getGenre");
        
        Database.getInstance().addGenre("Rock", null);
        
        instance.setGenre(Database.getInstance().getGenres().get(1));
        assertEquals(Database.getInstance().getGenres().get(1), instance.getGenre());
    }


    /**
     * Test of getLabel and set Label method, of class Medium.
     */
    @Test
    public void testSetAndGetLabel() {
        System.out.println("getLabel");
        
        Label testLabel = new Label(loggedUser, "myLabel");
        instance.setLabel(testLabel);

        assertEquals(testLabel, instance.getLabel());
    }

    /**
     * Test of getPath method, of class Medium.
     */
    @Test 
    public void testGetPath() {
        System.out.println("getPath");
        instance.upload("testupload.rtf");
        
        assertNotNull(null, instance.getPath());
        assertFalse(instance.getPath().isEmpty());
    }

    /**
     * Test of getUploadDate method, of class Medium.
     */
    @Test
    public void testGetUploadDate() {
        System.out.println("getUploadDate");
        
        Date expResult = new Date();
        testUpload();
        Date result = instance.getUploadDate();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPlaylistList method, of class Medium.
     */
    @Test
    public void testGetPlaylistList() {
        System.out.println("getPlaylistList");
        
        Playlist playlist1 = new Playlist(instance, "myPlaylist", loggedUser);
        Playlist playlist2 = new Playlist(instance, "myPlaylist2", loggedUser);
        
        assertTrue(instance.getPlaylistList().contains(playlist1));
        assertTrue(instance.getPlaylistList().contains(playlist2));

    }
}
