package io.github.jwdeveloper.ff.extension.bai.blocks.api.builder;

import io.github.jwdeveloper.ff.extension.bai.blocks.impl.events.*;

import java.util.function.Consumer;

public interface BlockEventsBuilder
{
    BlockEventsBuilder onPlaced(Consumer<FluentBlockPlacedEvent> eventConsumer);

    BlockEventsBuilder onDestroy(Consumer<FluentBlockDestoryEvent> eventConsumer);

    BlockEventsBuilder onDamage(Consumer<FluentBlockDamageEvent> eventConsumer);

    BlockEventsBuilder onLeftClick(Consumer<FluentBlockClickEvent> eventConsumer);

    BlockEventsBuilder onRightClick(Consumer<FluentBlockClickEvent> eventConsumer);

    BlockEventsBuilder onState(Consumer<FluentBlockStateEvent> eventConsumer);
}
