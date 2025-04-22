package com.myproject.physics;

import java.util.List;

/**
 * A utility class for rendering colliders to the console.
 * This class provides a simple way to log the positions of colliders
 * in the physics simulation for debugging or monitoring purposes.
 */
public class ConsoleRenderer {

    /**
     * Renders the list of colliders by printing their positions to the console.
     *
     * @param colliders The list of colliders to render.
     */
    public void render(List<Collider> colliders) {
        System.out.println("Rendering colliders:");
        for (Collider col : colliders) {
            System.out.println("Collider at " + col.getCenter());
        }
    }
}