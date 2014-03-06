package de.dezibel.data;

import java.util.Date;
import java.util.LinkedList;

/**
 * Stores personal-, login- and role-informations about the user.
 *
 * @author Pascal und Bastian, Tobias und Richard
 * @inv pseudonym!=null implies artist=true
 */
public class User implements Lockable {

    private boolean artist;
    private boolean labelManager;
    private boolean admin;
    private String firstname;
    private String lastname;
    private String pseudonym;
    private Date birthdate;
    private String city;
    private String country;
    private boolean male;
    private boolean locked;
    private String lockText;
    private String email;
    private String password;
    private String description;
    private LinkedList<User> favorizedUsers;
    private LinkedList<User> followers;
    private LinkedList<Label> favorizedLabels;
    private LinkedList<Label> managedLabels;
    private LinkedList<Label> publishingLabels;
    private LinkedList<News> newsList;
    private LinkedList<Application> sentApplications;
    private LinkedList<Medium> createdMediums;
    private LinkedList<Medium> favorizedMediums;
    private LinkedList<Playlist> createdPlaylists;
    private LinkedList<Playlist> favorizedPlaylists;
    private LinkedList<Comment> createdComments;
    private LinkedList<Album> createdAlbums;
    private LinkedList<Album> favorizedAlbums;

    /**
     * Class Constructor for user which creates a user with the minimum datas
     * given by the parameters.
     *
     * @param email the users email adress
     * @param firstname the users firstname
     * @param lastname the users lastname
     * @param password the users password
     * @param isMale the users gender
     */
    public User(String email, String firstname, String lastname,
            String password, boolean isMale) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.male = isMale;

