package io.github.jwdeveloper.ff.extension.resourcepack.implementation;


import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.SimpleCommandBuilder;
import io.github.jwdeveloper.ff.extension.resourcepack.api.FluentResourcepack;
import io.github.jwdeveloper.ff.extension.resourcepack.api.ResourcepackOptions;
import io.github.jwdeveloper.ff.extension.resourcepack.implementation.data.ResourcepackConfig;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.ff.plugin.implementation.config.options.FluentConfigFile;

import java.util.function.Consumer;

public class ResourcepackExtention implements FluentApiExtension {
    private final Consumer<ResourcepackOptions> consumer;
    private ResourcepackOptions options;

    public ResourcepackExtention(Consumer<ResourcepackOptions> options) {
        this.consumer = options;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        options = new ResourcepackOptions();
        consumer.accept(options);

        builder.container().registerSingleton(FluentResourcepack.class, FluentResourcepackImpl.class);
        builder.bindToConfig(ResourcepackConfig.class, options.getConfigPath());
        builder.defaultCommand().subCommandsConfig(subCommandConfig ->
        {
            var fullCommandName = "/" + builder.defaultCommand().getName() + " " + options.getCommandName();
            subCommandConfig.addSubCommand(options.getCommandName(), commandBuilder ->
            {
                commandBuilder.propertiesConfig(propertiesConfig ->
                {
                    propertiesConfig.setDescription("Manage plugin resourcepack");
                    propertiesConfig.setUsageMessage(fullCommandName);
                });
                commandBuilder.subCommandsConfig(subCommandConfig1 ->
                {
                    subCommandConfig1.addSubCommand("download", e -> downloadCommand(e, fullCommandName));
                    subCommandConfig1.addSubCommand("link", e -> linkCommand(e, fullCommandName));
                });

            });
        });
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

        var config = (FluentConfigFile<ResourcepackConfig>) fluentAPI.container().findInjection(FluentConfigFile.class, ResourcepackConfig.class);
        if (StringUtils.isNullOrEmpty(config.get().getUrl()))
        {
                 config.get().setUrl(options.getResourcepackUrl());
                 config.save();
        }

    }

    public void downloadCommand(SimpleCommandBuilder commandBuilder, String commandName) {
        commandBuilder.propertiesConfig(propertiesConfig ->
                {
                    propertiesConfig.setDescription("downloads plugin resourcepack");
                    propertiesConfig.setUsageMessage(commandName + " download");
                })
                .eventsConfig(eventConfig ->
                {
                    eventConfig.onPlayerExecute(event ->
                    {
                        var service = FluentApi.container().findInjection(FluentResourcepack.class);
                        service.downloadResourcepack(event.getPlayer());
                    });
                });
    }

    public void linkCommand(SimpleCommandBuilder commandBuilder, String commandName) {
        commandBuilder.propertiesConfig(propertiesConfig ->
                {
                    propertiesConfig.setDescription("Sending to player resourcepack link");
                    propertiesConfig.setUsageMessage(commandName + " link");
                })
                .eventsConfig(eventConfig ->
                {
                    eventConfig.onPlayerExecute(event ->
                    {
                        var service = FluentApi.container().findInjection(FluentResourcepack.class);
                        service.sendResourcepackInfo(event.getPlayer());
                    });
                });
    }


    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String getName() {
        return "resourcepack";
    }
}
