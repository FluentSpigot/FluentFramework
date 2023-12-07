package io.github.jwdeveloper.ff.extension.gui.api;


import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.extension.gui.api.buttons.ButtonBuilder;
import io.github.jwdeveloper.ff.extension.gui.api.managers.EventsManager;
import io.github.jwdeveloper.ff.extension.gui.api.references.InventoryRef;
import io.github.jwdeveloper.ff.extension.gui.api.styles.StyleRenderer;
import io.github.jwdeveloper.ff.extension.gui.implementation.buttons.ButtonUI;
import org.bukkit.event.inventory.InventoryType;

import java.util.function.Consumer;

public interface InventoryDecorator {
    InventoryComponent withComponent(Class<? extends InventoryComponent> component);

    <T extends InventoryComponent> T withComponent(T component);

    InventoryDecorator withStyleRenderer(StyleRenderer renderer);

    InventoryDecorator withParent(FluentInventory inventory);

    InventoryDecorator withButton(ButtonUI buttonUI);

     InventoryDecorator withButton(ButtonBuilder builder);

    InventoryDecorator withButton(Consumer<ButtonBuilder> builder);

    InventoryDecorator withEvents(Consumer<EventsManager> manager);

    InventoryDecorator withPermissions(String... permissions);

    InventoryDecorator withTitle(String title);

    InventoryDecorator withCancelClick(boolean isClickCanceled);

    InventoryDecorator withType(InventoryType type);

    InventoryDecorator withHeight(int height);

    InventoryDecorator withInventoryReference(InventoryRef inventoryRef);
}
