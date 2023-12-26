package io.github.jwdeveloper.ff.extension.files.implementation;
import io.github.jwdeveloper.ff.core.logger.plugin.PluginLogger;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.core.spigot.tasks.implementation.SimpleTaskTimer;
import io.github.jwdeveloper.ff.extension.files.api.FluentFilesManager;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FluentFile;
import io.github.jwdeveloper.ff.extension.files.implementation.config.FluentFilesConfig;

import java.util.*;

public class FluentFilesManagerImpl implements FluentFilesManager
{
    private final Map<Class<?>, FluentFile> files;
    private final FluentFilesConfig config;
    private final FluentTaskFactory taskFactory;
    private final PluginLogger logger;

    private SimpleTaskTimer taskTimer;

    public FluentFilesManagerImpl(FluentFilesConfig config, FluentTaskFactory taskFactory, PluginLogger logger)
    {
        this.files = new HashMap<>();
        this.config = config;
        this.taskFactory = taskFactory;
        this.logger = logger;
    }


    @Override
    public Map<Class<?>, FluentFile> getTrackedFiles() {
        return files;
    }

    @Override
    public void addFileToTrack(List<FluentFile> file) {
        for(var item : file)
        {
            addFileToTrack(item);
        }
    }

    @Override
    public void addFileToTrack(FluentFile file)
    {
        files.put(file.getTarget().getClass(), file);
    }

    @Override
    public void load()
    {
        for(var file : files.values())
        {
            file.load();
        }
    }

    @Override
    public void save()
    {
        for(var file : files.values())
        {
            if(!file.getModel().isAllowAutomaticSaving())
            {
                continue;
            }
            file.save();
        }
    }

    public FluentFile getFile(String path)
    {
       return files.values().stream().filter(e -> e.getPath().equals(path)).findFirst().get();
    }


    public void start()
    {
        load();
        if(!config.isAutoSave())
        {
            return;
        }

        if(taskTimer == null)
        {
            taskTimer = taskFactory.taskTimer(20*10,(iteration, task) ->
            {
                logger.info("Start saving files");
                save();
                logger.info("Files saved");
            });
        }

        stop();
        taskTimer.start();
    }

    public void stop()
    {
        if(taskTimer != null)
        {
            taskTimer.stop();
        }
    }
}
