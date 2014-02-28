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
import com.javadocking.dockable.DockingMode;
import com.javadocking.dockable.StateActionDockable;
import com.javadocking.dockable.action.DefaultDockableStateActionFactory;
import com.javadocking.model.FloatDockModel;
import com.javadocking.visualizer.FloatExternalizer;
import com.javadocking.visualizer.LineMinimizer;
import com.javadocking.visualizer.SingleMaximizer;

public class DezibelPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private DragablePanel pnLogin;
	private DragablePanel pnRegister;
	//private DragablePanel pnPlayer;
	private DragablePanel pnPlayer;
	private DragablePanel pnNews;
	private DragablePanel pnAds;
	private DragablePanel pnMyList;
	private DragablePanel pnFavorites;
	private Dockable daLogin;
	private Dockable daRegister;
	private Dockable daNews;
	private Dockable daAds;
	private Dockable daMyLists;
	private Dockable daFavorites;
	private Dockable daPlayer;
	
	private LineDock leftLineDock;
	private LineDock rightLineDock;
	private SingleDock centerDock;
	
	public DezibelPanel(JFrame frame)
	{
		super(new BorderLayout());
		
		// Create the dock model for the docks.
		FloatDockModel dockModel = new FloatDockModel();
		dockModel.addOwner("dezibel", frame);

		// Give the dock model to the docking manager.
		DockingManager.setDockModel(dockModel);
		
		// Create the content components.
		pnLogin 		= new LoginPanel(this);
		pnRegister 		= new DragablePanel(this);
		//pnPlayer 		= new PlayerPanel();
		pnPlayer 		= new DragablePanel(this);
		pnNews 			= new DragablePanel(this);
		pnAds 			= new DragablePanel(this);
		pnMyList 		= new DragablePanel(this);
		pnFavorites 	= new DragablePanel(this);
		
		// Create the dockables around the content components.
		// MainPanles, that can only be displayed in the Center
		daLogin 		= new DefaultDockable("pnLogin", 	pnLogin, 	"Login",	null,DockingMode.CENTER);
		daRegister 	= new DefaultDockable("pnRegister", pnRegister, "Register",	null,DockingMode.CENTER);
		
		// Panels that can be docked at left/right border
		daNews 		= new DefaultDockable("pnNews", 	pnNews, 	"News",		null,DockingMode.CENTER + DockingMode.LEFT + DockingMode.RIGHT + DockingMode.VERTICAL_LINE);
		daAds 			= new DefaultDockable("pnAds", 		pnAds, 		"Ads",		null,DockingMode.CENTER + DockingMode.LEFT + DockingMode.RIGHT + DockingMode.VERTICAL_LINE);
		daMyLists 		= new DefaultDockable("pnMyList", 	pnMyList, 	"MyLists",	null,DockingMode.CENTER + DockingMode.LEFT + DockingMode.RIGHT + DockingMode.VERTICAL_LINE);
		daFavorites 	= new DefaultDockable("pnFavorites",pnFavorites,"Favorites",null,DockingMode.CENTER + DockingMode.LEFT + DockingMode.RIGHT + DockingMode.VERTICAL_LINE);
		
		// Panels that can be docked only at top/bottom and center
		daPlayer 		= new DefaultDockable("pnPlayer", 	pnPlayer, 	"Player",	null,DockingMode.CENTER + DockingMode.SINGLE + DockingMode.FLOAT + DockingMode.BOTTOM + DockingMode.TOP);
				
		// Add actions to the dockables.
		daLogin 	= addActions(daLogin);
		daRegister 	= addActions(daRegister);
		daPlayer 	= addActions(daPlayer);
		daNews 		= addActions(daNews);
		daAds 		= addActions(daAds);
		daMyLists 	= addActions(daMyLists);
		daFavorites = addActions(daFavorites);
		
		// Create the child tab dock.
		leftLineDock = new LineDock();
		rightLineDock = new LineDock();
		centerDock = new SingleDock();
		leftLineDock.setOrientation(LineDock.ORIENTATION_VERTICAL);
		rightLineDock.setOrientation(LineDock.ORIENTATION_VERTICAL);
		
		//leftLineDock.addDockable(daLogin,new Position(0));
		//leftLineDock.addDockable(daRegister, new Position(1));
//		centerDock.addDockable(daLogin,new Position(0));
//		leftLineDock.addDockable(daMyLists,new Position(0));
//		leftLineDock.addDockable(daFavorites, new Position(1));
//		
//		rightLineDock.addDockable(daNews, new Position(0));
//		rightLineDock.addDockable(daAds,new Position(1));
		
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
		this.showLogin();
		this.showRegistration();
		this.showLogin();
	}
	
	private Dockable addActions(Dockable dockable)
	{
		//int[] states = {DockableState.NORMAL, DockableState.MINIMIZED, DockableState.MAXIMIZED, DockableState.EXTERNALIZED};
		int[] states = {DockableState.NORMAL, DockableState.MINIMIZED};
		Dockable wrapper = new StateActionDockable(dockable, new DefaultDockableStateActionFactory(), states);
		return wrapper;
	}
	
	private void showSidebars(){
		leftLineDock.addDockable(daMyLists,new Position(0));
		leftLineDock.addDockable(daFavorites, new Position(1));
		
		rightLineDock.addDockable(daNews, new Position(0));
		rightLineDock.addDockable(daAds,new Position(1));
	}
	
	private void showLogin(){
		if(this.centerDock.getDockableCount() > 0)
			this.centerDock.removeDockable(this.centerDock.getDockable(this.centerDock.getDockableCount() -1));
		
		this.centerDock.addDockable(daLogin,new Position(0));
	}
	
	private void showRegistration(){
		this.centerDock.removeDockable(daLogin);
		this.centerDock.addDockable(this.daRegister,new Position(0));
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

