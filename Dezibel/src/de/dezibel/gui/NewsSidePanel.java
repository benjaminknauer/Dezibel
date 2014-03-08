package de.dezibel.gui;

import de.dezibel.control.NewsControl;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import de.dezibel.control.NewsControl;
import de.dezibel.data.News;

/**
 *
 * @author Pascal
 *
 */
public class NewsSidePanel extends DragablePanel {

    private static final long serialVersionUID = 1L;
    private JLabel lbTitle;
    private JTable tblNews;
    private JScrollPane spNews;
    private NewsSideTableModel model;

    public NewsSidePanel(DezibelPanel parent) {
        super(parent);

        this.createComponents();
        this.createLayout();
        this.setBackground(DezibelColor.PanelBackground);
    }

    private void createComponents() {
        lbTitle = new JLabel("Neuigkeiten");
        model = new NewsSideTableModel();
        tblNews = new JTable(model);
        spNews = new JScrollPane(tblNews);
        spNews.setViewportView(tblNews);

        spNews.setBackground(DezibelColor.PanelBackground);
        tblNews.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    News n = (News) model.getValueAt(
                            tblNews.getSelectedRow(), -1);
                    if (n != null) {
                        onDoubleClick();
                    }
                }

            }
        });
    }

    private void createLayout() {
        this.removeAll();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        lbTitle.setAlignmentX(CENTER_ALIGNMENT);
        lbTitle.setFont(DezibelFont.SIDEPANEL_TITLE);
        this.add(lbTitle);
        this.add(spNews);
    }

    private void onDoubleClick() {
        //TODO hier funktion beim DezibelPanel erstellen die die News anzeigt
        System.out.println("Double-Clicked on News");
    }

    public void reset() {
        int i = model.getRowCount() - 1;

        while (i > 0) {
            model.removeRow(i);
            i--;
        }
    }

    /**
     * Refresh all user-information, displayed on the panel See
     * <code>DragablePanel</code>
     */
    public void refresh() {
        this.reset();
        NewsControl controller = new NewsControl();
        NewsSideTableModel model = new NewsSideTableModel();
        model.setData(controller.searchForNews());
        this.tblNews.setModel(model);
    }

    public void onTopBottom() {

    }

    public void onLeftRight() {

    }

    public void onCenter() {
    }

    public void onExternalized() {

    }

}
