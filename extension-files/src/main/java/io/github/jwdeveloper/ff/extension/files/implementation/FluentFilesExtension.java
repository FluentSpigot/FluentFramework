package io.github.jwdeveloper.ff.extension.files.implementation;

import io.github.jwdeveloper.ff.core.injector.api.containers.Container;
import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.core.injector.api.events.events.OnInjectionEvent;
import io.github.jwdeveloper.ff.extension.files.api.fluent_files.FluentFile;
import io.github.jwdeveloper.ff.extension.files.api.FluentFilesManager;
import io.github.jwdeveloper.ff.extension.files.implementation.config.FluentFilesConfig;
import io.github.jwdeveloper.ff.extension.files.implementation.config.FluentFilesOptions;
import io.github.jwdeveloper.ff.extension.files.implementation.builder.FluentFileBuilderResult;
import io.github.jwdeveloper.ff.extension.files.implementation.builder.FluentFilesBuilderImpl;
import io.github.jwdeveloper.ff.extension.files.implementation.builder.FluentFilesFactoryImpl;
import io.github.jwdeveloper.ff.plugin.api.FluentApiContainerBuilder;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.ExtentionPriority;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;

import java.util.function.Consumer;
import java.util.regex.Pattern;

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


        var builder = (FluentFilesBuilderImpl) options.getBuilder();
        var builderResult = builder.build();

        registerFluentFilesToContainer(builderResult, fluentApiBuilder.container());
        fluentApiBuilder.bindToConfig(FluentFilesConfig.class);
        fluentApiBuilder.container()
                .register(FluentFilesManager.class,
                        LifeTime.SINGLETON,
                        (x) -> registerFileManager(fluentApiBuilder, x, options, builderResult));
        fluentApiBuilder.container()
                .configure(containerConfiguration ->
                {
                    containerConfiguration.onInjection(this::resolveFluentFileInjection);
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

    private void registerFluentFilesToContainer(FluentFileBuilderResult builderResult, FluentApiContainerBuilder container)
    {
        for(var model : builderResult.getFluentFileModels())
        {
            if(model.hasObject())
            {
                container.registerSigleton(model.getClassType(), model.getObject());
                continue;
            }
            container.registerSigleton(model.getClassType());
        }
    }

    private Object registerFileManager(FluentApiSpigotBuilder builder,
                                       Container container,
                                       FluentFilesOptions options,
                                       FluentFileBuilderResult builderResult)
    {
        var defaultSavingPath = builder.pluginPath().resolve(options.getPath()).toString();
        var config = (FluentFilesConfig) container.find(FluentFilesConfig.class);
        config.setSavingPath(defaultSavingPath);

        manager = new FluentFilesManagerImpl(config, builder.tasks(), builder.logger());
        var factory = new FluentFilesFactoryImpl(config);
        var fluentFiles = factory.getFluentFiles(builderResult, container);
        manager.addFileToTrack(fluentFiles);
        return manager;
    }

    private Object resolveFluentFileInjection(OnInjectionEvent event) {
        if (!event.input().isAssignableFrom(FluentFile.class)) {
            return event.output();
        }
        try {
            var generic = event.inputGenericParameters()[0];

            var pattern = Pattern.compile("<(.*?)>");
            var matcher = pattern.matcher(generic.getTypeName());

            var typeName = generic.getTypeName();
            while (matcher.find()) {
                typeName = matcher.group(1);
            }

            var clazz = Class.forName(typeName);

            return manager.getTrackedFiles().get(clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
