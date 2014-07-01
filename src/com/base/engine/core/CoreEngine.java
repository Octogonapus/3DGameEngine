package com.base.engine.core;

import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Window;

/**
 * @author Octogonapus
 */

public class CoreEngine
{
	private boolean isRunning;
	private Game game;
    private RenderingEngine renderingEngine;
    private int width, height;
    private double frameTime;
	
	public CoreEngine(int width, int height, double frameCap, Game game)
	{
        isRunning = false;
        this.game = game;
        this.width = width;
        this.height = height;
        this.frameTime = 1.0 / frameCap;
	}

    /**
     * Create a window.
     *
     * @param title Title of the window
     */
    public void createWindow(String title)
    {
        Window.createWindow(width, height, title);
        renderingEngine = new RenderingEngine();
    }

    /**
     * Start the engine.
     */
	public void start()
	{
		if(isRunning)
			return;
		
		run();
	}

    /**
     * Stop the engine.
     */
	public void stop()
	{
		if(!isRunning)
			return;
		
		isRunning = false;
	}

    /**
     * Run per tick.
     */
	private void run()
	{
		isRunning = true;
		
		int frames = 0;
		long frameCounter = 0;

        game.init();
		
		double lastTime = Time.getTime();
		double unprocessedTime = 0;
		
		while(isRunning)
		{
			boolean render = false;
			
			double startTime = Time.getTime();
			double passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime;
			frameCounter += passedTime;
			
			while(unprocessedTime > frameTime)
			{
				render = true;
				
				unprocessedTime -= frameTime;
				
				if(Window.isCloseRequested())
                {
                    stop();
                }
				
				game.input((float) frameTime);
				Input.update();
				
				game.update((float) frameTime);

				if(frameCounter >= 1.0)
				{
					System.out.println(frames);
					frames = 0;
					frameCounter = 0;
				}
			}
			if(render)
			{
                game.render(renderingEngine);
                Window.render();
				frames++;
			}
			else
			{
				try 
				{
					Thread.sleep(1);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		cleanUp();
	}

    /**
     * Dispose of allocated resources.
     */
	private void cleanUp()
	{
		Window.dispose();
	}
}
