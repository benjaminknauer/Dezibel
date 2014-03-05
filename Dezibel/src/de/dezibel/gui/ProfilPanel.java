package de.dezibel.gui;

import de.dezibel.data.Label;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.tree.DefaultTreeCellEditor;

public class ProfilPanel extends DragablePanel {

    private static final long serialVersionUID = 1L;
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

    public ProfilPanel(DezibelPanel parent) {
        super(parent);
        this.createComponents();
        this.createLayout();
        //this.add(tabPanel);
    }

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
        
        tfFirstName = new JTextField();
        tfFirstName.setColumns(20);
        
        addComponent(pnProfile, gbl, lbFirstName, 0, 0);
        addComponent(pnProfile, gbl, tfFirstName, 1, 0);
        addComponent(pnProfile, gbl, lbLastName, 0, 1);
        addComponent(pnProfile, gbl, lbRole, 0, 2);
        addComponent(pnProfile, gbl, lbPseudonym, 0, 3);
        addComponent(pnProfile, gbl, lbGender, 0, 4);
        addComponent(pnProfile, gbl, lbEmail, 0, 5);
        addComponent(pnProfile, gbl, lbBirthDate, 0, 6);
        addComponent(pnProfile, gbl, lbCity, 0, 7);
        addComponent(pnProfile, gbl, lbCountry, 0, 8);
        addComponent(pnProfile, gbl, lbAboutMe, 0, 9);


    }

    private void createLayout() {

        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);
        this.add(tabPanel, BorderLayout.CENTER);
    }

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
