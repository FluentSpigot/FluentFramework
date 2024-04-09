package io.github.jw.spigot.ff.example;

import io.github.jwdeveloper.dependance.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;

@Injection
public class ExampleConfig
{

    public ExampleConfig()
    {
        FluentLogger.LOGGER.info("Hello world!");
    }

    @Injection
    public static String getString()
    {
        return "dupa";
    }



}
