/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dezibel.gui;

import javax.swing.JMenuItem;

/**
 * Class to represent a special Menu Item that has a concrete reference to an entity object.
 * Useful in ContextMenus e.g. "add to playlist~"
 * @author Benny
 */
public class MenuItem extends JMenuItem{
    Object entity;
    
    /**
     * Constructor for this MenuItem
     * @param text The text of this MenuItem
     * @param entity The Object this MenuItem should represent
     */
    public MenuItem(String text, Object entity){
        super(text);
        this.entity = entity;
    }
    
    public Object getEntity(){
        return entity;
    }
    
}
