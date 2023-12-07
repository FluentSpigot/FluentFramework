package io.github.jwdeveloper.ff.core.spigot.events.api;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.SimpleEvent;
import org.bukkit.event.Event;

import java.util.List;
import java.util.function.Consumer;

public interface FluentEventManager {

    <T extends Event> SimpleEvent<T> onEvent(Class<T> eventType, Consumer<T> action);
    List<SimpleEvent<?>> getEvents();
}
