package com.base.engine.rendering;

import com.base.engine.core.Transform;

/**
 * @author Octogonapus
 */

public class ForwardAmbient extends Shader
{
    private static final ForwardAmbient instance = new ForwardAmbient();

    private ForwardAmbient()
    {
        super("forward-ambient");
    }

    /**
     * Update uniforms.
     *
     * @param transform Transform for new projection matrix
     * @param material  Material for ambient color
     */
    public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine)
    {
        super.updateUniforms(transform, material, renderingEngine);
    }

    public static ForwardAmbient getInstance()
    {
        return instance;
    }
}
