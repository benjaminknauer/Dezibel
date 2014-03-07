/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dezibel.gui;

import de.dezibel.UpdateEntity;
import de.dezibel.control.AlbumControl;
import de.dezibel.control.PlaylistControl;
import de.dezibel.data.Album;
import de.dezibel.data.Database;
import de.dezibel.data.Label;
import de.dezibel.data.Medium;
import de.dezibel.data.Playlist;
import de.dezibel.data.User;
import de.dezibel.gui.DezibelPanel;
import de.dezibel.gui.MenuItem;
import de.dezibel.gui.PlaylistMediaTableModel;
import de.dezibel.gui.PlaylistPanel;
import de.dezibel.gui.UploadDialog;
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
            if(table.getParent().getParent().getParent() instanceof PlaylistPanel){
                addPlaylistPanelMenuItems();
            }
        } else if (currentTableModel.getValueAt(rowNumber, -1) instanceof User) {
            createUserMenu();
        } else if (currentTableModel.getValueAt(rowNumber, -1) instanceof Label) {
            createLabelMenu();
        } else if (currentTableModel.getValueAt(rowNumber, -1) instanceof Album) {
            createAlbumMenu();
        } else if (currentTableModel.getValueAt(rowNumber, -1) instanceof Playlist) {
            createPlaylistMenu();
        }
        return currentPopupMenu;
    }

    private void createMediumMenu() {
        currentPopupMenu = new JPopupMenu();

        final Medium m = (Medium) currentTableModel.getValueAt(currentTable.getSelectedRow(), -1);
        if (m.getArtist() == Database.getInstance().getLoggedInUser()
                && (m.getPath() == null || m.getPath().isEmpty())) {
            JMenuItem menuItemUpload = new JMenuItem("Upload");
            menuItemUpload.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    UploadDialog ud = new UploadDialog(dp.getFrame(), null, m);
                    ud.setVisible(true);
                }
            });
            currentPopupMenu.add(menuItemUpload);
        }

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
                    dp.refresh(UpdateEntity.PLAYLIST);
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
                        dp.refresh(UpdateEntity.ALBUM);
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
                        dp.refresh(UpdateEntity.ALBUM);
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
                            dp.refresh(UpdateEntity.ALBUM);
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
                    dp.refresh(UpdateEntity.PLAYLIST);
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
                User user = (User) currentTableModel.getValueAt(
                        currentTable.getSelectedRow(), -1);
                dp.showProfile(user);
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

    private void createPlaylistMenu() {
        currentPopupMenu = new JPopupMenu();
        JMenuItem menuItemShow = new JMenuItem("Anzeigen");
        menuItemShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Playlist p = (Playlist) currentTableModel.getValueAt(
                        currentTable.getSelectedRow(), -1);
                if (p != null) {
                    dp.showPlaylist(p);
                }
            }
        });

        JMenuItem menuItemQueue = new JMenuItem("Warteschlange");
        menuItemQueue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Playlist p = (Playlist) currentTableModel.getValueAt(
                        currentTable.getSelectedRow(), -1);
                if (p != null) {
                    Player.getInstance().addMedialist(p.getList());
                    Player.getInstance().play();
                }
            }
        });

        JMenuItem menuItemRename = new JMenuItem("Umbenennen");
        menuItemRename.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Playlist p = (Playlist) currentTableModel.getValueAt(
                        currentTable.getSelectedRow(), -1);
                if (p != null) {
                    ImageIcon logoIcon = new ImageIcon(this.getClass().getResource("/img/mini-logo.png"));
                    String title = ((String) JOptionPane.showInputDialog(null,
                            "Titel der Wiedergabeliste", "Titel eingeben",
                            JOptionPane.QUESTION_MESSAGE, logoIcon, null, null)).trim();
                    if (title != null && !title.isEmpty()) {
                        p.setTitle(title);
                        dp.refresh(UpdateEntity.PLAYLIST);
                    }
                }
            }
        });

        JMenuItem menuItemDelete = new JMenuItem("Wiedergabeliste löschen");
        menuItemDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Playlist p = (Playlist) currentTableModel.getValueAt(
                        currentTable.getSelectedRow(), -1);
                if (p != null) {
                    Database.getInstance().deletePlaylist(p);
                    dp.refresh(UpdateEntity.PLAYLIST);
                }
            }
        });

        currentPopupMenu.add(menuItemShow);
        currentPopupMenu.add(menuItemQueue);
        currentPopupMenu.add(menuItemRename);
        currentPopupMenu.add(menuItemDelete);
    }
    
    private void addPlaylistPanelMenuItems(){
        JMenuItem deleteMediumAt = new JMenuItem("Nur hier entfernen");
        deleteMediumAt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Playlist p = ((PlaylistMediaTableModel) currentTableModel).getCurrentPlaylist();
                if (p != null) {
                    new PlaylistControl().removeMediumAt(p, currentTable.getSelectedRow());
                    dp.refresh(UpdateEntity.PLAYLIST);
                }
            }
        });
        
        
        JMenuItem removeMediumFromPlaylist = new JMenuItem("Komplett aus Wiedergabeliste entfernen");
        removeMediumFromPlaylist.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Playlist p = ((PlaylistMediaTableModel) currentTableModel).getCurrentPlaylist();
                if (p != null) {
                    new PlaylistControl().removeMedium(p, (Medium) currentTableModel.getValueAt(
                        currentTable.getSelectedRow(), -1));
                    dp.refresh(UpdateEntity.PLAYLIST);
                }
            }
        });
        
        currentPopupMenu.add(removeMediumFromPlaylist);
        currentPopupMenu.add(deleteMediumAt);
}
}