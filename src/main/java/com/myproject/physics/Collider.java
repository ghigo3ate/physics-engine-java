package com.myproject.physics;

/**
 * Represents a collider used for collision detection in a 2D physics simulation.
 * Supports two types of colliders: Circle and Axis-Aligned Bounding Box (AABB).
 */
public class Collider {

    /**
     * Enum representing the type of the collider.
     */
    public enum Type {
        CIRCLE, AABB
    }

    private Type type; // The type of the collider (CIRCLE or AABB).
    private Vector2D center; // The center position of the collider.
    private float radius; // The radius of the collider (used for CIRCLE type only).
    private Vector2D halfSize; // The half-size dimensions of the collider (used for AABB type only).

    /**
     * Constructs a collider with the specified type, center, radius, and half-size.
     *
     * @param type     The type of the collider (CIRCLE or AABB).
     * @param center   The center position of the collider.
     * @param radius   The radius of the collider (used for CIRCLE type).
     * @param halfSize The half-size dimensions of the collider (used for AABB type).
     */
    public Collider(Type type, Vector2D center, float radius, Vector2D halfSize) {
        this.type = type;
        this.center = center;
        this.radius = radius;
        this.halfSize = halfSize;
    }

    /**
     * Constructs a circle collider with the specified type, center, and radius.
     *
     * @param type   The type of the collider (CIRCLE).
     * @param center The center position of the collider.
     * @param radius The radius of the collider.
     */
    public Collider(Type type, Vector2D center, float radius) {
        this(type, center, radius, new Vector2D(0, 0));
    }

    /**
     * Constructs an AABB collider with the specified type, center, and half-size.
     *
     * @param type     The type of the collider (AABB).
     * @param center   The center position of the collider.
     * @param halfSize The half-size dimensions of the collider.
     */
    public Collider(Type type, Vector2D center, Vector2D halfSize) {
        this(type, center, 0.0f, halfSize);
    }

    /**
     * Gets the type of the collider.
     *
     * @return The type of the collider (CIRCLE or AABB).
     */
    public Type getType() {
        return type;
    }

    /**
     * Gets the center position of the collider.
     *
     * @return The center position of the collider.
     */
    public Vector2D getCenter() {
        return center;
    }

    /**
     * Gets the radius of the collider.
     *
     * @return The radius of the collider (used for CIRCLE type).
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Gets the half-size dimensions of the collider.
     *
     * @return The half-size dimensions of the collider (used for AABB type).
     */
    public Vector2D getHalfSize() {
        return halfSize;
    }

    /**
     * Sets the center position of the collider.
     *
     * @param newCenter The new center position of the collider.
     */
    public void setCenter(Vector2D newCenter) {
        this.center = newCenter;
    }

    /**
     * Checks for a collision between this collider and another collider.
     *
     * @param other The other collider to check for collision.
     * @return True if a collision is detected, false otherwise.
     */
    public boolean checkCollision(Collider other) {
        if (this.type == Type.CIRCLE && other.type == Type.CIRCLE) {
            return checkCircleCircleCollision(other);
        } else if (this.type == Type.AABB && other.type == Type.AABB) {
            return checkAABBAABBCollision(other);
        } else if (this.type == Type.CIRCLE && other.type == Type.AABB) {
            return checkCircleAABBCollision(other);
        } else if (this.type == Type.AABB && other.type == Type.CIRCLE) {
            return other.checkCircleAABBCollision(this);
        }
        return false;
    }

    /**
     * Checks for a collision between two circle colliders.
     *
     * @param other The other circle collider.
     * @return True if a collision is detected, false otherwise.
     */
    private boolean checkCircleCircleCollision(Collider other) {
        Vector2D diff = other.center.subtract(this.center);
        float distanceSquared = diff.dot(diff);
        float radiusSum = this.radius + other.radius;
        return distanceSquared <= radiusSum * radiusSum;
    }

    /**
     * Checks for a collision between two AABB colliders.
     *
     * @param other The other AABB collider.
     * @return True if a collision is detected, false otherwise.
     */
    private boolean checkAABBAABBCollision(Collider other) {
        return !(this.center.x + this.halfSize.x < other.center.x - other.halfSize.x ||
                this.center.x - this.halfSize.x > other.center.x + other.halfSize.x ||
                this.center.y + this.halfSize.y < other.center.y - other.halfSize.y ||
                this.center.y - this.halfSize.y > other.center.y + other.halfSize.y);
    }

    /**
     * Checks for a collision between a circle collider and an AABB collider.
     *
     * @param aabb The AABB collider.
     * @return True if a collision is detected, false otherwise.
     */
    private boolean checkCircleAABBCollision(Collider aabb) {
        float closestX = Math.max(
                aabb.getCenter().x - aabb.getHalfSize().x,
                Math.min(this.center.x, aabb.getCenter().x + aabb.getHalfSize().x)
        );
        float closestY = Math.max(
                aabb.getCenter().y - aabb.getHalfSize().y,
                Math.min(this.center.y, aabb.getCenter().y + aabb.getHalfSize().y)
        );

        float distanceX = this.center.x - closestX;
        float distanceY = this.center.y - closestY;

        return (distanceX * distanceX + distanceY * distanceY)
                < (this.radius * this.radius);
    }
}