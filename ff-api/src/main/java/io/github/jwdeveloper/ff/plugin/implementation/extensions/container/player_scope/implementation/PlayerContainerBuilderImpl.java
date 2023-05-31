package io.github.jwdeveloper.ff.plugin.implementation.extensions.container.player_scope.implementation;

import io.github.jwdeveloper.ff.core.common.logger.PluginLogger;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.container.player_scope.api.PlayerContainerBuilder;
import io.github.jwdeveloper.ff.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.ff.core.injector.api.containers.Container;
import io.github.jwdeveloper.ff.core.injector.implementation.containers.DefaultContainer;
import io.github.jwdeveloper.ff.core.injector.implementation.containers.builder.ContainerBuilderImpl;
import io.github.jwdeveloper.ff.core.injector.implementation.events.EventHandlerImpl;
import io.github.jwdeveloper.ff.core.injector.implementation.factory.InjectionInfoFactoryImpl;
import io.github.jwdeveloper.ff.core.injector.implementation.search.SearchAgentImpl;

public class PlayerContainerBuilderImpl extends ContainerBuilderImpl<PlayerContainerBuilder> implements PlayerContainerBuilder {
    private Container parentContainer;
    private PluginLogger logger;

    public PlayerContainerBuilderImpl(PluginLogger logger)
    {
        this.logger = logger;
    }

    public PlayerContainerBuilder setParentContainer(Container container) {
        parentContainer = container;
        return this;
    }

    @Override
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
    }
}
