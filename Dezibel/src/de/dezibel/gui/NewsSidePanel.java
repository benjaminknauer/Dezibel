package de.dezibel.gui;

import de.dezibel.UpdateEntity;
import de.dezibel.control.NewsControl;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import de.dezibel.control.NewsControl;
import de.dezibel.data.News;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Panel for displaying the 15 latest news from favorised 
 * users/labels in the sidebar.
 * @author Pascal
 */
public class NewsSidePanel extends DragablePanel {

    private static final long serialVersionUID = 1L;
    private JLabel lbTitle;
    private JTable tblNews;
    private JScrollPane spNews;
    private NewsSideTableModel model;
    
    /**
     * Creates the panel with its components.
     * Background-Color is set to <code>DezibelColor.Panelbackground</code>
     * @param parent
     */
    public NewsSidePanel(DezibelPanel parent) {
        super(parent);

        this.createComponents();
        this.createLayout();
        this.setBackground(DezibelColor.PanelBackground);
    }
    
    /**
     * Help function to create all components
     */
    private void createComponents() {
        lbTitle = new JLabel("Neuigkeiten");
        model = new NewsSideTableModel();
        tblNews = new JTable(model);
        spNews = new JScrollPane(tblNews);
        spNews.setViewportView(tblNews);

        spNews.setBackground(DezibelColor.PanelBackground);
        
        tblNews.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                parent.refresh(UpdateEntity.PLAYLIST);
                parent.refresh(UpdateEntity.FAVORITES);
                parent.refresh(UpdateEntity.RECOMMENDATIONS);
            }

        });
        
        tblNews.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
 
                if (e.getClickCount() == 2) {
                    News n = (News) model.getValueAt(
                            tblNews.getSelectedRow(), -1);
                    if (n != null) {
                        onDoubleClick(n);
                    }
                }

            }
        });
    }
    
    /**
     * Help function to create the layout
     */
    private void createLayout() {
        this.removeAll();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        lbTitle.setAlignmentX(CENTER_ALIGNMENT);
        lbTitle.setFont(DezibelFont.SIDEPANEL_TITLE);
        this.add(lbTitle);
        this.add(spNews);
    }

    /**
     * This function is called, if the user clicks on a news.
     * The function <code>showNews()</code> is called from <code>DezibelPanel</code>
     * @param n The selected news in the list
     */
    private void onDoubleClick(News n) {
        this.parent.showNews(n);
    }
    
    @Override
    /**
     * Clears all news in the current list.
     */
    public void reset() {
        int i = model.getRowCount() - 1;

        while (i > 0) {
            model.removeRow(i);
            i--;
        }
    }
    
    @Override
    /**
     * Refresh all user-information, displayed on the panel See
     * <code>DragablePanel</code>
     */
    public void refresh() {
        this.reset();
        NewsControl controller = new NewsControl();
        model.setData(controller.searchForNews());
        this.tblNews.setModel(model);
    }
    
    @Override
    public void onTopBottom() {

    }
    
    @Override
    public void onLeftRight() {

    }
    
    @Override
    public void onCenter() {
    }
    
    @Override
    public void onExternalized() {

    }

}
