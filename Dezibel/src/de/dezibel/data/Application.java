package de.dezibel.data;

import java.util.Date;

/**
 * This class represents the application from an artist for a label and vice versa.
 * @author Henner
 * @inv self.text != null && self.creationDate != null &&  self.label != null && self.artist != null
 */
public class Application {

    private boolean fromArtist;

    private String text;

    private Date creationDate;

    private Label label;

    private User artist;

    /**
     * Creates a new application object representing an application from an 
     * artist for a label if <p>fromArtist</p> is set to true or the other way
     * round if it's set to false.
     * @param fromArtist Set to true if the applicant is an artist, otherwise false.
     * @param text The message the recipient will be shown.
     * @param artist The artist who applys or is applied for.
     * @param label The label that applys or is applied for.
     */
    public Application(boolean fromArtist, String text, User artist, Label label) {
        this.fromArtist = fromArtist;
        this.text = text;
        this.label = label;
        this.artist = artist;

        this.creationDate = new Date();
    }

    /**
     * Accept the application.
     */
    public void accept() {
        
    }

    /**
     * Decline the application.
     */
    public void decline() {

    }

    /**
     * Returns true if this application was written by an artist and false otherwise.
     * @return True if the applicant is an artist, false if the applicant is a label.
     * @pre true
     * @post self.fromArtist = self.fromArtistAtPre
     */
    public boolean isFromArtist() {
        return this.fromArtist;
    }

    public String getText() {
        return this.text;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }
    
    public Label getLabel() {
        return this.label;
    }

    public User getUser() {
        return this.artist;
    }
}
