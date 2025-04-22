package com.myproject.physics;

/**
 * Represents a rigid body in the physics simulation.
 * Handles position, velocity, forces, and mass for motion integration.
 */
public class Rigidbody {
    public Vector2D position; // The position of the rigid body.
    public Vector2D velocity; // The velocity of the rigid body.
    public Vector2D forceAccum; // Accumulated forces acting on the rigid body.
    public float mass; // The mass of the rigid body (0 for static objects).

    /**
     * Constructs a new Rigidbody with the specified mass.
     * Initializes position, velocity, and force accumulator to zero.
     *
     * @param mass The mass of the rigid body. Use 0 for static objects.
     */
    public Rigidbody(float mass) {
        this.mass = mass;
        this.position = new Vector2D(0, 0);
        this.velocity = new Vector2D(0, 0);
        this.forceAccum = new Vector2D(0, 0);
    }

    /**
     * Applies a force to the rigid body.
     * The force is added to the accumulated forces for the current simulation step.
     *
     * @param force The force to apply, represented as a 2D vector.
     */
    public void applyForce(Vector2D force) {
        this.forceAccum = this.forceAccum.add(force);
    }

    /**
     * Integrates the motion of the rigid body over a given time step.
     * Updates position and velocity based on accumulated forces and mass.
     * Resets the force accumulator after integration.
     *
     * @param duration The time step for the integration, in seconds.
     */
    public void integrate(float duration) {
        if (mass <= 0.0f) return; // Static objects do not move

        Vector2D acceleration = forceAccum.multiply(1.0f / mass);
        velocity = velocity.add(acceleration.multiply(duration));
        position = position.add(velocity.multiply(duration));
        forceAccum = new Vector2D(0, 0);
    }
}