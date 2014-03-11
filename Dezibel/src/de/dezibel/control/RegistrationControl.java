package de.dezibel.control;

import de.dezibel.data.Database;
import de.dezibel.data.User;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.*;

/**
 * Control class to manage the registration
 *
 * @author Tobias
 */
public class RegistrationControl {

    /**
     * Checks if the given mail is already in use
     * @param mail The mail to check
     * @return true, if the mail is already in use, else false
     */
    public boolean checkIfMailAlreadyInUse(String mail) {
        LinkedList<User> users = Database.getInstance().getUsers();
        Iterator<User> it = users.iterator();
        while (it.hasNext()) {
            if (it.next().getEmail().equals(mail)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if the given mail contains an @ sign that is not located
     * at the beginning or ending of the mail adress
     * @param mail The mail to check
     * @return true, if the mail is already in use, else false
     */
    public boolean checkIfMailValid(String mail) {
        Pattern p = Pattern.compile(".+(@).+");
	Matcher m = p.matcher(mail);
        return m.find();
    }

    /**
     * Adds the user to Database
     * @param pw The password
     * @param mail The mail
     * @param firstname The firstname
     * @param lastname The lastname
     */
    public void addUser(String pw, String mail, String firstname, String lastname) {
        HashGenerator hg = new HashGenerator();
        pw = hg.hash(pw);
        Database.getInstance().addUser(mail, firstname, lastname,
                pw, null, null, null, true);
    }
}
