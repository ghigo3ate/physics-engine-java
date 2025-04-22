package com.myproject.physics;

/**
 * Represents a 2D vector with basic operations for vector arithmetic.
 * This class is used for handling positions, velocities, forces, and other
 * vector-based calculations in the physics simulation.
 */
public class Vector2D {
    public float x; // The x-component of the vector.
    public float y; // The y-component of the vector.

    /**
     * Constructs a new Vector2D with the specified x and y components.
     *
     * @param x The x-component of the vector.
     * @param y The y-component of the vector.
     */
    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a new Vector2D initialized to (0, 0).
     */
    public Vector2D() {
        this(0, 0);
    }

    /**
     * Adds this vector to another vector and returns the result.
     *
     * @param v The vector to add.
     * @return A new Vector2D representing the sum of the two vectors.
     */
    public Vector2D add(Vector2D v) {
        return new Vector2D(this.x + v.x, this.y + v.y);
    }

    /**
     * Subtracts another vector from this vector and returns the result.
     *
     * @param v The vector to subtract.
     * @return A new Vector2D representing the difference of the two vectors.
     */
    public Vector2D subtract(Vector2D v) {
        return new Vector2D(this.x - v.x, this.y - v.y);
    }

    /**
     * Multiplies this vector by a scalar and returns the result.
     *
     * @param scalar The scalar to multiply by.
     * @return A new Vector2D representing the scaled vector.
     */
    public Vector2D multiply(float scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    /**
     * Computes the dot product of this vector and another vector.
     *
     * @param v The vector to compute the dot product with.
     * @return The dot product of the two vectors.
     */
    public float dot(Vector2D v) {
        return this.x * v.x + this.y * v.y;
    }

    /**
     * Calculates the magnitude (length) of this vector.
     *
     * @return The magnitude of the vector.
     */
    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Normalizes this vector to a unit vector (magnitude of 1).
     * If the vector has zero magnitude, it remains unchanged.
     */
    public void normalize() {
        float mag = magnitude();
        if (mag > 0) {
            this.x /= mag;
            this.y /= mag;
        }
    }

    /**
     * Returns a string representation of this vector in the format "(x, y)".
     *
     * @return A string representation of the vector.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}