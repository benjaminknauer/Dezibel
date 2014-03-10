package de.dezibel.gui;

import java.text.SimpleDateFormat;
import java.util.LinkedList;

import javax.swing.table.DefaultTableModel;

import de.dezibel.data.Comment;
import de.dezibel.data.News;

/**
 * This class is used by NewsSidePanel for the News-JTable
 * @author Pascal
 *
 */
public class NewsSideTableModel extends DefaultTableModel {
	private String[] headlines = new String[]{"News"};
	    private Class<?>[] columnTypes = new Class<?>[]{String.class};
	    
	    private News[] data;

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
	            News c = data[row];
	            switch (col) {
	                case -1:
	                    return c;
	                    
	                case 0:
	                    return c.getTitle();             
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
	    public void setData(LinkedList<News> data) {
	        if (data == null) {
	            this.data = null;
	        } else {
	            this.data = new News[data.size()];
	            data.toArray(this.data);
	        }
	        fireTableDataChanged();
	    }
}
