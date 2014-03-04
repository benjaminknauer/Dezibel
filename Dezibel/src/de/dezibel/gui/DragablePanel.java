package de.dezibel.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.javadocking.dockable.Dockable;
import com.javadocking.dockable.DraggableContent;
import com.javadocking.drag.DragListener;
import com.javadocking.event.DockingListener;

import javax.swing.JSlider;

/**
 *
 * @author Tobias,Pascal
 */
public class DragablePanel extends JPanel implements DraggableContent{

    private static final long serialVersionUID = 1L;
    protected DezibelPanel parent;
    
    public DragablePanel(DezibelPanel parent) {
        super(new FlowLayout());
        
        this.parent = parent;
        setMinimumSize(new Dimension(80, 80));
        setPreferredSize(new Dimension(150, 150));
        setBackground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.lightGray));
    }

    // Implementations of DraggableContent.
    @Override
    public void addDragListener(DragListener dragListener) {
        addMouseListener(dragListener);
        addMouseMotionListener(dragListener);
        for (Component c : getComponents()) {
            if (!(c instanceof JSlider)) {
                c.addMouseListener(dragListener);
                c.addMouseMotionListener(dragListener);
            }
        }
    }
    
public void onTopBottom(){
		System.out.println("Methode onTopBottom() von DragablePanel");
	}
	
	
	public void onLeftRight(){
		System.out.println("Methode onLeftRight() von DragablePanel");
	}
	
	public void onCenter(){
		System.out.println("Methode onCenter() von DragablePanel");
	} 
}
