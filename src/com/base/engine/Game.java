package com.base.engine;

import org.lwjgl.input.Keyboard;

/**
 * @author Octogonapus
 */

public class Game {
    private Mesh mesh;
    private Shader shader;
    private Transform transform;

    public Game() {
        mesh = ResourceLoader.loadMesh("monkey.obj");
        shader = new Shader();

        transform = new Transform();

        shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
        shader.addFragmentShader(ResourceLoader.loadShader("basicFragment.fs"));
        shader.compileShader();
        shader.addUniform("transform");
    }

    public void input() {
        if (Input.getKeyDown(Keyboard.KEY_UP)) {
            System.out.println("Pressed up");
        }
        if (Input.getKeyUp(Keyboard.KEY_UP)) {
            System.out.println("Released up");
        }

        if (Input.getMouseDown(1)) {
            System.out.println("Pressed right mouse at " + Input.getMousePosition());
        }
        if (Input.getMouseUp(1)) {
            System.out.println("Released right mouse at " + Input.getMousePosition());
        }
    }

    float temp = 0.0f;
    float sinTemp;

    public void update() {
        temp += Time.getDelta();
        sinTemp = (float) Math.sin(temp);
        transform.setTranslation(0, 0, 0);
        transform.setRotation(0, sinTemp * 180, 0);
        transform.setScale(1, 1, 1);
    }

    public void render() {
        shader.bind();
        shader.setUniform("transform", transform.getTransformation());
        mesh.draw();
    }
}
