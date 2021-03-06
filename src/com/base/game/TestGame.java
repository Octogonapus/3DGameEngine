package com.base.game;

import com.base.engine.components.*;
import com.base.engine.core.*;
import com.base.engine.rendering.*;

/**
 * @author Octogonapus
 */

public class TestGame extends Game
{
    private Mesh planeMesh;
    private Mesh tinyPlaneMesh;

    private Material testMaterial;
    private Material brickMaterial;
    private Material brick2Material;

    private Camera camera;
    private DirectionalLight directionalLight;

    private GameObject cameraObject;
    private GameObject planeObject;
    private GameObject directionalLightObject;
    private GameObject tinyPlaneObject;

    /**
     * Initializes this game, called when this game starts.
     */
    @Override
    public void init()
    {
        //Initialize components
        camera = new Camera((float) Math.toRadians(70.0f), (float) Window.getWidth() / (float) Window.getHeight(), 0.01f, 1000.0f);
        directionalLight = new DirectionalLight(Color.COLOR_WHITE, 0.4f);

        //Load meshes
        planeMesh = new Mesh("plane3.obj");
        tinyPlaneMesh = new Mesh("plane3.obj");

        //Load and initialize materials
        testMaterial = new Material(new Texture("test.png"));

        brickMaterial = new Material(new Texture("bricks.jpg"), 0.5f, 4f, new Texture("bricks_normal.jpg"), new Texture("bricks_disp.png"), 0.03f, -0.5f);

        brick2Material = new Material(new Texture("bricks2.jpg"), 0.5f, 4f, new Texture("bricks2_normal.jpg"), new Texture("bricks2_disp.jpg"), 0.04f, -1.0f);

        //Initialize objects
        cameraObject = new GameObject().addComponent(camera).addComponent(new FreeLook(0.5f)).addComponent(new FreeMove(20f));
        planeObject = new GameObject().addComponent(new MeshRenderer(planeMesh, brickMaterial));
        directionalLightObject = new GameObject().addComponent(directionalLight);
        tinyPlaneObject = new GameObject().addComponent(new MeshRenderer(tinyPlaneMesh, brick2Material));

        //Add children and components

        //Transform objects and components
        cameraObject.getTransform().setTranslation(0, 1, -6);
        planeObject.getTransform().setTranslation(0, -5, 0);
        directionalLightObject.getTransform().setRot(new Quaternion(new Vector3f(1, 0, 0), (float) Math.toRadians(-45)));
//        tinyPlaneObject.getTransform().setScale(0.2f);

        //Add objects
        addObject(cameraObject);
        addObject(planeObject);
        addObject(directionalLightObject);
        addObject(tinyPlaneObject);
    }
}
