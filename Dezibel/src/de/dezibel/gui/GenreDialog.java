/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dezibel.gui;

import de.dezibel.control.UploadControl;
import de.dezibel.data.Genre;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Bastian
 */
public class GenreDialog extends JDialog {

    private JTextField gName;
    private UploadControl uc;

    private JComboBox<Genre> superGenre;

    /**
     *
     * @param frame
     */
    public GenreDialog(JFrame frame) {
        super(frame);
        uc = new UploadControl();
    }

    public void init() {
        setTitle("Genre erstellen");

        JLabel lbName = new JLabel("Name");
        JLabel lbSuperGenre = new JLabel("Obergenre");
        JButton btCreate = new JButton("Erstellen");
        JButton btCancel = new JButton("Abbrechen");

        superGenre = new JComboBox<Genre>(uc.getSelectableGenres());

        btCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                GenreDialog.this.dispose();
            }
        });
        
        btCreate.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                new Genre(gName.getText(), (Genre)superGenre.getSelectedItem());
            }
            
        });
    }
}
