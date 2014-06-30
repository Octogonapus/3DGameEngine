package com.base.engine.core;

import com.base.engine.rendering.Shader;

import java.util.ArrayList;

/**
 * @author Octogonapus
 */

public class GameObject
{
    private ArrayList<GameObject> children;
    private ArrayList<GameComponent> components;
    private Transform transform;

    public GameObject()
    {
        children = new ArrayList<GameObject>(0);
        components = new ArrayList<GameComponent>(0);
        transform = new Transform();
    }

    /**
     * Handle input.
     */
    public void input()
    {
        for (GameComponent component : components)
        {
            component.input(transform);
        }

        for (GameObject child : children)
        {
            child.input();
        }
    }

    /**
     * Update objects.
     */
    public void update()
    {
        for (GameComponent component : components)
        {
            component.update(transform);
        }

        for (GameObject child : children)
        {
            child.update();
        }
    }

    /**
     * Render objects.
     */
    public void render(Shader shader)
    {
        for (GameComponent component : components)
        {
            component.render(transform, shader);
        }

        for (GameObject child : children)
        {
            child.render(shader);
        }
    }

    public void addChild(GameObject child)
    {
        children.add(child);
    }

    public void addComponent(GameComponent component)
    {
        components.add(component);
    }

    public Transform getTransform()
    {
        return transform;
    }
}
