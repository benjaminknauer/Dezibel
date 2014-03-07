
package de.dezibel.gui;

import de.dezibel.data.Database;
import de.dezibel.data.Playlist;
import de.dezibel.data.User;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Aris, Tristan
 */
public class FavoritesPanel extends DragablePanel{

    private JLabel lbTitel;
    private JScrollPane scrollPane1;
    private JScrollPane scrollPane2;
    private JTable tblFavoritesUser;
    private JTable tblFavoritesLabel;
    private FavoritesTableModelUser ftmu;
   private FavoritesTableModelLabel ftml;

     
    private JPopupMenu currentPopupMenu;
    private DezibelPanel dp;
    private User currentUser;

    public FavoritesPanel(DezibelPanel parent) {
        super(parent);
        this.dp = parent;
            createComponents();
            createLayout();
            
        

    }
    @Override
    public void refresh() {
        if (Database.getInstance().getLoggedInUser() != null) {
        LinkedList<Playlist> myPlaylists = Database.getInstance().getLoggedInUser()
                .getCreatedPlaylists();
        LinkedList<Playlist> favoritePlaylists = Database.getInstance().getLoggedInUser()
                .getFavoritePlaylists();
        myPlaylists.addAll(favoritePlaylists);
        //mltm.setData(myPlaylists);
        }
    }

    private void createComponents() {
        lbTitel = new JLabel("Favorites");
        ftmu = new FavoritesTableModelUser();
        ftml = new FavoritesTableModelLabel();
        tblFavoritesUser = new JTable(ftmu);
        tblFavoritesLabel = new JTable(ftml);
        scrollPane1 = new JScrollPane(tblFavoritesUser);
        scrollPane2 = new JScrollPane(tblFavoritesLabel);


        tblFavoritesUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    User u = (User) ftmu.getValueAt(
                            tblFavoritesUser.getSelectedRow(), -1);
                    dp.showFavoritesUser(u);
                }
            }            
        });
        
        tblFavoritesLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                   User u = (User) ftmu.getValueAt(
                            tblFavoritesLabel.getSelectedRow(), -1);
                    dp.showFavoritesLabel(u);
                }
            }            
        });

    }

    private void createLayout() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(lbTitel);
        this.add(scrollPane1);
        this.add(scrollPane2);
    }

    void setFavoiteUser(User newUser) {
        this.currentUser = newUser;
        this.refresh();
    }
}
