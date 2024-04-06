package io.github.jwdeveloper.ff.plugin.implementation.extensions.container.player_scope.implementation;

import io.github.jwdeveloper.dependance.injector.api.containers.Container;
import io.github.jwdeveloper.dependance.injector.implementation.containers.DefaultContainer;
import io.github.jwdeveloper.dependance.injector.implementation.containers.builder.ContainerBuilderImpl;
import io.github.jwdeveloper.dependance.injector.implementation.events.EventHandlerImpl;
import io.github.jwdeveloper.dependance.injector.implementation.factory.InjectionInfoFactoryImpl;
import io.github.jwdeveloper.ff.core.logger.plugin.PluginLogger;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.container.player_scope.api.PlayerContainerBuilder;


public class PlayerContainerBuilderImpl  {
    private Container parentContainer;
    private PluginLogger logger;

    public PlayerContainerBuilderImpl(PluginLogger logger)
    {
        this.logger = logger;
    }

    public PlayerContainerBuilder setParentContainer(Container container) {
        parentContainer = container;
        // return this;
        return null;
    }

 /*   @Override
    public Container build() {
        var eventHandler = new EventHandlerImpl(config.getEvents());
        var instanceProvider = new PlayerContextInstanceProvider(parentContainer);
        var injectionInfoFactory = new InjectionInfoFactoryImpl();
        var searchAgent = new SearchAgentImpl();

        return new DefaultContainer(
                searchAgent,
                instanceProvider,
                eventHandler,
                logger,
                injectionInfoFactory,
                config.getRegistrations());
    }*/
}
