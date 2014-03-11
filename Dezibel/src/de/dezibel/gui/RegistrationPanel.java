package de.dezibel.gui;

import de.dezibel.control.RegistrationControl;
import de.dezibel.io.MailUtil;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Panel to display and handle the registration process
 *
 * @author Richard, Tobias, Pascal
 */
public class RegistrationPanel extends DragablePanel {

    private static final long serialVersionUID = 1L;
    private JPanel pnMain;
    private JButton bnRegister;
    private JButton bnBack;

    private JLabel lbMail;
    private JLabel lbPassword;
    private JLabel lbFirstname;
    private JLabel lbLastname;

    private JTextField tfMail;
    private JPasswordField tfPassword;
    private JPasswordField tfPasswordRecap;
    private JTextField tfFirstname;
    private JTextField tfLastname;

    private RegistrationControl regControl;

    /**
     * Constructor
     *
     * @param parent The parent panel
     */
    public RegistrationPanel(DezibelPanel parent) {
        super(parent);

        this.createComponents();
        this.pnMain.setLayout(this.createLayout());
        this.setLayout(new GridBagLayout());
        this.add(pnMain);
        this.regControl = new RegistrationControl();
    }

    /**
     * Creates all necessary components
     */
    private void createComponents() {
        pnMain = new JPanel();
        pnMain.setBackground(this.getBackground());
        bnRegister = new JButton("Registrieren");
        bnBack = new JButton("Zurück");

        bnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRegister();
            }
        });

        bnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onBack();
            }
        });

        this.lbMail = new JLabel("Email:");
        this.lbPassword = new JLabel("Passwort:");
        this.lbFirstname = new JLabel("Vorname:");
        this.lbLastname = new JLabel("Nachname:");

        this.tfMail = new JTextField();
        this.tfPassword = new JPasswordField();
        this.tfPassword = new JPasswordField();
        this.tfPasswordRecap = new JPasswordField();
        this.tfFirstname = new JTextField();
        this.tfLastname = new JTextField();
    }

    /**
     * Generates the layout and returns it
     *
     * @return The layout to use
     */
    private GroupLayout createLayout() {
        GroupLayout layout = new GroupLayout(this.pnMain);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER, true)
                .addGroup(GroupLayout.Alignment.LEADING,
                        layout.createSequentialGroup()
                        .addComponent(lbMail, 128, 128, 128)
                        .addComponent(tfMail, 128, 128, 128))
                .addGroup(GroupLayout.Alignment.LEADING,
                        layout.createSequentialGroup()
                        .addComponent(lbPassword, 128, 128, 128)
                        .addComponent(tfPassword, 128, 128, 128))
                .addGroup(GroupLayout.Alignment.LEADING,
                        layout.createSequentialGroup()
                        .addComponent(lbPassword, 128, 128, 128)
                        .addComponent(tfPasswordRecap, 128, 128, 128))
                .addGroup(GroupLayout.Alignment.LEADING,
                        layout.createSequentialGroup()
                        .addComponent(lbFirstname, 128, 128, 128)
                        .addComponent(tfFirstname, 128, 128, 128))
                .addGroup(GroupLayout.Alignment.LEADING,
                        layout.createSequentialGroup()
                        .addComponent(lbLastname, 128, 128, 128)
                        .addComponent(tfLastname, 128, 128, 128))
                .addGroup(GroupLayout.Alignment.CENTER,
                        layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(bnRegister)
                        .addComponent(bnBack))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER, true)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup(
                                        GroupLayout.Alignment.LEADING, true)
                                .addComponent(lbMail, 32, 32, 32)
                                .addComponent(tfMail, 32, 32, 32))
                        .addGroup(
                                layout.createParallelGroup()
                                .addComponent(lbPassword, 32, 32, 32)
                                .addComponent(tfPassword, 32, 32, 32))
                        .addGroup(
                                layout.createParallelGroup()
                                .addComponent(lbPassword, 32, 32, 32)
                                .addComponent(tfPasswordRecap, 32, 32, 32))
                        .addGroup(
                                layout.createParallelGroup()
                                .addComponent(lbFirstname, 32, 32, 32)
                                .addComponent(tfFirstname, 32, 32, 32))
                        .addGroup(
                                layout.createParallelGroup()
                                .addComponent(lbLastname, 32, 32, 32)
                                .addComponent(tfLastname, 32, 32, 32))
                        .addGroup(
                                layout.createParallelGroup(
                                        GroupLayout.Alignment.TRAILING)
                                .addGap(10, 20, 30)
                                .addComponent(bnRegister)
                                .addComponent(bnBack)))
        );

        return layout;
    }

    /**
     * Handles the process when the register button is hit.
     */
    private void onRegister() {
        if (this.tfMail.getText().isEmpty()
                || this.tfPassword.getText().isEmpty()
                || this.tfPasswordRecap.getText().isEmpty()
                || this.tfFirstname.getText().isEmpty()
                || this.tfLastname.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bitte füllen Sie alle Pflichtfelder",
                    "Pflichtfelder leer", JOptionPane.INFORMATION_MESSAGE);
        } else if (!this.regControl.checkIfMailAlreadyInUse(this.tfMail.getText())) {
            if (this.regControl.checkIfMailValid(this.tfMail.getText())) {
                if (regControl.checkIfNamesValid(this.tfFirstname.getText(), this.tfLastname.getText())) {
                    if (this.tfPassword.getText().equals(this.tfPasswordRecap.getText())) {
                        if (this.regControl.checkIfPWValid(this.tfPasswordRecap.getText())) {
                            this.regControl.addUser(this.tfPassword.getText(),
                                    this.tfMail.getText(), this.tfFirstname.getText(), this.tfLastname.getText());
                            MailUtil.sendMail("Registrierung auf Dezibel",
                                    "Hallo " + this.tfFirstname.getText() + ",\n\n"
                                    + "deine Registrierung war erfolgreich.",
                                    this.tfMail.getText());
                            JOptionPane.showMessageDialog(this, "Ihr Konto wurde erfolgreich eingerichtet",
                                    "Registrierung erfolgreich", JOptionPane.INFORMATION_MESSAGE);
                            this.onBack();
                        } else {
                            JOptionPane.showMessageDialog(this, "Das Passwort muss mindestens sechs Zeichen lang sein und je mindestens eine Zahl und einen Buchstaben enthalten.",
                                "Passwort", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Das Passwort ist nicht identisch",
                                "Passwort", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Bitte gültige Namen eingeben",
                            "Name ungültig", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Die eingegebene Email-Adresse ist ungültig",
                        "Email ungültig", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Es existiert bereichts ein Konto zu dieser Email",
                    "Email existiert schon", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Handles the process when the back button is hit
     */
    private void onBack() {
        JTextField tf;
        for (Component c : this.pnMain.getComponents()) {
            if (c instanceof JTextField) {
                tf = (JTextField) c;
                tf.setText("");
            }
        }
        this.parent.showLogin();
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub

    }

    @Override
    public void refresh() {
        // TODO Auto-generated method stub

    }
}
