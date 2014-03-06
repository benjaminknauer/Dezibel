/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dezibel.control;

import de.dezibel.data.Album;
import de.dezibel.data.Label;
import de.dezibel.data.Medium;
import de.dezibel.data.User;
import de.dezibel.gui.DezibelPanel;
import de.dezibel.player.Player;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

/**
 *
 * @author Benny
 */
public class ContextMenu {

    DefaultTableModel currentTableModel;
    JPopupMenu currentPopupMenu;
    JTable currentTable;
    DezibelPanel dp;
    
    public ContextMenu(DezibelPanel parent) {
        dp = parent;
    }

    public JPopupMenu getContextMenu(JTable table, MouseEvent me) {
        currentTable = table;
        currentTableModel = (DefaultTableModel) table.getModel();

        Point p = me.getPoint();
        int rowNumber = table.rowAtPoint(p);
        table.getSelectionModel().setSelectionInterval(rowNumber, rowNumber);

        if (currentTableModel.getValueAt(rowNumber, -1) instanceof Medium) {
            createMediumMenu();
        } else if (currentTableModel.getValueAt(rowNumber, -1) instanceof User) {
            createUserMenu();
        } else if (currentTableModel.getValueAt(rowNumber, -1) instanceof Label) {
            createLabelMenu();
        } else if (currentTableModel.getValueAt(rowNumber, -1) instanceof Album) {
            createAlbumMenu();
        }
        
        return currentPopupMenu;
    }

    private void createMediumMenu() {
        currentPopupMenu = new JPopupMenu();
        JMenuItem menuItemPlay = new JMenuItem("Abspielen");
        menuItemPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Medium m = (Medium) currentTableModel.getValueAt(
                        currentTable.getSelectedRow(), -1);
                if (m != null) {
                    Player.getInstance().addMedium(m);
                    Player.getInstance().play();
                }
            }
        });
        currentPopupMenu.add(menuItemPlay);
    }

    private void createUserMenu() {
        currentPopupMenu = new JPopupMenu();
        JMenuItem menuItemShowUser = new JMenuItem("Anzeigen");
        menuItemShowUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dp.showProfile();
            }
        });
        currentPopupMenu.add(menuItemShowUser);
    }

    private void createLabelMenu() {
        currentPopupMenu = new JPopupMenu();
        JMenuItem menuItemShowLabel = new JMenuItem("Anzeigen");
        menuItemShowLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Labelprofil des ausgewählten Labels anzeigen
            }
        });
        currentPopupMenu.add(menuItemShowLabel);
    }

    private void createAlbumMenu() {
        currentPopupMenu = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("Abspielen");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Gewähltes Album abspielen
            }
        });
        currentPopupMenu.add(menuItem);
    }
}
