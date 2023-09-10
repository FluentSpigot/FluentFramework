package io.github.jwdeveloper.ff.extension.files.api.fluent_files;

public interface FileWatcher extends TextFile
{
    void onContentChanged(String content);
}
