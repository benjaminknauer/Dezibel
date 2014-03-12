package de.dezibel.gui;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import de.dezibel.data.Album;
import de.dezibel.data.Label;
import de.dezibel.data.Medium;
import de.dezibel.data.User;

/**
 * A small panel, which shows the medium details Comments can be selected and
 * the comment-text will be displayed in a separate text-area.
 *
 * @author Pascal
 *
 */
public class MediumPanel extends DragablePanel {

    // ref. to clickable properties for displaying them in other panels.
    private Medium currentMedium;
    private User artist;
    private Label label;
    private Album album;

    // Labels for properties
    private JLabel lbTitle;
    private JLabel lbAlbum;
    private JLabel lbUploadDate;
    private JLabel lbAvgRating;
    private JLabel lbArtist;
    private JLabel lbGenre;
    private JLabel lbLabel;

    // Labels that will be filled with the medium-properties
    private JLabel lbInfoTitle;
    private JLabel bnInfoAlbum;
    private JLabel lbInfoUploadDate;
    private JLabel lbInfoAvgRating;
    private JLabel bnInfoArtist;
    private JLabel lbInfoGenre;
    private JLabel bnInfoLabel;

    private JTable tbComments;
    private JScrollPane spComments;
    private CommentTableModel commentModel;

    private String clickable1 = "<HTML><FONT color=\"#000099\"><U>";
    private String clickable2 = "</U></FONT></HTML>";

    public MediumPanel(DezibelPanel parent, Medium current) {
        super(parent);
        this.currentMedium = current;
        this.createComponents();
        this.createLayout();
        this.refresh();
    }

    @Override
    /**
     * @see de.dezibel.gui.DragablePanel#reset()
     */
    public void reset() {
        currentMedium = null;
        artist = null;
        label = null;
        lbInfoTitle.setText("");
        bnInfoAlbum.setText("");
        lbInfoUploadDate.setText("");
        lbInfoAvgRating.setText("");
        bnInfoArtist.setText("");
        lbInfoGenre.setText("");
        bnInfoLabel.setText("");
    }

    @Override
    /**
     * @see de.dezibel.gui.DragablePanel#refresh()
     */
    public void refresh() {
        if (currentMedium != null) {
            lbInfoTitle.setText("");
            bnInfoAlbum.setText("");
            lbInfoUploadDate.setText("");
            lbInfoAvgRating.setText("");
            bnInfoArtist.setText("");
            lbInfoGenre.setText("");
            bnInfoLabel.setText("");

            if (currentMedium.getArtist() != null) {
                artist = currentMedium.getArtist();
                bnInfoArtist.setText(clickable1 + currentMedium.getArtist().getPseudonym() + clickable2);
            } else {
                bnInfoArtist.setText("-");
            }

            lbInfoTitle.setText(currentMedium.getTitle());

            if (currentMedium.getAlbum() != null) {
                album = currentMedium.getAlbum();
                bnInfoAlbum.setText(clickable1 + currentMedium.getAlbum().getTitle() + clickable2);
                //bnInfoAlbum.setText(currentMedium.getAlbum().getTitle());
            } else {
                bnInfoAlbum.setText("-");
            }

            if (currentMedium.getGenre() != null) {
                lbInfoGenre.setText(currentMedium.getGenre().getName());
            } else {
                lbInfoGenre.setText("-");
            }

            if (currentMedium.getLabel() != null) {
                label = currentMedium.getLabel();
                bnInfoLabel.setText(clickable1 + currentMedium.getLabel().getName() + clickable2);
                //bnInfoLabel.setText(currentMedium.getLabel().getName());
            } else {
                bnInfoLabel.setText("-");
            }

            if (currentMedium.getUploadDate() != null) {
                DateFormat formater = new SimpleDateFormat();

                lbInfoUploadDate.setText(formater.format(currentMedium.getUploadDate()));
            } else {
                lbInfoUploadDate.setText("-");
            }

            Double rating = currentMedium.getAvgRating();
            lbInfoAvgRating.setText(rating.toString());

            if ((currentMedium.getComments() != null) && (currentMedium.getComments().size() > 0)) {
                commentModel.setData(currentMedium.getComments());
            }
        }
    }

