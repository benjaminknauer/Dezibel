package de.dezibel.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

    /**
     * Test
     *
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new PlayerPanel());
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Constructor
     */
    public PlayerPanel() {
        super();
        init();
    }

    /**
     * Initiates all components of the PlayerPanel
     */
    private void init() {
        setLayout(new BorderLayout());

        // Add title label
        JLabel lblTitle = new JLabel();
        add(lblTitle, BorderLayout.NORTH);

        // Add seeker
        JPanel seeker = new JPanel();
        seeker.setLayout(new FlowLayout());

        JLabel lblElapsedTime = new JLabel();
        seeker.add(lblElapsedTime);

        JSlider slider = new JSlider();
        seeker.add(slider);

        JLabel lblTimeLeft = new JLabel();
        seeker.add(lblTimeLeft);
        add(seeker, BorderLayout.CENTER);

        // Add Buttons und volume slider
        JPanel buttons = new JPanel();
        JButton btnPrev = new JButton("prev");
        JButton btnPlayPause = new JButton("play");
        JButton btnStop = new JButton("stop");
        JButton btnNext = new JButton("next");
        JLabel lblVolume = new JLabel("Volume");
        JSlider volume = new JSlider(0, 100);
        GroupLayout layout = new GroupLayout(buttons);
        layout.setAutoCreateGaps(true);
        buttons.setLayout(layout);
        
        int buttonWidth = 80;
        int buttonHeight = 20;
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                .addComponent(btnPrev, buttonWidth, buttonWidth, buttonWidth)
                .addComponent(btnPlayPause, buttonWidth, buttonWidth, buttonWidth)
                .addComponent(btnStop, buttonWidth, buttonWidth, buttonWidth)
                .addComponent(btnNext, buttonWidth, buttonWidth, buttonWidth)
                .addComponent(lblVolume, 80, 80, 80)
                .addComponent(volume, 10, 50, 100)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                .addComponent(btnPrev, buttonHeight, buttonHeight, buttonHeight)
                .addComponent(btnPlayPause, buttonHeight, buttonHeight, buttonHeight)
                .addComponent(btnStop, buttonHeight, buttonHeight, buttonHeight)
                .addComponent(btnNext, buttonHeight, buttonHeight, buttonHeight)
                .addComponent(lblVolume, buttonHeight, buttonHeight, buttonHeight)
                .addComponent(volume, buttonHeight, buttonHeight, buttonHeight)
        );

        add(buttons, BorderLayout.SOUTH);
    }

}
