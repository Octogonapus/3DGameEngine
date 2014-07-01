package com.base.engine.core;

/**
 * @author Octogonapus
 */

public class Vector2f 
{
	private float x;
	private float y;
	
	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

    /**
     * Get the length of this vector.
     *
     * @return  The length of this vector
     */
	public float length()
	{
		return (float)Math.sqrt(x * x + y * y);
	}

    /**
     * Get the dot product of two vectors.
     *
     * @param r The other vector
     * @return  The dot product
     */
	public float dot(Vector2f r)
	{
		return x * r.getX() + y * r.getY();
	}

    /**
     * Gets the cross product of two vectors.
     *
     * @param r The other vector
     * @return  The cross product
     */
    public float cross(Vector2f r)
    {
        return x * r.getY() - y * r.getX();
    }

    /**
     * Normalize this vector.
     *
     * @return  The normalized vector
     */
	public Vector2f normalized()
	{
		float length = length();
		
		return new Vector2f(x / length, y / length);
	}

    /**
     * Rotate this vector.
     *
     * @param angle Angle to rotate
     * @return      The rotated vector
     */
	public Vector2f rotate(float angle)
	{
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		
		return new Vector2f((float)(x * cos - y * sin),(float)(x * sin + y * cos));
	}

    /**
     * Interpolate this vector.
     *
     * @param dest          Destination vector
     * @param lerpFactor    Interpolation factor
     * @return              Interpolated vector
     */
    public Vector2f lerp(Vector2f dest, float lerpFactor)
    {
        return dest.sub(this).mul(lerpFactor).add(this);
    }
	
	public Vector2f add(Vector2f r)
	{
		return new Vector2f(x + r.getX(), y + r.getY());
	}
	
	public Vector2f add(float r)
	{
		return new Vector2f(x + r, y + r);
	}
	
	public Vector2f sub(Vector2f r)
	{
		return new Vector2f(x - r.getX(), y - r.getY());
	}
	
	public Vector2f sub(float r)
	{
		return new Vector2f(x - r, y - r);
	}
	
	public Vector2f mul(Vector2f r)
	{
		return new Vector2f(x * r.getX(), y * r.getY());
	}
	
	public Vector2f mul(float r)
	{
		return new Vector2f(x * r, y * r);
	}
	
	public Vector2f div(Vector2f r)
	{
		return new Vector2f(x / r.getX(), y / r.getY());
	}
	
	public Vector2f div(float r)
	{
		return new Vector2f(x / r, y / r);
	}
	
	public Vector2f abs()
	{
		return new Vector2f(Math.abs(x), Math.abs(y));
	}
	
	public String toString()
	{
		return "(" + x + " " + y + ")";
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

    public boolean equals(Vector2f r)
    {
        return x == r.getX() && y == r.getY();
    }

    public float max()
    {
        return Math.max(x, y);
    }
}
