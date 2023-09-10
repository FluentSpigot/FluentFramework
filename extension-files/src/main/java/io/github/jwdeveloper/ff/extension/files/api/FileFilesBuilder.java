package io.github.jwdeveloper.ff.extension.files.api;

import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FileWatcher;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.repository.Repository;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.TextFile;

import java.util.function.Consumer;

public interface FileFilesBuilder
{

    <T extends Repository> void addJsonRepository(Class<T> repository);
    void addFluentFile(Consumer<FluentFileModel> model);
    void addFluentFile(FluentFileModel model);

    <T> void addJsonFile(Class<T> fileClass);

    <T> void addJsonFile(T fileClass);

    <T extends TextFile> void addTextFile(Class<T> fileClass);

    <T extends TextFile> void addTextFile(T fileClass);

    <T extends Repository> void addJsonRepository(T repository);

    <T extends FileWatcher> void addFileWatcher(Class<T> repository);

    <T extends FileWatcher> void addFileWatcher(T repository);
}
