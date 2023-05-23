package io.github.jwdeveloper.ff.extension.files.implementation;

import io.github.jwdeveloper.ff.extension.files.api.FluentFiles;
import io.github.jwdeveloper.ff.extension.files.file_handlers.FilesDataContext;

public class FluentFilesImpl extends FilesDataContext implements FluentFiles {

    public FluentFilesImpl(String path) {
        super(path);
    }

}
