package io.github.jwdeveloper.ff.plugin;

import io.github.jwdeveloper.ff.extension.commands.FluentCommandAPI;
import io.github.jwdeveloper.ff.extension.commands.api.FluentCommandOptions;
import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.commands.FluentCommand;
import io.github.jwdeveloper.ff.extension.translator.FluentTranslatorAPI;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslatorOptions;
import io.github.jwdeveloper.ff.extension.updater.FluentUpdaterApi;
import io.github.jwdeveloper.ff.extension.updater.api.UpdaterApiOptions;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.ff.plugin.metrics.BstatsApi;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FluentPluginBuilder {
    private final List<FluentApiExtension> apiExtensions;
    private final Plugin plugin;


    public FluentPluginBuilder(Plugin plugin) {
        this.plugin = plugin;
        apiExtensions = new ArrayList<>();
    }


    public FluentPluginBuilder withExtension(FluentApiExtension extension) {
        apiExtensions.add(extension);
        return this;
    }

    public FluentPluginBuilder withCustomExtension(Consumer<FluentApiExtentionBuilder> builderConsumer)
    {
        var builder = new FluentApiExtentionBuilder(plugin);
        builderConsumer.accept(builder);
        apiExtensions.add(builder.build());
        return this;
    }

    public FluentPluginBuilder withTranslator(Consumer<FluentTranslatorOptions> options)
    {
        apiExtensions.add(FluentTranslatorAPI.use(options));
        return this;
    }

    public FluentPluginBuilder withTranslator()
    {
        apiExtensions.add(FluentTranslatorAPI.use());
        return this;
    }

    public FluentPluginBuilder withUpdater(Consumer<UpdaterApiOptions> options)
    {
        apiExtensions.add(FluentUpdaterApi.use(options));
        return this;
    }

    public FluentPluginBuilder withCommand(Consumer<FluentCommandOptions> options)
    {
        apiExtensions.add(FluentCommandAPI.use(options));
        return this;
    }


    public FluentPluginBuilder withBstatsMetrics(int bstatsMetricsId)
    {
        apiExtensions.add(BstatsApi.use(bstatsMetricsId));
        return this;
    }


    public FluentApiSpigot create()
    {
        try {
            return tryCreate();
        } catch (Exception e) {
            Bukkit.getPluginManager().disablePlugin(plugin);
            FluentLogger.LOGGER.error("Unable to run FluentApi",e);
        }
        return null;
    }

    public FluentApiSpigot tryCreate() throws Exception {
        var builder = FluentApiBuilder.create(plugin);
        for (var extension : apiExtensions) {
            builder.useExtension(extension);
        }

        var api = builder.build();
        api.enable();
        api.events().onEvent(PluginDisableEvent.class, event ->
        {
            api.disable();
        });

        if(api.meta().isDebug())
        {
            FluentCommand.create("disable")
                    .propertiesConfig(propertiesConfig ->
                    {
                        propertiesConfig.setDescription("Command only for plugin development purpose. Can be only trigger by Console. disables all plugins");
                        propertiesConfig.setUsageMessage("/disable");
                        propertiesConfig.setHideFromTabDisplay(true);
                    })
                    .eventsConfig(eventConfig ->
                    {
                        eventConfig.onConsoleExecute(consoleCommandEvent ->
                        {
                            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Plugins disabled");
                            Bukkit.getPluginManager().disablePlugins();
                        });
                    })
                    .buildAndRegister();
        }
        return api;
    }
}
