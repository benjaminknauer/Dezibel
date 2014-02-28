/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dezibel.data;

import java.util.Date;
import java.util.LinkedList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Benny
 */
public class NewsTest {
    News testNews;
    User testArtist;
    
    public NewsTest() {
    }
    
    @Before
    public void setUp() {
        testArtist = new User("testmail", "Hans", "Müller", "123", true);
        testNews = new News("Neue News", "Test Text", testArtist);
    }

    /**
     * Test of comment method, of class News.
     */
    @Test
    public void testComment() {
        System.out.println("comment");
        Comment testComment = new Comment("Kommentar", testNews, testArtist);
        testNews.comment(testComment);
        Comment testComment2 = new Comment("Kommentar2", testNews, testArtist);
        testNews.comment(testComment2);
        
        assertTrue(testNews.getComments().contains(testComment));
        assertTrue(testNews.getComments().contains(testComment2));
    }


    /**
     * Test of delete method, of class News.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        
        Comment testComment = new Comment("Kommentar", testNews, testArtist);
        testNews.comment(testComment);
        Comment testComment2 = new Comment("Kommentar2", testNews, testArtist);
        testNews.comment(testComment2);
        Comment testComment3 = new Comment("Kommentar3", testNews, testArtist);
        testNews.comment(testComment3);
        
        testNews.deleteComment(testComment2);
        
       // assertTrue(testNews.getComments().contains(testComment));
       // assertFalse(testNews.getComments().contains(testComment2));
       // assertTrue(testNews.getComments().contains(testComment3));
    }

    /**
     * Test of deleteComment method, of class News.
     */
    @Test
    public void testDeleteComment() {
        System.out.println("deleteComment");
        Comment comment = null;
        News instance = null;
        instance.deleteComment(comment);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getText method, of class News.
     */
    @Test
    public void testGetText() {
        System.out.println("getText");
        News instance = null;
        String expResult = "";
        String result = instance.getText();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTitle method, of class News.
     */
    @Test
    public void testGetTitle() {
        System.out.println("getTitle");
        News instance = null;
        String expResult = "";
        String result = instance.getTitle();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreationDate method, of class News.
     */
    @Test
    public void testGetCreationDate() {
        System.out.println("getCreationDate");
        News instance = null;
        Date expResult = null;
        Date result = instance.getCreationDate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAuthor method, of class News.
     */
    @Test
    public void testGetAuthor() {
        System.out.println("getAuthor");
        News instance = null;
        User expResult = null;
        User result = instance.getAuthor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLabel method, of class News.
     */
    @Test
    public void testGetLabel() {
        System.out.println("getLabel");
        News instance = null;
        Label expResult = null;
        Label result = instance.getLabel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isMarkedForDeletion method, of class News.
     */
    @Test
    public void testIsMarkedForDeletion() {
        System.out.println("isMarkedForDeletion");
        News instance = null;
        boolean expResult = false;
        boolean result = instance.isMarkedForDeletion();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isAuthorLabel method, of class News.
     */
    @Test
    public void testIsAuthorLabel() {
        System.out.println("isAuthorLabel");
        News instance = null;
        boolean expResult = false;
        boolean result = instance.isAuthorLabel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
