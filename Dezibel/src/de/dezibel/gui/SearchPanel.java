package de.dezibel.gui;

import de.dezibel.control.Search;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.TreeSet;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tobias, Pascal
 */
public class SearchPanel extends DragablePanel {

    private static final long serialVersionUID = 1L;
    private JTextField tfSearch;
    private JComboBox<String> cbFilter;
    private JButton bnSearch;
    private JRadioButton rbSongAlphabetical;
    private JRadioButton rbRating;
    private JRadioButton rbUploadDate;
    private JRadioButton rbUserAlphabetical;
    private JRadioButton rbLabelAlphabetical;
    private JRadioButton rbPlaylistAlphabetical;
    private JPanel pnSorting;
    private JTable tableResults;
    private DefaultTableModel tableModelSong;
    private DefaultTableModel tableModelUser;
    private DefaultTableModel tableModelLabel;
    private DefaultTableModel tableModelPlaylist;
    private JScrollPane tablePanel;
    private GroupLayout layout;

    private JPanel pnSortingMedium;
    private JPanel pnSortingUser;
    private JPanel pnSortingLabel;
    private JPanel pnSortingPlaylist;

    public SearchPanel(DezibelPanel parent) {
        super(parent);
        this.createComponents();
        this.createLayout();
        this.createListener();
    }

