package io.github.jwdeveloper.ff.core.workers.background;

import io.github.jwdeveloper.ff.core.spigot.tasks.api.cancelation.CancellationToken;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;

import java.util.concurrent.FutureTask;
import java.util.function.Supplier;

public abstract class BackgroundWorkerBase implements BackgroundWorker {
    protected final FluentTaskFactory tasksFactory;
    protected CancellationToken cancelationToken;
    private boolean paused;
    private boolean running;

    public BackgroundWorkerBase(FluentTaskFactory factory) {
        this.tasksFactory = factory;
        this.cancelationToken = new CancellationToken();
    }

    public void onRun() {

    }

    public abstract void onWork(CancellationToken cancelationToken);

    public void onClose() {

    }

    @Override
    public final synchronized void runAsync() {
        runAsync(this.cancelationToken);
    }

    @Override
    public final synchronized void runAsync(CancellationToken cancelationToken) {
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


    protected <T> void waitForBukkitThread(Supplier<T> body, CancellationToken ctx) {
        var future = new FutureTask<>(body::get);
        ctx.throwIfCancel();
        tasksFactory.task(future);
        while (!future.isDone()) {
            ctx.throwIfCancel();
        }
    }

}
