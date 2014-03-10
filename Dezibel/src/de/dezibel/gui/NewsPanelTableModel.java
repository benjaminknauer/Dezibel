/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dezibel.gui;

import javax.swing.GroupLayout;

/**
 *
 * @author Tristan
 */

public class NewsPanelTableModel extends DragablePanel {
    
    
    
    
    
    
    
    
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
    
}
