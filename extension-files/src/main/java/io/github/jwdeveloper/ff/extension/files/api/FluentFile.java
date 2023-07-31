package io.github.jwdeveloper.ff.extension.files.api;


public interface FluentFile<TARGET>
{
    TARGET getTarget();
    void refresh();
    void load();
    void save();
    String getPath();
}
