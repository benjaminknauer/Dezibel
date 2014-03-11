package de.dezibel.gui;

import de.dezibel.data.User;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 * Shows the information of users in the SearchPanel.
 * @author Richard, Tobias
 */
public class ArtistTableModel extends DefaultTableModel {

    private String[] headlines = new String[]{"Vorname", "Nachname", "Künstlername"};
    private Class<?>[] columnTypes = new Class<?>[]{String.class, String.class, String.class};

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
                case 2:
                    return u.getPseudonym();
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
    /**
     * Returns the user in row i.
     * @param i the row of the user you want to have
     * @return the user in the specified row
     */
    public User getUserAt(int i){
        return data[i];
    }
    
}
