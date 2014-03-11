package de.dezibel.gui;

import de.dezibel.data.Label;
import de.dezibel.data.Medium;
import de.dezibel.data.User;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 * TableModel for the favorized Users and Labels in the sidepanel
 *
 * @author Aris, Tristan
 */
public class FavoritesTableModel extends DefaultTableModel {

    private String[] headlines = new String[]{"Name"};
    private Class<?>[] columnTypes = new Class<?>[]{String.class};
    private User[] dataUser;
    private Label[] dataLabel;

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
        if (dataUser == null && dataLabel == null) {
            return 0;
        } else {
            if(dataUser == null){
                return dataLabel.length;
            } else if(dataLabel == null){
                return dataUser.length;
            } else{
                return dataUser.length + dataLabel.length;
            }
        }
    }

    @Override
    public Object getValueAt(int row, int col) {
        if (dataUser != null && row >= 0 && row < dataUser.length) {
            User u = dataUser[row];
            switch (col) {
                case -1:
                    return u;
                case 0:
                    if(u.isArtist()){
                        return u.getPseudonym();
                    }
                    return u.getFirstname() + " " + u.getLastname();
            }
        } else if(dataUser != null && row >= dataUser.length && row < dataUser.length + dataLabel.length){
            Label l = dataLabel[row - dataUser.length];
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
     *
     * @param data The data to display
     */
    public void setDataUser(LinkedList<User> data) {
        if (data == null) {
            this.dataUser = null;
        } else {
            this.dataUser = new User[data.size()];
            data.toArray(this.dataUser);
        }
        fireTableDataChanged();
    }

    public void setDataLabel(LinkedList<Label> data) {
        if (data == null) {
            this.dataLabel = null;
        } else {
            this.dataLabel = new Label[data.size()];
            data.toArray(this.dataLabel);
        }
        fireTableDataChanged();
    }

    public void setHeader(String[] headlines) {
        this.headlines = headlines;
    }
}
