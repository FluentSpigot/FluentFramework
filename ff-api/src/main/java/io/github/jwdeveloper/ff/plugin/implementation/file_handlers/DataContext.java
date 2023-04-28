package io.github.jwdeveloper.ff.plugin.implementation.file_handlers;

import io.github.jwdeveloper.ff.core.repository.api.CustomFile;

public interface DataContext
{
    void addCustomFileObject(CustomFile object);

    void addJsonObject(Object object);

    void addObject(Class<? extends FileHandler> handlerType, Object object);

    void registerFileHandler(FileHandler fileHandler);

    void save();
}
