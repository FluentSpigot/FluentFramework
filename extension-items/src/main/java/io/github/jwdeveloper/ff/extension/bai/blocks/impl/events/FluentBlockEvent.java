package io.github.jwdeveloper.ff.extension.bai.blocks.impl.events;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlockInstance;
import lombok.Getter;

@Getter
public class FluentBlockEvent
{
    private final FluentBlockInstance blockInstance;

    public FluentBlockEvent(FluentBlockInstance fluentBlockInstance) {
        this.blockInstance = fluentBlockInstance;
    }
}
