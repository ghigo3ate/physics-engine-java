/*
 * Updated PhysicsPanel.java: supports variable number of balls, pause/resume, and custom ball color.
 */
package com.myproject.physics;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.geom.AffineTransform;
import java.awt.event.ActionListener;

public class PhysicsPanel extends JPanel {
    private PhysicsWorld world;
    private Timer timer;
    private final float deltaTime = 0.016f; // ~60 FPS
    private final float scale = 200.0f;
    private List<Rigidbody> bodies;
    private List<Collider> colliders;
    private Color ballColor;

    /**
     * Constructs a PhysicsPanel with the specified number of balls and color.
     */
    public PhysicsPanel(int numBalls, Color ballColor) {
        this.ballColor = ballColor;
        world = new PhysicsWorld();
        bodies = new ArrayList<>();
        colliders = new ArrayList<>();
        Random rand = new Random();

        // Create dynamic balls centered around origin
        for (int i = 0; i < numBalls; i++) {
            Rigidbody rb = new Rigidbody(1.0f);
            float x = rand.nextFloat() - 0.5f;
            float y = rand.nextFloat() - 0.5f;
            rb.position = new Vector2D(x, y);
            rb.velocity = new Vector2D(rand.nextFloat() * 10 - 5, rand.nextFloat() * 10 - 5);
            Collider col = new Collider(Collider.Type.CIRCLE, new Vector2D(x, y), 0.1f);

            world.addRigidbody(rb, col);
            bodies.add(rb);
            colliders.add(col);
        }

        // Create static ground at center bottom
        Rigidbody rbGround = new Rigidbody(0.0f);
        rbGround.position = new Vector2D(0, 0);
        Collider colGround = new Collider(Collider.Type.AABB, new Vector2D(0, 0), new Vector2D(2, 0.5f));
        world.addRigidbody(rbGround, colGround);
        bodies.add(rbGround);
        colliders.add(colGround);

        // Prepare timer but do NOT start until UI triggers it
        ActionListener step = e -> {
            world.update(deltaTime);
            for (int i = 0; i < bodies.size(); i++) {
                colliders.get(i).setCenter(bodies.get(i).position);
            }
            repaint();
        };
        timer = new Timer((int)(deltaTime * 1000), step);
    }

    /**
     * Starts or restarts the simulation.
     */
    public void startSimulation() {
        if (timer.isRunning()) {
            timer.stop();
        }
        timer.start();
    }

    /**
     * Pauses the simulation.
     */
    public void pauseSimulation() {
        if (timer.isRunning()) {
            timer.stop();
        }
    }

    /**
     * Resumes the simulation.
     */
    public void resumeSimulation() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Clear background
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Draw all colliders
        for (Collider col : colliders) {
            if (col.getType() == Collider.Type.CIRCLE) {
                g2d.setColor(ballColor);
                drawCircle(g2d, col);
            } else {
                g2d.setColor(Color.RED);
                drawAABB(g2d, col);
            }
        }
    }

    private Vector2D simulationToScreen(Vector2D pos) {
        int width = getWidth();
        int height = getHeight();
        float screenX = width / 2 + pos.x * scale;
        float screenY = height / 2 - pos.y * scale;
        return new Vector2D(screenX, screenY);
    }

    private void drawCircle(Graphics2D g2d, Collider collider) {
        Vector2D center = simulationToScreen(collider.getCenter());
        int radius = (int)(collider.getRadius() * scale);
        g2d.fillOval((int)(center.x - radius), (int)(center.y - radius), 2 * radius, 2 * radius);
    }

    private void drawAABB(Graphics2D g2d, Collider collider) {
        Stroke oldStroke = g2d.getStroke();
        AffineTransform oldTx = g2d.getTransform();
        Vector2D screenCenter = simulationToScreen(collider.getCenter());
        g2d.translate(screenCenter.x, screenCenter.y);
        double fullWidth  = collider.getHalfSize().x * 1.05 * scale;
        double fullHeight = collider.getHalfSize().y * 1.2 * scale;
        g2d.scale(fullWidth, fullHeight);
        g2d.setStroke(new BasicStroke((float) (10 / Math.max(fullWidth, fullHeight))));
        g2d.drawRect(-1, -1, 2, 2);
        g2d.setStroke(oldStroke);
        g2d.setTransform(oldTx);
    }
}
