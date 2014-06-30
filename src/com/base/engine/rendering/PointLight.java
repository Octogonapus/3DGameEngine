package com.base.engine.rendering;

import com.base.engine.core.Vector3f;

/**
 * @author Octogonapus
 */

public class PointLight
{
    private BaseLight baseLight;
    private Vector3f position;
    private Attenuation atten;
    private float range;

    public PointLight(BaseLight baseLight, Attenuation atten, Vector3f position, float range)
    {
        this.baseLight = baseLight;
        this.atten = atten;
        this.position = position;
        this.range = range;
    }

    public BaseLight getBaseLight()
    {
        return baseLight;
    }

    public void setBaseLight(BaseLight baseLight)
    {
        this.baseLight = baseLight;
    }

    public Vector3f getPosition()
    {
        return position;
    }

    public void setPosition(Vector3f position)
    {
        this.position = position;
    }

    public Attenuation getAtten()
    {
        return atten;
    }

    public void setAtten(Attenuation atten)
    {
        this.atten = atten;
    }

    public float getRange()
    {
        return range;
    }

    public void setRange(float range)
    {
        this.range = range;
    }
}
