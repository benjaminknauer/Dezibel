/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dezibel.data;

import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Henner
 */
public class CommentTest {
    
    Comment testComment;
    News testNews;
    
    public CommentTest() {
        
    }
    
    @Before
    public void setUp() {
    }

    /**
     * Test of delete method, of class Comment.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        Comment instance = null;
        instance.delete();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getText method, of class Comment.
     */
    @Test
    public void testGetText() {
        System.out.println("getText");
        Comment instance = null;
        String expResult = "";
        String result = instance.getText();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreationDate method, of class Comment.
     */
    @Test
    public void testGetCreationDate() {
        System.out.println("getCreationDate");
        Comment instance = null;
        Date expResult = null;
        Date result = instance.getCreationDate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCommentable method, of class Comment.
     */
    @Test
    public void testGetCommentable() {
        System.out.println("getCommentable");
        Comment instance = null;
        Commentable expResult = null;
        Commentable result = instance.getCommentable();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAuthor method, of class Comment.
     */
    @Test
    public void testGetAuthor() {
        System.out.println("getAuthor");
        Comment instance = null;
        User expResult = null;
        User result = instance.getAuthor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isMarkedForDeletion method, of class Comment.
     */
    @Test
    public void testIsMarkedForDeletion() {
        System.out.println("isMarkedForDeletion");
        Comment instance = null;
        boolean expResult = false;
        boolean result = instance.isMarkedForDeletion();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
