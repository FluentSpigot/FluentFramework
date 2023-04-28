package io.github.jwdeveloper.ff.plugin.api;


import io.github.jwdeveloper.ff.core.injector.api.containers.builders.ContainerBuilder;


public interface FluentApiContainerBuilder extends ContainerBuilder<FluentApiContainerBuilder> {
    <T> FluentApiContainerBuilder registerDecorator(Class<T> _interface, Class<? extends T> _implementaition);
}
