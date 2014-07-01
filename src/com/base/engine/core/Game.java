package com.base.engine.core;

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
     */
    public void input(float delta)
    {
        getRootObject().input(delta);
    }

    /**
     * Update children.
     */
    public void update(float delta)
    {
        getRootObject().update(delta);
    }

    /**
     * Render children.
     */
    public void render(RenderingEngine renderingEngine)
    {
        renderingEngine.render(getRootObject());
    }

    public void addObject(GameObject object)
    {
        getRootObject().addChild(object);
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
