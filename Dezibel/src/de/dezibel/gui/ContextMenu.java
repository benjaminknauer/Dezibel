package de.dezibel.gui;

import de.dezibel.UpdateEntity;
import de.dezibel.control.AdminControl;
import de.dezibel.control.AlbumControl;
import de.dezibel.control.ApplicationControl;
import de.dezibel.control.PlaylistControl;
import de.dezibel.data.Album;
import de.dezibel.data.Application;
import de.dezibel.data.Database;
import de.dezibel.data.Label;
import de.dezibel.data.Medium;
import de.dezibel.data.News;
import de.dezibel.data.Playlist;
import de.dezibel.data.User;
import de.dezibel.player.Player;
import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Handles the right click actions for all panels
 *
 * @author Benny, Tobias, Henner, Bastian, Alex
 */
public class ContextMenu {

    DefaultTableModel currentTableModel;
    JPopupMenu currentPopupMenu;
    JTable currentTable;
    DezibelPanel dp;

    /**
     * Constructor
     *
     * @param parent Parent panel
     */
    public ContextMenu(DezibelPanel parent) {
        dp = parent;
    }

    /**
     * Returns the specific context menu depends on what clicked on
     *
     * @param table Table to work on
     * @param me MouseEvent to check where clicked
     * @return The specific context menu
     */
    public JPopupMenu getContextMenu(JTable table, MouseEvent me) {
        currentTable = table;
        currentTableModel = (DefaultTableModel) table.getModel();

        Point p = me.getPoint();
        int rowNumber = table.rowAtPoint(p);
        table.getSelectionModel().setSelectionInterval(rowNumber, rowNumber);

        if (currentTableModel.getValueAt(rowNumber, -1) instanceof Medium) {
            createMediumMenu();
            if (table.getParent().getParent().getParent() instanceof PlaylistPanel) {
                addPlaylistPanelMenuItems();
            }
            if (table.getParent().getParent().getParent() instanceof PlayerPanel) {
                addPlayerPanelMenuItems();
            }
        } else if (currentTableModel.getValueAt(rowNumber, -1) instanceof User) {
            createUserMenu();
        } else if (currentTableModel.getValueAt(rowNumber, -1) instanceof Label) {
            createLabelMenu();
        } else if (currentTableModel.getValueAt(rowNumber, -1) instanceof Album) {
            createAlbumMenu();
        } else if (currentTableModel.getValueAt(rowNumber, -1) instanceof Playlist) {
            createPlaylistMenu();
        } else if (currentTableModel.getValueAt(rowNumber, -1) instanceof News) {
            createNewsMenu();
        } else if (currentTableModel.getValueAt(rowNumber, -1) instanceof Application) {
            createApplicationsMenu();
        }
        return currentPopupMenu;
    }

