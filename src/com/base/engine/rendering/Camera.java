package com.base.engine.rendering;

import com.base.engine.core.*;

/**
 * @author Octogonapus
 */

public class Camera
{
	public static final Vector3f yAxis = new Vector3f(0,1,0);
	
	private Vector3f pos, forward, up;
    private Matrix4f projection;
	
	public Camera(float fov, float aspect, float zNear, float zFar)
	{
		this.pos = new Vector3f(0, 0 ,0);
		this.forward = new Vector3f(0, 0 ,1).normalized();
		this.up = new Vector3f(0, 1 ,0).normalized();
        projection = new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
	}

	boolean mouseLocked = false;
	Vector2f centerPosition = new Vector2f(Window.getWidth()/2, Window.getHeight()/2);

    /**
     * Takes input and reacts accordingly.
     */
	public void input()
	{
		float sensitivity = 0.5f;
		float movAmt = (float)(10 * Time.getDelta());
		
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
			move(getForward(), movAmt);
		if(Input.getKey(Input.KEY_S))
			move(getForward(), -movAmt);
		if(Input.getKey(Input.KEY_A))
			move(getLeft(), movAmt);
		if(Input.getKey(Input.KEY_D))
			move(getRight(), movAmt);
        if (Input.getKey(Input.KEY_Z))
            move(yAxis, movAmt);
        if (Input.getKey(Input.KEY_X))
            move(yAxis, -movAmt);
		
		if(mouseLocked)
		{
			Vector2f deltaPos = Input.getMousePosition().sub(centerPosition);
			
			boolean rotY = deltaPos.getX() != 0;
			boolean rotX = deltaPos.getY() != 0;
			
			if(rotY)
				rotateY(deltaPos.getX() * sensitivity);
			if(rotX)
				rotateX(-deltaPos.getY() * sensitivity);
				
			if(rotY || rotX)
				Input.setMousePosition(new Vector2f(Window.getWidth()/2, Window.getHeight()/2));
		}
	}

    /**
     * Moves an amount in a direction.
     *
     * @param dir   The direction to move in
     * @param amt   The amount to move
     */
	public void move(Vector3f dir, float amt)
	{
		pos = pos.add(dir.mul(amt));
	}

    /**
     * Rotates around an x axis.
     *
     * @param angle Angle to rotate
     */
    public void rotateX(float angle)
    {
        Vector3f hAxis = yAxis.cross(forward).normalized();

        forward = forward.rotate(angle, hAxis).normalized();

        up = forward.cross(hAxis).normalized();
    }

    /**
     * Rotates around a y axis.
     *
     * @param angle Angle to rotate
     */
	public void rotateY(float angle)
	{
		Vector3f hAxis = yAxis.cross(forward).normalized();
		
		forward = forward.rotate(angle, yAxis).normalized();
		
		up = forward.cross(hAxis).normalized();
	}

    /**
     * Get the left vector of this camera.
     *
     * @return  The left vector
     */
	public Vector3f getLeft()
	{
		return forward.cross(up).normalized();
	}

    /**
     * Get the right vector of this camera.
     *
     * @return  The right vector
     */
	public Vector3f getRight()
	{
		return up.cross(forward).normalized();
	}

    /**
     * Gets this camera's projection matrix.
     *
     * @return  This camera's projection matrix
     */
    public Matrix4f getViewProjection()
    {
        Matrix4f cameraRotation = new Matrix4f().initRotation(forward, up);
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(-pos.getX(), -pos.getY(), -pos.getZ());

        return projection.mul(cameraRotation.mul(cameraTranslation));
    }
	
	public Vector3f getPos()
	{
		return pos;
	}

	public void setPos(Vector3f pos)
	{
		this.pos = pos;
	}

	public Vector3f getForward()
	{
		return forward;
	}

	public void setForward(Vector3f forward)
	{
		this.forward = forward;
	}

	public Vector3f getUp()
	{
		return up;
	}

	public void setUp(Vector3f up)
	{
		this.up = up;
	}
}
