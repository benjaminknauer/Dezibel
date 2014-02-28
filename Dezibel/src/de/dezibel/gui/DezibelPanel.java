package de.dezibel.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.javadocking.DockingManager;
import com.javadocking.dock.BorderDock;
import com.javadocking.dock.LineDock;
import com.javadocking.dock.Position;
import com.javadocking.dock.SingleDock;
import com.javadocking.dockable.DefaultDockable;
import com.javadocking.dockable.Dockable;
import com.javadocking.dockable.DockableState;
import com.javadocking.dockable.StateActionDockable;
import com.javadocking.dockable.action.DefaultDockableStateActionFactory;
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
		DragablePanel pnPlayer 		= new PlayerPanel();
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
		SingleDock centerDock = new SingleDock();
		leftLineDock.setOrientation(LineDock.ORIENTATION_VERTICAL);
		rightLineDock.setOrientation(LineDock.ORIENTATION_VERTICAL);
		
		leftLineDock.addDockable(dockable1,new Position(0));
		leftLineDock.addDockable(dockable2, new Position(1));
		rightLineDock.addDockable(dockable3,new Position(0));
		rightLineDock.addDockable(dockable4, new Position(1));
		
		BorderDock borderDock = new BorderDock();
		
		borderDock.addChildDock(leftLineDock, new Position(Position.LEFT));
		borderDock.addChildDock(rightLineDock, new Position(Position.RIGHT));
		borderDock.addChildDock(centerDock, new Position(Position.CENTER));

		
		dockModel.addRootDock("borderDock", borderDock, frame);
		
		// Create an externalizer.
		FloatExternalizer externalizer = new FloatExternalizer(frame);
		dockModel.addVisualizer("externalizer", externalizer, frame);
		
		// Create a minimizer.
		LineMinimizer minimizer = new LineMinimizer(borderDock);
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

