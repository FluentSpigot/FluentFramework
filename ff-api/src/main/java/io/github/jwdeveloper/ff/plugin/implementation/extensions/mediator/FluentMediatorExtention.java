package io.github.jwdeveloper.ff.plugin.implementation.extensions.mediator;

import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.core.mediator.api.MediatorHandler;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;

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
