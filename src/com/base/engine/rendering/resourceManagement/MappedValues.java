package com.base.engine.rendering.resourceManagement;

import com.base.engine.core.Vector3f;

import java.util.HashMap;

/**
 * @author Octogonapus
 */

public abstract class MappedValues
{
    private HashMap<String, Vector3f> vector3fHashMap;
    private HashMap<String, Float> floatHashMap;

    public MappedValues()
    {
        vector3fHashMap = new HashMap<String, Vector3f>(0);
        floatHashMap = new HashMap<String, Float>(0);
    }

    public void addVector3f(String name, Vector3f vector3f)
    {
        vector3fHashMap.put(name, vector3f);
    }

    public Vector3f getVector3f(String name)
    {
        Vector3f result = vector3fHashMap.get(name);
        if (result != null)
        {
            return result;
        }

        return new Vector3f(0, 0, 0);
    }

    public Vector3f removeVector3f(String name)
    {
        return vector3fHashMap.remove(name);
    }

    public void addFloat(String name, Float value)
    {
        floatHashMap.put(name, value);
    }

    public Float getFloat(String name)
    {
        Float result = floatHashMap.get(name);
        if (result != null)
        {
            return result;
        }

        return 0f;
    }

    public Float removeFloat(String name)
    {
        return floatHashMap.remove(name);
    }
}
