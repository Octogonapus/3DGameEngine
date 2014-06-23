package com.base.engine;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

/**
 * @author Octogonapus
 */

public class Util {
    public static FloatBuffer createFloatBuffer(int size) {
        return BufferUtils.createFloatBuffer(size);
    }

    public static IntBuffer createIntBuffer(int size) {
        return BufferUtils.createIntBuffer(size);
    }

    public static FloatBuffer createFlippedBuffer(Vertex[] vertices) {
        FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE);

        for (Vertex vert : vertices) {
            buffer.put(vert.getPos().getX());
            buffer.put(vert.getPos().getY());
            buffer.put(vert.getPos().getZ());
        }

        buffer.flip();
        return buffer;
    }

    public static FloatBuffer createFlippedBuffer(Matrix4f value) {
        FloatBuffer buffer = createFloatBuffer(16);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buffer.put(value.get(i, j));
            }
        }

        buffer.flip();
        return buffer;
    }

    public static IntBuffer createFlippedBuffer(int... values) {
        IntBuffer buffer = createIntBuffer(values.length);
        buffer.put(values);
        buffer.flip();

        return buffer;
    }

    public static String[] removeEmptyStrings(String[] data) {
        ArrayList<String> result = new ArrayList<String>(0);

        for (String token : data) {
            if (!token.equals(" ")) {
                result.add(token);
            }
        }

        return result.toArray(new String[result.size()]);
    }

    public static int[] toIntArray(Integer[] data) {
        int[] result = new int[data.length];

        for (int i = 0; i < data.length; i++) {
            result[i] = data[i];
        }

        return result;
    }
}
