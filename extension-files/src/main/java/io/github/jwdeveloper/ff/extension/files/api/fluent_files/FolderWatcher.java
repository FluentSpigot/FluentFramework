package io.github.jwdeveloper.ff.extension.files.api.fluent_files;

import io.github.jwdeveloper.ff.extension.files.implementation.fluent_files.FolderFileWatcher;

public interface FolderWatcher
{
     void onFileUpdated(FolderFileWatcher.FileModel fileModel);
}
