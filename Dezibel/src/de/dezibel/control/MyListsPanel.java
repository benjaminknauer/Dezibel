/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dezibel.control;

import de.dezibel.data.Database;
import de.dezibel.data.Playlist;
import de.dezibel.gui.DezibelPanel;
import de.dezibel.gui.DragablePanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.LinkedList;
import javax.swing.BoxLayout;

/**
 *
 * @author Benny
 */
public class MyListsPanel extends DragablePanel{
    JLabel lbTitel;
    JScrollPane scrollPane;
    JTable tblPlaylists;
    DefaultTableModel dtm;
    
    public MyListsPanel(DezibelPanel parent){
        super(parent);
        createComponents();
        createLayout();
    }
    
    private void createComponents(){
        lbTitel = new JLabel("Wiedergabelisten");
        dtm = new DefaultTableModel();
        tblPlaylists = new JTable(dtm);
        scrollPane = new JScrollPane(tblPlaylists);
        String[] header = {"Titel"};
        LinkedList<Playlist> myPlaylists = Database.getInstance().getLoggedInUser()
                .getCreatedPlaylists();
        LinkedList<Playlist> favoritePlaylists = Database.getInstance().getLoggedInUser()
                .getFavoritePlaylists();
        myPlaylists.addAll(favoritePlaylists);
        Object[][] datastuff = {myPlaylists.toArray()};
        dtm.setDataVector(datastuff, header);

    }
    
    private void createLayout(){
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(lbTitel);
        this.add(scrollPane);
    }
    
}
