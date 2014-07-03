package com.base.engine.core;

import com.base.engine.components.Camera;
import com.base.engine.rendering.RenderingEngine;

/**
 * @author Octogonapus
 */

public abstract class Game
{
    private GameObject root;

    /**
     * Initialize objects.
     */
    public void init() {}

    /**
     * Handle input.
     *
     * @param delta Delta time
     */
    public void input(float delta)
    {
        getRootObject().input(delta);
    }

    /**
     * Update children.
     *
     * @param delta Delta time
     */
    public void update(float delta)
    {
        getRootObject().update(delta);
    }

    /**
     * Render children.
     *
     * @param renderingEngine The Rendering Engine to render with
     */
    public void render(RenderingEngine renderingEngine)
    {
        renderingEngine.render(getRootObject());
    }

    public void addObject(GameObject object)
    {
        getRootObject().addChild(object);
    }

    public void setMainCamera(RenderingEngine renderingEngine, Camera camera)
    {
        renderingEngine.setMainCamera(camera);
    }

    private GameObject getRootObject()
    {
        if (root == null)
        {
            root = new GameObject();
        }

        return root;
    }
}
