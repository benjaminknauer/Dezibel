
import de.dezibel.io.XStreamAdapter;

public class Database {

	private static Database instance = null;

	private XStreamAdapter xStreamer;

	private Database() {

	}

	public static Database getInstance() {
		return null;
	}

	public void save() {

	}

	public void load() {

	}

	public boolean addUser(String email, String firstname, String lastname, String passwort) {
		return false;
	}

	public boolean addMedium(String titel, User artist, String path) {
		return false;
	}

}
