package io.github.jwdeveloper.ff.extension.bai.items.api.decorator;

import io.github.jwdeveloper.ff.extension.bai.items.impl.events.FluentItemCreateEvent;

public interface FluentItemDecorator
{
    public void onDecorating(FluentItemCreateEvent event);
}
