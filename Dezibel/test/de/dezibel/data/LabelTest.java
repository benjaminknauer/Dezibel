package de.dezibel.data;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Benjamin Knauer
 */
public class LabelTest {

    User labelmanager;
    User artist;
    Label label;

    public LabelTest() {
    }

    @Before
    public void setUp() {
        labelmanager = new User("test@test.de", "Max", "Musterman", "123", true);
        artist = new User("test2@test.de", "Tim", "Meyer", "123", true);

        label = new Label(labelmanager, "Mein Label");
    }

    /**
     * Test of addArtist method, of class Label.
     */
    @Test
    public void testAddArtist() {
        System.out.println("addArtist");

        label.addArtist(artist);

        assertTrue(label.getArtists().contains(artist));
        assertTrue(artist.getPublishingLabels().contains(label));

    }

    /**
     * Test of removeArtist method, of class Label.
     */
    @Test
    public void testRemoveArtist() {
        System.out.println("removeArtist");

        label.addArtist(artist);

        label.removeArtist(artist);

        assertFalse(label.getArtists().contains(artist));
        assertFalse(artist.getPublishingLabels().contains(label));

    }

    /**
     * Test of addManager method, of class Label.
     */
    @Test
    public void testAddManager() {
        System.out.println("addManager");

        User labelmanager2 = new User("test3@test.de", "Timo", "Boll", "123", true);

        label.addManager(labelmanager2);
        assertTrue(label.getLabelManagers().contains(labelmanager2));
        assertTrue(labelmanager2.getManagedLabels().contains(label));

    }

    /**
     * Test of addNews method, of class Label.
     */
    @Test
    public void testAddNews() {
        System.out.println("addNews");
        // AddNews is called in the constructor of News
        News news1 = new News("Hallo!!!", "Hallo, dies ist ein Test!", label);

        assertTrue(label.getNews().contains(news1));
        assertEquals(label, news1.getLabel());

    }

    /**
     * Test of deleteNews method, of class Label.
     */
    @Test
    public void testDeleteNews() {
        System.out.println("deleteNews");

        News news1 = new News("Hallo!!!", "Hallo, dies ist ein Test!", label);

        label.deleteNews(news1);

        assertFalse(label.getNews().contains(news1));
        assertNull(news1.getLabel());
    }

    /**
     * Test of deleteApplication method, of class Label.
     */
    @Test
    public void testDeleteApplication() {
        System.out.println("deleteApplication");

        Application application1 = new Application(true, "Bewerbung", artist, label);

        label.deleteApplication(application1);
        assertFalse(label.getApplications().contains(application1));
        assertNull(application1.getLabel());

    }

    /**
     * Test of addApplication method, of class Label.
     */
    @Test
    public void testAddApplication() {
        System.out.println("addApplication");
        // addApplication is called in the constructor of Application
        Application application1 = new Application(true, "Bewerbung", artist, label);

        assertTrue(label.getApplications().contains(application1));
        assertEquals(label, application1.getLabel());
    }

    /**
     * Test of removeAlbum method, of class Label.
     */
    @Test
    public void testRemoveAlbum() {
        System.out.println("removeAlbum");
        Medium testMedium1, testMedium2;
        testMedium1 = new Medium("TestMedium1", this.artist, "path");
        testMedium2 = new Medium("TestMedium2", this.artist, "path2");
        Album testAlbum1, testAlbum2;
        testAlbum1 = new Album(testMedium1, "TestAlbum1", this.label, true);
        testAlbum2 = new Album(testMedium2, "TestAlbum2", this.artist, true);       
        
        this.label.addAlbum(testAlbum2);
        this.label.removeAlbum(testAlbum1);
        
        // testAlbum1 should be marked for deletion now.
        assertFalse(this.label.getAlbums().contains(testAlbum1));
        assertTrue(testAlbum1.isMarkedForDeletion());
        
        this.label.removeAlbum(testAlbum2);
        
        // testAlbum2 should NOT be marked for deletion now (because the label wasn't the creator)
        assertFalse(this.label.getAlbums().contains(testAlbum2));
        assertFalse(testAlbum2.isMarkedForDeletion());
        
        assertTrue(this.label.getAlbums().isEmpty());
        assertNull(testAlbum1.getLabel());
        assertNull(testAlbum2.getLabel());
    }
    /**
     * Test of addAlbum method, of class Label.
     */
    @Test
    public void testAddAlbum() {
        System.out.println("addAlbum");
        Medium testMedium1, testMedium2;
        testMedium1 = new Medium("TestMedium1", this.artist, "path");
        testMedium2 = new Medium("TestMedium2", this.artist, "path2");
        Album testAlbum1, testAlbum2;
        testAlbum1 = new Album(testMedium1, "TestAlbum1", this.label, true);
        testAlbum2 = new Album(testMedium2, "TestAlbum2", this.artist, true);
        
        this.label.addAlbum(testAlbum2);
        
        assertTrue(testAlbum1.getLabel().equals(this.label));
        assertTrue(testAlbum2.getLabel().equals(this.label));
        assertTrue(this.label.getAlbums().contains(testAlbum1));
        assertTrue(this.label.getAlbums().contains(testAlbum2));
        assertEquals(2,this.label.getAlbums().size());
    }

