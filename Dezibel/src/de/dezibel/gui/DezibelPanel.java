package de.dezibel.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.javadocking.DockingExecutor;
import com.javadocking.DockingManager;
import com.javadocking.dock.BorderDock;
import com.javadocking.dock.LeafDock;
import com.javadocking.dock.LineDock;
import com.javadocking.dock.Position;
import com.javadocking.dock.SingleDock;
import com.javadocking.dock.factory.LeafDockFactory;
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

import de.dezibel.UpdateEntity;
import de.dezibel.control.LabelControl;
import de.dezibel.control.ProfileControl;
import de.dezibel.control.SaveControl;
import de.dezibel.data.Album;
import de.dezibel.data.Database;
import de.dezibel.data.Label;
import de.dezibel.data.Medium;
import de.dezibel.data.News;
import de.dezibel.data.Playlist;
import de.dezibel.data.User;

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
	private DragablePanel pnSideNews;
	private DragablePanel pnAds;
	private DragablePanel pnMyList;
	private DragablePanel pnFavorites;
	private DragablePanel pnProfil;
	private DragablePanel pnLabelProfil;
	private DragablePanel pnSearch;
	private DragablePanel pnAlbum;

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
	private Dockable daLabelProfil;
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
	private DockingExecutor executor;
	private boolean addLeft;
	// Needed to be private, cause it is usded in every call of refresh!
	private JMenuItem itemCreateNews;

	/**
	 * Constructor of the panel
	 * 
	 * @param frame
	 *            Frame that contains the panel.
	 */
	public DezibelPanel(JFrame frame) {
		super(new BorderLayout());
		this.frame = frame;
		frame.setBackground(DezibelColor.Background);
		this.setBackground(DezibelColor.Background);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				SaveControl saveControl = new SaveControl();
				saveControl.save();
			}
		});
		
		// All Side-Panels will be added in the following order: left, right left right ....
		this.addLeft = true;
		// Create the content components from classes
		pnLogin = new LoginPanel(this);
		pnRegister = new RegistrationPanel(this);
		pnPlayer = new PlayerPanel(this);
		pnSideNews = new NewsSidePanel(this);
		pnAds = new AdsPanel(this);
		pnMyList = new MyListsPanel(this);
		pnFavorites = new FavoritesPanel(this);
		pnProfil = new ProfilPanel(this);
		pnLabelProfil = new LabelProfilPanel(this);
		pnSearch = new SearchPanel(this);
		
		// Setting the background of each panel to DezibelColor.Background, maybe this will be used in future versions
		pnLogin.setBackground(DezibelColor.Background);
		pnRegister.setBackground(DezibelColor.Background);
		pnPlayer.setBackground(DezibelColor.Background);
		pnSideNews.setBackground(DezibelColor.Background);
		pnAds.setBackground(DezibelColor.Background);
		pnMyList.setBackground(DezibelColor.Background);
		pnFavorites.setBackground(DezibelColor.Background);
		pnProfil.setBackground(DezibelColor.Background);
		pnSearch.setBackground(DezibelColor.Background);

		// this.setBackground(new Color(239, 239, 239));
		this.createDocking();
		this.showLogin();
	}

	/**
	 * Shows the login-panel docked in the center with no other panels on the
	 * frame Any panel docked in the center will be removed.
	 */
	public void showLogin() {
		this.showAtCenter(daLogin);
	}

	/**
	 * Shows the registration-panel docked in the center with no other panels on
	 * the frame Any panel docked in the center will be removed.
	 */
	public void showRegistration() {
		this.showAtCenter(daRegister);
	}

	/**
	 * Creates the typical workspace, with sidebards on the right and left, the
	 * player-panel docked at the bottom, or at its last position and a profil-panel at the center.
	 * And a beautiful menubar, a.k.a toolbar.
	 */
	public void showWorkspace() {
		// Lets get the new content of the currently logged in user for the panels.
		((MyListsPanel) pnMyList).refresh();
		((AdsPanel) pnAds).refresh();
		((NewsSidePanel) pnSideNews).refresh();
		((FavoritesPanel) pnFavorites).refresh();
		this.createMenubar();
		frame.setJMenuBar(menuBar);
		this.showSidebars();
		this.showAtCenter(daSearch);
		
		// Change docking of the playerpanel
		this.executor.changeDocking(daPlayer, borderDock);
		((ProfilPanel) daProfil.getContent()).setUser(Database.getInstance()
				.getLoggedInUser());
	}
	
	/**
	 * Shows the profil-panel, with the content of <code>user</code>
	 * in the center. Every panel at the center will be removed from the centerdock.
	 * @param user
	 */
	public void showProfile(User user) {
		ProfilPanel pn = (ProfilPanel) pnProfil;
		pn.setUser(user);
		pn.setBackground(DezibelColor.Background);
		if (new ProfileControl().isLocked(((ProfilPanel) daProfil.getContent())
				.getUser())
				&& !(new ProfileControl().getLoggedInUser().isAdmin())) {

			JOptionPane
					.showMessageDialog(
							this,
							"Der Nutzer, dessen Profil Sie"
									+ " aufzurufen versuchen ist temporär gesperrt. Das gewünschte"
									+ "Profil kann daher leider zurzeit nicht aufgerufen werden!");

		} else {
			this.showAtCenter(daProfil);
		}
	}

	public void showProfile(Label label) {
		LabelProfilPanel lpn = (LabelProfilPanel) pnLabelProfil;
		lpn.setUser(label);
		lpn.setBackground(DezibelColor.Background);
		if (new LabelControl().isLocked(((LabelProfilPanel) daLabelProfil
				.getContent()).getLabel())
				&& !(new LabelControl().getLoggedInUser().isAdmin())) {

			JOptionPane
					.showMessageDialog(
							this,
							"Das Label, dessen Profil Sie"
									+ " aufzurufen versuchen ist temporär gesperrt. Das gewünschte"
									+ "Profil kann daher leider zurzeit nicht aufgerufen werden!");

		} else {
			this.showAtCenter(daLabelProfil);
		}
	}
	
	/**
	 * Shows the playlist given by <code>list</code> in a PlaylistPanel at the center.
	 * @param list The playlist the user wants to receive more information.
	 */
	public void showPlaylist(Playlist list) {
		PlaylistPanel pnPlaylist = new PlaylistPanel(this, list);
		pnPlaylist.setBackground(DezibelColor.Background);
		Dockable daPlaylist = new DefaultDockable("pnPlaylist", pnPlaylist,
				"Playlist", null, DockingMode.CENTER + DockingMode.SINGLE);
		this.showAtCenter(daPlaylist);
	}

	/**
	 * Show the search at the center.
	 */
	public void showSearch() {
		SearchPanel sn = (SearchPanel) pnSearch;
		sn.setBackground(DezibelColor.Background);
		this.showAtCenter(daSearch);
	}
	
	/**
	 * Shows the albumpanel at the center. See <code>showPlaylist</code> for more details.
	 * @param album The album the user wants to receive more information from.
	 */
	public void showAlbum(Album album) {
		pnAlbum = new AlbumPanel(this, album);
		pnAlbum.setBackground(DezibelColor.Background);
		Dockable daAlbum = new DefaultDockable("pnAlbum", pnAlbum, "Album",
				null, DockingMode.CENTER + DockingMode.SINGLE);
		this.showAtCenter(daAlbum);

	}
	
	/**
	 * Shows the newspanel at the center, where the user can see all news from his favorised users/labels.
	 * @param n The news given by <code>n</code> will be selected and details will be displayed.
	 */
	public void showNews(News n) {
		NewsPanel pnNews = new NewsPanel(this, n);
		Dockable daNews = new DefaultDockable("pnNews", pnNews, "News",
				null, DockingMode.CENTER + DockingMode.SINGLE);
		this.showAtCenter(daNews);

	}
	
	/**
	 * This function is called from any child-panel if its content changes and other panels have to display the
	 * same content.
	 * @param ue A constant of which change was made.
	 */
	public void refresh(UpdateEntity ue) {
		switch (ue) {
		case PLAYLIST:
			pnMyList.refresh();
			if (this.centerDock.getDockable(0).getContent() instanceof PlaylistPanel) {
				PlaylistPanel pn = (PlaylistPanel) this.centerDock.getDockable(
						0).getContent();
				pn.refresh();
			}
			pnProfil.refresh();
			break;
		case FAVORITES:
			pnFavorites.refresh();
			pnProfil.refresh();
			pnSideNews.refresh();
			break;

		case APPLICATION:
			pnProfil.refresh();
			pnLabelProfil.refresh();
			break;

		case ALBUM:
			pnProfil.refresh();
			pnLabelProfil.refresh();
			if (pnAlbum != null) {
				pnAlbum.refresh();
			}

			break;
		case RECOMMENDATIONS:
			pnAds.refresh();

			break;
		case MEDIUM:
			((ProfilPanel) daProfil.getContent()).setTab(1);
			((LabelProfilPanel) daLabelProfil.getContent()).setTab(1);
			pnProfil.refresh();
			pnLabelProfil.refresh();
			break;

		case NEWS:
			((ProfilPanel) daProfil.getContent()).setTab(6);
			((LabelProfilPanel) daLabelProfil.getContent()).setTab(3);
			pnProfil.refresh();
			pnLabelProfil.refresh();
			pnSideNews.refresh();
			break;
                case LABEL:
			((ProfilPanel) daProfil.getContent()).setTab(5);
			((LabelProfilPanel) daLabelProfil.getContent()).setTab(3);
			pnProfil.refresh();
			pnSideNews.refresh();
			break;

		default:
			break;
		}
		
		// Check if the user is now an artist or labelmanager. In this case the user 
		// is allowed to create news at the menubar.
		if ((Database.getInstance().getLoggedInUser().isArtist() == true)
				|| Database.getInstance().getLoggedInUser().isLabelManager() == true) {
			if (itemCreateNews != null) {
				itemCreateNews.setEnabled(true);
			}
		} else {
			if (itemCreateNews != null) {
				itemCreateNews.setEnabled(false);
			}
		}
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
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.setMinimumSize(new Dimension(1024, 700)); 
		frame.setLocationRelativeTo(null);

		// Create the panel and add it to the frame.
		DezibelPanel panel = new DezibelPanel(frame);
		frame.getContentPane().add(panel);

		// Show.
		frame.setVisible(true);
	}

	/**
	 * Main-Function, it creates a the typical UI
	 * 
	 * @param args startup-arguments (will be ignored!)
	 */
	public static void main(String args[]) {
		Runnable doCreateAndShowGUI = new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		};
		SwingUtilities.invokeLater(doCreateAndShowGUI);
	}

	/**
	 * Creates the typical docking-behavior for each panel.
	 * Any panel is placed in a Dockable and the dockmodel for DezibelPanel
	 * is set to FloatDockModel.
	 */
	private void createDocking() {
		executor = new DockingExecutor();

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
		daNews = new DefaultDockable("pnNews", pnSideNews, "News", null,
				DockingMode.LEFT + DockingMode.RIGHT
						+ DockingMode.VERTICAL_LINE);
		daAds = new DefaultDockable("pnAds", pnAds, "Ads", null,
				DockingMode.LEFT + DockingMode.RIGHT
						+ DockingMode.VERTICAL_LINE);
		daMyLists = new DefaultDockable("pnMyList", pnMyList, "MyLists", null,
				DockingMode.LEFT + DockingMode.RIGHT
						+ DockingMode.VERTICAL_LINE);
		daFavorites = new DefaultDockable("pnFavorites", pnFavorites,
				"Favorites", null, DockingMode.LEFT + DockingMode.RIGHT
						+ DockingMode.VERTICAL_LINE);

		// Panels that can be docked only at top/bottom and center
		daPlayer = new DefaultDockable("pnPlayer", pnPlayer, "Player", null,
				DockingMode.CENTER + DockingMode.SINGLE + DockingMode.BOTTOM
						+ DockingMode.TOP + DockingMode.HORIZONTAL_LINE);
		daProfil = new DefaultDockable("pnProfil", pnProfil, "Profil", null,
				DockingMode.CENTER + DockingMode.SINGLE);
		daLabelProfil = new DefaultDockable("pnLabelProfil", pnLabelProfil,
				"Profil", null, DockingMode.CENTER + DockingMode.SINGLE);

		daSearch = new DefaultDockable("pnSearch", pnSearch, "Search", null,
				DockingMode.CENTER + DockingMode.SINGLE + DockingMode.BOTTOM
						+ DockingMode.TOP);

		// Add actions to the dockables.
		daLogin = addActions(daLogin);
		daRegister = addActions(daRegister);
		daPlayer = addActionsWithCloseExt(daPlayer);
		daNews = addActions(daNews);
		daAds = addActions(daAds);
		daMyLists = addActions(daMyLists);
		daFavorites = addActions(daFavorites);

		// Create the child tab dock.
		leftLineDock = new LineDock();
		rightLineDock = new LineDock();
		centerDock = new SingleDock();
		leftLineDock.setOrientation(LineDock.ORIENTATION_VERTICAL);
		rightLineDock.setOrientation(LineDock.ORIENTATION_VERTICAL);

		// Adding some listener to the panels, for dynamic layouts.
		this.addSideCenterListener();
		this.addTopBottomCenterListener();

		// Setting the main Dock to BorderDock, which have five positions
		// left,right,top,bottom and center. Every dockable to be docked at the
		// borderdock will be docked in a new LeafDock, depending on its
		// dockingmode.
		borderDock = new BorderDock();
		borderDock.setChildDockFactory(new LeafDockFactory());
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
		int[] states = { DockableState.NORMAL };
		Dockable wrapper = new StateActionDockable(dockable,
				new DefaultDockableStateActionFactory(), states);
		return wrapper;
	}

	/**
	 * See <code>addActions</code> for more details.
	 * Actions will be the normal behavior, close, minimize and externalize.
	 * @param dockable
	 *            the dockable where the actions should be added
	 * @return a new dockable with the actions, based on the object given as
	 *         parameter
	 */
	private Dockable addActionsWithCloseExt(Dockable dockable) {
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
						clearCenter();
						executor.changeDocking(daPlayer, borderDock);
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
		showSidebar(daMyLists);
		showSidebar(daNews);
		showSidebar(daFavorites);
		showSidebar(daAds);
	}

	/**
	 * Docks the specific dockable to the left or right sidebar, depending on
	 * which has a lower dockable count. bar shouldbe one of daMyLists, daNews,
	 * daFavorites, daAds
	 * 
	 * @param bar
	 *            the new Dockable which will be docked at the left or right
	 */
	private void showSidebar(Dockable bar) {
		LeafDock dockL = (LeafDock) borderDock
				.getChildDockOfPosition(Position.LEFT);
		LeafDock dockR = (LeafDock) borderDock
				.getChildDockOfPosition(Position.RIGHT);

		if (dockR == null) {
			dockR = new LineDock();
			borderDock.setDock(dockR, Position.RIGHT);
		}

		if (dockL == null) {
			dockL = new LineDock();
			borderDock.setDock(dockL, Position.LEFT);
		}

		if (dockL.getDockableCount() > dockR.getDockableCount()) {
			addLeft = false;
		} else {
			addLeft = true;
		}

		if (addLeft) {
			this.executor.changeDocking(bar, dockL, new Position(0));
		} else {
			this.executor.changeDocking(bar, dockR, new Position(0));
		}
	}

	/**
	 * Creates the typical menubar with icons
	 */
	private void createMenubar() {
		UIManager.put("MenuItem.selectionForeground", Color.BLUE);
		if (this.menuBar == null) {
			JMenu menuShow;
			JCheckBoxMenuItem cbMenuItem;
			ImageIcon icon;
			JMenuItem ibn;
			
			menuBar = new JMenuBar();
			JMenuItem menuLogout = new JMenuItem("Logout", new ImageIcon(this
					.getClass().getResource("/img/logout24x24.png")));
			menuLogout.setHorizontalAlignment(SwingConstants.CENTER);

			menuBar.add(menuLogout);
			menuShow = new JMenu("Anzeige");
			menuShow.setIcon(new ImageIcon(this.getClass().getResource(
					"/img/view24x24.png")));
			menuBar.add(menuShow);
			menuLogout.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					onLogout();
				}
			});

			icon = new ImageIcon(this.getClass().getResource(
					"/img/profil24x24.png"));
			ibn = new JMenuItem("Profil", icon);
			ibn.setContentAreaFilled(false);
			ibn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					((ProfilPanel) daProfil.getContent()).setUser(Database
							.getInstance().getLoggedInUser());
					onGoTo(daProfil);
				}
			});
			menuBar.add(ibn);

			icon = new ImageIcon(this.getClass().getResource(
					"/img/search24x24.png"));
			ibn = new JMenuItem("Suche", icon);
			ibn.setContentAreaFilled(false);
			ibn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					onGoTo(daSearch);
				}
			});
			menuBar.add(ibn);

			icon = new ImageIcon(this.getClass().getResource(
					"/img/player24x24.png"));
			ibn = new JMenuItem("Player", icon);
			ibn.setContentAreaFilled(false);
			ibn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					onGoTo(daPlayer);
				}
			});
			menuBar.add(ibn);

			cbMenuItem = new JCheckBoxMenuItem("News");
			cbMenuItem.setSelected(true);
			cbMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					onMenuCheckedSideBar((JCheckBoxMenuItem) arg0.getSource(),
							daNews);

				}
			});
			menuShow.add(cbMenuItem);
			cbMenuItem = new JCheckBoxMenuItem("Meine Liste");
			cbMenuItem.setSelected(true);
			cbMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					onMenuCheckedSideBar((JCheckBoxMenuItem) arg0.getSource(),
							daMyLists);
				}
			});
			menuShow.add(cbMenuItem);
			cbMenuItem = new JCheckBoxMenuItem("Favoriten");
			cbMenuItem.setSelected(true);
			cbMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					onMenuCheckedSideBar((JCheckBoxMenuItem) arg0.getSource(),
							daFavorites);

				}
			});
			menuShow.add(cbMenuItem);
			cbMenuItem = new JCheckBoxMenuItem("Werbung");
			cbMenuItem.setSelected(true);
			cbMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					onMenuCheckedSideBar((JCheckBoxMenuItem) arg0.getSource(),
							daAds);
				}
			});
			menuShow.add(cbMenuItem);
			JMenu menuUpload = new JMenu("Upload");
			menuUpload.setIcon(new ImageIcon(this.getClass().getResource(
					"/img/upload24x24.png")));
			JMenuItem itemUpload = new JMenuItem("Upload", new ImageIcon(this
					.getClass().getResource("/img/upload24x24.png")));
			menuUpload.add(itemUpload);

			itemUpload.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					onUpload();
				}
			});

			JMenu menuNews = new JMenu("News");
			menuNews.setIcon(new ImageIcon(this.getClass().getResource(
					"/img/news24x24.png")));
			itemCreateNews = new JMenuItem("News schreiben", new ImageIcon(this
					.getClass().getResource("/img/news24x24.png")));
			itemCreateNews.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					onCreateNews();
				}
			});

			if ((Database.getInstance().getLoggedInUser().isArtist() == true)
					|| (Database.getInstance().getLoggedInUser()
							.isLabelManager() == true)) {
				itemCreateNews.setEnabled(true);
			} else
				itemCreateNews.setEnabled(false);

			menuBar.add(itemUpload);
			menuBar.add(itemCreateNews);
			if (Database.getInstance().getLoggedInUser().isAdmin()) {
				JMenuItem itemManageGenres = new JMenuItem("Genres verwalten",
						new ImageIcon(this.getClass().getResource(
								"/img/genre24x24.png")));

				itemManageGenres.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						onGenre();
					}
				});

				menuBar.add(itemManageGenres);
			}
			menuBar.add(menuShow);

		}
	}

	/**
	 * Removes the menubar from the frame. Needed for logout.
	 */
	private void removeMenubar() {
		if (this.menuBar != null) {
			menuBar.removeAll();
			frame.remove(menuBar);
			menuBar = null;
		}
	}

	/**
	 * Shows the dockable give by <code>da</code> at the center.
	 * If the centerdock has already a dockable it will be removed.
	 * If there is a empty childdock, like linedock it will be removed.
	 * @param da The dockable that should be displayed at the center.
	 */
	private void showAtCenter(Dockable da) {

		if (da.getState() != DockableState.EXTERNALIZED) {

			if (da.getDock() != null) {
				LeafDock leaf = da.getDock();
				leaf.removeDockable(da);

				if (leaf == this.borderDock
						.getChildDockOfPosition(Position.TOP)) {
					borderDock.emptyChild(leaf);
				}
				if (leaf == this.borderDock
						.getChildDockOfPosition(Position.BOTTOM)) {
					borderDock.emptyChild(leaf);
				}
			}

			this.clearCenter(da);
			this.executor.changeDocking(da, this.centerDock, new Position(0));

		} else {
			JOptionPane.showMessageDialog(this,
					"Kann diese Aktion nicht ausführen,"
							+ "solange das Fenster nicht angedockt ist",
					"Fehler beim Andocken des Fensters",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Just like <code>clearCenter</code>
	 * @param newDa If the newDa is an instance of <code>PlaylistPanel</code> the refresh
	 * 				method is called.
	 */
	private void clearCenter(Dockable newDa) {
		if (this.centerDock.getDockableCount() > 0) {
			// refresh panels to clear selection and to load new data
			if ((this.centerDock.getDockable(0).getContent() instanceof PlaylistPanel)
					&& !(newDa.getContent() instanceof PlaylistPanel)) {
				this.refresh(UpdateEntity.PLAYLIST);
			}
			this.clearCenter();
		}
	}

	/**
	 * Clear the centerdock from any dockable
	 */
	public void clearCenter() {
		if (this.centerDock.getDockableCount() > 0) {
			// close the dockable at centerposition
			this.executor.changeDocking(this.centerDock
					.getDockable(this.centerDock.getDockableCount() - 1), null,
					new Position(0));
		}
	}

	/**
	 * This function is called, if the user press the logout-button in the menubar.
	 */
	private void onLogout() {
		// Check if player is not externalized, otherwise the user can not logout.
		if (daPlayer.getState() == DockableState.EXTERNALIZED) {
			JOptionPane.showMessageDialog(this,
					"Ausloggen nicht moeglich, solange "
							+ "der Player nicht angedockt ist",
					"Fehler beim Ausloggen", JOptionPane.ERROR_MESSAGE);
		} else {
			Database.getInstance().setLoggedInUser(null);
			this.removeMenubar();
			if (daAds.getDock() != null) {
				daAds.getDock().removeDockable(daAds);
			}
			if (daFavorites.getDock() != null) {
				daFavorites.getDock().removeDockable(daFavorites);
			}
			if (daNews.getDock() != null) {
				daNews.getDock().removeDockable(daNews);
			}
			if (daMyLists.getDock() != null) {
				daMyLists.getDock().removeDockable(daMyLists);
			}

			if (daProfil.getDock() != null) {
				daProfil.getDock().removeDockable(daProfil);
			}

			if (daLabelProfil.getDock() != null) {
				daLabelProfil.getDock().removeDockable(daLabelProfil);
			}

			if (daSearch.getDock() != null) {
				daSearch.getDock().removeDockable(daSearch);
			}

			if (daPlayer.getDock() != null) {
				daPlayer.getDock().removeDockable(daPlayer);
			}

			// Clear the center, and remove all user-specific content from the panels.
			this.clearCenter();
			pnLogin.reset();
			pnRegister.reset();
			pnPlayer.reset();
			pnSideNews.reset();
			pnAds.reset();
			pnMyList.reset();
			pnFavorites.reset();
			pnProfil.reset();
			pnSearch.reset();
			pnPlayer.reset();
			Database.getInstance().save();
			this.showLogin();
		}
	}

	/**
	 * If the specific menuitem is selected the dockable will be displayed, otherwise it
	 * will be removed from the sidebar.
	 * @param src The menuitem for the dockable.
	 * @param da The dockable to be added/removed from the sidebar.
	 */
	private void onMenuCheckedSideBar(JCheckBoxMenuItem src, Dockable da) {
		if (src.isSelected()) {
			showSidebar(da);
		} else {
			if (da.getDock() != null) {
				da.getDock().removeDockable(da);
			}
		}
	}

	/**
	 * Displays the specific dockable at the center.
	 * @param da dockable which should be displayed at the center.
	 */
	private void onGoTo(Dockable da) {
		this.showAtCenter(da);
	}

	/**
	 * Shows a new <code>UploadDialog</code> to upload a new musicfile.
	 */
	private void onUpload() {
		UploadDialog ud = new UploadDialog(frame, null, null, this);
		ud.setVisible(true);
	}

	/**
	 * Shows a new <code>GenreDialog</code> for editing the genre list.
	 * Can only be called, if the current user is the administrator.
	 */
	private void onGenre() {
		GenreDialog dl = new GenreDialog(frame);
		dl.setVisible(true);
	}

	/**
	 * Shows a new <code>NewsDialog</code> for creating a new news.
	 * Can only be called, if the current user is an artist or labelmanager.
	 */
	private void onCreateNews() {
		NewsDialog nd = new NewsDialog(frame, this);
		nd.setVisible(true);
	}

	/**
	 * returns the frame, which has <code>DezibelPanel</code> as childpanel.
	 * @return The frame in wich <code>Dezipelpanel</code> is displayed
	 */
	public JFrame getFrame() {
		return this.frame;
	}
	
	/**
	 * Shows an instance of <code>Mediumpanel</code> at the center.
	 * The content of the panel is the information of the <code>medium</code>
	 * @param medium The medium the user wants to receive information.
	 */
	public void showMedium(Medium medium) {
		MediumPanel pnMedium = new MediumPanel(this, medium);
		pnMedium.setBackground(DezibelColor.Background);
		Dockable daMedium = new DefaultDockable("pnMedium", pnMedium, "Medium",
				null, DockingMode.CENTER + DockingMode.SINGLE);
		this.showAtCenter(daMedium);
	}
}
