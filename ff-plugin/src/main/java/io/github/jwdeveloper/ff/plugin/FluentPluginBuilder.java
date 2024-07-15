package io.github.jwdeveloper.ff.plugin;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.commands.SimpleCommandApi;
import io.github.jwdeveloper.ff.extension.commands.FluentCommandFramework;
import io.github.jwdeveloper.ff.extension.commands.api.data.FluentCommandOptions;
import io.github.jwdeveloper.ff.extension.files.FluentFilesApi;
import io.github.jwdeveloper.ff.extension.files.implementation.config.FluentFilesOptions;
import io.github.jwdeveloper.ff.extension.translator.FluentTranslatorAPI;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslatorOptions;
import io.github.jwdeveloper.ff.extension.updater.FluentUpdaterApi;
import io.github.jwdeveloper.ff.extension.updater.api.UpdaterApiOptions;
import io.github.jwdeveloper.ff.plugin.addons.AddonsApi;
import io.github.jwdeveloper.ff.plugin.addons.AddonsOptions;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.ff.plugin.metrics.BstatsApi;
import org.bukkit.*;
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

    public FluentPluginBuilder withCustomExtension(Consumer<FluentApiExtentionBuilder> builderConsumer) {
        var builder = new FluentApiExtentionBuilder(plugin);
        builderConsumer.accept(builder);
        apiExtensions.add(builder.build());
        return this;
    }

    public FluentPluginBuilder withTranslator(Consumer<FluentTranslatorOptions> options) {
        apiExtensions.add(FluentTranslatorAPI.use(options));
        return this;
    }

    public FluentPluginBuilder withFiles(Consumer<FluentFilesOptions> options) {
        apiExtensions.add(FluentFilesApi.use(options));
        return this;
    }

    public FluentPluginBuilder withTranslator() {
        apiExtensions.add(FluentTranslatorAPI.use());
        return this;
    }

    public FluentPluginBuilder withUpdater(Consumer<UpdaterApiOptions> options) {
        apiExtensions.add(FluentUpdaterApi.use(options));
        return this;
    }

    public FluentPluginBuilder withCommand(Consumer<FluentCommandOptions> options) {
        apiExtensions.add(FluentCommandFramework.use(options));
        return this;
    }

    public FluentPluginBuilder withAddons(Consumer<AddonsOptions> options) {
        apiExtensions.add(AddonsApi.use(options));
        return this;
    }

    public FluentPluginBuilder withAddons() {
        return withAddons((x)->{});
    }

    public FluentPluginBuilder withBstatsMetrics(int bstatsMetricsId) {
        apiExtensions.add(BstatsApi.use(bstatsMetricsId));
        return this;
    }


    public FluentApiSpigot create() {
        try {
            return tryCreate();
        } catch (Exception e) {
            Bukkit.getPluginManager().disablePlugin(plugin);
            FluentLogger.LOGGER.error("Unable to run FluentApi", e);
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

        if (api.meta().isDebug()) {
            SimpleCommandApi.create("disable")
                    .propertiesConfig(propertiesConfig ->
                    {
                        propertiesConfig.setDescription("Command only for plugin development purpose. Can be only trigger by Console. disables all plugins");
                        propertiesConfig.setUsageMessage("/disable");
                        propertiesConfig.setHideFromTabDisplay(true);
                        propertiesConfig.setHideFromDocumentation(true);
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


            SimpleCommandApi.create("debbug")
                    .propertiesConfig(propertiesConfig ->
                    {
                        propertiesConfig.setDescription("Command only for plugin development purpose. Can be only trigger by Console. disables all plugins");
                        propertiesConfig.setUsageMessage("/debbug");
                        propertiesConfig.setHideFromTabDisplay(true);
                        propertiesConfig.setHideFromDocumentation(true);
                    })
                    .eventsConfig(eventConfig ->
                    {
                        eventConfig.onPlayerExecute(playerCommandEvent ->
                        {
                            if (!playerCommandEvent.getPlayer().isOp()) {
                                return;
                            }

                            var world = Bukkit.getWorld("debbugworld");
                            if (world == null) {
                                world = Bukkit.createWorld(new WorldCreator("debbugworld").type(WorldType.FLAT));
                            }
                            world.setDifficulty(Difficulty.PEACEFUL);
                            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
                            playerCommandEvent.getPlayer().teleport(world.getSpawnLocation());
                        });
                    }).buildAndRegister();
        }
        return api;
    }
}
