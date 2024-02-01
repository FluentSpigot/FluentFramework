package io.github.jwdeveloper.ff.core.spigot.tasks.api;

import io.github.jwdeveloper.ff.core.spigot.tasks.api.cancelation.CancelationToken;
import io.github.jwdeveloper.ff.core.spigot.tasks.implementation.SimpleTaskTimer;
import io.github.jwdeveloper.ff.core.workers.conditional.ConditionalActionBuilder;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public interface FluentTaskFactory {
    SimpleTaskTimer taskTimer(int ticks, TaskAction task);
    SimpleTaskTimer taskTimer(int ticks, TaskAction task, CancelationToken cancelationToken);

    BukkitTask task(Runnable action);

    void taskLater(Runnable action, int ticks);

    void taskAsync(Runnable action);

    CancelationToken taskAsync(Consumer<CancelationToken> action);

    void taskAsync(Consumer<CancelationToken> action, CancelationToken ctx);

    void taskAsync(Runnable action, CancelationToken ctx);
}
