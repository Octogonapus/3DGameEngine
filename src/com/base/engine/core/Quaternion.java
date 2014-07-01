package com.base.engine.core;

/**
 * @author Octogonapus
 */

public class Quaternion
{
	private float x, y, z, w;
	
	public Quaternion(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

    public Quaternion(Vector3f axis, float angle)
    {
        float sinHalfAngle = (float)Math.sin(angle / 2);
        float cosHalfAngle = (float)Math.cos(angle / 2);

        this.x = axis.getX() * sinHalfAngle;
        this.y = axis.getY() * sinHalfAngle;
        this.z = axis.getZ() * sinHalfAngle;
        this.w = cosHalfAngle;
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
	public Quaternion normalized()
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
     * Subtract a quaternion from this quaternion.
     *
     * @param r The other quaternion
     * @return  The difference
     */
    public Quaternion sub(Quaternion r)
    {
        return new Quaternion(x - r.getX(), y - r.getY(), z - r.getZ(), w - r.getW());
    }

    /**
     * Add a quaternion to this quaternion.
     *
     * @param r The other quaternion
     * @return  The sum
     */
    public Quaternion add(Quaternion r)
    {
        return new Quaternion(x + r.getX(), y + r.getY(), z + r.getZ(), w + r.getW());
    }

    /**
     * Multiply this quaternion by a float.
     *
     * @param r The float
     * @return  The product
     */
    public Quaternion mul(float r)
    {
        return new Quaternion(x * r, y * r, z * r, w * r);
    }

    /**
     * Multiply this quaternion by another quaternion.
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
     * Multiply this quaternion by a vector.
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

    /**
     * Transform this quaternion into a rotation matrix.
     *
     * @return  This quaternion represented as a rotation matrix
     */
    public Matrix4f toRotationMatrix()
    {
        Vector3f forward = new Vector3f(2.0f * (x*z - w*y), 2.0f * (y*z + w*x), 1.0f - 2.0f * (x*x + y*y));
        Vector3f up = new Vector3f(2.0f * (x*y + w*z), 1.0f - 2.0f * (x*x + z*z), 2.0f * (y*z - w*x));
        Vector3f right = new Vector3f(1.0f - 2.0f * (y*y + z*z), 2.0f * (x*y - w*z), 2.0f * (x*z + w*y));

        return new Matrix4f().initRotation(forward, up, right);
    }

    public Vector3f getForward()
    {
        return new Vector3f(0, 0, 1).rotate(this);
    }

    public Vector3f getBack()
    {
        return new Vector3f(0, 0, -1).rotate(this);
    }

    public Vector3f getUp()
    {
        return new Vector3f(0, 1, 0).rotate(this);
    }

    public Vector3f getDown()
    {
        return new Vector3f(0, -1, 0).rotate(this);
    }

    public Vector3f getRight()
    {
        return new Vector3f(1, 0, 0).rotate(this);
    }

    public Vector3f getLeft()
    {
        return new Vector3f(-1, 0, 0).rotate(this);
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

    public Quaternion set(float x, float y, float z, float w)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public Quaternion set(Quaternion r)
    {
        set(r.getX(), r.getY(), r.getZ(), r.getW());
        return this;
    }

    public boolean equals(Quaternion r)
    {
        return x == r.getX() && y == r.getY() && z == r.getZ() && w == r.getW();
    }
}
