package io.github.jwdeveloper.ff.extension.gui.prefab.widgets.api;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


public class WidgetOptions {
    @Getter
    private final UUID id = UUID.randomUUID();

    @Getter
    @Setter
    private boolean canRender = true;
}
