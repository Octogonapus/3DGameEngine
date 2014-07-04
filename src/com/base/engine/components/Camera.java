package com.base.engine.components;

import com.base.engine.core.*;

/**
 * @author Octogonapus
 */

public class Camera extends GameComponent
{
    private Matrix4f projection;
	
	public Camera(float fov, float aspect, float zNear, float zFar)
	{
        projection = new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
	}

    @Override
    public void addToEngine(CoreEngine engine)
    {
        engine.getRenderingEngine().addCamera(this);
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
