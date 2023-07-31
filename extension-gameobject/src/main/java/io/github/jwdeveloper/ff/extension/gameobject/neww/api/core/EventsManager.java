package io.github.jwdeveloper.ff.extension.gameobject.neww.api.core;

import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;

import java.util.function.Consumer;

public interface EventsManager {
    EventsManager onInitialization(Consumer<FluentApiSpigot> event);

    EventsManager onEnable(Consumer<Void> event);

    EventsManager onPhisicAsync(Consumer<Void> event);

    EventsManager onUpdateAsync(Consumer<Double> event);

    EventsManager onBeforeRenderAsync(Consumer<Void> event);

    EventsManager onRender(Consumer<Void> event);

    EventsManager onDestroy(Consumer<Void> event);
}
