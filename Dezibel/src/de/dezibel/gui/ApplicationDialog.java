package de.dezibel.gui;

import de.dezibel.control.ApplicationControl;
import de.dezibel.data.Album;
import de.dezibel.data.Label;
import de.dezibel.data.Medium;
import de.dezibel.data.News;
import de.dezibel.data.Playlist;
import de.dezibel.data.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Dialog to comment
 *
 * @author Tobias
 */
class ApplicationDialog extends JDialog {
   
    private User artist;
    private Label label;
    private boolean applicationFromArtist;
    private JLabel lbYourApplication;
    private JTextArea taText;
    private JScrollPane sp;
    private JButton btApply;
    private JButton btCancel;

    /**
     * Constructor
     *
     * @param frame The frame to block
     */
    public ApplicationDialog(JFrame frame, User artist, Label label, boolean applicationFromArtist) {
        super(frame);
        setModal(true);

        this.artist = artist;
        this.label = label;
        this.applicationFromArtist = applicationFromArtist;
        
        createComponents();
        createLayout();
    }

    private void createComponents() {
        if(applicationFromArtist)
            setTitle("Bewerbung an " + label.getName());
        else
            setTitle("Bewerbung an " + artist.getFirstname() + " \"" + artist.getPseudonym() + "\" " + artist.getLastname() + " für " + label.getName());

        lbYourApplication = new JLabel("Deine Bewerbung");
        taText = new JTextArea();
        sp = new JScrollPane(taText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        btApply = new JButton("Bewerbung abschicken");
        btCancel = new JButton("Abbrechen");

        btApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = taText.getText();
                if(text == null || text.trim().isEmpty() || text.equals("Dein Bewerbungstext")){
                    JOptionPane.showMessageDialog(ApplicationDialog.this, "Bitte einen Text angeben!");
                    return;
                }
                new ApplicationControl().createApplication(applicationFromArtist, taText.getText(), artist, label);
                ApplicationDialog.this.dispose();
            }
        });

        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ApplicationDialog.this.dispose();
            }
        });
    }
    
    private void createLayout(){
        this.setLayout(null);
        lbYourApplication.setBounds(5, 5, 100, 32);
        this.add(lbYourApplication);
        sp.setBounds(5, 42, 250, 250);
        this.add(sp);
        btApply.setBounds(5, 300, 120, 32);
        this.add(btApply);
        btCancel.setBounds(130, 300, 120, 32);
        this.add(btCancel);

        this.setResizable(false);
        this.setSize(265, 371);        
    }

}
