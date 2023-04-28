package io.github.jwdeveloper.ff.core.injector.decorator.api.builder;

import io.github.jwdeveloper.ff.core.injector.decorator.api.Decorator;

public interface DecoratorBuilder {
    <T> DecoratorBuilder decorate(Class<T> _interface, Class<? extends T> implementation);
    Decorator build() throws Exception;
}
