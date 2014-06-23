package com.base.engine;

/**
 * @author Octogonapus
 */

public class MainComponent {
    public static final int WIDTH = 800, HEIGHT = 600;
    public static final String TITLE = "3D Engine";
    public static final double FRAME_CAP = 5000.0;

    private boolean isRunning;
    private Game game;

    public MainComponent() {
        System.out.println(RenderUtil.getOpenGLVersion());
        RenderUtil.initGraphics();
        isRunning = false;
        game = new Game();
    }

    /**
     * Starts the window.
     */
    public void start() {
        if (isRunning) {
            return;
        }

        run();
    }

    /**
     * Stops the window.
     */
    public void stop() {
        if (!isRunning) {
            return;
        }

        isRunning = false;
    }

    /**
     * Controls updating.
     */
    private void run() {
        isRunning = true;

        int frames = 0;
        long frameCounter = 0;

        final double frameTime = 1.0 / FRAME_CAP;

        long lastTime = Time.getTime();
        double unprocessedTime = 0;

        while (isRunning) {
            boolean render = false;

            long startTime = Time.getTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime / (double) Time.SECOND;
            frameCounter += passedTime;

            while (unprocessedTime > frameTime) {
                render = true;

                unprocessedTime -= frameTime;

                //TODO: Update game

                if (frameCounter >= Time.SECOND) {
                    System.out.println(frames);
                    frames = 0;
                    frameCounter = 0;
                }
            }

            if (Window.isCloseRequested()) {
                stop();
            }

            Time.setDelta(frameTime);
            Input.update();

            game.input();
            game.update();

            if (render) {
                render();
                frames++;
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        cleanUp();
    }

    /**
     * Updates the window.
     */
    private void render() {
        RenderUtil.clearScreen();
        game.render();
        Window.render();
    }

    /**
     * Disposes of any allocated resources.
     */
    private void cleanUp() {
        Window.dispose();
    }

    public static void main(String[] args) {
        Window.createWindow(WIDTH, HEIGHT, TITLE);

        MainComponent game = new MainComponent();
        game.start();
    }
}
