package de.dezibel.gui;

import de.dezibel.control.AlbumControl;
import de.dezibel.control.UploadControl;
import de.dezibel.data.Album;
import de.dezibel.data.Genre;
import de.dezibel.data.Label;
import de.dezibel.data.Medium;
import de.dezibel.data.User;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

/**
 * Dialog to upload a medium
 *
 * @author Tobias, Richard
 */
public final class UploadDialog extends JDialog {

    private final UploadControl upc;

    private JTextField tfUpload;

    private JComboBox<User> cbUser;
    private JComboBox<Genre> cbGenre;
    private JComboBox<Label> cbLabel;
    private JComboBox<Object> cbAlbum;

    private final Label label;
    private final Medium medium;

    /**
     * Constructor
     *
     * @param frame the fram to block
     * @param label the standard label if the user wants to upload for a label.
     * label is set if the UploadDialog is opened from a labelprofil from a
     * labelmanager
     * @param medium is set if the medium already exists but contains no file
     * and you want to upload a file now
     */
    public UploadDialog(JFrame frame, Label label, Medium medium) {
        super(frame);
        setModal(true);
        upc = new UploadControl();
        this.label = label;
        this.medium = medium;
        this.init();
    }

    /**
     * Inits the dialogframe
     */
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
        cbAlbum = new JComboBox<>((Object[]) upc.getSelectableAlbums());
        cbAlbum.addItem("Neues Album erstellen");

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

        btUpload.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (tfTitle.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(UploadDialog.this, "Bitte geben Sie einen Titel an", "Titel angeben", JOptionPane.INFORMATION_MESSAGE);
                } else if (medium == null) {
                    Object albumSelection = cbAlbum.getSelectedItem();
                    Album album;
                    String newAlbumName = null;
                    String coverPath = null;
                    if (albumSelection instanceof String) {
                        // Get attributes for new album
                        JPanel panel = new JPanel();
                        JLabel lbAlbumTitle = new JLabel("Titel des Albums");
                        JTextField tfAlbumTitle = new JTextField("");

                        JLabel lbFilePath = new JLabel("Album-Cover");
                        final JTextField tfFilePath = new JTextField("");
                        tfFilePath.setEditable(false);

                        JButton btnFilePath = new JButton("...");
                        btnFilePath.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JFileChooser fc = new JFileChooser();
                                fc.setFileFilter(new FileFilter() {

                                    @Override
                                    public boolean accept(File f) {
                                        return f.isDirectory() || f.getPath().endsWith(".jpg")
                                                || f.getPath().endsWith(".jpeg")
                                                || f.getPath().endsWith(".png")
                                                || f.getPath().endsWith(".bmp");
                                    }

                                    @Override
                                    public String getDescription() {
                                        return "nur Bilddateien";
                                    }
                                });
                                int returnVal = fc.showOpenDialog(UploadDialog.this);
                                if (returnVal == JFileChooser.APPROVE_OPTION) {
                                    tfFilePath.setText(fc.getSelectedFile().getAbsolutePath());
                                }
                            }
                        });
                        panel.setLayout(null);
                        lbAlbumTitle.setBounds(10, 5, 200, 32);
                        panel.add(lbAlbumTitle);
                        tfAlbumTitle.setBounds(10, 42, 250, 32);
                        panel.add(tfAlbumTitle);

                        lbFilePath.setBounds(10, 79, 200, 32);
                        panel.add(lbFilePath);
                        tfFilePath.setBounds(10, 116, 220, 32);
                        panel.add(tfFilePath);

                        btnFilePath.setBounds(225, 118, 40, 28);
                        panel.add(btnFilePath);
                        
                        panel.setPreferredSize(new Dimension(270, 215));

                        int ret = JOptionPane.showConfirmDialog(UploadDialog.this, panel,
                                "Neues Album anlegen", JOptionPane.OK_CANCEL_OPTION);
                        if (ret == JOptionPane.OK_OPTION) {
                            newAlbumName = tfAlbumTitle.getText().trim();
                            coverPath = tfFilePath.getText();
                        }
                        album = null;
                    } else {
                        album = (Album) cbAlbum.getSelectedItem();
                    }
                    switch (upc.upload(tfTitle.getText(), (User) cbUser.getSelectedItem(),
                            (tfUpload.getText().isEmpty() ? null : tfUpload.getText()),
                            (Genre) cbGenre.getSelectedItem(),
                            (Label) cbLabel.getSelectedItem(),
                            album, newAlbumName, coverPath)) {
                        case SUCCESS:
                            JOptionPane.showMessageDialog(UploadDialog.this, "Das Medium wurde erfolgreich erstellt!", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                            UploadDialog.this.dispose();
                            break;
                        case USER_IS_NOT_ARTIST:
                            upc.promoteUserToArtist(UploadDialog.this, (User) cbUser.getSelectedItem());
                            cbUser.setModel(new DefaultComboBoxModel<>(upc.getSelectableUsers((Label) cbLabel.getSelectedItem())));
                            break;
                    }
                } else {
                    switch (medium.upload(tfUpload.getText())) {
                        case UPLOAD_ERROR:
                            JOptionPane.showMessageDialog(UploadDialog.this,
                                    "Die Datei konnte nicht hochgeladen werden.",
                                    "Fehler", JOptionPane.ERROR_MESSAGE);
                            break;
                        case SUCCESS:
                            JOptionPane.showMessageDialog(UploadDialog.this, "Das Medium wurde erfolgreich erstellt!", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                            UploadDialog.this.dispose();
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
