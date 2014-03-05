package de.dezibel.gui;

import de.dezibel.control.ProfileControl;
import de.dezibel.data.Label;
import de.dezibel.data.User;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.tree.DefaultTreeCellEditor;

public class ProfilPanel extends DragablePanel {

    private static final long serialVersionUID = 1L;
    private ProfileControl controler;
    private User currentUser;
    
    private JTabbedPane tabPanel;
    private JPanel pnProfile;
    private JPanel pnUploads;
    private JPanel pnFavorites;
    private JPanel pnFollower;
    private JPanel pnComments;
    private JPanel pnNews;
    private GridBagLayout gbl;
    private GridBagConstraints gbc;
    
    private JTextField tfFirstName;
    private JTextField tfLastName;
    private JTextField tfRole;
    private JTextField tfPseudonym;
    private JComboBox<String> tfGender;
    private JTextField tfEmail;
    private JTextField tfBirthDate;
    private JTextField tfCity;
    private JTextField tfCountry;
    private JTextField tfAboutMe;
    
    /**
     * Constructor of the ProfilPanel class.
     * @param parent 
     */
    public ProfilPanel(DezibelPanel parent) {
        super(parent);
        this.createComponents();
        
        // Create Layout
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);
        this.add(tabPanel, BorderLayout.CENTER);
        
