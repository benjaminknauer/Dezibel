/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dezibel.gui;

import de.dezibel.data.Label;
import de.dezibel.data.News;
import de.dezibel.data.User;
import java.util.LinkedList;
import javax.swing.GroupLayout;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Aris, Tristan
 */

public class NewsPanelTableModel extends DefaultTableModel {
    
    private String[] headlines = new String[]{"Title"};
    private Class<?>[] columnTypes = new Class<?>[]{String.class};
    private News[] dataNews;

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
        if (dataNews == null) {
	            return 0;
	} else {
            return dataNews.length;
        }
    }
    
    public int getNewsIndex(News n){
        
        for(int i = 0; i < dataNews.length; i++){
            if(n.equals(dataNews[i])){
                return i;
            }
        }
        return -1;
    }

    @Override
    public Object getValueAt(int row, int col) {
        if (dataNews != null && row >= 0 && row < dataNews.length) {
            News n = dataNews[row];
            switch (col) {
                case -1:
                    return n;
                case 0:
                    return n.getTitle();
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
    public void setDataNews(LinkedList<News> data) {
        if (data == null) {
            this.dataNews = null;
        } else {
            this.dataNews = new News[data.size()];
            data.toArray(this.dataNews);
        }
        fireTableDataChanged();
    }

    public void setHeader(String[] headlines) {
        this.headlines = headlines;
    }
}
