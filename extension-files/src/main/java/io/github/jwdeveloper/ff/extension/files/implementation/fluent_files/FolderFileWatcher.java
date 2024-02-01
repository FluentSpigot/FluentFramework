package io.github.jwdeveloper.ff.extension.files.implementation.fluent_files;

import io.github.jwdeveloper.ff.core.spigot.tasks.api.cancelation.CancelationToken;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.extension.files.api.FluentFileModel;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FluentFile;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FolderWatcher;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class FolderFileWatcher implements FluentFile<FolderWatcher> {
    private final FluentFileModel model;
    private final FolderWatcher folderWatcher;

    public FolderFileWatcher(FluentFileModel model, FolderWatcher folderWatcher, FluentTaskFactory taskFactory) {
        this.model = model;
        this.folderWatcher = folderWatcher;
        taskFactory.taskAsync((ctx) ->
        {
            try {
                run(ctx);
            } catch (Exception e) {

              FluentLogger.LOGGER.error("FolderWatcherError",e);
            }
        });
    }

    @Override
    public FluentFileModel getModel() {
        return model;
    }

    @Override
    public FolderWatcher getTarget() {
        return folderWatcher;
    }

    @Override
    public String getPath() {
        return model.getCustomPath();
    }

    @Override
    public void refresh() {

    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }


    public void run(CancelationToken ctx) throws InterruptedException {

        var filePath = getPath();
        var path = Paths.get(filePath);
        if (!FileUtility.pathExists(path.toString())) {
            FileUtility.ensurePath(path.toString());
        }
        ctx.throwIfCancel();
        while (ctx.isNotCancel()) {
            var files = FileUtility.getAllFiles(filePath);
            var stack = new Stack<FileModel>();
            for (var file : files)
            {
                var checkedResult = checkFile(file);
                if (checkedResult.getState() == FileState.NONE) {
                    continue;
                }
                stack.push(checkedResult);
            }

            for (var result : stack) {
                folderWatcher.onFileUpdated(result);
            }

            Thread.sleep(100);
        }
    }

    Map<String, FileModel> modelMap = new HashMap<>();

    public FileModel checkFile(String path) {
        var model = modelMap.computeIfAbsent(path, (e) -> new FileModel(path, FileState.NONE, null));
        try {
            var _path = Path.of(path);
            var currentModified = Files.getLastModifiedTime(_path);
            var lastModifiedTime = model.getModificationTime();
            model.setModificationTime(currentModified);
            if (lastModifiedTime == null) {
                model.setState(FileState.CREATED);
                return model;
            }
            if (lastModifiedTime.equals(currentModified)) {
                model.setState(FileState.NONE);
                return model;
            }
            model.setState(FileState.UPDATED);
            return model;

        } catch (NoSuchFileException noSuchFileException) {
            model.setState(FileState.DELETED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return model;
    }


    @Data
    @AllArgsConstructor
    public class FileModel {
        String path;
        FileState state;
        FileTime modificationTime;
    }

    public enum FileState {
        NONE, CREATED, UPDATED, DELETED
    }
}
