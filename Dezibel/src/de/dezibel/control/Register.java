package de.dezibel.control;

import de.dezibel.ErrorCode;
import de.dezibel.data.Database;

/**
 * Controls the registration process.
 * @author Richard
 */
public class Register {
    
    /**
     * Registers a new user with the given parameters. The 
     * @param email The email of the new user
     * @param password The password (not encrypted)
     * @param firstname The firstname of the new user
     * @param lastname The lastname of the new user
     * @return Returns <p>ErrorCode.SUCCES</p> if no error occured or
     *          <p>ErrorCode.EMAIL_ALREADY_IN_USE</p> if the email is already
     *          in use
     */
    public ErrorCode register(String email, String password, String firstname,
            String lastname) {
        // TODO Registerpanel um Angabe für das Geschlecht erweitern
        String encryptedPasswort = new HashGenerator().hash(password);
        return Database.getInstance().addUser(email, firstname, lastname, 
                encryptedPasswort, null, "", "", true);
    }
    
}
