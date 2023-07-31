package io.github.jwdeveloper.ff.extension.files.api;

import io.github.jwdeveloper.ff.extension.files.api.repository.Repository;

public interface FileFilesBuilder {
    <T extends Repository> void addJsonRepository(Class<T> repository);

    <T extends Repository> void addJsonRepository(T repository);

    <T extends FileWatcher> void addFileWatcher(Class<T> repository);

    <T extends FileWatcher> void addFileWatcher(T repository);
}
