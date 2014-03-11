/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dezibel.data;

import java.util.Date;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Benny
 */
public class ApplicationTest {
    User testArtist;
    Label testLabel;
    Application testApplication;
    
    
    public ApplicationTest() {
    }
    
    @Before
    public void setUp() {
        testArtist = new User("mail", "Horst", "Müller", "123", true);
        testLabel = new Label(testArtist, "test label");
        
        //Artist creates Application
        testApplication = new Application(true, "Ich bin Horst und bin Künstler", 
                testArtist, testLabel);
    }

    /**
     * Test of accept method, of class Application.
     */
    @Test
    public void testAccept() {
        System.out.println("accept");
        
        testApplication.accept();
        
        assertTrue(testArtist.getPublishingLabels().contains(testLabel));
        assertTrue(testLabel.getArtists().contains(testArtist));
        assertFalse(testArtist.getApplications().contains(testApplication));
        assertFalse(testLabel.getApplications().contains(testApplication));
    }

    /**
     * Test of decline method, of class Application.
     */
    @Test
    public void testDecline() {
        System.out.println("decline");

        testApplication.accept();
        
        assertFalse(testArtist.getApplications().contains(testApplication));
        assertFalse(testLabel.getApplications().contains(testApplication));
        
    }

    /**
     * Test of delete method, of class Application.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        
        testApplication.delete();
        
        assertFalse(testArtist.getApplications().contains(testApplication));
        assertFalse(testLabel.getApplications().contains(testApplication));
    }

    /**
     * Test of isFromArtist method, of class Application.
     */
    @Test
    public void testIsFromArtist() {
        System.out.println("isFromArtist");
 
        assertTrue(testApplication.isFromArtist());
        
        // Label created Application
        testApplication = new Application(false, "Das Label möchte "
                + "sie unter Vertrag nehmen!", testArtist, testLabel);
        
        assertFalse(testApplication.isFromArtist());
    }


    /**
     * Test of getCreationDate method, of class Application.
     */
    @Test
    public void testGetCreationDate() {
        System.out.println("getCreationDate");
        
        testApplication = new Application(true, "neue Bewerbung",
                testArtist, testLabel);
        
        assertEquals(testApplication.getCreationDate(), new Date());
    }

    /**
     * Test of getLabel method, of class Application.
     */
    @Test
    public void testGetLabel() {
        System.out.println("getLabel");
        
        assertEquals(testLabel, testApplication.getLabel());
    }

    /**
     * Test of getUser method, of class Application.
     */
    @Test
    public void testGetUser() {
        System.out.println("getUser");

        assertEquals(testArtist, testApplication.getUser());
    }

    /**
     * Test of isMarkedForDeletion method, of class Application.
     */
    @Test
    public void testIsMarkedForDeletion() {
        System.out.println("isMarkedForDeletion");

        assertFalse(testApplication.isMarkedForDeletion());
        
        testApplication.delete();
        
        assertTrue(testApplication.isMarkedForDeletion());
    }
}
