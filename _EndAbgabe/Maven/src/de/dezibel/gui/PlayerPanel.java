package de.dezibel.gui;

import de.dezibel.data.Medium;
import de.dezibel.player.Player;
import de.dezibel.player.PlayerObserver;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * The Panel class for the music player.
 * @author Tobias, Richard
 */
public class PlayerPanel extends DragablePanel {

    private final Player player;

    private GroupLayout layout;
    private JLabel lblCover;
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
    private int selectedRow = -1;

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

        // Cover-Panel
        lblCover = new JLabel();

        // Add title label
        lblTitle = new JLabel();

        // Add seeker
        lblElapsedTime = new JLabel();
        slider = new JSlider(0, 1000, 0);
        lblTimeLeft = new JLabel();
        slider.setBackground(DezibelColor.SliderBackground);
        // Add Buttons und volume slider
        btnPrev = new JButton(new ImageIcon(this.getClass().getResource("/img/icons/prev.png")));
        btnPrev.setOpaque(false);
        btnPrev.setBorderPainted(false);
        btnPrev.setContentAreaFilled(false);
        btnPrev.setFocusable(false);
        btnPlayPause = new JButton(new ImageIcon(this.getClass().getResource("/img/icons/play.png")));
        btnPlayPause.setOpaque(false);
        btnPlayPause.setBorderPainted(false);
        btnPlayPause.setContentAreaFilled(false);
        btnPlayPause.setFocusable(false);
        btnStop = new JButton(new ImageIcon(this.getClass().getResource("/img/icons/stop.png")));
        btnStop.setOpaque(false);
        btnStop.setBorderPainted(false);
        btnStop.setContentAreaFilled(false);
        btnStop.setFocusable(false);
        btnNext = new JButton(new ImageIcon(this.getClass().getResource("/img/icons/next.png")));
        btnNext.setOpaque(false);
        btnNext.setBorderPainted(false);
        btnNext.setContentAreaFilled(false);
        btnNext.setFocusable(false);
        volume = new JSlider(JSlider.VERTICAL, 0, 100, 50);

        // Add logo
        lblCover.setIcon(new ImageIcon(this.getClass().getResource("/img/mini-logo.png")));

