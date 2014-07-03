package com.base.engine.core;

import com.base.engine.components.GameComponent;
import com.base.engine.rendering.RenderingEngine;
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
     *
     * @param delta Delta time
     */
    public void input(float delta)
    {
        for (GameComponent component : components)
        {
            component.input(delta);
        }

        for (GameObject child : children)
        {
            child.input(delta);
        }
    }

    /**
     * Update objects.
     *
     * @param delta Delta time
     */
    public void update(float delta)
    {
        transform.update();

        for (GameComponent component : components)
        {
            component.update(delta);
        }

        for (GameObject child : children)
        {
            child.update(delta);
        }
    }

    /**
     * Render objects.
     *
     * @param shader            The shader to render
     * @param renderingEngine   The Rendering Engine to render with
     */
    public void render(Shader shader, RenderingEngine renderingEngine)
    {
        for (GameComponent component : components)
        {
            component.render(shader, renderingEngine);
        }

        for (GameObject child : children)
        {
            child.render(shader, renderingEngine);
        }
    }

    /**
     * Adds all components and children to a Rendering Engine
     *
     * @param renderingEngine   The Rendering Engine to add the components and children to
     */
    public void addToRenderingEngine(RenderingEngine renderingEngine)
    {
        for (GameComponent component : components)
        {
            component.addToRenderingEngine(renderingEngine);
        }

        for (GameObject child : children)
        {
            child.addToRenderingEngine(renderingEngine);
        }
    }

    /**
     * Add a child.
     *
     * @param child The child to add
     */
    public void addChild(GameObject child)
    {
        children.add(child);
        child.getTransform().setParent(transform);
    }

    /**
     * Add a component.
     *
     * @param component The component to add
     * @return          This game object with the added component
     */
    public GameObject addComponent(GameComponent component)
    {
        components.add(component);
        component.setParent(this);
        return this;
    }

    public Transform getTransform()
    {
        return transform;
    }
}
