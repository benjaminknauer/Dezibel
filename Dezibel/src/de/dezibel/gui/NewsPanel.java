package de.dezibel.gui;

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
public class NewsPanel extends DragablePanel {

	private static final long serialVersionUID = 1L;
	private JLabel lbTitle;
	private JTable tblNews;
    private JScrollPane spNews;
    private NewsTableModel model;
	
	public NewsPanel(DezibelPanel parent) {
		super(parent);
		
		this.createComponents();
		this.createLayout();
		this.setBackground(DezibelColor.PanelBackground);
	}
	
	private void createComponents(){
		lbTitle = new JLabel("Neuigkeiten");
		model = new NewsTableModel();
		tblNews = new JTable(model);
		spNews = new JScrollPane(tblNews);
		spNews.setViewportView(tblNews);
		
		spNews.setBackground(DezibelColor.PanelBackground);
		tblNews.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
                    News n = (News) model.getValueAt(
                            tblNews.getSelectedRow(), -1);
                    if (n != null) {
                    	onDoubleClick();
                    }
                }
				
			}
		});
	}
	
	private void createLayout(){
		this.removeAll();
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		this.add(lbTitle);
		this.add(spNews);
	}
	
	private void onDoubleClick(){
        //TODO hier funktion beim DezibelPanel erstellen die die News anzeigt
		System.out.println("Double-Clicked on News");
	}
	
	public void reset(){
		int i=model.getRowCount()-1;
	
		while(i > 0)
		{
			model.removeRow(i);
			i--;
		}
    }
    
    /**
     * Refresh all user-information, displayed on the panel
     * See <code>DragablePanel</code>
     */
    public void refresh(){
    	this.reset();
    	NewsControl controller = new NewsControl();
    	NewsTableModel  model = new NewsTableModel();
    	model.setData(controller.searchForNews());
    	this.tblNews.setModel(model);
    }
    
    public void onTopBottom() {
        
    }
    public void onLeftRight() {
    	
    }
    
    public void onCenter() {
    }
    
    public void onExternalized(){
    	
    }

}
