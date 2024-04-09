package io.github.jwdeveloper.ff.extension.bai.blocks.impl.builder;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.builder.BlockEventsBuilder;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.FluentBlockEvents;
import io.github.jwdeveloper.ff.extension.bai.blocks.impl.events.*;

import java.util.function.Consumer;

public class EventsBuilder implements BlockEventsBuilder {

    private final FluentBlockEvents events = new FluentBlockEvents();

    @Override
    public EventsBuilder onPlaced(Consumer<FluentBlockPlacedEvent> eventConsumer) {

        events.getOnPlaced().subscribe(eventConsumer);
        return this;
    }

    @Override
    public EventsBuilder onDestroy(Consumer<FluentBlockDestoryEvent> eventConsumer) {
        events.getOnDestroyed().subscribe(eventConsumer);
        return this;
    }

    @Override
    public EventsBuilder onDamage(Consumer<FluentBlockDamageEvent> eventConsumer) {
        events.getOnDamage().subscribe(eventConsumer);
        return this;
    }

    @Override
    public EventsBuilder onLeftClick(Consumer<FluentBlockClickEvent> eventConsumer) {
        events.getOnLeftClick().subscribe(eventConsumer);
        return this;
    }

    @Override
    public EventsBuilder onRightClick(Consumer<FluentBlockClickEvent> eventConsumer) {
        events.getOnRightClick().subscribe(eventConsumer);
        return this;
    }

    @Override
    public BlockEventsBuilder onState(Consumer<FluentBlockStateEvent> eventConsumer) {
        events.getOnStateUpdated().subscribe(eventConsumer);
        return this;
    }

    public FluentBlockEvents build() {
        return events;
    }
}
