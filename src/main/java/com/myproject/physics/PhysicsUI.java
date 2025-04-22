package com.myproject.physics;

import javax.swing.*;
import java.awt.*;

/**
 * The entry point for the 2D Physics Engine application.
 * This class initializes the Swing-based user interface and displays the simulation panel.
 */
public class PhysicsUI {

    /**
     * The main method to launch the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create the main application window
                JFrame frame = new JFrame("2D Physics Engine Visualization");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1000, 1000);

                // Add the PhysicsPanel to the frame
                PhysicsPanel panel = new PhysicsPanel();
                frame.add(panel, BorderLayout.CENTER);

                // Make the frame visible
                frame.setVisible(true);
            }
        });
    }
}