package com.base.engine.components;

import com.base.engine.core.Input;
import com.base.engine.core.Matrix4f;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.RenderingEngine;

/**
 * @author Octogonapus
 */

public class Camera extends GameComponent
{
	public static final Vector3f yAxis = new Vector3f(0,1,0), xAxis = new Vector3f(1, 0, 0);

    private Matrix4f projection;
	
	public Camera(float fov, float aspect, float zNear, float zFar)
	{
        projection = new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
	}

	boolean mouseLocked = false;
	Vector2f centerPosition = new Vector2f(400/2, 300/2);

    /**
     * Takes input and reacts accordingly.
     */
    @Override
	public void input(float delta)
	{
		float sensitivity = 0.5f;
		float movAmt = 10 * delta;
		
		if(Input.getKey(Input.KEY_ESCAPE))
		{
			Input.setCursor(true);
			mouseLocked = false;
		}
		if(Input.getMouseDown(0))
		{
			Input.setMousePosition(centerPosition);
			Input.setCursor(false);
			mouseLocked = true;
		}
		
		if(Input.getKey(Input.KEY_W))
			move(getTransform().getRot().getForward(), movAmt);
		if(Input.getKey(Input.KEY_S))
			move(getTransform().getRot().getForward(), -movAmt);
		if(Input.getKey(Input.KEY_A))
			move(getTransform().getRot().getLeft(), movAmt);
		if(Input.getKey(Input.KEY_D))
			move(getTransform().getRot().getRight(), movAmt);
        if (Input.getKey(Input.KEY_Z))
            move(yAxis, movAmt);
        if (Input.getKey(Input.KEY_X))
            move(yAxis, -movAmt);
		
		if(mouseLocked)
		{
			Vector2f deltaPos = Input.getMousePosition().sub(centerPosition);
			
			boolean rotY = deltaPos.getX() != 0;
			boolean rotX = deltaPos.getY() != 0;

            if (rotY)
            {
                getTransform().rotate(yAxis, (float) Math.toRadians(deltaPos.getX() * sensitivity));
            }
			if(rotX)
            {
                getTransform().rotate(getTransform().getRot().getRight(), (float) Math.toRadians(-deltaPos.getY() * sensitivity));
            }
			if(rotY || rotX)
            {
                Input.setMousePosition(new Vector2f(400 / 2, 300 / 2));
            }
		}
	}

    @Override
    public void addToRenderingEngine(RenderingEngine renderingEngine)
    {
        renderingEngine.addCamera(this);
    }

    /**
     * Moves an amount in a direction.
     *
     * @param dir   The direction to move in
     * @param amt   The amount to move
     */
	public void move(Vector3f dir, float amt)
	{
        getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

    /**
     * Gets this camera's projection matrix.
     *
     * @return  This camera's projection matrix
     */
    public Matrix4f getViewProjection()
    {
        Matrix4f cameraRotation = getTransform().getTransformedRot().conjugate().toRotationMatrix();
        Vector3f cameraPos = getTransform().getTransformedPos().mul(-1);
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());

        return projection.mul(cameraRotation.mul(cameraTranslation));
    }
}
