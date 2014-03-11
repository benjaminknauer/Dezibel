package de.dezibel.gui;

import de.dezibel.UpdateEntity;
import de.dezibel.data.Database;
import de.dezibel.data.Playlist;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.FocusAdapter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Class representing the "MyPlylists" Side Panel in the MainPanel. Shows all of
 * the user's created playlists.
 *
 * @author Benny
 */
public class MyListsPanel extends DragablePanel {

    private JLabel lbTitel;
    private JScrollPane scrollPane;
    private JTable tblPlaylists;
    private MyListsTableModel mltm;
    private JPopupMenu currentPopupMenu;
    private DezibelPanel dp;

    /**
     * Constructor + parent container
     *
     * @param parent this object's parent
     */
    public MyListsPanel(DezibelPanel parent) {
        super(parent);
        this.dp = parent;
        createComponents();
        createLayout();
        this.setBackground(DezibelColor.PanelBackground);

    }

    @Override

    public void refresh() {
        if (Database.getInstance().getLoggedInUser() != null) {
            LinkedList<Playlist> myPlaylists = Database.getInstance().getLoggedInUser()
                    .getCreatedPlaylists();
            LinkedList<Playlist> favoritePlaylists = Database.getInstance().getLoggedInUser()
                    .getFavoritePlaylists();
            for (Playlist currentPlaylist : favoritePlaylists) {
                if (!(myPlaylists.contains(currentPlaylist))) {
                    myPlaylists.add(currentPlaylist);
                }
            }
            mltm.setData(myPlaylists);
        }
    }

    /**
     * help method to create all components
     */
    private void createComponents() {
        lbTitel = new JLabel("Wiedergabelisten");
        mltm = new MyListsTableModel();
        tblPlaylists = new JTable(mltm);
        scrollPane = new JScrollPane(tblPlaylists);

        tblPlaylists.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (tblPlaylists.getSelectedRow() != -1) {
                    Playlist p = (Playlist) mltm.getValueAt(
                            tblPlaylists.getSelectedRow(), -1);
                    dp.showPlaylist(p);
                }
            }
        });

        tblPlaylists.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!e.isTemporary()) {
                    tblPlaylists.clearSelection();
                }
            }
        });

        tblPlaylists.addMouseListener(new MouseAdapter() {
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
                currentPopupMenu = contextMenu.getContextMenu(tblPlaylists, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());
            }
        });

    }

    /**
     * Help method to align all components
     */
    private void createLayout() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        lbTitel.setFont(DezibelFont.SIDEPANEL_TITLE);
        lbTitel.setAlignmentX(CENTER_ALIGNMENT);
        this.add(lbTitel);
        this.add(scrollPane);
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub
    }
}
