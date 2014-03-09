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
    private JFrame frame;

    /**
     * Constructor
     *
     * @param frame The frame to block
     */
    public ApplicationDialog(JFrame frame, User artist, Label label, boolean applicationFromArtist) {
        super(frame);
        setModal(true);

        this.frame = frame;
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
        lbYourApplication.setBounds(303, 5, 100, 32);
        this.add(lbYourApplication);
        sp.setBounds(5, 42, 700, 250);
        this.add(sp);
        btApply.setBounds(200, 300, 175, 32);
        this.add(btApply);
        btCancel.setBounds(412, 300, 120, 32);
        this.add(btCancel);

        this.setResizable(false);   
        this.setBounds(frame.getX() + (frame.getWidth()-715) / 2, frame.getY() + (frame.getHeight() - 371)/2, 715, 371);
    }

}
