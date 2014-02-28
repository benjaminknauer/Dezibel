package de.dezibel.gui;

import java.awt.BorderLayout;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LoginPanel extends DragablePanel {
	
	private JLabel labelMail;
	private JLabel labelPassword;
	private JTextField tfMail;
	private JTextField tfPassword;
	private JButton bnLogin;
	private JButton bnRegister;

	public LoginPanel(DezibelPanel parent){
		super(parent);
		labelMail = new JLabel("Mail");
		labelPassword  = new JLabel ("Passwort");
		
		tfMail = new JTextField();
        tfMail.setBounds(105,102,200,30);
        
        tfPassword = new JTextField();
        tfPassword.setBounds(105,102,200,30);
        bnLogin = new JButton("Login");
        bnRegister = new JButton("Register");
        
        //        this.add(labelMail);
//        this.add(labelPassword);
//        this.add(tfMail);
//        this.add(tfPassword);
//        
        int min = 0;
        int pref = 20;
        int max = 100;
        GroupLayout layout = new GroupLayout(this);
        layout.setHorizontalGroup(
        		layout.createParallelGroup(GroupLayout.Alignment.CENTER,true)
        		.addGroup(GroupLayout.Alignment.LEADING,layout.createSequentialGroup()
        				.addComponent(labelMail,128,128,128)
        				.addComponent(tfMail,128,128,128))
        		.addGroup(GroupLayout.Alignment.LEADING,layout.createSequentialGroup()
        						.addComponent(labelPassword,128,128,128)
        						.addComponent(tfPassword,128,128,128))
        		.addGroup(GroupLayout.Alignment.LEADING,layout.createSequentialGroup()
        						.addGap(min, pref, max)
        						.addComponent(bnLogin)
        						.addComponent(bnRegister))
        		);
        
        layout.setVerticalGroup(
        		layout.createParallelGroup(GroupLayout.Alignment.CENTER,true)
        		.addGroup(layout.createSequentialGroup()
        				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING,true)
        						.addComponent(labelMail,32,32,32)
        						.addComponent(tfMail,32,32,32))
        				.addGroup(layout.createParallelGroup()
        						.addComponent(labelPassword,32,32,32)
        						.addComponent(tfPassword,32,32,32))
        				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
        						.addGap(10,20,30)
        						.addComponent(bnLogin)
        						.addComponent(bnRegister)
        						))
        		
        		);
        this.setLayout(layout);

      
        
        
	}
	
	
}
