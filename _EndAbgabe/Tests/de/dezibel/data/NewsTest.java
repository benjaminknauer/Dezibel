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

        testNews.delete();

        assertTrue(testNews.getComments().isEmpty());
        assertTrue(testArtist.getNews().isEmpty());


    }

    /**
     * Test of deleteComment method, of class News.
     */
    @Test
    public void testDeleteComment() {
        System.out.println("deleteComment");

        Comment testComment = new Comment("Kommentar", testNews, testArtist);
        testNews.comment(testComment);
        Comment testComment2 = new Comment("Kommentar2", testNews, testArtist);
        testNews.comment(testComment2);
        Comment testComment3 = new Comment("Kommentar3", testNews, testArtist);
        testNews.comment(testComment3);

        testNews.deleteComment(testComment2);

        assertTrue(testNews.getComments().contains(testComment));
        assertFalse(testNews.getComments().contains(testComment2));
        assertTrue(testNews.getComments().contains(testComment3));
    }

    /**
     * Test of getText method, of class News.
     */
    @Test
    public void testGetText() {
        System.out.println("getText");

        assertEquals("Test Text", testNews.getText());
    }

    /**
     * Test of getTitle method, of class News.
     */
    @Test
    public void testGetTitle() {
        System.out.println("getTitle");

        assertEquals("Neue News", testNews.getTitle());
    }

    /**
     * Test of getCreationDate method, of class News.
     */
    @Test
    public void testGetCreationDate() {
        System.out.println("getCreationDate");

        News neueNews = new News("Test", "test", testArtist);

        assertEquals(new Date(), neueNews.getCreationDate());
    }

    /**
     * Test of getAuthor method, of class News.
     */
    @Test
    public void testGetAuthor() {
        System.out.println("getAuthor");
        
        assertEquals(testArtist, testNews.getAuthor());
    }

    /**
     * Test of getLabel method, of class News.
     */
    @Test
    public void testGetLabel() {
        System.out.println("getLabel");
        
        //assertNull(testNews.getLabel());
        Label testLabel = new Label(testArtist, "test label");
        testNews = new News("test title", "test text", testLabel);
        assertEquals(testLabel, testNews.getLabel());
    }



    /**
     * Test of isAuthorLabel method, of class News.
     */
    @Test
    public void testIsAuthorLabel() {
        System.out.println("isAuthorLabel");
 
        assertFalse(testNews.isAuthorLabel());
        
        Label testLabel = new Label(testArtist, "test label");
        testNews = new News("test title", "test text", testLabel);
        assertTrue(testNews.isAuthorLabel());
    }
}
