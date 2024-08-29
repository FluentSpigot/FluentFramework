package io.github.jwdeveloper.ff.extension.resourcepack.implementation;


import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.extension.resourcepack.api.FluentResourcepack;
import io.github.jwdeveloper.ff.extension.resourcepack.api.ResourcepackOptions;
import io.github.jwdeveloper.ff.extension.resourcepack.implementation.data.ResourcepackConfig;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.ff.plugin.implementation.config.options.FluentConfigFile;
import io.github.jwdeveloper.spigot.commands.builder.CommandBuilder;

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
        builder.mainCommand().addSubCommand(options.getCommandName(), subBuilder ->
        {
            subBuilder.withDescription("Manages plugin resource packs");
            subBuilder.addSubCommand("download", this::downloadCommand);
            subBuilder.addSubCommand("link", this::linkCommand);
        });
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) {

        var config = (FluentConfigFile<ResourcepackConfig>) fluentAPI.container().findInjection(FluentConfigFile.class, ResourcepackConfig.class);
        if (StringUtils.isNullOrEmpty(config.get().getUrl())) {
            config.get().setUrl(options.getResourcepackUrl());
            config.save();
        }
    }

    public void downloadCommand(CommandBuilder commandBuilder) {
        commandBuilder.withDescription("Downloads plugin resource pack");
        commandBuilder.onPlayerExecute(event ->
        {
            var service = FluentApi.container().findInjection(FluentResourcepack.class);
            service.downloadResourcepack(event.sender());
        });
    }

    public void linkCommand(CommandBuilder commandBuilder) {
        commandBuilder.withDescription("Sending to player resource pack link");
        commandBuilder.onPlayerExecute(event ->
        {
            var service = FluentApi.container().findInjection(FluentResourcepack.class);
            service.sendResourcepackInfo(event.sender());
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
