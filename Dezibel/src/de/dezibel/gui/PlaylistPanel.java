
package de.dezibel.gui;

import de.dezibel.data.Playlist;

/**
 *
 * @author Benny
 */

import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JScrollPane;

public class PlaylistPanel extends DragablePanel{
    
    JLabel lbTitle;
    JLabel lbCreator;
    JTable tblPlaylistMedia;
    JScrollPane spPlaylistMedia;
    
    public PlaylistPanel(DezibelPanel parent, Playlist currentPlaylist){
        super(parent);
        createComponents(currentPlaylist);
        createLayout();
        
    }
    
    private void createComponents(Playlist currentPlaylist) {
        lbTitle = new JLabel(currentPlaylist.getTitle());
        String creatorString = currentPlaylist.getCreator().getPseudonym();
        if(creatorString == null || creatorString.trim().isEmpty()){
            creatorString = currentPlaylist.getCreator().getFirstname() + " "
                    + currentPlaylist.getCreator().getLastname();
        }
        lbCreator = new JLabel(creatorString);
        PlaylistMediaTableModel model = new PlaylistMediaTableModel();
        tblPlaylistMedia = new JTable(model);
        model.setData(currentPlaylist.getList());
        
        spPlaylistMedia = new JScrollPane(tblPlaylistMedia);
   
        
    }
    
    private void createLayout(){
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(gbl);
        
        gbc.weighty = 0.1;
        gbc.insets = new Insets(0, 30, 0, 0);
        this.add(lbTitle, gbc);
        
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(0, 0, 0, 30);
        gbc.anchor = GridBagConstraints.EAST;
        this.add(lbCreator, gbc);
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.weighty = 0.9;
        gbc.weightx = 1;
        this.add(spPlaylistMedia, gbc);
   
    }
    
}
