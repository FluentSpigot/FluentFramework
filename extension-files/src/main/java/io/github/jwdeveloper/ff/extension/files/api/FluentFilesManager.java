package io.github.jwdeveloper.ff.extension.files.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FluentFilesManager
{
    Map<Class<?>, FluentFile> getTrackedFiles();
    void addFileToTrack(List<FluentFile> file);
    void addFileToTrack(FluentFile file);

    void load();

    void save();

    FluentFile getFile(String path);
}
