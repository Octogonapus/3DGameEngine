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

    private Material testMaterial;
    private Material brickMaterial;
    private Material brick2Material;

    private Camera camera;
    private DirectionalLight directionalLight;

    private GameObject cameraObject;
    private GameObject planeObject;
    private GameObject directionalLightObject;

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

        //Load and initialize materials
        testMaterial = new Material();
        testMaterial.addTexture("diffuse", new Texture("test.png"));
        testMaterial.addFloat("specularIntensity", 1f);
        testMaterial.addFloat("specularPower", 8f);

        brickMaterial = new Material();
        brickMaterial.addTexture("diffuse", new Texture("bricks.jpg"));
        brickMaterial.addFloat("specularIntensity", 1f);
        brickMaterial.addFloat("specularPower", 8f);

        brick2Material = new Material();
        brick2Material.addTexture("diffuse", new Texture("bricks2.jpg"));
        brick2Material.addTexture("normalMap", new Texture("bricks2_normal.png"));
        brick2Material.addFloat("specularIntensity", 1f);
        brick2Material.addFloat("specularPower", 8f);

        //Initialize objects
        cameraObject = new GameObject().addComponent(camera).addComponent(new FreeLook(0.5f)).addComponent(new FreeMove(20));
        planeObject = new GameObject().addComponent(new MeshRenderer(planeMesh, brick2Material));
        directionalLightObject = new GameObject().addComponent(directionalLight);

        //Add children and components

        //Transform objects and components
        cameraObject.getTransform().setTranslation(0, 1, -6);
        planeObject.getTransform().setTranslation(0, -5, 0);
        directionalLightObject.getTransform().setRot(new Quaternion(new Vector3f(1, 0, 0), (float) Math.toRadians(-45)));

        //Add objects
        addObject(cameraObject);
        addObject(planeObject);
        addObject(directionalLightObject);
    }
}
