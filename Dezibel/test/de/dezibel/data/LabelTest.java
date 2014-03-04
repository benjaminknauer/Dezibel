package de.dezibel.data;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
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

    //TODO:
    /**
     * Test of removeAlbum method, of class Label.
     */
    @Test
    @Ignore
    public void testRemoveAlbum() {
        System.out.println("removeAlbum");


    }
    //TODO:
    /**
     * Test of addAlbum method, of class Label.
     */
    @Test
    @Ignore
    public void testAddAlbum() {
        System.out.println("addAlbum");


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
        
        System.out.println("------ " + label.getLabelManagers());
        System.out.println("------ " + labelmanager.getManagedLabels());
        System.out.println("------ " + labelmanager2.getManagedLabels());
        
        News news1 = new News("Hallo!!!", "Hallo, dies ist ein Test!", label);
        Application application1 = new Application(true, "Bewerbung", artist, label);
        
        User follower1 = new User("test4@test.de", "Silke", "Haller", "123", false);
        follower1.addFavoriteLabel(label);
        
        label.delete();

        System.out.println("------ " + label.getLabelManagers());
        System.out.println("------ " + labelmanager.getManagedLabels());
        System.out.println("------ " + labelmanager2.getManagedLabels());
        
        assertFalse(artist.getPublishingLabels().contains(label));
        assertFalse(labelmanager.getManagedLabels().contains(label));
        System.out.println(labelmanager2.getManagedLabels());
        assertFalse(labelmanager2.getManagedLabels().contains(label)); // FEHLER!!!
        assertNull(news1.getLabel());
        assertNull(application1.getLabel());
        assertFalse(follower1.getFavoriteLabels().contains(label));
        
        //TODO Album testen
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
        assertTrue(follower1.getFavoriteLabels().contains(label));

        
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
        assertFalse(follower1.getFavoriteLabels().contains(label));
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
