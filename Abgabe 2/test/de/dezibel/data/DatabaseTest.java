package de.dezibel.data;

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
        instance.addMedium("Titel", user1, "", instance.getTopGenre(), null, null);
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
        Database instance = Database.getInstance();
        String text = "This is a comment.";
        Commentable commentable;
        User author;
        instance.addUser("mail@mail.com", "Hans", "Peter", "123", new Date(),
                "Ort", "Land", true);
        author = instance.getUsers().get(instance.getUsers().size() - 1);
        instance.addUser("mail@mail.com", "Artist", "Artist", "123", new Date(),
                "Ort", "Land", true);
        User artist = instance.getUsers().get(instance.getUsers().size() - 1);
        instance.addMedium("Titel", artist, "", instance.getTopGenre(), null, null);
        commentable = instance.getMedia().get(instance.getMedia().size() - 1);
        instance.addComment(text, commentable, author);
        Comment comment = instance.getComments().get(instance.getComments().size() - 1);
        assertTrue(instance.getComments().contains(comment));
        assertEquals(text, comment.getText());
        assertEquals(commentable, comment.getCommentable());
        assertEquals(author, comment.getAuthor());
    }

    /**
     * Test of deleteComment method, of class Database.
     */
    @Test
    public void testDeleteComment() {
        System.out.println("deleteComment");
        Database instance = Database.getInstance();
        String text = "This is a comment.";
        Commentable commentable;
        User author;
        instance.addUser("mail@mail.com", "Hans", "Peter", "123", new Date(),
                "Ort", "Land", true);
        author = instance.getUsers().get(instance.getUsers().size() - 1);
        instance.addUser("mail@mail.com", "Artist", "Artist", "123", new Date(),
                "Ort", "Land", true);
        User artist = instance.getUsers().get(instance.getUsers().size() - 1);
        instance.addMedium("Titel", artist, "", instance.getTopGenre(), null, null);
        commentable = instance.getMedia().get(instance.getMedia().size() - 1);
        instance.addComment(text, commentable, author);
        Comment comment = instance.getComments().get(instance.getComments().size() - 1);
        instance.deleteComment(comment);
        assertFalse(instance.getComments().contains(comment));
        assertFalse(author.getCreatedComments().contains(comment));
        assertFalse(commentable.getComments().contains(comment));
    }

    /**
     * Test of addGenre method, of class Database.
     */
    @Test
    public void testAddGenre() {
        System.out.println("addGenre");
        Database instance = Database.getInstance();
        String name = "Genre";
        Genre superGenre = instance.getTopGenre();
        instance.addGenre(name, superGenre);
        Genre genre = instance.getGenres().get(instance.getGenres().size() - 1);
        assertTrue(instance.getGenres().contains(genre));
        assertEquals(name, genre.getName());
        assertEquals(superGenre, genre.getSuperGenre());
    }

    /**
     * Test of deleteGenre method, of class Database.
     */
    @Test
    public void testDeleteGenre() {
        System.out.println("deleteGenre");
        Database instance = Database.getInstance();
        String name = "Genre";
        Genre superGenre = instance.getTopGenre();
        instance.addGenre(name, superGenre);
        Genre genre = instance.getGenres().get(instance.getGenres().size() - 1);
        instance.deleteGenre(genre);
        assertFalse(instance.getGenres().contains(genre));
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
        Database instance = Database.getInstance();
        String title = "Title";
        User artist;
        String path = "";
        Genre genre = instance.getTopGenre();
        Label label;
        instance.addUser("mail@mail.com", "Hans", "Peter", "123", new Date(),
                "Ort", "Land", true);
        artist = instance.getUsers().get(instance.getUsers().size() - 1);
        instance.addUser("mail@mail.com", "Super", "Manager", "123", new Date(),
                "Ort", "Land", true);
        User manager = instance.getUsers().get(instance.getUsers().size() - 1);
        instance.addLabel(manager, "Label1");
        label = instance.getLabels().get(instance.getLabels().size() - 1);
        instance.addMedium(title, artist, path, genre, label, null);
        Medium medium = instance.getMedia().get(instance.getMedia().size() - 1);
        assertTrue(instance.getMedia().contains(medium));
        assertEquals(title, medium.getTitle());
        assertEquals(artist, medium.getArtist());
        assertEquals(path, medium.getPath());
        assertEquals(genre, medium.getGenre());
        assertEquals(label, medium.getLabel());
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
        assertNull(news.getLabel());
    }

    /**
     * Test of addNews method, of class Database.
     */
    @Test
    public void testAddNews_3args_2() {
        System.out.println("addNews");
        Database instance = Database.getInstance();
        instance.addUser("mail@mail.com", "Hans", "Peter", "123", new Date(),
                "Ort", "Land", true);
        User manager = instance.getUsers().get(instance.getUsers().size() - 1);
        instance.addLabel(manager, "Label1");
        Label label = instance.getLabels().get(instance.getLabels().size() - 1);
        instance.addNews("News-Title", "News-Text", label);
        News news = instance.getNews().get(instance.getNews().size() - 1);
        assertTrue(instance.getNews().contains(news));
        assertEquals("News-Title", news.getTitle());
        assertEquals("News-Text", news.getText());
        assertEquals(label, news.getLabel());
        assertNull(news.getAuthor());
    }

    /**
     * Test of deleteNews method, of class Database.
     */
    @Test
    public void testDeleteNews() {
        System.out.println("deleteNews");
        Database instance = Database.getInstance();
        instance.addUser("mail@mail.com", "Hans", "Peter", "123", new Date(),
                "Ort", "Land", true);
        User manager = instance.getUsers().get(instance.getUsers().size() - 1);
        instance.addLabel(manager, "Label1");
        Label label = instance.getLabels().get(instance.getLabels().size() - 1);
        instance.addNews("News-Title", "News-Text", label);
        News news = instance.getNews().get(instance.getNews().size() - 1);
        instance.deleteNews(news);
        assertFalse(instance.getNews().contains(news));
        assertFalse(label.getNews().contains(news));
    }

    /**
     * Test of addPlaylist method, of class Database.
     */
    @Test
    public void testAddPlaylist() {
        System.out.println("addPlaylist");
        Medium medium;
        String title = "Playlist1";
        User author;
        Database instance = Database.getInstance();
        instance.addUser("mail@mail.com", "Hans", "Peter", "123", new Date(),
                "Ort", "Land", true);
        author = instance.getUsers().get(instance.getUsers().size() - 1);
        instance.addMedium("Medium1", author, "", instance.getTopGenre(), null, null);
        medium = instance.getMedia().get(instance.getMedia().size() - 1);
        instance.addMedium("Medium2", author, "", instance.getTopGenre(), null, null);
        instance.addPlaylist(medium, title, author);
        Playlist playlist = instance.getPlaylists().get(instance.getPlaylists().size() - 1);
        assertTrue(instance.getPlaylists().contains(playlist));
        assertTrue(playlist.getList().contains(medium));
        assertEquals(title, playlist.getTitle());
        assertEquals(author, playlist.getCreator());
    }

    /**
     * Test of deletePlaylist method, of class Database.
     */
    @Test
    public void testDeletePlaylist() {
        System.out.println("deletePlaylist");
        Medium medium;
        String title = "Playlist1";
        User author;
        Database instance = Database.getInstance();
        instance.addUser("mail@mail.com", "Hans", "Peter", "123", new Date(),
                "Ort", "Land", true);
        author = instance.getUsers().get(instance.getUsers().size() - 1);
        instance.addMedium("Medium1", author, "", instance.getTopGenre(), null, null);
        medium = instance.getMedia().get(instance.getMedia().size() - 1);
        instance.addMedium("Medium2", author, "", instance.getTopGenre(), null, null);
        instance.addPlaylist(medium, title, author);
        Playlist playlist = instance.getPlaylists().get(instance.getPlaylists().size() - 1);
        instance.deletePlaylist(playlist);
        assertFalse(instance.getPlaylists().contains(playlist));
        assertFalse(author.getCreatedPlaylists().contains(playlist));
        assertFalse(medium.getPlaylistList().contains(playlist));
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
