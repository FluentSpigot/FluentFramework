package io.github.jwdeveloper.ff.extension.files.implementation.fluent_files.builder;

import io.github.jwdeveloper.ff.core.injector.api.containers.Container;
import io.github.jwdeveloper.ff.extension.files.api.FluentFile;
import io.github.jwdeveloper.ff.extension.files.api.repository.SaveableRepository;
import io.github.jwdeveloper.ff.extension.files.implementation.config.FluentFilesConfig;
import io.github.jwdeveloper.ff.extension.files.implementation.fluent_files.JsonFile;

import java.util.ArrayList;
import java.util.List;

public class FluentFilesFactoryImpl {

    private final FluentFilesConfig config;

    public FluentFilesFactoryImpl(FluentFilesConfig config) {
        this.config = config;
    }

    public List<FluentFile> getFluentFiles(BuilderResult builderResult, Container container) {
        var result = new ArrayList<FluentFile>();
        result.addAll(getJsonFiles(builderResult, container));
        return result;
    }


    private List<FluentFile> getJsonFiles(BuilderResult builderResult, Container container) {
        var types = new ArrayList<Class<?>>(builderResult.getJsonFiles().getFirsts());
        types.addAll(builderResult.getJsonFiles()
                .getSeconds()
                .stream()
                .map(Object::getClass)
                .toList());

        var result = new ArrayList<FluentFile>();
        for (var type : types)
        {
            var object = (SaveableRepository)container.find(type);
            var jsonFile = new JsonFile(object, config);
            result.add(jsonFile);
        }
        return result;
    }

}
