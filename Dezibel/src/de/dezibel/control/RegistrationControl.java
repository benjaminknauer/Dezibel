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
     * Checks if the given mail is valid
     * @param mail The mail to check
     * @return true, if the mail is valid, else false
     */
    public boolean checkIfMailValid(String mail) {
        Pattern p;
        p = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
	Matcher m = p.matcher(mail);
        return m.find();
    }
    
    /**
     * Checks if the given names are valid
     * @param firstName The firstName to check
     * @param lastName The lastName to check
     * @return true, if the names are valid, else false
     */
    public boolean checkIfNamesValid(String firstName, String lastName) {
        Pattern p;
        p = Pattern.compile("^([A-Za-z\\s]+){3,20}$");
	Matcher m = p.matcher(firstName);
        Matcher mm = p.matcher(lastName);
        return m.find() && mm.find();
    }
    
    /**
     * Checks if the given password is valid
     * @param password The password to check
     * @return true, if the names are valid, else false
     */
    public boolean checkIfPWValid(String password) {
        boolean number = false;
        boolean letter = false;
        if (password.length() > 5){
            for (int i = 0; i < password.length(); i++){
                if (Character.isLetter(password.charAt(i))){
                    letter = true;
                }
                if (Character.isDigit(password.charAt(i))){
                    number = true;
                }
            }
            return letter && number;
        } else {
            return false;
        }
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
