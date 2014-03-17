package de.dezibel.gui;

import de.dezibel.UpdateEntity;
import de.dezibel.data.Database;
import de.dezibel.data.Medium;
import de.dezibel.data.Playlist;
import de.dezibel.player.Player;
import java.awt.Dimension;
import java.awt.FontMetrics;

/**
 * Class for the Playlist Panel. Shows all information and media about a certain
 * playlist in the center of dezibelpanel.
 *
 * @author Benny
 */
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class PlaylistPanel extends DragablePanel {

    JLabel lbTitle;
    JLabel lbCreator;
    JLabel lbComments;
    JButton btnComment;
    JButton btnFavorite;
    JTable tblPlaylistMedia;
    JScrollPane spPlaylistMedia;
    JTable tblPlaylistComments;
    JScrollPane spPlaylistComments;
    DezibelPanel dp;
    PlaylistMediaTableModel model;
    CommentTableModel commentModel;
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
        lbComments = new JLabel("Kommentare");
        lbCreator.setHorizontalAlignment(JLabel.RIGHT);
        btnComment = new JButton(new ImageIcon(this.getClass().getResource("/img/icons/commentIcon.png")));
        btnComment.setOpaque(true);
        btnComment.setBorderPainted(true);
        btnComment.setContentAreaFilled(true);
        btnComment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                CommentDialog cd = new CommentDialog(dp.getFrame(), dp);
                cd.commentPlaylists(PlaylistPanel.this.currentPlaylist);
                cd.setVisible(true);
            }
        });
        btnFavorite = new JButton(new ImageIcon(this.getClass().getResource("/img/icons/favoriteFalse.png")));

        if (Database.getInstance().getLoggedInUser().getFavoritePlaylists().contains(currentPlaylist)) {
            btnFavorite.setIcon(new ImageIcon(this.getClass().getResource("/img/icons/favoriteTrue.png")));
        } else {
            btnFavorite.setIcon(new ImageIcon(this.getClass().getResource("/img/icons/favoriteFalse.png")));
        }

        btnFavorite.setOpaque(false);
        btnFavorite.setBorderPainted(false);
        btnFavorite.setContentAreaFilled(false);
        btnFavorite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (Database.getInstance().getLoggedInUser().getFavoritePlaylists().contains(PlaylistPanel.this.currentPlaylist)) {
                    Database.getInstance().getLoggedInUser().removeFavoritePlaylist(PlaylistPanel.this.currentPlaylist);
                    btnFavorite.setIcon(new ImageIcon(this.getClass().getResource("/img/icons/favoriteFalse.png")));
                } else {
                    Database.getInstance().getLoggedInUser().addFavoritePlaylist(PlaylistPanel.this.currentPlaylist);
                    btnFavorite.setIcon(new ImageIcon(this.getClass().getResource("/img/icons/favoriteTrue.png")));
                }
                dp.refresh(UpdateEntity.PLAYLIST);
            }
        });
        model = new PlaylistMediaTableModel();
        model.setData(currentPlaylist);
        tblPlaylistMedia = new JTable(model);
        spPlaylistMedia = new JScrollPane(tblPlaylistMedia);

        commentModel = new CommentTableModel();
        commentModel.setData(currentPlaylist.getComments());
        tblPlaylistComments = new JTable(commentModel);
        tblPlaylistComments.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent ce) {
                resizeCommentRows();
            }
        });
        tblPlaylistComments.getTableHeader().addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                resizeCommentRows();
            }
        });
        TextAreaCellRenderer tacr = new TextAreaCellRenderer();
        tblPlaylistComments.getColumnModel().getColumn(0).setCellRenderer(tacr);

        tblPlaylistComments.setFocusable(false);
        tblPlaylistComments.setRowSelectionAllowed(false);
        spPlaylistComments = new JScrollPane(tblPlaylistComments);

        DefaultTableCellRenderer topRenderer = new DefaultTableCellRenderer();
        topRenderer.setVerticalAlignment(javax.swing.JLabel.TOP);
        tblPlaylistComments.getColumnModel().getColumn(1).setCellRenderer(topRenderer);
        tblPlaylistComments.getColumnModel().getColumn(2).setCellRenderer(topRenderer);
        tblPlaylistComments.getColumnModel().getColumn(0).setMinWidth((int) (parent.getWidth() * 0.5));
        tblPlaylistMedia.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    Medium m = (Medium) model.getValueAt(
                            tblPlaylistMedia.getSelectedRow(), -1);
                    if (m != null) {
                        //TODO Song an der Anfang der Queue
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
                currentPopupMenu = contextMenu.getContextMenu(tblPlaylistMedia, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());
            }
        });

    }

    private void createLayout() {

        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(gbl);

        lbTitle.setFont(DezibelFont.CENTERPANEL_TITLE);
        lbCreator.setFont(DezibelFont.CENTERPANEL_TITLE);

        gbc.weighty = 0.1;
        gbc.insets = new Insets(0, 30, 0, 0);
        this.add(lbTitle, gbc);

        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 1;
        this.add(btnComment, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        this.add(btnFavorite, gbc);

        gbc.weightx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(0, 0, 0, 30);
        gbc.anchor = GridBagConstraints.EAST;
        this.add(lbCreator, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.weighty = 0.6;
        gbc.weightx = 1;
        this.add(spPlaylistMedia, gbc);

        gbc.weighty = 0.3;
        this.add(spPlaylistComments, gbc);

        int minWidth = (int) Math.max(lbCreator.getPreferredSize()
                .getWidth(), lbTitle.getPreferredSize().getWidth());
        lbCreator.setMinimumSize(new Dimension(minWidth, 32));
        lbTitle.setMinimumSize(new Dimension(minWidth, 32));
    }

    @Override
    public void refresh() {
        model.setData(currentPlaylist);
        commentModel.setData(currentPlaylist.getComments());
        lbTitle = new JLabel(currentPlaylist.getTitle());
        String creatorString = currentPlaylist.getCreator().getPseudonym();
        if (creatorString == null || creatorString.trim().isEmpty()) {
            creatorString = currentPlaylist.getCreator().getFirstname() + " "
                    + currentPlaylist.getCreator().getLastname();
        }
        lbCreator = new JLabel(creatorString);

        int minWidth = Math.max(lbCreator.getWidth(), lbTitle.getWidth());
        lbCreator.setMinimumSize(new Dimension(minWidth, 32));
        lbTitle.setMinimumSize(new Dimension(minWidth, 32));

        if (Database.getInstance().getLoggedInUser().getFavoritePlaylists().contains(currentPlaylist)) {
            btnFavorite.setIcon(new ImageIcon(this.getClass().getResource("/img/icons/favoriteTrue.png")));
        } else {
            btnFavorite.setIcon(new ImageIcon(this.getClass().getResource("/img/icons/favoriteFalse.png")));
        }

        if (currentPlaylist.getList().isEmpty()) {
            dp.clearCenter();
        }
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub
    }

    private void resizeCommentRows() {
        JTextArea textarea = (JTextArea) tblPlaylistComments.getColumnModel()
                .getColumn(0).getCellRenderer().getTableCellRendererComponent(
                        tblPlaylistComments, null, false, false, 0, 0);
        FontMetrics fm = textarea.getFontMetrics(textarea.getFont());
        int columnWidth = tblPlaylistComments.getColumnModel().getColumn(0).getWidth();
        for (int row = 0; row < tblPlaylistComments.getRowCount(); row++) {
            int lines = 0;
            for (String s : ((String) tblPlaylistComments.getValueAt(row, 0)).split("\n")) {
                lines++;
                if (fm.stringWidth(s) > columnWidth) {
                    lines += fm.stringWidth(s) / columnWidth;
                }
            }
            tblPlaylistComments.setRowHeight(row, lines * fm.getHeight() + 20);
        }
    }
}
