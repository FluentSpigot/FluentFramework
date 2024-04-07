package io.github.jwdeveloper.ff.extension.bai.blocks.api.data;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.builder.BlockEventsBuilder;
import io.github.jwdeveloper.ff.extension.bai.items.impl.events.FluentItemUseEvent;
import lombok.Getter;

import java.util.function.Consumer;

@Getter
public class FluentBlockEvents {
    private final EventGroup<FluentItemUseEvent> onPlace = new EventGroup<>();

    private final EventGroup<FluentItemUseEvent> onDestroy = new EventGroup<>();
    private final EventGroup<FluentItemUseEvent> onDamage = new EventGroup<>();
    private final EventGroup<FluentItemUseEvent> onLeftClick = new EventGroup<>();

    private final EventGroup<FluentItemUseEvent> onRightClick = new EventGroup<>();

}
