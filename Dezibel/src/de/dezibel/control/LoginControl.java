package de.dezibel.control;

import de.dezibel.data.Database;
import de.dezibel.data.User;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Control class to manage the login
 * 
 * @author Tobias
 */
public class LoginControl {
    
    private User user;
    
    /**
     * Checks if a user with the given mail already exists
     * @param mail The mail to check
     * @return true, if the mail is already in use, else false
     */
    public boolean checkIfMailExists(String mail) {
        LinkedList<User> users = Database.getInstance().getUsers();
        Iterator<User> it = users.iterator();
        while(it.hasNext()) {
            User tmp = it.next();
            if(tmp.getEmail().equals(mail)) {
                user = tmp;
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if the given password for the specific user is correct
     * @param pw The password to check
     * @return true, if the password is right, else false
     */
    public boolean checkPassword(String pw) {
        HashGenerator hg = new HashGenerator();
        pw = hg.hash(pw);
        return user.getPassword().equals(pw);
    }
    
    /**
     * Sets the currently logged user 
     */
    public void markLoggedInUser() {
        Database.getInstance().setLoggedInUser(user);
    }
    
}
