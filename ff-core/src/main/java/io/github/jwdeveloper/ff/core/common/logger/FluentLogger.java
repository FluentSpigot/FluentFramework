package io.github.jwdeveloper.ff.core.common.logger;

import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public class FluentLogger
{
    public static PluginLogger LOGGER;

    public static PluginLogger setLogger(String name)
    {
        if(LOGGER == null)
        {
            LOGGER = new SimpleLogger(name);
        }
        return LOGGER;
    }
}
