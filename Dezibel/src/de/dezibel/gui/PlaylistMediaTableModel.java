package de.dezibel.gui;

import de.dezibel.data.Medium;
import de.dezibel.data.Playlist;
import java.util.Date;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 * Shows the mediumobjects in a playlist.
 * @author Benjamin Knauer, Henner
 */
public class PlaylistMediaTableModel extends DefaultTableModel {
    private Playlist currentPlaylist;
    private String[] headlines = new String[]{"","Künstler", "Titel", "Album", 
        "Genre", "Uploaddatum", "Bewertung"};
    private Class<?>[] columnTypes = new Class<?>[]{Boolean.class, String.class, String.class,
        String.class, String.class, Date.class, Double.class};
    
    private boolean[] selected;
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
                    return selected[row]; 
                case 1:
                    return m.getArtist().getPseudonym();
                case 2:
                    return m.getTitle();
                case 3:
                    if (m.getAlbum() != null) return m.getAlbum().getTitle();
                    else return "";
                case 4:
                    if (m.getGenre() != null) return m.getGenre().getName();
                    else return "";
                case 5:
                    return m.getUploadDate();
                case 6:
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
    
    @Override
    public void setValueAt(Object value, int row, int column){
        if (data != null && row >= 0 && row < data.length) {
            selected[row] = (Boolean) value;
        }
    }
    
    /**
     * Sets the data of this model.
     * @param playlist The data to display
     */
    public void setData(Playlist playlist) {
        this.currentPlaylist = playlist;
        if (playlist == null) {
            this.data = null;
            this.selected = null;
        } else {
            this.data = new Medium[playlist.getList().size()];
            playlist.getList().toArray(this.data);
            this.selected = new boolean[playlist.getList().size()];
        }
        fireTableDataChanged();
    }
    
    public Playlist getCurrentPlaylist(){
        return currentPlaylist;
    }
    
}
