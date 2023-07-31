package io.github.jwdeveloper.ff.core.spigot.tasks.api;

import io.github.jwdeveloper.ff.core.async.cancelation.CancelationToken;
import io.github.jwdeveloper.ff.core.spigot.tasks.implementation.SimpleTaskTimer;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public interface FluentTaskFactory {
    SimpleTaskTimer taskTimer(int ticks, TaskAction task);
    BukkitTask task(Runnable action);
    void taskLater(Runnable action, int ticks);
    void taskAsync(Runnable action);
    CancelationToken taskAsync(Consumer<CancelationToken> action);
    void taskAsync(Consumer<CancelationToken> action, CancelationToken ctx);
    void taskAsync(Runnable action, CancelationToken ctx);


}
