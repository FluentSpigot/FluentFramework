package io.github.jwdeveloper.ff.core.common.logger;

import java.util.logging.Logger;

public class FluentLogger
{
    public static BukkitLogger LOGGER;

    public static BukkitLogger setLogger(Logger logger)
    {
        if(LOGGER == null)
        {
            LOGGER = new BukkitLogger(logger);
        }
        return LOGGER;
    }
}
