package io.github.jwdeveloper.ff.extension.gui.prefab.widgets.api;

import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.buttons.ButtonBuilder;

public interface ButtonWidget {
    void onCreate(ButtonBuilder builder, InventoryApi inventoryApi);
}
