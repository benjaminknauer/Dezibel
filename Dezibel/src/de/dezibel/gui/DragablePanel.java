package de.dezibel.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.javadocking.dockable.DraggableContent;
import com.javadocking.drag.DragListener;

/**
 *
 * @author Tobias,Pascal, Richard
 */
public abstract class DragablePanel extends JPanel implements DraggableContent {

    private static final long serialVersionUID = 1L;
    protected DezibelPanel parent;

    public DragablePanel(DezibelPanel parent) {
        super(new FlowLayout());

        this.parent = parent;
        setMinimumSize(new Dimension(80, 80));
        setPreferredSize(new Dimension(150, 150));
        setBackground(DezibelColor.Background);
        setBorder(BorderFactory.createLineBorder(DezibelColor.LineBorder));
    }

    // Implementations of DraggableContent.
    @Override
    public void addDragListener(DragListener dragListener) {
        addMouseListener(dragListener);
        addMouseMotionListener(dragListener);
        for (Component c : getComponents()) {
            if (c instanceof JPanel) {
                c.addMouseListener(dragListener);
                c.addMouseMotionListener(dragListener);
            }
        }
    }
    
    /**
     * Clears all user-depending information (TextFields, Playlists)
     * This method has to be overwrite in subclasses.
     */
    public abstract void reset();
    
    /**
     * Refresh all user-information, displayed on the panel
     */
    public abstract void refresh();
    
    /**
     * This function is called when the dragable will be docked at top or bottom
     * This method has to be overwrite in subclasses.
     */
    public void onTopBottom(){
    	
    }
    
    /**
     * This function is called when the dragable will be docked at left or right
     * This method has to be overwrite in subclasses.
     */
    public  void onLeftRight(){
    	
    }
    
    /**
     * This function is called when the dragable will be docked at center
     * This method has to be overwrite in subclasses.
     */
    public void onCenter(){
    	
    }
    
    /**
     * This function is called when the dragable will be externalized
     * This method has to be overwrite in subclasses.
     */
    public void onExternalized(){
    	
    }
}
