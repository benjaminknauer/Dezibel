/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dezibel.data;

import java.util.Date;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Henner
 */
public class CommentTest {
    
    Comment testComment;
    News testCommentable;
    User testAuthor;
    
    public CommentTest() {
        
    }
    
    @Before
    public void setUp() {
        testAuthor = new User("testmail", "Hans", "Müller", "123", true);
        testCommentable = new News("Neue News", "Test Text", testAuthor);
        testComment = new Comment("Test Kommentar", testCommentable, testAuthor);
    }

    /**
     * Test of delete method, of class Comment.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        Comment instance = new Comment("Test Komment 2", testCommentable, testAuthor);
        instance.delete();
        
        assertFalse(testCommentable.getComments().contains(instance));
        assertFalse(testAuthor.getCreatedComments().contains(instance));
        assertTrue(instance.isMarkedForDeletion());
        assertFalse(Database.getInstance().getComments().contains(instance));
    }

    /**
     * Test of getText method, of class Comment.
     */
    @Test
    public void testGetText() {
        System.out.println("getText");
        Comment instance = testComment;
        assertTrue(testComment.getText().equals("Test Kommentar"));
    }

    /**
     * Test of getCreationDate method, of class Comment.
     */
    @Test
    public void testGetCreationDate() {
        System.out.println("getCreationDate");
        Comment instance = new Comment("Test Komment 2", testCommentable, testAuthor);
        Date expResult = new Date();
        Date result = instance.getCreationDate();
        assertEquals(expResult.getTime(), result.getTime());
    }

    /**
     * Test of getCommentable method, of class Comment.
     */
    @Test
    public void testGetCommentable() {
        System.out.println("getCommentable");
        Comment instance = testComment;
        Commentable expResult = testCommentable;
        Commentable result = instance.getCommentable();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAuthor method, of class Comment.
     */
    @Test
    public void testGetAuthor() {
        System.out.println("getAuthor");
        Comment instance = testComment;
        User expResult = testAuthor;
        User result = instance.getAuthor();
        assertEquals(expResult, result);
    }

    /**
     * Test of isMarkedForDeletion method, of class Comment.
     */
    @Test
    public void testIsMarkedForDeletion() {
        System.out.println("isMarkedForDeletion");
        Comment instance = testComment;
        boolean expResult = false;
        boolean result = instance.isMarkedForDeletion();
        assertEquals(expResult, result);
    }
    
}