        favorizedUsers = new LinkedList<>();
        followers = new LinkedList<>();
        favorizedLabels = new LinkedList<>();
        managedLabels = new LinkedList<>();
        publishingLabels = new LinkedList<>();
        newsList = new LinkedList<>();
        sentApplications = new LinkedList<>();
        createdMediums = new LinkedList<>();
        favorizedMediums = new LinkedList<>();
        createdPlaylists = new LinkedList<>();
        favorizedPlaylists = new LinkedList<>();
        createdComments = new LinkedList<>();
        createdAlbums = new LinkedList<>();
        favorizedAlbums = new LinkedList<>();
    }

    // TODO: Mails in notify methoden versenden.
    /**
     * Informs the user about a new news by a favorite.
     *
     * @param news someone else's new news
     */
    public void notify(News news) {

    }

    /**
     * Informs the user about a new medium by a favorite.
     *
     * @param medium someone else's new medium
     */
    public void notify(Medium medium) {

    }

    /**
     * Informs the user about a new album by a favorite.
     *
     * @param album someone else's new album
     */
    public void notify(Album album) {

    }

    /**
     * Informs the user about a new playlist by a favorite.
     *
     * @param playlist someone else's new playlist
     */
    public void notify(Playlist playlist) {

    }

    /**
     * Informs the user about a new artist of a favorized label.
     *
     * @param label favorized label
     * @param artist new artist of favorized label
     */
    public void notify(Label label, User artist) {

    }

    /**
     * Adds a new album to the artists list of created albums.
     *
     * @param album album which should be created
     */
    public void addAlbum(Album album) {
        if (this.createdAlbums.contains(album)) {
            return;
        }
        this.createdAlbums.add(album);
    }

    /**
     * Removes an album from the artists list of created albums.
     *
     * @param album album which should be removed
     * @pre createdAlbums is not empty
     * @post createdAlbums size is reduced by 1
     */
    public void removeAlbum(Album album) {
        this.createdAlbums.remove(album);
    }

    /**
     * Adds a label to the list of labels under which the user publishes his
     * mediums.
     *
     * @param label new publishing label
     */
    public void addArtistLabel(Label label) {
        if (!(this.publishingLabels.contains(label))) {
            this.publishingLabels.add(label);
            label.addArtist(this);
        }
    }

    /**
     * Removes a label from the list of labels under which the user publishes
     * his mediums.
     *
     * @param label artist label which should be removed
     * @pre publishingLabels is not empty
     * @post publishingLabels size is reduced by 1
     */
    public void removeArtistLabel(Label label) {
        if (publishingLabels.contains(label)) {
            this.publishingLabels.remove(label);
            label.removeArtist(this);
        }
    }

    /**
     * Adds a label to the list of labels the user has to manage.
     *
     * @param label new managed label
     */
    public void addManagerLabel(Label label) {
        if (!(this.managedLabels.contains(label))) {
            this.managedLabels.add(label);
            label.addManager(this);
        }
    }

    /**
     * Removes a label from the list of labels which the user has to manage.
     *
     * @param label manager label which should be removed
     * @pre managedLabels is not empty
     * @post managedLabels size is reduced by 1
     */
    public void removeManagerLabel(Label label) {
        if (this.managedLabels.contains(label)) {
            this.managedLabels.remove(label);
            label.removeManager(this);
        }
    }

    /**
     * Adds a label to the list of labels the user has favorized.
     *
     * @param label new favorized label
     */
    public void addFavoriteLabel(Label label) {
        if (!(this.favorizedLabels.contains(label))) {
            this.favorizedLabels.add(label);
            label.follow(this);
        }
    }

    /**
     * Removes a label from the list of labels which the user favorized.
     *
     * @param label favorized label which should be removed
     * @pre favoriteLabels is not empty
     * @post favoriteLabels size is reduced by 1
     */
    public void removeFavoriteLabel(Label label) {
        if (this.favorizedLabels.contains(label)) {
            this.favorizedLabels.remove(label);
            label.removeFollower(this);
        }
    }

    /**
     * Adds a user to the list of users the user has favorized.
     *
     * @param user new favorized user
     */
    public void addFavoriteUser(User user) {
        if (!(this.favorizedUsers.contains(user))) {
            this.favorizedUsers.add(user);
            user.addFollower(this);
        }
    }

    /**
     * Removes a user from the list of favorized users.
     *
     * @param user user which should be removed
     * @pre favoriteUsers is not empty
     * @post favoriteUsers size is reduced by 1
     */
    public void removeFavoriteUser(User user) {
        if (this.favorizedUsers.contains(user)) {
            this.favorizedUsers.remove(user);
            user.removeFollower(this);
        }
    }

    /**
     * Adds a user to the list of users who follow the user.
     *
     * @param user new following user
     */
    public void addFollower(User user) {
        if (!(this.followers.contains(user))) {
            this.followers.add(user);
            user.addFavoriteUser(this);
        }
    }

    /**
     * Removes a user from the list of followers.
     *
     * @param user user which should be removed
     * @pre followers is not empty
     * @post followers size is reduced by 1
     */
    public void removeFollower(User user) {
        if (this.followers.contains(user)) {
            this.followers.remove(user);
            user.removeFavoriteUser(this);
        }
    }

    /**
     * Adds a new favorized playlist to the users list of favorite playlists.
     *
     * @param favPL new favorized playlist
     */
    public void addFavoritePlaylist(Playlist favPL) {
        if (!(this.favorizedPlaylists.contains(favPL))) {
            this.favorizedPlaylists.add(favPL);
        }
    }

    /**
     * Removes playlist from the users list of favorite playlists.
     *
     * @param favPL favorized playlist
     */
    public void removeFavoritePlaylist(Playlist favPL) {
        if (this.favorizedPlaylists.contains(favPL)) {
            this.favorizedPlaylists.remove(favPL);
        }
    }

    /**
     * Adds a new favorized album to the users list of favorite albums.
     *
     * @param favAlbum new favorized album
     */
    public void addFavoriteAlbum(Album favAlbum) {
        if (!(this.favorizedAlbums.contains(favAlbum))) {
            this.favorizedAlbums.add(favAlbum);
        }
    }

    /**
     * Removes album from the users list of favorite albums.
     *
     * @param favAlbum favorized album
     */
    public void removeFavoriteAlbum(Album favAlbum) {
        if (this.favorizedAlbums.contains(favAlbum)) {
            this.favorizedAlbums.remove(favAlbum);
        }
    }

    /**
     * Adds a new favorized medium to the users list of favorite mediums.
     *
     * @param favMedium new favorized medium
     */
    public void addFavoriteMedium(Medium favMedium) {
        if (!(this.favorizedMediums.contains(favMedium))) {
            this.favorizedMediums.add(favMedium);
        }
    }

    /**
     * Removes medium from the users list of favorite mediums.
     *
     * @param favMedium favorized medium
     */
    public void removeFavoriteMedium(Medium favMedium) {
        if (this.favorizedMediums.contains(favMedium)) {
            this.favorizedMediums.remove(favMedium);
        }
    }

    /**
     * Adds a news to the list of news the user created.
     *
     * @param news new created news
     */
    public void addNews(News news) {
        if (news.getAuthor() == this) {
            if (!(this.newsList.contains(news))) {
                this.newsList.add(news);
            }
        }
    }

    /**
     * Removes a news from the list of the news the user created and deletes it.
     *
     * @param news The news to be removed and deleted.
     * @pre newsList is not empty
     * @post newsList size is reduced by 1
     */
    public void deleteNews(News news) {
        this.newsList.remove(news);
        if (!news.isMarkedForDeletion()) {
            news.delete();
        }
    }

    /**
     * Adds a new application to the list of applications.
     *
     * @param app new application sent by the user
     */
    public void addApplication(Application app) {
        if (app.getUser() == this) {
            this.sentApplications.add(app);
        }
    }

    /**
     * Removes an application from the list of applications.
     *
     * @param app application which should be removed
     * @pre sentApplications is not empty
     * @post sentApplications size is reduced by 1
     */
    public void deleteApplication(Application app) {
        this.sentApplications.remove(app);
        if (app != null && !app.isMarkedForDeletion()) {
            app.delete();
        }
    }

    /**
     * Adds a new medium to the list of mediums the user created.
     *
     * @param medium new medium created by the user
     */
    public void addCreatedMedium(Medium medium) {
        if (medium.getArtist() == this) {
            this.createdMediums.add(medium);
        }
    }

    /**
     * Removes a medium from the list of created mediums, which is viewable by
     * users (admins still see this intern list as usual).
     *
     * @param medium medium which should be removed
     * @pre medium is not marked as deleted yet
     * @post medium is marked as deleted
     */
    public void removeCreatedMedium(Medium medium) {
        medium.markAsDeleted();
    }

    /**
     * Adds a new playlist to the list of playlists created by the user.
     *
     * @param list new playlist created by the user
     */
    public void addCreatedPlaylist(Playlist list) {
        if (list.getCreator() == this) {
            this.createdPlaylists.add(list);
        }
    }

    /**
     * Removes a playlist from the users list of created playlists.
     *
     * @param list playlist which should be removed
     * @pre createdPlaylists is not empty
     * @post createdPlaylists size is reduced by 1
     */
    public void removeCreatedPlaylist(Playlist list) {
        if (this.createdPlaylists.contains(list)) {
            this.createdPlaylists.remove(list);
        }
    }

    /**
     * Adds a new comment to the list of comments created by the user.
     *
     * @param comment new comment created by the user
     */
    public void addCreatedComments(Comment comment) {
        if (comment.getAuthor() == this) {
            this.createdComments.add(comment);
        }
    }

    /**
     * Removes a comment from the list of comments which were created by the
     * user and deletes it.
     *
     * @param comment comment which should be removed and deleted.
     * @pre createdComments is not empty
     * @post createdComments size is reduced by 1
     */
    public void deleteComment(Comment comment) {
        this.createdComments.remove(comment);
        if (comment != null && !comment.isMarkedForDeletion()) {
            comment.delete();
        }
    }

    /**
     * Sets a flag for the user to give him the artist functionality.
     *
     * @pre user is not flagged as an artist yet
     * @post user is flagged as an artist
     */
    public void promoteToArtist() {
        if (this.pseudonym != null && !this.pseudonym.isEmpty()) {
            this.artist = true;
        }
    }

    /**
     * Sets a flag for the user to give him the labelmanager functionality.
     *
     * @pre user is not flagged as a labelmanager yet
     * @post user is flagged as a labelmanager
     */
    public void promoteToLabelManager() {
        this.labelManager = true;
    }

    /**
     * Sets a flag for the user to give him the admin functionality.
     *
     * @pre user is not flagged as an admin yet
     * @post user is flagged as an admin
     */
    public void promoteToAdmin() {
        this.admin = true;
    }

    /**
     * @see Lockable#lock()
     */
    public void lock() {
        this.lock("Ihr Account wurde gesperrt. Bitte wenden Sie sich "
                + "für mehr Informationen an den Administartor.");
    }

    /**
     * @see Lockable#lock(java.lang.String)
     */
    public void lock(String text) {
        this.locked = true;
        this.lockText = text;
        // TODO: Senden von E-Mails hinzufügen.
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
        return this.lockText;
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

    public String getPseudonym() {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
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

    public boolean isMale() {
        return male;
    }

    public void setIsMale(boolean isMale) {
        this.male = isMale;
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

    public boolean isArtist() {
        return artist;
    }

    public boolean isLabelManager() {
        return labelManager;
    }

    public boolean isAdmin() {
        return admin;
    }

    public LinkedList<User> getFavorizedUsers() {
        return (LinkedList<User>) this.favorizedUsers.clone();
    }

    public LinkedList<Label> getFavorizedLabels() {
        return (LinkedList<Label>) this.favorizedLabels.clone();
    }

    public LinkedList<User> getFollowers() {
        return (LinkedList<User>) this.followers.clone();
    }

    public LinkedList<Label> getManagedLabels() {
        return (LinkedList<Label>) this.managedLabels.clone();
    }

    public LinkedList<Label> getPublishingLabels() {
        return (LinkedList<Label>) this.publishingLabels.clone();
    }

    public LinkedList<News> getNews() {
        return (LinkedList<News>) this.newsList.clone();
    }

    public LinkedList<Application> getApplications() {
        return (LinkedList<Application>) this.sentApplications.clone();
    }

    public LinkedList<Medium> getCreatedMediums() {
        return (LinkedList<Medium>) this.createdMediums.clone();
    }

    public LinkedList<Playlist> getCreatedPlaylists() {
        return (LinkedList<Playlist>) this.createdPlaylists.clone();
    }

    public LinkedList<Comment> getCreatedComments() {
        return (LinkedList<Comment>) this.createdComments.clone();
    }

    public LinkedList<Album> getCreatedAlbums() {
        return (LinkedList<Album>) this.createdAlbums.clone();
    }

    public LinkedList<Playlist> getFavoritePlaylists() {
        return (LinkedList<Playlist>) this.favorizedPlaylists.clone();
    }

    public LinkedList<Album> getFavoriteAlbums() {
        return (LinkedList<Album>) this.favorizedAlbums.clone();
    }

    public LinkedList<Medium> getFavoriteMediums() {
        return (LinkedList<Medium>) this.favorizedMediums.clone();
    }

    public String toString() {
        return this.pseudonym;
    }
}
