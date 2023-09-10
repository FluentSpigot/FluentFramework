package io.github.jwdeveloper.ff.plugin.implementation.extensions.decorator;

import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.injector.decorator.api.builder.DecoratorBuilder;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;

public class FluentDecoratorExtention implements FluentApiExtension {

    private DecoratorBuilder decoratorBuilder;

    public FluentDecoratorExtention(DecoratorBuilder decoratorBuilder)
    {
        this.decoratorBuilder = decoratorBuilder;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

        builder.container().configure(containerConfiguration ->
        {
            try
            {
                var decorator = decoratorBuilder.build();
                containerConfiguration.onEvent(decorator);
            } catch (Exception e) {
                FluentLogger.LOGGER.error("Unable to register decorator ",e);
                return;
            }
        });
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {

    }
}
