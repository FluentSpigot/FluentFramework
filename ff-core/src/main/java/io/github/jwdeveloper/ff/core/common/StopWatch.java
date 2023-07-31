package io.github.jwdeveloper.ff.core.common;


public class StopWatch
{
    private long startTime;
    private long endTime;
    private boolean running;

    public void start()
    {
        if (!running) {
            reset();
            startTime = System.currentTimeMillis();
            running = true;

        }
    }

    public String stop() {
        if (running) {
            endTime = System.currentTimeMillis();
            running = false;
        }
        return toString();
    }

    public void reset()
    {
        endTime = 0;
        startTime =0;
        running = false;
    }

    public static StopWatch create()
    {
        return new StopWatch();
    }

    @Override
    public String toString() {
        return "Time: "+getMiliseconds()+"ms "+getTick()+"ticks "+getSeconds()+" seconds";
    }

    public long getMiliseconds() {
        if (running) {
            return System.currentTimeMillis() - startTime;
        } else {
            return endTime - startTime;
        }
    }

    public long getTick()
    {
        return millisecondsToTicks(getMiliseconds());
    }

    public double getSeconds()
    {
        return millisecondsToSeconds(getMiliseconds());
    }

    private  double millisecondsToSeconds(long milliseconds) {
        return (double) milliseconds / 1000;
    }


    private   long millisecondsToTicks(long milliseconds) {
        // 20 ticks per second in Minecraft
        return milliseconds / 50;
    }
}
