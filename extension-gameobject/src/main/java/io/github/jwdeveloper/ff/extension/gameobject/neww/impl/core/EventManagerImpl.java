package io.github.jwdeveloper.ff.extension.gameobject.neww.impl.core;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.extension.gameobject.neww.api.core.EventsManager;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;

import java.util.function.Consumer;

public class EventManagerImpl implements EventsManager {

    public final EventGroup<Void> enableEvent = new EventGroup<>();

    public final EventGroup<FluentApiSpigot> initializationEvent = new EventGroup<>();

    public final EventGroup<Void> destroyEvent = new EventGroup<>();

    public final EventGroup<Double> updateAsyncEvent = new EventGroup<>();

    public final EventGroup<Void> phisicAsyncEvent = new EventGroup<>();

    public final EventGroup<Void> renderEvent = new EventGroup<>();

    public final EventGroup<Void> renderAsyncEvent = new EventGroup<>();

    @Override
    public EventsManager onInitialization(Consumer<FluentApiSpigot> event) {
        initializationEvent.subscribe(event);
        return this;
    }

    @Override
    public EventsManager onUpdateAsync(Consumer<Double> event) {
        updateAsyncEvent.subscribe(event);
        return this;
    }

    @Override
    public EventsManager onEnable(Consumer<Void> event) {
        enableEvent.subscribe(event);
        return this;
    }

    @Override
    public EventsManager onPhisicAsync(Consumer<Void> event) {
        phisicAsyncEvent.subscribe(event);
        return this;
    }

    @Override
    public EventsManager onBeforeRenderAsync(Consumer<Void> event) {
        renderAsyncEvent.subscribe(event);
        return this;
    }

    @Override
    public EventsManager onRender(Consumer<Void> event) {
        renderEvent.subscribe(event);
        return this;
    }

    @Override
    public EventsManager onDestroy(Consumer<Void> event) {
        destroyEvent.subscribe(event);
        return this;
    }


}
