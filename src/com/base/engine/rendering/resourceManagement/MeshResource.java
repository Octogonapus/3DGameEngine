package com.base.engine.rendering.resourceManagement;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

/**
 * @author Octogonapus
 */

public class MeshResource
{
    //HOLY FUCK A POINTER
    private int vbo, ibo;
    private int size;
    private int refCount;

    public MeshResource(int size)
    {
        vbo = glGenBuffers();
        ibo = glGenBuffers();
        this.size = size;
        refCount = 1;
    }

    @Override
    protected void finalize() throws Throwable
    {
        glDeleteBuffers(vbo);
        glDeleteBuffers(ibo);
        super.finalize();
    }

    public int getVbo()
    {
        return vbo;
    }

    public int getIbo()
    {
        return ibo;
    }

    public int getSize()
    {
        return size;
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
