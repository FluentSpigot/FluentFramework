package io.github.jwdeveloper.ff.extension.files.implementation.fluent_files.builder;

import io.github.jwdeveloper.ff.extension.files.api.FileFilesBuilder;
import io.github.jwdeveloper.ff.extension.files.api.FileWatcher;
import io.github.jwdeveloper.ff.extension.files.api.repository.Repository;

public class FluentFilesBuilderImpl implements FileFilesBuilder {

    private final BuilderResult result;

    public FluentFilesBuilderImpl() {
        result = new BuilderResult();
    }

    @Override
    public <T extends Repository> void addJsonRepository(Class<T> repository) {
        result.getJsonFiles().addFirst(repository);
    }

    @Override
    public <T extends Repository> void addJsonRepository(T repository) {
        result.getJsonFiles().addSecond(repository);
    }

    @Override
    public <T extends FileWatcher> void addFileWatcher(Class<T> repository) {

    }

    @Override
    public <T extends FileWatcher> void addFileWatcher(T repository) {

    }

    public BuilderResult build()
    {
        return result;
    }

}
