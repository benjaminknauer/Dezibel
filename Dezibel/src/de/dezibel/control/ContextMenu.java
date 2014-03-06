/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dezibel.control;

import de.dezibel.data.Album;
import de.dezibel.data.Database;
import de.dezibel.data.Label;
import de.dezibel.data.Medium;
import de.dezibel.data.Playlist;
import de.dezibel.data.User;
import de.dezibel.gui.DezibelPanel;
import de.dezibel.gui.MenuItem;
import de.dezibel.player.Player;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Benny
 */
public class ContextMenu {

    DefaultTableModel currentTableModel;
    JPopupMenu currentPopupMenu;
    JTable currentTable;
    DezibelPanel dp;

    public ContextMenu(DezibelPanel parent) {
        dp = parent;
    }

    public JPopupMenu getContextMenu(JTable table, MouseEvent me) {
        currentTable = table;
        currentTableModel = (DefaultTableModel) table.getModel();

        Point p = me.getPoint();
        int rowNumber = table.rowAtPoint(p);
        table.getSelectionModel().setSelectionInterval(rowNumber, rowNumber);

        if (currentTableModel.getValueAt(rowNumber, -1) instanceof Medium) {
            createMediumMenu();
        } else if (currentTableModel.getValueAt(rowNumber, -1) instanceof User) {
            createUserMenu();
        } else if (currentTableModel.getValueAt(rowNumber, -1) instanceof Label) {
            createLabelMenu();
        } else if (currentTableModel.getValueAt(rowNumber, -1) instanceof Album) {
            createAlbumMenu();
        }
        return currentPopupMenu;
    }

    private void createMediumMenu() {
        currentPopupMenu = new JPopupMenu();
        JMenuItem menuItemPlay = new JMenuItem("Warteschlange");
        JMenu menuAddToPlaylist = new JMenu("zur Wiedergabeliste hinzufügen");
        JMenuItem menuItemNewPlaylist = new JMenuItem("neue Wiedergabeliste");


        menuItemPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Medium m = (Medium) currentTableModel.getValueAt(
                        currentTable.getSelectedRow(), -1);
                if (m != null) {
                    Player.getInstance().addMedium(m);
                    Player.getInstance().play();
                }
            }
        });

        menuItemNewPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon logoIcon = new ImageIcon(this.getClass().getResource("/img/mini-logo.png"));
                String title = ((String) JOptionPane.showInputDialog(null,
                        "Titel der Wiedergabeliste", "Titel eingeben",
                        JOptionPane.QUESTION_MESSAGE, logoIcon, null, null)).trim();
                if (title != null && !title.isEmpty()) {
                    new PlaylistControl().createPlaylist(title,
                            (Medium) currentTableModel.getValueAt(
                            currentTable.getSelectedRow(), -1));
                }
            }
        });


        currentPopupMenu.add(menuItemPlay);
        currentPopupMenu.add(menuAddToPlaylist);
        menuAddToPlaylist.add(menuItemNewPlaylist);
        menuAddToPlaylist.addSeparator();
        menuAddToPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        if (Database.getInstance().getLoggedInUser()
                .equals(((Medium) currentTableModel.getValueAt(
                currentTable.getSelectedRow(), -1)).getArtist())) {
            JMenu menuAddToAlbum = new JMenu("zu Album hinzufügen");
            JMenuItem menuItemNewAlbum = new JMenuItem("neues Album");

            menuItemNewAlbum.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ImageIcon logoIcon = new ImageIcon(this.getClass().getResource("/img/mini-logo.png"));
                    String title = ((String) JOptionPane.showInputDialog(null,
                            "Titel des Albums", "Titel eingeben",
                            JOptionPane.QUESTION_MESSAGE, logoIcon, null, null)).trim();
                    if (title != null && !title.isEmpty()) {
                        new AlbumControl().createAlbum(title,
                                (Medium) currentTableModel.getValueAt(
                                currentTable.getSelectedRow(), -1), null);
                    }
                }
            });

            currentPopupMenu.add(menuAddToAlbum);

            menuAddToAlbum.add(menuItemNewAlbum);

            menuAddToAlbum.addSeparator();

            MenuItem currentMenuItem;
            for (Album currentAlbum : Database.getInstance().getLoggedInUser().getCreatedAlbums()) {
                currentMenuItem = new MenuItem(currentAlbum.getTitle(), currentAlbum);
                currentMenuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new AlbumControl().addMediumToAlbum((Medium) currentTableModel.getValueAt(
                                currentTable.getSelectedRow(), -1),
                                (Album) ((MenuItem) e.getSource()).getEntity());
                    }
                });
                menuAddToAlbum.add(currentMenuItem);
            }

            menuAddToAlbum.addSeparator();

            for (Label currentLabel : Database.getInstance().getLoggedInUser().getManagedLabels()) {
                for (Album currentAlbum : currentLabel.getAlbums()) {
                    currentMenuItem = new MenuItem(currentAlbum.getTitle(), currentAlbum);
                    currentMenuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            new AlbumControl().addMediumToAlbum((Medium) currentTableModel.getValueAt(
                                    currentTable.getSelectedRow(), -1),
                                    (Album) ((MenuItem) e.getSource()).getEntity());
                        }
                    });
                    menuAddToAlbum.add(currentMenuItem);
                }
                menuAddToAlbum.addSeparator();
            }
        }

        MenuItem currentMenuItem;
        for (Playlist currentPlaylist : Database.getInstance().getLoggedInUser().getCreatedPlaylists()) {
            currentMenuItem = new MenuItem(currentPlaylist.getTitle(), currentPlaylist);
            currentMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new PlaylistControl().addMediumToPlaylist((Medium) currentTableModel.getValueAt(
                            currentTable.getSelectedRow(), -1),
                            (Playlist) ((MenuItem) e.getSource()).getEntity());
                }
            });
            menuAddToPlaylist.add(currentMenuItem);
        }
    }

    private void createUserMenu() {
        currentPopupMenu = new JPopupMenu();
        JMenuItem menuItemShowUser = new JMenuItem("Anzeigen");
        menuItemShowUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //dp.showProfile();
            }
        });
        currentPopupMenu.add(menuItemShowUser);
    }

    private void createLabelMenu() {
        currentPopupMenu = new JPopupMenu();
        JMenuItem menuItemShowLabel = new JMenuItem("Anzeigen");
        menuItemShowLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Labelprofil des ausgewählten Labels anzeigen
            }
        });
        currentPopupMenu.add(menuItemShowLabel);
    }

    private void createAlbumMenu() {
        currentPopupMenu = new JPopupMenu();
        JMenuItem menuItemQueue = new JMenuItem("Warteschlange");
        menuItemQueue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Album a = (Album) currentTableModel.getValueAt(
                        currentTable.getSelectedRow(), -1);
                if (a != null) {
                    Player.getInstance().addMedialist(a.getMediaList());
                    Player.getInstance().play();
                }
            }
        });
        currentPopupMenu.add(menuItemQueue);
    }
}