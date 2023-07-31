package io.github.jwdeveloper.ff.core.async;

import io.github.jwdeveloper.ff.core.async.cancelation.CancelationToken;

import java.io.Closeable;

public interface BackgroundWorker extends Closeable {
    void runAsync();

    void runAsync(CancelationToken cancelationToken);

    void pause();

    boolean isPaused();

    boolean isRunning();
}
