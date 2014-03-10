package de.dezibel.control;

import de.dezibel.data.Database;
import de.dezibel.data.User;
import de.dezibel.data.Comment;
import de.dezibel.data.Playlist;
import de.dezibel.data.Medium;
import de.dezibel.data.Album;
import de.dezibel.data.Application;
import de.dezibel.data.Label;
import de.dezibel.data.News;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Control Class to manage the access and editing of profile data.
 *
 * @author Bastian
 */
public class ProfileControl {

    /**
     * Returns the currently logged in User.
     *
     * @return user who is logged in
     */
    public User getLoggedInUser() {
        return Database.getInstance().getLoggedInUser();
    }

    /**
     * Checks if the logged in user and the user who belongs to the displayed
     * profile is the same.
     *
     * @param user user the profile belongs to
     * @return <code>true</code> if the profile belongs to the given user,
     * <code>false</code> otherwise
     */
    public boolean belongsToLoggedUser(User user) {
        return Database.getInstance().getLoggedInUser().equals(user);
    }

    /**
     *
     * @param user
     * @param title
     * @param filepath
     */
    public void createMedium(User user, String title, String filepath) {
        Database.getInstance().addMedium(title, user, filepath, null, null);
    }

    // Control for profile tab
    /**
     * Returns the first name of the given user.
     *
     * @param user user the profile belongs to
     * @return users first name
     */
    public String getFirstName(User user) {
        if (user.getFirstname() != null) {
            return user.getFirstname();
        } else {
            return "";
        }
    }

    /**
     * Changes the given users first name to the one given by the parameter.
     *
     * @param user user the profile belongs to
     * @param firstName new first name
     */
    public void setFirstName(User user, String firstName) {
        if (belongsToLoggedUser(user)) {
            user.setFirstname(firstName);
        }
    }

    /**
     * Returns the last name of the given user.
     *
     * @param user user the profile belongs to
     * @return users last name
     */
    public String getLastName(User user) {
        if (user.getLastname() != null) {
            return user.getLastname();
        } else {
            return null;
        }
    }

    /**
     * Changes the given users last name to the one given by the parameter.
     *
     * @param user user the profile belongs to
     * @param lastName users new last name
     */
    public void setLastName(User user, String lastName) {
        if (belongsToLoggedUser(user)) {
            user.setLastname(lastName);
        }
    }

    /**
     * Returns the roles of the given user.
     *
     * @param user user the profile belongs to
     * @return users roles
     */
    public String getRole(User user) {
        String role;

        if (user.isArtist() && user.isLabelManager() && user.isAdmin()) {
            role = "Künstler, Labelmanager, Administrator";
        } else if (user.isArtist() && user.isLabelManager()) {
            role = "Künstler, Labelmanager";
        } else if (user.isArtist() && user.isAdmin()) {
            role = "Künstler, Admin";
        } else if (user.isLabelManager() && user.isAdmin()) {
            role = "Labelmanager, Administrator";
        } else if (user.isArtist()) {
            role = "Künstler";
        } else if (user.isLabelManager()) {
            role = "Labelmanager";
        } else if (user.isAdmin()) {
            role = "Admin";
        } else {
            role = "Standard-Benutzer";
        }

        return role;
    }

    /**
     * Returns the pesudonym of the given user.
     *
     * @param user user the profile belongs to
     * @return users pseudonym
     */
    public String getPseudonym(User user) {
        if (user.getPseudonym() != null) {
            return user.getPseudonym();
        } else {
            return "";
        }
    }

    /**
     * Changes the pseudonym of the given user to the one given by the
     * parameter.
     *
     * @param user user the profile belongs to
     * @param pseudonym users new pseudonym
     */
    public void setPseudonym(User user, String pseudonym) {
        if (belongsToLoggedUser(user) && user.isArtist()) {
            user.setPseudonym(pseudonym);
        }
    }

    /**
     * Returns the gender of the given user.
     *
     * @param user user the profile belongs to
     * @return users gender
     */
    public String getGender(User user) {
        String gender;

        if (user.isMale()) {
            gender = "männlich";
        } else {
            gender = "weiblich";
        }

        return gender;
    }

