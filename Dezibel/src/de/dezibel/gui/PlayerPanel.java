package de.dezibel.gui;

import de.dezibel.data.Medium;
import de.dezibel.player.Player;
import de.dezibel.player.PlayerObserver;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Tobias, Richard
 */
public class PlayerPanel extends DragablePanel {

    private final Player player;

    private GroupLayout layout;
    private JLabel lblTitle;
    private JSlider slider;
    private JLabel lblElapsedTime;
    private JLabel lblTimeLeft;
    private JButton btnPrev;
    private JButton btnPlayPause;
    private JButton btnStop;
    private JButton btnNext;
    private JSlider volume;
    private JTable tablePlaylist;
    private JScrollPane scrollPane;

    private MediaTableModel mediaTableModel;

    /**
     * Constructor
     *
     * @param parent The parent panel
     */
    public PlayerPanel(DezibelPanel parent) {
        super(parent);
        // Initialize Player
        this.player = Player.getInstance();
        this.init();
    }

    /**
     * Initiates all components of the PlayerPanel
     */
    private void init() {
        // Initialize layout
        layout = new GroupLayout(this);

        // Add title label
        lblTitle = new JLabel();

        // Add seeker
        lblElapsedTime = new JLabel();
        slider = new JSlider(0, 1000, 0);
        lblTimeLeft = new JLabel();

        // Add Buttons und volume slider
        btnPrev = new JButton("prev");
        btnPlayPause = new JButton("play");
        btnStop = new JButton("stop");
        btnNext = new JButton("next");
        volume = new JSlider(JSlider.VERTICAL, 0, 100, 50);

        // Playlist
        mediaTableModel = new MediaTableModel();
        tablePlaylist = new JTable(mediaTableModel);
        tablePlaylist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePlaylist.setEnabled(false);
        scrollPane = new JScrollPane(tablePlaylist);

        createTopBottomLayout();

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

        slider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                slider.setValue((int) ((double) e.getX() / (double) slider.getWidth() * 1000.0));
                player.jumpTo((int) ((double) slider.getValue() / 1000.0 * (double) player.getTotalDuration()));
            }
        });

        this.player.addObserver(new PlayerObserver() {
            @Override
            public void onStateChanged(Medium newMedium) {
                lblTitle.setText(newMedium.getArtist().getPseudonym() + " - "
                        + newMedium.getTitle());
                volume.setValue(player.getVolume());
                mediaTableModel.setData(player.getPlaylist());
                tablePlaylist.getSelectionModel().setSelectionInterval(
                        Player.getInstance().getCurrentIndex(),
                        Player.getInstance().getCurrentIndex());
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        if (player.isPlaying()) {
                            btnPlayPause.setText("Pause");
                        } else {
                            btnPlayPause.setText("Play");
                        }
                    }
                });
            }
        });
        volume.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                player.setVolume(volume.getValue());
            }
        });
        volume.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                volume.setValue((int) ((double) (volume.getHeight() - e.getY())
                        / (double) volume.getHeight() * 100.0));
            }
        });

        // Slider-Thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (player.isPlaying()) {
                        int seconds = player.getCurrentTime() % 60;
                        int minutes = player.getCurrentTime() / 60;
                        lblElapsedTime.setText(minutes + ":" + (seconds < 10 ? "0" + seconds : seconds));

                        seconds = (player.getTotalDuration() - player.getCurrentTime()) % 60;
                        minutes = (player.getTotalDuration() - player.getCurrentTime()) / 60;
                        lblTimeLeft.setText("-" + minutes + ":" + (seconds < 10 ? "0" + seconds : seconds));

                        slider.setValue((int) (((double) player.getCurrentTime() / (double) player.getTotalDuration()) * 1000));
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * Creates the panel layout when the panel is docked on top or bottom.
     */
    private void createTopBottomLayout() {
        int minHGap = 0, prefHGap = 20, maxHGap = 20;
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblTitle, GroupLayout.Alignment.CENTER)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblElapsedTime)
                                        .addGap(minHGap, prefHGap, maxHGap)
                                        .addComponent(slider)
                                        .addGap(minHGap, prefHGap, maxHGap)
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
        scrollPane.setVisible(false);
        setLayout(layout);
    }

    /**
     * Creates the panel layout when the panel is docked on center.
     */
    private void createCenterLayout() {
        int minHGap = 0, prefHGap = 20, maxHGap = 20;
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblTitle, GroupLayout.Alignment.CENTER)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblElapsedTime)
                                        .addGap(minHGap, prefHGap, maxHGap)
                                        .addComponent(slider)
                                        .addGap(minHGap, prefHGap, maxHGap)
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
                .addComponent(scrollPane)
        );
        int minVGap = 0, prefVGap = 20, maxVGap = 20;
        int sliderHeight = (int) (lblTitle.getPreferredSize().getHeight()
                + lblElapsedTime.getPreferredSize().getHeight()
                + btnPrev.getPreferredSize().getHeight()) + 2 * prefVGap;
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                .addGroup(
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
                )
                .addGap(minVGap, prefVGap, maxVGap)
                .addComponent(scrollPane)
        );
        scrollPane.setVisible(true);
        setLayout(layout);
    }

    @Override
    public void onTopBottom() {
        createTopBottomLayout();
    }

    @Override
    public void onCenter() {
        createCenterLayout();
    }

}
