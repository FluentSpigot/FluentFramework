package io.github.jwdeveloper.ff.extension.files.implementation.config;

import io.github.jwdeveloper.ff.extension.files.api.FileFilesBuilder;
import io.github.jwdeveloper.ff.extension.files.api.FluentFileModel;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FileWatcher;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.repository.Repository;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.TextFile;
import io.github.jwdeveloper.ff.extension.files.implementation.builder.FluentFilesBuilderImpl;
import io.github.jwdeveloper.ff.plugin.api.extention.ExtensionOptions;
import lombok.Data;

import java.util.function.Consumer;

@Data
public class FluentFilesOptions extends ExtensionOptions implements FileFilesBuilder
{
    // server/plugins/<plugin>/ + path
    private String path = "files";

    private String configPath = "files";
    private FileFilesBuilder builder = new FluentFilesBuilderImpl();

    @Override
    public <T extends Repository> void addJsonRepository(Class<T> repository) {
        builder.addJsonRepository(repository);
    }

    @Override
    public void addFluentFile(Consumer<FluentFileModel> model) {
        builder.addFluentFile(model);
    }

    @Override
    public void addFluentFile(FluentFileModel model) {
        builder.addFluentFile(model);
    }

    @Override
    public <T> void addJsonFile(Class<T> fileClass) {
        builder.addJsonFile(fileClass);
    }

    @Override
    public <T> void addJsonFile(T fileClass) {
        builder.addJsonFile(fileClass);
    }

    @Override
    public <T extends TextFile> void addTextFile(Class<T> fileClass) {
        builder.addTextFile(fileClass);
    }

    @Override
    public <T extends TextFile> void addTextFile(T fileClass) {
        builder.addTextFile(fileClass);
    }


    @Override
    public <T extends Repository> void addJsonRepository(T repository) {
        builder.addJsonRepository(repository);
    }


    @Override
    public <T extends FileWatcher> void addFileWatcher( Class<T> repository) {
        builder.addFileWatcher(repository);
    }

    @Override
    public <T extends FileWatcher> void addFileWatcher(T repository) {
        builder.addFileWatcher(repository);
    }
}
