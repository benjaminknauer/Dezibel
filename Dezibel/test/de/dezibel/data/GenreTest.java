
package de.dezibel.data;

import java.util.Date;
import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alexander Trahe
 */
public class GenreTest {
    
    Genre genre1;
    Genre genre2;
    Genre genre3;
    Genre genre4;
    Genre genre5;
    
    Medium med1;
    Medium med2;
    
    User loggedUser;
    
    public GenreTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        loggedUser = new User("panni@web.de", "Peter", "Pan", "a1b2c3", true);
        loggedUser.setBirthdate(new Date(1912, 11, 24));
        loggedUser.setCity("Greven");
        loggedUser.setCountry("Deutschland");
        
        genre1 = new Genre("Rock", Database.getInstance().getTopGenre());
        genre2 = new Genre("Klassik", Database.getInstance().getTopGenre());
        genre3 = new Genre("Kuschelrock", genre1);
        genre4 = new Genre("Punkrock", genre1);
        genre5 = new Genre("irgendwas rockiges", genre4);
        
        med1 = new Medium("Super Hits", loggedUser, "c:\bla");
        med2 = new Medium("Crappy Hits", loggedUser, "c:\bli");
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of delete method, of class Genre.
     */
    @Test
    public void testDelete() {
        genre4.delete();
        assertFalse(Database.getInstance().getGenres().contains(genre4));
        assertTrue(genre4.isMarkedForDeletion());
    }

    /**
     * Test of addMedium method, of class Genre.
     */
    @Test
    public void testAddMedium() {
        genre4.addMedium(med1);
        med1.setGenre(genre4);
        genre4.addMedium(med2);
        med2.setGenre(genre4);
        assertTrue(genre4.getMedia().contains(med1));
        assertTrue(genre4.getMedia().contains(med2));
        assertTrue(med1.getGenre() == genre4);
        assertTrue(med2.getGenre() == genre4);
    }

    /**
     * Test of hasMedium method, of class Genre.
     */
    @Test
    public void testHasMedium() {
        genre4.addMedium(med1);
        assertTrue(genre4.hasMedium(med1));
        assertFalse(genre3.hasMedium(med2));
    }

    /**
     * Test of removeMedium method, of class Genre.
     */
    @Test
    public void testRemoveMedium() {
        genre4.addMedium(med1);
        med1.setGenre(genre4);
        genre4.addMedium(med2);
        med2.setGenre(genre4);
        genre4.removeMedium(med1);
        assertTrue(genre4.getMedia().contains(med2));
        assertFalse(genre4.getMedia().contains(med1));
        assertEquals(null, med1.getGenre());
    }

    /**
     * Test of addSubGenre method, of class Genre.
     */
    @Test
    public void testAddSubGenre() {
        genre4.addSubGenre(genre2);
        assertFalse(genre4.hasSubGenre(genre2));
    }

    /**
     * Test of hasSubGenre method, of class Genre.
     */
    @Test
    public void testHasSubGenre() {
        assertTrue(genre1.hasSubGenre(genre3));
    }

    /**
     * Test of removeSubGenre method, of class Genre.
     */
    @Test
    public void testRemoveSubGenre() {
        genre1.removeSubGenre(genre4);
        assertFalse(genre1.hasSubGenre(genre4));
        assertEquals(null, genre4.getSuperGenre());
    }

    /**
     * Test of getMedia method, of class Genre.
     */
    @Test
    public void testGetMedia() {
        genre4.addMedium(med1);
        med1.setGenre(genre4);
        genre4.addMedium(med2);
        med2.setGenre(genre4);
        assertTrue(genre4.getMedia().contains(med1));
        assertTrue(genre4.getMedia().contains(med2));
    }

    /**
     * Test of getSubGenres method, of class Genre.
     */
    @Test
    public void testGetSubGenres() {
        assertTrue(genre1.getSubGenres().contains(genre3));
        assertTrue(genre1.getSubGenres().contains(genre4));
    }

    /**
     * Test of setSuperGenre method, of class Genre.
     */
    @Test
    public void testSetSuperGenre() {
        genre5.setSuperGenre(genre3);
        assertEquals(genre3, genre5.getSuperGenre());
        assertTrue(genre3.getSubGenres().contains(genre5));
    }

    /**
     * Test of getSuperGenre method, of class Genre.
     */
    @Test
    public void testGetSuperGenre() {
        assertEquals(genre4, genre5.getSuperGenre());
        assertEquals(genre1, genre4.getSuperGenre());
    }

    /**
     * Test of getName method, of class Genre.
     */
    @Test
    public void testGetName() {
        assertEquals("Punkrock", genre4.getName());
    }

    /**
     * Test of isMarkedForDeletion method, of class Genre.
     */
    @Test
    public void testIsMarkedForDeletion() {
        genre4.delete();
        assertTrue(genre4.isMarkedForDeletion());
    }
    
}
