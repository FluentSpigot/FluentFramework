package io.github.jwdeveloper.ff.extension.gui;



import io.github.jwdeveloper.ff.extension.gui.core.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.core.implementation.InventoryComponentBase;
import io.github.jwdeveloper.ff.extension.gui.inventory.ButtonRef;
import io.github.jwdeveloper.ff.extension.gui.inventory.InventoryRef;

public class PianoComponent extends InventoryComponentBase {

    private ButtonRef joinButton = new ButtonRef();

    private InventoryRef inventoryRef;
    @Override
    public void onCreate(InventoryDecorator decorator) {
        decorator.withInventoryReference(inventoryRef);
        decorator.withButton(fluentButtonUIBuilder ->
        {
           fluentButtonUIBuilder.setReference(joinButton);
        });

    }



}
