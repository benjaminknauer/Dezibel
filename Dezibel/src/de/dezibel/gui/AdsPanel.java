package de.dezibel.gui;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Shows recommendations.
 * @author Richard
 */
public class AdsPanel extends DragablePanel {
    
    private RecommendationsTableModel tableModelRecommendations;
    
    public AdsPanel(DezibelPanel parent) {
        super(parent);
        init();
    }
    
    public void init() {
        tableModelRecommendations = new RecommendationsTableModel();
        JTable tableRecommendations = new JTable(tableModelRecommendations);
        JScrollPane scrollPane = new JScrollPane(tableRecommendations);
        
        JButton btnRefresh = new JButton("Aktualisieren");
        
        GroupLayout layout = new GroupLayout(this);
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(scrollPane)
                .addGap(10)
                .addComponent(btnRefresh, GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(scrollPane)
                .addGap(10)
                .addComponent(btnRefresh)
        );
    }
    
}
