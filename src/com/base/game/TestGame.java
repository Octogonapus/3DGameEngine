package com.base.game;

import com.base.engine.components.*;
import com.base.engine.core.*;
import com.base.engine.rendering.*;

/**
 * @author Octogonapus
 */

public class TestGame extends Game
{
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
        Mesh tempMesh = new Mesh("monkeySmoothNormalsTextures.obj");
        Material material = new Material();
        material.addTexture("diffuse", new Texture("test.png"));
        material.addFloat("specularIntensity", 1f);
        material.addFloat("specularPower", 8f);

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

        Camera camera = new Camera((float) Math.toRadians(70.0f), (float) Window.getWidth() / (float) Window.getHeight(), 0.01f, 1000.0f);

        GameObject testMesh = new GameObject().addComponent(new MeshRenderer(mesh2, material));
        GameObject testMesh2 = new GameObject().addComponent(new MeshRenderer(mesh2, material));
        GameObject tempMeshObject = new GameObject().addComponent(new MeshRenderer(tempMesh, material));

        testMesh.getTransform().getPos().set(0, 2, 0);
        testMesh2.getTransform().getPos().set(0, 0, 5);
        testMesh.addChild(testMesh2);

        testMesh.getTransform().setRot(new Quaternion(new Vector3f(0, 1, 0), 0.4f));

        testMesh2.addChild(new GameObject().addComponent(camera));

        addObject(testMesh);
        addObject(tempMeshObject);
        tempMeshObject.getTransform().setTranslation(5, 5, 5);
        tempMeshObject.getTransform().setRot(new Quaternion(new Vector3f(0, 1, 0), (float) Math.toRadians(-70.0f)));

        directionalLight.getTransform().setRot(new Quaternion(new Vector3f(1, 0, 0), (float) Math.toRadians(-45)));
    }
}
