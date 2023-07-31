package io.github.jwdeveloper.ff.extension.files.implementation.fluent_files;

import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.extension.files.api.FileWatcher;
import io.github.jwdeveloper.ff.extension.files.api.FluentFile;
import io.github.jwdeveloper.ff.extension.files.implementation.config.FluentFilesConfig;
import java.io.File;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class FluentFileWatcher implements FluentFile<FileWatcher>
{
    private final FileWatcher fileWatcher;
    private final FluentFilesConfig config;
    private final FluentTaskFactory taskFactory;

    public FluentFileWatcher(FileWatcher fileWatcher, FluentFilesConfig config, FluentTaskFactory taskFactory)
    {
        this.fileWatcher = fileWatcher;
        this.config = config;
        this.taskFactory = taskFactory;
        taskFactory.taskAsync(() ->
        {
            run();
        });
    }

    @Override
    public FileWatcher getTarget()
    {
        return fileWatcher;
    }

    @Override
    public void refresh()
    {

    }

    @Override
    public void load()
    {
        var path = getPath();
    }

    @Override
    public void save() {

    }

    @Override
    public String getPath() {
        return FileUtility.combinePath(config.getSavingPath(), fileWatcher.getFileName()+".json");
    }


    private  File file;
    private AtomicBoolean stop = new AtomicBoolean(false);
    private LinkedList<Consumer<Path>> onFileChangedEvents;
    public boolean isStopped() { return stop.get(); }

    public void run()
    {
        try (WatchService watcher = FileSystems.getDefault().newWatchService()) {
            Path path = file.toPath().getParent();
            path.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
            while (!isStopped()) {
                WatchKey key;
                try { key = watcher.poll(25, TimeUnit.MILLISECONDS); }
                catch (InterruptedException e) { return; }
                if (key == null) { Thread.yield(); continue; }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path filename = ev.context();

                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        Thread.yield();
                        continue;
                    } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY
                            && filename.toString().equals(file.getName())) {
                        for(var e : onFileChangedEvents)
                        {
                            e.accept(filename);
                        }
                    }
                    boolean valid = key.reset();
                    if (!valid) { break; }
                }
                Thread.yield();
            }
        } catch (Throwable e) {
            FluentLogger.LOGGER.info("File watcher error",e);
        }
    }
}
