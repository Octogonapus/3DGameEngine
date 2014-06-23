package com.base.engine;

/**
 * @author Octogonapus
 */

public class Game {
    private Mesh mesh;
    private Shader shader;
    private Transform transform;
    private Camera camera;

    public Game() {
        mesh = ResourceLoader.loadMesh("monkey.obj");
        shader = new Shader();
        camera = new Camera();

        Transform.setProjection(70f, MainComponent.WIDTH, MainComponent.HEIGHT, 0.1f, 1000);
        Transform.setCamera(camera);
        transform = new Transform();

        shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
        shader.addFragmentShader(ResourceLoader.loadShader("basicFragment.fs"));
        shader.compileShader();
        shader.addUniform("transform");
    }

    /**
     * Handle input.
     */
    public void input() {
        camera.input();
    }

    float temp = 0.0f;
    float sinTemp;

    /**
     * Update meshes to be rendered.
     */
    public void update() {
        temp += Time.getDelta();
        sinTemp = (float) Math.sin(temp);
        transform.setTranslation(0, 0, 2);
        transform.setRotation(0, 135, 0);
        //transform.setScale(1, 1, 1);
    }

    /**
     * Render meshes.
     */
    public void render() {
        shader.bind();
        shader.setUniform("transform", transform.getProjectedTransformation());
        mesh.draw();
    }
}
