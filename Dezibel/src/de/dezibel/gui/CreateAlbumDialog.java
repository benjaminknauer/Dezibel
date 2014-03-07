package de.dezibel.gui;

import com.sun.scenario.effect.Effect;
import de.dezibel.control.AlbumControl;
import de.dezibel.data.Database;
import de.dezibel.data.Medium;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import javafx.scene.chart.PieChart;

/**
 * 
 * @author Benny
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
    
    public CreateAlbumDialog(JFrame frame, Medium medium) {
        super(frame);
        
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
                JFileChooser jfc = new JFileChooser();
                String path = jfc.getSelectedFile().getPath();
                tfFilePath.setText(path);
            }
        });
        btnCreate = new JButton("Anlegen");
        btnCreate.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new AlbumControl().createAlbum(tfAlbumTitle.getText(), medium, tfFilePath.getText());
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
        lbAlbumTitle.setBounds(5, 5, 100, 32);
        this.add(lbAlbumTitle);
        tfAlbumTitle.setBounds(5, 42, 250, 250);
        this.add(tfAlbumTitle);
        btnCreate.setBounds(5, 300, 120, 32);
        this.add(btnCreate);
        btnCancel.setBounds(130, 300, 120, 32);
        this.add(btnCancel);

        this.setResizable(false);
        this.setSize(400, 256);
    }
    
}