package io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common;

import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.api.InventorySettings;
import io.github.jwdeveloper.ff.extension.gui.api.events.GuiOpenEvent;
import io.github.jwdeveloper.ff.extension.gui.api.references.ButtonRef;
import io.github.jwdeveloper.ff.extension.gui.api.references.InventoryRef;
import io.github.jwdeveloper.ff.extension.gui.OLD.events.ButtonClickEvent;
import io.github.jwdeveloper.ff.extension.gui.implementation.buttons.ButtonUI;
import org.bukkit.Material;


public class ExitButtonComponent implements InventoryComponent {
    private final ButtonRef buttonRef = new ButtonRef();
    private final InventoryRef inventoryRef = new InventoryRef();

    @Override
    public void onInitialization(InventoryDecorator decorator, InventoryApi inventoryApi) {

        decorator.withInventoryReference(inventoryRef);
        decorator.withButton(btn ->
        {
            btn.withReference(buttonRef);
            btn.withPosition(inventoryRef.get().settings().getHeight() - 1, InventorySettings.INVENTORY_WIDTH - 1);
            btn.withMaterial(Material.BARRIER);
            btn.withOnLeftClick(this::onClick);
        });
        decorator.withEvents(e -> e.onOpen(this::onOpen));
    }

    @Override
    public void disable() {
        inventoryRef.get().buttons().removeButton(buttonRef.get());
    }

    private void onOpen(GuiOpenEvent event) {
        var button = buttonRef.getOrThrow();
        var inv = event.getInventory();
        button.editStyleRendererOptions(styleRendererOptionsDecorator ->
        {
            if (inv.hasParent()) {
                styleRendererOptionsDecorator.withTitle("Go back");
            } else {
                styleRendererOptionsDecorator.withTitle("Close");
            }
        });

        inv.buttons().refresh(button);
    }

    private void onClick(ButtonClickEvent event) {
        if (event.getInventory().hasParent()) {
            event.getInventory().parent().open(event.getPlayer());
        } else {
            event.getInventory().close();
        }
    }

    public ButtonUI button()
    {
        return buttonRef.get();
    }

}
