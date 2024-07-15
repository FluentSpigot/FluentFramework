package io.github.jw.spigot.ff.example.test;

import immersive.ImmersiveBlocksExtension;
import io.github.jw.spigot.ff.example.ExampleFFPlugin;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.plugin.FluentPluginBuilder;
import io.github.jwdeveloper.ff.plugin.tests.FluentPluginTest;
import org.junit.jupiter.api.Test;

import java.lang.management.PlatformLoggingMXBean;

public class ExampleTest extends FluentPluginTest
{

    @Override
    public Class<?> mainPluginClass() {
        return ExampleFFPlugin.class;
    }

    @Override
    public void onFluentPluginBuild(FluentPluginBuilder builder) {

        FluentLogger.LOGGER.info("hello world");
        builder.withExtension(new ImmersiveBlocksExtension());
        builder.withCustomExtension(fluentApiExtentionBuilder ->
        {
            fluentApiExtentionBuilder.onConfiguration(fluentApiSpigotBuilder ->
            {
                var i =0;
                fluentApiSpigotBuilder.container().scan(ExampleTest.class,(classes, containerBuilder) ->
                {

                    FluentLogger.LOGGER.info("hello from scanner!");
                });
               var scanner =  fluentApiSpigotBuilder.jarScanner();

            });
        });
    }


    @Test
    public void test()
    {

    }
}
