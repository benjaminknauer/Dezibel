package de.dezibel.gui;

import de.dezibel.control.NewsControl;
import de.dezibel.data.Database;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Dialog to create news
 *
 * @author Tobias
 */
class NewsDialog extends JDialog {

    private final NewsControl nc;

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
        final JTextField tfTitle = new JTextField();
        final JTextArea taText = new JTextArea();
        JScrollPane sp = new JScrollPane(taText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        JButton btComment = new JButton("Abschicken");
        JButton btCancel = new JButton("Abbrechen");

        
        
        btComment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nc.createNews(Database.getInstance().getLoggedInUser(), tfTitle.getText(), taText.getText());
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
        lbTitle.setBounds(5, 5, 75, 32);
        this.add(lbTitle);
        tfTitle.setBounds(80, 5, 175, 32);
        this.add(tfTitle);
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
