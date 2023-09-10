package io.github.jwdeveloper.ff.extension.files.implementation.fluent_files;

import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.files.json.JsonUtility;
import io.github.jwdeveloper.ff.extension.files.api.FluentFileModel;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FluentFile;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.repository.SaveableRepository;
import io.github.jwdeveloper.ff.extension.files.implementation.config.FluentFilesConfig;
import lombok.Getter;

public class JsonRepositoryFile implements FluentFile<SaveableRepository> {
    private final SaveableRepository repository;
    private final FluentFilesConfig config;

    @Getter
    private final FluentFileModel model;

    public JsonRepositoryFile(FluentFileModel model, SaveableRepository repository, FluentFilesConfig config)
    {
        this.repository = repository;
        this.config = config;
        this.model = model;
    }


    @Override
    public SaveableRepository getTarget() {
        return repository;
    }

    @Override
    public void refresh()
    {
        save();
        load();
    }

    @Override
    public void load()
    {
        var content = JsonUtility.loadList(config.getSavingPath(), repository.getFileName(), repository.getEntityClass());
        repository.deleteAll();
        repository.insertMany(content);
    }

    @Override
    public void save()
    {
        JsonUtility.save(repository.getContent(), config.getSavingPath(), repository.getFileName());
    }

    @Override
    public String getPath() {
        return FileUtility.combinePath(config.getSavingPath(), repository.getFileName()+".json");
    }
}
