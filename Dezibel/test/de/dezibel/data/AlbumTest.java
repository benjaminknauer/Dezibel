/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dezibel.data;
import de.dezibel.ErrorCode;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Tristan
 */
public class AlbumTest {
    private String coverPathTest;
    private Album albumTest;
    private User loggedUser;
    
    private Medium medium1;
    private Medium medium2;
    private Label publisher;
    
    @Before
    public void setUp() {
        loggedUser =  new User("pet_mart@gmail.com", "Peter", "Martinez", "777", true);        
        medium1 = new Medium("Flippy Beats", loggedUser, coverPathTest);
        medium1 = new Medium("Funk Grooves", loggedUser, coverPathTest);
        albumTest = new Album(medium1, "First", publisher);
        Database.getInstance().addUser("pet_mart@gmail.com", "Peter", "Martinez",
                "777", new Date(1991, 8, 3), "Münster", "Deutschland", true);

    }
    
    /**
     * Test of uploadCover method, of class control/Album.
     */
    @Test
    public void testUploadCover() {
        System.out.println("uploadCover");
        String path = "/Users/Benny/DJ Musik/Alternative/123.mp3";
        ErrorCode expResult = ErrorCode.SUCCESS;
        ErrorCode result = albumTest.uploadCover(path);
    }    
            
    /**
     * Test of getCover method, of class control/Album.
     */
    @Test
    public void testGetCover() {
        System.out.println("getCover");
    }
    
    /**
     * Test of hasCover method, of class control/Album.
     */
    @Test
    public void testHasCover() {
        System.out.println("hasCover");
    }
}
