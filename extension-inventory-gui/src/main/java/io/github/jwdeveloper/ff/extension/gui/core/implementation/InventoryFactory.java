package io.github.jwdeveloper.ff.extension.gui.core.implementation;

import io.github.jwdeveloper.ff.extension.gui.core.ExampleList;
import io.github.jwdeveloper.ff.extension.gui.core.api.FluentInventory;
import io.github.jwdeveloper.ff.extension.gui.core.api.InventorySettings;
import io.github.jwdeveloper.ff.extension.gui.core.implementation.managers.ButtonManagerImpl;
import io.github.jwdeveloper.ff.extension.gui.core.implementation.managers.ChildernManagerImpl;
import io.github.jwdeveloper.ff.extension.gui.core.implementation.managers.EventManagerImpl;
import io.github.jwdeveloper.ff.extension.gui.core.implementation.managers.PermissionManagerImpl;
import new_version.implementation.FluentInventoryImpl;
import io.github.jwdeveloper.ff.core.translator.api.FluentTranslator;
import lombok.Getter;
import new_version.implementation.InventorySpigotListener;

public class InventoryFactory {
    private InventorySpigotListener listener;

    @Getter
    private FluentTranslator translator;

    public InventoryFactory(InventorySpigotListener listener, FluentTranslator translator)
    {
        this.listener = listener;
        this.translator = translator;
    }


    public FluentInventory getBasicInventory() {
        var settings = new InventorySettings();
        var childrenManager = new ChildernManagerImpl();
        var eventsManager = new EventManagerImpl();
        var permissionManager = new PermissionManagerImpl(settings);
        var buttonManager = new ButtonManagerImpl(settings);
        return null;
      //  return new FluentInventoryImpl(childrenManager, buttonManager,eventsManager,permissionManager, listener, settings );
    }

    public <T> ExampleList<T> getListInventory()
    {
        return new ExampleList<T>();
    }

}
