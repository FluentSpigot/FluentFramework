package io.github.jwdeveloper.ff.extension.gui.core.implementation;

import io.github.jwdeveloper.ff.extension.gui.core.api.FluentInventory;
import io.github.jwdeveloper.ff.extension.gui.core.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.core.api.managers.events.EventsManager;
import io.github.jwdeveloper.ff.extension.gui.core.implementation.button.ButtonUI;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskManager;
import io.github.jwdeveloper.ff.extension.gui.inventory.styles.renderer.ButtonStyleRenderer;
import io.github.jwdeveloper.ff.extension.gui.inventory.FluentButtonUIBuilder;
import io.github.jwdeveloper.ff.extension.gui.inventory.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.inventory.InventoryRef;
import io.github.jwdeveloper.ff.extension.gui.implementation.FluentInventoryImpl;
import org.bukkit.event.inventory.InventoryType;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class InventoryDecoratorImpl implements InventoryDecorator {

    private final FluentInventoryImpl inventory;
    private List<FluentButtonUIBuilder> buttons;
    private ButtonStyleRenderer styleRenderer;
    private List<InventoryComponent> components;

    public InventoryDecoratorImpl(FluentInventory inventory) {
        this.inventory = (FluentInventoryImpl)inventory;
        buttons = new ArrayList<>();
        components= new LinkedList<>();
    }


    @Override
    public InventoryComponent withComponent(InventoryComponent inventoryPlugin) {
        components.add(inventoryPlugin);
        return inventoryPlugin;
    }

    @Override
    public InventoryDecorator withButtonRenderer(ButtonStyleRenderer renderer) {
        this.styleRenderer = styleRenderer;
        return this;
    }

    @Override
    public InventoryDecorator withChild(FluentInventory inventory) {
        inventory.children().addChild(inventory);
        return this;
    }

    @Override
    public InventoryDecorator withButton(ButtonUI buttonUI) {
        return null;
    }


    public InventoryDecorator withButtonRef(ButtonUI buttonUI, Consumer<FluentButtonUIBuilder> manager) {
      //  var builder = new FluentButtonUIBuilder(buttonUI);
      //  manager.accept(builder);
      //  buttons.add(builder);        return this;
        return null;
    }


    @Override
    public InventoryDecorator withButton(Consumer<FluentButtonUIBuilder> manager) {

        var builder = new FluentButtonUIBuilder();
        manager.accept(builder);
        buttons.add(builder);
        return this;
    }

    @Override
    public InventoryDecorator withEvents(Consumer<EventsManager> manager) {
        manager.accept(inventory.events());
        return this;
    }

    @Override
    public InventoryDecorator withPermissions(String... permissions) {
        inventory.permissions().addPermissions(permissions);
        return this;
    }


    @Override
    public InventoryDecorator withTitle(String title) {
       // inventory.getInventorySettings().setTitle(title);
        return this;
    }

    @Override
    public InventoryDecorator withType(InventoryType type) {
       // inventory.getInventorySettings().setInventoryType(type);
        return this;
    }

    @Override
    public InventoryDecorator withHeight(int height) {
     //   inventory.getInventorySettings().setHeight(height);
        return this;
    }

    @Override
    public InventoryDecorator withTasks(Consumer<FluentTaskManager> tasks) {
        return null;
    }

    @Override
    public InventoryDecorator withInventoryReference(InventoryRef inventoryRef)
    {
        inventoryRef.set(inventory);
        return this;
    }


    public void apply()
    {
        for(var component : components)
        {
            component.onInitialize(this);
        }
        for (var button : buttons) {
            var btn = button.build(styleRenderer);
           // inventory.buttons().addButton(btn);
        }
    }
}
