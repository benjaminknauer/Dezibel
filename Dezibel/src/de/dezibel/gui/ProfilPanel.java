package de.dezibel.gui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ProfilPanel extends DragablePanel {
	
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabPanel;
	private JPanel pnTest;
	
	public ProfilPanel(DezibelPanel parent) {
		super(parent);
		this.createComponents();
		this.add(tabPanel);
	}
	
	private void createComponents(){
		this.tabPanel = new JTabbedPane();
		this.pnTest = new JPanel();
		tabPanel.addTab("Test",null,pnTest);
	}
	
	private void createLayout(){
		
	}
}
