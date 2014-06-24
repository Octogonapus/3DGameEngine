package com.base.engine;

/**
 * @author Octogonapus
 */

public class PhongShader extends Shader
{
    private static final PhongShader instance = new PhongShader();

    public static PhongShader getInstance()
    {
        return instance;
    }

    private static Vector3f ambientLight = Shader.LIGHT_AMBIENT;
    private static DirectionalLight directionalLight = new DirectionalLight(new BaseLight(Shader.COLOR_WHITE, 0), new Vector3f(0, 0, 0));

    private PhongShader()
    {
        super();

        addVertexShader(ResourceLoader.loadShader("phongVertex.vs"));
        addFragmentShader(ResourceLoader.loadShader("phongFragment.fs"));
        compileShader();

        addUniform("transform");
        addUniform("transformProjected");
        addUniform("baseColor");
        addUniform("ambientLight");

        addUniform("directionalLight.base.color");
        addUniform("directionalLight.base.intensity");
        addUniform("directionalLight.direction");
    }

    /**
     * Update uniforms.
     * worldMatrix and projectedMatrix are used instead of a straight up transformation.
     *
     * @param worldMatrix       World matrix
     * @param projectedMatrix   Projection matrix
     * @param material          Material for color and texture
     */
    public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material)
    {
        if(material.getTexture() != null)
            material.getTexture().bind();
        else
            RenderUtil.unbindTextures();

        setUniform("transform", worldMatrix);
        setUniform("transformProjected", projectedMatrix);
        setUniform("baseColor", material.getColor());
        setUniform("ambientLight", ambientLight);
        setUniform("directionalLight", directionalLight);
    }

    public void setUniform(String uniformName, BaseLight baseLight) {
        setUniform(uniformName + ".color", baseLight.getColor());
        setUniformf(uniformName + ".intensity", baseLight.getIntsensity());
    }

    public void setUniform(String uniformName, DirectionalLight directionalLight)
    {
        setUniform(uniformName + ".base", directionalLight.getBase());
        setUniform(uniformName + ".direction", directionalLight.getDirection());
    }

    public static Vector3f getAmbientLight() {
        return ambientLight;
    }

    public static void setAmbientLight(Vector3f ambientLight) {
        PhongShader.ambientLight = ambientLight;
    }

    public static void setDirectionalLight(DirectionalLight directionalLight) {
        PhongShader.directionalLight = directionalLight;
    }
}
