package com.base.engine.rendering;

import com.base.engine.core.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

/**
 * @author Octogonapus
 */

public class Shader
{
    public static Vector3f COLOR_WHITE = new Vector3f(1, 1, 1), COLOR_BLACK = new Vector3f(0, 0, 0);
    public static Vector3f LIGHT_AMBIENT = new Vector3f(0.1f, 0.1f, 0.1f);

	private int program;
	private HashMap<String, Integer> uniforms;
	
	public Shader()
	{
		program = glCreateProgram();
		uniforms = new HashMap<String, Integer>();
		
		if(program == 0)
		{
			System.err.println("Shader creation failed: Could not find valid memory location in constructor");
			System.exit(1);
		}
	}

    /**
     * Bind the shader for use.
     */
	public void bind()
	{
		glUseProgram(program);
	}

    /**
     * Update uniforms.
     */
	public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine)
	{
        //
	}

    /**
     * Add a uniform.
     *
     * @param uniform   The uniform to add
     */
	public void addUniform(String uniform)
	{
		int uniformLocation = glGetUniformLocation(program, uniform);
		
		if(uniformLocation == 0xFFFFFFFF)
		{
			System.err.println("Error: Could not find uniform: " + uniform);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		uniforms.put(uniform, uniformLocation);
	}

    /**
     * Add a vertex shader.
     *
     * @param text  The name of the vertex shader
     */
	public void addVertexShader(String text)
	{
		addProgram(text, GL_VERTEX_SHADER);
	}

    /**
     * Add a geometry shader.
     *
     * @param text  The name of the geometry shader
     */
	public void addGeometryShader(String text)
	{
		addProgram(text, GL_GEOMETRY_SHADER);
	}

    /**
     * Add a fragment shader.
     *
     * @param text  The name of the fragment shader
     */
	public void addFragmentShader(String text)
	{
		addProgram(text, GL_FRAGMENT_SHADER);
	}

    /**
     * Add a vertex shader from a file.
     *
     * @param text  The file name of the vertex shader
     */
    public void addVertexShaderFromFile(String text)
    {
        addProgram(loadShader(text), GL_VERTEX_SHADER);
    }

    /**
     * Add a geometry shader from a file.
     *
     * @param text  The file name of the geometry shader
     */
    public void addGeometryShaderFromFile(String text)
    {
        addProgram(loadShader(text), GL_GEOMETRY_SHADER);
    }

    /**
     * Add a fragment shader from a file.
     *
     * @param text  The file name of the fragment shader
     */
    public void addFragmentShaderFromFile(String text)
    {
        addProgram(loadShader(text), GL_FRAGMENT_SHADER);
    }

    public void setAttribLocation(String attribName, int location)
    {
        glBindAttribLocation(program, location, attribName);
    }

    /**
     * Compile this shader.
     */
	public void compileShader()
	{
		glLinkProgram(program);
		
		if(glGetProgram(program, GL_LINK_STATUS) == 0)
		{
			System.err.println(glGetProgramInfoLog(program, 1024));
			System.exit(1);
		}
		
		glValidateProgram(program);
		
		if(glGetProgram(program, GL_VALIDATE_STATUS) == 0)
		{
			System.err.println(glGetProgramInfoLog(program, 1024));
			System.exit(1);
		}
	}

    /**
     * Add a program.
     *
     * @param text  The name of the program
     * @param type  The type of the program
     */
	private void addProgram(String text, int type)
	{
		int shader = glCreateShader(type);
		
		if(shader == 0)
		{
			System.err.println("Shader creation failed: Could not find valid memory location when adding shader");
			System.exit(1);
		}
		
		glShaderSource(shader, text);
		glCompileShader(shader);
		
		if(glGetShader(shader, GL_COMPILE_STATUS) == 0)
		{
			System.err.println(glGetShaderInfoLog(shader, 1024));
			System.exit(1);
		}
		
		glAttachShader(program, shader);
	}

    /**
     * Load a shader by file name.
     *
     * @param fileName  The file name of the shader
     * @return          The loaded shader
     */
    private static String loadShader(String fileName)
    {
        StringBuilder shaderSource = new StringBuilder();
        BufferedReader shaderReader = null;
        final String INCLUDE_DIRECTIVE = "#include";

        try
        {
            shaderReader = new BufferedReader(new FileReader("./res/shaders/" + fileName));
            String line;

            while((line = shaderReader.readLine()) != null)
            {
                if (line.startsWith(INCLUDE_DIRECTIVE))
                {
                    shaderSource.append(loadShader(line.substring(INCLUDE_DIRECTIVE.length() + 2, line.length() - 1)));
                }
                else
                {
                    shaderSource.append(line).append("\n");
                }
            }

            shaderReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }


        return shaderSource.toString();
    }
	
	public void setUniformi(String uniformName, int value)
	{
		glUniform1i(uniforms.get(uniformName), value);
	}
	
	public void setUniformf(String uniformName, float value)
	{
		glUniform1f(uniforms.get(uniformName), value);
	}
	
	public void setUniform(String uniformName, Vector3f value)
	{
		glUniform3f(uniforms.get(uniformName), value.getX(), value.getY(), value.getZ());
	}
	
	public void setUniform(String uniformName, Matrix4f value)
	{
		glUniformMatrix4(uniforms.get(uniformName), true, Util.createFlippedBuffer(value));
	}
}
