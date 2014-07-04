package com.base.engine.rendering.resourceManagement;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.glCreateProgram;

/**
 * @author Octogonapus
 */

public class ShaderResource
{
    private int program;
    private int refCount;
    private HashMap<String, Integer> uniforms;
    private ArrayList<String> uniformNames, uniformTypes;

    public ShaderResource()
    {
        this.program = glCreateProgram();
        refCount = 1;

        uniforms = new HashMap<String, Integer>();
        uniformNames = new ArrayList<String>(0);
        uniformTypes = new ArrayList<String>(0);

        if(program == 0)
        {
            System.err.println("Shader creation failed: Could not find valid memory location");
            System.exit(1);
        }
    }

    @Override
    protected void finalize() throws Throwable
    {
        glDeleteBuffers(program);
        super.finalize();
    }

    public int getProgram()
    {
        return program;
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

    public HashMap<String, Integer> getUniforms()
    {
        return uniforms;
    }

    public ArrayList<String> getUniformNames()
    {
        return uniformNames;
    }

    public ArrayList<String> getUniformTypes()
    {
        return uniformTypes;
    }
}
