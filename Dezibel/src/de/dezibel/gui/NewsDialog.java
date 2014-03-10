package de.dezibel.gui;

import de.dezibel.control.NewsControl;
import de.dezibel.data.Database;
import de.dezibel.data.Label;
import de.dezibel.data.User;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;

/**
 * Dialog to create news
 *
 * @author Tobias, Benjamin
 */
class NewsDialog extends JDialog {

    private final NewsControl nc;
    JComboBox<Object> cbAuthor = new JComboBox<>();
    User loggedInUser = Database.getInstance().getLoggedInUser();

    /**
     * Constructor
     *
     * @param frame The frame to block
     */
    public NewsDialog(JFrame frame) {
        super(frame);
        setModal(true);
        nc = new NewsControl();
        this.init();
    }

    /**
     * Initiates the dialog to enter news
     */
    public void init() {


        setTitle("Neuigkeiten erstellen");

        JLabel lbTitle = new JLabel("Titel");
        JLabel lbAuhor = new JLabel("Author");

        if (loggedInUser.isArtist()) {
            cbAuthor.addItem(loggedInUser);
        }
        if (loggedInUser.isLabelManager()) {
            for (Label currentLabel : loggedInUser.getManagedLabels()) {
                cbAuthor.addItem(currentLabel);
                for (User currentArtist : currentLabel.getArtists()) {
                    cbAuthor.addItem(currentLabel);
                }
            }
        }
        final JTextField tfTitle = new JTextField();
        final JTextArea taText = new JTextArea();
        JScrollPane sp = new JScrollPane(taText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        JButton btComment = new JButton("Abschicken");
        JButton btCancel = new JButton("Abbrechen");



        btComment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nc.createNews(cbAuthor.getSelectedItem(), tfTitle.getText(), taText.getText());
                NewsDialog.this.dispose();
            }
        });

        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewsDialog.this.dispose();
            }
        });

        this.setLayout(null);
        lbTitle.setBounds(10, 5, 75, 32);
        this.add(lbTitle);
        tfTitle.setBounds(85, 5, 175, 32);
        this.add(tfTitle);
        lbAuhor.setBounds(10, 42, 75, 32);
        this.add(lbAuhor);
        cbAuthor.setBounds(85, 42, 175, 32);
        this.add(cbAuthor);
        sp.setBounds(10, 79, 250, 250);
        this.add(sp);
        btComment.setBounds(10, 334, 120, 32);
        this.add(btComment);
        btCancel.setBounds(135, 334, 120, 32);
        this.add(btCancel);

        this.setResizable(false);
        this.setSize(265, 391);
    }
}
