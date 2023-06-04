package io.github.jwdeveloper.ff.extension.gui.inventory.components.back;

import io.github.jwdeveloper.ff.extension.gui.core.api.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.core.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.inventory.ButtonRef;
import io.github.jwdeveloper.ff.extension.gui.inventory.InventoryRef;

public class BackComponent implements InventoryComponent {
    private final InventoryRef inventoryRef = new InventoryRef();
    private final ButtonRef closeButtonRef = new ButtonRef();

    @Override
    public void onCreate(InventoryDecorator decorator) {
        decorator.withInventoryReference(inventoryRef);
        decorator.withButton(builder ->
        {
            builder.setReference(closeButtonRef);
        });
        decorator.withEvents(eventsManager ->
        {
            eventsManager.onOpen(event ->
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
