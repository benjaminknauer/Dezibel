package de.dezibel.data;

import java.util.LinkedList;

/**
 * This class represents a Label.
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
     * @param artist artist to be added
     */
    public void addArtist(User artist) {
        this.artists.add(artist);
        artist.addArtistLabel(this);
    }
    
    /**
     * This method removes an artist form the list of artists.
     * @param artist artist to be removed
     */
    public void removeArtist(User artist){
        this.artists.remove(artist);
        artist.removeArtistLabel(this);
    }

    /**
     * This method adds a manager to the label.
     * @param manager manager to be added
     */
    public void addManager(User manager) {
        this.labelManager.add(manager);
        manager.addManagerLabel(this);
    }
    
    /**
     * This method removes a manager form the list of artists.
     * @param manager 
     */
    public void removeManager(User manager){
        this.labelManager.remove(manager);
        manager.removeManagerLabel(this);
        if (labelManager.size() == 0){
            // TODO removeLabel bei Database einfügen
            Database.getInstance().removeLabel(this);
            for (User currentArtist : artists){
                currentArtist.removeArtistLabel(this);
            }
            for (User currentFollower : followers){
                currentFollower.removeFollowedLabel(this);
            }
            
        }
    }
    
    /**
     * This method adds a follower to the label.
     * @param fan follower of the label
     */
    public void follow(User fan) {
        this.followers.add(fan);
        // Assoziation
    }
    
    public void removeFollower(User fan){
        this.followers.remove(fan);
        fan.removeFollowedLabel(this);
    }

    /**
     * @see Lockable#lock()
     */
    public void lock() {
        
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
        return labelManager;
    }

    public LinkedList<User> getArtists() {
        return artists;
    }

    public LinkedList<User> getFollowers() {
        return followers;
    }
    
}