    /**
     * Changes the gender of the given user to the one given by the parameter.
     *
     * @param user user the profile belongs to
     * @param gender users new gender
     */
    public void setGender(User user, String gender) {
        if (belongsToLoggedUser(user)) {
            if (gender.equals("männlich")) {
                user.setIsMale(true);
            }
            if (gender.equals("weiblich")) {
                user.setIsMale(false);
            } else {

            }
        }
    }

    /**
     * Returns the email adress of the given user.
     *
     * @param user user the profile belongs to
     * @return users email adress
     */
    public String getEmail(User user) {
        if (user.getEmail() != null) {
            return user.getEmail();
        } else {
            return "";
        }
    }

    /**
     * Changes the email adress of the given user to the one given by the
     * parameter.
     *
     * @param user user the profile belongs to
     * @param eMail users new email adress
     */
    public void setEmail(User user, String eMail) {
        if (belongsToLoggedUser(user)) {
            user.setEmail(eMail);
        }
    }

    /**
     * Returns the birthdate of the given user.
     *
     * @param user user the profile belongs to
     * @return users birthdate
     */
    public String getBirthDate(User user) {
        if (user.getBirthdate() != null) {
            SimpleDateFormat dFormat = new SimpleDateFormat("dd.MM.yyyy");
            return dFormat.format(user.getBirthdate());
        } else {
            return "";
        }
    }

    /**
     * Changes the given users birthdate to the one given by the parameter.
     *
     * @param user user the profile belongs to
     * @param bDate users new birthdate
     */
    public void setBirthDate(User user, String bDate) {
        if (bDate != "") {
            if (belongsToLoggedUser(user)) {
                Date birthDate;
                try {
                    birthDate = new SimpleDateFormat("dd.MM.yyyy",
                            Locale.GERMAN).parse(bDate);
                    user.setBirthdate(birthDate);
                } catch (ParseException ex) {
                    Logger.getLogger(ProfileControl.class.getName()).log(Level.SEVERE,
                            null, ex);
                }
            }
        }
    }

    /**
     * Returns the city of the given user.
     *
     * @param user user the profile belongs to
     * @return users city
     */
    public String getCity(User user) {
        if (user.getCity() != null) {
            return user.getCity();
        } else {
            return "";
        }
    }

    /**
     * Changes the given users city to the one given by the parameter.
     *
     * @param user user the profile belongs to
     * @param city users new city
     */
    public void setCity(User user, String city) {
        if (belongsToLoggedUser(user)) {
            user.setCity(city);
        }
    }

    /**
     * Returns the country of the given user.
     *
     * @param user user the profile belongs to
     * @return users country
     */
    public String getCountry(User user) {
        if (user.getCountry() != null) {
            return user.getCountry();
        } else {
            return "";
        }
    }

    /**
     * Changes the given users country to the one given by the paramter.
     *
     * @param user user the profile belongs to
     * @param country users new country
     */
    public void setCountry(User user, String country) {
        if (belongsToLoggedUser(user)) {
            user.setCountry(country);
        }
    }

    /**
     * Returns the about-me text of the given user.
     *
     * @param user user the profile belongs to
     * @return users about-me text
     */
    public String getAboutMe(User user) {
        if (user.getDescription() != null) {
            return user.getDescription();
        } else {
            return "";
        }
    }

    /**
     * Changes the given users about-me text to the one given by the parameter.
     *
     * @param user user the profile belongs to
     * @param aboutMe users new about-me text
     */
    public void setAboutMe(User user, String aboutMe) {
        if (belongsToLoggedUser(user)) {
            user.setDescription(aboutMe);
        }
    }

    // Control for follower tab
    /**
     * Returns the given users list of followers.
     *
     * @param user user the profile belongs to
     * @return users list of followers
     */
    public LinkedList<User> getFollowers(User user) {
        return user.getFollowers();
    }

