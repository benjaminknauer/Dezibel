package de.dezibel.gui;

import de.dezibel.control.CommentControl;
import de.dezibel.data.Medium;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 * Dialog to comment
 *
 * @author Tobias
 */
class CommentDialog extends JDialog {

    private final CommentControl cc;
    
    /**
     * Constructor
     * @param frame The frame to block
     */
    public CommentDialog(JFrame frame) {
        cc = new CommentControl();
    }
    
    /**
     * Handles the commenting for the given medium
     * @param m The medium to comment
     */
    public void commentMedia(final Medium m) {
        setTitle("Kommentieren");
        
        JLabel lbYourComment = new JLabel("Dein Kommentar");
        final JTextArea taText = new JTextArea();
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
        lbYourComment.setBounds(5, 5, 100, 32);
        this.add(lbYourComment);
        taText.setBounds(5, 42, 250, 250);
        this.add(taText);
        btComment.setBounds(5, 300, 120, 32);
        this.add(btComment);
        btCancel.setBounds(130, 300, 120, 32);
        this.add(btCancel);

        this.setResizable(false);
        this.setSize(265, 371);
    }
    
}
