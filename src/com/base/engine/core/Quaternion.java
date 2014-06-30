package com.base.engine.core;

/**
 * @author Octogonapus
 */

public class Quaternion
{
	private float x;
	private float y;
	private float z;
	private float w;
	
	public Quaternion(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

    /**
     * Get the length of this quaternion.
     *
     * @return  The length of this quaternion
     */
	public float length()
	{
		return (float)Math.sqrt(x * x + y * y + z * z + w * w);
	}

    /**
     * Normalize this quaternion.
     *
     * @return  The normalized quaternion.
     */
	public Quaternion normalize()
	{
		float length = length();
		
		return new Quaternion(x / length, y / length, z / length, w / length);
	}

    /**
     * Get the conjugate of this quaternion.
     *
     * @return  The conjugate of this quaternion
     */
	public Quaternion conjugate()
	{
		return new Quaternion(-x, -y, -z, w);
	}

    /**
     * Multiply two quaternions.
     *
     * @param r The other quaternion
     * @return  The product
     */
	public Quaternion mul(Quaternion r)
	{
		float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();
		
		return new Quaternion(x_, y_, z_, w_);
	}

    /**
     * Multiply a quaternion and a vector.
     *
     * @param r The vector
     * @return  The product
     */
	public Quaternion mul(Vector3f r)
	{
		float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ =  w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ =  w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ =  w * r.getZ() + x * r.getY() - y * r.getX();
		
		return new Quaternion(x_, y_, z_, w_);
	}
	
	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public float getZ()
	{
		return z;
	}

	public void setZ(float z)
	{
		this.z = z;
	}

	public float getW()
	{
		return w;
	}

	public void setW(float w)
	{
		this.w = w;
	}
}