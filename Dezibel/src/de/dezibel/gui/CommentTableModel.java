package de.dezibel.gui;

import de.dezibel.data.Comment;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;

/**
 * Shows the information of albums in the ProfilPanel.
 * @author Richard, Tobias
 */
public class CommentTableModel extends DefaultTableModel {

    private String[] headlines = new String[]{"Kommentare", "von Benutzer", "erstellt am"};
    private Class<?>[] columnTypes = new Class<?>[]{String.class, String.class, String.class};

    private Comment[] data;

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
            Comment c = data[row];
            switch (col) {
                case -1:
                    return c;
                case 0:
                    return c.getText();
                case 1:
                    return c.getAuthor();
                case 2:
                     SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                     return sdf.format(c.getCreationDate());
                            
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
    public void setData(LinkedList<Comment> data) {
        if (data == null) {
            this.data = null;
        } else {
            this.data = new Comment[data.size()];
            data.toArray(this.data);
        }
        fireTableDataChanged();
    }
    
}
