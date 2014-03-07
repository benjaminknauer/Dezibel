package de.dezibel.gui;

import de.dezibel.control.Search;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * The panel that displays the search components and results.
 *
 * @author Tobias, Pascal, Richard
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
    private JRadioButton rbAlbumAlphabetical;
    private JPanel pnSorting;
    private JTable tableResults;
    private MediaTableModel tableModelSong;
    private UserTableModel tableModelUser;
    private LabelTableModel tableModelLabel;
    private AlbumTableModel tableModelAlbum;
    private JScrollPane tablePanel;
    private GroupLayout layout;

    private JPanel pnSortingMedium;
    private JPanel pnSortingUser;
    private JPanel pnSortingLabel;
    private JPanel pnSortingAlbum;

    private JPopupMenu currentPopupMenu;

    public SearchPanel(DezibelPanel parent) {
        super(parent);
        this.createComponents();
        this.createLayout();
        this.createListener();
    }

    private void createComponents() {
        String[] choices = {"Musik", "Benutzer", "Label", "Album"};

        tfSearch = new JTextField("Suche...");
        tfSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (tfSearch.getText().equals("Suche...")) {
                    tfSearch.setText("");
                }
            }
        });

        cbFilter = new JComboBox<>(choices);
        bnSearch = new JButton("Suchen");
        JLabel lblSortingMedium = new JLabel("Sortierung: ");
        JLabel lblSortingUser = new JLabel("Sortierung: ");
        JLabel lblSortingLabel = new JLabel("Sortierung: ");
        JLabel lblSortingAlbum = new JLabel("Sortierung: ");
        rbSongAlphabetical = new JRadioButton("Alphabetisch");
        rbUserAlphabetical = new JRadioButton("Alphabetisch");
        rbLabelAlphabetical = new JRadioButton("Alphabetisch");
        rbAlbumAlphabetical = new JRadioButton("Alphabetisch");
        rbRating = new JRadioButton("Bewertung");
        rbRating.setBackground(Color.WHITE);
        rbUploadDate = new JRadioButton("Hochladedatum");
        rbUploadDate.setBackground(Color.WHITE);
        tableModelSong = new MediaTableModel();
        tableModelUser = new UserTableModel();
        tableModelLabel = new LabelTableModel();
        tableModelAlbum = new AlbumTableModel();
        tableResults = new JTable(tableModelSong);

        tableResults.addMouseListener(new MouseAdapter() {
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
                currentPopupMenu = contextMenu.getContextMenu(tableResults, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());
            }
        });

        tablePanel = new JScrollPane(tableResults);
        tablePanel.setViewportView(tableResults);

        ButtonGroup bnGroupMedium = new ButtonGroup();
        ButtonGroup bnGroupUser = new ButtonGroup();
        ButtonGroup bnGroupLabel = new ButtonGroup();
        ButtonGroup bnGroupAlbum = new ButtonGroup();

        bnGroupMedium.add(rbSongAlphabetical);
        bnGroupMedium.add(rbRating);
        bnGroupMedium.add(rbUploadDate);
        bnGroupUser.add(rbUserAlphabetical);
        bnGroupLabel.add(rbLabelAlphabetical);
        bnGroupAlbum.add(rbAlbumAlphabetical);

        rbSongAlphabetical.setSelected(true);
        rbSongAlphabetical.setBackground(Color.WHITE);
        rbUserAlphabetical.setSelected(true);
        rbUserAlphabetical.setBackground(Color.WHITE);
        rbLabelAlphabetical.setSelected(true);
        rbLabelAlphabetical.setBackground(Color.WHITE);
        rbAlbumAlphabetical.setSelected(true);
        rbAlbumAlphabetical.setBackground(Color.WHITE);

        pnSorting = new JPanel(new CardLayout());
        pnSortingMedium = new JPanel();
        pnSortingMedium.setBackground(Color.WHITE);
        pnSortingUser = new JPanel();
        pnSortingUser.setBackground(Color.WHITE);
        pnSortingLabel = new JPanel();
        pnSortingLabel.setBackground(Color.WHITE);
        pnSortingAlbum = new JPanel();
        pnSortingAlbum.setBackground(Color.WHITE);

        pnSorting.add(pnSortingMedium, "Musik");
        pnSorting.add(pnSortingUser, "Benutzer");
        pnSorting.add(pnSortingLabel, "Label");
        pnSorting.add(pnSortingAlbum, "Album");

        GroupLayout layoutMedium = new GroupLayout(pnSortingMedium);
        layoutMedium.setAutoCreateGaps(true);
        layoutMedium.setAutoCreateContainerGaps(true);
        layoutMedium.setHorizontalGroup(
                layoutMedium.createParallelGroup()
                .addGroup(layoutMedium.createSequentialGroup()
                        .addComponent(lblSortingMedium)
                        .addComponent(rbSongAlphabetical)
                        .addComponent(rbRating)
                        .addComponent(rbUploadDate)));
        layoutMedium.setVerticalGroup(layoutMedium.createParallelGroup()
                .addGroup(layoutMedium.createParallelGroup()
                        .addComponent(lblSortingMedium)
                        .addComponent(rbSongAlphabetical)
                        .addComponent(rbRating)
                        .addComponent(rbUploadDate)));
        pnSortingMedium.setLayout(layoutMedium);

        GroupLayout layoutUser = new GroupLayout(pnSortingUser);
        layoutUser.setAutoCreateContainerGaps(true);
        layoutUser.setAutoCreateGaps(true);
        layoutUser.setHorizontalGroup(layoutUser.createParallelGroup()
                .addGroup(layoutUser.createSequentialGroup()
                        .addComponent(lblSortingUser)
                        .addComponent(rbUserAlphabetical)));
        layoutUser.setVerticalGroup(layoutUser.createParallelGroup()
                .addGroup(layoutUser.createParallelGroup()
                        .addComponent(lblSortingUser)
                        .addComponent(rbUserAlphabetical)));
        pnSortingUser.setLayout(layoutUser);

        GroupLayout layoutLabel = new GroupLayout(pnSortingLabel);
        layoutLabel.setAutoCreateContainerGaps(true);
        layoutLabel.setAutoCreateGaps(true);
        layoutLabel.setHorizontalGroup(layoutLabel.createParallelGroup()
                .addGroup(layoutLabel.createSequentialGroup()
                        .addComponent(lblSortingLabel)
                        .addComponent(rbLabelAlphabetical)));
        layoutLabel.setVerticalGroup(layoutLabel.createParallelGroup()
                .addGroup(layoutLabel.createParallelGroup()
                        .addComponent(lblSortingLabel)
                        .addComponent(rbLabelAlphabetical)));
        pnSortingLabel.setLayout(layoutLabel);

        GroupLayout layoutPlaylist = new GroupLayout(pnSortingAlbum);
        layoutPlaylist.setAutoCreateContainerGaps(true);
        layoutPlaylist.setAutoCreateGaps(true);
        layoutPlaylist.setHorizontalGroup(layoutPlaylist.createParallelGroup()
                .addGroup(layoutPlaylist.createSequentialGroup()
                        .addComponent(lblSortingAlbum)
                        .addComponent(rbAlbumAlphabetical)));
        layoutPlaylist.setVerticalGroup(layoutPlaylist.createParallelGroup()
                .addGroup(layoutPlaylist.createParallelGroup()
                        .addComponent(lblSortingAlbum)
                        .addComponent(rbAlbumAlphabetical)));
        pnSortingAlbum.setLayout(layoutPlaylist);

    }

    private void createLayout() {
        this.removeAll();
        layout = new GroupLayout(this);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(tfSearch, 256, 256, 256)
                        .addComponent(cbFilter, 128, 128, 128)
                        .addComponent(bnSearch, 80, 80, 80))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(pnSorting))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(tablePanel))
        );

        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(tfSearch, 32, 32, 32)
                                .addComponent(cbFilter, 32, 32, 32)
                                .addComponent(bnSearch, 32, 32, 32))
                        .addComponent(pnSorting)
                        .addComponent(tablePanel)
                        .addGap(0, 10, 10000))
        );
        this.setLayout(layout);
    }

    private void createListener() {
        cbFilter.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                CardLayout cl = (CardLayout) pnSorting.getLayout();
                cl.show(pnSorting, (String) e.getItem());
                switch (e.getItem().toString()) {
                    case "Musik":
                        tableResults.setModel(tableModelSong);
                        break;
                    case "Benutzer":
                        tableResults.setModel(tableModelUser);
                        break;
                    case "Label":
                        tableResults.setModel(tableModelLabel);
                        break;
                    case "Album":
                        tableResults.setModel(tableModelAlbum);
                        break;
                }
            }
        });

        bnSearch.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                Search searchcontrol = new Search();
                LinkedList result;
                DefaultTableModel model = null;
                int sortation = 0;

                switch (cbFilter.getSelectedItem().toString()) {
                    case "Musik":
                        model = tableModelSong;
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
                        tableModelSong.setData(result);
                        break;
                    case "Benutzer":
                        model = tableModelUser;
                        result = searchcontrol.searchForUsers(tfSearch.getText(), 0);
                        tableModelUser.setData(result);
                        break;
                    case "Label":
                        model = tableModelLabel;
                        result = searchcontrol.searchForLabels(tfSearch.getText(), 0);
                        tableModelLabel.setData(result);
                        break;
                    case "Album":
                        model = tableModelAlbum;
                        result = searchcontrol.searchForAlbums(tfSearch.getText(), 0);
                        tableModelAlbum.setData(result);
                        break;
                }

                tableResults.setModel(model);
            }
        });
    }

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}
}
