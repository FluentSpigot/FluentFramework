package io.github.jwdeveloper.ff.plugin.implementation.logger;

import io.github.jwdeveloper.ff.core.common.ColorPallet;
import io.github.jwdeveloper.ff.core.logger.plugin.PluginLogger;
import io.github.jwdeveloper.ff.core.spigot.messages.FluentMessages;
import io.github.jwdeveloper.ff.plugin.api.logger.LoggerConfiguration;
import io.github.jwdeveloper.ff.plugin.api.logger.PlayerLogger;
import io.github.jwdeveloper.ff.plugin.api.logger.PlayerLoggerConfig;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class LoggerConfigurationImpl implements LoggerConfiguration
{
    private final PluginLogger pluginLogger;
    private final PlayerLoggerConfig playerLoggerConfig;

    public LoggerConfigurationImpl(PluginLogger pluginLogger, Plugin plugin) {
        this.pluginLogger = pluginLogger;
        this.playerLoggerConfig = new PlayerLoggerConfig();
        this.playerLoggerConfig.setPrefix(plugin.getName());
        this.playerLoggerConfig.setColorPallet(ColorPallet.DEFAULT());
    }

    @Override
    public void configureLogger() {

    }

    @Override
    public void configurePlayerLogger(Consumer<PlayerLoggerConfig> config)
    {
        config.accept(playerLoggerConfig);
    }


    public PlayerLogger getPlayerLogger()
    {
        return new PlayerLoggerImpl(playerLoggerConfig, new FluentMessages());
    }

    public PluginLogger getPluginLogger()
    {
        return pluginLogger;
    }
}
