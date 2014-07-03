package com.base.engine.rendering.resourceManagement;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;

/**
 * @author Octogonapus
 */

public class TextureResource
{
    private int id;
    private int refCount;

    public TextureResource(int id)
    {
        this.id = id;
        refCount = 1;
    }

    @Override
    protected void finalize() throws Throwable
    {
        glDeleteBuffers(id);
        super.finalize();
    }

    public int getId()
    {
        return id;
    }

    public void addReference()
    {
        refCount++;
    }

    public boolean removeReference()
    {
        refCount--;
        return refCount == 0;
    }
}
