package io.github.jw.spigot.ff.example.resource;

import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FolderWatcher;
import io.github.jwdeveloper.ff.extension.files.implementation.fluent_files.FolderFileWatcher;

import java.nio.file.Path;
import java.util.function.Consumer;

public class ResourcepackWatcher implements FolderWatcher {

    private String outputPath;
    private String inputPath;

    public ResourcepackWatcher(String inputPath, String outputPath) {
        this.outputPath = outputPath;
        this.inputPath = inputPath;
    }

    @Override
    public void onFileUpdated(FolderFileWatcher.FileModel fileModel) {

        var fileFullPath = fileModel.getPath();
        var output = translatePath(Path.of(fileFullPath), Path.of(inputPath), Path.of(outputPath));
        try {
            FileUtility.copyFile(Path.of(fileFullPath), output);
        } catch (Exception e) {

            FluentLogger.LOGGER.error("Unable to copy file!", e);
        }
    }

    private Path translatePath(Path filePath, Path inputPath, Path outputPath) {
        if (!filePath.startsWith(inputPath)) {
            throw new IllegalArgumentException("File path must be under the input path directory.");
        }

        Path relativePath = inputPath.relativize(filePath);
        return outputPath.resolve(relativePath);
    }
}
