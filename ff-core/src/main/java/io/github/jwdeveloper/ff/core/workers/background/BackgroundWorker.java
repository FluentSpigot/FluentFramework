package io.github.jwdeveloper.ff.core.workers.background;

import io.github.jwdeveloper.ff.core.spigot.tasks.api.cancelation.CancelationToken;

import java.io.Closeable;

public interface BackgroundWorker extends Closeable {
    void runAsync();

    void runAsync(CancelationToken cancelationToken);

    void pause();

    boolean isPaused();

    boolean isRunning();
}
