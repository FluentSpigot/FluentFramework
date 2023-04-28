package io.github.jwdeveloper.ff.extension.gui.core;

import io.github.jwdeveloper.ff.extension.gui.core.api.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.core.api.InventoryDecorator;

public class ExampleInventory implements InventoryComponent
{
    @Override
    public void onCreate(InventoryDecorator decorator) {
       /* decorator.withComponent(this)
                .withHeight(3)
                .withTitle("sasdasd");
        decorator.withButton(buttonUIBuilder ->
        {
            buttonUIBuilder.setOnRefresh(event ->
            {
                var btn = event.getButton();
                btn.setTitle("Clicks " + value);
                if (value > 10) {
                    btn.delete();
                }
            });
        });*/
    }
}