        // Playlist
        mediaTableModel = new MediaTableModel();
        tablePlaylist = new JTable(mediaTableModel);
        // Save selection even after table update
        tablePlaylist.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedRow = e.getFirstIndex();
            }
        });
        // Restore selected raw table
        mediaTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if (selectedRow >= 0 && selectedRow < mediaTableModel.getRowCount()) {
                            tablePlaylist.requestFocus();
                            tablePlaylist.changeSelection(selectedRow, selectedRow, false, false);
                        } else {
                            tablePlaylist.clearSelection();
                        }
                    }
                });
            }
        });
        tablePlaylist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePlaylist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    Point p = me.getPoint();
                    int rowNumber = tablePlaylist.rowAtPoint(p);
                    player.setCurrentMedia(rowNumber);
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
                JPopupMenu currentPopupMenu = contextMenu.getContextMenu(tablePlaylist, me);
                currentPopupMenu.show(me.getComponent(), me.getX(), me.getY());
            }
        });
        // Renderer that shows the currently playing song
        tablePlaylist.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            UIDefaults defaults = javax.swing.UIManager.getDefaults();

            public Component getTableCellRendererComponent(
                    JTable table, Object color,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {
                super.getTableCellRendererComponent(table, color, isSelected, hasFocus, row, column);
                if (table.getSelectedRow() == row) {
                    if (player.getCurrentIndex() == row) {
                        this.setBackground(new Color(123, 223, 153));
                        this.setForeground(Color.BLACK);
                    } else {
                        this.setBackground(defaults.getColor("List.selectionBackground"));
                        this.setForeground(defaults.getColor("List.selectionForeground"));
                    }
                } else {
                    if (player.getCurrentIndex() == row) {
                        this.setBackground(Color.GREEN.brighter().brighter());
                        this.setForeground(Color.BLACK);
                    } else {
                        this.setBackground(Color.WHITE);
                        this.setForeground(Color.BLACK);
                    }
                }
                return this;
            }
        });
        tablePlaylist.setDefaultRenderer(Date.class, new DefaultTableCellRenderer() {
            private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
            UIDefaults defaults = javax.swing.UIManager.getDefaults();

            public Component getTableCellRendererComponent(
                    JTable table, Object color,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {
                if (table.getSelectedRow() == row) {
                    if (player.getCurrentIndex() == row) {
                        this.setBackground(new Color(123, 223, 153));
                        this.setForeground(Color.BLACK);
                    } else {
                        this.setBackground(defaults.getColor("List.selectionBackground"));
                        this.setForeground(defaults.getColor("List.selectionForeground"));
                    }
                } else {
                    if (player.getCurrentIndex() == row) {
                        this.setBackground(Color.GREEN.brighter().brighter());
                        this.setForeground(Color.BLACK);
                    } else {
                        this.setBackground(Color.WHITE);
                        this.setForeground(Color.BLACK);
                    }
                }
                if (table.getModel().getValueAt(row, column) == null) {
                    this.setText("");
                } else {
                    this.setText(dateFormatter.format(table.getModel().getValueAt(row, column)));
                }
                return this;
            }
        });
        tablePlaylist.setDefaultRenderer(Double.class, new DefaultTableCellRenderer() {
            UIDefaults defaults = javax.swing.UIManager.getDefaults();

            public Component getTableCellRendererComponent(
                    JTable table, Object color,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {
                super.getTableCellRendererComponent(table, color, isSelected, hasFocus, row, column);
                if (table.getSelectedRow() == row) {
                    if (player.getCurrentIndex() == row) {
                        this.setBackground(new Color(123, 223, 153));
                        this.setForeground(Color.BLACK);
                    } else {
                        this.setBackground(defaults.getColor("List.selectionBackground"));
                        this.setForeground(defaults.getColor("List.selectionForeground"));
                    }
                } else {
                    if (player.getCurrentIndex() == row) {
                        this.setBackground(Color.GREEN.brighter().brighter());
                        this.setForeground(Color.BLACK);
                    } else {
                        this.setBackground(Color.WHITE);
                        this.setForeground(Color.BLACK);
                    }
                }
                this.setHorizontalAlignment(SwingConstants.RIGHT);
                return this;
            }
        });
        // Listener for reordering the playlist via keys
        tablePlaylist.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if (tablePlaylist.getSelectedRow() != -1) {
                    if (ke.getKeyCode() == KeyEvent.VK_UP) {
                        player.moveUp(tablePlaylist.getSelectedRow());
                    } else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                        player.moveDown(tablePlaylist.getSelectedRow());
                    }
                }
            }
        });
        scrollPane = new JScrollPane(tablePlaylist);
        scrollPane.setBackground(DezibelColor.Background);
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
                if (player.getCurrentMedium() != null) {
                    if (player.isPlaying()) {
                        player.pause();
                        btnPlayPause.setIcon(new ImageIcon(this.getClass().getResource("/img/icons/play.png")));
                    } else {
                        player.play();
                        btnPlayPause.setIcon(new ImageIcon(this.getClass().getResource("/img/icons/pause.png")));
                    }
                }
            }
        });
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.stop();
                btnPlayPause.setIcon(new ImageIcon(this.getClass().getResource("/img/icons/play.png")));
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
                if (newMedium != null) {
                    if (newMedium.getAlbum() != null) {
                        Image img = newMedium.getAlbum().getCover();
                        if (img != null) {
                            lblCover.setIcon(new ImageIcon(img));
                        } else {
                            lblCover.setIcon(new ImageIcon(this.getClass().getResource("/img/mini-logo.png")));
                        }
                    } else {
                        lblCover.setIcon(new ImageIcon(this.getClass().getResource("/img/mini-logo.png")));
                    }
                    if (newMedium.isLocked()) {
                        lblTitle.setText("Medium gesperrt");
                        lblCover.setIcon(new ImageIcon(this.getClass().getResource("/img/medium_locked.png")));
                    } else if (newMedium.isDeleted()) {
                        lblTitle.setText("Gelöscht: " + newMedium.getArtist().getPseudonym() + " - "
                                + newMedium.getTitle());
                    } else {
                        lblTitle.setText(newMedium.getArtist().getPseudonym() + " - "
                                + newMedium.getTitle());
                    }
                    volume.setValue(player.getVolume());
                    mediaTableModel.setData(player.getPlaylist());
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            if (player.isPlaying()) {
                                btnPlayPause.setIcon(new ImageIcon(this.getClass().getResource("/img/icons/pause.png")));
                            } else {
                                btnPlayPause.setIcon(new ImageIcon(this.getClass().getResource("/img/icons/play.png")));
                            }
                        }
                    });
                }
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
                        .addGap(minHGap, prefHGap, maxHGap)
                        .addComponent(lblCover, 128, 128, 128)
                        .addGap(minHGap, prefHGap, maxHGap)
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
                .addGap(10, prefVGap, maxVGap)
                .addComponent(lblCover, 128, 128, 128)
                .addGap(minVGap, prefVGap, maxVGap)
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
        // Reset table selection
        selectedRow = -1;
        tablePlaylist.clearSelection();

        int minHGap = 0, prefHGap = 20, maxHGap = 20;
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(minHGap, prefHGap, maxHGap)
                        .addComponent(lblCover, 128, 128, 128)
                        .addGap(minHGap, prefHGap, maxHGap)
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
                        .addGap(minVGap, prefVGap, maxVGap)
                        .addComponent(lblCover, 128, 128, 128)
                        .addGap(minVGap, prefVGap, maxVGap)
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

    @Override
    public void reset() {
        this.player.stop();
        this.player.clearPlaylist();
        btnPlayPause.setIcon(new ImageIcon(this.getClass().getResource("/img/icons/play.png")));
        this.lblTitle.setText("");
        this.lblElapsedTime.setText("");
        this.lblTimeLeft.setText("");
        this.mediaTableModel.setData(null);
        this.lblCover.setIcon(new ImageIcon(this.getClass().getResource("/img/mini-logo.png")));
        this.slider.setValue(0);
    }

    @Override
    public void refresh() {
        // unused
    }
    
    @Override
    public void onExternalized() {
        createCenterLayout();
        this.setPreferredSize(new Dimension(600, 350));
    }

}
