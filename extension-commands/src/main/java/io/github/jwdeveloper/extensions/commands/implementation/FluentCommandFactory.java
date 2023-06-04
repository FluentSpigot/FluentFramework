package io.github.jwdeveloper.extensions.commands.implementation;

import io.github.jwdeveloper.extensions.commands.api.annotations.Argument;
import io.github.jwdeveloper.extensions.commands.api.annotations.Command;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.SimpleCommandBuilder;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

public class FluentCommandFactory {
    public FluentCommandInvoker create(Class<?> clazz, SimpleCommandBuilder builder) {
        var invoker = new FluentCommandInvoker(clazz);

        handleHeader(clazz, builder);
        handleArguments(clazz, builder);
        handleMethods(clazz, builder, invoker);
        return invoker;
    }

    private void handleMethods(Class<?> clazz, SimpleCommandBuilder builder, FluentCommandInvoker invoker) {
        for (var method : clazz.getDeclaredMethods()) {
            handleMethod(method, builder, invoker);
        }
    }

    private void handleMethod(Method method, SimpleCommandBuilder builder, FluentCommandInvoker invoker) {
        if (!method.isAnnotationPresent(Command.class)) {
            return;
        }
        var annotation = method.getAnnotation(Command.class);
        if (StringUtils.isNullOrEmpty(annotation.name())) {
            handleInvoke(method, builder, invoker);
            return;
        }

        builder.subCommandsConfig(eventConfig ->
        {
            eventConfig.addSubCommand(annotation.name(), builder1 ->
            {
                handleHeader(method, builder1);
                handleArguments(method, builder1);
                handleInvoke(method, builder1, invoker);
            });
        });
    }

    private void handleInvoke(Method method, SimpleCommandBuilder builder, FluentCommandInvoker invoker) {
        builder.eventsConfig(eventConfig ->
        {
            eventConfig.onExecute(commandEvent ->
            {
                invoker.invoke(commandEvent, method);
            });
        });
    }

    private void handleHeader(AnnotatedElement element, SimpleCommandBuilder builder) {
        if (!element.isAnnotationPresent(Command.class)) {
            return;
        }
        var annotation = element.getAnnotation(Command.class);
        builder.propertiesConfig(propertiesConfig ->
        {
            if(StringUtils.isNotNullOrEmpty(annotation.name()))
            {
                propertiesConfig.setName(annotation.name());
            }

            propertiesConfig.setDescription(annotation.description());
            propertiesConfig.setAccess(annotation.access());
            propertiesConfig.setShortDescription(annotation.shortDescription());
            propertiesConfig.setLabel(annotation.label());
        });
    }

    private void handleArguments(AnnotatedElement element, SimpleCommandBuilder builder) {
        var annotations = element.getDeclaredAnnotationsByType(Argument.class);

        for(var i = 0;i< annotations.length;i++)
        {
            var argument = annotations[i];
            builder.argumentsConfig(propertiesConfig ->
            {
                propertiesConfig.addArgument(argument.name(), argumentBuilder ->
                {
                    argumentBuilder.setType(argument.argumentType());
                    argumentBuilder.setDescription(argument.description());
                });
            });
        }
    }

}
