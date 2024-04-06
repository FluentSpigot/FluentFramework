package io.github.jw.spigot.ff.example.test;

import io.github.jw.spigot.ff.example.ExampleFFPlugin;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.plugin.FluentPluginBuilder;
import io.github.jwdeveloper.ff.plugin.tests.FluentPluginTest;
import org.junit.jupiter.api.Test;

public class ExampleTest extends FluentPluginTest
{

    @Override
    public Class<?> mainPluginClass() {
        return ExampleFFPlugin.class;
    }

    @Override
    public void onFluentPluginBuild(FluentPluginBuilder builder) {

        FluentLogger.LOGGER.info("hello world");
    }


    @Test
    public void test()
    {

    }
}