    /**
     * Test of delete method, of class Label.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        
        label.addArtist(artist);
        
        User labelmanager2 = new User("test3@test.de", "Timo", "Boll", "123", true);
        label.addManager(labelmanager2);
        
        News news1 = new News("Hallo!!!", "Hallo, dies ist ein Test!", label);
        Application application1 = new Application(true, "Bewerbung", artist, label);
        
        User follower1 = new User("test4@test.de", "Silke", "Haller", "123", false);
        follower1.addFavoriteLabel(label);
        
        Medium medium1 = new Medium("Medium1", artist, "path");
        Album album1 = new Album(medium1, "Album1", label, true);
        
        label.delete();

        assertFalse(artist.getPublishingLabels().contains(label));
        assertFalse(labelmanager.getManagedLabels().contains(label));
        assertFalse(labelmanager2.getManagedLabels().contains(label));
        assertNull(news1.getLabel());
        assertNull(application1.getLabel());
        assertFalse(follower1.getFavorizedLabels().contains(label));
        assertFalse(label.getAlbums().contains(album1));
        assertTrue(album1.isMarkedForDeletion());
    }

    /**
     * Test of removeManager method, of class Label.
     */
    @Test
    public void testRemoveManager() {
        System.out.println("removeManager");

        User labelmanager2 = new User("test3@test.de", "Timo", "Boll", "123", true);
        label.addManager(labelmanager2);
        
        label.removeManager(labelmanager);
        assertFalse(label.getLabelManagers().contains(labelmanager));
        assertFalse(labelmanager.getManagedLabels().contains(label));
    }

    /**
     * Test of follow method, of class Label.
     */
    @Test
    public void testFollow() {
        System.out.println("follow");
        
        User follower1 = new User("test4@test.de", "Silke", "Haller", "123", false);
        label.follow(follower1);
        
        assertTrue(label.getFollowers().contains(follower1));
        assertTrue(follower1.getFavorizedLabels().contains(label));

        
    }

    /**
     * Test of removeFollower method, of class Label.
     */
    @Test
    public void testRemoveFollower() {
        System.out.println("removeFollower");

        User follower1 = new User("test4@test.de", "Silke", "Haller", "123", false);
        label.follow(follower1);
        
        label.removeFollower(follower1);
        
        assertFalse(label.getFollowers().contains(follower1));
        assertFalse(follower1.getFavorizedLabels().contains(label));
    }

    /**
     * Test of lock method, of class Label.
     */
    @Test
    public void testLock_0args() {
        System.out.println("lock");

        label.lock();
        
        assertTrue(label.isLocked());
        assertEquals("", label.getLockText());
    }

    /**
     * Test of lock method, of class Label.
     */
    @Test
    public void testLock_String() {
        System.out.println("lock");
        
        label.lock("Label wurde gesperrt!");
        
        assertTrue(label.isLocked());
        assertEquals("Label wurde gesperrt!", label.getLockText());
    }

    /**
     * Test of unlock method, of class Label.
     */
    @Test
    public void testUnlock() {
        System.out.println("unlock");

        label.lock("Label wurde gesperrt!");
        
        assertTrue(label.isLocked());
        
        label.unlock();
        
        assertFalse(label.isLocked());
        
    }
}
