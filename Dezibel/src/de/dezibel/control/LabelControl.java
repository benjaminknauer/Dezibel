/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dezibel.control;

import de.dezibel.data.Album;
import de.dezibel.data.Application;
import de.dezibel.data.Database;
import de.dezibel.data.Label;
import de.dezibel.data.Medium;
import de.dezibel.data.News;
import de.dezibel.data.User;
import java.util.LinkedList;

/**
 * Manages label functions.
 * @author Bastian, Alexander
 */
public class LabelControl {
    
    /**
     * Creates a new Label with user as its first labelmanager.
     * 
     * @param user first labelmanager
     * @param name labels name
     */
    public void createLabel(User user, String name){
        Database.getInstance().addLabel(user, name);
    }
    
    /**
     * Promotes the submitted user to label manager.
     * @param user The user to promote
     */
    public void promoteUserToLabelManager(User user) {
            user.promoteToLabelManager();
    }
    

    /**
     * Returns the currently logged in User.
     *
     * @return user who is logged in
     */
    public User getLoggedInUser() {
        return Database.getInstance().getLoggedInUser();
    }

    /**
     * Checks if the logged in user is a labelmanager of the given label.
     *
     * @param label label the profile belongs to
     * @return <code>true</code> if the profile belongs to the given user,
     * <code>false</code> otherwise
     */
    public boolean belongsToLoggedUser(Label label) {
        return label.getLabelManagers().contains(Database.getInstance().getLoggedInUser());
    }


    // Control for profile tab
    /**
     * Returns the name of the given label.
     *
     * @param label user the profile belongs to
     * @return label name
     */
    public String getName(Label label) {
        if (label.getName() != null) {
            return label.getName();
        } else {
            return "";
        }
    }

    /**
     * Returns the company details of the given label.
     *
     * @param label label the profile belongs to
     * @return labels company details
     */
    public String getCompanyDetails(Label label) {
        if (label.getCompanyDetails() != null) {
            return label.getCompanyDetails();
        } else {
            return null;
        }
    }

    /**
     * Changes the given labels company details to the one given by the parameter.
     *
     * @param label label the profile belongs to
     * @param companyDetails labels new company details
     */
    public void setCompanyDetails(Label label, String companyDetails) {
        if (belongsToLoggedUser(label)) {
            label.setCompanyDetails(companyDetails);
        }
    }

    // Control for follower tab
    /**
     * Returns the given labels list of followers.
     *
     * @param label label the profile belongs to
     * @return labels list of followers
     */
    public LinkedList<User> getFollowers(Label label) {
        return label.getFollowers();
    }
    
    /**
     * Adds the logged in user to the given labels list followers.
     *
     * @param label label the profile belongs to
     */
    public void addFollower(Label label) {
        getLoggedInUser().addFavoriteLabel(label);
    }
    
    /**
     * Removes logged in user from the given labels list followers.
     *
     * @param label label the profile belongs to
     */
    public void removeFollower(Label label) {
        getLoggedInUser().removeFavoriteLabel(label);
    }

    // Control for news tab
    /**
     * Returns the given labels list of news.
     *
     * @param label label the profile belongs to
     * @return labels list of news
     */
    public LinkedList<News> getNews(Label label) {
        return label.getNews();
    }

    // Control for upload tab

    /**
     * Returns the given labels list of associated mediums.
     *
     * @param label label the profile belongs to
     * @return labels list of associated mediums
     */
    public LinkedList<Medium> getAssociatedMediums(Label label) {
        LinkedList<Medium> m = new LinkedList<>();
        for (User currentArtist: label.getArtists()){
            for (Medium currentMedium: currentArtist.getCreatedMediums()){
                if (currentMedium.getLabel()!= null && currentMedium.getLabel().equals(label)){
                    m.add(currentMedium);
                }
            }
            
        }
        return m;
    }

    /**
     * Adds medium to the labels given album.
     *
     * @param label label the profile belongs to
     * @param medium medium which should be added
     * @param album album the medium should be added to
     */
    public void addToAlbum(Label label, Medium medium, Album album) {
        if (belongsToLoggedUser(label)) {
            album.addNewMedium(medium);
        }
    }

    /**
     * Creates a new album with a given first medium, a title and a coverpath
     * for the given label.
     *
     * @param label label the profile belongs to
     * @param medium albums first medium
     * @param title albums title
     * @param cover path to albums cover
     */
    public void createAlbum(Label label, Medium medium, String title, String cover) {
        Database.getInstance().addAlbum(medium, title, label, cover);
    }

    /**
     * Returns the given users list of created albums.
     *
     * @param label label the profile belongs to
     * @return labels list of created albums
     */
    public LinkedList<Album> getCreatedAlbums(Label label) {
        return label.getAlbums();
    }

    /**
     * Removes the given album from the given labels list of created albums.
     *
     * @param label label the profile belongs to
     * @param album medium which should be removed from users created albums
     */
    public void removeCreatedAlbum(Label label, Album album) {
        if (belongsToLoggedUser(label)) {
            label.removeAlbum(album);
        }
    }
    
    /**
     * Returns the list of labels the user manages
     * 
     * @param label label the profile belongs to
     * @return list of managers
     */
    public LinkedList<User> getManagers(Label label){
        return label.getLabelManagers();
    }

    /**
     * Returns the list of artists publishing for this label.
     * 
     * @param label label the profile belongs to
     * @return list of artists publishing for this label
     */
    public LinkedList<User> getArtists(Label label){
        return label.getArtists();
    }    
    
    //Control for applications tab
    
    /**
     * Returns the given labels list of applications.
     *
     * @param label label the profile belongs to
     * @return labels list of applications
     */
    public LinkedList<Application> getApplications(Label label) {
        return label.getApplications();
    }
    
    /**
     * Checks if the given label is locked.
     * 
     * @param label label the profile belongs to
     * @return true if the label is locked, false otherwise
     */
    public boolean isLocked(Label label){
        return label.isLocked();
    }

    
}
