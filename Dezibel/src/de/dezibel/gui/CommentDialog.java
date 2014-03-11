package de.dezibel.gui;

import de.dezibel.UpdateEntity;
import de.dezibel.control.CommentControl;
import de.dezibel.data.Album;
import de.dezibel.data.Medium;
import de.dezibel.data.News;
import de.dezibel.data.Playlist;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Dialog to comment
 *
 * @author Tobias
 */
class CommentDialog extends JDialog {

    private final CommentControl cc;
    private final DezibelPanel dPanel;

    /**
     * Constructor
     *
     * @param frame The frame to block
     */
    public CommentDialog(JFrame frame, DezibelPanel dp) {
        super(frame);
        dPanel = dp;
        setModal(true);
        cc = new CommentControl();
    }

    /**
     * Handles the commenting for the given medium
     *
     * @param m The medium to comment
     */
    public void commentMedia(final Medium m) {
        setTitle("Kommentieren");

        JLabel lbYourComment = new JLabel("Dein Kommentar");
        final JTextArea taText = new JTextArea();
        JScrollPane sp = new JScrollPane(taText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        JButton btComment = new JButton("Kommentieren");
        JButton btCancel = new JButton("Abbrechen");

        btComment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cc.commentMedia(m, taText.getText());
                CommentDialog.this.dispose();
            }
        });

        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommentDialog.this.dispose();
            }
        });

        this.setLayout(null);
        lbYourComment.setBounds(5, 5, 200, 32);
        this.add(lbYourComment);
        sp.setBounds(5, 42, 250, 250);
        this.add(sp);
        btComment.setBounds(5, 300, 120, 32);
        this.add(btComment);
        btCancel.setBounds(130, 300, 120, 32);
        this.add(btCancel);

        this.setResizable(false);
        this.setSize(265, 371);
    }

    /**
     * Handles the commenting for the given album
     *
     * @param a The album to comment
     */
    public void commentAlbum(final Album a) {
        setTitle("Kommentieren");

        JLabel lbYourComment = new JLabel("Dein Kommentar");
        final JTextArea taText = new JTextArea();
        JScrollPane sp = new JScrollPane(taText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        JButton btComment = new JButton("Kommentieren");
        JButton btCancel = new JButton("Abbrechen");

        btComment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cc.commentAlbum(a, taText.getText());
                dPanel.refresh(UpdateEntity.ALBUM);
                CommentDialog.this.dispose();
            }
        });

        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommentDialog.this.dispose();
            }
        });

        this.setLayout(null);
        lbYourComment.setBounds(5, 5, 100, 32);
        this.add(lbYourComment);
        sp.setBounds(5, 42, 250, 250);
        this.add(sp);
        btComment.setBounds(5, 300, 120, 32);
        this.add(btComment);
        btCancel.setBounds(130, 300, 120, 32);
        this.add(btCancel);

        this.setResizable(false);
        this.setSize(265, 371);
    }

    /**
     * Handles the commenting for the given playlist
     *
     * @param p The playlist to comment
     */
    public void commentPlaylists(final Playlist p) {
        setTitle("Kommentieren");

        JLabel lbYourComment = new JLabel("Dein Kommentar");
        final JTextArea taText = new JTextArea();
        JScrollPane sp = new JScrollPane(taText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        JButton btComment = new JButton("Kommentieren");
        JButton btCancel = new JButton("Abbrechen");

        btComment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cc.commentPlaylist(p, taText.getText());
                CommentDialog.this.dispose();
            }
        });

        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommentDialog.this.dispose();
            }
        });

        this.setLayout(null);
        lbYourComment.setBounds(5, 5, 100, 32);
        this.add(lbYourComment);
        sp.setBounds(5, 42, 250, 250);
        this.add(sp);
        btComment.setBounds(5, 300, 120, 32);
        this.add(btComment);
        btCancel.setBounds(130, 300, 120, 32);
        this.add(btCancel);

        this.setResizable(false);
        this.setSize(265, 371);
    }

    /**
     * Handles the commenting for the given news
     *
     * @param n The news to comment
     */
    public void commentNews(final News n) {
        setTitle("Kommentieren");

        JLabel lbYourComment = new JLabel("Dein Kommentar");
        final JTextArea taText = new JTextArea();
        JScrollPane sp = new JScrollPane(taText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        JButton btComment = new JButton("Kommentieren");
        JButton btCancel = new JButton("Abbrechen");

        btComment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cc.commentNews(n, taText.getText());
                CommentDialog.this.dispose();
            }
        });

        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommentDialog.this.dispose();
            }
        });

        this.setLayout(null);
        lbYourComment.setBounds(5, 5, 100, 32);
        this.add(lbYourComment);
        sp.setBounds(5, 42, 250, 250);
        this.add(sp);
        btComment.setBounds(5, 300, 120, 32);
        this.add(btComment);
        btCancel.setBounds(130, 300, 120, 32);
        this.add(btCancel);

        this.setResizable(false);
        this.setSize(265, 371);
    }

}
