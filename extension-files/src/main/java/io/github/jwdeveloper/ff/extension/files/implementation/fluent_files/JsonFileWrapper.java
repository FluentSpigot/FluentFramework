package io.github.jwdeveloper.ff.extension.files.implementation.fluent_files;

import io.github.jwdeveloper.ff.core.common.java.ObjectUtils;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.files.json.JsonUtility;
import io.github.jwdeveloper.ff.extension.files.api.FluentFileModel;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FluentFile;
import io.github.jwdeveloper.ff.extension.files.implementation.config.FluentFilesConfig;
import lombok.Getter;

public class JsonFileWrapper implements FluentFile<Object> {
    private Object fileObject;
    private final FluentFilesConfig config;

    @Getter
    private final FluentFileModel model;

    public JsonFileWrapper(FluentFileModel model, Object fileObject, FluentFilesConfig config) {
        this.fileObject = fileObject;
        this.config = config;
        this.model = model;
    }


    @Override
    public Object getTarget() {
        return fileObject;
    }

    @Override
    public void refresh() {
        save();
        load();
    }

    @Override
    public void load() {
        try {
            var loadFromFile = JsonUtility.load(config.getSavingPath(), fileObject.getClass().getSimpleName(), fileObject.getClass());
            ObjectUtils.copyToObjectDeep(loadFromFile, fileObject);
        } catch (Exception e) {
            throw new RuntimeException("Unable to load json file", e);
        }

    }

    @Override
    public void save() {
        JsonUtility.save(fileObject, config.getSavingPath(), fileObject.getClass().getSimpleName());
    }

    @Override
    public String getPath() {
        return FileUtility.combinePath(config.getSavingPath(), fileObject.getClass().getSimpleName() + ".json");
    }
}
