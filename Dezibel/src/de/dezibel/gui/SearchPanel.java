package de.dezibel.gui;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;

public class SearchPanel extends DragablePanel {

	private static final long serialVersionUID = 1L;
	private JLabel lbSearch;
	private JTextField tfSearch;
	private JComboBox<String> cbFilter;
	private JButton bnSearch;
	private JRadioButton rbAlphabetical;
	private JRadioButton rbRating;
	private JRadioButton rbUploadDate;
	private JPanel		pnSorting;
	private JTree		treeResults;
	private JScrollPane treePanel;
	private GroupLayout layout;
	
	private JPanel pnSortingMedium;
	private JPanel pnSortingUser;
	private JPanel pnSortingLabel;
	private JPanel pnSortingPlaylists;
	
	public SearchPanel(DezibelPanel parent) {
		super(parent);
		this.createComponents();
		this.createLayout();
		this.setLayout(layout);
	}
	
	private void createComponents(){
		String[] choices = {"Songs","User","Label","Playlists"};
		lbSearch = new JLabel("Search:");
		tfSearch = new JTextField("Search...");
		cbFilter = new JComboBox<String>(choices);
		bnSearch = new JButton("Search");
		rbAlphabetical = new JRadioButton("Alphabetical");
		rbRating = new JRadioButton("Rating");
		rbUploadDate = new JRadioButton("Upload-Date");
		pnSorting = new JPanel();
		treeResults = new JTree();
		treePanel = new JScrollPane(treeResults);
		
	}
	
	private void createLayout(){
		layout = new GroupLayout(this);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
				.addGroup(layout.createSequentialGroup().addComponent(tfSearch,128,128,128)
				.addComponent(cbFilter,128,128,128).addComponent(bnSearch,128,128,128))
				.addGroup(layout.createSequentialGroup().addComponent(pnSorting).addGap(128,128,128))
				.addGroup(layout.createSequentialGroup().addComponent(treePanel))
				);
		
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING,true)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(tfSearch)
								.addComponent(cbFilter)
								.addComponent(bnSearch))
						.addGroup(layout.createParallelGroup()
								.addComponent(pnSorting)
								.addGap(128,128,128)))
						.addGroup(layout.createParallelGroup()
								.addComponent(treePanel))
				);
	}
}
