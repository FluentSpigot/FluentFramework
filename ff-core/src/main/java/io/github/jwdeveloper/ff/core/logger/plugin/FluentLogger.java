package io.github.jwdeveloper.ff.core.logger.plugin;

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
