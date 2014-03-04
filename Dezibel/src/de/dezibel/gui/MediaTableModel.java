package de.dezibel.gui;

import de.dezibel.data.Medium;
import de.dezibel.data.User;
import java.util.LinkedList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Tobias
 */
public class MediaTableModel extends AbstractTableModel {

    LinkedList<Medium> results;

    @Override
    public int getRowCount() {
        return results.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int row, int column) {
        switch (column) {
            case 0:
                return results.get(row);
            case 1:
                return results.get(row);
            case 2:
                return results.get(row);
            case 3:
                return results.get(row);
            default:
                throw new IllegalArgumentException("Ungültiger Spaltenindex");
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Title";
            case 1:
                return "Artist";
            case 2:
                return "Album";
            case 3:
                return "Genre";
            default:
                throw new IllegalArgumentException("Ungültiger Spaltenindex");
        }
    }

    public void addMedium(String title, User artist, String path) {
        results.add(new Medium(title, artist, path));
        fireTableDataChanged();
    }

    public Medium getPerson(int index) {
        return results.get(index);
    }

    public void removeMedium(int index) {
        results.remove(index);
        fireTableDataChanged();
    }

    public void setPersonen(LinkedList<Medium> media) {
        this.results = media;
        fireTableDataChanged();
    }

    public LinkedList<Medium> getMedia() {
        return results;
    }
}
