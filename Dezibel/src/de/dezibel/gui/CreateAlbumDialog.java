package de.dezibel.gui;

import com.sun.scenario.effect.Effect;
import de.dezibel.control.AlbumControl;
import de.dezibel.data.Database;
import de.dezibel.data.Medium;
import de.dezibel.UpdateEntity;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.io.File;
import javafx.scene.chart.PieChart;
import javax.swing.filechooser.FileFilter;

/**
 * Dialog for creating an album.
 * @author Benny, Henner
 */
public class CreateAlbumDialog extends JDialog{
    
    private JLabel lbAlbumTitle;
    private JTextField tfAlbumTitle;
    private JLabel lbFilePath;
    private JTextField tfFilePath;
    private JButton btnFilePath;
    private JButton btnCreate;
    private JButton btnCancel;
    
    private Medium medium;
    private DezibelPanel dPanel;
    
    /**
     * Constructor.
     * @param frame parent frame
     * @param medium first medium in the album
     */
    public CreateAlbumDialog(JFrame frame, Medium medium, DezibelPanel dp) {
        super(frame);
        dPanel = dp;
        this.setLocationRelativeTo(frame);
        this.medium = medium;
        
        createComponents();
        createLayout();
    }
    
    private void createComponents() {
        lbAlbumTitle = new JLabel("Titel des Albums");
        tfAlbumTitle = new JTextField("");
        
        lbFilePath = new JLabel("Album-Cover");
        tfFilePath = new JTextField("");
        tfFilePath.setEditable(false);
        
        btnFilePath = new JButton("...");
        btnFilePath.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileFilter(new FileFilter() {

                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getPath().toLowerCase().endsWith(".jpg")
                                || f.getPath().toLowerCase().endsWith(".jpeg") 
                                || f.getPath().toLowerCase().endsWith(".png")
                                || f.getPath().toLowerCase().endsWith(".bmp"); 
                    }

                    @Override
                    public String getDescription() {
                        return "nur Bilddateien";
                    }
                });
                int returnVal = fc.showOpenDialog(CreateAlbumDialog.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    tfFilePath.setText(fc.getSelectedFile().getAbsolutePath());
                }
            }
        });
        btnCreate = new JButton("Anlegen");
        btnCreate.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new AlbumControl().createAlbum(tfAlbumTitle.getText(), medium, tfFilePath.getText());
                dPanel.refresh(UpdateEntity.ALBUM);
                CreateAlbumDialog.this.dispose();
            }
        }
                );
        btnCancel = new JButton("Abbrechen");
        btnCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                CreateAlbumDialog.this.dispose();
            }
        });
    }
    
    private void createLayout(){
        
        this.setLayout(null);
        lbAlbumTitle.setBounds(10, 5, 200, 32);
        this.add(lbAlbumTitle);
        tfAlbumTitle.setBounds(10, 42, 250, 32);
        this.add(tfAlbumTitle);
        
        lbFilePath.setBounds(10, 79, 200, 32);
        this.add(lbFilePath);
        tfFilePath.setBounds(10, 116, 220, 32);
        this.add(tfFilePath);
        
        btnFilePath.setBounds(225, 118, 40, 28);
        this.add(btnFilePath);
        
        btnCreate.setBounds(130, 153, 120, 32);
        this.add(btnCreate);
        btnCancel.setBounds(5, 153, 120, 32);
        this.add(btnCancel);

        this.setResizable(false);
        this.setSize(270, 215 );
    }
    
}