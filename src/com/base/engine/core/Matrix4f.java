package com.base.engine.core;

/**
 * @author Octogonapus
 */

public class Matrix4f
{
	private float[][] m;
	
	public Matrix4f()
	{
		m = new float[4][4];
	}

    /**
     * Initialize an identity matrix.
     *
     * @return  An identity matrix.
     */
	public Matrix4f initIdentity()
	{
		m[0][0] = 1;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = 0;
		m[1][0] = 0;	m[1][1] = 1;	m[1][2] = 0;	m[1][3] = 0;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = 1;	m[2][3] = 0;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;
		
		return this;
	}

    /**
     * Initialize a translation matrix.
     *
     * @param x X amount to translate
     * @param y Y amount to translate
     * @param z Z amount to translate
     * @return  A translation matrix
     */
	public Matrix4f initTranslation(float x, float y, float z)
	{
		m[0][0] = 1;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = x;
		m[1][0] = 0;	m[1][1] = 1;	m[1][2] = 0;	m[1][3] = y;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = 1;	m[2][3] = z;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;
		
		return this;
	}

    /**
     * Initialize a rotation matrix.
     *
     * @param x X amount to rotate
     * @param y Y amount to rotate
     * @param z Z amount to rotate
     * @return  A translation matrix
     */
	public Matrix4f initRotation(float x, float y, float z)
	{
		Matrix4f rx = new Matrix4f();
		Matrix4f ry = new Matrix4f();
		Matrix4f rz = new Matrix4f();
		
		x = (float)Math.toRadians(x);
		y = (float)Math.toRadians(y);
		z = (float)Math.toRadians(z);
		
		rx.m[0][0] = 1;					    rx.m[0][1] = 0;					    rx.m[0][2] = 0;					    rx.m[0][3] = 0;
		rx.m[1][0] = 0;					    rx.m[1][1] = (float)Math.cos(x);    rx.m[1][2] = -(float)Math.sin(x);   rx.m[1][3] = 0;
		rx.m[2][0] = 0;					    rx.m[2][1] = (float)Math.sin(x);    rx.m[2][2] = (float)Math.cos(x);    rx.m[2][3] = 0;
		rx.m[3][0] = 0;					    rx.m[3][1] = 0;					    rx.m[3][2] = 0;					    rx.m[3][3] = 1;
		
		ry.m[0][0] = (float)Math.cos(y);    ry.m[0][1] = 0;					    ry.m[0][2] = -(float)Math.sin(y);   ry.m[0][3] = 0;
		ry.m[1][0] = 0;					    ry.m[1][1] = 1;					    ry.m[1][2] = 0;					    ry.m[1][3] = 0;
		ry.m[2][0] = (float)Math.sin(y);    ry.m[2][1] = 0;					    ry.m[2][2] = (float)Math.cos(y);    ry.m[2][3] = 0;
		ry.m[3][0] = 0;					    ry.m[3][1] = 0;					    ry.m[3][2] = 0;					    ry.m[3][3] = 1;

        rz.m[0][0] = (float)Math.cos(z);    rz.m[0][1] = -(float)Math.sin(z);   rz.m[0][2] = 0;			            rz.m[0][3] = 0;
        rz.m[1][0] = (float)Math.sin(z);    rz.m[1][1] = (float)Math.cos(z);    rz.m[1][2] = 0;					    rz.m[1][3] = 0;
        rz.m[2][0] = 0;					    rz.m[2][1] = 0;					    rz.m[2][2] = 1;					    rz.m[2][3] = 0;
        rz.m[3][0] = 0;					    rz.m[3][1] = 0;					    rz.m[3][2] = 0;					    rz.m[3][3] = 1;
		
		m = rz.mul(ry.mul(rx)).getM();
		
		return this;
	}

