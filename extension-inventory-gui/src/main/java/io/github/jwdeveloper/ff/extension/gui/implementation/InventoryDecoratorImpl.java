package io.github.jwdeveloper.ff.extension.gui.implementation;

import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskManager;
import io.github.jwdeveloper.ff.extension.gui.api.FluentInventory;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.api.buttons.ButtonBuilder;
import io.github.jwdeveloper.ff.extension.gui.api.managers.EventsManager;
import io.github.jwdeveloper.ff.extension.gui.api.references.InventoryRef;
import io.github.jwdeveloper.ff.extension.gui.api.styles.StyleRenderer;
import io.github.jwdeveloper.ff.extension.gui.implementation.buttons.ButtonBuilderImpl;
import io.github.jwdeveloper.ff.extension.gui.implementation.buttons.ButtonUI;
import io.github.jwdeveloper.ff.extension.gui.implementation.managers.ButtonManagerImpl;
import io.github.jwdeveloper.ff.extension.gui.implementation.styles.DefaultStyleRenderer;
import io.github.jwdeveloper.ff.extension.gui.prefab.renderers.FluentButtonStyle;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import org.bukkit.event.inventory.InventoryType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class InventoryDecoratorImpl implements InventoryDecorator {

    private final FluentInventoryImpl inventory;
    private final List<Consumer<ButtonBuilder>> buttons;
    private final List<InventoryComponent> components;
    private final ButtonManagerImpl temporaryButtonManager;
    private StyleRenderer styleRenderer;

    public InventoryDecoratorImpl(FluentInventory inventory) {
        this.inventory = (FluentInventoryImpl) inventory;
        buttons = new ArrayList<>();
        components = new LinkedList<>();
        temporaryButtonManager = new ButtonManagerImpl(6);
        styleRenderer = new DefaultStyleRenderer(FluentApi.messages(), FluentButtonStyle.getColorSet(), FluentApi.container().findInjection(FluentTranslator.class));
    }

    @Override
    public InventoryComponent withComponent(Class<? extends InventoryComponent> component) {
        return withComponent(FluentApi.container().findInjection(component));
    }

    @Override
    public <T extends InventoryComponent> T withComponent(T component) {
        var optional = components.stream().filter(e -> e.getClass().isAssignableFrom(component.getClass())).findAny();
        if (optional.isPresent()) {
            return (T) optional.get();
        }

        component.onInitialization(this);
        components.add(component);
        return (T) component;
    }

    @Override
    public InventoryDecorator withStyleRenderer(StyleRenderer styleRenderer) {
        this.styleRenderer = styleRenderer;
        return this;
    }

    @Override
    public InventoryDecorator withParent(FluentInventory inventory) {
        inventory.setParent(inventory);
        return this;
    }

    @Override
    public InventoryDecorator withButton(ButtonUI buttonUI) {
        temporaryButtonManager.addButton(buttonUI);
        return this;
    }


    @Override
    public InventoryDecorator withButton(Consumer<ButtonBuilder> consumer) {
        buttons.add(consumer);
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
        inventory.settings().setTitle(title);
        return this;
    }

    @Override
    public InventoryDecorator withType(InventoryType type) {
        inventory.settings().setInventoryType(type);
        return this;
    }

    @Override
    public InventoryDecorator withHeight(int height) {
        inventory.settings().setHeight(height);
        return this;
    }

    @Override
    public InventoryDecorator withTasks(Consumer<FluentTaskManager> tasks) {
        return null;
    }

    @Override
    public InventoryDecorator withInventoryReference(InventoryRef inventoryRef) {
        inventoryRef.set(inventory);
        return this;
    }

    public void apply() {
        var settings = inventory.settings();
        settings.setStyleRenderer(styleRenderer);
        inventory.buttons().resize(settings.getHeight(), settings.getWidth());
        for (var button : temporaryButtonManager.getButtons()) {
            if (button == null) {
                continue;
            }
            inventory.buttons().addButton(button);
        }
        for (var consumer : buttons) {
            var builder = new ButtonBuilderImpl();
            consumer.accept(builder);
            var button = builder.build();
            inventory.buttons().addButton(button);
        }
        inventory.components().add(components);
    }
}