    /**
     * Creates the MediumPopupMenu which handles the right clicks on media
     */
    private void createMediumMenu() {
        currentPopupMenu = new JPopupMenu();

        final Medium m = (Medium) currentTableModel.getValueAt(currentTable.getSelectedRow(), -1);
        if (m.getArtist() == Database.getInstance().getLoggedInUser()
                && (m.getPath() == null || m.getPath().isEmpty())) {
            JMenuItem menuItemUpload = new JMenuItem("Upload");
            menuItemUpload.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    UploadDialog ud = new UploadDialog(dp.getFrame(), null, m, dp);
                    ud.setVisible(true);
                }
            });
            currentPopupMenu.add(menuItemUpload);
        }

        JMenuItem menuItemShow = new JMenuItem("Eigenschaften anzeigen");
        JMenuItem menuItemPlay = new JMenuItem("Warteschlange");
        JMenuItem menuItemPlayNext = new JMenuItem("Als nächsten abspielen");
        JMenu menuRate = new JMenu("Bewerten");
        final JRadioButtonMenuItem menuItemRate1 = new JRadioButtonMenuItem("1 Stern", false);
        final JRadioButtonMenuItem menuItemRate2 = new JRadioButtonMenuItem("2 Sterne", false);
        final JRadioButtonMenuItem menuItemRate3 = new JRadioButtonMenuItem("3 Sterne", false);
        final JRadioButtonMenuItem menuItemRate4 = new JRadioButtonMenuItem("4 Sterne", false);
        final JRadioButtonMenuItem menuItemRate5 = new JRadioButtonMenuItem("5 Sterne", false);
        JMenu menuAddToPlaylist = new JMenu("zur Wiedergabeliste hinzufügen");
        JMenuItem menuItemNewPlaylist = new JMenuItem("neue Wiedergabeliste");
        JMenuItem menuItemComment = new JMenuItem("Kommentieren");

        menuItemShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Medium m = (Medium) currentTableModel.getValueAt(
                        currentTable.getSelectedRow(), -1);
                if (m != null) {
                    dp.showMedium(m);
                }
            }
        });

        currentPopupMenu.add(menuItemShow);

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

        currentPopupMenu.add(menuItemPlay);

        menuItemPlayNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Medium m = (Medium) currentTableModel.getValueAt(
                        currentTable.getSelectedRow(), -1);
                if (m != null) {
                    Player.getInstance().addMediumAsNext(m);
                    if (!Player.getInstance().isPlaying()) {
                        Player.getInstance().play();
                    }
                }
            }
        });

        currentPopupMenu.add(menuItemPlayNext);

        if (m.getArtist() != Database.getInstance().getLoggedInUser()) {
            menuItemRate1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Medium m = (Medium) currentTableModel.getValueAt(
                            currentTable.getSelectedRow(), -1);
                    m.rate(1, Database.getInstance().getLoggedInUser());
                    Database.getInstance().getLoggedInUser().removeFavoriteMedium(m);
                    menuItemRate1.setSelected(true);
                    currentTableModel.fireTableCellUpdated(currentTable.getSelectedRow(), 5);
                    dp.refresh(UpdateEntity.FAVORITES);
                }
            });

            menuItemRate2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Medium m = (Medium) currentTableModel.getValueAt(
                            currentTable.getSelectedRow(), -1);
                    m.rate(2, Database.getInstance().getLoggedInUser());
                    Database.getInstance().getLoggedInUser().removeFavoriteMedium(m);
                    menuItemRate2.setSelected(true);
                    currentTableModel.fireTableCellUpdated(currentTable.getSelectedRow(), 5);
                    dp.refresh(UpdateEntity.FAVORITES);
                }
            });

            menuItemRate3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Medium m = (Medium) currentTableModel.getValueAt(
                            currentTable.getSelectedRow(), -1);
                    m.rate(3, Database.getInstance().getLoggedInUser());
                    Database.getInstance().getLoggedInUser().removeFavoriteMedium(m);
                    menuItemRate3.setSelected(true);
                    currentTableModel.fireTableCellUpdated(currentTable.getSelectedRow(), 5);
                    dp.refresh(UpdateEntity.FAVORITES);
                }
            });

            menuItemRate4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Medium m = (Medium) currentTableModel.getValueAt(
                            currentTable.getSelectedRow(), -1);
                    m.rate(4, Database.getInstance().getLoggedInUser());
                    Database.getInstance().getLoggedInUser().removeFavoriteMedium(m);
                    menuItemRate4.setSelected(true);
                    currentTableModel.fireTableCellUpdated(currentTable.getSelectedRow(), 5);
                    dp.refresh(UpdateEntity.FAVORITES);
                }
            });

            menuItemRate5.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Medium m = (Medium) currentTableModel.getValueAt(
                            currentTable.getSelectedRow(), -1);
                    m.rate(5, Database.getInstance().getLoggedInUser());
                    Database.getInstance().getLoggedInUser().addFavoriteMedium(m);
                    menuItemRate5.setSelected(true);
                    currentTableModel.fireTableCellUpdated(currentTable.getSelectedRow(), 5);
                    dp.refresh(UpdateEntity.FAVORITES);
                }
            });
            currentPopupMenu.add(menuRate);
        }

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
                }
                dp.refresh(UpdateEntity.PLAYLIST);
            }
        });

        currentPopupMenu.add(menuAddToPlaylist);
        menuAddToPlaylist.add(menuItemNewPlaylist);
        menuAddToPlaylist.addSeparator();
        menuAddToPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        User currentMediumArtist = ((Medium) currentTableModel.getValueAt(
                currentTable.getSelectedRow(), -1)).getArtist();

        if (Database.getInstance().getLoggedInUser() == currentMediumArtist
                || isManagedArtist(currentMediumArtist)) {
            JMenu menuAddToAlbum = new JMenu("zu Album hinzufügen");
            JMenuItem menuItemNewAlbum = new JMenuItem("neues Album");

            menuItemNewAlbum.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new CreateAlbumDialog(dp.getFrame(), (Medium) currentTableModel.getValueAt(
                            currentTable.getSelectedRow(), -1), dp).setVisible(true);
                    dp.refresh(UpdateEntity.ALBUM);
                }
            });


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


            for (Label currentLabel : Database.getInstance().getLoggedInUser().getManagedLabels()) {
                for (Album currentAlbum : currentLabel.getAlbums()) {
                    System.out.println(currentAlbum);
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
                for (User currentArtist : currentLabel.getArtists()) {
                    for (Album currentAlbum : currentArtist.getCreatedAlbums()) {

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
                }
            }

            currentPopupMenu.add(menuAddToAlbum);
        }
        MenuItem currentMenuItem;
        for (Playlist currentPlaylist : Database.getInstance()
                .getLoggedInUser().getCreatedPlaylists()) {
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

        menuItemComment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommentDialog cd = new CommentDialog(dp.getFrame(), dp);
                cd.commentMedia(m);
                cd.setVisible(true);
            }
        });

        currentPopupMenu.add(menuItemComment);

        // Admin controls
        if (Database.getInstance().getLoggedInUser().isAdmin()) {
            if (m.isLocked()) {
                JMenuItem unlockItem = new JMenuItem("Entsperren");
                unlockItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int ret = JOptionPane.showConfirmDialog(currentTable.getParent(),
                                "Soll das Medium wirklich entsperrt werden?",
                                "Medium entsperren", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE);
                        if (ret == JOptionPane.YES_OPTION) {
                            new AdminControl().unlock(m);
                        }
                    }
                });
                currentPopupMenu.add(unlockItem);
            } else {
                JMenuItem lockItem = new JMenuItem("Sperren");
                lockItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JPanel detailPanel = new JPanel();
                        detailPanel.setLayout(new BorderLayout());
                        JLabel lblReason = new JLabel("Grund: ");
                        JTextArea txtReason = new JTextArea();
                        JScrollPane scrollPane = new JScrollPane(txtReason);
                        scrollPane.setPreferredSize(new Dimension(300, 320));
                        detailPanel.add(lblReason, BorderLayout.NORTH);
                        detailPanel.add(scrollPane, BorderLayout.SOUTH);
                        int ret = JOptionPane.showConfirmDialog(currentTable.getParent(),
                                detailPanel, "Medium sperren",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.PLAIN_MESSAGE);
                        if (ret == JOptionPane.OK_OPTION) {
                            new AdminControl().lock(m, txtReason.getText());
                            JOptionPane.showMessageDialog(currentTable.getParent(),
                                    "Das Medium wurde gesperrt!", "Medium gesperrt",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                });
                currentPopupMenu.add(lockItem);
            }
        }
    }

    /**
     * Adds the PopupMenu which handles the right clicks on media in the player
     * playlist to the MediumPopupMenu
     */
    private void addPlayerPanelMenuItems() {
        JMenuItem menuItemRemove = new JMenuItem("Entfernen");
        JMenuItem menuItemDuplicate = new JMenuItem("Duplizieren");
        JMenuItem menuItemMoveUp = new JMenuItem("Hochschieben");
        JMenuItem menuItemMoveDown = new JMenuItem("Runterschieben");

        menuItemRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player.getInstance().removeMedium(currentTable.getSelectedRow());
            }
        });

        menuItemDuplicate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player.getInstance().addMedium((Medium) currentTable.getValueAt(currentTable.getSelectedRow(), -1));
            }
        });

        menuItemMoveUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player.getInstance().moveUp(currentTable.getSelectedRow());
            }
        });

        menuItemMoveDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player.getInstance().moveDown(currentTable.getSelectedRow());
            }
        });

        currentPopupMenu.remove(0);
        currentPopupMenu.add(menuItemRemove);
        currentPopupMenu.add(menuItemDuplicate);
        currentPopupMenu.add(menuItemMoveUp);
        currentPopupMenu.add(menuItemMoveDown);
    }

    /**
     * Creates the UserPopupMenu which handles the right clicks on users
     */
    private void createUserMenu() {
        final User selectedUser = (User) currentTableModel.getValueAt(currentTable.getSelectedRow(), -1);

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

        if (selectedUser.isArtist() && Database.getInstance().getLoggedInUser().isLabelManager()) {
            JMenu recruitForLabelMenu = new JMenu("für Label anwerben");
            currentPopupMenu.add(recruitForLabelMenu);

            for (final Label currentLabel : Database.getInstance().getLoggedInUser().getManagedLabels()) {
                if (selectedUser.hasApplied(currentLabel) || selectedUser.hasPublisher(currentLabel)) {
                    continue;
                }

                JMenuItem currentLabelMenuItem = new JMenuItem(currentLabel.getName());
                currentLabelMenuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new ApplicationDialog(dp.getFrame(), selectedUser, currentLabel, false).setVisible(true);
                        dp.refresh(UpdateEntity.APPLICATION);
                    }
                });
                recruitForLabelMenu.add(currentLabelMenuItem);
            }
            if (recruitForLabelMenu.getMenuComponentCount() < 1) {
                currentPopupMenu.remove(recruitForLabelMenu);
            }

        }

        if (Database.getInstance().getLoggedInUser().isLabelManager()) {
            JMenu menuItemManager = new JMenu("Als Labelmanager einstellen");

            MenuItem currentMenuItem;
            for (final Label currentLabel : Database.getInstance().getLoggedInUser().getManagedLabels()) {
                currentMenuItem = new MenuItem(currentLabel.getName(), currentLabel);
                currentMenuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        User u = (User) currentTableModel.getValueAt(
                                currentTable.getSelectedRow(), -1);
                        u.addManagerLabel(currentLabel);
                    }
                });
                menuItemManager.add(currentMenuItem);
            }

            currentPopupMenu.add(menuItemManager);
        }

        // Admin controls
        if (Database.getInstance().getLoggedInUser().isAdmin()) {
            if (selectedUser.isLocked()) {
                JMenuItem unlockItem = new JMenuItem("Entsperren");
                unlockItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int ret = JOptionPane.showConfirmDialog(currentTable.getParent(),
                                "Soll der Benutzer wirklich entsperrt werden?",
                                "Benutzer entsperren", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE);
                        if (ret == JOptionPane.YES_OPTION) {
                            new AdminControl().unlock(selectedUser);
                        }
                    }
                });
                currentPopupMenu.add(unlockItem);
            } else {
                JMenuItem lockItem = new JMenuItem("Sperren");
                lockItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JPanel detailPanel = new JPanel();
                        detailPanel.setLayout(new BorderLayout());
                        JLabel lblReason = new JLabel("Grund: ");
                        JTextArea txtReason = new JTextArea();
                        JScrollPane scrollPane = new JScrollPane(txtReason);
                        scrollPane.setPreferredSize(new Dimension(300, 320));
                        detailPanel.add(lblReason, BorderLayout.NORTH);
                        detailPanel.add(scrollPane, BorderLayout.SOUTH);
                        int ret = JOptionPane.showConfirmDialog(currentTable.getParent(),
                                detailPanel, "Benutzer sperren",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.PLAIN_MESSAGE);
                        if (ret == JOptionPane.OK_OPTION) {
                            new AdminControl().lock(selectedUser, txtReason.getText());
                            JOptionPane.showMessageDialog(currentTable.getParent(),
                                    "Der Benutzer wurde gesperrt!", "Benutzer gesperrt",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                });
                currentPopupMenu.add(lockItem);
            }
        }
    }

    /**
     * Creates the LabelPopupMenu which handles the right clicks on labels
     */
    private void createLabelMenu() {
        final Label selectedLabel = (Label) currentTableModel.getValueAt(currentTable.getSelectedRow(), -1);

        currentPopupMenu = new JPopupMenu();
        JMenuItem menuItemShowLabel = new JMenuItem("Anzeigen");
        menuItemShowLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Label label = (Label) currentTableModel.getValueAt(
                        currentTable.getSelectedRow(), -1);
                dp.showProfile(label);
            }
        });
        currentPopupMenu.add(menuItemShowLabel);

        if (Database.getInstance().getLoggedInUser().isArtist() && !Database.getInstance().getLoggedInUser().hasApplied(selectedLabel)
                && !Database.getInstance().getLoggedInUser().hasPublisher(selectedLabel)) {
            JMenuItem menuItemApply = new JMenuItem("Als Künstler bewerben");
            menuItemApply.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ApplicationDialog(dp.getFrame(), Database.getInstance().getLoggedInUser(), selectedLabel, true).setVisible(true);
                    dp.refresh(UpdateEntity.APPLICATION);
                }
            });
            currentPopupMenu.add(menuItemApply);
        }

        // Admin controls
        if (Database.getInstance().getLoggedInUser().isAdmin()) {
            if (selectedLabel.isLocked()) {
                JMenuItem unlockItem = new JMenuItem("Entsperren");
                unlockItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int ret = JOptionPane.showConfirmDialog(currentTable.getParent(),
                                "Soll das Label wirklich entsperrt werden?",
                                "Label entsperren", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE);
                        if (ret == JOptionPane.YES_OPTION) {
                            new AdminControl().unlock(selectedLabel);
                        }
                    }
                });
                currentPopupMenu.add(unlockItem);
            } else {
                JMenuItem lockItem = new JMenuItem("Sperren");
                lockItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JPanel detailPanel = new JPanel();
                        detailPanel.setLayout(new BorderLayout());
                        JLabel lblReason = new JLabel("Grund: ");
                        JTextArea txtReason = new JTextArea();
                        JScrollPane scrollPane = new JScrollPane(txtReason);
                        scrollPane.setPreferredSize(new Dimension(300, 320));
                        detailPanel.add(lblReason, BorderLayout.NORTH);
                        detailPanel.add(scrollPane, BorderLayout.SOUTH);
                        int ret = JOptionPane.showConfirmDialog(currentTable.getParent(),
                                detailPanel, "Label sperren",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.PLAIN_MESSAGE);
                        if (ret == JOptionPane.OK_OPTION) {
                            new AdminControl().lock(selectedLabel, txtReason.getText());
                            JOptionPane.showMessageDialog(currentTable.getParent(),
                                    "Das Label wurde gesperrt!", "Label gesperrt",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                });
                currentPopupMenu.add(lockItem);
            }
        }
