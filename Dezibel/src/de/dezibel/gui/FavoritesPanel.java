package de.dezibel.gui;

import de.dezibel.data.Database;
import de.dezibel.data.Label;
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
public class FavoritesPanel extends DragablePanel {

    private JLabel lbTitel;
    private JScrollPane scrollPane;
    private JTable tblFavorites;
    private FavoritesTableModel ftm;
    private JPopupMenu currentPopupMenu;
    private User currentUser;

    public FavoritesPanel(DezibelPanel parent) {
        super(parent);
        createComponents();
        createLayout();
        this.setBackground(DezibelColor.PanelBackground);
    }

    @Override
    public void refresh() {
        if (Database.getInstance().getLoggedInUser() != null) {
            LinkedList<User> favorizedUsers = Database.getInstance().getLoggedInUser()
                    .getFavorizedUsers();
            LinkedList<Label> favorizedLabels = Database.getInstance().getLoggedInUser()
                    .getFavorizedLabels();
            ftm.setDataUser(favorizedUsers);
            ftm.setDataLabel(favorizedLabels);
        }
    }

    private void createComponents() {
        lbTitel = new JLabel("Favorites");
        ftm = new FavoritesTableModel();
        tblFavorites = new JTable(ftm);
        scrollPane = new JScrollPane(tblFavorites);



        tblFavorites.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {

                    if (ftm.getValueAt(tblFavorites.getSelectedRow(), -1) instanceof User) {
                        User u = (User) ftm.getValueAt(
                                tblFavorites.getSelectedRow(), -1);
                        parent.showProfile(u);
                    } else if (ftm.getValueAt(tblFavorites.getSelectedRow(), -1) instanceof Label) {
                        Label l = (Label) ftm.getValueAt(
                                tblFavorites.getSelectedRow(), -1);
                        parent.showProfile(l);
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
                ContextMenu contextMenu = new ContextMenu(parent);
                currentPopupMenu = contextMenu.getContextMenu(tblFavorites, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());
            }
            
        });



    }

    private void createLayout() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.lbTitel.setAlignmentX(CENTER_ALIGNMENT);
        this.lbTitel.setFont(DezibelFont.SIDEPANEL_TITLE);
        this.add(lbTitel);
        this.add(scrollPane);
    }

    void setFavoiteUser(User newUser) {
        this.currentUser = newUser;
        this.refresh();
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub
    }
}
