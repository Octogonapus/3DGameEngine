package com.base.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * @author Octogonapus
 */

public class Window {
    /**
     * Creates a window.
     *
     * @param width     Width of window
     * @param height    Height of window
     * @param title     Title of window
     */
    public static void createWindow(int width, int height, String title) {
        Display.setTitle(title);
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
            Keyboard.create();
            Mouse.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates window.
     */
    public static void render() {
        Display.update();
    }

    /**
     * Frees window resources.
     */
    public static void dispose() {
        Display.destroy();
        Keyboard.destroy();
        Mouse.destroy();
    }

    /**
     * Checks if the window is requested to close.
     *
     * @return  If the window is requested to close
     */
    public static boolean isCloseRequested() {
        return Display.isCloseRequested();
    }

    /**
     * Gets the display width.
     *
     * @return  The display width
     */
    public static int getWidth() {
        return Display.getDisplayMode().getWidth();
    }

    /**
     * Gets the display height.
     *
     * @return  The display height
     */
    public static int getHeight() {
        return Display.getDisplayMode().getHeight();
    }

    /**
     * Gets the display title.
     *
     * @return  The display title
     */
    public static String getTitle() {
        return Display.getTitle();
    }
}
