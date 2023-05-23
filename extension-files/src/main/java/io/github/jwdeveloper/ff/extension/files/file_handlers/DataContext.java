package io.github.jwdeveloper.ff.extension.files.file_handlers;
import io.github.jwdeveloper.ff.extension.files.api.CustomFile;

public interface DataContext
{
    void addCustomFileObject(CustomFile object);

    void addJsonObject(Object object);

    void addObject(Class<? extends FileHandler> handlerType, Object object);

    void registerFileHandler(FileHandler fileHandler);

    void save();
}