    /**
     * Help function for creating all components needed by this panel
     */
    private void createComponents() {
        lbArtist = new JLabel("Künstler:");
        lbTitle = new JLabel("Titel:");
        lbAlbum = new JLabel("Album:");
        lbGenre = new JLabel("Genre:");
        lbLabel = new JLabel("Label:");
        lbUploadDate = new JLabel("Hochgeladen am:");
        lbAvgRating = new JLabel("Durchschnittliche Bewertung:");

        lbInfoTitle = new JLabel("");
        bnInfoAlbum = new JLabel("");
        lbInfoUploadDate = new JLabel("");
        lbInfoAvgRating = new JLabel("");
        bnInfoArtist = new JLabel("");
        lbInfoGenre = new JLabel("");
        bnInfoLabel = new JLabel("");

        bnInfoArtist.setHorizontalAlignment(SwingConstants.LEFT);
        bnInfoArtist.setHorizontalTextPosition(SwingConstants.LEFT);
        bnInfoArtist.setOpaque(false);
        bnInfoArtist.setBackground(this.getBackground());
        bnInfoArtist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    onClickArtist();
                }
            }
        });

        bnInfoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        bnInfoLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        bnInfoLabel.setOpaque(false);
        bnInfoLabel.setBackground(this.getBackground());
        bnInfoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    onClickLabel();
                }
            }
        });

        bnInfoAlbum.setHorizontalAlignment(SwingConstants.LEFT);
        bnInfoAlbum.setHorizontalTextPosition(SwingConstants.LEFT);
        bnInfoAlbum.setOpaque(false);
        bnInfoAlbum.setBackground(this.getBackground());
        bnInfoAlbum.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    onClickAlbum();
                }
            }
        });

        commentModel = new CommentTableModel();
        commentModel.setData(currentMedium.getComments());
        tbComments = new JTable(commentModel);
        tbComments.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent ce) {
                resizeCommentRows();
            }
        });
        tbComments.getTableHeader().addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                resizeCommentRows();
            }
        });
        TextAreaCellRenderer tacr = new TextAreaCellRenderer();
        tbComments.getColumnModel().getColumn(0).setCellRenderer(tacr);

        tbComments.setFocusable(false);
        tbComments.setRowSelectionAllowed(false);
        spComments = new JScrollPane(tbComments);

        DefaultTableCellRenderer topRenderer = new DefaultTableCellRenderer();
        topRenderer.setVerticalAlignment(javax.swing.JLabel.TOP);
        tbComments.getColumnModel().getColumn(1).setCellRenderer(topRenderer);
        tbComments.getColumnModel().getColumn(2).setCellRenderer(topRenderer);
        tbComments.getColumnModel().getColumn(0).setMinWidth((int) (parent.getWidth() * 0.5));
    }

    /**
     * Help function to create the panel layout using GroupLayout
     */
    private void createLayout() {
        GroupLayout layout = new GroupLayout(this);

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(lbArtist)
                                .addComponent(lbTitle)
                                .addComponent(lbAlbum)
                                .addComponent(lbGenre)
                                .addComponent(lbLabel)
                                .addComponent(lbUploadDate)
                                .addComponent(lbAvgRating))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(bnInfoArtist)
                                .addComponent(lbInfoTitle)
                                .addComponent(bnInfoAlbum)
                                .addComponent(lbInfoGenre)
                                .addComponent(bnInfoLabel)
                                .addComponent(lbInfoUploadDate)
                                .addComponent(lbInfoAvgRating)))
                .addComponent(spComments)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(lbArtist)
                                .addComponent(lbTitle)
                                .addComponent(lbAlbum)
                                .addComponent(lbGenre)
                                .addComponent(lbLabel)
                                .addComponent(lbUploadDate)
                                .addComponent(lbAvgRating))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(bnInfoArtist)
                                .addComponent(lbInfoTitle)
                                .addComponent(bnInfoAlbum)
                                .addComponent(lbInfoGenre)
                                .addComponent(bnInfoLabel)
                                .addComponent(lbInfoUploadDate)
                                .addComponent(lbInfoAvgRating))
                )
                .addComponent(spComments)
        );

        layout.linkSize(SwingConstants.HORIZONTAL, lbInfoTitle, bnInfoAlbum, lbInfoUploadDate, lbInfoAvgRating,
                bnInfoArtist, lbInfoGenre, bnInfoLabel);
        layout.linkSize(SwingConstants.VERTICAL, lbInfoTitle, bnInfoAlbum, lbInfoUploadDate, lbInfoAvgRating,
                bnInfoArtist, lbInfoGenre, bnInfoLabel);
        this.setLayout(layout);
    }

    /**
     * This function is called, when the user click on the artist of the current
     * displayed media.
     */
    private void onClickArtist() {
        if (artist != null) {
            this.parent.showProfile(artist);
        }
    }

    /**
     * This function is called, when the user click on the album of the current
     * displayed media.
     */
    private void onClickAlbum() {
        if (album != null) {
            this.parent.showAlbum(album);
        }
    }

    /**
     * This function is called, when the user click on the label of the current
     * displayed media.
     */
    private void onClickLabel() {
        if (label != null) {
            this.parent.showProfile(label);
        }
    }

    /**
     * Help-method to resize the comment rows.
     */
    private void resizeCommentRows() {
        JTextArea textarea = (JTextArea) tbComments.getColumnModel()
                .getColumn(0).getCellRenderer().getTableCellRendererComponent(
                        tbComments, null, false, false, 0, 0);
        FontMetrics fm = textarea.getFontMetrics(textarea.getFont());
        int columnWidth = tbComments.getColumnModel().getColumn(0).getWidth();
        for (int row = 0; row < tbComments.getRowCount(); row++) {
            int lines = 0;
            for (String s : ((String) tbComments.getValueAt(row, 0)).split("\n")) {
                lines++;
                if (fm.stringWidth(s) > columnWidth) {
                    lines += fm.stringWidth(s) / columnWidth;
                }
            }
            tbComments.setRowHeight(row, lines * fm.getHeight() + 20);
        }
    }
}
