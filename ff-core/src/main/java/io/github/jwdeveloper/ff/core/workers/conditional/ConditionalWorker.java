package io.github.jwdeveloper.ff.core.workers.conditional;


import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;

import java.util.Timer;

public class ConditionalWorker {
    private long startTime;
    private long timeout;
    private Timer timer;
    private final ConditionalAction action;
    private final FluentTaskFactory factory;

    public ConditionalWorker(ConditionalAction action, FluentTaskFactory factory) {
        this.action = action;
        this.factory = factory;
    }

    public void start(long timeout)
    {
        this.timeout = timeout;
        this.startTime = System.currentTimeMillis();
        this.timer = new Timer();
        factory.taskTimer(5, (iteration, task) ->
        {
            long currentTime = System.currentTimeMillis();
            if (currentTime - startTime >= this.timeout) {
                action.onCancel();
                action.onEnd();
                timer.cancel();
                task.stop();
                return;
            }
            if (action.onCondition()) {
                action.onDone();
                action.onEnd();
                task.stop();
                return;
            }
        }).startAfterTicks(1).start();
        action.onStart();
    }


}
/**
 * 46bc8a00-3498-48ab-b7a0-c01eb772fd4b
 */
