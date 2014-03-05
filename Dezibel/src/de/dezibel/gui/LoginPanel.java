package de.dezibel.gui;

import de.dezibel.control.LoginControl;
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
public class LoginPanel extends DragablePanel {

    private static final long serialVersionUID = 1L;
    private JPanel pnLoginPanel;
    private JLabel labelMail;
    private JLabel labelPassword;
    private JTextField tfMail;
    private JTextField tfPassword;
    private JButton bnLogin;
    private JButton bnRegister;
    
    private LoginControl loginControl;

    public LoginPanel(DezibelPanel parent) {
        super(parent);

        pnLoginPanel = new JPanel();
        labelMail = new JLabel("Mail:");
        labelPassword = new JLabel("Passwort:");
        tfMail = new JTextField();
        tfPassword = new JTextField();
        bnLogin = new JButton("Login");
        bnRegister = new JButton("Register");
        
        loginControl = new LoginControl();

        bnLogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                onLogin();
            }
        });

        bnRegister.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                onRegister();
            }
        });

        pnLoginPanel.setBackground(this.getBackground());
        tfMail.setBounds(105, 102, 200, 30);
        tfPassword.setBounds(105, 102, 200, 30);

        int min = 128;
        int pref = 128;
        int max = 128;

        GroupLayout layout = new GroupLayout(pnLoginPanel);
        layout.setHorizontalGroup(layout
                .createParallelGroup(GroupLayout.Alignment.CENTER, true)
                .addGroup(
                        GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(labelMail, 128, 128, 128)
                        .addComponent(tfMail, 128, 128, 128))
                .addGroup(
                        GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(labelPassword, 128, 128, 128)
                        .addComponent(tfPassword, 128, 128, 128))
                .addGroup(
                        GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                        .addGap(min, pref, max)
                        .addComponent(bnLogin).addComponent(bnRegister)
                ));

        layout.setVerticalGroup(layout.createParallelGroup(
                GroupLayout.Alignment.CENTER, true)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                                .addComponent(labelMail, 32, 32, 32)
                                .addComponent(tfMail, 32, 32, 32))
                        .addGroup(
                                layout.createParallelGroup()
                                .addComponent(labelPassword, 32, 32, 32)
                                .addComponent(tfPassword, 32, 32, 32))
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addGap(10, 20, 30)
                                .addComponent(bnLogin)
                                .addComponent(bnRegister)
                        ))
        );

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        pnLoginPanel.setLayout(layout);
        this.setLayout(new GridBagLayout());
        this.add(pnLoginPanel);
    }

    private void onLogin() {
        if (this.tfMail.getText().isEmpty() || this.tfPassword.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mail or Password can not be empty",
                    "Type Error", JOptionPane.INFORMATION_MESSAGE);
        } else {
            if(loginControl.checkIfMailExists(this.tfMail.getText()))
                if(loginControl.checkPassword(this.tfPassword.getText())) {
                    loginControl.markLoggedInUser();
                    this.parent.showWorkspace();
                }
                else
                    JOptionPane.showMessageDialog(this, "Password false",
                    "Type Error", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "A user with this mail doesnt exist",
                    "Type Error", JOptionPane.INFORMATION_MESSAGE);
                    
        }
    }

    private void onRegister() {
        this.parent.showRegistration();
    }
}
