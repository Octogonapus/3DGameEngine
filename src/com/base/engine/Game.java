package com.base.engine;
public class Game 
{
	private Mesh mesh;
	private Shader shader;
	private Material material;
	private Transform transform;
	private Camera camera;
	
	public Game()
	{
		mesh = ResourceLoader.loadMesh("monkey.obj");
		material = new Material(ResourceLoader.loadTexture("test.png"), new Vector3f(0,1,1));
		shader = BasicShader.getInstance();
		camera = new Camera();
		
		Transform.setProjection(70f, Window.getWidth(), Window.getHeight(), 0.1f, 1000);
		Transform.setCamera(camera);
		transform = new Transform();
	}

    /**
     * Handle input.
     */
	public void input()
	{
		camera.input();
	}
	
	float temp = 0.0f;

    /**
     * Update objects.
     */
	public void update()
	{
		temp += Time.getDelta();
		
		float sinTemp = (float)Math.sin(temp);
		
		//transform.setTranslation(0, 0, 3);
		transform.setRotation(0, sinTemp * 180, 0);
		//transform.setScale(0.7f * sinTemp, 0.7f * sinTemp, 0.7f * sinTemp);
	}

    /**
     * Render objects.
     */
	public void render()
	{
		RenderUtil.setClearColor(Transform.getCamera().getPos().div(2048f).abs());
		shader.bind();
		shader.updateUniforms(transform.getTransformation(), transform.getProjectedTransformation(), material);
		mesh.draw();
	}
}
