package de.dezibel.gui;

import de.dezibel.data.Label;
import de.dezibel.data.Playlist;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 * TableModel for playlists. Used in the "mylists" sidepanel.
 * @author Benny
 */
public class MyListsTableModel extends DefaultTableModel {

    private String[] headlines = new String[]{"Titel"};
    private Class<?>[] columnTypes = new Class<?>[]{String.class};

    private Playlist[] data;

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
            Playlist p = data[row];
            switch (col) {
                case -1:
                    return p;
                case 0:
                    return p.getTitle();
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
    public void setData(LinkedList<Playlist> data) {
        if (data == null) {
            this.data = null;
        } else {
            this.data = new Playlist[data.size()];
            data.toArray(this.data);
        }
        fireTableDataChanged();
    }
    
}

