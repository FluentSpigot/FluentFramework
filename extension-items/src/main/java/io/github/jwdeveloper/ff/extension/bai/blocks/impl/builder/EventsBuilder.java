package io.github.jwdeveloper.ff.extension.bai.blocks.impl.builder;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.builder.BlockBuilder;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.builder.BlockEventsBuilder;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.FluentBlockEvents;
import io.github.jwdeveloper.ff.extension.bai.items.impl.events.FluentItemUseEvent;

import java.util.function.Consumer;

public class EventsBuilder implements BlockEventsBuilder {

    private final FluentBlockEvents events = new FluentBlockEvents();

    @Override
    public EventsBuilder onPlace(Consumer<FluentItemUseEvent> eventConsumer) {

        events.getOnPlace().subscribe(eventConsumer);
        return this;
    }

    @Override
    public EventsBuilder onDestroy(Consumer<FluentItemUseEvent> eventConsumer) {
        events.getOnDestroy().subscribe(eventConsumer);
        return this;
    }

    @Override
    public EventsBuilder onDamage(Consumer<FluentItemUseEvent> eventConsumer) {
        events.getOnDamage().subscribe(eventConsumer);
        return this;
    }

    @Override
    public EventsBuilder onLeftClick(Consumer<FluentItemUseEvent> eventConsumer) {
        events.getOnLeftClick().subscribe(eventConsumer);
        return this;
    }

    @Override
    public EventsBuilder onRightClick(Consumer<FluentItemUseEvent> eventConsumer) {
        events.getOnRightClick().subscribe(eventConsumer);
        return this;
    }

    public FluentBlockEvents build() {
        return events;
    }
}
