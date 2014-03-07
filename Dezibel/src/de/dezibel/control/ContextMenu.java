/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dezibel.control;

import de.dezibel.UpdateEntity;
import de.dezibel.data.Album;
import de.dezibel.data.Database;
import de.dezibel.data.Label;
import de.dezibel.data.Medium;
import de.dezibel.data.Playlist;
import de.dezibel.data.User;
import de.dezibel.gui.DezibelPanel;
import de.dezibel.gui.MenuItem;
import de.dezibel.gui.UploadDialog;
import de.dezibel.player.Player;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JRadioButtonMenuItem;

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
        } else if(currentTableModel.getValueAt(rowNumber, -1) instanceof Playlist){
            createPlaylistMenu();
        }
        return currentPopupMenu;
    }

    private void createMediumMenu() {
        currentPopupMenu = new JPopupMenu();
        
        final Medium m = (Medium) currentTableModel.getValueAt(currentTable.getSelectedRow(), -1);
        if (m.getArtist() == Database.getInstance().getLoggedInUser() &&
                (m.getPath() == null || m.getPath().isEmpty())) {
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
        JMenu menuRate = new JMenu("Bewerten");
        final JRadioButtonMenuItem menuItemRate1 = new JRadioButtonMenuItem("1 Stern",false);
        final JRadioButtonMenuItem menuItemRate2 = new JRadioButtonMenuItem("2 Stern",false);
        final JRadioButtonMenuItem menuItemRate3 = new JRadioButtonMenuItem("3 Stern",false);
        final JRadioButtonMenuItem menuItemRate4 = new JRadioButtonMenuItem("4 Stern",false);
        final JRadioButtonMenuItem menuItemRate5 = new JRadioButtonMenuItem("5 Stern",false);
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
        
        menuItemRate1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Medium m = (Medium) currentTableModel.getValueAt(
                        currentTable.getSelectedRow(), -1);
                m.rate(1, Database.getInstance().getLoggedInUser());
                menuItemRate1.setSelected(true);
                currentTableModel.fireTableCellUpdated(currentTable.getSelectedRow(), 5);
            }
        });
        
        menuItemRate2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Medium m = (Medium) currentTableModel.getValueAt(
                        currentTable.getSelectedRow(), -1);
                m.rate(2, Database.getInstance().getLoggedInUser());
                menuItemRate2.setSelected(true);
                currentTableModel.fireTableCellUpdated(currentTable.getSelectedRow(), 5);
            }
        });
        
        menuItemRate3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Medium m = (Medium) currentTableModel.getValueAt(
                        currentTable.getSelectedRow(), -1);
                m.rate(3, Database.getInstance().getLoggedInUser());
                menuItemRate3.setSelected(true);
                currentTableModel.fireTableCellUpdated(currentTable.getSelectedRow(), 5);
            }
        });
        
        menuItemRate4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Medium m = (Medium) currentTableModel.getValueAt(
                        currentTable.getSelectedRow(), -1);
                m.rate(4, Database.getInstance().getLoggedInUser());
                menuItemRate4.setSelected(true);
                currentTableModel.fireTableCellUpdated(currentTable.getSelectedRow(), 5);
            }
        });
        
        menuItemRate5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Medium m = (Medium) currentTableModel.getValueAt(
                        currentTable.getSelectedRow(), -1);
                m.rate(5, Database.getInstance().getLoggedInUser());
                menuItemRate5.setSelected(true);
                currentTableModel.fireTableCellUpdated(currentTable.getSelectedRow(), 5);
            }
        });

        currentPopupMenu.add(menuRate);
        ButtonGroup menuRateButtonGroup = new ButtonGroup();
        menuRate.add(menuItemRate1);
        menuRateButtonGroup.add(menuItemRate1);
        menuRate.add(menuItemRate2);
        menuRateButtonGroup.add(menuItemRate2);
        menuRate.add(menuItemRate3);
        menuRateButtonGroup.add(menuItemRate3);
        menuRate.add(menuItemRate4);
        menuRateButtonGroup.add(menuItemRate4);
        menuRate.add(menuItemRate5);
        menuRateButtonGroup.add(menuItemRate5);
        
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
//        menuAddToPlaylist.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//            }
//        });

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
    
    private void createPlaylistMenu(){
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
        
        currentPopupMenu.add(menuItemShow);
        currentPopupMenu.add(menuItemQueue);
    }
}