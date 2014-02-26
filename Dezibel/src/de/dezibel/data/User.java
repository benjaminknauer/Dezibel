import java.util.Date;
import java.util.LinkedList;

public class User implements Lockable, Lockable {

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

	private LinkedList<User> fan;

	public User(String email, String firstname, String lastname, String passwort) {

	}

	public void follow(User fan) {

	}

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

}
