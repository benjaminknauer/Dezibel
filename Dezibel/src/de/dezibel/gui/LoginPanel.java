package de.dezibel.gui;

import de.dezibel.control.LoginControl;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Panel to display and handle the login process
 * 
 * @author Pascal, Tobias
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

    /**
     * Constructor
     * @param parent The parent panel
     */
    public LoginPanel(DezibelPanel parent) {
        super(parent);
        //this.setBackground(Color.WHITE);
        ImageIcon logoIcon = new ImageIcon(this.getClass().getResource("/img/logo.png"));

        labelLogo = new JLabel(logoIcon);
        pnLoginPanel = new JPanel();
        labelMail = new JLabel("Email:");
        labelPassword = new JLabel("Passwort:");
        tfMail = new JTextField();
        tfPassword = new JPasswordField();
        bnLogin = new JButton("Login");
        bnRegister = new JButton("Registrieren");
        pnLoginPanel.setOpaque(false);
        //pnLoginPanel.setBackground(parent.getBackground());
        pnLoginPanel.setBackground(Color.WHITE);
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

        ///pnLoginPanel.setBackground(this.getBackground());
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
        
        //this.setBackground(new Color(239, 239, 239));
        this.setBorder(null);
    }

    /**
     * Handles the process when the lgoin button is hit
     */
    private void onLogin() {
        if (this.tfMail.getText().isEmpty() || this.tfPassword.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email oder Passwort ist leer",
                    "Fehler", JOptionPane.INFORMATION_MESSAGE);
        } else {
            if(loginControl.checkIfMailExists(this.tfMail.getText()))
                if(loginControl.checkPassword(this.tfPassword.getText())) {
                    if(!(loginControl.checkLock())){
                        loginControl.markLoggedInUser();
                        this.clearTextFields();
                        this.parent.showWorkspace();
                    }
                    else
                        JOptionPane.showMessageDialog(this, 
                                "Ihr Account ist temporär gesperrt",
                                "Account gesperrt",
                                JOptionPane.INFORMATION_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(this, "Passwort falsch",
                    "Falsches Passwort", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Ein Benutzer mit dieser Email existiert nicht",
                    "Benutzer existiert nicht", JOptionPane.INFORMATION_MESSAGE);
                    
        }
    }

    /**
     * Handles the process when the register button is hit
     */
    private void onRegister() {
        this.clearTextFields();
        this.parent.showRegistration();
    }
    
    /**
     * Clears all text fields
     */
    public void clearTextFields() {
        JTextField tf;
        for (Component c : this.pnLoginPanel.getComponents()) {
            if (c instanceof JTextField) {
                tf = (JTextField) c;
                tf.setText("");
            }
        }
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
