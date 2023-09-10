package io.github.jwdeveloper.ff.extension.files.api;

import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FluentFile;

import java.util.List;
import java.util.Map;

public interface FluentFilesManager
{
    Map<Class<?>, FluentFile> getTrackedFiles();
    void addFileToTrack(List<FluentFile> file);
    void addFileToTrack(FluentFile file);

    void load();

    void save();

    FluentFile getFile(String path);
}
