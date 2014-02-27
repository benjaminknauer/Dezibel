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
    private String email;
    private String password;
    private String description;
    private LinkedList<User> myFavoriteUsers;
    private LinkedList<Label> myFavoriteLabel;
    private LinkedList<User> myFollowers;

    /**
     * Class Constructor 
     * 
     * @param email the users email adress
     * @param firstname the users firstname
     * @param lastname the users lastname
     * @param password the users password
     */
    public User(String email, String firstname, String lastname, String password) {
            this.email = email;
            this.firstname = firstname;
            this.lastname = lastname;
            this.password = password;
            
            this.myFavoriteLabel = new LinkedList<Label>();
            this.myFavoriteUsers = new LinkedList<User>();
            this.myFollowers = new LinkedList<User>();
    }

    /**
     * Adds a new favorite uses to the list 
     * 
     * @param fan the user to be followed 
     */
    public void follow(User fan) {
            this.myFavoriteUsers.add(fan);
    }
    
    /**
     * 
     * @param news 
     */
    public void notify(News news) {

    }

    public void notify(Medium medium) {

    }

    public void notify(Album album) {

    }

    public void notify(Playlist playlist) {

    }

    public void notify(Label label, User artist) {

    }

    public void addArtistLabel(Label label) {

    }

    public void addManagerLabel(Label label) {

    }

    public void promoteToArtist() {

    }

    public void promoteToLabelManager() {

    }

    public void promoteToAdmin() {

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

    }

    /**
     * @see Lockable#unlock()
     */
    public void unlock() {

    }

    /**
     * @see Lockable#isLocked()
     */
    public boolean isLocked() {
        return false;
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

    public LinkedList<User> getMyFavoriteUsers() {
        return myFavoriteUsers;
    }

    public LinkedList<Label> getMyFavoriteLabel() {
        return myFavoriteLabel;
    }

    public LinkedList<User> getMyfollowers() {
        return myfollowers;
    }

    
}
