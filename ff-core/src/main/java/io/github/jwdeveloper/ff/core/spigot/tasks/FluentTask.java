package io.github.jwdeveloper.ff.core.spigot.tasks;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.TaskAction;
import io.github.jwdeveloper.ff.core.spigot.tasks.implementation.SimpleTaskFactory;
import io.github.jwdeveloper.ff.core.spigot.tasks.implementation.SimpleTaskTimer;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class FluentTask {
    private static SimpleTaskFactory manger;

    public SimpleTaskTimer taskTimer(int ticks, TaskAction task) {
        return getManager().taskTimer(ticks, task);
    }

    public BukkitTask task(Runnable action) {
        return getManager().task(action);
    }

    public void taskLater(int ticks, Runnable action) {
        getManager().taskLater(action, ticks);
    }

    public void taskAsync(Runnable runnable) {
        getManager().taskAsync(runnable);
    }

    public static FluentTaskFactory getManager() {
        if (manger == null) {
            throw new RuntimeException(FluentTaskFactory.class.getSimpleName() + " are disabled, use to enable it " + FluentTask.class.getSimpleName() + ".enable(plugin)");
        }
        return manger;
    }

    public static FluentTaskFactory enable(Plugin plugin) {
        manger = new SimpleTaskFactory(plugin, FluentLogger.LOGGER);
        return manger;
    }
}
