package com.base.engine;

/**
 * @author Octogonapus
 */

public class Vector2f {
    private float x, y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the length of the vector.
     *
     * @return  The length of the vector
     */
    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Computes the dot product of this vector and another vector.
     *
     * @param r The other vector
     * @return  The dot product
     */
    public float dot(Vector2f r) {
        return x * r.getX() + y * r.getY();
    }

    /**
     * Normalizes this vector.
     *
     * @return  The normalized vector
     */
    public Vector2f normalize() {
        float length = length();
        x /= length;
        y /= length;

        return this;
    }

    /**
     * Rotates this vector.
     *
     * @param angle Angle to rotate
     * @return      Rotated vector
     */
    public Vector2f rotate(float angle) {
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
    }

    /**
     * Adds this vector and another vector.
     *
     * @param r The other vector
     * @return  The sum
     */
    public Vector2f add(Vector2f r) {
        return new Vector2f(x + r.getY(), y + r.getY());
    }

    /**
     * Adds a float to this vector.
     *
     * @param r The float to add
     * @return  The sum
     */
    public Vector2f add(float r) {
        return new Vector2f(x + r, y + r);
    }

    /**
     * Subtracts this vector and another vector.
     *
     * @param r The other vector
     * @return  The difference
     */
    public Vector2f sub(Vector2f r) {
        return new Vector2f(x - r.getY(), y - r.getY());
    }

    /**
     * Subtracts a float from this vector.
     *
     * @param r The float to add
     * @return  The difference
     */
    public Vector2f sub(float r) {
        return new Vector2f(x - r, y - r);
    }

    /**
     * Multiplies this vector and another vector.
     *
     * @param r The other vector
     * @return  The product
     */
    public Vector2f mul(Vector2f r) {
        return new Vector2f(x * r.getY(), y * r.getY());
    }

    /**
     * Multiplies a float by this vector.
     *
     * @param r The float to add
     * @return  The product
     */
    public Vector2f mul(float r) {
        return new Vector2f(x * r, y * r);
    }

    /**
     * Divides this vector and another vector.
     *
     * @param r The other vector
     * @return  The quotient
     */
    public Vector2f div(Vector2f r) {
        return new Vector2f(x / r.getY(), y / r.getY());
    }

    /**
     * Divides a float by this vector.
     *
     * @param r The float to add
     * @return  The quotient
     */
    public Vector2f div(float r) {
        return new Vector2f(x / r, y / r);
    }

    @Override
    public String toString() {
        return "Vector2f{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
