package de.dezibel.gui;

import de.dezibel.data.Medium;
import de.dezibel.data.User;
import de.dezibel.player.Player;
import de.dezibel.player.PlayerObserver;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
        User u = new User("Hans", "Peter", "Hans", "123", true);
        Medium m1 = new Medium("Alarm01", u, "C:\\Alarm01.wav");
        Medium m2 = new Medium("Tsunami", u, "C:\\DVBBS & Borgeous - Tsunami.mp3");
        player.addMedium(m1);
        player.addMedium(m2);
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
        final JSlider slider = new JSlider(0, 1000, 0);
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
        player.addObserver(new PlayerObserver() {
            @Override
            public void onTrackChanged(Medium newMedium) {
                lblTitle.setText(newMedium.getArtist().getFirstname() + " - "
                        + newMedium.getTitle());
                volume.setValue(player.getVolume());
            }
        });
        volume.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                player.setVolume(volume.getValue());
            }
        });
        
        // Slider-Thread
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (player.isPlaying()) {
                        int seconds = player.getCurrentTime() % 60;
                        int minutes = player.getCurrentTime() / 60;
                        lblElapsedTime.setText(minutes + ":" + (seconds < 10?"0" + seconds:seconds));
                        
                        seconds = (player.getTotalDuration() - player.getCurrentTime()) % 60;
                        minutes = (player.getTotalDuration() - player.getCurrentTime()) / 60;
                        lblTimeLeft.setText("-" + minutes + ":" + (seconds < 10?"0" + seconds:seconds));
                    }
                }
            }
        }).start();
    }

}
