package io.github.jwdeveloper.ff.core.spigot.events;

import io.github.jwdeveloper.ff.core.common.java.JavaUtils;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.events.api.FluentEventManager;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.SimpleEvent;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.SimpleEventManager;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class FluentEvent
{
    private static SimpleEventManager manager;



    public static <T extends Event> SimpleEvent<T> onEvent(Class<T> eventClass, Consumer<T> action)
    {
        return getManager().onEvent(eventClass, action);
    }
    public static FluentEventManager getManager() {
        if (manager == null) {
            throw new RuntimeException(FluentEventManager.class.getSimpleName()+" are disabled, use to enable it "+ FluentEventManager.class.getSimpleName()+".enable(plugin)");
        }
        return manager;
    }

    public static FluentEventManager enable(Plugin plugin)
    {
        JavaUtils.ifNotNull(manager, SimpleEventManager::unregister);
        manager = new SimpleEventManager(plugin, FluentLogger.LOGGER);
        return manager;
    }
}
