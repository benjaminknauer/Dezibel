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
import javax.swing.JTextArea;

/**
 *
 * @author Tristan , Aristid
 */
public class UploadPanel  extends JFrame {   
    
    private Container container;
    private JTextArea taPlaylists;
    private JTextArea taMedia;
    private JTextArea taAlbums;   

    private JLabel lbPlaylist;
    private JLabel lbMedia;
    private JLabel lbAlbums;
    
    
    
    
    
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

    public void  createUploadPanel(){
        
        lbPlaylist = new JLabel("Wiedergabe Listen");
        lbPlaylist.setHorizontalAlignment(JLabel.LEADING);
        lbPlaylist.setBounds(0,0, 200,30);
        taPlaylists = new JTextArea();
        taPlaylists.setBounds(110,0,400,100);
        container.add(taPlaylists);       
        JScrollPane spPlaylists = new JScrollPane();
        spPlaylists.setBounds(110, 10, 400, 100);
        spPlaylists.getViewport().setView(taPlaylists);
        container.add(spPlaylists);
        container.add(lbPlaylist);
        
        lbMedia = new JLabel("Media");
        lbMedia.setHorizontalAlignment(JLabel.LEADING);
        lbMedia.setBounds(0,150, 200,30);
        taMedia = new JTextArea();
        taMedia.setBounds(110,150,400,100);
        container.add(taMedia); 
        JScrollPane spMedia = new JScrollPane();
        spMedia.setBounds(110,150,400,100);
        spMedia.getViewport().setView(taMedia);
        container.add(spMedia);
        container.add(lbMedia);
        
        lbAlbums = new JLabel("Alben");
        lbAlbums.setHorizontalAlignment(JLabel.LEADING);
        lbAlbums.setBounds(0,300, 200,30);
        taAlbums = new JTextArea();
        taAlbums.setBounds(110,300,400,100);
        container.add(taAlbums);        
        JScrollPane spAlbums = new JScrollPane();
        spAlbums.setBounds(110,300,400,100);
        spAlbums.getViewport().setView(taAlbums);
        container.add(spAlbums);
        container.add(lbAlbums);
    
    }
    
}
