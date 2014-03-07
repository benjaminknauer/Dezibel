package de.dezibel.gui;

import de.dezibel.data.Medium;
import de.dezibel.data.Playlist;
import de.dezibel.player.Player;

/**
 *
 * @author Benny
 */
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;

public class PlaylistPanel extends DragablePanel {

    JLabel lbTitle;
    JLabel lbCreator;
    JTable tblPlaylistMedia;
    JScrollPane spPlaylistMedia;
    DezibelPanel dp;
    PlaylistMediaTableModel model;
    JPopupMenu currentPopupMenu;
    Playlist currentPlaylist;

    public PlaylistPanel(DezibelPanel parent, Playlist currentPlaylist) {
        super(parent);
        this.dp = parent;
        createComponents(currentPlaylist);
        createLayout();

    }

    private void createComponents(Playlist currentPlaylist) {
        this.currentPlaylist = currentPlaylist;
        lbTitle = new JLabel(currentPlaylist.getTitle());
        String creatorString = currentPlaylist.getCreator().getPseudonym();
        if (creatorString == null || creatorString.trim().isEmpty()) {
            creatorString = currentPlaylist.getCreator().getFirstname() + " "
                    + currentPlaylist.getCreator().getLastname();
        }
        lbCreator = new JLabel(creatorString);
        model = new PlaylistMediaTableModel();
        model.setData(currentPlaylist);
        tblPlaylistMedia = new JTable(model);
        spPlaylistMedia = new JScrollPane(tblPlaylistMedia);

        tblPlaylistMedia.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    Medium m = (Medium) model.getValueAt(
                            tblPlaylistMedia.getSelectedRow(), -1);
                    if (m != null) {
                        //TODO Song an der Anfang der Queue
                        Player.getInstance().clearPlaylist();
                        Player.getInstance().addMedium(m);

                        Player.getInstance().play();
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
                currentPopupMenu = contextMenu.getContextMenu(tblPlaylistMedia, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());
            }
        });


    }

    private void createLayout() {
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(gbl);

        gbc.weighty = 0.1;
        gbc.insets = new Insets(0, 30, 0, 0);
        this.add(lbTitle, gbc);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(0, 0, 0, 30);
        gbc.anchor = GridBagConstraints.EAST;
        this.add(lbCreator, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.weighty = 0.9;
        gbc.weightx = 1;
        this.add(spPlaylistMedia, gbc);
    }

    @Override
    public void refresh() {
        model.setData(currentPlaylist);
        lbTitle = new JLabel(currentPlaylist.getTitle());
        String creatorString = currentPlaylist.getCreator().getPseudonym();
        if (creatorString == null || creatorString.trim().isEmpty()) {
            creatorString = currentPlaylist.getCreator().getFirstname() + " "
                    + currentPlaylist.getCreator().getLastname();
        }
        lbCreator = new JLabel(creatorString);
    }
}
