package de.dezibel.gui;

import com.sun.corba.se.spi.orbutil.fsm.Input;
import de.dezibel.control.LoginControl;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Pascal, Tobias
 *
 */
public class LoginPanel extends DragablePanel {

    private static final long serialVersionUID = 1L;
    private JPanel pnLoginPanel;
    private JLabel labelLogo;
    private JLabel labelMail;
    private JLabel labelPassword;
    private JTextField tfMail;
    private JPasswordField tfPassword;
    private JButton bnLogin;
    private JButton bnRegister;
    
    private LoginControl loginControl;

    public LoginPanel(DezibelPanel parent) {
        super(parent);
        ImageIcon logoIcon;
        File logoFile;
        try {
            Image logoImage = ImageIO.read(this.getClass().getResourceAsStream("/img/logo.png"));
            logoIcon = new ImageIcon(logoImage);
        } catch (IOException ex) {
            logoIcon = null;
            System.out.println("error during logoloading");
        }
        labelLogo = new JLabel(logoIcon);
        pnLoginPanel = new JPanel();
        labelMail = new JLabel("Mail:");
        labelPassword = new JLabel("Passwort:");
        tfMail = new JTextField();
        tfPassword = new JPasswordField();
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
        pnLoginPanel.setOpaque(false);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipady = 100;
        this.add(labelLogo, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(pnLoginPanel, gbc);
        
        this.setBackground(new Color(239, 239, 239));
        this.setBorder(null);
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
