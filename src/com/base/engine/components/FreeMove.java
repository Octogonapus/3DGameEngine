package com.base.engine.components;

import com.base.engine.core.Input;
import com.base.engine.core.Vector3f;

/**
 * @author Octogonapus
 */

public class FreeMove extends GameComponent
{
    public static final Vector3f yAxis = new Vector3f(0,1,0);

    private float speed;
    private int keyForwards, keyBackwards, keyLeft, keyRight, keyUp, keyDown;

    public FreeMove(float speed)
    {
        this(speed, Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D, Input.KEY_Z, Input.KEY_X);
    }

    public FreeMove(float speed, int keyForwards, int keyBackwards, int keyLeft, int keyRight, int keyUp, int keyDown)
    {
        this.speed = speed;
        this.keyForwards = keyForwards;
        this.keyBackwards = keyBackwards;
        this.keyLeft = keyLeft;
        this.keyRight = keyRight;
        this.keyUp = keyUp;
        this.keyDown = keyDown;
    }

    /**
     * Takes inputAll and reacts accordingly.
     *
     * @param delta Delta time
     */
    @Override
    public void input(float delta)
    {
        float movAmt = speed * delta;

        if(Input.getKey(keyForwards))
        {
            move(getTransform().getRot().getForward(), movAmt);
        }
        if(Input.getKey(keyBackwards))
        {
            move(getTransform().getRot().getForward(), -movAmt);
        }
        if(Input.getKey(keyLeft))
        {
            move(getTransform().getRot().getLeft(), movAmt);
        }
        if(Input.getKey(keyRight))
        {
            move(getTransform().getRot().getRight(), movAmt);
        }
        if (Input.getKey(keyUp))
        {
            move(yAxis, movAmt);
        }
        if (Input.getKey(keyDown))
        {
            move(yAxis, -movAmt);
        }
    }

    /**
     * Moves an amount in a direction.
     *
     * @param dir   The direction to move in
     * @param amt   The amount to move
     */
    public void move(Vector3f dir, float amt)
    {
        getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
    }
}
