package de.dezibel.gui;

import de.dezibel.control.AdsControl;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;

/**
 * Shows recommendations.
 * @author Richard
 */
public class AdsPanel extends DragablePanel {
    
    private RecommendationsTableModel tableModelRecommendations;
    private AdsControl control;
    private JLabel lbTitle;
    
    public AdsPanel(DezibelPanel parent) {
        super(parent);
        this.control = new AdsControl();
        init();
        this.setBackground(DezibelColor.PanelBackground);
    }
    
    public void init() {
        tableModelRecommendations = new RecommendationsTableModel();
        tableModelRecommendations.setData(control.getRecommendedMedia());
        lbTitle = new JLabel("Empfehlungen");
        JTable tableRecommendations = new JTable(tableModelRecommendations);
        JScrollPane scrollPane = new JScrollPane(tableRecommendations);
        
        JButton btnRefresh = new JButton("Empfehlungen neu abrufen");
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModelRecommendations.setData(control.getRecommendedMedia());
            }
        });
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.lbTitle.setAlignmentX(CENTER_ALIGNMENT);
        this.lbTitle.setFont(DezibelFont.SIDEPANEL_TITLE);
        this.add(lbTitle);
        this.add(scrollPane);
        btnRefresh.setAlignmentX(CENTER_ALIGNMENT);
        this.add(btnRefresh);
        
        //TODO Die anderen Panels verwenden alle BoxLayout (kann aber wieder geändert werden ;) )
//        GroupLayout layout = new GroupLayout(this);
//        layout.setHorizontalGroup(layout.createParallelGroup()
//                .addComponent(scrollPane)
//                .addGap(10)
//                .addComponent(btnRefresh, GroupLayout.Alignment.CENTER)
//                .addGap(10)
//        );
//        layout.setVerticalGroup(layout.createSequentialGroup()
//                .addComponent(scrollPane)
//                .addGap(10)
//                .addComponent(btnRefresh)
//                .addGap(10)
//        );
//        setLayout(layout);
    }

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh() {
		tableModelRecommendations.setData(control.getRecommendedMedia());
		
	}
    
}
