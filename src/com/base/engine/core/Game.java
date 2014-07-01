package com.base.engine.core;

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
     * Update objects.
     */
    public void update(float delta)
    {
        getRootObject().update(delta);
    }

    public GameObject getRootObject()
    {
        if (root == null)
        {
            root = new GameObject();
        }

        return root;
    }
}
