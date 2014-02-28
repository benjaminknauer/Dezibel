package de.dezibel.data;

import de.dezibel.ErrorCode;
import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the Database class.
 *
 * @author Richard
 */
public class DatabaseTest {

    @Before
    public void setUp() {
    }

    /**
     * Test of save and load method, of class Database.
     */
    @Test
    public void testSaveAndLoad() {
        System.out.println("save");
        // Create old save file, if exists
        File saveFile = new File("save.xml");
        if (saveFile.exists()) saveFile.delete();
        
        Database instance = Database.getInstance();
        instance.addUser("mail@mail.com", "Hans", "Peter", "123", new Date(),
                "Ort", "Land", true);
        User user1 = instance.getUsers().get(instance.getUsers().size() - 1);
        instance.addMedium("Titel", user1, "", instance.getTopGenre(), null);
        Medium medium = instance.getMedia().get(instance.getMedia().size() - 1);
        instance.save();
        // Add second user who does not get saved
        instance.addUser("mail2@mail.com", "Hans2", "Peter2", "123", new Date(),
                "Ort2", "Land2", true);
        instance.load();
        assertEquals(2, instance.getUsers().size());
        User loadedUser = instance.getUsers().get(instance.getUsers().size() - 1);
        // Test if attributes get saved and loaded correctly
        assertEquals(user1.getLastname(), loadedUser.getLastname());
        // Test if associations get saved and loaded correctly
        assertEquals(1, user1.getCreatedMediums().size());
    }

