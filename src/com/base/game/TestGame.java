package com.base.game;

import com.base.engine.components.*;
import com.base.engine.core.*;
import com.base.engine.rendering.*;

/**
 * @author Octogonapus
 */

public class TestGame extends Game
{
    Mesh planeMesh;
    Mesh monkeyMesh;
    Mesh monkeyMesh2;

    Material testMaterial;
    Material brickMaterial;

    DirectionalLight directionalLight;
    PointLight pointLight;

    GameObject planeObject;
    GameObject monkeyObject;
    GameObject monkeyObject2;
    GameObject directionalLightObject;
    GameObject pointLightObject;

    private Camera camera;
    private boolean doRotate;

    /**
     * Initializes this game, called when this game starts.
     */
    public void init()
    {
        //Initialize components
        directionalLight = new DirectionalLight(Shader.COLOR_WHITE, 0.4f);
        pointLight = new PointLight(new Vector3f(0, 1, 0), 0.4f, new Attenuation(0, 0, 1));
        camera = new Camera((float) Math.toRadians(70.0f), (float) Window.getWidth() / (float) Window.getHeight(), 0.01f, 1000.0f);

        //Load meshes
        planeMesh = new Mesh("plane.obj");
        monkeyMesh = new Mesh("monkeySmooth.obj");
        monkeyMesh2 = new Mesh("monkeySmooth.obj");

        //Load and initialize materials
        testMaterial = new Material();
        testMaterial.addTexture("diffuse", new Texture("test.png"));
        testMaterial.addFloat("specularIntensity", 1f);
        testMaterial.addFloat("specularPower", 8f);

        brickMaterial = new Material();
        brickMaterial.addTexture("diffuse", new Texture("bricks.jpg"));
        brickMaterial.addFloat("specularIntensity", 1f);
        brickMaterial.addFloat("specularPower", 8f);

        //Initialize objects
        planeObject = new GameObject().addComponent(new MeshRenderer(planeMesh, testMaterial));
        monkeyObject = new GameObject().addComponent(new MeshRenderer(monkeyMesh, testMaterial));
        monkeyObject2 = new GameObject().addComponent(new MeshRenderer(monkeyMesh2, brickMaterial));
        directionalLightObject = new GameObject().addComponent(directionalLight);
        pointLightObject = new GameObject().addComponent(pointLight);

        //Add children and components
        monkeyObject.addChild(monkeyObject2);

        //Transform objects and components
        planeObject.getTransform().setScale(20);
        planeObject.getTransform().setTranslation(0, -5, 0);
        monkeyObject.getTransform().setTranslation(0, 2, 0);
        monkeyObject2.getTransform().setTranslation(3, 0, 0);
        directionalLightObject.getTransform().setRot(new Quaternion(new Vector3f(1, 0, 0), (float) Math.toRadians(-45)));
        pointLightObject.getTransform().setTranslation(0, 5, 0);

        //Add objects
        addObject(new GameObject().addComponent(camera).addComponent(new FreeMove(10f)).addComponent(new FreeLook(0.5f)));
        addObject(planeObject);
        addObject(monkeyObject);
        addObject(directionalLightObject);
        addObject(pointLightObject);
    }

    @Override
    public void input(float delta)
    {
        super.input(delta);

        doRotate = Input.getKey(Input.KEY_SPACE);
    }

    @Override
    public void update(float delta)
    {
        super.update(delta);

        if (doRotate)
        {
            monkeyObject.getTransform().setRot(camera.getTransform().getRot());
        }
    }
}
