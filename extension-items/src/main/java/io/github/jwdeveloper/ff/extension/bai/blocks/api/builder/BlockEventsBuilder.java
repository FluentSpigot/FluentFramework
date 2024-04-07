package io.github.jwdeveloper.ff.extension.bai.blocks.api.builder;

import io.github.jwdeveloper.ff.extension.bai.items.impl.events.FluentItemUseEvent;

import java.util.function.Consumer;

public interface BlockEventsBuilder
{
    BlockEventsBuilder onPlace(Consumer<FluentItemUseEvent> eventConsumer);

    BlockEventsBuilder onDestroy(Consumer<FluentItemUseEvent> eventConsumer);

    BlockEventsBuilder onDamage(Consumer<FluentItemUseEvent> eventConsumer);

    BlockEventsBuilder onLeftClick(Consumer<FluentItemUseEvent> eventConsumer);

    BlockEventsBuilder onRightClick(Consumer<FluentItemUseEvent> eventConsumer);
}