    // Control for news tab
    /**
     * Returns the given users list of news.
     *
     * @param user user the profile belongs to
     * @return users list of news
     */
    public LinkedList<News> getNews(User user) {
        return user.getNews();
    }

    // Control comment tab
    /**
     * Returns the given users list of comments
     *
     * @param user user the profile belongs to
     * @return users list of comments
     */
    public LinkedList<Comment> getCreatedComments(User user) {
        return user.getCreatedComments();
    }

    // Control for upload tab
    /**
     * Creates a new playlist with one medium and a title for the given user.
     *
     * @param medium first medium of the playlist
     * @param user user the profile belongs to
     * @param title title of the playlist
     */
    public void createPlaylist(Medium medium, User user, String title) {
        Database.getInstance().addPlaylist(medium, title, user);
    }

    /**
     * Returns the given users list of created playlists.
     *
     * @param user user the profile belongs to
     * @return users list of created playlists
     */
    public LinkedList<Playlist> getCreatedPlaylists(User user) {
        return user.getCreatedPlaylists();
    }

    /**
     * Removes the given playlist from the given users list of created
     * playlists.
     *
     * @param user user the profile belongs to
     * @param pList playlist which should be removed
     */
    public void removeCreatedPlaylist(User user, Playlist pList) {
        if (belongsToLoggedUser(user)) {
            user.removeCreatedPlaylist(pList);
        }
    }

    /**
     * Renames the given users given playlist to the given title.
     *
     * @param user user the profile belongs to
     * @param pList playlist which should be renamed
     * @param title new title for the playlist
     */
    public void renameCreatedPlaylist(User user, Playlist pList, String title) {
        if (belongsToLoggedUser(user)) {
            pList.setTitle(title);
        }
    }

    /**
     * Returns the given users list of created mediums.
     *
     * @param user user the profile belongs to
     * @return users list of created mediums
     */
    public LinkedList<Medium> getCreatedMediums(User user) {
        return user.getCreatedMediums();
    }

    /**
     * Removes the given medium from the given users list of created mediums.
     *
     * @param user user the profile belongs to
     * @param medium medium which should be removed from users created mediums
     */
    public void removeCreatedMedium(User user, Medium medium) {
        if (belongsToLoggedUser(user)) {
            user.removeCreatedMedium(medium);
        }
    }

    /**
     * Adds medium to the users given playlist.
     *
     * @param user user the profile belongs to
     * @param medium medium which should be added
     * @param pList playlist the medium should be added to
     */
    public void addToPlaylist(User user, Medium medium, Playlist pList) {
        if (belongsToLoggedUser(user)) {
            pList.addMedium(medium);
        }
    }

    /**
     * Adds medium to the users given album.
     *
     * @param user user the profile belongs to
     * @param medium medium which should be added
     * @param album album the medium should be added to
     */
    public void addToAlbum(User user, Medium medium, Album album) {
        if (belongsToLoggedUser(user)) {
            album.addNewMedium(medium);
        }
    }

    /**
     * Creates a new album with a given first medium, a title and da coverpath
     * for the given user.
     *
     * @param user user the profile belongs to
     * @param medium albums first medium
     * @param title albums title
     * @param cover path to albums cover
     */
    public void createAlbum(User user, Medium medium, String title, String cover) {
        Database.getInstance().addAlbum(medium, title, user, cover, true);
    }

    /**
     * Returns the given users list of created albums.
     *
     * @param user user the profile belongs to
     * @return users list of created albums
     */
    public LinkedList<Album> getCreatedAlbums(User user) {
        return user.getCreatedAlbums();
    }

    /**
     * Removes the given album from the given users list of created albums.
     *
     * @param user user the profile belongs to
     * @param medium medium which should be removed from users created albums
     */
    public void removeCreatedAlbum(User user, Medium medium) {
        if (belongsToLoggedUser(user)) {
            user.removeCreatedMedium(medium);
        }
    }
    
    /**
     * Returns the list of labels the user manages
     * 
     * @param user user the profile belongs to
     * @return list of managed labels
     */
    public LinkedList<Label> getManagedLabels(User user){
        return user.getManagedLabels();
    }

