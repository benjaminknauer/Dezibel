package de.dezibel.control;

import de.dezibel.data.User;
import java.util.Comparator;

/**
 * Comparator for the name of users with lastname as first key and
 * firstname as second
 * 
 * @author Tobias
 */
public class UserNameComparator implements Comparator<User> {
    
    /**
     * Compares the two given users
     * @param user1 User
     * @param user2 User
     * @return 0, if the two user names are the same.
     *          postive if user1>user2.
     *          negative if user1<user2.
     */
    @Override
    public int compare(User user1, User user2) {
        if(user1 == null && user2 == null)
            return 0;
        else if(user1 == null)
            return -1;
        else if(user2 == null)
            return 1;
        if(user1.getLastname().compareTo(user2.getLastname()) == 0)
            return user1.getFirstname().compareTo(user2.getFirstname());
        return user1.getLastname().compareTo(user2.getLastname());
    }
}