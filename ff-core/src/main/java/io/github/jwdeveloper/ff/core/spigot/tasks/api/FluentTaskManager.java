package io.github.jwdeveloper.ff.core.spigot.tasks.api;

import io.github.jwdeveloper.ff.core.spigot.tasks.implementation.SimpleTaskTimer;
import org.bukkit.scheduler.BukkitTask;

public interface FluentTaskManager {
    SimpleTaskTimer taskTimer(int ticks, TaskAction task);

    BukkitTask task(Runnable action);

    void taskLater(Runnable action, int ticks);

    BukkitTask taskAsync(Runnable action);
}
