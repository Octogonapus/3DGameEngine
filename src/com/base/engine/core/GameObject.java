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
    private CoreEngine engine;

    public GameObject()
    {
        children = new ArrayList<GameObject>(0);
        components = new ArrayList<GameComponent>(0);
        transform = new Transform();
        engine = null;
    }

    /**
     * Handle all input.
     *
     * @param delta Delta time
     */
    public void inputAll(float delta)
    {
        input(delta);

        for (GameObject child : children)
        {
            child.inputAll(delta);
        }
    }

    /**
     * Update objects.
     *
     * @param delta Delta time
     */
    public void updateAll(float delta)
    {
        transform.update();

        update(delta);

        for (GameObject child : children)
        {
            child.updateAll(delta);
        }
    }

    /**
     * Render objects.
     *
     * @param shader            The shader to render with
     * @param renderingEngine   The Rendering Engine to render with
     */
    public void renderAll(Shader shader, RenderingEngine renderingEngine)
    {
        render(shader, renderingEngine);

        for (GameObject child : children)
        {
            child.renderAll(shader, renderingEngine);
        }
    }

    /**
     * Handle input for this object only.
     *
     * @param delta Delta time
     */
    public void input(float delta)
    {
        for (GameComponent component : components)
        {
            component.input(delta);
        }
    }

    /**
     * Update this object only.
     *
     * @param delta Delta time
     */
    public void update(float delta)
    {
        for (GameComponent component : components)
        {
            component.update(delta);
        }
    }

    /**
     * Render this object only.
     *
     * @param shader            The shader to render with
     * @param renderingEngine   The Rendering Engine to render with
     */
    public void render(Shader shader, RenderingEngine renderingEngine)
    {
        for (GameComponent component : components)
        {
            component.render(shader, renderingEngine);
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
        child.setEngine(engine);
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

    public void setEngine(CoreEngine engine)
    {
        if (this.engine != engine)
        {
            this.engine = engine;

            for (GameComponent component : components)
        {
            component.addToEngine(engine);
        }

        for (GameObject child : children)
        {
            child.setEngine(engine);
        }
        }
    }

    public ArrayList<GameObject> getAllAttached()
    {
        ArrayList<GameObject> result = new ArrayList<GameObject>(0);

        for (GameObject child : children)
        {
            result.addAll(child.getAllAttached());
        }

        result.add(this);
        return result;
    }
}
