package com.base.engine;

import org.lwjgl.input.Keyboard;

/**
 * @author Octogonapus
 */

public class Camera {
    public static final Vector3f yAxis = new Vector3f(0, 1, 0);
    private Vector3f pos, forward, up;

    public Camera() {
        this(new Vector3f(0, 0, 0), new Vector3f(0, 0, 1), new Vector3f(0, 1, 0));
    }

    public Camera(Vector3f pos, Vector3f forward, Vector3f up) {
        this.pos = pos;
        this.forward = forward;
        this.up = up;

        up.normalize();
        forward.normalize();
    }

    public void input() {
        float moveAmt = (float) (10 * Time.getDelta());
        float rotationAmt = (float) (100 * Time.getDelta());

        if (Input.getKey(Keyboard.KEY_W)) {
            move(getForward(), moveAmt);
        }
        if (Input.getKey(Keyboard.KEY_S)) {
            move(getForward(), -moveAmt);
        }
        if (Input.getKey(Keyboard.KEY_A)) {
            move(getLeft(), moveAmt);
        }
        if (Input.getKey(Keyboard.KEY_D)) {
            move(getRight(), moveAmt);
        }

        if (Input.getKey(Keyboard.KEY_UP)) {
            rotateX(-rotationAmt);
        }
        if (Input.getKey(Keyboard.KEY_DOWN)) {
            rotateX(rotationAmt);
        }
        if (Input.getKey(Keyboard.KEY_LEFT)) {
            rotateY(-rotationAmt);
        }
        if (Input.getKey(Keyboard.KEY_RIGHT)) {
            rotateY(rotationAmt);
        }
    }

    /**
     * Move in a direction.
     *
     * @param dir   Direction to move in
     * @param amt   Amount to move
     */
    public void move(Vector3f dir, float amt) {
        pos = pos.add(dir.mul(amt));
    }

    /**
     * Gets the normalized vector facing left.
     *
     * @return  The normalized vector facing left
     */
    public Vector3f getLeft() {
        Vector3f left = forward.cross(up);
        left.normalize();
        return left;
    }

    /**
     * Gets the normalized vector facing right.
     *
     * @return  The normalized vector facing right
     */
    public Vector3f getRight() {
        Vector3f right = up.cross(forward);
        right.normalize();
        return right;
    }

    /**
     * Rotate an amount around the x-axis.
     *
     * @param angle The amount to rotate
     */
    public void rotateX(float angle) {
        Vector3f hAxis = yAxis.cross(forward);
        hAxis.normalize();

        forward.rotate(angle, hAxis);
        forward.normalize();

        up = forward.cross(hAxis);
        up.normalize();
    }

    /**
     * Rotate an amount around the y-axis.
     *
     * @param angle The amount to rotate
     */
    public void rotateY(float angle) {
        Vector3f hAxis = yAxis.cross(forward);
        hAxis.normalize();

        forward.rotate(angle, yAxis);
        forward.normalize();

        up = forward.cross(hAxis);
        up.normalize();
    }

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public Vector3f getForward() {
        return forward;
    }

    public void setForward(Vector3f forward) {
        this.forward = forward;
    }

    public Vector3f getUp() {
        return up;
    }

    public void setUp(Vector3f up) {
        this.up = up;
    }
}