// die haben gesagt ich soll das machen
//        if (selectedLabel.getLabelManagers().contains(
//                Database.getInstance().getLoggedInUser())) {
//            JMenuItem menuItemDelete = new JMenuItem("Löschen");
//            menuItemDelete.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent ae) {
//                    selectedLabel.delete();
//                }
//            });
//            currentPopupMenu.add(menuItemDelete);
//        }
    }

    /**
     * Creates the AlbumPopupMenu which handles the right clicks on albums
     */
    private void createAlbumMenu() {
        currentPopupMenu = new JPopupMenu();
        final Album a = (Album) currentTableModel.getValueAt(
                currentTable.getSelectedRow(), -1);
        JMenuItem menuItemQueue = new JMenuItem("Warteschlange");
        JMenuItem menuItemComment = new JMenuItem("Kommentieren");
        menuItemQueue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (a != null) {
                    Player.getInstance().addMedialist(a.getMediaList());
                    Player.getInstance().play();
                }
                dp.refresh(UpdateEntity.ALBUM);
            }
        });
        currentPopupMenu.add(menuItemQueue);

        menuItemComment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommentDialog cd = new CommentDialog(dp.getFrame(), dp);
                cd.commentAlbum(a);
                cd.setVisible(true);
            }
        });
        currentPopupMenu.add(menuItemComment);

        if (!(Database.getInstance().getLoggedInUser().getFavoriteAlbums().contains(a))) {
            JMenuItem menuItemFavorize = new JMenuItem("Favorisieren");
            menuItemFavorize.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    Database.getInstance().getLoggedInUser().addFavoriteAlbum(a);
                    dp.refresh(UpdateEntity.ALBUM);
                }
            });
            currentPopupMenu.add(menuItemFavorize);
        } else {
            JMenuItem menuItemFavorize = new JMenuItem("Entfavorisieren");
            menuItemFavorize.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    Database.getInstance().getLoggedInUser().removeFavoriteAlbum(a);
                    dp.refresh(UpdateEntity.ALBUM);
                }
            });
            currentPopupMenu.add(menuItemFavorize);
        }

        if (a.getArtist() == Database.getInstance().getLoggedInUser() //|| a.getLabel().getLabelManagers().contains(
                //Database.getInstance().getLoggedInUser())
                ) {

            JMenuItem menuItemDelete = new JMenuItem("Löschen");
            menuItemDelete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    a.delete();
                    dp.refresh(UpdateEntity.ALBUM);
                }
            });
            currentPopupMenu.add(menuItemDelete);
        }
    }

    /**
     * Creates the PlaylistPopupMenu which handles the right clicks on playlists
     */
    private void createPlaylistMenu() {
        currentPopupMenu = new JPopupMenu();
        final Playlist p = (Playlist) currentTableModel.getValueAt(
                currentTable.getSelectedRow(), -1);
        JMenuItem menuItemShow = new JMenuItem("Anzeigen");
        JMenuItem menuItemComment = new JMenuItem("Kommentieren");
        menuItemShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (p != null) {
                    dp.showPlaylist(p);
                }
            }
        });

        currentPopupMenu.add(menuItemShow);

        JMenuItem menuItemQueue = new JMenuItem("Warteschlange");
        menuItemQueue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (p != null) {
                    Player.getInstance().addMedialist(p.getList());
                    Player.getInstance().play();
                }
            }
        });

        currentPopupMenu.add(menuItemQueue);

        if (p.getCreator() == Database.getInstance().getLoggedInUser()) {
            JMenuItem menuItemRename = new JMenuItem("Umbenennen");
            menuItemRename.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
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
                    if (p != null) {
                        Database.getInstance().deletePlaylist(p);
                        dp.refresh(UpdateEntity.PLAYLIST);
                    }
                }
            });

            currentPopupMenu.add(menuItemRename);
            currentPopupMenu.add(menuItemDelete);
        }

        currentPopupMenu.add(menuItemQueue);

        menuItemComment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommentDialog cd = new CommentDialog(dp.getFrame(), dp);
                cd.commentPlaylists(p);
                cd.setVisible(true);
            }
        });
        currentPopupMenu.add(menuItemComment);

        if (!(Database.getInstance().getLoggedInUser().getFavoritePlaylists().contains(p))) {
            JMenuItem menuItemFavorize = new JMenuItem("Favorisieren");
            menuItemFavorize.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    Database.getInstance().getLoggedInUser().addFavoritePlaylist(p);
                    dp.refresh(UpdateEntity.PLAYLIST);
                }
            });
            currentPopupMenu.add(menuItemFavorize);
        } else {
            JMenuItem menuItemFavorize = new JMenuItem("Entfavorisieren");
            menuItemFavorize.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    Database.getInstance().getLoggedInUser().removeFavoritePlaylist(p);
                    dp.refresh(UpdateEntity.PLAYLIST);
                }
            });
            currentPopupMenu.add(menuItemFavorize);
        }
    }

    /**
     * Adds the PopupMenu which handles the right clicks on playlists in the
     * playlistpanel to the MediumPopupMenu
     */
    private void addPlaylistPanelMenuItems() {

        final Playlist p = ((PlaylistMediaTableModel) currentTableModel).getCurrentPlaylist();
        if (p.getCreator() == Database.getInstance().getLoggedInUser()) {
            JMenuItem deleteMediumAt = new JMenuItem("Nur hier entfernen");
            deleteMediumAt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
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

    /**
     * Adds the PopupMenu which handles the right clicks on news
     */
    private void createNewsMenu() {
        currentPopupMenu = new JPopupMenu();
        final News n = (News) currentTableModel.getValueAt(
                currentTable.getSelectedRow(), -1);
        JMenuItem menuItemShow = new JMenuItem("Anzeigen");
        JMenuItem menuItemComment = new JMenuItem("Kommentieren");
        menuItemShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (n != null) {
                    //dp.showNews(n);
                }
            }
        });
        currentPopupMenu.add(menuItemShow);
        menuItemComment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommentDialog cd = new CommentDialog(dp.getFrame(), dp);
                cd.commentNews(n);
                cd.setVisible(true);
            }
        });
        currentPopupMenu.add(menuItemComment);
    }

    private void createApplicationsMenu() {
        currentPopupMenu = new JPopupMenu();
        final Application a = (Application) currentTableModel.getValueAt(
                currentTable.getSelectedRow(), -1);
        if ((a.isFromArtist() && Database.getInstance().getLoggedInUser().equals(a.getUser())) || (!a.isFromArtist() && Database.getInstance().getLoggedInUser().getManagedLabels().contains(a.getLabel()))) {
            return;
        }
        JMenuItem menuItemAccept = new JMenuItem("Akzeptieren");
        JMenuItem menuItemDecline = new JMenuItem("Ablehnen");
        menuItemAccept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ApplicationControl().acceptApplication(a);
                dp.refresh(UpdateEntity.APPLICATION);
            }
        });
        currentPopupMenu.add(menuItemAccept);
        menuItemDecline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ApplicationControl().declineApplication(a);
                dp.refresh(UpdateEntity.APPLICATION);
            }
        });
        currentPopupMenu.add(menuItemDecline);
    }

    private boolean isManagedArtist(User u) {
        for (Label l : Database.getInstance().getLoggedInUser().getManagedLabels()) {
            if (l.getArtists().contains(u)) {
                return true;
            }
        }
        return false;
    }
}
