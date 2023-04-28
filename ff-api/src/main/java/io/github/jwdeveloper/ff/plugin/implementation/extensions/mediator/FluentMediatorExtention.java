package io.github.jwdeveloper.ff.api.implementation.extensions.mediator;

import io.github.jwdeveloper.spigot.fluent.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.spigot.fluent.core.mediator.api.MediatorHandler;
import io.github.jwdeveloper.spigot.fluent.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtension;

public class FluentMediatorExtention implements FluentApiExtension {

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        var mediators = builder.classFinder().findByInterface(MediatorHandler.class);
        builder.container().register(
                FluentMediator.class,
                LifeTime.SINGLETON,
                (x) -> new FluentMediatorImpl(mediators, x::find, builder.logger()));
    }
}
