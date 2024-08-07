package io.github.jwdeveloper.ff.core.spigot.tasks.implementation;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.cancelation.CancellationToken;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.cancelation.CancelationTokenSource;
import io.github.jwdeveloper.ff.core.logger.plugin.PluginLogger;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.TaskAction;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SimpleTaskFactory implements FluentTaskFactory  {
    private final Plugin plugin;
    private final PluginLogger logger;
    private final CancelationTokenSource cancelationTokenSource;
    private final List<Thread> asyncThreads;

    public SimpleTaskFactory(Plugin plugin, PluginLogger logger) {
        this.plugin = plugin;
        this.logger = logger;
        this.cancelationTokenSource = new CancelationTokenSource();
        this.asyncThreads = new ArrayList<>();
    }

    public SimpleTaskTimer taskTimer(int ticks, TaskAction task) {
        return new SimpleTaskTimer(ticks, task, plugin, logger, cancelationTokenSource.createToken());
    }

    @Override
    public SimpleTaskTimer taskTimer(int ticks, TaskAction task, CancellationToken cancelationToken)
    {
        cancelationTokenSource.attacheToken(cancelationToken);
        return new SimpleTaskTimer(ticks, task, plugin, logger, cancelationToken);
    }

    public BukkitTask task(Runnable action) {
        return Bukkit.getScheduler().runTask(plugin, action);
    }

    public void taskLater(Runnable action, int ticks) {
        Bukkit.getScheduler().runTaskLater(plugin, action, ticks);
    }

    public void taskAsync(Runnable action) {
        taskAsync(action, cancelationTokenSource.createToken());
    }

    @Override
    public CancellationToken taskAsync(Consumer<CancellationToken> action) {
        var ctx = cancelationTokenSource.createToken();
        taskAsync(action, ctx);
        return ctx;
    }

    @Override
    public void taskAsync(Consumer<CancellationToken> action, CancellationToken ctx) {
        cancelationTokenSource.attacheToken(ctx);
        if(ctx.isCancel())
        {
            return;
        }
        taskAsync(() ->
        {
            action.accept(ctx);
        }, ctx);
    }


    @Override
    public void taskAsync(Runnable action, CancellationToken ctx) {
        var thread = new Thread(() ->
        {
            if (ctx.isCancel()) {
                return;
            }
            try {
                action.run();
            } catch (Exception ex) {
                logger.error("Error while running async task", ex);
            }
        });
        asyncThreads.add(thread);
        thread.start();
    }

    @Override
    public CancellationToken createCancelationToken() {
        return cancelationTokenSource.createToken();
    }


    @Override
    public void close()
    {
        cancelationTokenSource.cancel();
        for (var task : asyncThreads) {
            try {
                task.interrupt();
            } catch (Exception e) {

            }
        }
    }
}
