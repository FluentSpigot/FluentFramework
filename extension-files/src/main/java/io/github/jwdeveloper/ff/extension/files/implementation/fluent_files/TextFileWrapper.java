package io.github.jwdeveloper.ff.extension.files.implementation.fluent_files;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.extension.files.api.FluentFileModel;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FluentFile;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.TextFile;
import io.github.jwdeveloper.ff.extension.files.implementation.config.FluentFilesConfig;
import lombok.Getter;

public class TextFileWrapper implements FluentFile<TextFile> {
    private final TextFile textFile;
    private final FluentFilesConfig config;

    @Getter
    private final FluentFileModel model;

    public TextFileWrapper(FluentFileModel model, TextFile textFile, FluentFilesConfig config) {
        this.textFile = textFile;
        this.config = config;
        this.model = model;
    }


    @Override
    public TextFile getTarget() {
        return textFile;
    }

    @Override
    public void refresh() {
        save();
        load();
    }

    @Override
    public void load() {
        try {
            var content = FileUtility.loadFileContent(getPath());
            if(StringUtils.isNullOrEmpty(content))
            {
                content = StringUtils.EMPTY;
            }
            textFile.setContent(content);
        } catch (Exception e) {
            throw new RuntimeException("Unable to load text file", e);
        }

    }

    @Override
    public void save()
    {
        var content = textFile.getContent();
        FileUtility.save(content, getPath());
    }

    @Override
    public String getPath()
    {
        if(model.hasCustomPath())
        {
            return FileUtility.combinePath(model.getCustomPath(), textFile.getFileName());
        }
        return FileUtility.combinePath(config.getSavingPath(), textFile.getFileName());
    }
}