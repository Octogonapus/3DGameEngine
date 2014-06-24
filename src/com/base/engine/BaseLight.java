package com.base.engine;

/**
 * @author Octogonapus
 */

public class BaseLight
{
    private Vector3f color;
    private float intsensity;

    public BaseLight(Vector3f color, float intsensity)
    {
        this.color = color;
        this.intsensity = intsensity;
    }

    public Vector3f getColor()
    {
        return color;
    }

    public void setColor(Vector3f color)
    {
        this.color = color;
    }

    public float getIntsensity()
    {
        return intsensity;
    }

    public void setIntsensity(float intsensity)
    {
        this.intsensity = intsensity;
    }
}
