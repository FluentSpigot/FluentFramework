package io.github.jwdeveloper.ff.core.spigot.tasks.implementation;

import io.github.jwdeveloper.ff.core.spigot.tasks.api.cancelation.CancellationToken;
import io.github.jwdeveloper.ff.core.logger.plugin.PluginLogger;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.TaskAction;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public class SimpleTaskTimer {
    private final TaskAction task;
    private final Plugin plugin;
    private final PluginLogger logger;
    private Consumer<SimpleTaskTimer> onStop;
    private Consumer<SimpleTaskTimer> onStart;

    @Setter
    private int speed = 20;
    private int time = 0;
    private int runAfter = 0;
    private int stopAfter = Integer.MAX_VALUE;
    private boolean isCancel = false;
    private BukkitTask bukkitTask;

    @Setter
    private CancellationToken cancelationToken;

    public SimpleTaskTimer(int speed,
                           TaskAction action,
                           Plugin plugin,
                           PluginLogger logger,
                           CancellationToken cancelationToken) {
        this.speed = speed;
        this.task = action;
        this.plugin = plugin;
        this.logger = logger;
        this.cancelationToken = cancelationToken;
    }

    public void setIteration(int iteration) {
        this.time = iteration;
    }

    public SimpleTaskTimer startAsync() {
        if (onStart != null)
            onStart.accept(this);
        bukkitTask = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::taskBody, runAfter, speed);
        return this;
    }

    public void reset() {
        this.time = 0;
    }

    public SimpleTaskTimer start() {
        if (cancelationToken.isCancel()) {
            return this;
        }
        isCancel = false;
        if (onStart != null)
            onStart.accept(this);
        bukkitTask = Bukkit.getScheduler().runTaskTimer(plugin, this::taskBody, runAfter, speed);
        return this;
    }

    private void taskBody() {
        try {
            if (time >= stopAfter || isCancel || bukkitTask.isCancelled() || cancelationToken.isCancel()) {
                if (onStop != null)
                    onStop.accept(this);
                stop();
                return;
            }
            task.execute(time, this);
            time++;
        } catch (Exception e) {
            logger.error("FluentTask error", e);
            stop();
        }
    }

    public void stop() {
        if (bukkitTask == null)
            return;

        Bukkit.getScheduler().cancelTask(bukkitTask.getTaskId());
        bukkitTask.cancel();
        isCancel = true;
        bukkitTask = null;
    }

    public boolean isRunning() {
        return bukkitTask != null;
    }

    public void cancel() {
        isCancel = true;
    }

    public SimpleTaskTimer startAfterTicks(int iterations) {
        this.runAfter = iterations;
        return this;
    }

    public SimpleTaskTimer stopAfterIterations(int iterations) {
        this.stopAfter = iterations;
        return this;
    }

    public SimpleTaskTimer onStop(Consumer<SimpleTaskTimer> event) {
        this.onStop = event;
        return this;
    }

    public SimpleTaskTimer onStart(Consumer<SimpleTaskTimer> event) {
        this.onStart = event;
        return this;
    }

    public void runAgain() {
        this.time = 0;
        this.isCancel = false;
    }
}
