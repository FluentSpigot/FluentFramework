package io.github.jwdeveloper.ff.core.spigot.events.implementation;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.logger.plugin.PluginLogger;
import io.github.jwdeveloper.ff.core.spigot.events.api.FluentEventManager;
import org.bukkit.Bukkit;
import org.bukkit.event.*;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SimpleEventManager implements Listener, FluentEventManager {
    private final List<SimpleEvent<PluginDisableEvent>> onPluginDisableEvents;
    private final List<SimpleEvent<PluginEnableEvent>> onPluginEnableEvents;
    private final List<SimpleEvent<?>> events;
    private final Plugin plugin;
    private final PluginLogger logger;

    public SimpleEventManager(Plugin plugin, PluginLogger logger) {
        this.plugin = plugin;
        this.logger = logger;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
        onPluginDisableEvents = new ArrayList<>();
        onPluginEnableEvents = new ArrayList<>();
        events = new ArrayList<>();
    }

    public void unregister() {
        PluginEnableEvent.getHandlerList().unregister(this);
        PluginDisableEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    protected final void onPluginStart(PluginEnableEvent pluginEnableEvent) {
        if (pluginEnableEvent.getPlugin() == plugin) {
            for (var fluentEvent : onPluginEnableEvents) {
                fluentEvent.invoke(pluginEnableEvent);
            }
        }
    }

    @EventHandler
    protected final void onPluginStopEvent(PluginDisableEvent pluginDisableEvent) {
        if (pluginDisableEvent.getPlugin() == plugin) {
            for (var fluentEvent : onPluginDisableEvents) {
                fluentEvent.invoke(pluginDisableEvent);
            }
        }
    }


    public List<SimpleEvent<?>> getEvents() {
        return events;
    }

    public <T extends Event> SimpleEvent<T> onEvent(Class<T> eventType, Consumer<T> action) {
        var fluentEvent = new SimpleEvent<T>(action, logger);

        if (eventType.equals(PluginDisableEvent.class)) {
            onPluginDisableEvents.add((SimpleEvent<PluginDisableEvent>) fluentEvent);
            return fluentEvent;
        }
        if (eventType.equals(PluginEnableEvent.class)) {
            onPluginEnableEvents.add((SimpleEvent<PluginEnableEvent>) fluentEvent);
            return fluentEvent;
        }

        var virtualListener = new Listener() {
        };
        Bukkit.getServer().getPluginManager().registerEvents(virtualListener, plugin);
        Bukkit.getPluginManager().registerEvent(eventType, virtualListener, EventPriority.NORMAL,
                (listener, event) ->
                {
                    if (!fluentEvent.isRegister()) {
                        return;
                    }
                    if (!event.getClass().getSimpleName().equalsIgnoreCase(eventType.getSimpleName())) {
                        return;
                    }
                    fluentEvent.invoke((T) event);
                }, plugin);


        fluentEvent.onUnregister(tSimpleEvent ->
        {
            try {
                // var handlers = (HandlerList) ObjectUtility.getStaticFieldValue(eventType, "handlers");
                //   var handlersMethod = eventType.getDeclaredField("handlers");
                //  handlersMethod.setAccessible(true);
                //  var handlers = (HandlerList) handlersMethod.get(null);
                ///handlers.unregister(virtualListener);
                events.remove(fluentEvent);
                HandlerList.unregisterAll(virtualListener);
            } catch (Exception e) {
                FluentLogger.LOGGER.error("Unable to unregister listener for event: " + eventType.getSimpleName(), e);
            }
        });
        events.add(fluentEvent);
        return fluentEvent;
    }
}
