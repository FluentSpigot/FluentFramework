package io.github.jwdeveloper.ff.extension.files.api;

public interface FileWatcher
{
    void onContentChanged(String content);

    String getFileName();
}
