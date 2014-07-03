package com.base.engine.rendering;

import com.base.engine.components.BaseLight;
import com.base.engine.components.Camera;
import com.base.engine.core.GameObject;
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
    private Vector3f ambientLight;

    private ArrayList<BaseLight> lights;
    private BaseLight activeLight;

    private HashMap<String, Integer> samplerMap;

    public RenderingEngine()
    {
        super();

        lights = new ArrayList<BaseLight>(0);
        samplerMap = new HashMap<String, Integer>(0);

        samplerMap.put("diffuse", 0);

        addVector3f("ambient", new Vector3f(0.1f, 0.1f, 0.1f));

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_DEPTH_CLAMP);
        glEnable(GL_TEXTURE_2D);

//        mainCamera = new Camera((float) Math.toRadians(70.0f), (float) Window.getWidth() / (float) Window.getHeight(), 0.01f, 1000.0f);
//        ambientLight = new Vector3f(0.1f, 0.1f, 0.1f);
//        activeDirectionalLight = new DirectionalLight(new BaseLight(new Vector3f(0, 0, 1), 0.4f), new Vector3f(1, 1, 1));
//        activePointLight = new PointLight(new BaseLight(new Vector3f(0, 1, 0), 0.4f), new Attenuation(0, 0, 1), new Vector3f(5, 0, 5), 100);
//        spotLight = new SpotLight(new PointLight(new BaseLight(new Vector3f(0, 1, 1), 0.4f), new Attenuation(0, 0, 0.1f), new Vector3f(5, 0, 5), 100), new Vector3f(1, 0, 0), 0.7f);
    }

    /**
     * Render a GameObject.
     *
     * @param object    The GameObject to render
     */
    public void render(GameObject object)
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        lights.clear();
        object.addToRenderingEngine(this);

        Shader forwardAmbient = ForwardAmbient.getInstance();

        object.render(forwardAmbient, this);

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);
        glDepthMask(false);
        glDepthFunc(GL_EQUAL);
        {
            for (BaseLight light : lights)
            {
                activeLight = light;
                object.render(light.getShader(), this);
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
