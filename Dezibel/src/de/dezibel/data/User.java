package de.dezibel.data;

import java.util.Date;
import java.util.LinkedList;

/**
 * Stores personal-, login- and role-informations about the users.
 *
 * @author Pascal und Bastian
 */
public class User implements Lockable {

    private boolean isArtist;
    private boolean isLabelManager;
    private boolean isAdmin;
    private String firstname;
    private String lastname;
    private Date birthdate;
    private String city;
    private String country;
    private boolean isMale;
    private boolean locked;
    private String email;
    private String password;
    private String description;
    private LinkedList<User> favoriteUsers;
    private LinkedList<User> followers;
    private LinkedList<Label> favoriteLabels;
    private LinkedList<Label> managedLabels;
    private LinkedList<Label> publishingLabels;
    private LinkedList<News> newsList;
    private LinkedList<Application> sentApplications;
    private LinkedList<Medium> createdMediums;
    private LinkedList<Playlist> createdPlaylists;
    private LinkedList<Comment> createdComments;

    /**
     * Class Constructor
     *
     * @param email the users email adress
     * @param firstname the users firstname
     * @param lastname the users lastname
     * @param password the users password
     */
    public User(String email, String firstname, String lastname, 
            String password) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;

        this.favoriteLabels = new LinkedList<Label>();
        this.favoriteUsers = new LinkedList<User>();
        this.followers = new LinkedList<User>();
    }

    /**
     * Adds a new follower to the list of followers.
     *
     * @param fan the user to be followed
     */
    public void follow(User fan) {
        this.followers.add(fan);
    }

    /**
     *
     * @param news
     */
    public void notify(News news) {
        // TODO: Wo notifyen?
    }

    /**
     *
     * @param medium
     */
    public void notify(Medium medium) {

    }

    /**
     *
     * @param album
     */
    public void notify(Album album) {

    }

    /**
     *
     * @param playlist
     */
    public void notify(Playlist playlist) {

    }

    /**
     *
     * @param label
     * @param artist
     */
    public void notify(Label label, User artist) {

    }

    /**
     * Adds a label to the list of labels under which the user publishes his 
     * mediums.
     * 
     * @param label new publishing label
     */
    public void addArtistLabel(Label label) {
        this.publishingLabels.add(label);
    }

    /**
     * Adds a label to the list of labels the user has to manage.
     * 
     * @param label new managed label
     */
    public void addManagerLabel(Label label) {
        this.managedLabels.add(label);
    }

    /**
     * Adds a label to the list of labels the user has favorized.
     * @param label  new favorized label
     */
     public void addFavoriteLabel(Label label){
        favoriteLabels.add(label);
    }
     
    /**
     * Adds a user to the list of users the user has favorized.
     * @param user new favorized user
     */
     public void addFavoriteUser(User user){
         favoriteUsers.add(user);
     }
     
     /**
      * Adds a user to the list of users who follow the user.
      * @param user new following user
      */
     public void addFollower(User user){
         followers.add(user);
     }
     
     /**
      * Adds a news to the list of news the user created.
      * @param news new created news
      */
     public void addNews(News news){
         this.newsList.add(news);
     }
     
     /**
      * Adds a new application to the list of applications.
      * @param app new application sent by the user
      */
     public void addApplication(Application app){
         this.sentApplications.add(app);
     }     
     
     /**
      * Adds a new medium to the list of mediums the user created.
      * @param medium new medium created by the user
      */
     public void addCreatedMedium(Medium medium){
         this.createdMediums.add(medium);
     }
     
     /**
      * Adds a new playlist to the list of playlists created by the user.
      * @param list new playlist created by the user
      */
     public void addCreatedPlaylist(Playlist list){
         this.createdPlaylists.add(list);
     }
     
     /**
      * Adds a new comment to the list of comments created by the user.
      * @param comment new comment created by the user
      */
     public void addCreatedComments(Comment comment){
         this.createdComments.add(comment);
     }
     
    /**
     * Sets a flag for the user to give him the artist functionality.
     */
    public void promoteToArtist() {

    }

    /**
     * Sets a flag for the user to give him the labelmanager functionality.
     */
    public void promoteToLabelManager() {

    }

    /**
     * Sets a flag for the user to give him the admin functionality.
     */
    public void promoteToAdmin() {

    }

    /**
     * Removes a comment from the list of comments which were created by the
     * user.
     * @param comment comment which should be removed
     */
    public void removeComment(Comment comment) {
        this.createdComments.remove(comment);
    }

    /**
     * @see Lockable#lock()
     */
    public void lock() {
        this.locked = true;
    }

    /**
     * @see Lockable#lock(java.lang.String)
     */
    public void lock(String text) {
        this.locked = true;
        // TODO: Add sending of emails.
    }

    /**
     * @see Lockable#unlock()
     */
    public void unlock() {
        this.locked = false;
    }

    /**
     * @see Lockable#isLocked()
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * @see Lockable#getLockText()
     */
    public String getLockText() {
        return null;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isIsMale() {
        return isMale;
    }

    public void setIsMale(boolean isMale) {
        this.isMale = isMale;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIsArtist() {
        return isArtist;
    }

    public boolean isIsLabelManager() {
        return isLabelManager;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public LinkedList<User> getFavoriteUsers() {
        return favoriteUsers;
    }

    public LinkedList<Label> getFavoriteLabels() {
        return favoriteLabels;
    }
    
    public LinkedList<User> getFollowers() {
        return followers;
    }

    public LinkedList<Label> getManagedLabels() {
        return managedLabels;
    }

    public LinkedList<Label> getPublishingLabels() {
        return publishingLabels;
    }

    public LinkedList<News> getNews() {
        return news;
    }

    public LinkedList<Application> getSentApplications() {
        return sentApplications;
    }

    public LinkedList<Medium> getCreatedMediums() {
        return createdMediums;
    }

    public LinkedList<Playlist> getCreatedPlaylists() {
        return createdPlaylists;
    }

    public LinkedList<Comment> getCreatedComments() {
        return createdComments;
    }

}
