package de.dezibel.gui;

import de.dezibel.control.AdsControl;
import de.dezibel.data.Medium;
import de.dezibel.player.Player;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;

/**
 * Shows recommendations.
 *
 * @author Richard
 */
public class AdsPanel extends DragablePanel {

    private RecommendationsTableModel tableModelRecommendations;
    private AdsControl control;
    private JLabel lbTitle;
    JTable tableRecommendations;

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
        tableRecommendations = new JTable(tableModelRecommendations);
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

        tableRecommendations.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    Medium m = (Medium) tableModelRecommendations.getValueAt(
                            tableRecommendations.getSelectedRow(), -1);
                    if (m != null) {
                        Player.getInstance().addMediumAsNext(m);
                        Player.getInstance().next();
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (me.isPopupTrigger()) {
                    showPopup(me);
                }
            }

            private void showPopup(MouseEvent me) {
                JPopupMenu currentPopupMenu;
                ContextMenu contextMenu = new ContextMenu(parent);
                currentPopupMenu = contextMenu.getContextMenu(tableRecommendations, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());
            }
        });
    }

    @Override
    public void reset() {
	// Nicht notwendig
    }

    @Override
    public void refresh() {
        tableModelRecommendations.setData(control.getRecommendedMedia());

    }

}
