package io.github.jwdeveloper.ff.extension.bai.blocks.impl.events;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlockInstance;
import lombok.Data;

@Data
public class FluentBlockPlacedEvent {

    private final FluentBlockInstance blockInstance;
}
