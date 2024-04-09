package io.github.jwdeveloper.ff.plugin.implementation.extensions.mediator;

import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.core.mediator.api.MediatorHandler;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;

public class fluentMediatorExtension implements FluentApiExtension {

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        var mediators = builder.jarScanner().findByInterface(MediatorHandler.class);
        builder.container().registerSingleton(FluentMediator.class,x ->
        {
            return new FluentMediatorImpl(mediators, x::find, builder.logger());
        });
    }
}
