package com.base.engine.rendering;

import com.base.engine.components.BaseLight;
import com.base.engine.components.DirectionalLight;
import com.base.engine.components.PointLight;
import com.base.engine.components.SpotLight;
import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;
import com.base.engine.core.Util;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.resourceManagement.ShaderResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
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

    private static HashMap<String, WeakReference<ShaderResource>> loadedShaders = new HashMap<String, WeakReference<ShaderResource>>(0);
	private ShaderResource resource;
    private String fileName;

    private class GLSLStruct
    {
        public String name;
        public String type;
    }

	public Shader(String fileName)
	{
        this.fileName = fileName;
        WeakReference<ShaderResource> ref = loadedShaders.get(fileName);

        if (ref != null)
        {
            resource = ref.get();
            if (resource != null)
            {
                resource.addReference();
            }
        }
        else
        {
            resource = new ShaderResource();

            String vertexShaderText = loadShader(fileName + ".vs");
            String fragmentShaderText = loadShader(fileName + ".fs");

            addVertexShader(vertexShaderText);
            addFragmentShader(fragmentShaderText);

            setAllAttribLocations(vertexShaderText);

            compileShader();

            addAllUniforms(vertexShaderText);
            addAllUniforms(fragmentShaderText);
        }
	}

    /**
     * Bind the shader for use.
     */
	public void bind()
	{
		glUseProgram(resource.getProgram());
	}

    /**
     * Update uniforms.
     *
     * @param transform         The transform
     * @param material          The material
     * @param renderingEngine   The Rendering Engine that holds the camera
     */
	public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine)
	{
        Matrix4f worldMatrix = transform.getTransformation();
        Matrix4f projectedMatrix = renderingEngine.getMainCamera().getViewProjection().mul(worldMatrix);

        for (int i = 0; i < resource.getUniformNames().size(); i++)
        {
            String uniformName = resource.getUniformNames().get(i);
            String uniformType = resource.getUniformTypes().get(i);

            if (uniformType.equals("sampler2D"))
            {
                int samplerSlot = renderingEngine.getSamplerSlot(uniformName);
                material.getTexture(uniformName).bind(samplerSlot);
                setUniformi(uniformName, samplerSlot);
            }
            else if (uniformName.startsWith("T_"))
            {
                if (uniformName.equals("T_MVP"))
                {
                    setUniform(uniformName, projectedMatrix);
                }
                else if (uniformName.equals("T_model"))
                {
                    setUniform(uniformName, worldMatrix);
                }
                else
                {
                    throw new IllegalArgumentException(uniformName + " is not a valid component of Transform");
                }
            }
            else if (uniformName.startsWith("R_"))
            {
                String unprefixedUniformName = uniformName.substring(2);

                if (uniformType.equals("vec3"))
                {
                    setUniform(uniformName, renderingEngine.getVector3f(unprefixedUniformName));
                }
                else if (uniformType.equals("float"))
                {
                    setUniformf(uniformName, renderingEngine.getFloat(unprefixedUniformName));
                }
                else if (uniformType.equals("DirectionalLight"))
                {
                    setUniformDirectionalLight(uniformName, (DirectionalLight) renderingEngine.getActiveLight());
                }
                else if (uniformType.equals("PointLight"))
                {
                    setUniformPointLight(uniformName, (PointLight) renderingEngine.getActiveLight());
                }
                else if (uniformType.equals("SpotLight"))
                {
                    setUniformSpotLight(uniformName, (SpotLight) renderingEngine.getActiveLight());
                }

                else
                {
                    renderingEngine.updateUniformStruct(transform, material, this, uniformName, uniformType);
                }
            }
            else if (uniformName.startsWith("C_"))
            {
                if (uniformName.equals("C_eyePos"))
                {
                    setUniform(uniformName, renderingEngine.getMainCamera().getTransform().getTransformedPos());
                }
                else
                {
                    throw new IllegalArgumentException(uniformName + " is not a valid component of Camera");
                }
            }
            else
            {
                if (uniformType.equals("vec3"))
                {
                    setUniform(uniformName, material.getVector3f(uniformName));
                }
                else if (uniformType.equals("float"))
                {
                    setUniformf(uniformName, material.getFloat(uniformName));
                }
                else
                {
                    throw new IllegalArgumentException(uniformType + " is not a supported type in Material");
                }
            }
        }
	}

    private HashMap<String, ArrayList<GLSLStruct>> findUniformStructs(String shaderText)
    {
        HashMap<String, ArrayList<GLSLStruct>> result = new HashMap<String, ArrayList<GLSLStruct>>();

        final String STRUCT_KEYWORD = "struct";
        int structStartLocation = shaderText.indexOf(STRUCT_KEYWORD);
        while(structStartLocation != -1)
        {
            int nameBegin = structStartLocation + STRUCT_KEYWORD.length() + 1;
            int braceBegin = shaderText.indexOf("{", nameBegin);
            int braceEnd = shaderText.indexOf("}", braceBegin);

            String structName = shaderText.substring(nameBegin, braceBegin).trim();
            ArrayList<GLSLStruct> glslStructs = new ArrayList<GLSLStruct>();

            int componentSemicolonPos = shaderText.indexOf(";", braceBegin);
            while(componentSemicolonPos != -1 && componentSemicolonPos < braceEnd)
            {
                int componentNameStart = componentSemicolonPos;

                while(!Character.isWhitespace(shaderText.charAt(componentNameStart - 1)))
                    componentNameStart--;

                int componentTypeEnd = componentNameStart - 1;
                int componentTypeStart = componentTypeEnd;

                while(!Character.isWhitespace(shaderText.charAt(componentTypeStart - 1)))
                    componentTypeStart--;

                String componentName = shaderText.substring(componentNameStart, componentSemicolonPos);
                String componentType = shaderText.substring(componentTypeStart, componentTypeEnd);

                GLSLStruct glslStruct = new GLSLStruct();
                glslStruct.name = componentName;
                glslStruct.type = componentType;

                glslStructs.add(glslStruct);

                componentSemicolonPos = shaderText.indexOf(";", componentSemicolonPos + 1);
            }

            result.put(structName, glslStructs);

            structStartLocation = shaderText.indexOf(STRUCT_KEYWORD, structStartLocation + STRUCT_KEYWORD.length());
        }

        return result;
    }

    /**
     * Add all uniforms in a sample of shader code.
     *
     * @param shaderText    The sample of shader code to add uniforms from
     */
    private void addAllUniforms(String shaderText)
    {
        HashMap<String, ArrayList<GLSLStruct>> structs = findUniformStructs(shaderText);

        final String UNIFORM_KEYWORD = "uniform";
        int uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD);
        while(uniformStartLocation != -1)
        {
            int begin = uniformStartLocation + UNIFORM_KEYWORD.length() + 1;
            int end = shaderText.indexOf(";", begin);

            String uniformLine = shaderText.substring(begin, end);

            int whiteSpacePos = uniformLine.indexOf(' ');
            String uniformName = uniformLine.substring(whiteSpacePos + 1, uniformLine.length());
            String uniformType = uniformLine.substring(0, whiteSpacePos);

            resource.getUniformNames().add(uniformName);
            resource.getUniformTypes().add(uniformType);
            addUniform(uniformName, uniformType, structs);

            uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD, uniformStartLocation + UNIFORM_KEYWORD.length());
        }
    }

    /**
     * Add a uniform with struct support.
     *
     * @param uniformName   The name of the uniform
     * @param uniformType   The type of the uniform
     * @param structs       An array of structs
     */
    private void addUniform(String uniformName, String uniformType, HashMap<String, ArrayList<GLSLStruct>> structs)
    {
        boolean addThis = true;
        ArrayList<GLSLStruct> structComponents = structs.get(uniformType);

        if(structComponents != null)
        {
            addThis = false;
            for(GLSLStruct struct : structComponents)
            {
                addUniform(uniformName + "." + struct.name, struct.type, structs);
            }
        }

        if(!addThis)
        {
            return;
        }

        int uniformLocation = glGetUniformLocation(resource.getProgram(), uniformName);

        if(uniformLocation == 0xFFFFFFFF)
        {
            System.err.println("Error: Could not find uniform: " + uniformName);
            new Exception().printStackTrace();
            System.exit(1);
        }

        resource.getUniforms().put(uniformName, uniformLocation);
    }

    /**
     * Add a vertex shader.
     *
     * @param text  The name of the vertex shader
     */
    private void addVertexShader(String text)
    {
        addProgram(text, GL_VERTEX_SHADER);
    }

    /**
     * Add a geometry shader.
     *
     * @param text  The name of the geometry shader
     */
    private void addGeometryShader(String text)
    {
        addProgram(text, GL_GEOMETRY_SHADER);
    }

    /**
     * Add a fragment shader.
     *
     * @param text  The name of the fragment shader
     */
    private void addFragmentShader(String text)
    {
        addProgram(text, GL_FRAGMENT_SHADER);
    }

    /**
     * Set an attribute location.
     *
     * @param attribName    The name of the attribute
     * @param location      The location of the attribute
     */
    public void setAttribLocation(String attribName, int location)
    {
        glBindAttribLocation(resource.getProgram(), location, attribName);
    }

    /**
     * Set all attribute locations in a sample of shader code.
     *
     * @param shaderText    The sample of shader code to add attributes from
     */
    private void setAllAttribLocations(String shaderText)
    {
        final String ATTRIBUTE_KEYWORD = "attribute";
        int attributeStartLocation = shaderText.indexOf(ATTRIBUTE_KEYWORD);
        int attribNumber = 0;
        while(attributeStartLocation != -1)
        {
            int begin = attributeStartLocation + ATTRIBUTE_KEYWORD.length() + 1;
            int end = shaderText.indexOf(";", begin);

            String attributeLine = shaderText.substring(begin, end);
            String attributeName = attributeLine.substring(attributeLine.indexOf(' ') + 1, attributeLine.length());

            setAttribLocation(attributeName, attribNumber);
            attribNumber++;

            attributeStartLocation = shaderText.indexOf(ATTRIBUTE_KEYWORD, attributeStartLocation + ATTRIBUTE_KEYWORD.length());
        }
    }

    /**
     * Compile this shader.
     */
	public void compileShader()
	{
		glLinkProgram(resource.getProgram());
		
		if(glGetProgram(resource.getProgram(), GL_LINK_STATUS) == 0)
		{
			System.err.println(glGetProgramInfoLog(resource.getProgram(), 1024));
			System.exit(1);
		}
		
		glValidateProgram(resource.getProgram());
		
		if(glGetProgram(resource.getProgram(), GL_VALIDATE_STATUS) == 0)
		{
			System.err.println(glGetProgramInfoLog(resource.getProgram(), 1024));
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

        if(glGetShaderi(shader, GL_COMPILE_STATUS) == 0)
        {
            System.err.println(glGetShaderInfoLog(shader, 1024));
            System.exit(1);
        }

        glAttachShader(resource.getProgram(), shader);
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
                if(line.startsWith(INCLUDE_DIRECTIVE))
                {
                    shaderSource.append(loadShader(line.substring(INCLUDE_DIRECTIVE.length() + 2, line.length() - 1)));
                }
                else
                    shaderSource.append(line).append("\n");
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
		glUniform1i(resource.getUniforms().get(uniformName), value);
	}
	
	public void setUniformf(String uniformName, float value)
	{
		glUniform1f(resource.getUniforms().get(uniformName), value);
	}
	
	public void setUniform(String uniformName, Vector3f value)
	{
		glUniform3f(resource.getUniforms().get(uniformName), value.getX(), value.getY(), value.getZ());
	}
	
	public void setUniform(String uniformName, Matrix4f value)
	{
		glUniformMatrix4(resource.getUniforms().get(uniformName), true, Util.createFlippedBuffer(value));
	}

    public void setUniformBaseLight(String uniformName, BaseLight baseLight) {
        setUniform(uniformName + ".color", baseLight.getColor());
        setUniformf(uniformName + ".intensity", baseLight.getIntensity());
    }

    public void setUniformDirectionalLight(String uniformName, DirectionalLight directionalLight)
    {
        setUniformBaseLight(uniformName + ".base", directionalLight);
        setUniform(uniformName + ".direction", directionalLight.getDirection());
    }

    public void setUniformPointLight(String uniformName, PointLight pointLight)
    {
        setUniformBaseLight(uniformName + ".base", pointLight);
        setUniformf(uniformName + ".atten.constant", pointLight.getAttenuation().getConstant());
        setUniformf(uniformName + ".atten.linear", pointLight.getAttenuation().getLinear());
        setUniformf(uniformName + ".atten.exponent", pointLight.getAttenuation().getExponent());
        setUniform(uniformName + ".position", pointLight.getTransform().getTransformedPos());
        setUniformf(uniformName + ".range", pointLight.getRange());
    }

    public void setUniformSpotLight(String uniformName, SpotLight spotLight)
    {
        setUniformPointLight(uniformName + ".pointLight", spotLight);
        setUniform(uniformName + ".direction", spotLight.getDirection());
        setUniformf(uniformName + ".cutoff", spotLight.getCutoff());
    }

    @Override
    protected void finalize() throws Throwable
    {
        if (resource.removeReference())
        {
            loadedShaders.remove(fileName);
        }

        super.finalize();
    }
}
