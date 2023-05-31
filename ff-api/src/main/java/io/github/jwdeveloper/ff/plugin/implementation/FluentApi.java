package io.github.jwdeveloper.ff.plugin.implementation;


import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.CommandBuilder;
import io.github.jwdeveloper.ff.core.spigot.events.api.FluentEventManager;
import io.github.jwdeveloper.ff.core.spigot.messages.FluentMessages;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskManager;
import io.github.jwdeveloper.ff.plugin.api.config.FluentConfig;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.container.FluentInjection;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.mediator.FluentMediator;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.api.FluentPermission;

public class FluentApi {
    private static FluentApiSpigot fluentApiSpigot;
    public static void setFluentApiSpigot(FluentApiSpigot fluentApiSpigot) {
        FluentApi.fluentApiSpigot = fluentApiSpigot;
    }

    public static FluentApiSpigot getFluentApiSpigot()
    {
        if (fluentApiSpigot == null) {
            FluentLogger.LOGGER.error("FluentAPI has not been initialized initialize");
            FluentLogger.LOGGER.error("Remember not to use FluentApi class inside OnConfiguration(FluentApiSpigotBuilder builder) method");
            return null;
        }
        return fluentApiSpigot;
    }


    public static FluentPermission permission() {
        return getFluentApiSpigot().permission();
    }

    public static FluentInjection container() {
        return getFluentApiSpigot().container();
    }

    public static FluentMediator mediator() {
        return getFluentApiSpigot().mediator();
    }

    public static FluentConfig config() {
        return getFluentApiSpigot().config();
    }
    public static FluentEventManager events() {
        return getFluentApiSpigot().events();
    }

    public static FluentMessages messages(){
        return getFluentApiSpigot().messages();
    }
    public static FluentTaskManager tasks() {
        return getFluentApiSpigot().tasks();
    }

    public static CommandBuilder createCommand(String commandName) {
        return getFluentApiSpigot().createCommand(commandName);
    }

    public static boolean isTestEnvironment()
    {
        return fluentApiSpigot.plugin().getClass().getSimpleName().contains("MockPlugin");
    }
    /*public static FluentParticlebuilder particles() {
        return getFluentApiSpigot().particles();
    }*/
}
