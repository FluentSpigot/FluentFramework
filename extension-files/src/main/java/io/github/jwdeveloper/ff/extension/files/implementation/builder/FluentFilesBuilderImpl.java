package io.github.jwdeveloper.ff.extension.files.implementation.builder;

import io.github.jwdeveloper.ff.extension.files.api.FileFilesBuilder;
import io.github.jwdeveloper.ff.extension.files.api.FluentFileModel;
import io.github.jwdeveloper.ff.extension.files.api.FluentFileType;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FileWatcher;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.TextFile;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.repository.Repository;

import java.util.function.Consumer;

public class FluentFilesBuilderImpl implements FileFilesBuilder {

    private final FluentFileBuilderResult result;

    public FluentFilesBuilderImpl() {
        result = new FluentFileBuilderResult();
    }


    @Override
    public void addFluentFile(Consumer<FluentFileModel> consumer) {
        var model = new FluentFileModel();
        consumer.accept(model);
        addFluentFile(model);
    }

    @Override
    public void addFluentFile(FluentFileModel model) {

        result.getFluentFileModels().add(model);
    }

    @Override
    public <T> void addJsonFile(Class<T> fileClass)
    {
        addFluentFile(model ->
        {
            model.setClassType(fileClass);
            model.setType(FluentFileType.JsonFile);
        });
    }

    @Override
    public <T> void addJsonFile(T fileClass) {
        addFluentFile(model ->
        {
            model.setClassType(fileClass.getClass());
            model.setObject(fileClass);
            model.setType(FluentFileType.JsonFile);
        });
    }

    @Override
    public <T extends TextFile> void addTextFile(Class<T> fileClass) {
        addFluentFile(model ->
        {
            model.setClassType(fileClass);
            model.setType(FluentFileType.FileText);
        });
    }

    @Override
    public <T extends TextFile> void addTextFile(T fileObject) {
        addFluentFile(model ->
        {
            model.setClassType(fileObject.getClass());
            model.setObject(fileObject);
            model.setType(FluentFileType.FileText);
        });
    }

    @Override
    public <T extends Repository> void addJsonRepository(Class<T> repository) {
        addFluentFile(model ->
        {
            model.setClassType(repository);
            model.setType(FluentFileType.JsonRepository);
        });
    }


    @Override
    public <T extends Repository> void addJsonRepository(T repository) {
        addFluentFile(model ->
        {
            model.setClassType(repository.getClass());
            model.setObject(repository);
            model.setType(FluentFileType.JsonRepository);
        });
    }

    @Override
    public <T extends FileWatcher> void addFileWatcher(Class<T> repository)
    {
        addFluentFile(model ->
        {
            model.setClassType(repository);
            model.setType(FluentFileType.FileWatcher);
        });
    }

    @Override
    public <T extends FileWatcher> void addFileWatcher(T fileWatcher) {
        addFluentFile(model ->
        {
            model.setClassType(fileWatcher.getClass());
            model.setObject(fileWatcher);
            model.setType(FluentFileType.FileWatcher);
        });
    }

    public FluentFileBuilderResult build() {
        return result;
    }

}
