package com.base.engine.components;

import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Attenuation;
import com.base.engine.rendering.Shader;

/**
 * @author Octogonapus
 */

public class PointLight extends BaseLight
{
    private static final int COLOR_DEPTH = 256;
    private Attenuation attenuation;
    private float range;

    public PointLight(Vector3f color, float intensity, Attenuation attenuation)
    {
        super(color, intensity);
        this.attenuation = attenuation;

        float a = attenuation.getExponent();
        float b = attenuation.getLinear();
        float c = attenuation.getConstant() - COLOR_DEPTH * getIntensity() * getColor().max();

        range = (float) (-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a);

        setShader(new Shader("forward-point"));
    }

    public float getRange()
    {
        return range;
    }

    public Attenuation getAttenuation()
    {
        return attenuation;
    }
}
