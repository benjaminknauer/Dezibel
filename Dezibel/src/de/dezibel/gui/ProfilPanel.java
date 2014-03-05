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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
    
    private JTable tFollower;
    private FollowerTableModel fModell;
    private JTable tComments;
    private CommentTableModel cModell;
    private JScrollPane tablePanel;
    private JLabel lbPlaylist;
    private JTable tPlaylists;
    private JLabel lbMedia;
    private JTable tMedia;
    private JLabel lbAlbums;
    private JTable tAlbums;
    
    /**
     * Constructor of the ProfilPanel class.
     * @param parent 
     */
    public ProfilPanel(DezibelPanel parent) {
        super(parent);
        this.currentUser = null;
        
        this.createComponents();
        // Create Layout
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);
        this.add(tabPanel, BorderLayout.CENTER);
        
        this.controler = new ProfileControl();
        this.currentUser = controler.getLoggedInUser();
    }

    public void setUser(User newUser){
    	this.currentUser = newUser;
    	this.refresh();
    }
    
    public User getUser(){
    	return this.currentUser;
    }
    
    public void refresh(){
    	this.tfFirstName.setText(controler.getFirstName(currentUser));
    	this.tfLastName.setText(controler.getLastName(currentUser)); 
    	this.tfRole.setText(controler.getRole(currentUser));
    	this.tfPseudonym.setText(controler.getPseudonym(currentUser));
    	//this.tfGender.setText(controler.getGender(currentUser));
    	this.tfEmail.setText(controler.getEmail(currentUser));
    	this.tfBirthDate.setText(controler.getBirthDate(currentUser));
    	this.tfCity.setText(controler.getCity(currentUser));
    	this.tfCountry.setText(controler.getCountry(currentUser));
    	this.tfAboutMe.setText(controler.getAboutMe(currentUser));
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
        this.createUploadsComponents();
        tabPanel.addTab("Uploads", null, pnUploads);
        this.pnFavorites = new JPanel();
        tabPanel.addTab("Favoriten", null, pnFavorites);
        this.pnFollower = new JPanel();
        this.createFollowerComponents();
        tabPanel.addTab("Follower", null, pnFollower);
        this.pnComments = new JPanel();
        this.createCommentsComponents();
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
        tfLastName = new JTextField(25);       
        tfRole = new JTextField(25);       
        tfPseudonym = new JTextField(25);       
        String[] items = {"m�nnlich", "weiblich"}; 
        tfGender = new JComboBox<>(items);
        tfEmail = new JTextField(25);       
        tfBirthDate = new JTextField(25);       
        tfCity = new JTextField(25);       
        tfCountry = new JTextField(25);       
        tfAboutMe = new JTextField(25);
        
        JButton btnEdit = new JButton("Bearbeiten");
        btnEdit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(tfFirstName.isEnabled() && currentUser != null){
                controler.setFirstName(currentUser, tfFirstName.getText());
                controler.setLastName(currentUser, tfLastName.getText());
                controler.setPseudonym(currentUser, tfPseudonym.getText());
                controler.setGender(currentUser, tfGender.getSelectedItem().toString());
                controler.setEmail(currentUser, tfEmail.getText());
                controler.setBirthDate(currentUser, tfBirthDate.getText());
                controler.setCity(currentUser, tfCity.getText());
                controler.setCountry(currentUser, tfCountry.getText());
                controler.setAboutMe(currentUser, tfAboutMe.getText());
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
    
        private void createFollowerComponents() {
        ProfileControl pc = new ProfileControl();
        fModell = new FollowerTableModel();
        fModell.setData(pc.getFollowers(pc.getLoggedInUser()));
        tFollower = new JTable(fModell);

        tFollower.addMouseListener(new MouseAdapter() {
            public void mouseDoubleClicked(MouseEvent e) {
                setUser(fModell.getUserAt(tFollower.getSelectedRow()));
            }
        });

        tablePanel = new JScrollPane(tFollower);
        BorderLayout fLayout = new BorderLayout();
        pnFollower.setLayout(fLayout);
        pnFollower.add(tablePanel, BorderLayout.CENTER);

        addComponent(pnFollower, gbl, tFollower, 0, 0);
    }

    private void createCommentsComponents() {
        ProfileControl pc = new ProfileControl();
        cModell = new CommentTableModel();
        cModell.setData(pc.getCreatedComments(pc.getLoggedInUser()));
        tComments = new JTable(cModell);

        tablePanel = new JScrollPane(tComments);
        BorderLayout fLayout = new BorderLayout();
        pnComments.setLayout(fLayout);
        pnComments.add(tComments, BorderLayout.CENTER);

        addComponent(pnComments, gbl, tComments, 0, 0);
    }

    private void createUploadsComponents() {
        lbPlaylist = new JLabel("Wiedergabe Listen");
        lbPlaylist.setHorizontalAlignment(JLabel.LEADING);
        lbPlaylist.setBounds(0,0, 200,30);        
        tPlaylists = new JTable(10,1);
        tPlaylists.isCellEditable(10,1);
        //tPlaylists.setEditable(false);
        tPlaylists.getTableHeader().setVisible(false);        
        JScrollPane spPlaylists = new JScrollPane(tPlaylists);       
      //  tableModelPlaylist = new PlaylistTableModel();        
        tPlaylists.setBounds(110,0,400,100);
        pnUploads.add(tPlaylists);           
       // JScrollPane spPlaylists = new JScrollPane(tPlaylists);
        spPlaylists.setBounds(110, -5, 400, 100);
        spPlaylists.getViewport().setView(tPlaylists);        
        pnUploads.add(spPlaylists); 
        pnUploads.add(lbPlaylist);
        
        
        
        lbMedia = new JLabel("Media");
        lbMedia.setHorizontalAlignment(JLabel.LEADING);
        lbMedia.setBounds(0,150, 200,30);       
        tMedia = new JTable(10,1);
        tMedia.setEnabled(false);
        tMedia.getTableHeader().setVisible(false);  
        JScrollPane spMedia = new JScrollPane(tMedia);
        tMedia.setBounds(110,150,400,100);
        pnUploads.add(tMedia);
        spMedia.setBounds(110,150,400,100);
        spMedia.getViewport().setView(tMedia);      
        pnUploads.add(spMedia);
        pnUploads.add(lbMedia); 
        
        lbAlbums = new JLabel("Alben");
        lbAlbums.setHorizontalAlignment(JLabel.LEADING);
        lbAlbums.setBounds(0,300, 200,30);
        tAlbums = new JTable(10,1);
        tAlbums.getTableHeader().setVisible(false);  
        tAlbums.setBounds(110,300,400,100);
        pnUploads.add(tAlbums);        
        tAlbums.setEnabled(false);
        JScrollPane spAlbums = new JScrollPane();
        spAlbums.setBounds(110,300,400,100);
        spAlbums.getViewport().setView(tAlbums);
        pnUploads.add(spAlbums);
        pnUploads.add(lbAlbums);
    }
}
