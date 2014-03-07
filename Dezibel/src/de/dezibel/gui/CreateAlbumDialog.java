package de.dezibel.gui;

import de.dezibel.ErrorCode;
import de.dezibel.control.UploadControl;
import de.dezibel.data.Album;
import de.dezibel.data.Genre;
import de.dezibel.data.Label;
import de.dezibel.data.Medium;
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
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Tobias, Richard
 */
public class CreateAlbumDialog extends JDialog {

    private UploadControl upc;

    private JTextField tfUpload;

    private JComboBox<User> cbUser;
    private JComboBox<Genre> cbGenre;
    private JComboBox<Label> cbLabel;
    private JComboBox<Album> cbAlbum;

    private Label label;
    private Medium medium;

    public CreateAlbumDialog(JFrame frame, Label label, Medium medium) {
        super(frame);
        setModal(true);
        upc = new UploadControl();
        this.label = label;
        this.medium = medium;
        this.init();
    }

    public void init() {
        setTitle("Upload");
        
        JLabel lbTitle = new JLabel("Titel");
        final JTextField tfTitle = new JTextField();
        JLabel lbUpload = new JLabel("Datei");
        tfUpload = new JTextField();
        JButton btChoose = new JButton("...");
        JButton btUpload = new JButton("Upload");
        JButton btCancel = new JButton("Abbrechen");

        JLabel lbGenre = new JLabel("Genre");
        JLabel lbLabel = new JLabel("Label");
        JLabel lbUser = new JLabel("Künstler");
        JLabel lbAlbum = new JLabel("Album");

        cbGenre = new JComboBox<>(upc.getSelectableGenres());
        cbLabel = new JComboBox<>(upc.getSelectableLabels());
        cbLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cbUser.setModel(new DefaultComboBoxModel<>(upc.getSelectableUsers((Label) cbLabel.getSelectedItem())));
            }
        });
        cbUser = new JComboBox<>(upc.getSelectableUsers(null));
        cbAlbum = new JComboBox<>(upc.getSelectableAlbums());

        btCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                CreateAlbumDialog.this.dispose();
            }
        });

        btChoose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(CreateAlbumDialog.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    tfUpload.setText(fc.getSelectedFile().getAbsolutePath());
                }
            }
        });

        btUpload.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (medium == null) {
                    switch (upc.upload(tfTitle.getText(), (User) cbUser.getSelectedItem(),
                            (tfUpload.getText().isEmpty() ? null : tfUpload.getText()),
                            (Genre) cbGenre.getSelectedItem(),
                            (Label) cbLabel.getSelectedItem(),
                            (Album) cbAlbum.getSelectedItem())) {
                        case SUCCESS:
                            JOptionPane.showMessageDialog(CreateAlbumDialog.this, "Das Medium wurde erfolgreich erstellt!", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                            CreateAlbumDialog.this.dispose();
                            break;
                        case USER_IS_NOT_ARTIST:
                            upc.promoteUserToArtist(CreateAlbumDialog.this, (User) cbUser.getSelectedItem());
                            cbUser.setModel(new DefaultComboBoxModel<>(upc.getSelectableUsers((Label) cbLabel.getSelectedItem())));
                            break;
                    }
                } else {
                    switch (medium.upload(tfUpload.getText())) {
                        case UPLOAD_ERROR:
                            JOptionPane.showMessageDialog(CreateAlbumDialog.this,
                                    "Die Datei konnte nicht hochgeladen werden.",
                                    "Fehler", JOptionPane.ERROR_MESSAGE);
                            break;
                        case SUCCESS:
                            JOptionPane.showMessageDialog(CreateAlbumDialog.this, "Das Medium wurde erfolgreich erstellt!", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                            CreateAlbumDialog.this.dispose();
                            break;
                    }
                }
            }
        });

        this.setLayout(null);
        lbTitle.setBounds(5, 5, 100, 32);
        this.add(lbTitle);
        tfTitle.setBounds(105, 5, 400, 32);
        this.add(tfTitle);
        lbGenre.setBounds(5, 42, 100, 32);
        this.add(lbGenre);
        cbGenre.setBounds(105, 42, 400, 32);
        this.add(cbGenre);
        lbLabel.setBounds(5, 79, 100, 32);
        this.add(lbLabel);
        cbLabel.setBounds(105, 79, 400, 32);
        this.add(cbLabel);
        lbUser.setBounds(5, 116, 100, 32);
        this.add(lbUser);
        cbUser.setBounds(105, 116, 400, 32);
        this.add(cbUser);
        lbAlbum.setBounds(5, 153, 100, 32);
        this.add(lbAlbum);
        cbAlbum.setBounds(105, 153, 400, 32);
        this.add(cbAlbum);
        lbUpload.setBounds(5, 190, 100, 32);
        this.add(lbUpload);
        tfUpload.setBounds(105, 190, 345, 32);
        this.add(tfUpload);
        btChoose.setBounds(455, 190, 50, 32);
        this.add(btChoose);
        btUpload.setBounds(260, 227, 120, 32);
        this.add(btUpload);
        btCancel.setBounds(385, 227, 120, 32);
        this.add(btCancel);

        this.setResizable(false);
        this.setSize(515, 300);

        if (this.medium != null) {
            // Read infromation if a medium is set
            tfTitle.setText(this.medium.getTitle());
            tfTitle.setEditable(false);
            cbGenre.setSelectedItem(this.medium.getGenre());
            cbGenre.setEnabled(false);
            cbLabel.setSelectedItem(this.medium.getLabel());
            cbLabel.setEnabled(false);
            cbUser.setSelectedItem(this.medium.getArtist());
            cbUser.setEnabled(false);
            cbAlbum.setSelectedItem(this.medium.getAlbum());
            cbAlbum.setEnabled(false);
        }
    }
}
