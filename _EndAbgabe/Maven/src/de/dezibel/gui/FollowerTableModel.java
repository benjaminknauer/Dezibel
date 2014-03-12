package de.dezibel.gui;

import de.dezibel.data.User;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 * TableModel for the followers in the Profile of a Label or User
 * @author Richard, Tobias
 */
public class FollowerTableModel extends DefaultTableModel {

    private String[] headlines = new String[]{"Vorname", "Nachname"};
    private Class<?>[] columnTypes = new Class<?>[]{String.class, String.class};

    private User[] data;

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
            User u = data[row];
            switch (col) {
                case -1:
                    return u;
                case 0:
                    return u.getFirstname();
                case 1:
                    return u.getLastname();
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
    public void setData(LinkedList<User> data) {
        if (data == null) {
            this.data = null;
        } else {
            this.data = new User[data.size()];
            data.toArray(this.data);
        }
        fireTableDataChanged();
    }
    
    public User getUserAt(int i){
        return data[i];
    }
    
}
