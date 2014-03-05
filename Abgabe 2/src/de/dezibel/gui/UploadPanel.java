/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dezibel.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.datatransfer.StringSelection;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableModel;

/**
 *
 * @author Tristan , Aristid
 */
public class UploadPanel  extends JFrame {   
    
    private Container container;
 //   private JTable tPlaylists;
    private JTextArea taMedia;
    private JTextArea taAlbums;   

    private JLabel lbPlaylist;
    private JLabel lbMedia;
    private JLabel lbAlbums;
    
    private MediaTableModel tableModelPlaylist;
    
    private JTable tPlaylists;
    private JTable tMedia;
    private JTable tAlbums;

    
    
    public UploadPanel() {
              
        frameInitialisieren();
        createUploadPanel();
        
    }
    
    public static void main(String[] args){
        
        new UploadPanel().setVisible(true);
    }
        
    
    public void frameInitialisieren(){
        
          this.setSize(new Dimension(1000,1000));
           this.container=this.getContentPane();
          this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          
        
    }
public boolean isCellEditable( int row, int col) { 

                return false; 

} 

        
    public void  createUploadPanel(){
        
        lbPlaylist = new JLabel("Wiedergabe Listen");
        lbPlaylist.setHorizontalAlignment(JLabel.LEADING);
        lbPlaylist.setBounds(0,0, 200,30);        
        tPlaylists = new JTable(10,1);
        tPlaylists.isCellEditable(10,1);
        //tPlaylists.setEditable(false);
        tPlaylists.getTableHeader().setVisible(false);        
        JScrollPane spPlaylists = new JScrollPane(tPlaylists);       
      //  tableModelPlaylist = new PlaylistTableModel();        
        tPlaylists.setBounds(110,0,400,100);
        container.add(tPlaylists);           
       // JScrollPane spPlaylists = new JScrollPane(tPlaylists);
        spPlaylists.setBounds(110, -5, 400, 100);
        spPlaylists.getViewport().setView(tPlaylists);        
        container.add(spPlaylists); 
        container.add(lbPlaylist);
        
        
        
        lbMedia = new JLabel("Media");
        lbMedia.setHorizontalAlignment(JLabel.LEADING);
        lbMedia.setBounds(0,150, 200,30);       
        tMedia = new JTable(10,1);
        tMedia.setEnabled(false);
        tMedia.getTableHeader().setVisible(false);  
        JScrollPane spMedia = new JScrollPane(tMedia);
        tMedia.setBounds(110,150,400,100);
        container.add(tMedia);
        spMedia.setBounds(110,150,400,100);
        spMedia.getViewport().setView(tMedia);      
        container.add(spMedia);
        container.add(lbMedia); 
        
        lbAlbums = new JLabel("Alben");
        lbAlbums.setHorizontalAlignment(JLabel.LEADING);
        lbAlbums.setBounds(0,300, 200,30);
        tAlbums = new JTable(10,1);
        tAlbums.getTableHeader().setVisible(false);  
        tAlbums.setBounds(110,300,400,100);
        container.add(tAlbums);        
        tAlbums.setEnabled(false);
        JScrollPane spAlbums = new JScrollPane();
        spAlbums.setBounds(110,300,400,100);
        spAlbums.getViewport().setView(tAlbums);
        container.add(spAlbums);
        container.add(lbAlbums);
    
    }
    
}
