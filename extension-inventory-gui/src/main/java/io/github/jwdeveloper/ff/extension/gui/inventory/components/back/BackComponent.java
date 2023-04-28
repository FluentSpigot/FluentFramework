package io.github.jwdeveloper.ff.extension.gui.inventory.components.back;

import io.github.jwdeveloper.ff.extension.gui.core.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.inventory.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.inventory.ButtonRef;
import io.github.jwdeveloper.ff.extension.gui.inventory.InventoryRef;

public class BackComponent extends InventoryComponent
{
    private final InventoryRef inventoryRef = new InventoryRef();
    private final ButtonRef closeButtonRef = new ButtonRef();

    @Override
    public void onInitialize(InventoryDecorator decorator)
    {
        decorator.withInventoryReference(inventoryRef);
        decorator.withButton(builder ->
        {
            builder.setReference(closeButtonRef);
        });
        decorator.withEvents(eventsManager ->
        {
            eventsManager.onCreate(event ->
            {
                var hasParent = inventoryRef.getOrThrow().children().hasParent();
                if(!hasParent)
                {
                    closeButtonRef.getOrThrow().setTitle("Close");
                }
                else
                {
                    closeButtonRef.getOrThrow().setTitle("Go Back");
                }
            });
        });
    }
}
