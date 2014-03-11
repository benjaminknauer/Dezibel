package de.dezibel.gui;

import de.dezibel.data.Application;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 * The table model for applications as they are shown in a label's profile
 * @author Richard, Tobias, Alexander
 */
public class ApplicationToLabelTableModel extends DefaultTableModel {

    private String[] headlines = new String[]{"Vorname", "Nachname", "Künstlername"};
    private Class<?>[] columnTypes = new Class<?>[]{String.class, String.class, String.class};

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
                    return a.getUser().getFirstname();
                case 1:
                    return a.getUser().getLastname();
                case 2:
                    return a.getUser().getPseudonym();
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
    
    /**
     * Returns the application in row i.
     * @param i the row of the application you want to have
     * @return the application in the specified row
     */    
    public Application getApplicationAt(int i){
        return data[i];
    }
    
}
