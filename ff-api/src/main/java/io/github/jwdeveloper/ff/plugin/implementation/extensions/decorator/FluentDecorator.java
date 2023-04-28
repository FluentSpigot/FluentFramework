package io.github.jwdeveloper.ff.plugin.implementation.extensions.decorator;

import io.github.jwdeveloper.ff.core.injector.decorator.api.builder.DecoratorBuilder;
import io.github.jwdeveloper.ff.core.injector.decorator.implementation.DecoratorBuilderImpl;
import io.github.jwdeveloper.ff.core.injector.implementation.factory.InjectionInfoFactoryImpl;

import java.util.HashMap;

public class FluentDecorator
{
    public static DecoratorBuilder CreateDecorator()
    {
        var injectionInfoFactory = new InjectionInfoFactoryImpl();
        return new DecoratorBuilderImpl(injectionInfoFactory, new HashMap<>());
    }

}
