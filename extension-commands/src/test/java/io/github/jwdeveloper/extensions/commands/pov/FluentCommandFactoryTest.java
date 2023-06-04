package io.github.jwdeveloper.extensions.commands.pov;

import io.github.jwdeveloper.ff.extension.commands.implementation.FluentCommandFactory;
import io.github.jwdeveloper.ff.api.tests.FluentApiTest;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FluentCommandFactoryTest extends FluentApiTest {
    @Override
    public void onBuild(FluentApiSpigotBuilder fluentApiBuilder) {

    }

    @Test
    public void shouldCreate() {

        var builder = getFluentApiMock().createCommand("example");
        var factory = new FluentCommandFactory();
        var clazz = ExampleCommand.class;
        var invokers = factory.create(clazz, builder);
        invokers.get(0).setCommandObject(new ExampleCommand());

        var command = builder.build();

        command.setLogs(true);
        var result = command.execute(getPlayer(), "example", new String[]{"mark", "12", "join", "12"});

        Assertions.assertTrue(result);
    }


}