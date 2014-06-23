package com.base.engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * @author Octogonapus
 */

public class RenderUtil {
    /**
     * Completely clears the screen.
     */
    public static void clearScreen() {
        //TODO: Stencil buffer
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    /**
     * Initialized openGL.
     */
    public static void initGraphics() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);

        //TODO: Depth clamp for later

        glEnable(GL_FRAMEBUFFER_SRGB);
    }

    /**
     * Get the current openGL version.
     *
     * @return  The current openGL version.
     */
    public static String getOpenGLVersion() {
        return glGetString(GL_VERSION);
    }
}
