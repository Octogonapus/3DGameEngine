package com.base.engine.rendering.resourceManagement;

import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;

/**
 * @author Octogonapus
 */

public class TextureResource
{
    private int id;
    private int refCount;

    public TextureResource()
    {
        this.id = glGenTextures();
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
