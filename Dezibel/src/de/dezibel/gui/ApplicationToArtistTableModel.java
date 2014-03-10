package de.dezibel.gui;

import de.dezibel.data.Application;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 * Shows the information of users in the ProfilPanel.
 * @author Richard, Tobias, Alexander
 */
public class ApplicationToArtistTableModel extends DefaultTableModel {

    private String[] headlines = new String[]{"Label"};
    private Class<?>[] columnTypes = new Class<?>[]{String.class};

    private Application[] data;

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
            Application a = data[row];
            switch (col) {
                case -1:
                    return a;
                case 0:
                    return a.getLabel().getName();
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
    public void setData(LinkedList<Application> data) {
        if (data == null) {
            this.data = null;
        } else {
            this.data = new Application[data.size()];
            data.toArray(this.data);
        }
        fireTableDataChanged();
    }
    
    public Application getUserAt(int i){
        return data[i];
    }
    
}