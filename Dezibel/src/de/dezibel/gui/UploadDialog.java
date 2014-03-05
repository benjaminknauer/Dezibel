package de.dezibel.gui;

import de.dezibel.data.Database;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Tobias, Richard
 */
public class UploadDialog extends JDialog {

    private JTextField tfUpload;

    public UploadDialog(JFrame frame) {
        super(frame);
        setModal(true);
        this.init();
    }

    public void init() {
        JLabel lbTitle = new JLabel("Titel");
        final JTextField tfTitle = new JTextField();
        JLabel lbUpload = new JLabel("Datei");
        tfUpload = new JTextField();
        JButton btChoose = new JButton("...");
        JButton btUpload = new JButton("Upload");
        JButton btCancel = new JButton("Abbrechen");

        btCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UploadDialog.this.dispose();
            }
        });

        btChoose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(UploadDialog.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    tfUpload.setText(fc.getSelectedFile().getAbsolutePath());
                }
            }
        });
        
        btUpload.addActionListener(new ActionListener () {

            @Override
            public void actionPerformed(ActionEvent e) {
                Database db = Database.getInstance();
                db.addMedium(tfTitle.getText(), db.getLoggedInUser(), tfUpload.getText(), db.getTopGenre(),null,null);
                UploadDialog.this.dispose();
            }
        });

        GroupLayout layout = new GroupLayout(this.getContentPane());
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(lbTitle)
                        .addComponent(tfTitle))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(lbUpload)
                        .addComponent(tfUpload)
                        .addComponent(btChoose))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(btUpload)
                        .addComponent(btCancel))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(lbTitle)
                        .addComponent(tfTitle))
                .addGroup(layout.createParallelGroup()
                        .addComponent(lbUpload)
                        .addComponent(tfUpload)
                        .addComponent(btChoose))
                .addGroup(layout.createParallelGroup()
                        .addComponent(btUpload)
                        .addComponent(btCancel))
        );
        this.getContentPane().setLayout(layout);

        pack();
    }
}
