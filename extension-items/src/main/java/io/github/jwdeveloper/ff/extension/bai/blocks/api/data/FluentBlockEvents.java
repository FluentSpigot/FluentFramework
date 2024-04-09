package io.github.jwdeveloper.ff.extension.bai.blocks.api.data;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.extension.bai.blocks.impl.events.*;
import lombok.Getter;

@Getter
public class FluentBlockEvents {
    private final EventGroup<FluentBlockPlacedEvent> onPlaced = new EventGroup<>();
    private final EventGroup<FluentBlockDestoryEvent> onDestroyed = new EventGroup<>();
    private final EventGroup<FluentBlockDamageEvent> onDamage = new EventGroup<>();
    private final EventGroup<FluentBlockClickEvent> onLeftClick = new EventGroup<>();
    private final EventGroup<FluentBlockClickEvent> onRightClick = new EventGroup<>();
    private final EventGroup<FluentBlockStateEvent> onStateUpdated = new EventGroup<>();

}
