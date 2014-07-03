package com.base.engine.core;

import com.base.engine.rendering.Vertex;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

/**
 * @author Octogonapus
 */

public class Util
{
    /**
     * Create a float buffer.
     *
     * @param size  The size of the float buffer
     * @return      The float buffer
     */
	public static FloatBuffer createFloatBuffer(int size)
	{
		return BufferUtils.createFloatBuffer(size);
	}

    /**
     * Create an int buffer.
     *
     * @param size  The size of the int buffer
     * @return      The int buffer
     */
	public static IntBuffer createIntBuffer(int size)
	{
		return BufferUtils.createIntBuffer(size);
	}

    /**
     * Create a byte buffer.
     *
     * @param size  The size of the byte buffer
     * @return      The byte buffer
     */
    public static ByteBuffer createByteBuffer(int size)
    {
        return BufferUtils.createByteBuffer(size);
    }

    /**
     * Create a flipped buffer.
     *
     * @param values    The values for the flipped buffer
     * @return          The flipped buffer
     */
	public static IntBuffer createFlippedBuffer(int... values)
	{
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		
		return buffer;
	}

    /**
     * Create a flipped buffer.
     *
     * @param vertices  The vertices for the flipped buffer
     * @return          The flipped buffer
     */
	public static FloatBuffer createFlippedBuffer(Vertex[] vertices)
	{
		FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE);

		for(int i = 0; i < vertices.length; i++)
		{
			buffer.put(vertices[i].getPos().getX());
			buffer.put(vertices[i].getPos().getY());
			buffer.put(vertices[i].getPos().getZ());
			buffer.put(vertices[i].getTexCoord().getX());
			buffer.put(vertices[i].getTexCoord().getY());
            buffer.put(vertices[i].getNormal().getX());
            buffer.put(vertices[i].getNormal().getY());
            buffer.put(vertices[i].getNormal().getZ());
		}
		
		buffer.flip();
		
		return buffer;
	}

    /**
     * Create a flipped buffer.
     *
     * @param value The matrix for the flipped buffer
     * @return      The flipped buffer
     */
	public static FloatBuffer createFlippedBuffer(Matrix4f value)
	{
		FloatBuffer buffer = createFloatBuffer(4 * 4);
		
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++)
				buffer.put(value.get(i, j));
		
		buffer.flip();
		
		return buffer;
	}

    /**
     * Remove empty strings from an array of strings.
     *
     * @param data  The array of strings
     * @return      The processed array of strings
     */
	public static String[] removeEmptyStrings(String[] data)
	{
		ArrayList<String> result = new ArrayList<String>();

        for (String aData : data)
            if (!aData.equals(""))
                result.add(aData);
		
		String[] res = new String[result.size()];
		result.toArray(res);
		
		return res;
	}

    /**
     * Make an Integer array into an int array.
     *
     * @param data  The Integer array
     * @return      The int array
     */
	public static int[] toIntArray(Integer[] data)
	{
		int[] result = new int[data.length];
		
		for(int i = 0; i < data.length; i++)
			result[i] = data[i];
		
		return result;
	}
}
