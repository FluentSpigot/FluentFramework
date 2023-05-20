package io.github.jwdeveloper.ff.plugin.implementation;

import io.github.jwdeveloper.ff.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.ff.core.injector.api.containers.FluentContainer;
import io.github.jwdeveloper.ff.core.injector.decorator.api.builder.DecoratorBuilder;
import io.github.jwdeveloper.ff.core.injector.implementation.containers.FluentContainerImpl;
import io.github.jwdeveloper.ff.core.injector.implementation.containers.builder.ContainerBuilderImpl;
import io.github.jwdeveloper.ff.core.injector.implementation.events.EventHandlerImpl;
import io.github.jwdeveloper.ff.core.injector.implementation.factory.InjectionInfoFactoryImpl;
import io.github.jwdeveloper.ff.core.injector.implementation.provider.InstanceProviderImpl;
import io.github.jwdeveloper.ff.core.injector.implementation.search.SearchAgentImpl;
import io.github.jwdeveloper.ff.plugin.api.FluentApiContainerBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtensionsManager;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.decorator.FluentDecorator;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.decorator.FluentDecoratorExtention;


public class FluentApiContainerBuilderImpl extends ContainerBuilderImpl<FluentApiContainerBuilder> implements FluentApiContainerBuilder {
    private final FluentApiExtensionsManager extentionsManager;
    private final DecoratorBuilder decoratorBuilder;
    private final SimpleLogger logger;

    public FluentApiContainerBuilderImpl(FluentApiExtensionsManager eventBuilder,
                                         SimpleLogger logger) {
        this.extentionsManager = eventBuilder;
        this.logger = logger;
        this.decoratorBuilder = FluentDecorator.CreateDecorator();
    }





    @Override
    public <T> FluentApiContainerBuilder registerDecorator(Class<T> _interface, Class<? extends T> _implementaition) {
        decoratorBuilder.decorate(_interface, _implementaition);
        return this;
    }


    public FluentContainer build() throws Exception {
        extentionsManager.register(new FluentDecoratorExtention(decoratorBuilder));

        var eventHandler = new EventHandlerImpl(config.getEvents());
        var instanceProvider = new InstanceProviderImpl();
        var injectionInfoFactory = new InjectionInfoFactoryImpl();
        var registrations = config.getRegistrations();
        var searchAgent = new SearchAgentImpl();

        return new FluentContainerImpl(
                searchAgent,
                instanceProvider,
                eventHandler,
                logger,
                injectionInfoFactory,
                registrations);
    }
}