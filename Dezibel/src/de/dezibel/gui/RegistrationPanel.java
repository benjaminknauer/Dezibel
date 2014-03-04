package de.dezibel.gui;

import de.dezibel.control.RegistrationControl;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Pascal, Tobias
 *
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
    private JTextField tfPassword;
    private JTextField tfPasswordRecap;
    private JTextField tfFirstname;
    private JTextField tfLastname;

    private RegistrationControl regControl;

    public RegistrationPanel(DezibelPanel parent) {
        super(parent);

        this.createComponents();
        this.pnMain.setLayout(this.createLayout());
        this.setLayout(new GridBagLayout());
        this.add(pnMain);
        this.regControl = new RegistrationControl();
    }

    private void createComponents() {
        pnMain = new JPanel();
        pnMain.setBackground(this.getBackground());
        bnRegister = new JButton("Register");
        bnBack = new JButton("Back");

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

        this.lbMail = new JLabel("Mail:");
        this.lbPassword = new JLabel("Password:");
        this.lbFirstname = new JLabel("Firstname:");
        this.lbLastname = new JLabel("Lastname:");

        this.tfMail = new JTextField();
        this.tfPassword = new JTextField();
        this.tfPasswordRecap = new JTextField();
        this.tfFirstname = new JTextField();
        this.tfLastname = new JTextField();
    }

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

    private void onRegister() {
        if (this.tfMail.getText().isEmpty()
                || this.tfPassword.getText().isEmpty()
                || this.tfPasswordRecap.getText().isEmpty()
                || this.tfFirstname.getText().isEmpty()
                || this.tfLastname.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mandatory field not filled, please fill every field",
                    "Type Error", JOptionPane.INFORMATION_MESSAGE);
        } 
        else if (!this.regControl.checkIfMailAlreadyInUse(this.tfMail.getText())) {
            if (this.tfPassword.getText().equals(this.tfPasswordRecap.getText())) {
                this.regControl.addUser(this.tfPassword.getText(),
                        this.tfMail.getText(),this.tfFirstname.getText(),this.tfLastname.getText());
                JOptionPane.showMessageDialog(this, "User succesfully created",
                        "Type Error", JOptionPane.INFORMATION_MESSAGE);
                this.onBack();
            }
            else {
                JOptionPane.showMessageDialog(this, "The password differs",
                        "Type Error", JOptionPane.INFORMATION_MESSAGE);
            }
        } 
        else {
            JOptionPane.showMessageDialog(this, "Mail already in use",
                    "Type Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void onBack() {
        JTextField tf;
        for (Component c : this.getComponents()) {
            if (c.getClass().isInstance(JTextField.class)) {
                tf = (JTextField) c;
                tf.setText("");
            }
        }
        this.parent.showLogin();
    }
}
