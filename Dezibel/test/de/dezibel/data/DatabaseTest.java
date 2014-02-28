package de.dezibel.data;

import de.dezibel.ErrorCode;
import java.io.File;
import java.util.Date;
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
        Database.getInstance().initializeDatabase();
    }

    /**
     * Test of save and load method, of class Database.
     */
    @Test
    public void testSaveAndLoad() {
        System.out.println("save");
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
        
        // Delete save.xml for further tests
        File saveFile = new File("save.xml");
        saveFile.delete();
    }

    /**
     * Test of addApplication method, of class Database.
     */
    @Test
    public void testAddApplication() {
        System.out.println("addApplication");
        boolean fromArtist = true;
        String text = "Please add me to your label";
        Database instance = Database.getInstance();
        instance.addUser("mail@mail.com", "Hans", "Peter", "123", new Date(),
                "Ort", "Land", true);
        User user = instance.getUsers().get(instance.getUsers().size() - 1);
        instance.addUser("mail@mail.com", "Super", "Manager", "123", new Date(),
                "Ort", "Land", true);
        User manager = instance.getUsers().get(instance.getUsers().size() - 1);
        instance.addLabel(manager, "Label1");
        Label label = instance.getLabels().get(instance.getLabels().size() - 1);
        instance.addApplication(fromArtist, text, user, label);
        Application application = instance.getApplications().get(
                instance.getApplications().size() - 1);
        assertTrue(instance.getApplications().contains(application));
        assertTrue(application.isFromArtist());
        assertEquals(text, application.getText());
        assertEquals(user, application.getUser());
        assertEquals(label, application.getLabel());
    }

    /**
     * Test of deleteApplication method, of class Database.
     */
    @Test
    public void testDeleteApplication() {
        System.out.println("deleteApplication");
        boolean fromArtist = true;
        String text = "Please add me to your label";
        Database instance = Database.getInstance();
        instance.addUser("mail@mail.com", "Hans", "Peter", "123", new Date(),
                "Ort", "Land", true);
        User user = instance.getUsers().get(instance.getUsers().size() - 1);
        instance.addUser("mail@mail.com", "Super", "Manager", "123", new Date(),
                "Ort", "Land", true);
        User manager = instance.getUsers().get(instance.getUsers().size() - 1);
        instance.addLabel(manager, "Label1");
        Label label = instance.getLabels().get(instance.getLabels().size() - 1);
        instance.addApplication(fromArtist, text, user, label);
        Application application = instance.getApplications().get(
                instance.getApplications().size() - 1);
        Database.getInstance().deleteApplication(application);
        assertFalse(instance.getApplications().contains(application));
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
        Database instance = Database.getInstance();
        instance.addUser("mail@mail.com", "Super", "Manager", "123", new Date(),
                "Ort", "Land", true);
        User manager = instance.getUsers().get(instance.getUsers().size() - 1);
        instance.addLabel(manager, "Label1");
        Label label = instance.getLabels().get(instance.getLabels().size() - 1);
        assertTrue(instance.getLabels().contains(label));
        assertTrue(label.getLabelManagers().contains(manager));
        assertEquals(label.getName(), "Label1");
    }

    /**
     * Test of deleteLabel method, of class Database.
     */
    @Test
    public void testDeleteLabel() {
        System.out.println("deleteLabel");
        Database instance = Database.getInstance();
        instance.addUser("mail@mail.com", "Super", "Manager", "123", new Date(),
                "Ort", "Land", true);
        User manager = instance.getUsers().get(instance.getUsers().size() - 1);
        instance.addLabel(manager, "Label1");
        Label label = instance.getLabels().get(instance.getLabels().size() - 1);
        instance.deleteLabel(label);
        assertFalse(instance.getLabels().contains(label));
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
        Database instance = Database.getInstance();
        instance.addUser("mail@mail.com", "Hans", "Peter", "123", new Date(),
                "Ort", "Land", true);
        User user = instance.getUsers().get(instance.getUsers().size() - 1);
        instance.addNews("News-Title", "News-Text", user);
        News news = instance.getNews().get(instance.getNews().size() - 1);
        assertTrue(instance.getNews().contains(news));
        assertEquals("News-Title", news.getTitle());
        assertEquals("News-Text", news.getText());
        assertEquals(user, news.getAuthor());
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
        String email = "mail@mail.com";
        String firstname = "Hans";
        String lastname = "Peter";
        String passwort = "123";
        Date birthdate = new Date();
        String city = "Ort";
        String country = "Land";
        boolean isMale = true;
        Database instance = Database.getInstance();
        instance.addUser(email, firstname, lastname, passwort, birthdate, city,
                country, isMale);
        User user = instance.getUsers().get(instance.getUsers().size() - 1);
        assertTrue(instance.getUsers().contains(user));
        assertEquals(email, user.getEmail());
        assertEquals(firstname, user.getFirstname());
        assertEquals(lastname, user.getLastname());
        assertEquals(passwort, user.getPassword());
        assertEquals(birthdate, user.getBirthdate());
        assertEquals(city, user.getCity());
        assertEquals(country, user.getCountry());
        assertEquals(isMale, user.isMale());
    }
}
