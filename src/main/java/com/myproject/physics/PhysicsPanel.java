package com.myproject.physics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.BasicStroke;
import java.awt.Stroke;

/**
 * A Swing-based panel for visualizing the 2D physics simulation.
 * This class handles rendering of physics objects and updating their states in real-time.
 */
public class PhysicsPanel extends JPanel {

    private PhysicsWorld world;
    private Rigidbody rb1, rb2, rbGround;
    private Collider col1, col2, colGround;
    private Timer timer;
    private final float deltaTime = 0.016f; // Approximately 60 FPS
    private final float scale = 200.0f;     // Scale factor for simulation-to-screen conversion

    /**
     * Constructs a new PhysicsPanel and initializes the physics world and objects.
     * Starts a timer to update the simulation and repaint the panel at ~60 FPS.
     */
    public PhysicsPanel() {
        // Initialize physics world and objects
        world = new PhysicsWorld();

        rb1 = new Rigidbody(1.0f);
        rb1.position = new Vector2D(-0.5f, 0.05f);
        rb1.velocity = new Vector2D(2, 0);
        col1 = new Collider(Collider.Type.CIRCLE, new Vector2D(-0.5f, -0.05f), 0.1f);

        rb2 = new Rigidbody(1.0f);
        rb2.position = new Vector2D(0.5f, -0.05f);
        rb2.velocity = new Vector2D(-2, 0);
        col2 = new Collider(Collider.Type.CIRCLE, new Vector2D(0.5f, 0.05f), 0.1f);

        world.addRigidbody(rb1, col1);
        world.addRigidbody(rb2, col2);

        rbGround = new Rigidbody(0.0f); // Static ground object
        rbGround.position = new Vector2D(0, 0);
        colGround = new Collider(Collider.Type.AABB, new Vector2D(0, 0), new Vector2D(2, 0.5f));
        world.addRigidbody(rbGround, colGround);

        // Start a Swing timer to update simulation and repaint at ~60 FPS
        timer = new Timer(16, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                world.update(deltaTime);
                // Update collider positions to match rigidbody positions
                col1.setCenter(rb1.position);
                col2.setCenter(rb2.position);
                colGround.setCenter(rbGround.position);

                repaint();
            }
        });
        timer.start();
    }

    /**
     * Converts simulation coordinates to screen coordinates.
     *
     * @param pos The position in simulation coordinates.
     * @return The position in screen coordinates.
     */
    private Vector2D simulationToScreen(Vector2D pos) {
        int width = getWidth();
        int height = getHeight();
        float screenX = width / 2 + pos.x * scale;
        float screenY = height / 2 - pos.y * scale; // Invert Y axis for screen
        return new Vector2D(screenX, screenY);
    }

    /**
     * Paints the physics objects onto the panel.
     *
     * @param g The Graphics object used for rendering.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Enable anti-aliasing for smooth rendering
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Clear background
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Draw dynamic circle objects (blue)
        g2d.setColor(Color.BLUE);
        drawCircle(g2d, col1);
        drawCircle(g2d, col2);

        // Draw ground (red rectangle)
        g2d.setColor(Color.RED);
        drawAABB(g2d, colGround);
    }

    /**
     * Draws a circle collider on the panel.
     *
     * @param g2d      The Graphics2D object used for rendering.
     * @param collider The circle collider to draw.
     */
    private void drawCircle(Graphics2D g2d, Collider collider) {
        Vector2D center = simulationToScreen(collider.getCenter());
        int radius = (int)(collider.getRadius() * scale);
        g2d.drawOval((int)(center.x - radius), (int)(center.y - radius), 2 * radius, 2 * radius);
    }

    /**
     * Draws an AABB collider on the panel.
     *
     * @param g2d      The Graphics2D object used for rendering.
     * @param collider The AABB collider to draw.
     */
    private void drawAABB(Graphics2D g2d, Collider collider) {
        // Save original stroke & transform
        Stroke oldStroke = g2d.getStroke();
        AffineTransform oldTx = g2d.getTransform();

        // Convert simulation center → screen coordinates
        Vector2D screenCenter = simulationToScreen(collider.getCenter());

        // 1) Translate to the rectangle's center
        g2d.translate(screenCenter.x, screenCenter.y);

        // 2) Scale coordinate system by the half‑sizes * 2 (to get full width/height)
        double fullWidth  = collider.getHalfSize().x * 1.05 * scale;
        double fullHeight = collider.getHalfSize().y * 1.2 * scale;
        g2d.scale(fullWidth, fullHeight);

        // 3) Make the stroke thicker (in device space, e.g. 10 pixels)
        g2d.setStroke(new BasicStroke((float) (10 / Math.max(fullWidth, fullHeight))));

        // 4) Draw a unit rect from (–0.5, –0.5) to (+0.5, +0.5)
        //    which, under our scale, becomes exactly the desired box.
        g2d.drawRect(-1, -1, 2, 2);

        // Restore stroke & transform
        g2d.setStroke(oldStroke);
        g2d.setTransform(oldTx);
    }
}