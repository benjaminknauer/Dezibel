package de.dezibel.gui;

import de.dezibel.control.LabelControl;
import de.dezibel.control.ProfileControl;
import de.dezibel.data.Label;
import de.dezibel.data.News;
import de.dezibel.data.User;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultTreeCellEditor;

public class ProfilPanel extends DragablePanel {

    private static final long serialVersionUID = 1L;
    private ProfileControl profileControler;
    private LabelControl labelControler;
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
    private JLabel lbPlaylist;
    private JTable tPlaylists;
    private JTable tMedia;
    private JTable tAlbums;
    private JLabel lbPseudonym;
    private JTable tNews;
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
        this.currentUser = profileControler.getLoggedInUser();
    }

    public void setUser(User newUser) {
        this.currentUser = newUser;
        this.refresh();
    }

    public User getUser() {
        return this.currentUser;
    }

    public void refresh() {

        tabPanel.setSelectedIndex(0);

        if (!(currentUser.isArtist())) {
            this.tfPseudonym.setVisible(false);
            this.lbPseudonym.setVisible(false);
        } else {
            this.tfPseudonym.setVisible(true);
            this.lbPseudonym.setVisible(true);
        }

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
        this.followerModell.setData(profileControler.getFollowers(currentUser));
        this.commentModell.setData(profileControler.getCreatedComments(currentUser));
        this.labelModellManaged.setData(profileControler.getManagedLabels(currentUser));
        this.labelModellPublishing.setData(profileControler.getPublishingLabels(currentUser));

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
                    setProfileTextfieldsEditable(false);
                } else {
                    setProfileTextfieldsEditable(true);
                }
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
                    System.out.print("favo gelöscht");
                } else if (!(profileControler.getFavorizedUsers(profileControler.getLoggedInUser()).contains(
                        currentUser))) {
                    profileControler.addToFavoriteUsers(currentUser);
                    System.out.print("favo zugefügt");
                }
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

        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbl.setConstraints(btnEdit, gbc);
        pnProfile.add(btnEdit);
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbl.setConstraints(btnFollow, gbc);
        pnProfile.add(btnFollow);

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

        tablePanel = new JScrollPane(tComments);
        tablePanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        BorderLayout fLayout = new BorderLayout();
        pnComments.setLayout(fLayout);
        pnComments.add(tablePanel, BorderLayout.CENTER);
    }

    private void createNewsComponents() {

        newsModell = new NewsSideTableModel();
        tNews = new JTable(newsModell);

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
        tPlaylists = new JTable(playlistModellFavo);
        tPlaylists.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane spPlaylists = new JScrollPane(tPlaylists);
        spPlaylists.getViewport().setView(tPlaylists);
        pnFavorites.add(spPlaylists);

        mediaModellFavo = new MediaTableModel();
        lbMediaFavo = new JLabel("Media");
        lbMediaFavo.setHorizontalAlignment(JLabel.CENTER);
        tMedia = new JTable(mediaModellFavo);
        tMedia.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane spMedia = new JScrollPane(tMedia);
        spMedia.getViewport().setView(tMedia);
        pnFavorites.add(spMedia);

        albumModellFavo = new AlbumTableModel();
        lbAlbumsFavo = new JLabel("Alben");
        lbAlbumsFavo.setHorizontalAlignment(JLabel.CENTER);
        tAlbums = new JTable(albumModellFavo);
        tAlbums.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane spAlbums = new JScrollPane();
        spAlbums.getViewport().setView(tAlbums);
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

        tPlaylists.addMouseListener(new MouseAdapter() {
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
                currentPopupMenu = contextMenu.getContextMenu(tPlaylists, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());
            }
        });

        tMedia.addMouseListener(new MouseAdapter() {
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
                currentPopupMenu = contextMenu.getContextMenu(tMedia, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());

            }

        });

    }

    private void createUploadsComponents() {

        lbPlaylist = new JLabel("Wiedergabe Listen");
        lbPlaylist.setHorizontalAlignment(JLabel.CENTER);
        playlistModellUpload = new MyListsTableModel();
        tPlaylists = new JTable(playlistModellUpload);
        tPlaylists.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane spPlaylists = new JScrollPane(tPlaylists);
        spPlaylists.getViewport().setView(tPlaylists);
        pnUploads.add(spPlaylists);
        // pnUploads.add(tPlaylists);

        mediaModellUpload = new MediaTableModel();
        tMedia = new JTable(mediaModellUpload);
        tMedia.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lbMediaUpload = new JLabel("Media");
        lbMediaUpload.setHorizontalAlignment(JLabel.CENTER);

        spMedia = new JScrollPane(tMedia);
        spMedia.getViewport().setView(tMedia);
        pnUploads.add(spMedia);

        albumModellUpload = new AlbumTableModel();
        tAlbums = new JTable(albumModellUpload);
        tAlbums.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        spAlbums = new JScrollPane();
        lbAlbumsUpload = new JLabel("Alben");
        lbAlbumsUpload.setHorizontalAlignment(JLabel.CENTER);
        spAlbums.getViewport().setView(tAlbums);
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

    }

    private void createLabelsComponents() {

        // Create Label Button
        btnCreateLabel = new JButton("Label erstellen");
        btnCreateLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String labelName = JOptionPane.showInputDialog("Wählen Sie einen Namen für Ihr Label:");
                System.out.print(labelName);
                if (labelName != null && labelName.length() > 0) {
                    labelControler.createLabel(currentUser, labelName);
                    labelControler.promoteUserToLabelManager(currentUser);
                    refresh();
                    tabPanel.setSelectedIndex(5);
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

        // Publishing Labels
        labelModellPublishing = new LabelTableModel();
        labelModellPublishing.setHeader(new String[]{"Meine Publisher"});
        tLabelsPublishing = new JTable(labelModellPublishing);
        tLabelsPublishing.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrPublishingLabels = new JScrollPane(tLabelsPublishing);

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

    @Override
    public void reset() {
		// TODO Auto-generated method stub

    }
}
