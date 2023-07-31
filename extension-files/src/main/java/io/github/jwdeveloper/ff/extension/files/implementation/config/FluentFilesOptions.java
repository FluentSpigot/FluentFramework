package io.github.jwdeveloper.ff.extension.files.implementation.config;

import io.github.jwdeveloper.ff.extension.files.api.FileFilesBuilder;
import io.github.jwdeveloper.ff.extension.files.api.FileWatcher;
import io.github.jwdeveloper.ff.extension.files.api.repository.Repository;
import io.github.jwdeveloper.ff.extension.files.implementation.fluent_files.builder.FluentFilesBuilderImpl;
import io.github.jwdeveloper.ff.plugin.api.extention.ExtensionOptions;
import lombok.Data;

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
