package de.dezibel.gui;

import javax.swing.Icon;

import com.javadocking.dockable.DefaultDockable;

/**
 * A ChangeableDockable has different appearances when it is docked at
 * different docking positions in a BorderDock.
 * A <code>DragablePanel</code> has to be added as content and
 * any docking-action will be send to the panel.
 * 
 * @author Pascal
 *
 */
public class ChangeableDockable extends DefaultDockable{

	private DragablePanel content;
	public ChangeableDockable(String id, DragablePanel content, String title,
			Icon icon, int dockingModes) {
		super(id, content, title, icon, dockingModes);
		this.content = content;
	}
	
	public void onBottom(){
		content.onBottom();
	}
	
	public void onTop(){
		content.onTop();
	}
	
	public void onLeft(){
		content.onLeft();
	}
	
	public void onRight(){
		content.onRight();
	}
	
	public void onCenter(){
		content.onCenter();
	}
}
