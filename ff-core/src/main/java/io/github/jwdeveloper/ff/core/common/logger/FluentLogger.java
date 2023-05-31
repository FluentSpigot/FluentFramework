package io.github.jwdeveloper.ff.core.common.logger;

import java.util.logging.Logger;

public class FluentLogger
{
    public static PluginLogger LOGGER;

    public static PluginLogger setLogger(Logger logger)
    {
        if(LOGGER == null)
        {
            LOGGER = new SimpleLogger(logger);
        }
        return LOGGER;
    }
}
