package io.github.jwdeveloper.ff.extension.gui.implementation;

import io.github.jwdeveloper.ff.extension.gui.OLD.events.InventorySpigotListener;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;

public class FluentInventoryExtension implements FluentApiExtension {
    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder)
    {
        builder.container().registerSigleton(InventorySpigotListener.class);
        builder.container().registerSigleton(InventoryApi.class, InventoryApiImpl.class);
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

        fluentAPI.container().findInjection(InventorySpigotListener.class);
    }
}
