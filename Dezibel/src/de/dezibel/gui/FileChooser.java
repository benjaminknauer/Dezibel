package de.dezibel.gui;

import javax.swing.JFileChooser;

/**
 * FileChooser to select files to upload
 * 
 * @author Tobias
 */
public class FileChooser extends JFileChooser {

    private JFileChooser chooser;
    
    public FileChooser() {
        chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(chooser);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: "
                    + chooser.getSelectedFile().getName());
        }
    }
    
    public String getPathOfSelectedFile() {
        return chooser.getSelectedFile().getAbsolutePath();
    }

}
