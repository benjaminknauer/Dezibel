package de.dezibel.gui;

import de.dezibel.UpdateEntity;
import de.dezibel.control.LabelControl;
import de.dezibel.control.ProfileControl;
import de.dezibel.control.AdminControl;
import de.dezibel.control.HashGenerator;
import de.dezibel.data.Album;
import de.dezibel.data.Application;
import de.dezibel.data.Comment;
import de.dezibel.data.Label;
import de.dezibel.data.Medium;
import de.dezibel.data.News;
import de.dezibel.data.Playlist;
import de.dezibel.data.User;
import de.dezibel.player.Player;
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
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class ProfilPanel extends DragablePanel {

    private static final long serialVersionUID = 1L;
    private ProfileControl profileControler;
    private LabelControl labelControler;
    private AdminControl adminControler;
    private User currentUser;
    private boolean isLabelVisible;

    private JTabbedPane tabPanel;
    private JPanel pnProfile;
    private JPanel pnUploads;
    private JPanel pnFavorites;
    private JPanel pnFollower;
    private JPanel pnComments;
    private JPanel pnNews;
    private JPanel pnLabels;
    private JPanel pnApplications;
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

    private JButton btnFollow;
    private JButton btnEdit;
    private JButton btnLock;

    private JTable tFollower;
    private FollowerTableModel followerModell;
    private JTable tComments;
    private CommentTableModel commentModell;
    private JTable tLabelsManaged;
    private LabelTableModel labelModellManaged;
    private JTable tLabelsPublishing;
    private LabelTableModel labelModellPublishing;
    private JScrollPane tablePanel;
    private JScrollPane scrManagedLabels;
    private JScrollPane scrPublishingLabels;
    private JScrollPane scrApplications;
    private JLabel lbPlaylist;
    private JTable tFavoPlaylists;
    private JTable tUploadPlaylists;
    private JTable tFavoMedia;
    private JTable tUploadMedia;
    private JTable tFavoAlbums;
    private JTable tUploadAlbums;
    private JLabel lbPseudonym;
    private JLabel lbOldPassword;
    private JLabel lbNewPassword;
    private JLabel lbRepeatNewPassword;
    private JTable tNews;
    private JTable tApplications;
    private JTextArea taNews;

    private JPopupMenu currentPopupMenu;
    private JButton btnCreateLabel;
    private boolean isNewsVisible;
    private JScrollPane spMedia;
    private JScrollPane spAlbums;
    private JLabel lbMediaUpload;
    private JLabel lbMediaFavo;
    private JLabel lbAlbumsFavo;
    private JLabel lbAlbumsUpload;
    private MyListsTableModel playlistModellUpload;
    private MyListsTableModel playlistModellFavo;
    private MediaTableModel mediaModellUpload;
    private MediaTableModel mediaModellFavo;
    private AlbumTableModel albumModellUpload;
    private AlbumTableModel albumModellFavo;
    private NewsSideTableModel newsModell;
    private ApplicationToArtistTableModel applicationsModel;
    private JTextArea taComments;
    
    private int showTabNr;
    private boolean isApplicationVisible;
    private JPasswordField tfOldPassword;
    private JPasswordField tfRepeatNewPassword;
    private JPasswordField tfNewPassword;

    /**
     * Constructor of the ProfilPanel class.
     *
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

        this.profileControler = new ProfileControl();
        this.labelControler = new LabelControl();
        this.adminControler = new AdminControl();
        this.currentUser = profileControler.getLoggedInUser();
        
        showTabNr = 0;
    }

     /**
     * Sets the tab which should be currently displayed.
     * 
     * @param tabNr nr of the tab which should be displayed
     */
    public void setTab(int tabNr){
        showTabNr = tabNr;
    }
    
    public void setUser(User newUser) {
        this.currentUser = newUser;
        showTabNr = 0;
        this.refresh();
    }

    public User getUser() {
        return this.currentUser;
    }

    @Override
    public void refresh() {

        if (!(currentUser.isLocked() && !(profileControler.getLoggedInUser(
        ).isAdmin()))) {
            
            tabPanel.setSelectedIndex(showTabNr);
            
            taNews.setText("");
            taComments.setText("");

            if (!(currentUser.isArtist())) {
                this.tfPseudonym.setVisible(false);
                this.lbPseudonym.setVisible(false);
            } else {
                this.tfPseudonym.setVisible(true);
                this.lbPseudonym.setVisible(true);
            }

            this.tfOldPassword.setText("");
            this.tfNewPassword.setText("");
            this.tfRepeatNewPassword.setText("");
            this.tfFirstName.setText(profileControler.getFirstName(currentUser));
            this.tfLastName.setText(profileControler.getLastName(currentUser));
            this.tfRole.setText(profileControler.getRole(currentUser));
            this.tfPseudonym.setText(profileControler.getPseudonym(currentUser));
            //this.tfGender.setText(profileControler.getGender(currentUser));
            this.tfEmail.setText(profileControler.getEmail(currentUser));
            this.tfBirthDate.setText(profileControler.getBirthDate(currentUser));
            this.tfCity.setText(profileControler.getCity(currentUser));
            this.tfCountry.setText(profileControler.getCountry(currentUser));
            this.tfAboutMe.setText(profileControler.getAboutMe(currentUser));

            if (currentUser == profileControler.getLoggedInUser()) {
                btnFollow.setVisible(false);
            }

            if (currentUser != profileControler.getLoggedInUser()) {
                btnFollow.setVisible(true);
            }

            if (profileControler.getLoggedInUser() != currentUser) {
                btnEdit.setVisible(false);
            }
            if (profileControler.getLoggedInUser() == currentUser) {
                btnEdit.setVisible(true);
            }

            if (profileControler.getFavorizedUsers(profileControler.getLoggedInUser()).contains(
                    currentUser)) {
                btnFollow.setText("Unfollow");
            }

            if (!(profileControler.getFavorizedUsers(profileControler.getLoggedInUser()).contains(
                    currentUser))) {
                btnFollow.setText("Follow");
            }

            if (tfFirstName.isEnabled()) {
                btnEdit.setText("Speichern");
            }

            if (!(tfFirstName.isEnabled())) {
                btnEdit.setText("Bearbeiten");
            }

            if (currentUser.isLabelManager()) {
                scrManagedLabels.setVisible(true);
            } else {
                scrManagedLabels.setVisible(false);
            }

            if (currentUser.getPublishingLabels().isEmpty()) {
                scrPublishingLabels.setVisible(false);
            } else {
                scrPublishingLabels.setVisible(true);
            }

            if (currentUser == profileControler.getLoggedInUser()) {
                btnCreateLabel.setVisible(true);
            } else {
                btnCreateLabel.setVisible(false);
            }

            if ((currentUser != profileControler.getLoggedInUser())
                    && !(currentUser.isLabelManager())
                    && currentUser.getPublishingLabels().isEmpty()) {
                tabPanel.remove(pnLabels);
                isLabelVisible = false;
            } else if (!(isLabelVisible)) {
                tabPanel.addTab("Labels", null, pnLabels);
            }

            if (!(currentUser.isArtist())) {
                tabPanel.remove(pnNews);
                isNewsVisible = false;
            } else if (!(isNewsVisible)) {
                tabPanel.addTab("News", null, pnNews);
            }
            
             if (!(currentUser.equals(profileControler.getLoggedInUser()))) {
                tabPanel.remove(pnApplications);
                isApplicationVisible = false;
            } else if (!(isApplicationVisible)) {
                tabPanel.addTab("Bewerbungen", null, pnApplications);
            }

            if (!(currentUser.isArtist())) {
                spMedia.setVisible(false);
                spAlbums.setVisible(false);
                lbMediaUpload.setVisible(false);
                lbAlbumsUpload.setVisible(false);
            } else {
                spMedia.setVisible(true);
                spAlbums.setVisible(true);
                lbMediaUpload.setVisible(true);
                lbAlbumsUpload.setVisible(true);
            }

            if (!(profileControler.getLoggedInUser().isAdmin()) || currentUser.isAdmin()) {
                btnLock.setVisible(false);
            } else {
                btnLock.setVisible(true);
            }

            if (currentUser.isLocked()) {
                btnLock.setText("Entsperren");
            } else {
                btnLock.setText("Sperren");
            }

            followerModell.setData(profileControler.getFollowers(currentUser));
            labelModellPublishing.setData(profileControler.getPublishingLabels(currentUser));
            commentModell.setData(profileControler.getCreatedComments(currentUser));
            labelModellManaged.setData(profileControler.getManagedLabels(currentUser));
            playlistModellUpload.setData(profileControler.getCreatedPlaylists(currentUser));
            playlistModellFavo.setData(profileControler.getFavorizedPlaylists(currentUser));
            mediaModellUpload.setData(profileControler.getCreatedMediums(currentUser));
            mediaModellFavo.setData(profileControler.getFavorizedMediums(currentUser));
            albumModellUpload.setData(profileControler.getCreatedAlbums(currentUser));
            albumModellFavo.setData(profileControler.getFavorizedAlbums(currentUser));
            newsModell.setData(profileControler.getNews(currentUser));
            applicationsModel.setData(profileControler.getApplications(currentUser));
        }
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
        this.createFavoritenComponents();
        tabPanel.addTab("Favoriten", null, pnFavorites);
        this.pnFollower = new JPanel();
        this.createFollowerComponents();
        tabPanel.addTab("Follower", null, pnFollower);
        this.pnComments = new JPanel();
        this.createCommentsComponents();
        tabPanel.addTab("Kommentare", null, pnComments);
        this.pnNews = new JPanel();
        this.createNewsComponents();
        tabPanel.addTab("Neuigkeiten", null, pnNews);
        this.pnLabels = new JPanel();
        this.createLabelsComponents();
        tabPanel.addTab("Labels", null, pnLabels);
        this.pnApplications = new JPanel();
        this.createApplicationComponents();
        tabPanel.addTab("Bewerbungen", null, pnApplications);
    }

    /**
     * This method creates all components in the profile tab.
     */
    private void createProfileComponents() {
        pnProfile.setLayout(gbl);

        JLabel lbFirstName = new JLabel("Vorname:");
        JLabel lbLastName = new JLabel("Nachname:");
        JLabel lbRole = new JLabel("Rolle:");
        lbPseudonym = new JLabel("Pseudonym:");
        JLabel lbGender = new JLabel("Geschlecht:");
        JLabel lbEmail = new JLabel("Email:");
        JLabel lbBirthDate = new JLabel("Geburtsdatum:");
        JLabel lbCity = new JLabel("Stadt:");
        JLabel lbCountry = new JLabel("Land:");
        JLabel lbAboutMe = new JLabel("Über mich:");
        lbOldPassword = new JLabel("Altes Passwort:");
        lbNewPassword = new JLabel("Neues Passwort:");
        lbRepeatNewPassword = new JLabel("Neues Passwort wiederholen:");

        tfFirstName = new JTextField(25);
        tfLastName = new JTextField(25);
        tfRole = new JTextField(25);
        tfPseudonym = new JTextField(25);
        String[] items = {"männlich", "weiblich"};
        tfGender = new JComboBox<>(items);
        tfEmail = new JTextField(25);
        tfBirthDate = new JTextField(25);
        tfCity = new JTextField(25);
        tfCountry = new JTextField(25);
        tfAboutMe = new JTextField(25);
        tfOldPassword = new JPasswordField(25);
        tfNewPassword = new JPasswordField(25);
        tfRepeatNewPassword = new JPasswordField(25);

        btnEdit = new JButton("Bearbeiten");
        btnEdit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (tfFirstName.isEnabled() && currentUser != null) {
                    profileControler.setFirstName(currentUser, tfFirstName.getText());
                    profileControler.setLastName(currentUser, tfLastName.getText());
                    profileControler.setPseudonym(currentUser, tfPseudonym.getText());
                    profileControler.setGender(currentUser, tfGender.getSelectedItem().toString());
                    profileControler.setEmail(currentUser, tfEmail.getText());
                    profileControler.setBirthDate(currentUser, tfBirthDate.getText());
                    profileControler.setCity(currentUser, tfCity.getText());
                    profileControler.setCountry(currentUser, tfCountry.getText());
                    profileControler.setAboutMe(currentUser, tfAboutMe.getText());
                    if (!(tfOldPassword.getText().equals(""))){
                        if (profileControler.checkPassword(currentUser, tfOldPassword.getText())){
                            if (!(tfNewPassword.getText().equals(""))){
                            
                                if (tfRepeatNewPassword.getText().equals(tfNewPassword.getText())){
                                    profileControler.setPassword(profileControler.getLoggedInUser(), tfNewPassword.getText());
                                } else {
                                    JOptionPane.showMessageDialog(parent, "Passwörter stimmen nicht überein!");
                                }
                            } else {
                                JOptionPane.showMessageDialog(parent, "Kein neues Passwort eingegeben!");
                            }
                        } else {
                                JOptionPane.showMessageDialog(parent, "Altes Passwort stimmt nicht!");
                            }
                    }
                    setProfileTextfieldsEditable(false);
                } else {
                    setProfileTextfieldsEditable(true);
                }
                showTabNr = 0;
                refresh();
            }
        });

        btnFollow = new JButton("Follow");
        btnFollow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (profileControler.getFavorizedUsers(profileControler.getLoggedInUser()).contains(
                        currentUser)) {
                    profileControler.removeFavoriteUser(currentUser);
                } else if (!(profileControler.getFavorizedUsers(profileControler.getLoggedInUser()).contains(
                        currentUser))) {
                    profileControler.addToFavoriteUsers(currentUser);
                }
                showTabNr = 0;
                refresh();
                parent.refresh(UpdateEntity.FAVORITES);
            }
        });

        btnLock = new JButton("Sperren");
        btnLock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(currentUser.isLocked())) {
                    JPanel detailPanel = new JPanel();
                    detailPanel.setLayout(new BorderLayout());
                    JLabel lblReason = new JLabel("Grund: ");
                    JTextArea txtReason = new JTextArea();
                    JScrollPane scrollPane = new JScrollPane(txtReason);
                    scrollPane.setPreferredSize(new Dimension(300, 320));
                    detailPanel.add(lblReason, BorderLayout.NORTH);
                    detailPanel.add(scrollPane, BorderLayout.SOUTH);
                    int ret = JOptionPane.showConfirmDialog(ProfilPanel.this,
                            detailPanel, "Medium sperren",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE);
                    if (ret == JOptionPane.OK_OPTION) {
                        new AdminControl().lock(currentUser, txtReason.getText());
                        JOptionPane.showMessageDialog(ProfilPanel.this,
                                "Das Medium wurde gesperrt!", "Medium gesperrt",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    int ret = JOptionPane.showConfirmDialog(ProfilPanel.this,
                            "Soll das Medium wirklich entsperrt werden?",
                            "Medium entsperren", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (ret == JOptionPane.YES_OPTION) {
                        new AdminControl().unlock(currentUser);
                    }
                }
                showTabNr = 0;
                refresh();
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
        addComponent(pnProfile, gbl, lbOldPassword, 0, 10);
        addComponent(pnProfile, gbl, tfOldPassword, 1, 10);
        addComponent(pnProfile, gbl, lbNewPassword, 0, 11);
        addComponent(pnProfile, gbl, tfNewPassword, 1, 11);
        addComponent(pnProfile, gbl, lbRepeatNewPassword, 0, 12);
        addComponent(pnProfile, gbl, tfRepeatNewPassword, 1, 12);

        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 13;
        gbl.setConstraints(btnEdit, gbc);
        pnProfile.add(btnEdit);
        gbc.gridx = 1;
        gbc.gridy = 13;
        gbl.setConstraints(btnFollow, gbc);
        pnProfile.add(btnFollow);
        gbc.gridx = 2;
        gbc.gridy = 13;
        gbl.setConstraints(btnLock, gbc);
        pnProfile.add(btnLock);

        setProfileTextfieldsEditable(false);

    }

    /**
     * This method enables/disables all textfiels in the profile tab.
     *
     * @param enabled
     */
    private void setProfileTextfieldsEditable(boolean enabled) {
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
        lbOldPassword.setVisible(enabled);
        tfOldPassword.setVisible(enabled);
        lbNewPassword.setVisible(enabled);
        tfNewPassword.setVisible(enabled);
        lbRepeatNewPassword.setVisible(enabled);
        tfRepeatNewPassword.setVisible(enabled);
    }

    /**
     * This method adds a components to a given GridBagLayout.
     *
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
        followerModell = new FollowerTableModel();
        tFollower = new JTable(followerModell);
        tFollower.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tFollower.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    User u = (User) followerModell.getValueAt(
                            tFollower.getSelectedRow(), -1);
                    if (u != null) {
                        parent.showProfile(u);
                    }
                }
            }
            @Override
            public void mousePressed(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            private void showPopup(MouseEvent me) {
                ContextMenu contextMenu = new ContextMenu(parent);
                currentPopupMenu = contextMenu.getContextMenu(tFollower, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());
            }
        });

        tablePanel = new JScrollPane(tFollower);
        BorderLayout fLayout = new BorderLayout();
        pnFollower.setLayout(fLayout);
        pnFollower.add(tablePanel, BorderLayout.CENTER);
    }

    private void createCommentsComponents() {
        commentModell = new CommentTableModel();
        tComments = new JTable(commentModell);
        tComments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tComments.removeColumn(tComments.getColumn("von Benutzer"));

        taComments = new JTextArea();
        taComments.setLineWrap(true);
        taComments.setWrapStyleWord(true);
        JScrollPane sptaComments = new JScrollPane(taComments);
        taComments.setEditable(false);

        tablePanel = new JScrollPane(tComments);
        tablePanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        pnComments.add(tablePanel);
        pnComments.add(sptaComments);

        GroupLayout layout = new GroupLayout(pnComments);
        layout.setHorizontalGroup(layout
                .createParallelGroup(GroupLayout.Alignment.CENTER, true)
                .addGroup(
                        GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(tablePanel, 128, 128, 2000))
                .addGroup(
                        GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(sptaComments, 128, 128, 2000))
        );

        layout.setVerticalGroup(layout.createParallelGroup(
                GroupLayout.Alignment.CENTER, true)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                                .addComponent(tablePanel, 100, 100, 1000))
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.TRAILING, true)
                                .addComponent(sptaComments, 100, 100, 1000))
                )
        );

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        pnComments.setLayout(layout);

        tComments.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Comment c = (Comment) commentModell.getValueAt(tComments.getSelectedRow(), -1);
                taComments.setText(c.getText());
            }
        });
    }

    private void createNewsComponents() {

        newsModell = new NewsSideTableModel();
        tNews = new JTable(newsModell);
        tNews.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane sptNews = new JScrollPane(tNews);
        sptNews.getViewport().setView(tNews);
        pnNews.add(sptNews);

        taNews = new JTextArea();
        taNews.setLineWrap(true);
        taNews.setWrapStyleWord(true);

        JScrollPane sptaNews = new JScrollPane(taNews);
        sptaNews.getViewport().setView(taNews);
        taNews.setEditable(false);
        pnNews.add(sptaNews);

        GroupLayout layout = new GroupLayout(pnNews);
        layout.setHorizontalGroup(layout
                .createParallelGroup(GroupLayout.Alignment.CENTER, true)
                .addGroup(
                        GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(sptNews, 128, 128, 2000))
                .addGroup(
                        GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(sptaNews, 128, 128, 2000))
        );

        layout.setVerticalGroup(layout.createParallelGroup(
                GroupLayout.Alignment.CENTER, true)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                                .addComponent(sptNews, 100, 100, 1000))
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.TRAILING, true)
                                .addComponent(sptaNews, 100, 100, 1000))
                )
        );

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        pnNews.setLayout(layout);

        tNews.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                News n = (News) newsModell.getValueAt(tNews.getSelectedRow(), -1);
                taNews.setText(n.getText());
            }
        });
    }

    private void createFavoritenComponents() {

        playlistModellFavo = new MyListsTableModel();
        lbPlaylist = new JLabel("Wiedergabe Listen");
        lbPlaylist.setHorizontalAlignment(JLabel.CENTER);
        tFavoPlaylists = new JTable(playlistModellFavo);
        tFavoPlaylists.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane spPlaylists = new JScrollPane(tFavoPlaylists);
        spPlaylists.getViewport().setView(tFavoPlaylists);
        pnFavorites.add(spPlaylists);

        mediaModellFavo = new MediaTableModel();
        lbMediaFavo = new JLabel("Media");
        lbMediaFavo.setHorizontalAlignment(JLabel.CENTER);
        tFavoMedia = new JTable(mediaModellFavo);
        tFavoMedia.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane spMedia = new JScrollPane(tFavoMedia);
        spMedia.getViewport().setView(tFavoMedia);
        pnFavorites.add(spMedia);

        albumModellFavo = new AlbumTableModel();
        lbAlbumsFavo = new JLabel("Alben");
        lbAlbumsFavo.setHorizontalAlignment(JLabel.CENTER);
        tFavoAlbums = new JTable(albumModellFavo);
        tFavoAlbums.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane spAlbums = new JScrollPane();
        spAlbums.getViewport().setView(tFavoAlbums);
        pnFavorites.add(spAlbums);

        GroupLayout layout = new GroupLayout(pnFavorites);
        layout.setHorizontalGroup(layout
                .createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(lbPlaylist, 128, 128, 200)
                .addComponent(spPlaylists, 128, 128, 1500)
                .addComponent(lbMediaFavo, 128, 128, 200)
                .addComponent(spMedia, 128, 128, 1500)
                .addComponent(lbAlbumsFavo, 128, 128, 200)
                .addComponent(spAlbums, 128, 128, 1500)
        );

        layout.setVerticalGroup(layout.createParallelGroup(
                GroupLayout.Alignment.CENTER, true)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(lbPlaylist, 32, 32, 32)
                        .addComponent(spPlaylists, 100, 100, 700)
                        .addComponent(lbMediaFavo, 32, 32, 32)
                        .addComponent(spMedia, 100, 100, 700)
                        .addComponent(lbAlbumsFavo, 32, 32, 32)
                        .addComponent(spAlbums, 100, 100, 700)
                )
        );

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        pnFavorites.setLayout(layout);

        tFavoPlaylists.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    Playlist p = (Playlist) playlistModellFavo.getValueAt(
                            tFavoPlaylists.getSelectedRow(), -1);
                    if (p != null) {
                        parent.showPlaylist(p);
                    }
                }
            }
            @Override
            public void mousePressed(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            private void showPopup(MouseEvent me) {
                ContextMenu contextMenu = new ContextMenu(parent);
                currentPopupMenu = contextMenu.getContextMenu(tFavoPlaylists, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());
                showTabNr = 2;
            }
        });

        tFavoMedia.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    Medium m = (Medium) mediaModellFavo.getValueAt(
                            tFavoMedia.getSelectedRow(), -1);
                    if (m != null) {
                        Player.getInstance().addMediumAsNext(m);
                        Player.getInstance().next();
                    }
                }
            }
            @Override
            public void mousePressed(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            private void showPopup(MouseEvent me) {
                ContextMenu contextMenu = new ContextMenu(parent);
                currentPopupMenu = contextMenu.getContextMenu(tFavoMedia, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());
                showTabNr = 2;
            }

        });
        
        tFavoAlbums.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    Album a = (Album) albumModellFavo.getValueAt(
                            tFavoAlbums.getSelectedRow(), -1);
                    if (a != null) {
                        parent.showAlbum(a);
                    }
                }
            }
            @Override
            public void mousePressed(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            private void showPopup(MouseEvent me) {
                ContextMenu contextMenu = new ContextMenu(parent);
                currentPopupMenu = contextMenu.getContextMenu(tFavoAlbums, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());
                showTabNr = 2;
            }

        });

    }

    private void createUploadsComponents() {

        lbPlaylist = new JLabel("Wiedergabe Listen");
        lbPlaylist.setHorizontalAlignment(JLabel.CENTER);
        playlistModellUpload = new MyListsTableModel();
        tUploadPlaylists = new JTable(playlistModellUpload);
        tUploadPlaylists.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane spPlaylists = new JScrollPane(tUploadPlaylists);
        spPlaylists.getViewport().setView(tUploadPlaylists);
        pnUploads.add(spPlaylists);
        // pnUploads.add(tUploadPlaylists);

        mediaModellUpload = new MediaTableModel();
        tUploadMedia = new JTable(mediaModellUpload);
        tUploadMedia.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lbMediaUpload = new JLabel("Media");
        lbMediaUpload.setHorizontalAlignment(JLabel.CENTER);

        spMedia = new JScrollPane(tUploadMedia);
        spMedia.getViewport().setView(tUploadMedia);
        pnUploads.add(spMedia);

        albumModellUpload = new AlbumTableModel();
        tUploadAlbums = new JTable(albumModellUpload);
        tUploadAlbums.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        spAlbums = new JScrollPane();
        lbAlbumsUpload = new JLabel("Alben");
        lbAlbumsUpload.setHorizontalAlignment(JLabel.CENTER);
        spAlbums.getViewport().setView(tUploadAlbums);
        pnUploads.add(spAlbums);

        GroupLayout layout = new GroupLayout(pnUploads);
        layout.setHorizontalGroup(layout
                .createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(lbPlaylist, 128, 128, 200)
                .addComponent(spPlaylists, 128, 128, 1500)
                .addComponent(lbMediaUpload, 128, 128, 200)
                .addComponent(spMedia, 128, 128, 1500)
                .addComponent(lbAlbumsUpload, 128, 128, 200)
                .addComponent(spAlbums, 128, 128, 1500)
        );

        layout.setVerticalGroup(layout.createParallelGroup(
                GroupLayout.Alignment.CENTER, true)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(lbPlaylist, 32, 32, 32)
                        .addComponent(spPlaylists, 100, 100, 700)
                        .addComponent(lbMediaUpload, 32, 32, 32)
                        .addComponent(spMedia, 100, 100, 700)
                        .addComponent(lbAlbumsUpload, 32, 32, 32)
                        .addComponent(spAlbums, 100, 100, 700)
                )
        );

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        pnUploads.setLayout(layout);

        
        tUploadPlaylists.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    Playlist p = (Playlist) playlistModellUpload.getValueAt(
                            tUploadPlaylists.getSelectedRow(), -1);
                    if (p != null) {
                        parent.showPlaylist(p);
                    }
                }
            }
            @Override
            public void mousePressed(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            private void showPopup(MouseEvent me) {
                ContextMenu contextMenu = new ContextMenu(parent);
                currentPopupMenu = contextMenu.getContextMenu(tUploadPlaylists, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());
                showTabNr = 1;
            }

        });
        
        tUploadMedia.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    Medium m = (Medium) mediaModellUpload.getValueAt(
                            tUploadMedia.getSelectedRow(), -1);
                    if (m != null) {
                        Player.getInstance().addMediumAsNext(m);
                        Player.getInstance().next();
                    }
                }
            }
            @Override
            public void mousePressed(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            private void showPopup(MouseEvent me) {
                ContextMenu contextMenu = new ContextMenu(parent);
                currentPopupMenu = contextMenu.getContextMenu(tUploadMedia, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());
                showTabNr = 1;
            }

        });
        
        tUploadAlbums.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    Album a = (Album) albumModellUpload.getValueAt(
                            tUploadAlbums.getSelectedRow(), -1);
                    if (a != null) {
                        parent.showAlbum(a);
                    }
                }
            }
            @Override
            public void mousePressed(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            private void showPopup(MouseEvent me) {
                ContextMenu contextMenu = new ContextMenu(parent);
                currentPopupMenu = contextMenu.getContextMenu(tUploadAlbums, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());
                showTabNr = 1;
            }

        });
    }

    private void createLabelsComponents() {

        // Create Label Button
        btnCreateLabel = new JButton("Label erstellen");
        btnCreateLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String labelName = JOptionPane.showInputDialog("Wählen Sie einen Namen für Ihr Label:");
                if (labelName != null && labelName.length() > 0) {
                    labelControler.createLabel(currentUser, labelName);
                    labelControler.promoteUserToLabelManager(currentUser);
                    showTabNr = 5;
                    refresh();
                } else {
                    JOptionPane.showMessageDialog(parent, "Eingabefeld war leer!"
                            + " Es wurde kein neues Label erstellt.");
                }
            }
        });

        // Managed Labels Tabel
        gbl = new GridBagLayout();
        labelModellManaged = new LabelTableModel();
        labelModellManaged.setHeader(new String[]{"Meine Labels"});
        tLabelsManaged = new JTable(labelModellManaged);
        tLabelsManaged.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrManagedLabels = new JScrollPane(tLabelsManaged);
        pnLabels.setLayout(gbl);

        tLabelsManaged.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    Label l = (Label) labelModellManaged.getValueAt(
                            tLabelsManaged.getSelectedRow(), -1);
                    if (l != null) {
                        parent.showProfile(l);
                    }
                }
            }
            public void mousePressed(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            private void showPopup(MouseEvent me) {
                ContextMenu contextMenu = new ContextMenu(parent);
                currentPopupMenu = contextMenu.getContextMenu(tLabelsManaged, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());
            }
        });

        // Publishing Labels
        labelModellPublishing = new LabelTableModel();
        labelModellPublishing.setHeader(new String[]{"Meine Publisher"});
        tLabelsPublishing = new JTable(labelModellPublishing);
        tLabelsPublishing.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrPublishingLabels = new JScrollPane(tLabelsPublishing);

        tLabelsPublishing.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    Label l = (Label) labelModellPublishing.getValueAt(
                            tLabelsPublishing.getSelectedRow(), -1);
                    if (l != null) {
                        parent.showProfile(l);
                    }
                }
            }
            @Override
            public void mousePressed(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            private void showPopup(MouseEvent me) {
                ContextMenu contextMenu = new ContextMenu(parent);
                currentPopupMenu = contextMenu.getContextMenu(tLabelsPublishing, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());
            }
        });

        gbc = new GridBagConstraints();

        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.fill = GridBagConstraints.BOTH;

        gbc.weightx = 1;
        gbc.weighty = 1;

        gbl.setConstraints(scrManagedLabels, gbc);
        pnLabels.add(scrManagedLabels);
        gbc.insets = new Insets(0, 5, 0, 5);
        gbl.setConstraints(scrPublishingLabels, gbc);
        pnLabels.add(scrPublishingLabels);

        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weighty = 0.1;
        gbl.setConstraints(btnCreateLabel, gbc);
        pnLabels.add(btnCreateLabel);
    }
    
    private void createApplicationComponents() {
        this.applicationsModel = new ApplicationToArtistTableModel();
        this.tApplications = new JTable(applicationsModel);
        this.scrApplications = new JScrollPane(tApplications);
        this.tApplications.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        this.tApplications.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if(me.getClickCount() == 2 && (me.getButton() == MouseEvent.BUTTON1)) {
                    Application a = (Application) applicationsModel.getValueAt(tApplications.getSelectedRow(), -1);
                    if(a != null) {
                        if(a.getLabel() != null) {
                            parent.showProfile(a.getLabel());
                        }
                    }
                }
            }
            
            @Override
            public void mousePressed(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            private void showPopup(MouseEvent me) {
                ContextMenu contextMenu = new ContextMenu(parent);
                currentPopupMenu = contextMenu.getContextMenu(tApplications, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());
            }
        });
        
        gbl = new GridBagLayout();
        pnApplications.setLayout(gbl);
        gbc = new GridBagConstraints();

        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.fill = GridBagConstraints.BOTH;

        gbc.weightx = 1;
        gbc.weighty = 1;

        gbl.setConstraints(scrApplications, gbc);
        pnApplications.add(scrApplications);
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub

    }

}
