package de.dezibel.data;

import java.util.LinkedList;

/**
 * This class represents a Label.
 *
 * @author Alexander Trahe, Benjamin Knauer, Tobias
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

    // Bool to tell the database that this instance of Label may be deleted.
    // Only set to true if all associations are cleared!
    private boolean markedForDeletion = false;

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
        manager.addManagerLabel(this);
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
        if (this.artists.contains(artist)) {
            this.artists.remove(artist);
            artist.removeArtistLabel(this);
        }
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
     * This method adds a news to the label.
     *
     * @param news news to be added
     */
    public void addNews(News news) {
        this.news.add(news);
    }

    /**
     * This method removes <code>news</code> from the Label's News and deletes
     * it.
     *
     * @param news The News object to be removed and deleted.
     */
    public void deleteNews(News news) {
        this.news.remove(news);
        if (news != null) {
            news.delete();
        }
    }

    /**
     * This method removes an application from the list of applications and
     * deletes it and all its associations!
     *
     * @param application application to be removed and deleted
     */
    public void deleteApplication(Application application) {
        this.applications.remove(application);
        if (application != null) {
            application.delete();
        }
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
     * This method removes an album from the list of albums.
     *
     * @param album album to be removed
     */
    public void removeAlbum(Album album) {
        this.albums.add(album);
        if(album != null)
            album.removeLabel(this);
    }

    /**
     * This method adds an album to the label.
     *
     * @param album album to be added
     */
    public void addAlbum(Album album){
        if(!this.albums.contains(album)){
            albums.add(album);
        }
    }
    
    /**
     * Completely deletes this label from the database and clears all its
     * associations. This will also automatically completely delete all news,
     * applications and comments associated with this label from the system!
     */
    public void delete() {
        if (this.markedForDeletion) {
            return;
        }
        this.markedForDeletion = true;
        for (User currentArtist : this.artists) {
            currentArtist.removeArtistLabel(this);
        }
        this.artists.clear();
        for (User currentFollower : this.followers) {
            currentFollower.removeFavoriteLabel(this);
        }
        this.followers.clear();
        for (News currentNews : news) {
            currentNews.delete();
        }
        this.news.clear();
        for (Application currentApplication : this.applications) {
            deleteApplication(currentApplication);
        }
        this.applications = null;
        for (Album currentAlbum : this.albums) {
            //TODO delete Album           removeAlbum(currentAlbum);
        }
        this.albums.clear();
        for (User currentManager : this.labelManager) {
            removeManager(currentManager);
        }
        this.labelManager.clear();
        Database.getInstance().deleteLabel(this);
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
            delete();
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
        if (this.followers.contains(fan)) {
            this.followers.remove(fan);
            fan.removeFavoriteLabel(this);
        }
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

    public boolean isMarkedForDeletion() {
        return markedForDeletion;
    }
}
