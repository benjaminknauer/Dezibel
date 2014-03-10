package de.dezibel.gui;

import de.dezibel.data.Album;
import de.dezibel.data.Medium;
import de.dezibel.data.Playlist;
import java.util.Date;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 * Shows the mediumobjects in a playlist.
 * @author Benjamin Knauer, Henner
 */
public class AlbumMediaTableModel extends DefaultTableModel {
    
    private Album currentAlbum;
    private String[] headlines = new String[]{"Titel", "Künstler",  
        "Genre", "Uploaddatum", "Bewertung"};
    private Class<?>[] columnTypes = new Class<?>[]{String.class, String.class,
        String.class, String.class, Date.class};
    
    private Medium[] data;

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
            Medium m = data[row];
            switch (col) {
                case -1:
                    return m;
                case 0 :
                    return m.getTitle();
                case 1:
                    return m.getArtist().getPseudonym();
                case 2:
                    return m.getGenre().getName();
                case 3:
                    return m.getUploadDate();
                case 4:
                    // Round to 2 digits
                    return Math.round(m.getAvgRating() * 100) / 100;
            }
        }
	return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0;
    }
    
    
    /**
     * Sets the data of this model.
     * @param playlist The data to display
     */
    public void setData(Album album) {
        this.currentAlbum = album;
        if (album == null) {
            this.data = null;
        } else {
            this.data = new Medium[album.getMediaList().size()];
            album.getMediaList().toArray(this.data);
        }
        fireTableDataChanged();
    }
    
    public Album getCurrentAlbum(){
        return currentAlbum;
    }
    
}
