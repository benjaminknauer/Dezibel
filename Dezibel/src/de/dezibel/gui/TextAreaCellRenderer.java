/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dezibel.gui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.UIDefaults;

/**
 *
 * @author Benny
 */
public class TextAreaCellRenderer implements TableCellRenderer {

    UIDefaults defaults = javax.swing.UIManager.getDefaults();
    private JTextArea textarea = new JTextArea(5, 10);

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        if (table.getSelectedRow() == row) {
            textarea.setBackground(defaults.getColor("List.selectionBackground"));
            textarea.setForeground(defaults.getColor("List.selectionForeground"));
        } else {
            textarea.setBackground(Color.WHITE);
            textarea.setForeground(Color.BLACK);
        }
        if (value != null) {
            textarea.setText(value.toString());
        }else{
            textarea.setText("");
        }
        textarea.setLineWrap(true);
        textarea.setWrapStyleWord(true);
        
        return textarea;
    }
}
