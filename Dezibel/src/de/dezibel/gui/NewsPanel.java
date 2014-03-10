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
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Container;
import javax.swing.GroupLayout;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Pascal
 *
 */
public class NewsPanel extends DragablePanel {

    private static final long serialVersionUID = 1L;
    private JLabel lbnews;
    private JLabel lbAutor ;
    private JLabel lbDatum;
    private JLabel lbTitel;
    private JLabel lbText;
        
    private JTextField tfAutor;  
    private JTextField tfDatum;    
    private JTextField tfTitel;           

    private JTextArea taText;
    
    private JTable tNews;
    private JScrollPane spNews1;
    private JScrollPane spNews2;
    private NewsSideTableModel model;
    private Container pnNews;
    
    

    public NewsPanel(DezibelPanel parent) {
        super(parent);
        this.createNewsPanel();
    //    this.createComponents();
      //  this.createLayout();
      //  this.setBackground(DezibelColor.PanelBackground);
    } 
    public  void createNewsPanel(){
        
      lbnews = new JLabel("News");
      lbAutor = new JLabel("Autor" );
      lbDatum = new JLabel("Datum");
      lbTitel = new JLabel("Titel");
      tfAutor = new JTextField();  
      tfDatum = new JTextField();    
      tfTitel  = new JTextField();    
      taText = new JTextArea();   
      tNews = new JTable();
      
      // Table News scrollpane 
      spNews1 = new JScrollPane();
      spNews1.getViewport().setView(tNews);
      
      // scrollpane fur taNews
      spNews2 = new JScrollPane();
      spNews2.getViewport().setView(taText);
          
      
      GroupLayout layout = new GroupLayout(pnNews);
      layout.setHorizontalGroup(layout
            .createSequentialGroup( )
            .addGroup(
                        true, layout.createSequentialGroup()
                        .addComponent(spNews1, 128, 128, 200)
                        .addGap(25)
                        
                        )
                              
                .addGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                
                .addGroup(
                        GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(lbTitel, 128, 128, 128)
                        .addComponent(tfTitel, 128, 128, 1500))
                .addGroup(
                        GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(lbAutor, 128, 128, 128)
                        .addComponent(tfAutor, 128, 128, 1500))
                .addGroup(
                       GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(lbDatum, 128, 128, 128)
                        .addComponent(tfDatum, 128, 128, 1500))
                 .addGroup(
                       GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(spNews2, 128, 128, 1500))              
                        
                )
      );
                
                

      layout.setVerticalGroup(layout.createParallelGroup(
                GroupLayout.Alignment.LEADING, true)
                 .addGroup(
                            layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                            .addComponent(spNews1, 32, 32, 1000)
                            .addGap(25)

                 )
                                   
                 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                 .addGroup(layout.createSequentialGroup()
                       
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                                .addComponent(lbTitel, 32, 32, 32)
                                .addComponent(tfTitel, 32, 32, 32))
                        .addGroup(
                                layout.createParallelGroup()
                                .addComponent(lbAutor, 32, 32, 32)
                                .addComponent(tfAutor, 32, 32, 32))
                        .addGroup(
                                layout.createParallelGroup()
                                .addComponent(lbDatum, 32, 32, 32)
                                .addComponent(tfDatum, 32, 32, 32))
                        .addGroup(
                                layout.createParallelGroup()
                                .addComponent(spNews2, 32, 32, 1000))
          
                          
                 ) 
                 )
       ); 

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        pnNews.setLayout(layout);
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void refresh() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
