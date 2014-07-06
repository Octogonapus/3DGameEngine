package com.base.game;

import com.base.engine.core.CoreEngine;

/**
 * @author Octogonapus
 */

public class Main
{
    public static void main(String[] args)
    {
        CoreEngine engine = new CoreEngine(1152, 864, 60, new TestGame());
        engine.createWindow("3D Game Engine");
        engine.start();
    }
}
