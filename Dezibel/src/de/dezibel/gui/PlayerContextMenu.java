package de.dezibel.gui;

import de.dezibel.data.Album;
import de.dezibel.data.Label;
import de.dezibel.data.Medium;
import de.dezibel.data.Playlist;
import de.dezibel.data.User;
import de.dezibel.player.Player;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Handles the right click actions in the player playlist
 * 
 * @author Tobias
 */
public class PlayerContextMenu {
    DefaultTableModel currentTableModel;
    JPopupMenu currentPopupMenu;
    JTable currentTable;
    DezibelPanel dp;
    
    /**
     * Constructor
     * @param parent The parent panel
     */
    public PlayerContextMenu(DezibelPanel parent) {
        dp = parent;
    }
    
    /**
     * Returns the specific context menu depends on what clicked on
     * @param table Table to work on
     * @return 
     */
    public JPopupMenu getContextMenu(JTable table) {
        currentTable = table;
        currentTableModel = (DefaultTableModel) table.getModel();
        
        createPopupMenu();
        
        return currentPopupMenu;
    }

    /**
     * Creates the PopupMenu which handles the right clicks on media in the player playlist
     */
    private void createPopupMenu() {
        currentPopupMenu = new JPopupMenu();
        JMenuItem menuItemRemove = new JMenuItem("Entfernen");
        JMenuItem menuItemDuplicate = new JMenuItem("Duplizieren");
        JMenuItem menuItemMoveUp = new JMenuItem("Hochschieben");
        JMenuItem menuItemMoveDown = new JMenuItem("Runterschieben");
        
        menuItemRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player.getInstance().removeMedium(currentTable.getSelectedRow());
            }
        });
        
        menuItemDuplicate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player.getInstance().addMedium((Medium) currentTable.getValueAt(currentTable.getSelectedRow(),-1));
            }
        });
        
        menuItemMoveUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player.getInstance().moveUp(currentTable.getSelectedRow());
            }
        });
        
        menuItemMoveDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player.getInstance().moveDown(currentTable.getSelectedRow());
            }
        });

        currentPopupMenu.add(menuItemRemove);
        currentPopupMenu.add(menuItemDuplicate);
        currentPopupMenu.add(menuItemMoveUp);
        currentPopupMenu.add(menuItemMoveDown);
    }
    
}
