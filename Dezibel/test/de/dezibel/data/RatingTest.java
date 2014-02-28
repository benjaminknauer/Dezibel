/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dezibel.data;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Benny
 */
public class RatingTest {
    
    Rating rating1;
    Rating rating2;
    
    public RatingTest() {
    }
    
    @Before
    public void setUp() {
        rating1 = new Rating(3);
        rating2 = new Rating(3);
    }

    /**
     * Test of contructor method, of class Rating.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testRating(){
        Rating testRating = new Rating(6);
    }
    
     /**
     * Test of contructor method, of class Rating.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testRating2(){
        Rating testRating = new Rating(0);
    }
    
    
    /**
     * Test of setPoints method, of class Rating.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testSetAndGetPoints() {
        System.out.println("getPoints");
         
        rating1.setPoints(0);

    }
    /**
     * Test of setPoints method, of class Rating.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testSetAndGetPoints2() {
        System.out.println("getPoints");
         
        rating1.setPoints(6);

    }
    /**
     * Test of setPoints and getPoints method, of class Rating.
     */
    @Test
    public void testSetAndGetPoints3() {
        System.out.println("getPoints");
         
        rating1.setPoints(1);
        rating2.setPoints(5);

        assertEquals(1, rating1.getPoints());
        assertEquals(5, rating2.getPoints());
   }
}