    private void createComponents() {
        String[] choices = {"Song", "User", "Label", "Playlist"};

        tfSearch = new JTextField("Search...");
        cbFilter = new JComboBox<>(choices);
        bnSearch = new JButton("Search");
        rbSongAlphabetical = new JRadioButton("Alphabetical");
        rbUserAlphabetical = new JRadioButton("Alphabetical");
        rbLabelAlphabetical = new JRadioButton("Alphabetical");
        rbPlaylistAlphabetical = new JRadioButton("Alphabetical");
        rbRating = new JRadioButton("Rating");
        rbUploadDate = new JRadioButton("Upload-Date");
        tableResults = new JTable();
        tablePanel = new JScrollPane(tableResults);
        tablePanel.setViewportView(tableResults);

        ButtonGroup bnGroupMedium = new ButtonGroup();
        ButtonGroup bnGroupUser = new ButtonGroup();
        ButtonGroup bnGroupLabel = new ButtonGroup();
        ButtonGroup bnGroupPlaylist = new ButtonGroup();

        bnGroupMedium.add(rbSongAlphabetical);
        bnGroupMedium.add(rbRating);
        bnGroupMedium.add(rbUploadDate);
        bnGroupUser.add(rbUserAlphabetical);
        bnGroupLabel.add(rbLabelAlphabetical);
        bnGroupPlaylist.add(rbPlaylistAlphabetical);

        rbSongAlphabetical.setSelected(true);
        rbUserAlphabetical.setSelected(true);
        rbLabelAlphabetical.setSelected(true);
        rbPlaylistAlphabetical.setSelected(true);

        pnSorting = new JPanel(new CardLayout());
        pnSortingMedium = new JPanel();
        pnSortingUser = new JPanel();
        pnSortingLabel = new JPanel();
        pnSortingPlaylist = new JPanel();

        pnSorting.add(pnSortingMedium, "Song");
        pnSorting.add(pnSortingUser, "User");
        pnSorting.add(pnSortingLabel, "Label");
        pnSorting.add(pnSortingPlaylist, "Playlist");

        GroupLayout layoutMedium = new GroupLayout(pnSortingMedium);
        layoutMedium.setAutoCreateGaps(true);
        layoutMedium.setAutoCreateContainerGaps(true);
        layoutMedium.setHorizontalGroup(
                layoutMedium.createParallelGroup().addGroup(layoutMedium.createSequentialGroup().addComponent(rbSongAlphabetical)
                        .addComponent(rbRating).addComponent(rbUploadDate)));
        layoutMedium.setVerticalGroup(layoutMedium.createParallelGroup().addGroup(layoutMedium.createParallelGroup().addComponent(rbSongAlphabetical).addComponent(rbRating).addComponent(rbUploadDate)));
        pnSortingMedium.setLayout(layoutMedium);

        GroupLayout layoutUser = new GroupLayout(pnSortingUser);
        layoutUser.setAutoCreateContainerGaps(true);
        layoutUser.setAutoCreateGaps(true);
        layoutUser.setHorizontalGroup(layoutUser.createParallelGroup().addGroup(layoutUser.createSequentialGroup().addComponent(rbUserAlphabetical)));
        layoutUser.setVerticalGroup(layoutUser.createParallelGroup().addGroup(layoutUser.createParallelGroup().addComponent(rbUserAlphabetical)));
        pnSortingUser.setLayout(layoutUser);

        GroupLayout layoutLabel = new GroupLayout(pnSortingLabel);
        layoutLabel.setAutoCreateContainerGaps(true);
        layoutLabel.setAutoCreateGaps(true);
        layoutLabel.setHorizontalGroup(layoutLabel.createParallelGroup().addGroup(layoutLabel.createSequentialGroup().addComponent(rbLabelAlphabetical)));
        layoutLabel.setVerticalGroup(layoutLabel.createParallelGroup().addGroup(layoutLabel.createParallelGroup().addComponent(rbLabelAlphabetical)));
        pnSortingLabel.setLayout(layoutLabel);

        GroupLayout layoutPlaylist = new GroupLayout(pnSortingPlaylist);
        layoutPlaylist.setAutoCreateContainerGaps(true);
        layoutPlaylist.setAutoCreateGaps(true);
        layoutPlaylist.setHorizontalGroup(layoutPlaylist.createParallelGroup().addGroup(layoutPlaylist.createSequentialGroup().addComponent(rbPlaylistAlphabetical)));
        layoutPlaylist.setVerticalGroup(layoutPlaylist.createParallelGroup().addGroup(layoutPlaylist.createParallelGroup().addComponent(rbPlaylistAlphabetical)));
        pnSortingPlaylist.setLayout(layoutPlaylist);

        tableModelSong = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Title", "Artist", "Albm", "Genre"
                }) {
                    private static final long serialVersionUID = 1L;
                    boolean[] canEdit = new boolean[]{
                        false, false, false, false
                    };

                    public boolean isCellEditable(int rowindex, int columnIndex) {
                        return canEdit[columnIndex];
                    }
                };

        tableModelUser = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Firstname", "Lastname", "Pseudonym", "City", "Country", "Mail"
                }) {
                    private static final long serialVersionUID = 1L;
                    boolean[] canEdit = new boolean[]{
                        false, false, false, false, false, false
                    };

                    public boolean isCellEditable(int rowindex, int columnIndex) {
                        return canEdit[columnIndex];
                    }
                };

        tableModelLabel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Name"
                }) {
                    private static final long serialVersionUID = 1L;
                    boolean[] canEdit = new boolean[]{
                        false
                    };

                    public boolean isCellEditable(int rowindex, int columnIndex) {
                        return canEdit[columnIndex];
                    }
                };

        tableModelPlaylist = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Title"
                }) {
                    private static final long serialVersionUID = 1L;
                    boolean[] canEdit = new boolean[]{
                        false
                    };

                    public boolean isCellEditable(int rowindex, int columnIndex) {
                        return canEdit[columnIndex];
                    }
                };
    }

    private void createLayout() {
        this.removeAll();
        layout = new GroupLayout(this);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                .addGroup(layout.createSequentialGroup().addComponent(tfSearch, 256, 256, 256)
                        .addComponent(cbFilter, 128, 128, 128).addComponent(bnSearch, 80, 80, 80))
                .addGroup(layout.createSequentialGroup().addComponent(pnSorting).addGap(128, 128, 128))
                .addGroup(layout.createSequentialGroup().addComponent(tablePanel))
        );

        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(tfSearch, 32, 32, 32)
                                .addComponent(cbFilter, 32, 32, 32)
                                .addComponent(bnSearch, 32, 32, 32))
                                .addComponent(pnSorting)
                                .addComponent(tablePanel))
        );
        this.setLayout(layout);
    }

    private void createListener() {
        cbFilter.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                CardLayout cl = (CardLayout) pnSorting.getLayout();
                cl.show(pnSorting, (String) e.getItem());
                switch (e.getItem().toString()) {
                    case "Song":
                        tableResults.setModel(tableModelSong);
                        break;
                    case "User":
                        tableResults.setModel(tableModelUser);
                        break;
                    case "Label":
                        tableResults.setModel(tableModelLabel);
                        break;
                    case "Playlist":
                        tableResults.setModel(tableModelPlaylist);
                        break;
                }
            }
        });

        bnSearch.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                Search searchcontrol = new Search();
                TreeSet result;
                DefaultTableModel model = null;
                int sortation = 0;
                String[] columnIdentifier;

                switch (cbFilter.getSelectedItem().toString()) {
                    case "Song":
                        model = tableModelSong;
                        columnIdentifier = new String[]{"Title", "Artist", "Albm", "Genre"};
                        if (rbSongAlphabetical.isSelected()) {
                            sortation = 0;
                        }
                        if (rbRating.isSelected()) {
                            sortation = 1;
                        }
                        if (rbUploadDate.isSelected()) {
                            sortation = 2;
                        }

                        result = searchcontrol.searchForMedia(tfSearch.getText(), sortation);
                        break;
                    case "User":
                        model = tableModelUser;
                        columnIdentifier = new String[]{"Firstname", "Lastname", "Pseudonym", "City", "Country", "Mail"};
                        result = searchcontrol.searchForUsers(tfSearch.getText(), 0);
                        break;
                    case "Label":
                        model = tableModelLabel;
                        columnIdentifier = new String[]{"Name"};
                        result = searchcontrol.searchForLabels(tfSearch.getText(), 0);
                        break;
                    case "Playlist":
                        model = tableModelPlaylist;
                        columnIdentifier = new String[]{"Title"};
                        result = searchcontrol.searchForPlaylists(tfSearch.getText(), 0);
                        break;
                }
                
                /*                if(model != null) {
                model.getDataVector().clear();
                    
                    
                }*/
                

            }
        });
    }
}
