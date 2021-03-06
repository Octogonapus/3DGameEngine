package com.base.engine.rendering;

import com.base.engine.components.BaseLight;
import com.base.engine.components.Camera;
import com.base.engine.core.GameObject;
import com.base.engine.core.Transform;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.resourceManagement.MappedValues;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

/**
 * @author Octogonapus
 */

public class RenderingEngine extends MappedValues
{
    private Camera mainCamera;

    private ArrayList<BaseLight> lights;
    private BaseLight activeLight;
    private Shader forwardAmbient;

    private HashMap<String, Integer> samplerMap;

    public RenderingEngine()
    {
        super();

        lights = new ArrayList<BaseLight>(0);
        samplerMap = new HashMap<String, Integer>(0);

        samplerMap.put("diffuse", 0);
        samplerMap.put("normalMap", 1);
        samplerMap.put("dispMap", 2);

        forwardAmbient = new Shader("forward-ambient");

        addVector3f("ambient", new Vector3f(0.1f, 0.1f, 0.1f));

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_DEPTH_CLAMP);
        glEnable(GL_TEXTURE_2D);
    }

    /**
     * Update a uniform struct that is not handled in Shader by default.
     *
     * @param transform     The transform
     * @param material      The material
     * @param shader        The shader for the uniform
     * @param uniformName   The name of the uniform
     * @param uniformType   The type of the uniform
     */
    public void updateUniformStruct(Transform transform, Material material, Shader shader, String uniformName, String uniformType)
    {
        throw new IllegalArgumentException(uniformType + " is not a supported type in Rendering Engine");
    }

    /**
     * Render a GameObject.
     *
     * @param object    The GameObject to render
     */
    public void render(GameObject object)
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        object.renderAll(forwardAmbient, this);

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);
        glDepthMask(false);
        glDepthFunc(GL_EQUAL);
        {
            for (BaseLight light : lights)
            {
                activeLight = light;
                object.renderAll(light.getShader(), this);
            }
        }
        glDepthFunc(GL_LESS);
        glDepthMask(true);
        glDisable(GL_BLEND);
    }

    public static String getOpenGLVersion()
    {
        return glGetString(GL_VERSION);
    }

    public Camera getMainCamera()
    {
        return mainCamera;
    }

    public void setMainCamera(Camera mainCamera)
    {
        this.mainCamera = mainCamera;
    }

    public void addLight(BaseLight light)
    {
        lights.add(light);
    }

    public void addCamera(Camera camera)
    {
        mainCamera = camera;
    }

    public BaseLight getActiveLight()
    {
        return activeLight;
    }

    public int getSamplerSlot(String samplerName)
    {
        return samplerMap.get(samplerName);
    }
}
