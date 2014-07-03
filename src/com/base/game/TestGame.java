package com.base.game;

import com.base.engine.components.*;
import com.base.engine.core.*;
import com.base.engine.rendering.*;

/**
 * @author Octogonapus
 */

public class TestGame extends Game
{
    private GameObject monkeyMeshObject;
    private Camera camera;
    private boolean doRotate = false;

    /**
     * Initializes this game, called when this game starts.
     */
    public void init()
    {

        float fieldDepth = 10.0f;
        float fieldWidth = 10.0f;
        float fieldDepth2 = 1.0f;
        float fieldWidth2 = 1.0f;

        Vertex[] vertices = new Vertex[]{
                new Vertex(new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
                new Vertex(new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))
        };

        int indices[] = {
                0, 1, 2,
                2, 1, 3
        };

        Vertex[] vertices2 = new Vertex[]{
                new Vertex(new Vector3f(-fieldWidth2, 0.0f, -fieldDepth2), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-fieldWidth2, 0.0f, fieldDepth2 * 3), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f(fieldWidth2 * 3, 0.0f, -fieldDepth2), new Vector2f(1.0f, 0.0f)),
                new Vertex(new Vector3f(fieldWidth2 * 3, 0.0f, fieldDepth2 * 3), new Vector2f(1.0f, 1.0f))
        };

        int indices2[] = {
                0, 1, 2,
                2, 1, 3
        };

        Mesh mesh = new Mesh(vertices, indices, true);
        Mesh mesh2 = new Mesh(vertices2, indices2, true);
        Mesh monkeyMesh = new Mesh("monkeySmooth.obj");
        Mesh monkeyMesh2 = new Mesh("monkeySmooth.obj");

        Material material = new Material();
        material.addTexture("diffuse", new Texture("test.png"));
        material.addFloat("specularIntensity", 1f);
        material.addFloat("specularPower", 8f);

        Material material2 = new Material();
        material2.addTexture("diffuse", new Texture("bricks.jpg"));
        material2.addFloat("specularIntensity", 1f);
        material2.addFloat("specularPower", 8f);

        GameObject planeObject = new GameObject();
        planeObject.addComponent(new MeshRenderer(mesh, material));
        planeObject.getTransform().getPos().set(0, -1, 5);

        GameObject directionalLightObject = new GameObject();
        DirectionalLight directionalLight = new DirectionalLight(new Vector3f(1, 1, 1), 0.4f);
        directionalLightObject.addComponent(directionalLight);

        GameObject pointLightObject = new GameObject();
        PointLight pointLight = new PointLight(new Vector3f(0, 1, 0), 0.4f, new Vector3f(0, 0, 1));
        pointLightObject.addComponent(pointLight);

        GameObject spotLightObject = new GameObject();
        SpotLight spotLight = new SpotLight(new Vector3f(0, 1, 1), 0.4f, new Vector3f(0, 0, 0.1f), 0.7f);
        spotLightObject.getTransform().getPos().set(5, 0, 5);
        spotLightObject.getTransform().setRot(new Quaternion(new Vector3f(0, 1, 0), (float) Math.toRadians(90.0f)));
        spotLightObject.addComponent(spotLight);

        addObject(planeObject);
        addObject(directionalLightObject);
        addObject(pointLightObject);
        addObject(spotLightObject);

        camera = new Camera((float) Math.toRadians(70.0f), (float) 400 / (float) 300, 0.01f, 1000.0f);
        planeObject.addChild(new GameObject().addComponent(camera));

        GameObject testMesh = new GameObject().addComponent(new MeshRenderer(mesh2, material));
        GameObject testMesh2 = new GameObject().addComponent(new MeshRenderer(mesh2, material));
        monkeyMeshObject = new GameObject().addComponent(new MeshRenderer(monkeyMesh, material));
        GameObject monkeyMesh2Object = new GameObject().addComponent(new MeshRenderer(monkeyMesh2, material2));
        monkeyMeshObject.addChild(monkeyMesh2Object);

        addObject(testMesh);
        addObject(monkeyMeshObject);
        monkeyMeshObject.getTransform().setTranslation(5, 5, 5);
        monkeyMeshObject.getTransform().setRot(new Quaternion(new Vector3f(0, 1, 0), (float) Math.toRadians(-70.0f)));
        monkeyMesh2Object.getTransform().setTranslation(0, 2, 0);

        testMesh.getTransform().getPos().set(0, 2, 0);
        testMesh2.getTransform().getPos().set(0, 0, 5);
        testMesh.addChild(testMesh2);
        testMesh.getTransform().setRot(new Quaternion(new Vector3f(0, 1, 0), 0.4f));

        directionalLight.getTransform().setRot(new Quaternion(new Vector3f(1, 0, 0), (float) Math.toRadians(-45)));
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
            monkeyMeshObject.getTransform().setRot(camera.getTransform().getRot());
        }
    }
}
