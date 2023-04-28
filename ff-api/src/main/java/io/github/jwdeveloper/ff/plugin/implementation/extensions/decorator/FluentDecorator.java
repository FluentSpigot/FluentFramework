package io.github.jwdeveloper.ff.api.implementation.extensions.decorator;

import io.github.jwdeveloper.spigot.fluent.core.injector.decorator.api.builder.DecoratorBuilder;
import io.github.jwdeveloper.spigot.fluent.core.injector.decorator.implementation.DecoratorBuilderImpl;
import io.github.jwdeveloper.spigot.fluent.core.injector.implementation.factory.InjectionInfoFactoryImpl;

import java.util.HashMap;

public class FluentDecorator
{
    public static DecoratorBuilder CreateDecorator()
    {
        var injectionInfoFactory = new InjectionInfoFactoryImpl();
        return new DecoratorBuilderImpl(injectionInfoFactory, new HashMap<>());
    }

}
