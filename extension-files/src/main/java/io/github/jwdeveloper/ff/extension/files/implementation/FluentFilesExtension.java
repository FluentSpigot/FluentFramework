package io.github.jwdeveloper.ff.extension.files.implementation;

import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.extension.files.api.FluentFile;
import io.github.jwdeveloper.ff.extension.files.api.FluentFilesManager;
import io.github.jwdeveloper.ff.extension.files.implementation.config.FluentFilesConfig;
import io.github.jwdeveloper.ff.extension.files.implementation.config.FluentFilesOptions;
import io.github.jwdeveloper.ff.extension.files.implementation.fluent_files.builder.FluentFilesBuilderImpl;
import io.github.jwdeveloper.ff.extension.files.implementation.fluent_files.builder.FluentFilesFactoryImpl;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.ExtentionPriority;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;

import java.util.function.Consumer;

public class FluentFilesExtension implements FluentApiExtension {

    private final Consumer<FluentFilesOptions> consumer;
    private FluentFilesManagerImpl manager;

    public FluentFilesExtension(Consumer<FluentFilesOptions> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder fluentApiBuilder) {
        var options = new FluentFilesOptions();
        consumer.accept(options);


        var trackingFiles = (FluentFilesBuilderImpl) options.getBuilder();
        var result = trackingFiles.build();

        for (var classType : result.getJsonFiles().getFirsts()) {
            fluentApiBuilder.container().registerSigleton(classType);
        }
        for (var object : result.getJsonFiles().getSeconds()) {
            fluentApiBuilder.container().registerSigleton(object.getClass(), object);
        }
        fluentApiBuilder.bindToConfig(FluentFilesConfig.class);
        fluentApiBuilder.container().register(FluentFilesManager.class, LifeTime.SINGLETON, (x) ->
        {
            var savingPath = fluentApiBuilder.pluginPath().resolve(options.getPath()).toString();
            var config = (FluentFilesConfig) x.find(FluentFilesConfig.class);
            config.setSavingPath(savingPath);

            manager = new FluentFilesManagerImpl(config, fluentApiBuilder.tasks(), fluentApiBuilder.logger());
            var factory = new FluentFilesFactoryImpl(config);
            var fluentFiles = factory.getFluentFiles(result, x);
            manager.addFileToTrack(fluentFiles);
            return manager;
        });

        fluentApiBuilder.container().configure(containerConfiguration ->
        {
            containerConfiguration.onInjection(event ->
            {
                if (!event.input().isAssignableFrom(FluentFile.class))
                {
                    return event.output();
                }
                try
                {
                    var generic = event.inputGenericParameters()[0];
                    var clazz = Class.forName(generic.getTypeName());
                    return  manager.getTrackedFiles().get(clazz);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) {
        var manager = (FluentFilesManagerImpl) fluentAPI.container().findInjection(FluentFilesManager.class);
        manager.start();

    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) {
        if (manager == null)
            return;

        manager.stop();
    }


    @Override
    public ExtentionPriority getPriority() {
        return ExtentionPriority.LOW;
    }

    @Override
    public String getName() {
        return "files";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
