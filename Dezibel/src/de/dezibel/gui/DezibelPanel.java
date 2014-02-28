package de.dezibel.gui;

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
import com.javadocking.dock.LineDock;
import com.javadocking.dock.Position;
import com.javadocking.dock.SplitDock;
import com.javadocking.dock.TabDock;
import com.javadocking.dockable.ActionDockable;
import com.javadocking.dockable.DefaultDockable;
import com.javadocking.dockable.Dockable;
import com.javadocking.dockable.DockableState;
import com.javadocking.dockable.DockingMode;
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
	private static final long serialVersionUID = 1L;

	public DezibelPanel(JFrame frame)
	{
		super(new BorderLayout());
		
		// Create the dock model for the docks.
		FloatDockModel dockModel = new FloatDockModel();
		dockModel.addOwner("dezibel", frame);

		// Give the dock model to the docking manager.
		DockingManager.setDockModel(dockModel);
		
		// Create the content components.
		DragablePanel pnLogin 		= new DragablePanel();
		DragablePanel pnRegister 	= new DragablePanel();
		DragablePanel pnPlayer 		= new DragablePanel();
		DragablePanel pnNews 		= new DragablePanel();
		DragablePanel pnAds 		= new DragablePanel();
		DragablePanel pnMyList 		= new DragablePanel();
		DragablePanel pnFavorites 	= new DragablePanel();
		
		// Create the dockables around the content components.
		Dockable dockable1 = new DefaultDockable("pnLogin", 	pnLogin, 	"Login");
		Dockable dockable2 = new DefaultDockable("pnRegister", 	pnRegister, "Register");
		Dockable dockable3 = new DefaultDockable("pnPlayer", 	pnPlayer, 	"Player");
		Dockable dockable4 = new DefaultDockable("pnNews", 		pnNews, 	"News");
		Dockable dockable5 = new DefaultDockable("pnAds", 		pnAds, 		"Ads");
		Dockable dockable6 = new DefaultDockable("pnMyList", 	pnMyList, 	"MyList");
		Dockable dockable7 = new DefaultDockable("pnFavorites", pnFavorites,"Favorites");

		// Add actions to the dockables.
		dockable1 = addActions(dockable1);
		dockable2 = addActions(dockable2);
		dockable3 = addActions(dockable3);
		dockable4 = addActions(dockable4);
		dockable5 = addActions(dockable5);
		dockable6 = addActions(dockable6);
		dockable7 = addActions(dockable7);
		
		// Create the child tab dock.
		LineDock leftLineDock = new LineDock();
		LineDock rightLineDock = new LineDock();
		leftLineDock.setOrientation(LineDock.ORIENTATION_VERTICAL);
		rightLineDock.setOrientation(LineDock.ORIENTATION_VERTICAL);
//		TabDock leftTabDock = new TabDock();
//		TabDock rightTabDock = new TabDock();
		
		// Add the dockables to the tab dock.
//		leftTabDock.addDockable(dockable1, new Position(0));
//		leftTabDock.addDockable(dockable2, new Position(1));
//		rightTabDock.addDockable(dockable3, new Position(0));
//		rightTabDock.addDockable(dockable4, new Position(1));
		leftLineDock.addDockable(dockable1,new Position(0));
		leftLineDock.addDockable(dockable2, new Position(1));
		rightLineDock.addDockable(dockable3,new Position(0));
		rightLineDock.addDockable(dockable4, new Position(1));

		// Create the split dock.
		//SplitDock splitDock = new SplitDock();
		com.javadocking.dock.BorderDock splitDock = new com.javadocking.dock.BorderDock();
		
		// Add the child docks to the split dock at the left and right.
		//splitDock.addChildDock(leftTabDock, new Position(Position.LEFT));
		//splitDock.addChildDock(rightTabDock, new Position(Position.RIGHT));
		splitDock.addChildDock(leftLineDock, new Position(Position.LEFT));
		splitDock.addChildDock(rightLineDock, new Position(Position.RIGHT));
		//splitDock.setDividerLocation(395);

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
	}
	
	/**
	 * Decorates the given dockable with no state actions to prevent, minimizing,maximizing and closing.
	 * 
	 * @param dockable	The dockable to decorate.
	 * @return			The wrapper around the given dockable, with actions.
	 */
	private Dockable addActions(Dockable dockable)
	{
		//int[] states = {DockableState.NORMAL, DockableState.MINIMIZED, DockableState.MAXIMIZED, DockableState.EXTERNALIZED};
		int[] states = {DockableState.NORMAL};
		Dockable wrapper = new StateActionDockable(dockable, new DefaultDockableStateActionFactory(), states);
		return wrapper;
	}
	
	public static void createAndShowGUI()
	{
		
		// Create the frame.
		JFrame frame = new JFrame("Dezibel");
		
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

