package com.base.engine.core;

/**
 * @author Octogonapus
 */

public class Transform
{
	private Vector3f pos;
	private Quaternion rot;
	private Vector3f scale;
	
	public Transform()
	{
		pos = new Vector3f(0, 0, 0);
		rot = new Quaternion(0, 0, 0, 1);
		scale = new Vector3f(1, 1, 1);
	}

    /**
     * Get the transformation matrix.
     *
     * @return  The transformation matrix
     */
	public Matrix4f getTransformation()
	{
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());
		
		return translationMatrix.mul(rotationMatrix.mul(scaleMatrix));
	}
	
	public Vector3f getPos()
	{
		return pos;
	}

	public void setPos(Vector3f pos)
	{
		this.pos = pos;
	}
	
	public void setTranslation(float x, float y, float z)
	{
		this.pos = new Vector3f(x, y, z);
	}

    public Quaternion getRot()
    {
        return rot;
    }

    public void setRot(Quaternion rot)
    {
        this.rot = rot;
    }

	public Vector3f getScale()
	{
		return scale;
	}

	public void setScale(Vector3f scale)
	{
		this.scale = scale;
	}
	
	public void setScale(float x, float y, float z)
	{
		this.scale = new Vector3f(x, y, z);
	}
}
