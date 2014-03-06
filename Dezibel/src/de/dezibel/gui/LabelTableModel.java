package de.dezibel.gui;

import de.dezibel.data.Label;
import de.dezibel.data.Medium;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 * Shows the information of labels in the searchpanel.
 * @author Richard, Tobias
 */
public class LabelTableModel extends DefaultTableModel {

    private String[] headlines = new String[]{"Name"};
    private Class<?>[] columnTypes = new Class<?>[]{String.class};

    private Label[] data;

    @Override
    public int getColumnCount() {
        return headlines.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnTypes[columnIndex];
    }

    @Override
    public String getColumnName(int columnIndex) {
        return headlines[columnIndex];
    }

    @Override
    public int getRowCount() {
        if (data == null) {
            return 0;
        } else {
            return data.length;
        }
    }

    @Override
    public Object getValueAt(int row, int col) {
        if (data != null && row >= 0 && row < data.length) {
            Label l = data[row];
            switch (col) {
                case -1:
                    return l;
                case 0:
                    return l.getName();
            }
        }
	return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    /**
     * Sets the data of this model.
     * @param data The data to display
     */
    public void setData(LinkedList<Medium> data) {
        if (data == null) {
            this.data = null;
        } else {
            this.data = new Label[data.size()];
            data.toArray(this.data);
        }
        fireTableDataChanged();
    }
    
}
