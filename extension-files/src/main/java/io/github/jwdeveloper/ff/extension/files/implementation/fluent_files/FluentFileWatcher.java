package io.github.jwdeveloper.ff.extension.files.implementation.fluent_files;

import io.github.jwdeveloper.ff.core.async.cancelation.CancelationToken;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.extension.files.api.FluentFileModel;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FileWatcher;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FluentFile;
import io.github.jwdeveloper.ff.extension.files.implementation.config.FluentFilesConfig;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

public class FluentFileWatcher implements FluentFile<FileWatcher> {
    private final FileWatcher fileWatcher;
    private final FluentFilesConfig config;

    @Getter
    private final FluentFileModel model;

    public FluentFileWatcher(FluentFileModel model, FileWatcher fileWatcher, FluentFilesConfig config, FluentTaskFactory taskFactory) {
        this.model = model;
        this.fileWatcher = fileWatcher;
        this.config = config;
        taskFactory.taskAsync(this::run);
    }

    @Override
    public FileWatcher getTarget() {
        return fileWatcher;
    }

    @Override
    public void refresh()
    {
        load();
    }

    @Override
    public void load() {
        try {
            var content = FileUtility.loadFileContent(getPath());
            if (StringUtils.isNullOrEmpty(content)) {
                content = StringUtils.EMPTY;
            }
            fileWatcher.setContent(content);
        } catch (Exception e) {
            throw new RuntimeException("Unable to load text file", e);
        }
    }

    @Override
    public void save()
    {
        var content = fileWatcher.getContent();
        FileUtility.save(content, getPath());
    }

    @Override
    public String getPath()
    {
        if (model.hasCustomPath()) {
            return FileUtility.combinePath(model.getCustomPath(), fileWatcher.getFileName());
        }
        return FileUtility.combinePath(config.getSavingPath(), fileWatcher.getFileName());
    }
    FileTime lastModifiedTime = null;
    public void run(CancelationToken ctx)
    {
        var filePath = getPath();
        var path = Paths.get(filePath);

        if(!FileUtility.pathExists(path.toString()))
        {
            FileUtility.ensureFile(path.toString());
        }
        try {
            lastModifiedTime = Files.getLastModifiedTime(path);
        }
        catch (NoSuchFileException e)
        {

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        ctx.throwIfCancel();
        while (ctx.isNotCancel())
        {
            try {
                FileTime currentModifiedTime = Files.getLastModifiedTime(path);

                if (!currentModifiedTime.equals(lastModifiedTime))
                {
                    lastModifiedTime = currentModifiedTime;
                    load();
                    fileWatcher.onContentChanged(fileWatcher.getContent());
                }

                Thread.sleep(100);
                ctx.throwIfCancel();
            }
            catch (NoSuchFileException noSuchFileException)
            {
                FluentLogger.LOGGER.error("File "+filePath+" not exists!",noSuchFileException);
                break;
            }
            catch (InterruptedException e)
            {
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
