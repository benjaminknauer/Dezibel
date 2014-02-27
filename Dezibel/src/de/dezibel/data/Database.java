package de.dezibel.data;

import de.dezibel.ErrorCode;
import de.dezibel.io.XStreamAdapter;
import java.util.Date;
import java.util.LinkedList;

/**
 * This singleton class represents the Database. It holds references to all
 * objects of all classes of <code>de.dezibel.data</code> and manages the
 * creation of such. It saves this data using the <code>XStreamAdaapter</code>
 * class to XML files.
 *
 * @author Henner
 * @inv self.xStreamer != null
 */
//TODO Implementier mich!
public class Database {

    private static Database instance = null;

    /**
     * The <code>XStreamAdapter</code> used to export and import the system's
     * data.
     */
    private XStreamAdapter xStreamer;

    /**
     * All of the system's data is stored in lists here in this order: [ 0 , 1 ,
     * 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 ]<br>
     * [User, Label, Medium, Playlist, Album, News, Comment, Rating,
     * Application, Genre]
     */
    private LinkedList[] data;
    /**
     * The amount of Lists in data.
     */
    private int listCount = 10;

    private LinkedList<User> users;
    private LinkedList<Label> labels;
    private LinkedList<Medium> media;
    private LinkedList<Playlist> playlists;
    private LinkedList<Album> albums;
    private LinkedList<News> news;
    private LinkedList<Comment> comments;
    private LinkedList<Rating> ratings;
    private LinkedList<Application> applications;
    private LinkedList<Genre> genres;

    private String topGenreName = "topGenre";

    /**
     * Private constructor called by the first call of
     * <code>getInstance()</code>. This creates the <code>Database</code> object
     * that holds and manages all data while the system is running. It will
     * attempt to import all data from XML files. If there is no saved data to
     * import, it will create empty lists.
     */
    private Database() {
        load();
        // No data loaded? Create empty lists and add the default stuff.
        if (data == null) {
            initializeDatabase();
        }
    }

    /**
     * Method to assure that there's a maximum of one instance of
     * <code>Database</code> at all times.
     *
     * @return The only instance of <code>Database</code>
     * @post self.instance != null && ((self.instanceAtPre != null) =>
     * self.instanceAtPost == self.instanceAtPre)
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * Save and export all current data using <code>XStreamAdapter</code>.
     */
    public void save() {
        xStreamer.save(data);
    }

    /**
     * Import and load all data from previously exported XML files.
     * <code>data</code> will be null if there were no XML files to load.
     *
     * @post If there is no data to load then (self.data == null)
     */
    public void load() {
        data = xStreamer.load();
        if (data == null) {
            return;
        }
        users = data[0];
        labels = data[1];
        media = data[2];
        playlists = data[3];
        albums = data[4];
        news = data[5];
        comments = data[6];
        ratings = data[7];
        applications = data[8];
        genres = data[9];
    }

    /**
     * Makes the Database add a new User with the given information. This will
     * fail and return a proper ErrorCode if there already exists a User
     * registered with the given e-mail address.
     *
     * @param email The e-mail the new User will be associated to.
     * @param firstname The first name of the new User.
     * @param lastname The last name of the new User.
     * @param passwort The password of the new User.
     * @param isMale True if user is male, false if female. No trannies here,
     * sorry.
     * @return ErrorCode
     * @pre All the parameters must not be null or the empty String.
     * @post A new User object has been created and added to the database.
     */
    public ErrorCode addUser(String email, String firstname, String lastname, String passwort, Date birthdate, String city, String country, boolean isMale) {
        for (User curUser : this.getUsers()) {
            if (curUser.getEmail().equals(email)) {
                return ErrorCode.EMAIL_ALREADY_IN_USE;
            }
        }

        User u = new User(email, firstname, lastname, passwort, isMale);
        u.setBirthdate(birthdate);
        u.setCity(city);
        u.setCountry(country);

        users.add(u);
        return ErrorCode.SUCCESS;
    }

    void removeApplication(Application application) {
         this.applications.remove(application);
    }

    public void removeLabel(Label label) {
         this.labels.remove(label);
    }

