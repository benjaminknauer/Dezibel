/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dezibel.data;

import de.dezibel.ErrorCode;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Benny
 */
public class MediumTest {

    User loggedUser;
    Medium instance;

    public MediumTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        loggedUser =  new User("max@mustermann.de", "Max", "Mustermann", "123", true);
        instance = new Medium("Mein Song", loggedUser);
        Database.getInstance().addUser("max@mustermann.de", "Max", "Mustermann",
                "123", new Date(1992, 11, 18), "Münster", "Deutschland", true);

        loggedUser.addCreatedMedium(instance);

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of isAvailable method, of class Medium.
     */
    @Test
    public void testIsAvailable() {
        System.out.println("isAvailable");
        boolean expResult = true;
        boolean result = instance.isAvailable();
        assertEquals(expResult, result);
    }

    /**
     * Test of upload method, of class Medium.
     */
    @Test
    public void testUpload() {
        System.out.println("upload");
        String path = "/Users/Benny/DJ Musik/Alternative/123.mp3";
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
        
        instance.upload("/Users/Benny/DJ Musik/Alternative/123.mp3");
        expResult = true;
        result = instance.isMediumSet();
        assertEquals(expResult, result);   
    }
    
    //TODO TEST!!!
    /**
     * Test of getFile method, of class Medium.
     */
    @Test
    @Ignore
    public void testGetFile() {
        System.out.println("getFile");
        File expResult = null;
        File result = instance.getFile();
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
        //TODO getComment(Commentable , User)
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
        
        instance.addPlaylist(list);
        
        assertTrue(list.getList().contains(instance));

    }

    /**
     * Test of removePlaylist method, of class Medium.
     */
    @Test
    public void testRemovePlaylist() {
        System.out.println("removePlaylist");
        Playlist list = new Playlist(instance, "TestList", loggedUser);
        
        instance.addPlaylist(list);
        
        instance.removePlaylist(list);
        assertFalse(instance.getPlaylistList().contains(list));
    }

    /**
     * Test of getComments method, of class Medium.
     */
    @Test
    public void testGetComments() {
        System.out.println("getComments");
        
        LinkedList expResult = null;
        LinkedList result = instance.getComments();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        
        Album album = new Album(instance, "Mein Album", loggedUser);
        instance.setAlbum(album);

        assertEquals(album, instance.getAlbum());
 
    }

    /**
     * Test of setAlbum method, of class Medium.
     */
    @Test
    public void testSetAlbum() {
        System.out.println("setAlbum");
        Album album = null;
        
        instance.setAlbum(album);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAvgRating method, of class Medium.
     */
    @Test
    public void testGetAvgRating() {
        System.out.println("getAvgRating");
        
        double expResult = 0.0;
        double result = instance.getAvgRating();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getArtist method, of class Medium.
     */
    @Test
    public void testGetArtist() {
        System.out.println("getArtist");
        
        User expResult = null;
        User result = instance.getArtist();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setArtist method, of class Medium.
     */
    @Test
    public void testSetArtist() {
        System.out.println("setArtist");
        User artist = null;
        
        instance.setArtist(artist);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGenre method, of class Medium.
     */
    @Test
    public void testGetGenre() {
        System.out.println("getGenre");
        
        Genre expResult = null;
        Genre result = instance.getGenre();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setGenre method, of class Medium.
     */
    @Test
    public void testSetGenre() {
        System.out.println("setGenre");
        Genre genre = null;
        
        instance.setGenre(genre);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLabel method, of class Medium.
     */
    @Test
    public void testGetLabel() {
        System.out.println("getLabel");
        
        Label expResult = null;
        Label result = instance.getLabel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLabel method, of class Medium.
     */
    @Test
    public void testSetLabel() {
        System.out.println("setLabel");
        Label label = null;
        
        instance.setLabel(label);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPath method, of class Medium.
     */
    @Test
    public void testGetPath() {
        System.out.println("getPath");
        
        String expResult = "";
        String result = instance.getPath();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUploadDate method, of class Medium.
     */
    @Test
    public void testGetUploadDate() {
        System.out.println("getUploadDate");
        
        Date expResult = null;
        Date result = instance.getUploadDate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRatingList method, of class Medium.
     */
    @Test
    public void testGetRatingList() {
        System.out.println("getRatingList");
        
        HashMap expResult = null;
        HashMap result = instance.getRatingList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlaylistList method, of class Medium.
     */
    @Test
    public void testGetPlaylistList() {
        System.out.println("getPlaylistList");
        
        LinkedList expResult = null;
        LinkedList result = instance.getPlaylistList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
