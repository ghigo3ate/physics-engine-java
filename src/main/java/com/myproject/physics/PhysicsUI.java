package com.myproject.physics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicReference;

public class PhysicsUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("2D Physics Engine Visualization");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 1000);
            frame.setLayout(new BorderLayout());

            // Control panel
            JPanel controlPanel = new JPanel();
            controlPanel.add(new JLabel("Number of Balls:"));
            JTextField numField = new JTextField("2", 5);
            controlPanel.add(numField);

            JButton startButton = new JButton("Start");
            controlPanel.add(startButton);

            JToggleButton pauseButton = new JToggleButton("Pause");
            pauseButton.setEnabled(false);
            controlPanel.add(pauseButton);

            JButton colorButton = new JButton("Color");
            controlPanel.add(colorButton);

            frame.add(controlPanel, BorderLayout.NORTH);

            // Holders for panel and color
            AtomicReference<PhysicsPanel> panelRef = new AtomicReference<>();
            AtomicReference<Color> colorRef = new AtomicReference<>(Color.BLUE);

            // Color chooser
            colorButton.addActionListener(e -> {
                Color chosen = JColorChooser.showDialog(frame, "Choose Ball Color", colorRef.get());
                if (chosen != null) colorRef.set(chosen);
            });

            // Action: start simulation
            ActionListener startSim = e -> {
                int num;
                try {
                    num = Integer.parseInt(numField.getText());
                } catch (NumberFormatException ex) {
                    num = 2;
                }
                PhysicsPanel old = panelRef.get();
                if (old != null) {
                    frame.remove(old);
                }
                PhysicsPanel newPanel = new PhysicsPanel(num, colorRef.get());
                panelRef.set(newPanel);
                frame.add(newPanel, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                newPanel.startSimulation();

                pauseButton.setEnabled(true);
                pauseButton.setSelected(false);
                pauseButton.setText("Pause");
            };
            startButton.addActionListener(startSim);

            // Action: toggle pause/resume
            ActionListener togglePause = e -> {
                PhysicsPanel panel = panelRef.get();
                if (panel == null) return;
                if (pauseButton.isSelected()) {
                    panel.pauseSimulation();
                    pauseButton.setText("Resume");
                } else {
                    panel.resumeSimulation();
                    pauseButton.setText("Pause");
                }
            };
            pauseButton.addActionListener(togglePause);

            // Key bindings
            JRootPane root = frame.getRootPane();
            InputMap im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap am = root.getActionMap();

            // Enter -> start
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "startSim");
            am.put("startSim", new AbstractAction() {
                @Override public void actionPerformed(ActionEvent e) {
                    startSim.actionPerformed(e);
                }
            });

            // Alt+S -> pause/resume
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_DOWN_MASK), "togglePause");
            am.put("togglePause", new AbstractAction() {
                @Override public void actionPerformed(ActionEvent e) {
                    // toggle the button model state before calling
                    pauseButton.setSelected(!pauseButton.isSelected());
                    togglePause.actionPerformed(e);
                }
            });

            frame.setVisible(true);
        });
    }
}