    /**
     * Test of addApplication method, of class Database.
     */
    @Test
    public void testAddApplication() {
        System.out.println("addApplication");
        boolean fromArtist = true;
        String text = "Please add me to your label";
        User artist = null;
        Label label = null;
        Database instance = null;
        ErrorCode expResult = null;
        ErrorCode result = instance.addApplication(fromArtist, text, artist, label);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteApplication method, of class Database.
     */
    @Test
    public void testDeleteApplication() {
        System.out.println("deleteApplication");
        Application application = null;
        Database instance = null;
        instance.deleteApplication(application);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addComment method, of class Database.
     */
    @Test
    public void testAddComment() {
        System.out.println("addComment");
        String text = "";
        Commentable commentable = null;
        User author = null;
        Database instance = null;
        ErrorCode expResult = null;
        ErrorCode result = instance.addComment(text, commentable, author);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteComment method, of class Database.
     */
    @Test
    public void testDeleteComment() {
        System.out.println("deleteComment");
        Comment comment = null;
        Database instance = null;
        instance.deleteComment(comment);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addGenre method, of class Database.
     */
    @Test
    public void testAddGenre() {
        System.out.println("addGenre");
        String name = "";
        Genre superGenre = null;
        Database instance = null;
        ErrorCode expResult = null;
        ErrorCode result = instance.addGenre(name, superGenre);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteGenre method, of class Database.
     */
    @Test
    public void testDeleteGenre() {
        System.out.println("deleteGenre");
        Genre genre = null;
        Database instance = null;
        instance.deleteGenre(genre);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addLabel method, of class Database.
     */
    @Test
    public void testAddLabel() {
        System.out.println("addLabel");
        User manager = null;
        String name = "";
        Database instance = null;
        ErrorCode expResult = null;
        ErrorCode result = instance.addLabel(manager, name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteLabel method, of class Database.
     */
    @Test
    public void testDeleteLabel() {
        System.out.println("deleteLabel");
        Label label = null;
        Database instance = null;
        instance.deleteLabel(label);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addMedium method, of class Database.
     */
    @Test
    public void testAddMedium() {
        System.out.println("addMedium");
        String title = "";
        User artist = null;
        String path = "";
        Genre genre = null;
        Label label = null;
        Database instance = null;
        ErrorCode expResult = null;
        ErrorCode result = instance.addMedium(title, artist, path, genre, label);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addNews method, of class Database.
     */
    @Test
    public void testAddNews_3args_1() {
        System.out.println("addNews");
        String title = "";
        String text = "";
        User author = null;
        Database instance = null;
        ErrorCode expResult = null;
        ErrorCode result = instance.addNews(title, text, author);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addNews method, of class Database.
     */
    @Test
    public void testAddNews_3args_2() {
        System.out.println("addNews");
        String title = "";
        String text = "";
        Label author = null;
        Database instance = null;
        ErrorCode expResult = null;
        ErrorCode result = instance.addNews(title, text, author);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteNews method, of class Database.
     */
    @Test
    public void testDeleteNews() {
        System.out.println("deleteNews");
        News news = null;
        Database instance = null;
        instance.deleteNews(news);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPlaylist method, of class Database.
     */
    @Test
    public void testAddPlaylist() {
        System.out.println("addPlaylist");
        Medium medium = null;
        String title = "";
        User author = null;
        Database instance = null;
        ErrorCode expResult = null;
        ErrorCode result = instance.addPlaylist(medium, title, author);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deletePlaylist method, of class Database.
     */
    @Test
    public void testDeletePlaylist() {
        System.out.println("deletePlaylist");
        Playlist playlist = null;
        Database instance = null;
        instance.deletePlaylist(playlist);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addUser method, of class Database.
     */
    @Test
    public void testAddUser() {
        System.out.println("addUser");
        String email = "";
        String firstname = "";
        String lastname = "";
        String passwort = "";
        Date birthdate = null;
        String city = "";
        String country = "";
        boolean isMale = false;
        Database instance = null;
        ErrorCode expResult = null;
        ErrorCode result = instance.addUser(email, firstname, lastname, passwort, birthdate, city, country, isMale);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUsers method, of class Database.
     */
    @Test
    public void testGetUsers() {
        System.out.println("getUsers");
        Database instance = null;
        LinkedList<User> expResult = null;
        LinkedList<User> result = instance.getUsers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLabels method, of class Database.
     */
    @Test
    public void testGetLabels() {
        System.out.println("getLabels");
        Database instance = null;
        LinkedList<Label> expResult = null;
        LinkedList<Label> result = instance.getLabels();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMedia method, of class Database.
     */
    @Test
    public void testGetMedia() {
        System.out.println("getMedia");
        Database instance = null;
        LinkedList<Medium> expResult = null;
        LinkedList<Medium> result = instance.getMedia();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlaylists method, of class Database.
     */
    @Test
    public void testGetPlaylists() {
        System.out.println("getPlaylists");
        Database instance = null;
        LinkedList<Playlist> expResult = null;
        LinkedList<Playlist> result = instance.getPlaylists();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAlbums method, of class Database.
     */
    @Test
    public void testGetAlbums() {
        System.out.println("getAlbums");
        Database instance = null;
        LinkedList<Album> expResult = null;
        LinkedList<Album> result = instance.getAlbums();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNews method, of class Database.
     */
    @Test
    public void testGetNews() {
        System.out.println("getNews");
        Database instance = null;
        LinkedList<News> expResult = null;
        LinkedList<News> result = instance.getNews();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getComments method, of class Database.
     */
    @Test
    public void testGetComments() {
        System.out.println("getComments");
        Database instance = null;
        LinkedList<Comment> expResult = null;
        LinkedList<Comment> result = instance.getComments();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRatings method, of class Database.
     */
    @Test
    public void testGetRatings() {
        System.out.println("getRatings");
        Database instance = null;
        LinkedList<Rating> expResult = null;
        LinkedList<Rating> result = instance.getRatings();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getApplications method, of class Database.
     */
    @Test
    public void testGetApplications() {
        System.out.println("getApplications");
        Database instance = null;
        LinkedList<Application> expResult = null;
        LinkedList<Application> result = instance.getApplications();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGenres method, of class Database.
     */
    @Test
    public void testGetGenres() {
        System.out.println("getGenres");
        Database instance = null;
        LinkedList<Genre> expResult = null;
        LinkedList<Genre> result = instance.getGenres();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTopGenre method, of class Database.
     */
    @Test
    public void testGetTopGenre() {
        System.out.println("getTopGenre");
        Database instance = null;
        Genre expResult = null;
        Genre result = instance.getTopGenre();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
