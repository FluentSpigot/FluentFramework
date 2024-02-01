package io.github.jwdeveloper.ff.extension.items.api.decorator;

import io.github.jwdeveloper.ff.extension.items.impl.events.FluentItemCreateEvent;

public interface FluentItemDecorator
{
    public void onDecorating(FluentItemCreateEvent event);
}
