package de.dezibel.data;

import de.dezibel.ErrorCode;
import de.dezibel.io.XStreamAdapter;
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

        // No data loaded? Create empty lists.
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
     * @return ErrorCode
     * @pre All the parameters must not be null or the empty String.
     * @post A new User object has been created and added to the database.
     */
    public ErrorCode addUser(String email, String firstname, String lastname, String passwort) {
        return null;
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
        for (int i = 0; i < this.listCount; i++) {
            this.data[i] = new LinkedList();
        }

        // Create default administrator.
        this.addUser("admin@dezibel.de", "admin", "admin", "admin");
        this.getUsers().get(0).promoteToAdmin();

        // Create topGenre
        this.addGenre(topGenreName, null);
    }

    public LinkedList<User> getUsers() {
        return this.data[0];
    }

    public LinkedList<Label> getLabels() {
        return this.data[1];
    }

    public LinkedList<Medium> getMedia() {
        return this.data[2];
    }
    
    public LinkedList<Playlist> getPlaylists() {
        return this.data[3];
    }
    
    public LinkedList<Album> getAlbums() {
        return this.data[4];
    }
    
    public LinkedList<News> getNews() {
        return this.data[5];
    }
    
    public LinkedList<Comment> getComments() {
        return this.data[6];
    }
    
    public LinkedList<Rating> getRatings() {
        return this.data[7];
    }
    
    public LinkedList<Application> getApplications() {
        return this.data[8];
    }

    public LinkedList<Genre> getGenres() {
        return this.data[9];
    }

}