    /**
     * Returns the list of labels the user publishes under
     * 
     * @param user user the profile belongs to
     * @return list of publishing labels
     */
    public LinkedList<Label> getPublishingLabels(User user){
        return user.getPublishingLabels();
    }    
    
    
    // Control for favorits tab
    /**
     * Returns the given users list of favorized users.
     *
     * @param user user the profile belongs to
     * @return users list of favorite users
     */
    public LinkedList<User> getFavorizedUsers(User user) {
        return user.getFavorizedUsers();
    }

    /**
     * Adds the user of the currently displayed profile to the logged in users
     * favorite users.
     *
     * @param user new favorite user
     */
    public void addToFavoriteUsers(User user) {
        if (!(belongsToLoggedUser(user))) {
            Database.getInstance().getLoggedInUser().addFavoriteUser(user);
        }
    }

    /**
     * Removes a user from the logged in users list of favorite users.
     * @param user former favorite user
     */
    public void removeFavoriteUser(User user){
        if(!(belongsToLoggedUser(user))) {
            Database.getInstance().getLoggedInUser().removeFavoriteUser(user);
        }
    }
    
    /**
     * Returns the given users list of favorized playlists.
     *
     * @param user user the profile belongs to
     * @return users list of favorite playlists
     */
    public LinkedList<Playlist> getFavorizedPlaylists(User user) {
        return user.getFavoritePlaylists();
    }

    /**
     * Removes the given playlist from the given users list of favorized
     * playlists.
     *
     * @param user user the profile belongs to
     * @param pList playlist which should be removed from favorite playlists
     */
    public void removeFavorizedPlaylist(User user, Playlist pList) {
        if (belongsToLoggedUser(user)) {
            user.removeFavoritePlaylist(pList);
        }
    }

    /**
     * Returns the given users list of favorized albums.
     *
     * @param user user the profile belongs to
     * @return users list of favorite albums
     */
    public LinkedList<Album> getFavorizedAlbums(User user) {
        return user.getFavoriteAlbums();
    }

    /**
     * Removes the given album from the given users list of favorized albums.
     *
     * @param user user the profile belongs to
     * @param album album which should be removed from users favorite albums
     */
    public void removeFavorizedAlbum(User user, Album album) {
        if (belongsToLoggedUser(user)) {
            user.removeFavoriteAlbum(album);
        }
    }

    /**
     * Returns the given users list of favorized mediums.
     *
     * @param user user the profile belongs to
     * @return users list of favorite mediums
     */
    public LinkedList<Medium> getFavorizedMediums(User user) {
        return user.getFavoriteMediums();
    }

    /**
     * Removes the given medium from the given users list of favorized mediums.
     *
     * @param user user the profile belongs to
     * @param medium medium which should be removed from users favorite mediums
     */
    public void removeFavorizedMedium(User user, Medium medium) {
        if (belongsToLoggedUser(user)) {
            user.removeFavoriteMedium(medium);
        }
    }
    
    public LinkedList<Application> getApplications(User user) {
        return user.getApplications();
    }
    
    /**
     * Checks if the given user is locked.
     * 
     * @param user user the profile belongs to
     * @return true if the user is locked, false otherwise
     */
    public boolean isLocked(User user){
        return user.isLocked();
    }
    
    /**
     * Changes the given users password to the one given by the parameter.
     *
     * @param user user the profile belongs to
     * @param password new password
     */
    public void setPassword(User user, String password) {
        if (belongsToLoggedUser(user)) {
            HashGenerator hg = new HashGenerator();
            password = hg.hash(password);
            user.setPassword(password);
        }
    }
    
    /**
     * Checks if the given password of the user is correct.
     * 
     * @param user user the profile belongs to
     * @param password password of the user
     * @return true if the user is locked, false otherwise
     */
    public boolean checkPassword(User user, String password){
        HashGenerator hg = new HashGenerator();
        password = hg.hash(password);
        return user.getPassword().equals(password);
    }
    
}
