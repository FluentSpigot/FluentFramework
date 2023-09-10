package io.github.jwdeveloper.ff.extension.files.api.fluent_files;


import io.github.jwdeveloper.ff.extension.files.api.FluentFileModel;

public interface FluentFile<TARGET>
{
    FluentFileModel getModel();
    TARGET getTarget();
    void refresh();
    void load();
    void save();
    String getPath();
}
