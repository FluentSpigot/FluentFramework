package io.github.jwdeveloper.ff.extension.commands.implementation;

import io.github.jwdeveloper.ff.extension.commands.api.annotations.Argument;
import io.github.jwdeveloper.ff.extension.commands.api.annotations.Command;
import io.github.jwdeveloper.ff.extension.commands.api.annotations.CommandChild;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.spigot.commands.FluentCommand;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.SimpleCommandBuilder;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class FluentCommandFactory {
    public List<FluentCommandInvoker> create(Class<?> clazz, SimpleCommandBuilder builder) {
        var invokers = handleChildren(clazz,builder);
        handleCommandAnnotation(clazz, builder);
        handleArgumentAnnotation(clazz, builder);

        var invoker = new FluentCommandInvoker(clazz);
        handleMethods(clazz, builder, invoker);

        invokers.add(invoker);
        return invokers;
    }


    private List<FluentCommandInvoker> handleChildren(Class<?> clazz, SimpleCommandBuilder builder) {
        var list = new ArrayList<FluentCommandInvoker>();
        var children = clazz.getDeclaredAnnotationsByType(CommandChild.class);
        for (var child : children) {
            var childBuilder = FluentCommand.create(child.childClass().getSimpleName());
            var invokers = create(child.childClass(), childBuilder);
            builder.subCommandsConfig(config ->
            {
                config.addSubCommand(childBuilder);
            });
            list.addAll(invokers);
        }
        return list;
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
                handleCommandAnnotation(method, builder1);
                handleArgumentAnnotation(method, builder1);
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

    private void handleCommandAnnotation(AnnotatedElement element, SimpleCommandBuilder builder) {
        if (!element.isAnnotationPresent(Command.class)) {
            return;
        }
        var annotation = element.getAnnotation(Command.class);
        builder.propertiesConfig(propertiesConfig ->
        {
            if (StringUtils.isNotNullOrEmpty(annotation.name())) {
                propertiesConfig.setName(annotation.name());
            }
            propertiesConfig.setLabel(annotation.label());
            propertiesConfig.setHideFromTabDisplay(annotation.hideFromDisplay());
            propertiesConfig.setDescription(annotation.description());
            propertiesConfig.setAccess(annotation.access());
            propertiesConfig.setShortDescription(annotation.shortDescription());
            propertiesConfig.setLabel(annotation.label());
            propertiesConfig.addPermissions(annotation.permissions());
        });
    }

    private void handleArgumentAnnotation(AnnotatedElement element, SimpleCommandBuilder builder) {
        var annotations = element.getDeclaredAnnotationsByType(Argument.class);
        for (var argument : annotations) {
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