        this.controler = new ProfileControl();
        this.currentUser = controler.getLoggedInUser();
    }

    /**
     * Method to create tabs.
     */
    private void createComponents() {
        gbl = new GridBagLayout();
        gbc = new GridBagConstraints();
        
        this.tabPanel = new JTabbedPane();

        this.pnProfile = new JPanel();
        this.createProfileComponents();
        tabPanel.addTab("Profil", null, pnProfile);

        this.pnUploads = new JPanel();
        tabPanel.addTab("Uploads", null, pnUploads);
        this.pnFavorites = new JPanel();
        tabPanel.addTab("Favoriten", null, pnFavorites);
        this.pnFollower = new JPanel();
        tabPanel.addTab("Follower", null, pnFollower);
        this.pnComments = new JPanel();
        tabPanel.addTab("Kommentare", null, pnComments);
        this.pnNews = new JPanel();
        tabPanel.addTab("Neuigkeiten", null, pnNews);
    }

    /**
     * This method creates all components in the profile tab.
     */
    private void createProfileComponents() {
        pnProfile.setLayout(gbl);
        
        JLabel lbFirstName = new JLabel("Vorname:");
        JLabel lbLastName = new JLabel("Nachname:");
        JLabel lbRole = new JLabel("Rolle:");
        JLabel lbPseudonym = new JLabel("Pseudonym:");
        JLabel lbGender = new JLabel("Geschlecht:");
        JLabel lbEmail = new JLabel("Email:");
        JLabel lbBirthDate = new JLabel("Geburtsdatum:");
        JLabel lbCity = new JLabel("Stadt:");
        JLabel lbCountry = new JLabel("Land:");
        JLabel lbAboutMe = new JLabel("Über mich:");
        
        tfFirstName = new JTextField(25);
        // tfFirstName.setText(controler.getFirstName(currentUser));
        tfLastName = new JTextField(25);       
        // tfLastName.setText(controler.getLastName(currentUser));  
        tfRole = new JTextField(25);       
        //tfRole.setText(controler.getRole(currentUser));
        tfPseudonym = new JTextField(25);       
        //tfPseudonym.setText(controler.getPseudonym(currentUser));
        String[] items = {"männlich", "weiblich"}; 
        tfGender = new JComboBox<>(items);
        //tfGender.setText(controler.getGender(currentUser));
        tfEmail = new JTextField(25);       
        //tfEmail.setText(controler.getEmail(currentUser));
        tfBirthDate = new JTextField(25);       
        //tfBirthDate.setText(controler.getBirthDate(currentUser));
        tfCity = new JTextField(25);       
        //tfCity.setText(controler.getCity(currentUser));
        tfCountry = new JTextField(25);       
        //tfCountry.setText(controler.getCountry(currentUser));
        tfAboutMe = new JTextField(25);
        //tfAboutMe.setText(controler.getAboutMe(currentUser));
        
        JButton btnEdit = new JButton("Bearbeiten");
        btnEdit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(tfFirstName.isEnabled()){
//                controler.setFirstName(currentUser, tfFirstName.getText());
//                controler.setLastName(currentUser, tfLastName.getText());
//                controler.setPseudonym(currentUser, tfPseudonym.getText());
//                controler.setGender(currentUser, tfGender.getSelectedItem().toString());
//                controler.setEmail(currentUser, tfEmail.getText());
//                controler.setBirthDate(currentUser, tfBirthDate.getText());
//                controler.setCity(currentUser, tfCity.getText());
//                controler.setCountry(currentUser, tfCountry.getText());
//                controler.setAboutMe(currentUser, tfAboutMe.getText());
                setProfileTextfieldsEditable(false);
                } else{
                    setProfileTextfieldsEditable(true);
                }
                
            }
        });
        
        addComponent(pnProfile, gbl, lbFirstName, 0, 0);
        addComponent(pnProfile, gbl, tfFirstName, 1, 0);
        addComponent(pnProfile, gbl, lbLastName, 0, 1);
        addComponent(pnProfile, gbl, tfLastName, 1, 1);
        addComponent(pnProfile, gbl, lbRole, 0, 2);
        addComponent(pnProfile, gbl, tfRole, 1, 2);
        addComponent(pnProfile, gbl, lbPseudonym, 0, 3);
        addComponent(pnProfile, gbl, tfPseudonym, 1, 3);
        addComponent(pnProfile, gbl, lbGender, 0, 4);
        addComponent(pnProfile, gbl, tfGender, 1, 4);
        addComponent(pnProfile, gbl, lbEmail, 0, 5);
        addComponent(pnProfile, gbl, tfEmail, 1, 5);
        addComponent(pnProfile, gbl, lbBirthDate, 0, 6);
        addComponent(pnProfile, gbl, tfBirthDate, 1, 6);
        addComponent(pnProfile, gbl, lbCity, 0, 7);
        addComponent(pnProfile, gbl, tfCity, 1, 7);
        addComponent(pnProfile, gbl, lbCountry, 0, 8);
        addComponent(pnProfile, gbl, tfCountry, 1, 8);
        addComponent(pnProfile, gbl, lbAboutMe, 0, 9);
        addComponent(pnProfile, gbl, tfAboutMe, 1, 9);
        
        //TODO Abfrage, ob eigenes Profil
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets =  new Insets(10, 0, 0, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbl.setConstraints(btnEdit, gbc);
        pnProfile.add(btnEdit);
        
        setProfileTextfieldsEditable(false);

    }

    /**
     * This method enables/disables all textfiels in the profile tab.
     * 
     * @param enabled 
     */
    private void setProfileTextfieldsEditable(boolean enabled){
        tfFirstName.setEnabled(enabled);     
        tfLastName.setEnabled(enabled);       
        tfRole.setEnabled(false);  // Role is not editable        
        tfPseudonym.setEnabled(enabled);        
        tfGender.setEnabled(enabled);         
        tfEmail.setEnabled(enabled);      
        tfBirthDate.setEnabled(enabled);        
        tfCity.setEnabled(enabled);      
        tfCountry.setEnabled(enabled);       
        tfAboutMe.setEnabled(enabled);  
    } 
            

    /**
     * This method adds a components to a given GridBagLayout.
     * @param cont
     * @param gbl
     * @param comp
     * @param x
     * @param y 
     */
    private void addComponent(Container cont,
            GridBagLayout gbl,
            Component comp,
            int x, int y) {
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = x;
        gbc.gridy = y;
        gbl.setConstraints(comp, gbc);
        cont.add(comp);
    }
}
