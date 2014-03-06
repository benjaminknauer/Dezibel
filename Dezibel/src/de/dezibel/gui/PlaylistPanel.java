
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
import java.awt.ScrollPane;

public class PlaylistPanel extends DragablePanel{
    
    JLabel lbTitle;
    JLabel lbCreator;
    JTable tblPlaylistMedia;
    ScrollPane spPlaylistMedia;
    
    public PlaylistPanel(DezibelPanel parent, Playlist currentPlaylist){
        super(parent);
        createComponents(currentPlaylist);
        
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
        
        spPlaylistMedia = new ScrollPane();
        spPlaylistMedia.add(tblPlaylistMedia);
        
    }
    
    private void createLayout(){
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(gbl);
        
        this.add(lbTitle, gbc);
        
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        this.add(lbCreator, gbc);
        
        this.add(spPlaylistMedia, gbc);
   
    }
    
}
