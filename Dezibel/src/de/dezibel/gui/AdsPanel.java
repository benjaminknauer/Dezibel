package de.dezibel.gui;

import de.dezibel.control.AdsControl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private AdsControl control;
    
    public AdsPanel(DezibelPanel parent) {
        super(parent);
        this.control = new AdsControl();
        init();
    }
    
    public void init() {
        tableModelRecommendations = new RecommendationsTableModel();
        tableModelRecommendations.setData(control.getRecommendedMedia());
        JTable tableRecommendations = new JTable(tableModelRecommendations);
        JScrollPane scrollPane = new JScrollPane(tableRecommendations);
        
        JButton btnRefresh = new JButton("Aktualisieren");
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModelRecommendations.setData(control.getRecommendedMedia());
            }
        });
        
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
        setLayout(layout);
    }
    
}
