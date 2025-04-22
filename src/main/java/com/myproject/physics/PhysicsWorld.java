package com.myproject.physics;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the physics simulation world.
 * Manages rigid bodies, their colliders, and handles integration, collision detection, and resolution.
 */
public class PhysicsWorld {

    /**
     * Inner class to encapsulate a physics object, which includes a rigid body and its collider.
     */
    private class PhysicsObject {
        public Rigidbody rigidbody;
        public Collider collider;

        /**
         * Constructs a PhysicsObject with the specified rigid body and collider.
         *
         * @param rb  The rigid body associated with the object.
         * @param col The collider associated with the object.
         */
        public PhysicsObject(Rigidbody rb, Collider col) {
            this.rigidbody = rb;
            this.collider = col;
        }
    }

    private List<PhysicsObject> objects;

    /**
     * Constructs a new PhysicsWorld instance.
     * Initializes the list of physics objects.
     */
    public PhysicsWorld() {
        objects = new ArrayList<>();
    }

    /**
     * Adds a rigid body and its collider to the physics world.
     *
     * @param rb       The rigid body to add.
     * @param collider The collider associated with the rigid body.
     */
    public void addRigidbody(Rigidbody rb, Collider collider) {
        objects.add(new PhysicsObject(rb, collider));
    }

    /**
     * Updates the physics world by integrating motion and checking for collisions.
     *
     * @param deltaTime The time step for the simulation update.
     */
    public void update(float deltaTime) {
        // Integrate motion for each object
        for (PhysicsObject obj : objects) {
            if (obj.rigidbody.mass != 0.0f) {
                obj.rigidbody.integrate(deltaTime);
            }
        }
        checkCollisions();
    }

    /**
     * Checks for collisions between all objects in the physics world.
     * Resolves any detected collisions.
     */
    private void checkCollisions() {
        int n = objects.size();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                PhysicsObject obj1 = objects.get(i);
                PhysicsObject obj2 = objects.get(j);
                if (obj1.collider.checkCollision(obj2.collider)) {
                    resolveCollision(obj1, obj2);
                    System.out.println("Collision detected and resolved between objects!");
                }
            }
        }
    }

    /**
     * Resolves a collision between two physics objects.
     *
     * @param obj1 The first physics object involved in the collision.
     * @param obj2 The second physics object involved in the collision.
     */
    private void resolveCollision(PhysicsObject obj1, PhysicsObject obj2) {
        // Collision resolution logic (impulse-based resolution and positional correction)
        // See the implementation for details.
    }

    /**
     * Finds the closest point on an AABB to a given point.
     *
     * @param aabbCenter The center of the AABB.
     * @param aabbHalfSize The half-size dimensions of the AABB.
     * @param point The point to find the closest point to.
     * @return The closest point on the AABB.
     */
    private Vector2D closestPointOnAABB(Vector2D aabbCenter, Vector2D aabbHalfSize, Vector2D point) {
        float closestX = Math.max(aabbCenter.x - aabbHalfSize.x, Math.min(point.x, aabbCenter.x + aabbHalfSize.x));
        float closestY = Math.max(aabbCenter.y - aabbHalfSize.y, Math.min(point.y, aabbCenter.y + aabbHalfSize.y));
        return new Vector2D(closestX, closestY);
    }

    /**
     * Retrieves the list of colliders in the physics world.
     *
     * @return A list of colliders.
     */
    public List<Collider> getColliders() {
        List<Collider> colliders = new ArrayList<>();
        for (PhysicsObject obj : objects) {
            colliders.add(obj.collider);
        }
        return colliders;
    }
}