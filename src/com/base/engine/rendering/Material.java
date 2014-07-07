package com.base.engine.rendering;

import com.base.engine.core.Vector3f;
import com.base.engine.rendering.resourceManagement.MappedValues;

import java.util.HashMap;

/**
 * @author Octogonapus
 */

public class Material extends MappedValues
{
    private HashMap<String, Texture> textureHashMap;
    private HashMap<String, Vector3f> vector3fHashMap;
    private HashMap<String, Float> floatHashMap;

    public Material(Texture diffuse)
    {
        this(diffuse, 0.5f, 4f, new Texture("default_normal.jpg"), new Texture("default_disp.png"), 0.0f, 0.0f);
    }

    public Material(Texture diffuse, float specularIntesnity, float specularPower, Texture normalMap, Texture dispMap, float dispMapScale, float dispMapOffset)
    {
        super();

        textureHashMap = new HashMap<String, Texture>(0);
        textureHashMap.put("diffuse", diffuse);
        textureHashMap.put("normalMap", normalMap);
        textureHashMap.put("dispMap", dispMap);

        vector3fHashMap = new HashMap<String, Vector3f>(0);

        floatHashMap = new HashMap<String, Float>(0);
        floatHashMap.put("specularIntensity", specularIntesnity);
        floatHashMap.put("specularPower", specularPower);
        floatHashMap.put("dispMapScale", dispMapScale);
        float baseBias = dispMapScale / 2.0f;
        floatHashMap.put("dispMapBias", -baseBias + baseBias * dispMapOffset);
    }

    public void addTexture(String name, Texture texture)
    {
        textureHashMap.put(name, texture);
    }

    public Texture getTexture(String name)
    {
        Texture result = textureHashMap.get(name);
        if (result != null)
        {
            return result;
        }

        return new Texture("test.png");
    }

    public Texture removeTexture(String name)
    {
        return textureHashMap.remove(name);
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
