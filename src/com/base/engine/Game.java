package com.base.engine;

public class Game 
{
	private Mesh mesh = new Mesh();
	private Shader shader;
	private Material material;
	private Transform transform;
	private Camera camera;
	
	public Game()
	{
		//mesh = ResourceLoader.loadMesh("boxSmallFaceCount.obj");
		material = new Material(ResourceLoader.loadTexture("test.png"), Shader.COLOR_WHITE);
        shader = PhongShader.getInstance();
        transform = new Transform();
		camera = new Camera();

        Vertex[] vertices = new Vertex[] {
                new Vertex(new Vector3f(-1.0f, -1.0f, 0.5773f),    new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(0.0f, -1.0f, -1.15475f),   new Vector2f(0.5f, 0.0f)),
                new Vertex(new Vector3f(1.0f, -1.0f, 0.5773f),	   new Vector2f(1.0f, 0.0f)),
                new Vertex(new Vector3f(0.0f, 1.0f, 0.0f),         new Vector2f(0.5f, 1.0f))
        };

        int indices[] = {
                0, 3, 1,
                1, 3, 2,
                2, 3, 0,
                1, 2, 0
        };

		mesh.addVertices(vertices, indices, true);
		
		Transform.setProjection(70f, Window.getWidth(), Window.getHeight(), 0.1f, 1000);
		Transform.setCamera(camera);

        PhongShader.setAmbientLight(Shader.LIGHT_AMBIENT);
        PhongShader.setDirectionalLight(new DirectionalLight(new BaseLight(Shader.COLOR_WHITE, 0.8f), new Vector3f(1, 1, 1)));
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
		
		transform.setTranslation(0, 0, 3);
		transform.setRotation(0, 180 * sinTemp, 0);
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
