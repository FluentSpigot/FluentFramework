package io.github.jwdeveloper.ff.plugin.implementation.extensions.container.player_scope.api;

import io.github.jwdeveloper.ff.core.injector.api.containers.Container;
import io.github.jwdeveloper.ff.core.injector.api.containers.builders.ContainerBuilder;

public interface PlayerContainerBuilder extends ContainerBuilder<PlayerContainerBuilder> {

    public Container build();

}
