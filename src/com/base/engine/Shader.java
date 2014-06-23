package com.base.engine;

import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

/**
 * @author Octogonapus
 */

public class Shader {
    //HOLY FUCK A POINTER
    private int program;
    private HashMap<String, Integer> uniforms;

    public Shader() {
        program = glCreateProgram();
        uniforms = new HashMap<String, Integer>(0);

        if (program == 0) {
            System.err.println("Shader creation failed: Could not find valid memory location");
            System.exit(1);
        }
    }

    /**
     * Bind shader.
     */
    public void bind() {
        glUseProgram(program);
    }

    /**
     * Add a uniform variable.
     *
     * @param uniform   Name of uniform
     */
    public void addUniform(String uniform) {
        int uniformLocation = glGetUniformLocation(program, uniform);

        if (uniformLocation == 0xFFFFFFFF) {
            System.err.println("Could not find uniform: " + uniform);
            new Exception().printStackTrace();
            System.exit(1);
        }

        uniforms.put(uniform, uniformLocation);
    }

    /**
     * Set a uniform value.
     *
     * @param uniformName   The uniform name
     * @param value         The uniform value
     */
    public void setUniformi(String uniformName, int value) {
        glUniform1i(uniforms.get(uniformName), value);
    }

    /**
     * Set a uniform value.
     *
     * @param uniformName   The uniform name
     * @param value         The uniform value
     */
    public void setUniformf(String uniformName, float value) {
        glUniform1f(uniforms.get(uniformName), value);
    }

    /**
     * Set a uniform value.
     *
     * @param uniformName   The uniform name
     * @param value         The uniform value
     */
    public void setUniform(String uniformName, Vector3f value) {
        glUniform3f(uniforms.get(uniformName), value.getX(), value.getY(), value.getZ());
    }

    /**
     * Set a uniform value.
     *
     * @param uniformName   The uniform name
     * @param value         The uniform value
     */
    public void setUniform(String uniformName, Matrix4f value) {
        glUniformMatrix4(uniforms.get(uniformName), true, Util.createFlippedBuffer(value));
    }

    /**
     * Add a vertex shader.
     *
     * @param text  Name of shader
     */
    public void addVertexShader(String text) {
        addProgram(text, GL_VERTEX_SHADER);
    }

    /**
     * Add a fragment shader.
     *
     * @param text  Name of shader
     */
    public void addFragmentShader(String text) {
        addProgram(text, GL_FRAGMENT_SHADER);
    }

    /**
     * Add a geometry shader.
     *
     * @param text  Name of shader
     */
    public void addGeometryShader(String text) {
        addProgram(text, GL_GEOMETRY_SHADER);
    }

    /**
     * Compile the shader.
     */
    public void compileShader() {
        glLinkProgram(program);

        if (glGetProgram(program, GL_LINK_STATUS) == 0) {
            System.err.println(glGetProgramInfoLog(program, 1024));
            System.exit(1);
        }

        glValidateProgram(program);

        if (glGetProgram(program, GL_VALIDATE_STATUS) == 0) {
            System.err.println(glGetProgramInfoLog(program, 1024));
            System.exit(1);
        }
    }

    /**
     * Add a program.
     *
     * @param text  Name of shader
     * @param type  Type of shader
     */
    private void addProgram(String text, int type) {
        int shader = glCreateShader(type);

        if (shader == 0) {
            System.err.println("Shader creation failed: Could not find valid memory location");
            System.exit(1);
        }

        glShaderSource(shader, text);
        glCompileShader(shader);

        if (glGetShader(shader, GL_COMPILE_STATUS) == 0) {
            System.err.println(glGetShaderInfoLog(shader, 1024));
            System.exit(1);
        }

        glAttachShader(program, shader);
    }
}
