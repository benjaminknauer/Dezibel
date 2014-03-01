package de.dezibel.control;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The HashGenerator generates a hash code of a fixed length out of a given
 * String with variable length. To provide more safty the hash methode uses a
 * salt.
 *
 * @author Tobias
 */
public class HashGenerator {

    //fixed salt, could be variable, maybe the email or name of the user
    private final String salt = "SoPra-Social-Media-Group3B-DEZIBEL!";

    /**
     * Hashes and salts the given plaintext and returns it
     *
     * @param plaintext The plaintext string to hash
     * @return The salted and hashed String, or if the hash function fails an
     * empty String
     */
    public String hash(String plaintext) {
        String saltedPlaintext = plaintext + salt;
        try {
             //init the algorithm using MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //hash
            md.update(saltedPlaintext.getBytes(), 0, saltedPlaintext.length());
            BigInteger bigInt = new BigInteger(1, md.digest());
            //formatting and return
            return String.format("%1$032x", bigInt);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
