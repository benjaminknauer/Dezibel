package de.dezibel.data;

import java.util.LinkedList;

/**
 * This class represents a Label.
 *
 * @author Alexander Trahe, Benjamin Knauer
 */
public class Label implements Lockable {

    private String companyDetails;
    private String name;
    private LinkedList<User> labelManager;
    private LinkedList<User> artists;
    private LinkedList<User> followers;
    private LinkedList<Application> applications;
    private LinkedList<News> news;
    private LinkedList<Album> albums;
    private boolean isLocked;
    private String lockText;

    /**
     * Class constructor.
     *
     * @param manager manager who creates the label
     * @param name name of the label
     */
    public Label(User manager, String name) {
        this.labelManager = new LinkedList<>();
        this.artists = new LinkedList<>();
        this.followers = new LinkedList<>();
        this.applications = new LinkedList<>();
        this.news = new LinkedList<>();
        this.albums = new LinkedList<>();

        this.name = name;
        this.labelManager.add(manager);
    }

    /**
     * This method adds an artist to the label.
     *
     * @param artist artist to be added
     */
    public void addArtist(User artist) {
        if (!artists.contains(artist)) {
            this.artists.add(artist);
            artist.addArtistLabel(this);
        }
    }

    /**
     * This method removes an artist from the list of artists.
     *
     * @param artist artist to be removed
     */
    public void removeArtist(User artist) {
        this.artists.remove(artist);
        artist.removeArtistLabel(this);
    }

    /**
     * This method adds a manager to the label.
     *
     * @param manager manager to be added
     */
    public void addManager(User manager) {
        if (!labelManager.contains(manager)) {
            this.labelManager.add(manager);
            manager.addManagerLabel(this);
        }
    }

    /**
     * This method removes an application from the list of artists.
     *
     * @param application application to be removed
     */
    public void removeApplication(Application application) {
        this.applications.remove(application);
        application.getUser().removeApplication(application);
        Database.getInstance().removeApplication(application);
    }

    /**
     * This method adds an application to the label.
     *
     * @param application application to be added
     */
    public void addApplication(Application application) {
        if (!labelManager.contains(application)) {
            this.applications.add(application);
        }
    }

    /**
     * This method removes an album from the list of artists.
     *
     * @param album album to be removed
     */
    public void removeAlbum(Album album) {
        this.albums.remove(album);
        album.setLabel(null);
    }

    /**
     * This method adds an album to the label.
     *
     * @param album album to be added
     */
    public void addAlbum(Album album) {
        if (!albums.contains(album)) {
            this.albums.add(album);
            album.setLabel(this);
        }
    }

    /**
     * This method removes a manager from the list of artists.
     *
     * @param manager manager to be removed
     */
    public void removeManager(User manager) {
        this.labelManager.remove(manager);
        manager.removeManagerLabel(this);
        if (labelManager.size() == 0) {
            // TODO removeLabel bei Database einfügen
            Database.getInstance().removeLabel(this);
            for (User currentArtist : artists) {
                currentArtist.removeArtistLabel(this);
            }
            for (User currentFollower : followers) {
                currentFollower.removeFavoriteLabel(this);
            }
            for (News currentNews : news) {
                currentNews.deleteComments();
                Database.getInstance().removeNews(currentNews);
            }
            for (Application currentApplication : applications) {
                removeApplication(currentApplication);
            }
            for (Album currentAlbum : albums) {
                removeAlbum(currentAlbum);
            }
            news = null;

        }
    }

    /**
     * This method adds a follower to the label.
     *
     * @param fan follower of the label
     */
    public void follow(User fan) {
        if (!followers.contains(fan)) {
            this.followers.add(fan);
            fan.addFavoriteLabel(this);
        }
    }

    /**
     * This method removes a follower from the list of followers.
     * 
     * @param fan follower to be removed
     */
    public void removeFollower(User fan) {
        this.followers.remove(fan);
        fan.removeFavoriteLabel(this);
    }

    /**
     * @see Lockable#lock()
     */
    public void lock() {
        lock("");
    }

    /**
     * @see Lockable#lock(java.lang.String)
     */
    public void lock(String text) {
        this.isLocked = true;
        this.lockText = text;
    }

    /**
     * @see Lockable#unlock()
     */
    public void unlock() {
        this.isLocked = false;
    }

    /**
     * @see Lockable#isLocked()
     */
    public boolean isLocked() {
        return isLocked;
    }

    /**
     * @see Lockable#getLockText()
     */
    public String getLockText() {
        return lockText;
    }

    public String getCompanyDetails() {
        return companyDetails;
    }

    public void setCompanyDetails(String companyDetails) {
        this.companyDetails = companyDetails;
    }

    public String getName() {
        return name;
    }

    public LinkedList<User> getLabelManagers() {
        return (LinkedList<User>) labelManager.clone();
    }

    public LinkedList<User> getArtists() {
        return (LinkedList<User>) artists.clone();
    }

    public LinkedList<User> getFollowers() {
        return (LinkedList<User>) followers.clone();
    }

    public LinkedList<Application> getApplications() {
        return (LinkedList<Application>) applications.clone();
    }

    public LinkedList<News> getNews() {
        return (LinkedList<News>) news.clone();
    }

    public LinkedList<Album> getAlbums() {
        return (LinkedList<Album>) albums.clone();
    }
}
