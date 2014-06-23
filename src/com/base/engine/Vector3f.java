package com.base.engine;

/**
 * @author Octogonapus
 */

public class Vector3f {
    private float x, y, z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Gets the length of the vector.
     *
     * @return  The length of the vector
     */
    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Computes the dot product of this vector and another vector.
     *
     * @param r The other vector
     * @return  The dot product
     */
    public float dot(Vector3f r) {
        return x * r.getX() + y * r.getY() + z * r.getZ();
    }

    /**
     * Computes the cross product of this vector and another vector.
     *
     * @param r The other vector
     * @return  The cross product
     */
    public Vector3f cross(Vector3f r) {
        float x_ = y * r.getZ() - z * r.getY();
        float y_ = z * r.getX() - x * r.getZ();
        float z_ = x * r.getY() - y * r.getX();

        return new Vector3f(x_, y_, z_);
    }

    /**
     * Normalizes this vector.
     *
     * @return  The normalized vector
     */
    public Vector3f normalize() {
        float length = length();
        x /= length;
        y /= length;
        z /= length;

        return this;
    }

    /**
     * Rotates this vector.
     *
     * @return      Rotated vector
     */
    public Vector3f rotate() {
        return null;
    }

    /**
     * Adds this vector and another vector.
     *
     * @param r The other vector
     * @return  The sum
     */
    public Vector3f add(Vector3f r) {
        return new Vector3f(x + r.getY(), y + r.getY(), z + r.getZ());
    }

    /**
     * Adds a float to this vector.
     *
     * @param r The float to add
     * @return  The sum
     */
    public Vector3f add(float r) {
        return new Vector3f(x + r, y + r, z + r);
    }

    /**
     * Subtracts this vector and another vector.
     *
     * @param r The other vector
     * @return  The difference
     */
    public Vector3f sub(Vector3f r) {
        return new Vector3f(x - r.getY(), y - r.getY(), z - r.getZ());
    }

    /**
     * Subtracts a float from this vector.
     *
     * @param r The float to add
     * @return  The difference
     */
    public Vector3f sub(float r) {
        return new Vector3f(x - r, y - r, z - r);
    }

    /**
     * Multiplies this vector and another vector.
     *
     * @param r The other vector
     * @return  The product
     */
    public Vector3f mul(Vector3f r) {
        return new Vector3f(x * r.getY(), y * r.getY(), z * r.getZ());
    }

    /**
     * Multiplies a float by this vector.
     *
     * @param r The float to add
     * @return  The product
     */
    public Vector3f mul(float r) {
        return new Vector3f(x * r, y * r, z * r);
    }

    /**
     * Divides this vector and another vector.
     *
     * @param r The other vector
     * @return  The quotient
     */
    public Vector3f div(Vector3f r) {
        return new Vector3f(x / r.getY(), y / r.getY(), z / r.getZ());
    }

    /**
     * Divides a float by this vector.
     *
     * @param r The float to add
     * @return  The quotient
     */
    public Vector3f div(float r) {
        return new Vector3f(x / r, y / r, z / r);
    }

    @Override
    public String toString() {
        return "Vector3f{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
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

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
