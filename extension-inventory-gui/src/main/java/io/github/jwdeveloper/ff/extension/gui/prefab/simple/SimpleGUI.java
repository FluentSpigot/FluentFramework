package io.github.jwdeveloper.ff.extension.gui.prefab.simple;

import io.github.jwdeveloper.ff.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.ff.extension.gui.api.FluentInventory;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.api.buttons.ButtonBuilder;
import io.github.jwdeveloper.ff.extension.gui.api.events.GuiCloseEvent;
import io.github.jwdeveloper.ff.extension.gui.api.events.GuiOpenEvent;
import io.github.jwdeveloper.ff.extension.gui.api.events.GuiTickEvent;
import io.github.jwdeveloper.ff.extension.gui.api.references.InventoryRef;
import io.github.jwdeveloper.ff.extension.gui.implementation.buttons.ButtonBuilderImpl;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.check_box.CheckBoxOptions;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.progrss_bar.ProgressBarOptions;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public abstract class SimpleGUI implements InventoryComponent
{
    private InventoryDecorator decorator;
    private InventoryApi inventoryApi;
    private InventoryRef inventory;
    public abstract void onInit(InventoryDecorator decorator, InventoryApi inventoryApi);


    @Override
    public void onInitialization(InventoryDecorator decorator, InventoryApi inventoryApi)
    {
        this.decorator = decorator;
        this.inventoryApi = inventoryApi;
        inventory = new InventoryRef();
        decorator.withInventoryReference(inventory);
        onInit( decorator,  inventoryApi);
    }

    public final SimpleLogger logger()
    {
       return inventory.get().logger();
    }

    public final void setParent(FluentInventory fluentInventory)
    {
        inventory.get().setParent(fluentInventory);
    }
    public final boolean hasParent()
    {
        return inventory.get().hasParent();
    }

    public final FluentInventory parent()
    {
        return inventory.get().parent();
    }

    public final InventoryRef inventory()
    {
        return inventory;
    }

    public final void open(Player player)
    {
        inventory().get().open(player);
    }

    public final void close()
    {
        inventory().get().close();
    }

    public final void refresh()
    {
        inventory().get().refresh();;
    }

    public final <T extends InventoryComponent> T  addComponent(T inventoryComponent)
    {
        decorator.withComponent(inventoryComponent);
        return inventoryComponent;
    }

    public final void addPermissions(String ... permissions)
    {
         decorator.withPermissions(permissions);
    }

    public final String translate(String key)
    {
        return inventoryApi.translator().get(key);
    }

    public final void onOpen(Consumer<GuiOpenEvent> e)
    {
        this.decorator.withEvents(eventsManager ->
        {
            eventsManager.onOpen(e);
        });
    }

    public final void onTick(Consumer<GuiTickEvent> e)
    {
        this.decorator.withEvents(eventsManager ->
        {
            eventsManager.onTick(e);
        });
    }


    public final void onRefresh(Consumer<GuiOpenEvent> e)
    {
        this.decorator.withEvents(eventsManager ->
        {
            eventsManager.onRefresh(e);
        });
    }

    public final void onClose(Consumer<GuiCloseEvent> e)
    {
        this.decorator.withEvents(eventsManager ->
        {
            eventsManager.onClose(e);
        });
    }


    public final ButtonBuilder buttonCheckBox(Consumer<CheckBoxOptions> consumer)
    {
        var button = button();
        inventoryApi.buttons().checkBox(button, consumer);
        return button;
    }

    public final ButtonBuilder buttonProgressBar(Consumer<ProgressBarOptions> consumer)
    {
        var button = button();
        inventoryApi.buttons().progressBar(button, consumer);
        return button;
    }

    public final ButtonBuilder button()
    {
        ButtonBuilderImpl builder =new ButtonBuilderImpl();
        decorator.withButton(builder);
        return builder;
    }

    public final ButtonBuilder button(String name)
    {
        return button().withStyleRenderer(styleRendererOptionsDecorator ->
        {
            styleRendererOptionsDecorator.withTitle(name);
        });
    }

    public final ButtonBuilder button(String name, int height, int width)
    {
        return button(name).withPosition(height,width);
    }


    public final ButtonBuilder button(String name, Material material)
    {
        return button(name).withMaterial(material);
    }

    public final ButtonBuilder button(String name, Material material, int height, int width)
    {
        return button(name).withMaterial(material).withPosition(height,width);
    }
}
