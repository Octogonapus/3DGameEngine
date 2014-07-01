package com.base.engine.rendering.meshLoading;

/**
 * @author Octogonapus
 */

public class OBJIndex
{
    public int vertexIndex, texCoordIndex, normalIndex;

    @Override
    public boolean equals(Object obj)
    {
        OBJIndex index = (OBJIndex) obj;
        return vertexIndex == index.vertexIndex && texCoordIndex == index.texCoordIndex && normalIndex == index.normalIndex;
    }

    @Override
    public int hashCode()
    {
        final int BASE = 17, MULTIPLIER = 31;
        int result = BASE;
        result = MULTIPLIER * result + vertexIndex;
        result = MULTIPLIER * result + texCoordIndex;
        result = MULTIPLIER * result + normalIndex;
        return result;
    }
}
