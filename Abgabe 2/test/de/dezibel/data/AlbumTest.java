/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dezibel.data;
import de.dezibel.ErrorCode;
import de.dezibel.io.ImageLoader;
import java.util.Date;
import java.util.LinkedList;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Tristan
 */
public class AlbumTest{
    private String coverPathTest;
    private Album albumTest;
    private User loggedUser;
    private ImageLoader imageloader;    
    private Medium medium1;
    private Medium medium2;
    private Label publisher;
    private LinkedList<Medium> mediaList;
    private Comment testComment1,testComment2;
    
    @Before
    public void setUp() {
        loggedUser =  new User("pet_mart@gmail.com", "Peter", "Martinez", "777", true);        
        medium1 = new Medium("Flippy Beats", loggedUser, coverPathTest);
        medium2 = new Medium("Funk Grooves", loggedUser, coverPathTest);
        publisher = new Label(loggedUser, "Regular Music Group");
        albumTest = new Album(medium1, "First", publisher);       
        imageloader = new ImageLoader();
        mediaList = new LinkedList<Medium>();
        testComment1 = new Comment("Hey Hey", albumTest, loggedUser);
        testComment2 = new Comment("Lalala", albumTest, loggedUser);
        Database.getInstance().addUser("pet_mart@gmail.com", "Peter", "Martinez","777", new Date(1991, 8, 3), "Münster", "Deutschland", true);
    }
    
    @After
    public void tearDown(){
        loggedUser = null;
        medium1 = null;
        medium2 = null;
        publisher = null;
        imageloader = null;
        mediaList = null;
    }
    
    /**
     * Test of uploadCover method, of class control/Album.
     */
    @Ignore
    @Test
    public void testUploadCover() {
        System.out.println("uploadCover");
        String path = "C:/Users/Tristan/Pictures/asdf.jpg";
        ErrorCode expResult = ErrorCode.SUCCESS;
        ErrorCode result = albumTest.uploadCover(path);
        albumTest.getCover().equals(imageloader.getImage(path));
    }    
            
    /**
     * Test of getCover method, of class control/Album.
     */
    @Test
    public void testGetCover() {
        System.out.println("getCover");
        //TODO test getcover
    }
    
    /**
     * Test of hasCover method, of class control/Album.
     */
    @Ignore
    @Test
    public void testHasCover() {
        System.out.println("hasCover");
        assertTrue(albumTest.hasCover());
    }
    
    /**
     * Test of addMedium method, of class control/Album.
     */
    @Test
    public void testAddMedium() {
        System.out.println("AddMedium");
        albumTest.addMedium(medium1);
        assertTrue(albumTest.getMediaList().getLast().equals(medium1));
        
        assertTrue(medium1.getAlbum().equals(albumTest));
    }
    
    /**
     * Test of removeCreatedMedium method, of class control/Album.
     */
    @Test
    public void testRemoveMedium() {
        System.out.println("RemoveMedium"); 
        albumTest.removeMedium(medium1);        
        assertTrue(albumTest.getMediaList().isEmpty());        
        assertTrue(albumTest.isMarkedForDeletion());
        assertTrue(medium1.getAlbum() == null);
    }
    
    /**
     * Test of comment method, of class News.
     */
    @Test
    public void testComment() {
        System.out.println("comment");
        albumTest.comment(testComment1);
        albumTest.comment(testComment2);

        assertTrue(albumTest.getComments().contains(testComment1));
        assertTrue(albumTest.getComments().contains(testComment2));
    }

    /**
     * Test of delete method, of class News.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        albumTest.comment(testComment1);
        albumTest.delete();

        assertTrue(albumTest.getMediaList().isEmpty());
        assertNull(medium1.getAlbum());
        assertNull(albumTest.getArtist());
        assertNull(albumTest.getLabel());
        assertFalse(loggedUser.getCreatedAlbums().contains(albumTest));
        assertFalse(albumTest.getComments().contains(testComment1));
        


    }

    /**
     * Test of deleteComment method, of class News.
     */
    @Test
    public void testDeleteComment() {
        System.out.println("deleteComment");

        albumTest.comment(testComment1);
        albumTest.comment(testComment2);

        albumTest.deleteComment(testComment2);

        assertTrue(albumTest.getComments().contains(testComment1));
        assertFalse(albumTest.getComments().contains(testComment2));
    }
    
    /**
     * Test of removeLabel method, of class News.
     */
    @Test
    public void testRemoveLabel() {
        System.out.println("removeLabel");
        
        // Label is creator -> album gets deleted
        albumTest.removeLabel();
        assertTrue(publisher.getAlbums().isEmpty());
        assertNull(albumTest.getLabel());
        assertTrue(albumTest.isMarkedForDeletion());
        
        // Label is not creator -> album does not get deleted
        Album albumTest2 = new Album(medium1, "Test1", loggedUser);
        assertNull(albumTest2.getLabel());
        
        albumTest2.setLabel(publisher);
        albumTest2.removeLabel();
        assertNull(albumTest2.getLabel());
        assertFalse(albumTest2.isMarkedForDeletion());
    }        
}
