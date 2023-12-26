package io.github.jwdeveloper.ff.core.common;


import lombok.Getter;

public class StopWatch
{
    private long startTime;
    private long endTime;

    @Getter
    private boolean started;

    public void start()
    {
        if (!started) {
            reset();
            startTime = System.currentTimeMillis();
            started = true;

        }
    }

    public String stop() {
        if (started) {
            endTime = System.currentTimeMillis();
            started = false;
        }
        return toString();
    }

    public void reset()
    {
        endTime = 0;
        startTime =0;
        started = false;
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
        if (started) {
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
