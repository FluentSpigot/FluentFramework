package io.github.jwdeveloper.ff.core.spigot.tasks.api;

import io.github.jwdeveloper.ff.core.spigot.tasks.api.cancelation.CancellationToken;
import io.github.jwdeveloper.ff.core.spigot.tasks.implementation.SimpleTaskTimer;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public interface FluentTaskFactory {
    SimpleTaskTimer taskTimer(int ticks, TaskAction task);
    SimpleTaskTimer taskTimer(int ticks, TaskAction task, CancellationToken cancelationToken);

    BukkitTask task(Runnable action);

    void taskLater(Runnable action, int ticks);

    void taskAsync(Runnable action);

    CancellationToken taskAsync(Consumer<CancellationToken> action);

    void taskAsync(Consumer<CancellationToken> action, CancellationToken ctx);

    void taskAsync(Runnable action, CancellationToken ctx);
}