    public void removeNews(News news) {
        this.news.remove(news);
    }

    public void removeComment(Comment comment) {
         this.comments.remove(comment);
    }

    public void removePlaylist(Playlist playlist) {
         this.playlists.remove(playlist);
    }

    /**
     * Makes the Database add a new Medium with the given information. The
     * <code>path</code> may be null which will make the new Medium a
     * placeholder Medium.
     *
     * @param titel The medium's title.
     * @param artist The medium's artist.
     * @param path The path to the Medium's file that will be uploaded to the
     * Database. May be null to create a placeholder Medium.
     * @return ErrorCode
     * @pre The <code>title</code> and <code>artist</code> must not be null or
     * empty.
     * @post A new Medium object has been created and added to the database.
     */
    public ErrorCode addMedium(String titel, User artist, String path) {
        return null;
    }

    /**
     * Creates a new genre specified by <code>name</code> and
     * <code>superGenre</code>. If <code>superGenre</code> is null, the new
     * genre's super genre will be set to the top genre.
     *
     * @param name The name of the new genre. Must be unique.
     * @param superGenre The super genre of the new genre. May be null.
     * @return ErrorCode
     * @pre There must not be a genre with the same name as <code>name</code>
     * @post A new Genre object has been created and added to the database
     */
    public ErrorCode addGenre(String name, Genre superGenre) {
        // Does a genre with this name already exist?
        for (Genre g : this.getGenres()) {
            if (name.equals(g.getName())) {
                return ErrorCode.GENRE_NAME_DUPLICATE;
            }
        }

        // No superGenre specified. Set superGenre to the topGenre.
        if (superGenre == null) // Special case for the initialization of the db and creating the topGenre.
        {
            if (!name.equals(topGenreName)) {
                superGenre = this.getGenres().get(0);
            }
        }

        this.getGenres().add(new Genre(name, superGenre));

        return ErrorCode.SUCCESS;
    }
//TODO Initialisierung vervollstaendigen?

    private void initializeDatabase() {
        data = new LinkedList[this.listCount];
        users = new LinkedList<>();
        data[0] = users;
        labels = new LinkedList<>();
        data[1] = labels;
        media = new LinkedList<>();
        data[2] = media;
        playlists = new LinkedList<>();
        data[3] = playlists;
        albums = new LinkedList<>();
        data[4] = albums;
        news = new LinkedList<>();
        data[5] = news;
        comments = new LinkedList<>();
        data[6] = comments;
        ratings = new LinkedList<>();
        data[7] = ratings;
        applications = new LinkedList<>();
        data[8] = applications;
        genres = new LinkedList<>();
        data[9] = genres;

        // Create default administrator.
        this.addUser("admin@dezibel.de", "admin", "admin", "admin", new Date(), null, null, (Math.random() < 0.5));
        this.getUsers().get(0).promoteToAdmin();

        // Create topGenre
        this.addGenre(topGenreName, null);
    }

    public LinkedList<User> getUsers() {
        return (LinkedList<User>) this.data[0].clone();
    }

    public LinkedList<Label> getLabels() {
        return (LinkedList<Label>) this.data[1].clone();
    }

    public LinkedList<Medium> getMedia() {
        return (LinkedList<Medium>) this.data[2].clone();
    }

    public LinkedList<Playlist> getPlaylists() {
        return (LinkedList<Playlist>) this.data[3].clone();
    }

    public LinkedList<Album> getAlbums() {
        return (LinkedList<Album>) this.data[4].clone();
    }

    public LinkedList<News> getNews() {
        return (LinkedList<News>) this.data[5].clone();
    }

    public LinkedList<Comment> getComments() {
        return (LinkedList<Comment>) this.data[6].clone();
    }

    public LinkedList<Rating> getRatings() {
        return (LinkedList<Rating>) this.data[7].clone();
    }

    public LinkedList<Application> getApplications() {
        return (LinkedList<Application>) this.data[8].clone();
    }

    public LinkedList<Genre> getGenres() {
        return (LinkedList<Genre>) this.data[9].clone();
    }
}
