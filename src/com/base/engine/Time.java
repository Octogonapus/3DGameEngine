package com.base.engine;

/**
 * @author Octogonapus
 */

public class Time {
    public static final long SECOND = 1000000000L;
    private static double delta;

    /**
     * Gets the system time.
     *
     * @return  The system time in nanoseconds
     */
    public static long getTime() {
        return System.nanoTime();
    }

    /**
     * Gets the amount of time that passes between frames.
     *
     * @return  The amount of time between frames
     */
    public static double getDelta() {
        return delta;
    }

    /**
     * Sets delta.
     *
     * @param delta The new value of delta
     */
    public static void setDelta(double delta) {
        Time.delta = delta;
    }
}
