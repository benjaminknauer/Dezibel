package de.dezibel.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
import com.javadocking.event.DockingEvent;
import com.javadocking.event.DockingListener;
import com.javadocking.model.FloatDockModel;
import com.javadocking.visualizer.FloatExternalizer;
import com.javadocking.visualizer.LineMinimizer;
import com.javadocking.visualizer.SingleMaximizer;

import de.dezibel.control.SaveControl;
import de.dezibel.data.Database;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * This is the main class of our UI. It uses a docking library, called "Sanaware
 * Javadocking", for dragable panels. It uses five areas where panels can be
 * docked to. Top,Right,Bottom, Left and Center. When a panel is docked, it
 * shows a minimum of information, like the playerpanel shows only the controls
 * for the music. If it is docked to the center there will be displayed a
 * playlist.
 * 
 * @author Pascal, Tobias, Richard
 * 
 */
public class DezibelPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JMenuBar menuBar;
	
	// Declares all panels the user can work with.
	private DragablePanel pnLogin;
	private DragablePanel pnRegister;
	private DragablePanel pnPlayer;
	private DragablePanel pnNews;
	private DragablePanel pnAds;
	private DragablePanel pnMyList;
	private DragablePanel pnFavorites;
	private DragablePanel pnProfil;
	private DragablePanel pnSearch;
	
	// Javadocking uses Dockable, to enable dragging and docking for childpanels
	// Any panel you want to drag and dock have to be in its own Dockable
	private Dockable daLogin;
	private Dockable daRegister;
	private Dockable daNews;
	private Dockable daAds;
	private Dockable daMyLists;
	private Dockable daFavorites;
	private Dockable daPlayer;
	private Dockable daProfil;
	private Dockable daSearch;
	
	// We uses a LineDock at the bottom,top,left and right where all panels can
	// be docked to.
	// Except some panels, like players where only can be docked at the bottom,
	// center or top.
	// Any panel can be dragged to the center where the panel will be docked and
	// shows extra information
	private BorderDock borderDock;
	private LineDock leftLineDock;
	private LineDock rightLineDock;
	private SingleDock centerDock;

	/**
	 * Constructor of the panel
	 * 
	 * @param frame
	 *            Frame that contains the panel.
	 */
	public DezibelPanel(JFrame frame) {
		super(new BorderLayout());
		this.frame = frame;
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				SaveControl saveControl = new SaveControl();
				saveControl.save();
			}
		});

		// Create the content components.
		pnLogin 	= new LoginPanel(this);
		pnRegister 	= new RegistrationPanel(this);
		pnPlayer 	= new PlayerPanel(this);
		pnNews 		= new DragablePanel(this);
		pnAds 		= new DragablePanel(this);
		pnMyList 	= new DragablePanel(this);
		pnFavorites = new DragablePanel(this);
		pnProfil 	= new ProfilPanel(this);
		pnSearch 	= new SearchPanel(this);
		
		this.createDocking();
		this.showLogin();
	}

	/**
	 * Shows the login-panel docked in the center with no other panels on the
	 * frame Any panel docked in the center will be removed.
	 */
	public void showLogin() {
		if (this.centerDock.getDockableCount() > 0) {
			this.centerDock.removeDockable(this.centerDock
					.getDockable(this.centerDock.getDockableCount() - 1));
		}
		this.centerDock.addDockable(daLogin, new Position(0));
	}

	/**
	 * Shows the registration-panel docked in the center with no other panels on
	 * the frame Any panel docked in the center will be removed.
	 */
	public void showRegistration() {
		this.centerDock.removeDockable(daLogin);
		this.centerDock.addDockable(this.daRegister, new Position(0));
	}

	/**
	 * Creates the typical workspace, with sidebards on the right and left, the
	 * player-panel docked at the bottom and a profil-panel at the center.
	 */
	public void showWorkspace() {
		this.createMenubar();
		frame.setJMenuBar(menuBar);
		if (this.centerDock.getDockableCount() > 0) {
			this.centerDock.removeDockable(this.centerDock
					.getDockable(this.centerDock.getDockableCount() - 1));
		}
		this.showSidebars();
		// this.centerDock.addDockable(this.daProfil, new Position(0));
		this.centerDock.addDockable(this.daSearch, new Position(0));
		LineDock bottomDock = new LineDock();
		bottomDock.setOrientation(LineDock.ORIENTATION_HORIZONTAL);
		bottomDock.addDockable(this.daPlayer, new Position(0));
		this.borderDock.addChildDock(bottomDock, new Position(Position.BOTTOM));
		((ProfilPanel) daProfil.getContent()).setUser(Database.getInstance()
				.getLoggedInUser());
	}

	/**
	 * This function is only called in the main-function and only once. It
	 * creates a <code>JFrame</code> with a <code>DezibelPanel</code> and some
	 * docking-features.
	 */
	public static void createAndShowGUI() {

		// Create the frame.
		JFrame frame = new JFrame("Dezibel");

		// Set the frame properties and show it.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((screenSize.width - 600) / 2,
				(screenSize.height - 800) / 2);
		frame.setSize(800, 600);

		// Create the panel and add it to the frame.
		DezibelPanel panel = new DezibelPanel(frame);
		frame.getContentPane().add(panel);

		// Show.
		frame.setVisible(true);
		frame.setMinimumSize(new Dimension(800, 600));
	}

	/**
	 * Main-Function, it creates a the typical UI
	 * 
	 * @param args
	 *            startup-arguments (will be ignored!)
	 */
	public static void main(String args[]) {
		Runnable doCreateAndShowGUI = new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		};
		SwingUtilities.invokeLater(doCreateAndShowGUI);
	}

	private void createDocking() {
		// Create the dock model for the docks.
		FloatDockModel dockModel = new FloatDockModel();
		dockModel.addOwner("dezibel", frame);

		// Give the dock model to the docking manager.
		DockingManager.setDockModel(dockModel);
		// Create the dockables around the content components.
		// MainPanles, that can only be displayed in the Center
		daLogin = new DefaultDockable("pnLogin", pnLogin, "Login", null,
				DockingMode.CENTER);
		daRegister = new DefaultDockable("pnRegister", pnRegister, "Register",
				null, DockingMode.CENTER);

		// Panels that can be docked at left/right border
		daNews = new DefaultDockable("pnNews", pnNews, "News", null,
				DockingMode.CENTER + DockingMode.LEFT + DockingMode.RIGHT
						+ DockingMode.VERTICAL_LINE + DockingMode.SINGLE);
		daAds = new DefaultDockable("pnAds", pnAds, "Ads", null,
				DockingMode.CENTER + DockingMode.LEFT + DockingMode.RIGHT
						+ DockingMode.VERTICAL_LINE + DockingMode.SINGLE);
		daMyLists = new DefaultDockable("pnMyList", pnMyList, "MyLists", null,
				DockingMode.CENTER + DockingMode.LEFT + DockingMode.RIGHT
						+ DockingMode.VERTICAL_LINE + DockingMode.SINGLE);
		daFavorites = new DefaultDockable("pnFavorites", pnFavorites,
				"Favorites", null, DockingMode.CENTER + DockingMode.LEFT
						+ DockingMode.RIGHT + DockingMode.VERTICAL_LINE
						+ DockingMode.SINGLE);

		// Panels that can be docked only at top/bottom and center
		daPlayer = new DefaultDockable("pnPlayer", pnPlayer, "Player", null,
				DockingMode.CENTER + DockingMode.SINGLE + DockingMode.BOTTOM
						+ DockingMode.TOP + DockingMode.HORIZONTAL_LINE);
		daProfil = new DefaultDockable("pnProfil", pnProfil, "Profil", null,
				DockingMode.CENTER + DockingMode.SINGLE);

		daSearch = new DefaultDockable("pnSearch", pnSearch, "Search", null,
				DockingMode.CENTER + DockingMode.SINGLE + DockingMode.BOTTOM
						+ DockingMode.TOP);

		// Add actions to the dockables.
		daLogin = addActions(daLogin);
		daRegister = addActions(daRegister);
		daPlayer = addActionsWithCloseExt(daPlayer);
		daNews = addActionsWithClose(daNews);
		daAds = addActionsWithClose(daAds);
		daMyLists = addActionsWithClose(daMyLists);
		daFavorites = addActionsWithClose(daFavorites);

		// Create the child tab dock.
		leftLineDock = new LineDock();
		rightLineDock = new LineDock();
		centerDock = new SingleDock();
		leftLineDock.setOrientation(LineDock.ORIENTATION_VERTICAL);
		rightLineDock.setOrientation(LineDock.ORIENTATION_VERTICAL);

		this.addSideCenterListener();
		this.addTopBottomCenterListener();

		borderDock = new BorderDock();
		borderDock.setDock(leftLineDock, Position.LEFT);
		borderDock.setDock(rightLineDock, Position.RIGHT);
		borderDock.setDock(centerDock, Position.CENTER);

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
	 * Adds the action to a dockable given as parameter. The dockable has the
	 * default behaviour
	 * 
	 * @param dockable
	 *            the dockable where the actions should be added
	 * @return a new dockable with the actions, based on the object given as
	 *         parameter
	 */
	private Dockable addActions(Dockable dockable) {
		// int[] states = { DockableState.NORMAL, DockableState.MINIMIZED };
		int[] states = { DockableState.NORMAL };
		Dockable wrapper = new StateActionDockable(dockable,
				new DefaultDockableStateActionFactory(), states);
		return wrapper;
	}

	private Dockable addActionsWithClose(Dockable dockable) {
		// int[] states = { DockableState.NORMAL, DockableState.MINIMIZED };
		int[] states = { DockableState.NORMAL, DockableState.CLOSED };
		Dockable wrapper = new StateActionDockable(dockable,
				new DefaultDockableStateActionFactory(), states);
		return wrapper;
	}

	private Dockable addActionsWithCloseExt(Dockable dockable) {
		// int[] states = { DockableState.NORMAL, DockableState.MINIMIZED };
		int[] states = { DockableState.NORMAL, DockableState.CLOSED,
				DockableState.MINIMIZED, DockableState.EXTERNALIZED };
		Dockable wrapper = new StateActionDockable(dockable,
				new DefaultDockableStateActionFactory(), states);
		return wrapper;
	}

	/**
	 * Adds a listener to the side-panels, which can be docked at center,left
	 * and right. If a dockable is docked to the center, <code>onCenter</code>
	 * is called from <code>DragablePanel</code>, else <code>onLeftRight</code>
	 */
	private void addSideCenterListener() {
		daNews.addDockingListener(new DockingListener() {
			@Override
			public void dockingChanged(DockingEvent e) {
				DragablePanel pn = (DragablePanel) daNews.getContent();
				if (e.getDestinationDock() == centerDock) {
					pn.onCenter();
				} else {
					pn.onLeftRight();
				}
			}

			@Override
			public void dockingWillChange(DockingEvent e) {
				// TODO Auto-generated method stub
			}
		});

		daAds.addDockingListener(new DockingListener() {
			@Override
			public void dockingChanged(DockingEvent e) {
				DragablePanel pn = (DragablePanel) daAds.getContent();
				if (e.getDestinationDock() == centerDock) {
					pn.onCenter();
				} else {
					pn.onLeftRight();
				}
			}

			@Override
			public void dockingWillChange(DockingEvent e) {
				// TODO Auto-generated method stub
			}
		});
		daMyLists.addDockingListener(new DockingListener() {
			@Override
			public void dockingChanged(DockingEvent e) {
				DragablePanel pn = (DragablePanel) daMyLists.getContent();
				if (e.getDestinationDock() == centerDock) {
					pn.onCenter();
				} else {
					pn.onLeftRight();
				}
			}

			@Override
			public void dockingWillChange(DockingEvent e) {
				// TODO Auto-generated method stub
			}
		});

		daFavorites.addDockingListener(new DockingListener() {
			@Override
			public void dockingChanged(DockingEvent e) {
				DragablePanel pn = (DragablePanel) daFavorites.getContent();
				if (e.getDestinationDock() == centerDock) {
					pn.onCenter();
				} else {
					pn.onLeftRight();
				}
			}

			@Override
			public void dockingWillChange(DockingEvent e) {
				// TODO Auto-generated method stub
			}
		});
	}

	/**
	 * Same as <code>addSideCenterListener</code>, but for Top,Bottom and Center
	 */
	private void addTopBottomCenterListener() {
		daPlayer.addDockingListener(new DockingListener() {
			@Override
			public void dockingChanged(DockingEvent e) {
				DragablePanel pn = (DragablePanel) daPlayer.getContent();
				if (e.getDestinationDock() == centerDock) {
					pn.onCenter();
				} else if (e.getDestinationDock() == null) {
					pn.onExternalized();
				} else {
					pn.onTopBottom();
				}
			}

			@Override
			public void dockingWillChange(DockingEvent e) {
				if (e.getDestinationDock() == centerDock) {
					if (centerDock.getDockableCount() > 0) {
						centerDock.removeDockable(centerDock
								.getDockable(centerDock.getDockableCount() - 1));
					}
				}
			}
		});
	}

	/**
	 * Shows the typical sidebars with MyList, Favorites, News and Ads. Is only
	 * called once, after the login-process when the typical workspace will be
	 * created
	 */
	private void showSidebars() {
//		leftLineDock.addDockable(daMyLists, new Position(0));
//		leftLineDock.addDockable(daFavorites, new Position(1));
//		rightLineDock.addDockable(daNews, new Position(0));
//		rightLineDock.addDockable(daAds, new Position(1));
		showSidebar(daMyLists);
		showSidebar(daNews);
		showSidebar(daFavorites);
		showSidebar(daAds);
	}
	/**
	 * Docks the specific dockable to the left or right sidebar, depending on which has
	 * a lower dockable count. bar shouldbe one of daMyLists, daNews, daFavorites, daAds
	 * @param bar the new Dockable which will be docked at the left or right
	 */
	private void showSidebar(Dockable bar){
		if(this.leftLineDock.getDockableCount() < rightLineDock.getDockableCount())
			leftLineDock.addDockable(bar, new Position(leftLineDock.getDockableCount()-1));
		else
			rightLineDock.addDockable(bar, new Position(rightLineDock.getDockableCount()-1));
	}
	
	private void createMenubar() {
		if (this.menuBar == null) {
			JMenu menuShow;
			JMenuItem itemLogout;
			JCheckBoxMenuItem cbMenuItem;
			menuBar = new JMenuBar();
			JMenu menuLogout = new JMenu("Ausloggen");

			menuBar.add(menuLogout);
			menuShow = new JMenu("Anzeige");
			menuBar.add(menuShow);
			itemLogout = new JMenuItem("Ausloggen");
			menuLogout.add(itemLogout);
			itemLogout.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					onLogout();
				}
			});

			cbMenuItem = new JCheckBoxMenuItem("Neuigkeiten");
			cbMenuItem.setSelected(true);
			cbMenuItem.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					onMenuCheckedSideBar((JCheckBoxMenuItem) arg0.getSource(), daNews);
					
				}
			});
			menuShow.add(cbMenuItem);
			cbMenuItem = new JCheckBoxMenuItem("Meine Liste");
			cbMenuItem.setSelected(true);
			cbMenuItem.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					onMenuCheckedSideBar((JCheckBoxMenuItem) arg0.getSource(), daMyLists);			}
			});
			menuShow.add(cbMenuItem);
			cbMenuItem = new JCheckBoxMenuItem("Favoriten");
			cbMenuItem.setSelected(true);
			cbMenuItem.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					onMenuCheckedSideBar((JCheckBoxMenuItem) arg0.getSource(), daFavorites);
					
				}
			});
			menuShow.add(cbMenuItem);
			cbMenuItem = new JCheckBoxMenuItem("Werbung");
			cbMenuItem.setSelected(true);
			cbMenuItem.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					onMenuCheckedSideBar((JCheckBoxMenuItem) arg0.getSource(), daAds);
				}
			});
			menuShow.add(cbMenuItem);

			JMenu menuUpload = new JMenu("Upload");
			JMenuItem itemUpload = new JMenuItem("Upload");
			menuUpload.add(itemUpload);

			itemUpload.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					onUpload();
				}
			});

			JMenuItem itemProfile = new JMenuItem("Profil");
			itemProfile.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (centerDock.getDockableCount() > 0) {
						centerDock.removeDockable(centerDock
								.getDockable(centerDock.getDockableCount() - 1));
					}
					centerDock.addDockable(daProfil, new Position(0));
				}
			});

			JMenuItem itemSearch = new JMenuItem("Suchen");
			itemSearch.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (centerDock.getDockableCount() > 0) {
						centerDock.removeDockable(centerDock
								.getDockable(centerDock.getDockableCount() - 1));
					}
					centerDock.addDockable(daSearch, new Position(0));
				}
			});

			JMenu menuGoTo = new JMenu("Gehe zu..");
			menuGoTo.add(itemSearch);
			menuGoTo.add(itemProfile);

			menuBar.add(menuShow);
			menuBar.add(menuUpload);
			menuBar.add(menuGoTo);
		}
	}

	private void removeMenubar() {
		if (this.menuBar != null) {
			menuBar.removeAll();
			frame.remove(menuBar);
			menuBar = null;
		}
	}
	
	private void onLogout(){
		Database.getInstance().setLoggedInUser(null);
		this.removeMenubar();
		if(daAds.getDock()!=null)
			daAds.getDock().removeDockable(daAds);
		if(daFavorites.getDock()!=null)
			daFavorites.getDock().removeDockable(daFavorites);
		if(daNews.getDock()!=null)
			daNews.getDock().removeDockable(daNews);
		if(daMyLists.getDock()!=null)
			daMyLists.getDock().removeDockable(daMyLists);
		
		if(daProfil.getDock()!=null)
			daProfil.getDock().removeDockable(daProfil);
		
		if(daSearch.getDock()!=null)
			daSearch.getDock().removeDockable(daSearch);
		
		if(daPlayer.getDock()!=null)
			daPlayer.getDock().removeDockable(daPlayer);
		
		this.showLogin();
	}
	
	private void onMenuCheckedSideBar(JCheckBoxMenuItem src, Dockable da){
		if(src.isSelected())
			showSidebar(da);
		else{
			if(da.getDock() != null)
					da.getDock().removeDockable(da);
		}
	}
	
	private void onUpload() {
		UploadDialog ud = new UploadDialog(frame, null);
		ud.setVisible(true);
	}
}
