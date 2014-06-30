package com.base.engine.rendering;

import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

/**
 * @author Octogonapus
 */

public class Texture
{
	private int id;

    public Texture(String fileName)
    {
        this(loadTexture(fileName));
    }

	public Texture(int id)
	{
		this.id = id;
	}

    /**
     * Bind this texture for use.
     */
	public void bind()
	{
		glBindTexture(GL_TEXTURE_2D, id);
	}

    /**
     * Load a texture by file name.
     *
     * @param fileName  The name of the texture
     * @return          The id of the loaded texture
     */
    private static int loadTexture(String fileName)
    {
        String[] splitArray = fileName.split("\\.");
        String ext = splitArray[splitArray.length - 1];

        try
        {
            int id = TextureLoader.getTexture(ext, new FileInputStream(new File("./res/textures/" + fileName))).getTextureID();

            return id;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }

        return 0;
    }
	
	public int getID()
	{
		return id;
	}
}