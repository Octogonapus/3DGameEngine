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
    public void input()
    {
        getRootObject().input();
    }

    /**
     * Update objects.
     */
    public void update()
    {
        getRootObject().update();
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
