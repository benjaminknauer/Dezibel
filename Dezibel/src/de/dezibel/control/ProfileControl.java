package de.dezibel.control;

import de.dezibel.data.Database;
import de.dezibel.data.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Control Class to manage the access and editing of profile data.
 * 
 * @author Bastian
 */
public class ProfileControl {
    
    Database db;
    
    // Display and edit profile data
    public boolean belongsToLoggedUser(User user){
        return db.getLoggedInUser().equals(user);
    }
    
    public String getFirstName(User user){
        return user.getFirstname();
    }
    
    public void setFirstName(User user, String firstName){
        if(belongsToLoggedUser(user)){
            user.setFirstname(firstName);
        }
    }
    
    public String getLastName(User user){
        return user.getLastname();
    }
    
    public void setLastName(User user, String lastName){
        if(belongsToLoggedUser(user)){
            user.setLastname(lastName);
        }
    }
    
    public String getRole(User user){
        String role;
        
        if(user.isArtist() && user.isLabelManager() && user.isAdmin()){
            role = "Künstler, Labelmanager, Administrator";
        }
        
        else if(user.isArtist() && user.isLabelManager()){
            role = "Künstler, Labelmanager";
        }
        else if(user.isArtist() && user.isAdmin()){
            role = "Künstler, Admin";
        }
        else if(user.isLabelManager() && user.isAdmin()) {
            role = "Labelmanager, Administrator";
        }
        
        else if(user.isArtist()){
            role = "Künstler";
        }
        else if(user.isLabelManager()){
            role = "Labelmanager";
        }
        else if(user.isAdmin()){
            role = "Admin";
        }
        else {
            role = "Standard-Benutzer";
        }
        
        return role;
    }
    
    public String getPseudonym(User user){
        return user.getPseudonym();
    }
    
    public void setPseudonym(User user, String pseudonym){
        if(belongsToLoggedUser(user)){
            user.setPseudonym(pseudonym);
        }
    }
    
    public String getGender(User user){
        String gender;
        
        if(user.isMale()){
            gender = "männlich";
        }
        else {
            gender = "weiblich";
        }
        
        return gender;
    }
    
    public void setGender(User user, String gender){
        if(belongsToLoggedUser(user)){
            if(gender.equals("männlich")){
                user.setIsMale(true);
            }
            if(gender.equals("weiblich")){
            user.setIsMale(false);
           }
            else{
                
            }
        }
    }
    
    public String getEmail(User user){
        return user.getEmail();
    }
    
    public void setEmail(User user, String eMail){
        if(belongsToLoggedUser(user)){
            user.setEmail(eMail);
        }
    }
    
    public String getBirthDate(User user){
        return user.getBirthdate().toString();
    }
    
    public void setBirthDate(User user, String bDate){
        if(belongsToLoggedUser(user)){
            Date birthDate;
            try {
                birthDate = new SimpleDateFormat("d MMMM, yyyy", 
                        Locale.GERMAN).parse(bDate);
                user.setBirthdate(birthDate);
            } catch (ParseException ex) {
                Logger.getLogger(ProfileControl.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
    }
    
    public String getCity(User user){
        return user.getCity();
    }
    
    public void setCity(User user, String city){
        if(belongsToLoggedUser(user)){
            user.setCity(city);
        }
    }
    
    public String getCountry(User user){
        return user.getCountry();
    }
    
    public void setCountry(User user, String country){
        if(belongsToLoggedUser(user)){
            user.setCountry(country);
        }
    }
    
    
}
