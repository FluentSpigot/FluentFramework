package io.github.jwdeveloper.ff.core.async;

import io.github.jwdeveloper.ff.core.async.cancelation.CancelationToken;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;

import java.util.concurrent.FutureTask;
import java.util.function.Supplier;

public abstract class BackgroundWorkerBase implements BackgroundWorker {
    protected final FluentTaskFactory tasksFactory;
    protected CancelationToken cancelationToken;
    private boolean paused;
    private boolean running;

    public BackgroundWorkerBase(FluentTaskFactory factory) {
        this.tasksFactory = factory;
        this.cancelationToken = new CancelationToken();
    }

    public void onRun() {

    }

    public abstract void onWork(CancelationToken cancelationToken);

    public void onClose() {

    }

    @Override
    public final synchronized void runAsync() {
        runAsync(this.cancelationToken);
    }

    @Override
    public final synchronized void runAsync(CancelationToken cancelationToken) {
        if (isRunning()) {
            close();
        }

        this.cancelationToken = cancelationToken;
        onRun();
        tasksFactory.taskAsync(this::onWork, cancelationToken);
        running = true;
        paused = false;
    }

    public final synchronized void close() {
        cancelationToken.cancel();
        onClose();
    }

    @Override
    public final void pause() {
        paused = !paused;
    }

    public final boolean isPaused() {
        return paused;
    }

    public final synchronized boolean isRunning() {
        return running && cancelationToken.isNotCancel();
    }


    protected <T> void waitForBukkitThread(Supplier<T> body, CancelationToken ctx) {
        var future = new FutureTask<>(body::get);
        ctx.throwIfCancel();
        tasksFactory.task(future);
        while (!future.isDone()) {
            ctx.throwIfCancel();
        }
    }

}
