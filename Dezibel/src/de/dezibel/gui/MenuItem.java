/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dezibel.gui;

import javax.swing.JMenuItem;

/**
 *
 * @author Benny
 */
public class MenuItem extends JMenuItem{
    Object entity;
    
    public MenuItem(String text, Object entity){
        super(text);
        this.entity = entity;
    }
    
    public Object getEntity(){
        return entity;
    }
    
}
