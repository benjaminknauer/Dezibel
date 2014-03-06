package de.dezibel.gui;

import de.dezibel.ErrorCode;
import de.dezibel.control.UploadControl;
import de.dezibel.data.Album;
import de.dezibel.data.Genre;
import de.dezibel.data.Label;
import de.dezibel.data.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

    private UploadControl upc;
    
    private JTextField tfUpload;
    
    private JComboBox<User> cbUser;
    private JComboBox<Genre> cbGenre;
    private JComboBox<Label> cbLabel;
    private JComboBox<Album> cbAlbum;
    
    private Label label;

    public UploadDialog(JFrame frame, Label label) {
        super(frame);
        setModal(true);
        upc = new UploadControl();
        this.label = label;
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
        
        cbUser = new JComboBox<>(upc.getSelectableUsers());
        cbGenre = new JComboBox<>(upc.getSelectableGenres());
        cbLabel = new JComboBox<>(upc.getSelectableLabels());
        cbAlbum = new JComboBox<>(upc.getSelectableAlbums());
        
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
                switch(upc.upload(tfTitle.getText(), (User) cbUser.getSelectedItem(),
                        tfUpload.getText(), (Genre) cbGenre.getSelectedItem(),
                        (Label) cbLabel.getSelectedItem(), (Album) cbAlbum.getSelectedItem())) {
                    case SUCCESS:
                        UploadDialog.this.dispose();
                }
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
