package com.javadocking.action;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.javadocking.DockingManager;
import com.javadocking.component.DefaultSwComponentFactory;
import com.javadocking.dock.Position;
import com.javadocking.dock.SplitDock;
import com.javadocking.dock.TabDock;
import com.javadocking.dockable.ActionDockable;
import com.javadocking.dockable.DefaultDockable;
import com.javadocking.dockable.Dockable;
import com.javadocking.dockable.DockableState;
import com.javadocking.dockable.DraggableContent;
import com.javadocking.dockable.StateActionDockable;
import com.javadocking.dockable.action.DefaultDockableStateActionFactory;
import com.javadocking.dockable.action.DefaultPopupMenuFactory;
import com.javadocking.drag.DragListener;
import com.javadocking.model.FloatDockModel;
import com.javadocking.visualizer.FloatExternalizer;
import com.javadocking.visualizer.LineMinimizer;
import com.javadocking.visualizer.SingleMaximizer;

public class DezibelPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Constructor.

	public DezibelPanel(JFrame frame)
	{
		super(new BorderLayout());

		// We only want to see the dockable actions, the close all, and close others actions in the popup menu.
		DefaultPopupMenuFactory popupMenuFactory = new DefaultPopupMenuFactory();
		popupMenuFactory.setPopupActions(DefaultPopupMenuFactory.DOCKABLE_ACTIONS | DefaultPopupMenuFactory.CLOSE_ALL_ACTION | DefaultPopupMenuFactory.CLOSE_OTHERS_ACTION);
		DefaultSwComponentFactory componentFactory = new DefaultSwComponentFactory();
		componentFactory.setPopupMenuFactory(popupMenuFactory);
		DockingManager.setComponentFactory(componentFactory);
		
		// Create the dock model for the docks.
		FloatDockModel dockModel = new FloatDockModel();
		dockModel.addOwner("frame0", frame);

		// Give the dock model to the docking manager.
		DockingManager.setDockModel(dockModel);

		// Create the content components.
		TextPanel textPanel1 = new TextPanel("I am window 1.");
		TextPanel textPanel2 = new TextPanel("I am window 2.");
		TextPanel textPanel3 = new TextPanel("I am window 3.");
		TextPanel textPanel4 = new TextPanel("I am window 4.");
		TextPanel textPanel5 = new TextPanel("I am window 5.");
		TextPanel textPanel6 = new TextPanel("I am window 6.");
		TextPanel textPanel7 = new TextPanel("I am window 7.");
		TextPanel textPanel8 = new TextPanel("I am window 8.");
		TextPanel textPanel9 = new TextPanel("I am window 9.");
		
		// Create the dockables around the content components.
		Dockable dockable1 = new DefaultDockable("Window1", textPanel1, "Window 1");
		Dockable dockable2 = new DefaultDockable("Window2", textPanel2, "Window 2");
		Dockable dockable3 = new DefaultDockable("Window3", textPanel3, "Window 3");
		Dockable dockable4 = new DefaultDockable("Window4", textPanel4, "Window 4");
		Dockable dockable5 = new DefaultDockable("Window5", textPanel5, "Window 5");
		Dockable dockable6 = new DefaultDockable("Window6", textPanel6, "Window 6");
		Dockable dockable7 = new DefaultDockable("Window7", textPanel7, "Window 7");
		Dockable dockable8 = new DefaultDockable("Window8", textPanel8, "Window 8");
		Dockable dockable9 = new DefaultDockable("Window9", textPanel9, "Window 9");

		// Add actions to the dockables.
		dockable1 = addActions(dockable1);
		dockable2 = addActions(dockable2);
		dockable3 = addActions(dockable3);
		dockable4 = addActions(dockable4);
		dockable5 = addActions(dockable5);
		dockable6 = addActions(dockable6);
		dockable7 = addActions(dockable7);
		dockable8 = addActions(dockable8);
		dockable9 = addActions(dockable9);

		// Create the child tab dock.
		TabDock leftTabDock = new TabDock();
		TabDock rightTabDock = new TabDock();
		
		// Add the dockables to the tab dock.
		leftTabDock.addDockable(dockable1, new Position(0));
		leftTabDock.addDockable(dockable2, new Position(1));
		rightTabDock.addDockable(dockable3, new Position(0));
		rightTabDock.addDockable(dockable4, new Position(1));

		// Create the split dock.
		SplitDock splitDock = new SplitDock();
		
		// Add the child docks to the split dock at the left and right.
		splitDock.addChildDock(leftTabDock, new Position(Position.LEFT));
		splitDock.addChildDock(rightTabDock, new Position(Position.RIGHT));
		splitDock.setDividerLocation(395);

		// Add the root dock to the dock model.
		dockModel.addRootDock("splitDock", splitDock, frame);
		
		// Create an externalizer.
		FloatExternalizer externalizer = new FloatExternalizer(frame);
		dockModel.addVisualizer("externalizer", externalizer, frame);

		// Create a minimizer.
		LineMinimizer minimizer = new LineMinimizer(splitDock);
		dockModel.addVisualizer("minimizer", minimizer, frame);
		
		// Create a maximizer.
		SingleMaximizer maximizer = new SingleMaximizer(minimizer);
		dockModel.addVisualizer("maximizer", maximizer, frame);
		
		// Add the maximizer to the panel.
		this.add(maximizer, BorderLayout.CENTER);
		
		// Minimize dockables.
		minimizer.visualizeDockable(dockable5);
		minimizer.visualizeDockable(dockable6);
		minimizer.visualizeDockable(dockable7);
		minimizer.visualizeDockable(dockable8);
		
		// Externalize dockable.
		//externalizer.visualizeDockable(dockable9);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point location = new Point((screenSize.width - 200) / 2, (screenSize.height - 200) / 2);
		externalizer.externalizeDockable(dockable9, location);
		
	}
	
	/**
	 * Decorates the given dockable with state actions and other actions.
	 * 
	 * @param dockable	The dockable to decorate.
	 * @return			The wrapper around the given dockable, with actions.
	 */
	private Dockable addActions(Dockable dockable)
	{
		Dockable wrapper = new StateActionDockable(dockable, new DefaultDockableStateActionFactory(), new int[0]);
		int[] states = {DockableState.NORMAL, DockableState.MINIMIZED, DockableState.MAXIMIZED, DockableState.EXTERNALIZED};
		wrapper = new StateActionDockable(wrapper, new DefaultDockableStateActionFactory(), states);
		return wrapper;
	}
	
	private class TextPanel extends JPanel implements DraggableContent
	{
		private static final long serialVersionUID = 1L;
		private JLabel label; 
		
		public TextPanel(String text)
		{
			super(new FlowLayout());
			
			// The panel.
			setMinimumSize(new Dimension(80,80));
			setPreferredSize(new Dimension(150,150));
			setBackground(Color.white);
			setBorder(BorderFactory.createLineBorder(Color.lightGray));
			
			// The label.
			label = new JLabel(text);
			label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			add(label);
		}
		
		// Implementations of DraggableContent.
		public void addDragListener(DragListener dragListener)
		{
			addMouseListener(dragListener);
			addMouseMotionListener(dragListener);
			label.addMouseListener(dragListener);
			label.addMouseMotionListener(dragListener);
		}
	}
	
	public static void createAndShowGUI()
	{
		
		// Create the frame.
		JFrame frame = new JFrame("Split dock");
		
		// Set the frame properties and show it.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((screenSize.width - 600) / 2, (screenSize.height - 800) / 2);
		frame.setSize(800,600);
		
		// Create the panel and add it to the frame.
		DezibelPanel panel = new DezibelPanel(frame);
		frame.getContentPane().add(panel);

		// Show.
		frame.setVisible(true);
		
	}

	public static void main(String args[]) 
	{
        Runnable doCreateAndShowGUI = new Runnable() 
        {
            public void run() 
            {
                createAndShowGUI();
            }
        };
        SwingUtilities.invokeLater(doCreateAndShowGUI);
    }
	
}

