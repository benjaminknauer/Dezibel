package de.dezibel.data;


import de.dezibel.io.XStreamAdapter;
import java.util.List;
/**
 * This singleton class represents the Database. It holds references to all objects of
 * all classes of <code>de.dezibel.data</code> and manages the creation of such.
 * It saves this data using the <code>XStreamAdaapter</code> class to XML files.
 * @author Henner
 * @inv self.xStreamer != null
 */
public class Database {

	private static Database instance = null;

        /**
         * The XStreamAdapter used to export and import the system's data.
         */
	private XStreamAdapter xStreamer;

        /**
         * All of the system's data is stored in lists here in this order:
         * [ 0  ,   1  ,    2  ,     3   ,   4  ,  5  ,    6   ,   7   ,      8     ,   9  ]
         * [User, Label, Medium, Playlist, Album, News, Comment, Rating, Application, Genre]
         */
        private List[] data;
        
        /**
         * Private constructor called by the first call of <code>getInstance()</code>.
         * This creates the Database object that holds and manages all data while
         * the system is running. It will attempt to import all data from XML
         * files.
         * If there is no saved data to import, it will create empty lists.
         */
	private Database() {

	}

        /**
         * Method to assure that there's a maximum of one instance of <code>Database</code>
         * at all times.
         * @return The only instance of <code>Database</code>
         * @post self.instance != null && ((self.instanceAtPre != null) => self.instanceAtPost == self.instanceAtPre)
         */
	public static Database getInstance() {
            if(instance == null)
                instance = new Database();
            return instance;
	}

        /**
         * Save and export all current data using <code>XStreamAdapter</code>.
         */
	public void save() {

	}
        
        /**
         * Import and load all data from previously exported XML files.
         */
	public void load() {

	}

        /**
         * Makes the Database add a new User with the given information.
         * This will fail and return a proper ErrorCode if there already exists
         * a User registered with the given e-mail address.
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
         * Makes the Database add a new Medium with the given information.
         * The <code>path</code> may be null which will make the new Medium a
         * placeholder Medium.
         * @param titel The medium's title.
         * @param artist The medium's artist.
         * @param path The path to the Medium's file that will be uploaded to
         * the Database. May be null to create a placeholder Medium.
         * @return ErrorCode
         * @pre The <code>title</code> and <code>artist</code> must not be null or empty.
         * @post A new Medium object has been created and added to the database.
         */
	public ErrorCode addMedium(String titel, User artist, String path) {
		return null;
	}

}
