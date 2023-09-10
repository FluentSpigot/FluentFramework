package io.github.jwdeveloper.ff.extension.commands.implementation;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.commands.FluentCommand;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.ArgumentBuilder;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.SimpleCommandBuilder;
import io.github.jwdeveloper.ff.extension.commands.api.annotations.Argument;
import io.github.jwdeveloper.ff.extension.commands.api.annotations.Command;
import io.github.jwdeveloper.ff.extension.commands.api.annotations.CommandChild;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FluentCommandFactory {
    public List<FluentCommandInvoker> create(Class<?> clazz, SimpleCommandBuilder builder) {
        var invokers = handleChildren(clazz, builder);
        handleCommandAnnotation(clazz, builder);
        var invoker = new FluentCommandInvoker(clazz);
        handleArgumentAnnotation(clazz, clazz, builder, invoker);
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
            handleMethod(clazz, method, builder, invoker);
        }
    }

    private void handleMethod(Class<?> clazz, Method method, SimpleCommandBuilder builder, FluentCommandInvoker invoker) {
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
                handleArgumentAnnotation(clazz, method, builder1, invoker);
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

    private void handleArgumentAnnotation(Class<?> clazz, AnnotatedElement element, SimpleCommandBuilder builder, FluentCommandInvoker invoker) {
        var annotations = element.getDeclaredAnnotationsByType(Argument.class);
        for (var argument : annotations) {
            builder.argumentsConfig(propertiesConfig ->
            {
                propertiesConfig.addArgument(argument.name(), argumentBuilder ->
                {
                    argumentBuilder.setType(argument.argumentType());
                    argumentBuilder.setDescription(argument.description());
                    argumentBuilder.setArgumentDisplay(argument.displayMode());
                    if (StringUtils.isNotNullOrEmpty(argument.onTabComplete())) {
                        addTabComplete(clazz, argumentBuilder, argument.onTabComplete(), invoker);
                    }
                });
            });
        }
    }

    private void addTabComplete(Class<?> clazz, ArgumentBuilder builder, String methodName, FluentCommandInvoker invoker) {


        var optional = Arrays.stream(clazz.getDeclaredMethods()).filter(e -> e.getName().equals(methodName)).findFirst();
        if (optional.isEmpty()) {
            FluentLogger.LOGGER.error("Not found OnComplete method: ", methodName, "in class", clazz.getSimpleName());
            return;
        }
        var method = optional.get();
        if (!method.getReturnType().equals(List.class)) {
            FluentLogger.LOGGER.error("OnComplete method: ", methodName, "in class", clazz.getSimpleName(), " should return List<String>");
            return;
        }
        if (method.getParameterCount() > 0) {
            FluentLogger.LOGGER.error("OnComplete method: ", methodName, "in class", clazz.getSimpleName(), " should has 0 parameters but was: ",method.getParameterCount());
            return;
        }
        builder.setTabComplete(() ->   invoker.invokeOnComplete(method));

    }

}
