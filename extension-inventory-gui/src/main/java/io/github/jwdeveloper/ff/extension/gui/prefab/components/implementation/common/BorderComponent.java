package io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common;

import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.api.events.GuiOpenEvent;
import io.github.jwdeveloper.ff.extension.gui.api.references.InventoryRef;
import io.github.jwdeveloper.ff.extension.gui.implementation.buttons.ButtonUI;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class BorderComponent implements InventoryComponent {
    private final List<ButtonUI> buttonUI;
    private final InventoryRef inventoryRef = new InventoryRef();
    private boolean isInit;
    private Material material;
    private boolean disabled;

    public BorderComponent() {
        buttonUI = new ArrayList<>();
        material = Material.GRAY_STAINED_GLASS_PANE;
        isInit = false;
    }


    @Override
    public void disable() {
        disabled = true;
    }

    @Override
    public void onInitialization(InventoryDecorator decorator, InventoryApi inventoryApi) {

        decorator.withInventoryReference(inventoryRef);
        decorator.withEvents(e -> e.onOpen(this::handleOnOpen));
    }

    private void handleOnOpen(GuiOpenEvent event) {

        if(disabled)
        {
            return;
        }

        if (isInit) {
            return;
        }
        var inventory = event.getInventory();
        var height = inventory.settings().getHeight();
        var width = inventory.settings().getWidth();
        for (var i = 0; i < height; i++) {
            for (var j = 0; j < width; j++) {
                if (i == 0 || i == height - 1 || j == 0 || j == width - 1) {
                    if (inventory.buttons().hasButton(i, j)) {
                        continue;
                    }
                    var button = new ButtonUI();
                    button.setPosition(i, j);
                    button.setMaterial(material);
                    button.setTitle(" ");
                    buttonUI.add(button);
                }
            }
        }
        inventory.buttons().addButtons(buttonUI);
        isInit = true;
    }

    public void setBorderMaterial(Material material) {
        this.material = material;
        if (!isInit) {
            return;
        }

        for (var button : buttonUI) {
            button.setMaterial(material);
        }
        inventoryRef.get().refresh();
    }
}
