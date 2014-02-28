package de.dezibel.gui;

import de.dezibel.data.Medium;
import de.dezibel.data.User;
import de.dezibel.player.Player;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.WindowConstants;

/**
 *
 * @author Tobias, Richard
 */
public class PlayerPanel extends DragablePanel {
    
    private Player player;

    /**
     * Test
     *
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new PlayerPanel();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Constructor
     */
    public PlayerPanel() {
        super();
        // Initialize Player
        player = Player.getInstance();
        User u = new User("asd", "sdg", "sdf", "123", true);
        Medium m = new Medium("Testsong", u, "C:\\DVBBS & Borgeous - Tsunami.mp3");
        player.addMedium(m);
        init();
    }

    /**
     * Initiates all components of the PlayerPanel
     */
    private void init() {
        // Add title label
        final JLabel lblTitle = new JLabel("Interpret - Titel");

        // Add seeker
        final JLabel lblElapsedTime = new JLabel("2:20");
        final JSlider slider = new JSlider();
        final JLabel lblTimeLeft = new JLabel("3:40");

        // Add Buttons und volume slider
        final JButton btnPrev = new JButton("prev");
        final JButton btnPlayPause = new JButton("play");
        final JButton btnStop = new JButton("stop");
        final JButton btnNext = new JButton("next");
        final JSlider volume = new JSlider(JSlider.VERTICAL, 0, 100, 50);
        
        int minHGap = 0, prefHGap = 20, maxHGap = 20;
        GroupLayout layout = new GroupLayout(this);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lblTitle, GroupLayout.Alignment.CENTER)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblElapsedTime)
                        .addComponent(slider)
                        .addComponent(lblTimeLeft))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnPrev)
                        .addGap(minHGap, prefHGap, maxHGap)
                        .addComponent(btnPlayPause)
                        .addGap(minHGap, prefHGap, maxHGap)
                        .addComponent(btnStop)
                        .addGap(minHGap, prefHGap, maxHGap)
                        .addComponent(btnNext)
                        .addGap(minHGap, prefHGap, 100000))
                    )
                .addGap(minHGap, prefHGap, maxHGap)
                .addComponent(volume))
        );
        int minVGap = 0, prefVGap = 20, maxVGap = 20;
        int sliderHeight = (int) (lblTitle.getPreferredSize().getHeight()
                + lblElapsedTime.getPreferredSize().getHeight()
                + btnPrev.getPreferredSize().getHeight()) + 2 * prefVGap;
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblTitle)
                    .addGap(minVGap, prefVGap, maxVGap)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lblElapsedTime)
                        .addComponent(slider)
                        .addComponent(lblTimeLeft))
                    .addGap(minVGap, prefVGap, maxVGap)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(btnPrev)
                        .addComponent(btnPlayPause)
                        .addComponent(btnStop)
                        .addComponent(btnNext))
                    )
                .addComponent(volume, sliderHeight, sliderHeight, sliderHeight)
                .addGap(minVGap, prefVGap, 100000)
        );
        setLayout(layout);
        
        // Listener
        btnPrev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.previous();
            }
        });
        btnPlayPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.isPlaying()) {
                    player.pause();
                    btnPlayPause.setText("Play");
                } else {
                    player.play();
                    btnPlayPause.setText("Pause");
                }
            }
        });
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.stop();
                btnPlayPause.setText("Play");
            }
        });
        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.next();
            }
        });
    }

}
