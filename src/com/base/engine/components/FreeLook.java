package com.base.engine.components;

import com.base.engine.core.Input;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Window;

/**
 * @author Octogonapus
 */

public class FreeLook extends GameComponent
{
    public static final Vector3f yAxis = new Vector3f(0, 1, 0);

    private float sensitivity;
    private int keyEsc;

    boolean mouseLocked = false;
    Vector2f centerPosition = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);

    public FreeLook(float sensitivity)
    {
        this(sensitivity, Input.KEY_ESCAPE);
    }

    public FreeLook(float sensitivity, int keyEsc)
    {
        this.sensitivity = sensitivity;
        this.keyEsc = keyEsc;
    }

    /**
     * Takes inputAll and reacts accordingly.
     */
    @Override
    public void input(float delta)
    {
        if(Input.getKey(keyEsc))
        {
            Input.setCursor(true);
            mouseLocked = false;
        }
        if(Input.getMouseDown(0))
        {
            Input.setMousePosition(centerPosition);
            Input.setCursor(false);
            mouseLocked = true;
        }

        if(mouseLocked)
        {
            Vector2f deltaPos = Input.getMousePosition().sub(centerPosition);

            boolean rotY = deltaPos.getX() != 0;
            boolean rotX = deltaPos.getY() != 0;

            if (rotY)
            {
                getTransform().rotate(yAxis, (float) Math.toRadians(deltaPos.getX() * sensitivity));
            }
            if(rotX)
            {
                getTransform().rotate(getTransform().getRot().getRight(), (float) Math.toRadians(-deltaPos.getY() * sensitivity));
            }
            if(rotY || rotX)
            {
                Input.setMousePosition(new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2));
            }
        }
    }
}