    /**
     * Initialize a scaling matrix.
     *
     * @param x X amount to scale
     * @param y Y amount to scale
     * @param z Z amount to scale
     * @return  A scaling matrix
     */
	public Matrix4f initScale(float x, float y, float z)
	{
		m[0][0] = x;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = 0;
		m[1][0] = 0;	m[1][1] = y;	m[1][2] = 0;	m[1][3] = 0;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = z;	m[2][3] = 0;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;
		
		return this;
	}

    /**
     * Initialize a perspective matrix.
     *
     * @param fov    Field of view
     * @param ar     Aspect ratio
     * @param zNear  Near clipping plane
     * @param zFar   Far clipping plane
     * @return       A projection matrix
     */
	public Matrix4f initPerspective(float fov, float ar, float zNear, float zFar)
	{
		float tanHalfFOV = (float)Math.tan(fov / 2);
		float zRange = zNear - zFar;
		
		m[0][0] = 1.0f / (tanHalfFOV * ar);	m[0][1] = 0;					m[0][2] = 0;	m[0][3] = 0;
		m[1][0] = 0;						m[1][1] = 1.0f / tanHalfFOV;	m[1][2] = 0;	m[1][3] = 0;
		m[2][0] = 0;						m[2][1] = 0;					m[2][2] = (-zNear -zFar)/zRange;	m[2][3] = 2 * zFar * zNear / zRange;
		m[3][0] = 0;						m[3][1] = 0;					m[3][2] = 1;	m[3][3] = 0;
		
		
		return this;
	}

    /**
     * Initialize an orthographic matrix.
     *
     * @return  An identity matrix.
     */
    public Matrix4f initOrthographic(float left, float right, float bottom, float top, float near, float far)
    {
        float width = right - left;
        float height = top - bottom;
        float depth = far - near;

        m[0][0] = 2 / width;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = -(right + left) / width;
        m[1][0] = 0;	m[1][1] = 2 / height;	m[1][2] = 0;	m[1][3] = -(top + bottom) / height;
        m[2][0] = 0;	m[2][1] = 0;	m[2][2] = -2 / depth;	m[2][3] = -(far + near) / depth;
        m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;

        return this;
    }

    /**
     * Initialize a rotation matrix.
     *
     * @param forward   Forward vector
     * @param up        Up vector
     * @return          A camera matrix
     */
	public Matrix4f initRotation(Vector3f forward, Vector3f up)
	{
		Vector3f f = forward.normalized();
		
		Vector3f r = up.normalized();
		r = r.cross(f);
		
		Vector3f u = f.cross(r);
		
		m[0][0] = r.getX();	m[0][1] = r.getY();	m[0][2] = r.getZ();	m[0][3] = 0;
		m[1][0] = u.getX();	m[1][1] = u.getY();	m[1][2] = u.getZ();	m[1][3] = 0;
		m[2][0] = f.getX();	m[2][1] = f.getY();	m[2][2] = f.getZ();	m[2][3] = 0;
		m[3][0] = 0;		m[3][1] = 0;		m[3][2] = 0;		m[3][3] = 1;
		
		return this;
	}

    /**
     * Multiply two matrices
     *
     * @param r The other matrix
     * @return  The product
     */
	public Matrix4f mul(Matrix4f r)
	{
		Matrix4f res = new Matrix4f();
		
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				res.set(i, j, m[i][0] * r.get(0, j) +
							  m[i][1] * r.get(1, j) +
							  m[i][2] * r.get(2, j) +
							  m[i][3] * r.get(3, j));
			}
		}
		
		return res;
	}

    /**
     * Return a deep copy of this matrix.
     *
     * @return  A deep copy of this matrix
     */
	public float[][] getM()
	{
		float[][] res = new float[4][4];
		
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++)
				res[i][j] = m[i][j];
		
		return res;
	}

    public float get(int x, int y)
	{
		return m[x][y];
	}

	public void setM(float[][] m)
	{
		this.m = m;
	}
	
	public void set(int x, int y, float value)
	{
		m[x][y] = value;
	}
}
