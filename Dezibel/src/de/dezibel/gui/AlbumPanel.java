package de.dezibel.gui;

import de.dezibel.data.Album;
import de.dezibel.data.Database;
import de.dezibel.data.Medium;
import de.dezibel.data.Playlist;
import de.dezibel.player.Player;
import java.awt.Font;
import java.awt.FontMetrics;

/**
 *
 * @author Benny
 */
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;

public class AlbumPanel extends DragablePanel {

    JLabel lbTitle;
    JLabel lbCreator;
    JLabel lbComments;
    JTable tblAlbumMedia;
    JScrollPane spAlbumMedia;
    JTable tblAlbumComments;
    JScrollPane spAlbumComments;
    DezibelPanel dp;
    AlbumMediaTableModel model;
    CommentTableModel commentModel;
    JPopupMenu currentPopupMenu;
    Album currentAlbum;

    public AlbumPanel(DezibelPanel parent, Album currentAlbum) {
        super(parent);
        this.dp = parent;
        createComponents(currentAlbum);
        createLayout();

    }

    private void createComponents(Album currentAlbum) {
        this.currentAlbum = currentAlbum;
        lbTitle = new JLabel(currentAlbum.getTitle());
        String creatorString = currentAlbum.getArtist().getPseudonym();
        
        lbCreator = new JLabel(creatorString);
        lbComments = new JLabel("Kommentare");
        model = new AlbumMediaTableModel();
        model.setData(currentAlbum);
        tblAlbumMedia = new JTable(model);
        spAlbumMedia = new JScrollPane(tblAlbumMedia);
        
        commentModel = new CommentTableModel();
        commentModel.setData(currentAlbum.getComments());
        tblAlbumComments = new JTable(commentModel);
        spAlbumComments = new JScrollPane(tblAlbumComments);
        
        DefaultTableCellRenderer topRenderer = new DefaultTableCellRenderer();
        topRenderer.setVerticalAlignment(javax.swing.JLabel.TOP);
        tblAlbumComments.getColumnModel().getColumn(1).setCellRenderer(topRenderer);
        tblAlbumComments.getColumnModel().getColumn(2).setCellRenderer(topRenderer);
        tblAlbumComments.getColumnModel().getColumn(0).setMinWidth((int) (parent.getWidth() * 0.5) );
        
        tblAlbumComments.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent ce) {
                resizeCommentRows();
            }
        });
        tblAlbumComments.getTableHeader().addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                resizeCommentRows();
            }
        });
        
        
        TextAreaCellRenderer tacr = new TextAreaCellRenderer();
        tblAlbumComments.getColumnModel().getColumn(0).setCellRenderer(tacr);  

        tblAlbumMedia.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    Medium m = (Medium) model.getValueAt(
                            tblAlbumMedia.getSelectedRow(), -1);
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
                currentPopupMenu = contextMenu.getContextMenu(tblAlbumMedia, me);
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

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(0, 0, 0, 30);
        gbc.anchor = GridBagConstraints.EAST;
        this.add(lbCreator, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.weighty = 0.6;
        gbc.weightx = 1;
        this.add(spAlbumMedia, gbc);
        
        gbc.weighty = 0.3;
        this.add(spAlbumComments,gbc);
    }

    @Override
    public void refresh() {
        model.setData(currentAlbum);
        commentModel.setData(currentAlbum.getComments());
        lbTitle = new JLabel(currentAlbum.getTitle());
        String creatorString = currentAlbum.getArtist().getPseudonym();
        lbCreator = new JLabel(creatorString);
        
        if(currentAlbum.getMediaList().isEmpty()){
            dp.clearCenter();
        }
    }
    
    private void resizeCommentRows() {
        JTextArea textarea = (JTextArea) tblAlbumComments.getColumnModel()
                .getColumn(0).getCellRenderer().getTableCellRendererComponent(
                        tblAlbumComments, null, false, false, 0, 0);
        FontMetrics fm = textarea.getFontMetrics(textarea.getFont());
        int columnWidth = tblAlbumComments.getColumnModel().getColumn(0).getWidth();
        for (int row = 0; row < tblAlbumComments.getRowCount(); row++) {
            int lines = 0;
            for (String s : ((String) tblAlbumComments.getValueAt(row, 0)).split("\n")) {
                lines++;
                if (fm.stringWidth(s) > columnWidth) {
                    lines += fm.stringWidth(s) / columnWidth;
                }
            }
            tblAlbumComments.setRowHeight(row, lines * fm.getHeight());
        }
    }

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
