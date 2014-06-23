package com.base.engine;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;

/**
 * @author Octogonapus
 */

public class Input {
    public static final int NUM_KEYCODES = 256, NUM_MOUSEBUTTONS = 5;

    private static ArrayList<Integer> currentKeys = new ArrayList<Integer>(0);
    private static ArrayList<Integer> downKeys = new ArrayList<Integer>(0);
    private static ArrayList<Integer> upKeys = new ArrayList<Integer>(0);

    private static ArrayList<Integer> currentMouse = new ArrayList<Integer>(0);
    private static ArrayList<Integer> downMouse = new ArrayList<Integer>(0);
    private static ArrayList<Integer> upMouse = new ArrayList<Integer>(0);

    /**
     * Recognizes pressed keys for only one frame until key release.
     */
    public static void update() {
        upKeys.clear();

        for (int i = 0; i < NUM_KEYCODES; i++) {
            if (!getKey(i) && currentKeys.contains(i)) {
                upKeys.add(i);
            }
        }

        downKeys.clear();

        for (int i = 0; i < NUM_KEYCODES; i++) {
            if (getKey(i) && !currentKeys.contains(i)) {
                downKeys.add(i);
            }
        }

        currentKeys.clear();

        for (int i = 0; i < NUM_KEYCODES; i++) {
            if (getKey(i)) {
                currentKeys.add(i);
            }
        }

        upMouse.clear();

        for (int i = 0; i < NUM_MOUSEBUTTONS; i++) {
            if (!getMouse(i) && currentMouse.contains(i)) {
                upMouse.add(i);
            }
        }

        downMouse.clear();

        for (int i = 0; i < NUM_MOUSEBUTTONS; i++) {
            if (getMouse(i) && !currentMouse.contains(i)) {
                downMouse.add(i);
            }
        }

        currentMouse.clear();

        for (int i = 0; i < NUM_MOUSEBUTTONS; i++) {
            if (getMouse(i)) {
                currentMouse.add(i);
            }
        }
    }

    /**
     * Gets if a key is pressed.
     *
     * @param keyCode   Integer code of key
     * @return          If the key is pressed
     */
    public static boolean getKey(int keyCode) {
        return Keyboard.isKeyDown(keyCode);
    }

    /**
     * Gets if a key was just pressed.
     *
     * @param keyCode   Integer code of key
     * @return          If the key was just pressed
     */
    public static boolean getKeyDown(int keyCode) {
        return downKeys.contains(keyCode);
    }

    /**
     * Gets if a key was just released.
     *
     * @param keyCode   Integer code of key
     * @return          If the key was just released
     */
    public static boolean getKeyUp(int keyCode) {
        return upKeys.contains(keyCode);
    }

    /**
     * Gets is a mouse button is pressed.
     *
     * @param mouseButton   Integer code of mouse button
     * @return              If the mouse button is pressed
     */
    public static boolean getMouse(int mouseButton) {
        return Mouse.isButtonDown(mouseButton);
    }

    /**
     * Gets is a mouse button was just pressed.
     *
     * @param mouseButton   Integer code of mouse button
     * @return              If the mouse button was just pressed
     */
    public static boolean getMouseDown(int mouseButton) {
        return downMouse.contains(mouseButton);
    }

    /**
     * Gets is a mouse button was just released.
     *
     * @param mouseButton   Integer code of mouse button
     * @return              If the mouse button was just released
     */
    public static boolean getMouseUp(int mouseButton) {
        return upMouse.contains(mouseButton);
    }

    /**
     * Gets the current mouse position.
     *
     * @return  The current mouse position
     */
    public static Vector2f getMousePosition() {
        return new Vector2f(Mouse.getX(), Mouse.getY());
    }
}
