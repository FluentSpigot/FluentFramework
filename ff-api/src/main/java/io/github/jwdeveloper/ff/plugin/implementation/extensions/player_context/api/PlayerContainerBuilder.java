package io.github.jwdeveloper.ff.api.implementation.extensions.player_context.api;

import io.github.jwdeveloper.spigot.fluent.core.injector.api.containers.Container;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.containers.builders.ContainerBuilder;

public interface PlayerContainerBuilder extends ContainerBuilder<PlayerContainerBuilder> {

    public Container build();

}
