package io.github.jwdeveloper.ff.extension.files.implementation.builder;

import io.github.jwdeveloper.ff.core.injector.api.containers.Container;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.extension.files.api.FluentFileModel;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FileWatcher;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FluentFile;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FolderWatcher;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.TextFile;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.repository.SaveableRepository;
import io.github.jwdeveloper.ff.extension.files.implementation.config.FluentFilesConfig;
import io.github.jwdeveloper.ff.extension.files.implementation.fluent_files.*;

import java.util.ArrayList;
import java.util.List;

public class FluentFilesFactoryImpl {

    private final FluentFilesConfig config;

    public FluentFilesFactoryImpl(FluentFilesConfig config) {
        this.config = config;
    }

    public List<FluentFile> getFluentFiles(FluentFileBuilderResult builderResult, Container container) {
        var result = new ArrayList<FluentFile>();
        for (var model : builderResult.getFluentFileModels()) {
            var fluentFile = switch (model.getType()) {
                case FileText -> getTextFiles(model, container);
                case JsonFile -> getJsonFiles(model,container);
                case JsonRepository -> getJsonRepositoryFiles(model,container);
                case FileWatcher -> getFileWatcher(model,container);
                case FolderWatcher -> getFolderWatcher(model,container);
            };
            result.add(fluentFile);
        }
        return result;
    }


    private FluentFileWatcher getFileWatcher(FluentFileModel model, Container container) {
        var object = (FileWatcher)container.find(model.getClassType());
        var tasksFactory = (FluentTaskFactory)container.find(FluentTaskFactory.class);
        return new FluentFileWatcher(model, object, config, tasksFactory);
    }

    private JsonRepositoryFile getJsonRepositoryFiles(FluentFileModel model, Container container) {
        var object = (SaveableRepository) container.find(model.getClassType());
        return new JsonRepositoryFile(model, object, config);
    }

    private JsonFileWrapper getJsonFiles(FluentFileModel model, Container container) {
        var object = container.find(model.getClassType());
        return new JsonFileWrapper(model, object, config);
    }

    private TextFileWrapper getTextFiles(FluentFileModel model, Container container) {
        var object = (TextFile) container.find(model.getClassType());
        return new TextFileWrapper(model, object, config);
    }

    private FolderFileWatcher getFolderWatcher(FluentFileModel model, Container container) {
        var object = (FolderWatcher) container.find(model.getClassType());
        var tasksFactory = (FluentTaskFactory)container.find(FluentTaskFactory.class);
        return new FolderFileWatcher(model, object, tasksFactory);
    }

}
