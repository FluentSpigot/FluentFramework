package io.github.jwdeveloper.ff.extension.gui.core.api;


import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskManager;
import io.github.jwdeveloper.ff.extension.gui.core.api.managers.events.EventsManager;
import io.github.jwdeveloper.ff.extension.gui.implementation.buttons.ButtonUI;
import io.github.jwdeveloper.ff.extension.gui.inventory.FluentButtonUIBuilder;
import io.github.jwdeveloper.ff.extension.gui.inventory.styles.renderer.ButtonStyleRenderer;
import io.github.jwdeveloper.ff.extension.gui.inventory.InventoryRef;
import org.bukkit.event.inventory.InventoryType;


import java.util.function.Consumer;

public interface InventoryDecorator
{
    InventoryComponent withComponent(Class<? extends InventoryComponent> component);
    InventoryComponent withComponent(InventoryComponent component);
    InventoryDecorator withButtonRenderer(ButtonStyleRenderer renderer);
    InventoryDecorator withChild(FluentInventory inventory);
    InventoryDecorator withButton(ButtonUI buttonUI);
    InventoryDecorator withButton(Consumer<FluentButtonUIBuilder> manager);
    InventoryDecorator withEvents(Consumer<EventsManager> manager);
    InventoryDecorator withPermissions(String ... permissions);
    InventoryDecorator withTitle(String title);
    InventoryDecorator withType(InventoryType type);
    InventoryDecorator withHeight(int height);
    InventoryDecorator withTasks(Consumer<FluentTaskManager> tasks);
    InventoryDecorator withInventoryReference(InventoryRef inventoryRef);
}
