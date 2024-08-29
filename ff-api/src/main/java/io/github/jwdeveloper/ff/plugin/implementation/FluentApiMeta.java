package io.github.jwdeveloper.ff.plugin.implementation;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;

import io.github.jwdeveloper.ff.core.common.versions.VersionCompare;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.plugin.api.config.FluentConfig;
import io.github.jwdeveloper.ff.plugin.implementation.config.options.PluginState;

import io.github.jwdeveloper.spigot.commands.api.builder.CommandBuilder;
import org.bukkit.plugin.Plugin;


public class FluentApiMeta {
    private final FluentConfig config;
    private final Plugin plugin;
    private final String path = "plugin-meta.environment";
    private final CommandBuilder defaultCommandBuilder;
    private final PluginState pluginState;

    public FluentApiMeta(FluentConfig config, Plugin plugin, CommandBuilder builder) {
        this.config = config;
        this.defaultCommandBuilder = builder;
        this.plugin = plugin;
        this.pluginState = loadPluginState();
    }

    public String getEnvironment() {
        return config.getOrCreate(path, "debug");
    }

    public void setEnvironment(String environment) {
        config.set(path, environment);
        config.save();
    }

    public boolean isEnvironment(String environment) {
        if (StringUtils.isNullOrEmpty(environment)) {
            throw new RuntimeException("Environment should not be null");
        }

        var env = getEnvironment();
        if (StringUtils.isNullOrEmpty(env)) {
            return false;
        }
        return environment.equalsIgnoreCase(env);
    }

    public boolean isDebug() {
        return isEnvironment("debug");
    }

    public boolean isRelease() {
        return isEnvironment("release");
    }

    public String getDefaultCommandName() {
        return defaultCommandBuilder.properties().name();
    }

    public boolean isPluginUpdated() {
        return pluginState == PluginState.UPDATED;
    }

    public boolean isPluginCreated() {
        return pluginState == PluginState.CREATED;
    }

    public boolean isPluginDowngraded() {
        return pluginState == PluginState.DOWNGRADED;
    }

    public PluginState getPluginState() {
        return pluginState;
    }

    public PluginState loadPluginState() {
        setEnviroment();
        var currentVersion = plugin.getDescription().getVersion();
        var configPluginVersion = config.configFile().getString("plugin-meta.plugin-version");
        config.set("plugin-meta.plugin-version", currentVersion);
        config.save();
        if (StringUtils.isNullOrEmpty(configPluginVersion)) {
            return PluginState.CREATED;
        }
        if (currentVersion.equals(configPluginVersion)) {
            return PluginState.NONE;
        }
        if (VersionCompare.isHigher(currentVersion, configPluginVersion)) {
            return PluginState.UPDATED;
        }
        if (VersionCompare.isLower(currentVersion, configPluginVersion)) {
            return PluginState.DOWNGRADED;
        }
        return PluginState.NONE;
    }

    private void setEnviroment() {
        var pluginYml = FileUtility.loadFileFromResponse("plugin.yml", plugin.getClass());
        var pluginYmlEnviroment = pluginYml.get("environment");
        if (pluginYmlEnviroment == null) {
            return;
        }
        if (!config.configFile().isConfigurationSection("plugin-meta")) {
            return;
        }
        var section = config.configFile().getConfigurationSection("plugin-meta");
        if (section.contains("environment")) {
            return;
        }
        section.set("environment", pluginYmlEnviroment);
        config.save();
    }
}


