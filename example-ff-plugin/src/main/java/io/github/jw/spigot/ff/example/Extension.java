package io.github.jw.spigot.ff.example;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import io.github.jw.spigot.ff.example.aib.ExampleBlockItem;
import io.github.jw.spigot.ff.example.drill.*;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;


public class Extension implements FluentApiExtension {
    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        builder.container().registerSingleton(ProtocolManager.class, e -> ProtocolLibrary.getProtocolManager());

        builder.container().scan(Extension.class, (classes, containerBuilder) ->
        {
            FluentLogger.LOGGER.info("hello 2reloareloadworld!", classes.size());
        });
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {
        new MiningDrill().register(fluentAPI);
        new ChopWood().register(fluentAPI);
        new BlockCanon().register(fluentAPI);
        new BlockPlaceTest().register(fluentAPI);
        new ExampleBlockItem().register();
        new MiningCart().register(fluentAPI);
    }


}
